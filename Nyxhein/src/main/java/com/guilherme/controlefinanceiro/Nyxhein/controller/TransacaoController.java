package com.guilherme.controlefinanceiro.Nyxhein.controller;

import com.guilherme.controlefinanceiro.Nyxhein.entity.Transacao;
import com.guilherme.controlefinanceiro.Nyxhein.repository.TransacaoRepository;
import com.guilherme.controlefinanceiro.Nyxhein.service.TransacaoService;
import org.apache.coyote.Response;
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

    @PostMapping
    public ResponseEntity<Transacao> Criar(
            @RequestParam Long contaId,
            @RequestParam Long categoriaId,
            @RequestBody Transacao transacao){
        Transacao transacaoCriada = transacaoService.criar(contaId, categoriaId, transacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(transacaoCriada);
    }

    @PatchMapping("/{id}/pagar")
    public ResponseEntity<Transacao> marcarComoPaga(@PathVariable Long id) {
        return ResponseEntity.ok(transacaoService.marcarComoPaga(id));
    }
}
