package com.guilherme.controlefinanceiro.Nyxhein.repository;

import com.guilherme.controlefinanceiro.Nyxhein.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findByContaId (Long contaId);
    List<Transacao> findByCategoriaId (Long categoriaId);
    
}
