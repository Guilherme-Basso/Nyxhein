package com.guilherme.controlefinanceiro.Nyxhein.dto;

import com.guilherme.controlefinanceiro.Nyxhein.entity.StatusTransacao;
import java.math.BigDecimal;
import java.time.LocalDate;

public record NovaTransacaoRequest(
        String descricao,
        BigDecimal valor,
        LocalDate dataTransacao,
        LocalDate dataVencimento,
        StatusTransacao status,
        Integer numeroParcelas
) {}