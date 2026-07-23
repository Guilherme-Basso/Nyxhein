package com.guilherme.controlefinanceiro.Nyxhein.dto;

import java.math.BigDecimal;

public record ResumoMes(
        int mes,
        BigDecimal totalReceita,
        BigDecimal totalDespesa,
        BigDecimal saldo
) {
}
