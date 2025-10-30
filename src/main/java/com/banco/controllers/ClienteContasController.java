package com.banco.controllers;

import javafx.fxml.FXML;
import com.banco.models.Cliente;
import com.banco.models.Conta;

public class ClienteContasController {
    private Cliente cliente;
    private Conta conta;

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    @FXML
    public void initialize() {
        // Initialize view elements
    }
}