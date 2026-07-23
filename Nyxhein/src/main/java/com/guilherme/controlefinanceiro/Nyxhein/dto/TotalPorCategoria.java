package com.guilherme.controlefinanceiro.Nyxhein.dto;

import com.guilherme.controlefinanceiro.Nyxhein.entity.TipoCategoria;

import java.math.BigDecimal;

public record TotalPorCategoria (
        Long categoriaId,
        String categoriaNome,
        TipoCategoria tipo,
        BigDecimal total
) { }
