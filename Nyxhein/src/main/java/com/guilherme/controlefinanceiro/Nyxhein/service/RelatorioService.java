package com.guilherme.controlefinanceiro.Nyxhein.service;

import com.guilherme.controlefinanceiro.Nyxhein.dto.ResumoMes;
import com.guilherme.controlefinanceiro.Nyxhein.dto.TotalPorCategoria;
import com.guilherme.controlefinanceiro.Nyxhein.dto.TotalPorMesTipo;
import com.guilherme.controlefinanceiro.Nyxhein.entity.StatusTransacao;
import com.guilherme.controlefinanceiro.Nyxhein.entity.TipoCategoria;
import com.guilherme.controlefinanceiro.Nyxhein.entity.Titular;
import com.guilherme.controlefinanceiro.Nyxhein.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RelatorioService {
    private final TransacaoRepository transacaoRepository;

    public RelatorioService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    public List<TotalPorCategoria> gastosPorCategoria(int ano, Integer mes, Titular titular, StatusTransacao status){
        return transacaoRepository.totalPorCategoria(ano, mes, titular, status);
    }

    public List<ResumoMes> resumoMensal(int ano, Titular titular, StatusTransacao status){
        List<TotalPorMesTipo> resultados = transacaoRepository.totalPorMesETipo(ano, titular, status);
        Map<Integer, BigDecimal> receitaPorMes = new HashMap<>();
        Map<Integer, BigDecimal> despesasPorMes = new HashMap<>();

        for (TotalPorMesTipo r: resultados){
            if (r.tipo() == TipoCategoria.RECEITA){
                receitaPorMes.put(r.mes(), r.total());
            } else {
                despesasPorMes.put(r.mes(), r.total());
            }
        }

        List<ResumoMes> resumo = new ArrayList<>();
        for (int mes = 1; mes <= 12; mes++){
            BigDecimal receita = receitaPorMes.getOrDefault(mes, BigDecimal.ZERO);
            BigDecimal despesa =  despesasPorMes.getOrDefault(mes, BigDecimal.ZERO);
            resumo.add(new ResumoMes(mes, receita, despesa, receita.subtract(despesa)));
        }

        return  resumo;
    }
}
