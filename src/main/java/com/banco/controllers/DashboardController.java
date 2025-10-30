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
import com.banco.services.ApiService;
import com.banco.utils.AlertUtils;

import java.io.IOException;

public class DashboardController {

    @FXML private Label lblUsuario;
    @FXML private Label lblTotalClientes;
    @FXML private Label lblContasAtivas;
    @FXML private Label lblTransacoesHoje;
    @FXML private Label lblSaldoTotal;
    
    @FXML private HBox btnDashboard;
    @FXML private HBox btnClientes;
    @FXML private HBox btnTransacoes;
    @FXML private HBox btnRelatorios;
    @FXML private HBox btnConfig;
    
    @FXML private VBox dashboardContent;
    @FXML private VBox contentArea;
    
    private ApiService apiService = new ApiService();

    @FXML
    public void initialize() {
        lblUsuario.setText("Administrador");
        carregarMetricas();
        setActiveMenu(btnDashboard);
        
        // Adicionar listeners de hover
        configurarEfeitosHover();
    }

    private void carregarMetricas() {
        // Em uma implementação real, buscaria da API
        new Thread(() -> {
            try {
                // Simular carregamento
                Thread.sleep(500);
                
                javafx.application.Platform.runLater(() -> {
                    lblTotalClientes.setText("47");
                    lblContasAtivas.setText("62");
                    lblTransacoesHoje.setText("28");
                    lblSaldoTotal.setText("1.250.000,00 MT");
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void configurarEfeitosHover() {
        // Configurar efeitos hover para os botões de ação rápida
        HBox[] acoesRapidas = {btnDashboard, btnClientes, btnTransacoes, btnRelatorios, btnConfig};
        
        for (HBox botao : acoesRapidas) {
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
        mostrarConteudoDashboard();
    }

    @FXML
    private void handleClientes() {
        setActiveMenu(btnClientes);
        abrirFormularioCliente(null);
    }

    @FXML
    private void handleTransacoes() {
        setActiveMenu(btnTransacoes);
        AlertUtils.mostrarInfo("Transações", "Módulo de transações em desenvolvimento!");
    }

    @FXML
    private void handleRelatorios() {
        setActiveMenu(btnRelatorios);
        AlertUtils.mostrarInfo("Relatórios", "Módulo de relatórios em desenvolvimento!");
    }

    @FXML
    private void handleConfig() {
        setActiveMenu(btnConfig);
        AlertUtils.mostrarInfo("Configurações", "Módulo de configurações em desenvolvimento!");
    }

    @FXML
    private void handleNovoCliente() {
        abrirFormularioCliente(new Cliente());
    }

    @FXML
    private void handleNovaConta() {
        AlertUtils.mostrarInfo("Nova Conta", "Funcionalidade de abertura de conta em desenvolvimento!");
    }

    @FXML
    private void handleDeposito() {
        AlertUtils.mostrarInfo("Depósito", "Funcionalidade de depósito em desenvolvimento!");
    }

    @FXML
    private void handleRelatorio() {
        AlertUtils.mostrarInfo("Relatório", "Geração de relatório em desenvolvimento!");
    }

    @FXML
    private void handleLogout() {
        boolean confirmado = AlertUtils.mostrarConfirmacao(
            "Confirmar Logout", 
            "Tem certeza que deseja sair do sistema?"
        );
        
        if (confirmado) {
            try {
                Stage currentStage = (Stage) lblUsuario.getScene().getWindow();
                
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
        // Resetar todos os menus
        HBox[] menus = {btnDashboard, btnClientes, btnTransacoes, btnRelatorios, btnConfig};
        
        for (HBox menu : menus) {
            menu.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-padding: 15 25;");
            if (menu.getChildren().get(1) instanceof Label) {
                ((Label) menu.getChildren().get(1)).setStyle("-fx-text-fill: #ecf0f1; -fx-padding: 0 0 0 15;");
            }
        }
        
        // Definir menu ativo
        activeMenu.setStyle("-fx-background-color: #3498db; -fx-cursor: hand; -fx-padding: 15 25;");
        if (activeMenu.getChildren().get(1) instanceof Label) {
            ((Label) activeMenu.getChildren().get(1)).setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 0 0 0 15;");
        }
    }

    private void mostrarConteudoDashboard() {
        try {
            // Limpar content area
            contentArea.getChildren().clear();
            
            // Carregar conteúdo do dashboard
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/dashboard-content.fxml"));
            Parent dashboardContent = loader.load();
            
            contentArea.getChildren().add(dashboardContent);
            
        } catch (IOException e) {
            AlertUtils.mostrarErro("Erro", "Não foi possível carregar o dashboard: " + e.getMessage());
        }
    }

    private void abrirGestaoClientes() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/clientes.fxml"));
            Parent clientesView = loader.load();
            
            // Limpar content area e adicionar a view de clientes
            contentArea.getChildren().clear();
            contentArea.getChildren().add(clientesView);
            
        } catch (IOException e) {
            AlertUtils.mostrarErro("Erro", "Não foi possível carregar a gestão de clientes: " + e.getMessage());
        }
    }

    private void abrirFormularioCliente(Cliente cliente) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/cliente-form.fxml"));
            Parent formulario = loader.load();
            
            ClienteController controller = loader.getController();
            if (cliente != null) {
                controller.setClienteParaEdicao(cliente);
            }
            
            Stage stage = new Stage();
            stage.setScene(new Scene(formulario, 600, 500));
            stage.setTitle(cliente == null ? "Novo Cliente" : "Editar Cliente");
            stage.initModality(javafx.stage.Modality.WINDOW_MODAL);
            stage.initOwner(lblUsuario.getScene().getWindow());
            stage.showAndWait();
            
            // Recarregar a lista de clientes se necessário
            if (controller.isClienteSalvo()) {
                abrirGestaoClientes(); // Recarregar a view
                carregarMetricas(); // Atualizar métricas
            }
            
        } catch (IOException e) {
            AlertUtils.mostrarErro("Erro", "Não foi possível abrir o formulário: " + e.getMessage());
        }
    }
    
    // Método para atualizar métricas a partir de dados externos
    public void atualizarMetricas(int totalClientes, int contasAtivas, int transacoesHoje, double saldoTotal) {
        javafx.application.Platform.runLater(() -> {
            lblTotalClientes.setText(String.valueOf(totalClientes));
            lblContasAtivas.setText(String.valueOf(contasAtivas));
            lblTransacoesHoje.setText(String.valueOf(transacoesHoje));
            lblSaldoTotal.setText(String.format("%,.2f MT", saldoTotal));
        });
    }
    
    // Método para atualizar informações do usuário
    public void setUsuario(String nome, String email) {
        lblUsuario.setText(nome);
        // Se tiver label de email, atualizar também
    }
}