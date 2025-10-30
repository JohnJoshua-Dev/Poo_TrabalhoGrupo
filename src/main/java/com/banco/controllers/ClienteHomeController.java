package com.banco.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.banco.models.Cliente;
import com.banco.models.Conta;
import com.banco.models.Usuario;
import com.banco.utils.AlertUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClienteHomeController {

    @FXML
    private Label lblNomeCliente;
    @FXML
    private Label lblNumeroConta;
    @FXML
    private Label lblBoasVindas;
    @FXML
    private Label lblSaldoDisponivel;
    @FXML
    private Label lblSaldoPrincipal;
    @FXML
    private Label lblUltimoAcesso;

    @FXML
    private HBox btnDashboard;
    @FXML
    private HBox btnContas;
    @FXML
    private HBox btnTransferencias;
    @FXML
    private HBox btnPagamentos;
    @FXML
    private HBox btnExtrato;
    @FXML
    private HBox btnCartoes;

    @FXML
    private VBox contentArea;

    private Cliente cliente;
    private Conta contaPrincipal;

    @FXML
    public void initialize() {
        configurarEfeitosHover();
        setActiveMenu(btnDashboard);
        atualizarBoasVindas();
    }

    public void initialize(Usuario usuario) {
        // Initialize normal UI components
        initialize();

        // Set the client data from the logged in user
        this.cliente = usuario.getCliente();
        carregarDadosCliente();
    }

    private void carregarDadosCliente() {
        // Dados mock - em produção viriam da sessão/login
        this.cliente = new Cliente();
        cliente.setNome("João Silva");
        cliente.setEmail("joao.silva@email.com");
        cliente.setTipoCliente("PREMIUM");

        this.contaPrincipal = new Conta();
        contaPrincipal.setNumero("00123456-7");
        contaPrincipal.setSaldo(45678.90);
        contaPrincipal.setSaldoDisponivel(25450.00);
        contaPrincipal.setTipo("CONTA_CORRENTE");

        // Atualizar interface
        lblNomeCliente.setText(getClienteNome());
        lblNumeroConta.setText("Conta: " + contaPrincipal.getNumero());
        lblSaldoPrincipal.setText(String.format("%,.2f MT", contaPrincipal.getSaldo()));
        lblSaldoDisponivel.setText(String.format("%,.2f MT", contaPrincipal.getSaldoDisponivel()));

        // Último acesso
        String ultimoAcesso = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        lblUltimoAcesso.setText("Hoje, " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
    }

    private void atualizarBoasVindas() {
        int hora = LocalDateTime.now().getHour();
        String saudacao;

        if (hora < 12) {
            saudacao = "Bom dia";
        } else if (hora < 18) {
            saudacao = "Boa tarde";
        } else {
            saudacao = "Boa noite";
        }

        lblBoasVindas.setText(saudacao + ", " + getClienteNome().split(" ")[0] + "!");
    }

    private void configurarEfeitosHover() {
        HBox[] menus = { btnDashboard, btnContas, btnTransferencias, btnPagamentos, btnExtrato, btnCartoes };

        for (HBox botao : menus) {
            botao.setOnMouseEntered(e -> {
                if (!botao.getStyle().contains("#3498db")) {
                    botao.setStyle(botao.getStyle().replace("transparent", "#34495e"));
                }
            });

            botao.setOnMouseExited(e -> {
                if (!botao.getStyle().contains("#3498db")) {
                    botao.setStyle(botao.getStyle().replace("#34495e", "transparent"));
                }
            });
        }
    }

    @FXML
    private void handleDashboard() {
        setActiveMenu(btnDashboard);
        // Dashboard já está visível
    }

    @FXML
    private void handleContas() {
        setActiveMenu(btnContas);
        abrirGestaoContas();
    }

    @FXML
    private void handleTransferencias() {
        setActiveMenu(btnTransferencias);
        abrirTransferencias();
    }

    @FXML
    private void handlePagamentos() {
        setActiveMenu(btnPagamentos);
        abrirPagamentos();
    }

    @FXML
    private void handleExtrato() {
        setActiveMenu(btnExtrato);
        abrirExtrato();
    }

    @FXML
    private void handleCartoes() {
        setActiveMenu(btnCartoes);
        abrirGestaoCartoes();
    }

    @FXML
    private void handleTransferir() {
        abrirFormularioTransferencia();
    }

    @FXML
    private void handlePagar() {
        abrirFormularioPagamento();
    }

    @FXML
    private void handleDepositar() {
        abrirFormularioDeposito();
    }

    @FXML
    private void handleVerExtrato() {
        abrirExtratoCompleto();
    }

    @FXML
    private void handleVerTodasTransacoes() {
        abrirExtratoCompleto();
    }

    @FXML
    private void handleGerirCartoes() {
        abrirGestaoCartoes();
    }

    @FXML
    private void handleLogout() {
        boolean confirmado = AlertUtils.mostrarConfirmacao(
                "Confirmar Saída",
                "Tem certeza que deseja sair da sua conta?");

        if (confirmado) {
            try {
                Stage currentStage = (Stage) lblNomeCliente.getScene().getWindow();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root, 900, 650);
                scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());

                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Sistema Bancário - POO");
                stage.setMinWidth(900);
                stage.setMinHeight(650);

                stage.show();
                currentStage.close();

            } catch (IOException e) {
                AlertUtils.mostrarErro("Erro", "Não foi possível fazer logout: " + e.getMessage());
            }
        }
    }

    private void setActiveMenu(HBox activeMenu) {
        HBox[] menus = { btnDashboard, btnContas, btnTransferencias, btnPagamentos, btnExtrato, btnCartoes };

        for (HBox menu : menus) {
            menu.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-padding: 15 25;");
            if (menu.getChildren().get(1) instanceof Label) {
                ((Label) menu.getChildren().get(1)).setStyle("-fx-text-fill: #ecf0f1; -fx-padding: 0 0 0 15;");
            }
        }

        activeMenu.setStyle("-fx-background-color: #3498db; -fx-cursor: hand; -fx-padding: 15 25;");
        if (activeMenu.getChildren().get(1) instanceof Label) {
            ((Label) activeMenu.getChildren().get(1))
                    .setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 0 0 0 15;");
        }
    }

    private void abrirGestaoContas() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/cliente-contas.fxml"));
            Parent contasView = loader.load();

            ClienteContasController controller = loader.getController();
            controller.setCliente(cliente);

            contentArea.getChildren().clear();
            contentArea.getChildren().add(contasView);

        } catch (IOException e) {
            AlertUtils.mostrarErro("Erro", "Não foi possível carregar as contas: " + e.getMessage());
        }
    }

    private void abrirTransferencias() {
        AlertUtils.mostrarInfo("Transferências", "Módulo de transferências em desenvolvimento!");
    }

    private void abrirPagamentos() {
        AlertUtils.mostrarInfo("Pagamentos", "Módulo de pagamentos em desenvolvimento!");
    }

    private void abrirExtrato() {
        AlertUtils.mostrarInfo("Extrato", "Módulo de extrato em desenvolvimento!");
    }

    private void abrirGestaoCartoes() {
        AlertUtils.mostrarInfo("Cartões", "Módulo de cartões em desenvolvimento!");
    }

    private void abrirFormularioTransferencia() {
        AlertUtils.mostrarInfo("Transferência", "Funcionalidade de transferência em desenvolvimento!");
    }

    private void abrirFormularioPagamento() {
        AlertUtils.mostrarInfo("Pagamento", "Funcionalidade de pagamento em desenvolvimento!");
    }

    private void abrirFormularioDeposito() {
        AlertUtils.mostrarInfo("Depósito", "Funcionalidade de depósito em desenvolvimento!");
    }

    private void abrirExtratoCompleto() {
        AlertUtils.mostrarInfo("Extrato Completo", "Extrato completo em desenvolvimento!");
    }

    // Métodos para atualizar dados
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        atualizarInterface();
    }

    public void setContaPrincipal(Conta conta) {
        this.contaPrincipal = conta;
        atualizarInterface();
    }

    private void atualizarInterface() {
        if (cliente != null) {
            lblNomeCliente.setText(getClienteNome());
            lblBoasVindas.setText("Bem-vindo, " + getClienteNome().split(" ")[0] + "!");
        }

        if (contaPrincipal != null) {
            lblNumeroConta.setText("Conta: " + contaPrincipal.getNumero());
            lblSaldoPrincipal.setText(String.format("%,.2f MT", contaPrincipal.getSaldo()));
            lblSaldoDisponivel.setText(String.format("%,.2f MT", contaPrincipal.getSaldoDisponivel()));
        }
    }

    /**
     * Tenta obter o nome do cliente de forma resiliente sem depender de um getter
     * específico
     * (usa reflexão para métodos getNome/getName ou campos nome/name).
     */
    private String getClienteNome() {
        if (cliente == null)
            return "";
        try {
            // tentar métodos getNome() e getName()
            for (String methodName : new String[] { "getNome", "getName" }) {
                try {
                    java.lang.reflect.Method m = cliente.getClass().getMethod(methodName);
                    Object val = m.invoke(cliente);
                    if (val != null)
                        return val.toString();
                } catch (NoSuchMethodException ignored) {
                }
            }
            // tentar campos 'nome' ou 'name'
            for (String fieldName : new String[] { "nome", "name" }) {
                try {
                    java.lang.reflect.Field f = cliente.getClass().getDeclaredField(fieldName);
                    f.setAccessible(true);
                    Object val = f.get(cliente);
                    if (val != null)
                        return val.toString();
                } catch (NoSuchFieldException ignored) {
                }
            }
        } catch (Exception ignored) {
        }
        return "";
    }

    public void atualizarSaldo(double novoSaldo) {
        if (contaPrincipal != null) {
            contaPrincipal.setSaldo(novoSaldo);
            lblSaldoPrincipal.setText(String.format("%,.2f MT", novoSaldo));
        }
    }
}