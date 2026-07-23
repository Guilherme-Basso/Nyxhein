package com.guilherme.controlefinanceiro.Nyxhein.controller;

import com.guilherme.controlefinanceiro.Nyxhein.dto.ResumoMes;
import com.guilherme.controlefinanceiro.Nyxhein.dto.TotalPorCategoria;
import com.guilherme.controlefinanceiro.Nyxhein.entity.StatusTransacao;
import com.guilherme.controlefinanceiro.Nyxhein.entity.Titular;
import com.guilherme.controlefinanceiro.Nyxhein.service.RelatorioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {
    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/por-categoria")
    public List<TotalPorCategoria> porCategoria(
            @RequestParam int ano,
            @RequestParam(required = false) Integer mes,
            @RequestParam(required = false) Titular titular,
            @RequestParam(required = false) StatusTransacao status) {
        return relatorioService.gastosPorCategoria(ano, mes, titular, status);
    }

    @GetMapping("/mensal")
    public List<ResumoMes> mensal(
            @RequestParam int ano,
            @RequestParam(required = false) Titular titular,
            @RequestParam(required = false) StatusTransacao status) {
        return relatorioService.resumoMensal(ano, titular, status);
    }
}
