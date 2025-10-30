package com.banco.models;

import java.time.LocalDateTime;

public class Cliente {
    private Long id;
    private String nomeCompleto;
    private String bi;
    private String nuit;
    private String telefone;
    private String email;
    private String morada;
    private boolean ativo;
    private LocalDateTime dataCriacao;

    // Construtores
    public Cliente() {
    }

    public Cliente(String nomeCompleto, String bi, String nuit, String telefone, String email, String morada) {
        this.nomeCompleto = nomeCompleto;
        this.bi = bi;
        this.nuit = nuit;
        this.telefone = telefone;
        this.email = email;
        this.morada = morada;
        this.ativo = true;
        this.dataCriacao = LocalDateTime.now();
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    // Alias methods for compatibility
    public String getNome() {
        return getNomeCompleto();
    }

    public void setNome(String nome) {
        setNomeCompleto(nome);
    }

    public void setTipoCliente(String tipo) {
    } // This method is used by the controller but might not be needed

    public String getBi() {
        return bi;
    }

    public void setBi(String bi) {
        this.bi = bi;
    }

    public String getNuit() {
        return nuit;
    }

    public void setNuit(String nuit) {
        this.nuit = nuit;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Override
    public String toString() {
        return nomeCompleto + " (" + bi + ")";
    }
}