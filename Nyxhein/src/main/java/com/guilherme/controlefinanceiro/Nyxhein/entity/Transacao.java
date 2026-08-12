package com.guilherme.controlefinanceiro.Nyxhein.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transacao")
public class Transacao {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String descricao;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valor;
    @Column(name = "data_transacao", nullable = false)
    private LocalDate dataTransacao;
    @ManyToOne
    @JoinColumn(name = "conta_id", nullable = false)
    private Conta conta;
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusTransacao status;
    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;
    @Column(name = "grupo_recorrencia", length = 36)
    private String grupoRecorrencia;

    public Transacao(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getDataTransacao() {
        return dataTransacao;
    }

    public void setDataTransacao(LocalDate dataTransacao) {
        this.dataTransacao = dataTransacao;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public StatusTransacao getStatus() {
        return status;
    }

    public void setStatus(StatusTransacao status) {
        this.status = status;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getGrupoRecorrencia() {
        return grupoRecorrencia;
    }

    public void setGrupoRecorrencia(String grupoRecorrencia) {
        this.grupoRecorrencia = grupoRecorrencia;
    }
}
