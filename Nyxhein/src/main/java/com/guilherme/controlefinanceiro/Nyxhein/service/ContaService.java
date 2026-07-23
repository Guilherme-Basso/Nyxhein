package com.guilherme.controlefinanceiro.Nyxhein.service;

import com.guilherme.controlefinanceiro.Nyxhein.entity.*;
import com.guilherme.controlefinanceiro.Nyxhein.repository.ContaRepository;
import com.guilherme.controlefinanceiro.Nyxhein.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContaService {
    private final ContaRepository contaRepository;
    private final TransacaoRepository transacaoRepository;

    public ContaService (ContaRepository contaRepository, TransacaoRepository transacaoRepository){
        this.transacaoRepository = transacaoRepository;
        this.contaRepository = contaRepository;
    }

    public List<Conta> listarTodas(){
        return contaRepository.findAll();
    }

    public Conta buscarPorId(Long id){
        return contaRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Conta não encontrada"));
    }

    public Conta criar (Conta conta){
        conta.setCriadoEm(LocalDateTime.now());
        return contaRepository.save(conta);
    }

    public BigDecimal calcularSaldoAtual (Long contaId){
        Conta conta = buscarPorId(contaId);

        BigDecimal totalPago = transacaoRepository.findByContaId(contaId).stream()
                .filter(t -> t.getStatus() == StatusTransacao.PAGO)
                .map(this::valorComSinal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return conta.getSaldoInicial().add(totalPago);
    }

    public BigDecimal calcularSaldoPrevisto(Long contaId) {
        Conta conta = buscarPorId(contaId);

        BigDecimal totalGeral = transacaoRepository.findByContaId(contaId).stream()
                .map(this::valorComSinal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return conta.getSaldoInicial().add(totalGeral);
    }

    public BigDecimal calcularSaldoAtualPorTitular(Titular titular) {
        return contaRepository.findByTitular(titular).stream()
                .map(conta -> calcularSaldoAtual(conta.getId()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calcularSaldoPrevistoPorTitular(Titular titular) {
        return contaRepository.findByTitular(titular).stream()
                .map(conta -> calcularSaldoPrevisto(conta.getId()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal valorComSinal(Transacao transacao) {
        if (transacao.getCategoria().getTipo() == TipoCategoria.RECEITA) {
            return transacao.getValor();
        } else {
            return transacao.getValor().negate();
        }
    }

    public Conta atualizar(Long id, Conta dadosAtualizados) {
        Conta contaExistente = buscarPorId(id);
        contaExistente.setNome(dadosAtualizados.getNome());
        contaExistente.setTipo(dadosAtualizados.getTipo());
        contaExistente.setTitular(dadosAtualizados.getTitular());
        contaExistente.setSaldoInicial(dadosAtualizados.getSaldoInicial());
        return contaRepository.save(contaExistente);
    }

    public void deletar(Long id) {
        Conta conta = buscarPorId(id);
        boolean temTransacoes = !transacaoRepository.findByContaId(id).isEmpty();

        if (temTransacoes) {
            throw new RuntimeException("Não é possível excluir uma conta que possui lançamentos. Exclua os lançamentos primeiro.");
        }

        contaRepository.delete(conta);
    }
}
