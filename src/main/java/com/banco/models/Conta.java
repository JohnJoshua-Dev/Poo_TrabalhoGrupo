package com.banco.models;

import java.time.LocalDateTime;

public class Conta {
    private Long id;
    private String numero;
    private String tipo;
    private double saldo;
    private double saldoDisponivel;
    private boolean ativa;
    private LocalDateTime dataCriacao;
    private Cliente cliente;

    // Construtores
    public Conta() {
    }

    public Conta(String numero, String tipo, Cliente cliente) {
        this.numero = numero;
        this.tipo = tipo;
        this.saldo = 0.0;
        this.saldoDisponivel = 0.0;
        this.ativa = true;
        this.dataCriacao = LocalDateTime.now();
        this.cliente = cliente;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getSaldoDisponivel() {
        return saldoDisponivel;
    }

    public void setSaldoDisponivel(double saldoDisponivel) {
        this.saldoDisponivel = saldoDisponivel;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return numero + " - " + tipo;
    }
}
