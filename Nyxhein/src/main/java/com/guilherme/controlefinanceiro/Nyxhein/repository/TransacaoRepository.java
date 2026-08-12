package com.guilherme.controlefinanceiro.Nyxhein.repository;

import com.guilherme.controlefinanceiro.Nyxhein.dto.TotalPorCategoria;
import com.guilherme.controlefinanceiro.Nyxhein.dto.TotalPorMesTipo;
import com.guilherme.controlefinanceiro.Nyxhein.entity.StatusTransacao;
import com.guilherme.controlefinanceiro.Nyxhein.entity.Titular;
import com.guilherme.controlefinanceiro.Nyxhein.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findByContaId (Long contaId);
    List<Transacao> findByCategoriaId (Long categoriaId);
    List<Transacao> findByGrupoRecorrenciaAndStatus(String grupoRecorrencia, StatusTransacao status);

    @Query("""
        SELECT new com.guilherme.controlefinanceiro.Nyxhein.dto.TotalPorCategoria(
            t.categoria.id, t.categoria.nome, t.categoria.tipo, SUM(t.valor))
        FROM Transacao t
        WHERE YEAR(t.dataTransacao) = :ano
          AND (:mes IS NULL OR MONTH(t.dataTransacao) = :mes)
          AND (:titular IS NULL OR t.conta.titular = :titular)
          AND (:status IS NULL OR t.status = :status)
        GROUP BY t.categoria.id, t.categoria.nome, t.categoria.tipo
        ORDER BY SUM(t.valor) DESC
        """)
    List<TotalPorCategoria> totalPorCategoria(
            @Param("ano") int ano,
            @Param("mes") Integer mes,
            @Param("titular") Titular titular,
            @Param("status") StatusTransacao status);

    @Query("""
        SELECT new com.guilherme.controlefinanceiro.Nyxhein.dto.TotalPorMesTipo(
            MONTH(t.dataTransacao), t.categoria.tipo, SUM(t.valor))
        FROM Transacao t
        WHERE YEAR(t.dataTransacao) = :ano
          AND (:titular IS NULL OR t.conta.titular = :titular)
          AND (:status IS NULL OR t.status = :status)
        GROUP BY MONTH(t.dataTransacao), t.categoria.tipo
        """)
    List<TotalPorMesTipo> totalPorMesETipo(
            @Param("ano") int ano,
            @Param("titular") Titular titular,
            @Param("status") StatusTransacao status);
}
