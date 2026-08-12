package com.guilherme.controlefinanceiro.Nyxhein.controller;

import com.guilherme.controlefinanceiro.Nyxhein.dto.NovaTransacaoRequest;
import com.guilherme.controlefinanceiro.Nyxhein.entity.Transacao;
import com.guilherme.controlefinanceiro.Nyxhein.service.TransacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transacoes")
public class TransacaoController {
    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService){
        this.transacaoService = transacaoService;
    }

    @GetMapping
    public List<Transacao> listarTodas(){
        return transacaoService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transacao> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(transacaoService.buscarPorId(id));
    }



    @PatchMapping("/{id}/pagar")
    public ResponseEntity<Transacao> marcarComoPaga(@PathVariable Long id) {
        return ResponseEntity.ok(transacaoService.marcarComoPaga(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transacao> atualizar(
            @PathVariable Long id,
            @RequestParam Long contaId,
            @RequestParam Long categoriaId,
            @RequestBody Transacao transacao) {

        Transacao transacaoAtualizada = transacaoService.atualizar(id, contaId, categoriaId, transacao);
        return ResponseEntity.ok(transacaoAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        transacaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<List<Transacao>> criar(
            @RequestParam Long contaId,
            @RequestParam Long categoriaId,
            @RequestBody NovaTransacaoRequest request) {

        List<Transacao> criadas = transacaoService.criar(contaId, categoriaId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(criadas);
    }

    @DeleteMapping("/grupo/{grupoRecorrencia}/pendentes")
    public ResponseEntity<Void> cancelarPendentesDoGrupo(@PathVariable String grupoRecorrencia) {
        transacaoService.cancelarPendentesDoGrupo(grupoRecorrencia);
        return ResponseEntity.noContent().build();
    }
}
