package com.guilherme.controlefinanceiro.Nyxhein.service;

import com.guilherme.controlefinanceiro.Nyxhein.entity.Categoria;
import com.guilherme.controlefinanceiro.Nyxhein.entity.Conta;
import com.guilherme.controlefinanceiro.Nyxhein.entity.StatusTransacao;
import com.guilherme.controlefinanceiro.Nyxhein.entity.Transacao;
import com.guilherme.controlefinanceiro.Nyxhein.repository.TransacaoRepository;
import org.springframework.stereotype.Service;
import com.guilherme.controlefinanceiro.Nyxhein.dto.NovaTransacaoRequest;
import java.util.ArrayList;
import java.util.UUID;

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

    public List<Transacao> criar(Long contaId, Long categoriaId, NovaTransacaoRequest request) {
        Conta conta = contaService.buscarPorId(contaId);
        Categoria categoria = categoriaService.buscarPorId(categoriaId);

        int parcelas = (request.numeroParcelas() == null || request.numeroParcelas() < 1)
                ? 1 : request.numeroParcelas();
        String grupo = parcelas > 1 ? UUID.randomUUID().toString() : null;

        List<Transacao> criadas = new ArrayList<>();

        for (int i = 0; i < parcelas; i++) {
            Transacao t = new Transacao();
            t.setDescricao(parcelas > 1
                    ? request.descricao() + " (" + (i + 1) + "/" + parcelas + ")"
                    : request.descricao());
            t.setValor(request.valor());
            t.setDataTransacao(request.dataTransacao().plusMonths(i));
            t.setDataVencimento(request.dataVencimento() != null
                    ? request.dataVencimento().plusMonths(i) : null);
            t.setStatus(i == 0 ? request.status() : StatusTransacao.PENDENTE);
            t.setConta(conta);
            t.setCategoria(categoria);
            t.setGrupoRecorrencia(grupo);
            t.setCriadoEm(LocalDateTime.now());

            criadas.add(transacaoRepository.save(t));
        }

        return criadas;
    }

    public int cancelarPendentesDoGrupo(String grupo) {
        List<Transacao> pendentes = transacaoRepository.findByGrupoRecorrenciaAndStatus(grupo, StatusTransacao.PENDENTE);
        transacaoRepository.deleteAll(pendentes);
        return pendentes.size();
    }

    public Transacao marcarComoPaga(Long id) {
        Transacao transacao = buscarPorId(id);
        transacao.setStatus(StatusTransacao.PAGO);
        return transacaoRepository.save(transacao);
    }

    public Transacao atualizar(Long id, Long contaId, Long categoriaId, Transacao dadosAtualizados) {
        Transacao transacaoExistente = buscarPorId(id);

        Conta conta = contaService.buscarPorId(contaId);
        Categoria categoria = categoriaService.buscarPorId(categoriaId);

        transacaoExistente.setDescricao(dadosAtualizados.getDescricao());
        transacaoExistente.setValor(dadosAtualizados.getValor());
        transacaoExistente.setDataTransacao(dadosAtualizados.getDataTransacao());
        transacaoExistente.setDataVencimento(dadosAtualizados.getDataVencimento());
        transacaoExistente.setStatus(dadosAtualizados.getStatus());
        transacaoExistente.setConta(conta);
        transacaoExistente.setCategoria(categoria);

        return transacaoRepository.save(transacaoExistente);
    }

    public void deletar(Long id) {
        Transacao transacao = buscarPorId(id);
        transacaoRepository.delete(transacao);
    }
}
