package com.guilherme.controlefinanceiro.Nyxhein.service;

import com.guilherme.controlefinanceiro.Nyxhein.entity.Categoria;
import com.guilherme.controlefinanceiro.Nyxhein.entity.Conta;
import com.guilherme.controlefinanceiro.Nyxhein.entity.StatusTransacao;
import com.guilherme.controlefinanceiro.Nyxhein.entity.Transacao;
import com.guilherme.controlefinanceiro.Nyxhein.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransacaoService {
    private final TransacaoRepository transacaoRepository;
    private final ContaService contaService;
    private final CategoriaService categoriaService;

    public TransacaoService(TransacaoRepository transacaoRepository, ContaService contaService, CategoriaService categoriaService){
        this.transacaoRepository = transacaoRepository;
        this.contaService = contaService;
        this.categoriaService = categoriaService;
    }

    public List<Transacao> listarTodas(){
        return transacaoRepository.findAll();
    }

    public Transacao buscarPorId(Long id){
        return transacaoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Transacão não encontrada com id: "+ id));
    }

    public Transacao criar(Long contaId, Long categoriaId, Transacao transacao){
        Conta conta = contaService.buscarPorId(contaId);
        Categoria categoria = categoriaService.buscarPorId(categoriaId);

        transacao.setConta(conta);
        transacao.setCategoria(categoria);
        transacao.setCriadoEm(LocalDateTime.now());

        return transacaoRepository.save(transacao);
    }

    public Transacao marcarComoPaga(Long id) {
        Transacao transacao = buscarPorId(id);
        transacao.setStatus(StatusTransacao.PAGO);
        return transacaoRepository.save(transacao);
    }
}
