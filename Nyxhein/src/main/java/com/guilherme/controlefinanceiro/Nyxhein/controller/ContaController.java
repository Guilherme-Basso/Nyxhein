package com.guilherme.controlefinanceiro.Nyxhein.controller;

import com.guilherme.controlefinanceiro.Nyxhein.entity.Conta;
import com.guilherme.controlefinanceiro.Nyxhein.entity.Titular;
import com.guilherme.controlefinanceiro.Nyxhein.service.ContaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contas")
public class ContaController {
    private final ContaService contaService;

    public ContaController(ContaService contaService){
        this.contaService = contaService;
    }

    @GetMapping
    public List<Conta> listarTodas(){
        return contaService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conta> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(contaService.buscarPorId(id));
    }

    @GetMapping("/{id}/saldo")
    public ResponseEntity<Map<String, BigDecimal>> saldoDaConta(@PathVariable Long id) {
        Map<String, BigDecimal> saldos = new HashMap<>();
        saldos.put("saldoAtual", contaService.calcularSaldoAtual(id));
        saldos.put("saldoPrevisto", contaService.calcularSaldoPrevisto(id));
        return ResponseEntity.ok(saldos);
    }

    @GetMapping("/saldo-titular/{titular}")
    public ResponseEntity<Map<String, BigDecimal>> saldoPorTitular(@PathVariable Titular titular) {
        Map<String, BigDecimal> saldos = new HashMap<>();
        saldos.put("saldoAtual", contaService.calcularSaldoAtualPorTitular(titular));
        saldos.put("saldoPrevisto", contaService.calcularSaldoPrevistoPorTitular(titular));
        return ResponseEntity.ok(saldos);
    }

    @PostMapping
    public ResponseEntity<Conta> criar(@RequestBody Conta conta){
        Conta contaCriada = contaService.criar(conta);
        return ResponseEntity.status(HttpStatus.CREATED).body(contaCriada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Conta> atualizar(@PathVariable Long id, @RequestBody Conta conta) {
        return ResponseEntity.ok(contaService.atualizar(id, conta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        contaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
