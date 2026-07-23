package com.guilherme.controlefinanceiro.Nyxhein.repository;

import com.guilherme.controlefinanceiro.Nyxhein.entity.Categoria;
import com.guilherme.controlefinanceiro.Nyxhein.entity.TipoCategoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List <Categoria> findByTipo(TipoCategoria tipo);
    List <Categoria> findByNomeContainingIgnoreCase(String nome);
}
