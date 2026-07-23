package com.guilherme.controlefinanceiro.Nyxhein.repository;

import com.guilherme.controlefinanceiro.Nyxhein.entity.Conta;
import com.guilherme.controlefinanceiro.Nyxhein.entity.Titular;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContaRepository extends JpaRepository<Conta, Long> {
    List<Conta> findByTitular(Titular titular);
}
