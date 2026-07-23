package com.guilherme.controlefinanceiro.Nyxhein.dto;

import com.guilherme.controlefinanceiro.Nyxhein.entity.TipoCategoria;

import java.math.BigDecimal;

public record TotalPorMesTipo(
        Integer mes,
        TipoCategoria tipo,
        BigDecimal total
) {
}
