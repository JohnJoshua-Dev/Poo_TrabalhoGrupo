package com.banco.controllers;

import com.banco.models.Usuario;
import com.banco.services.AuthService;
import com.banco.utils.AlertUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private CheckBox chkLembrar;
    @FXML
    private Button btnLogin;

    private AuthService authService = new AuthService();

    @FXML
    public void initialize() {
        // Configurações iniciais
        btnLogin.setDefaultButton(true);

        // Enter key support
        txtPassword.setOnAction(e -> handleLogin());

        // Carregar credenciais salvas (se existirem)
        carregarCredenciaisSalvas();
    }

    @FXML
    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText();

        // Validação
        if (username.isEmpty() || password.isEmpty()) {
            AlertUtils.mostrarErro("Erro de Validação", "Por favor, preencha todos os campos.");
            return;
        }

        // Mostrar loading
        btnLogin.setText("Autenticando...");
        btnLogin.setDisable(true);

        // Autenticação em thread separada
        new Thread(() -> {
            try {
                // Pequeno delay para simular processamento
                Thread.sleep(1000);

                Usuario usuario = authService.autenticar(username, password);

                // Voltar para a thread da UI
                javafx.application.Platform.runLater(() -> {
                    if (usuario != null) {
                        salvarCredenciaisSeSolicitado(username, password);
                        if (usuario.isAdmin()) {
                            abrirDashboard();
                        } else {
                            abrirHomepage(usuario);
                        }
                    } else {
                        AlertUtils.mostrarErro("Falha no Login", "Username ou password incorretos.");
                        btnLogin.setText("Entrar no Sistema");
                        btnLogin.setDisable(false);
                    }
                });

            } catch (Exception e) {
                javafx.application.Platform.runLater(() -> {
                    AlertUtils.mostrarErro("Erro de Conexão",
                            "Não foi possível conectar ao servidor: " + e.getMessage());
                    btnLogin.setText("Entrar no Sistema");
                    btnLogin.setDisable(false);
                });
            }
        }).start();
    }

    @FXML
    private void handleEsqueciSenha() {
        AlertUtils.mostrarInfo("Recuperar Senha",
                "Contacte o administrador do sistema para recuperar sua senha.\n\nEmail: admin@banco.co.mz\nTelefone: +258 84 123 4567");
    }

    private void carregarCredenciaisSalvas() {
        // Em uma implementação real, isso carregaria de um arquivo de configuração
        // Por enquanto, vamos usar valores padrão para teste
        txtUsername.setText("admin");
        // Não preenchemos a senha por segurança
    }

    private void salvarCredenciaisSeSolicitado(String username, String password) {
        if (chkLembrar.isSelected()) {
            // Em uma implementação real, salvaria em arquivo seguro
            System.out.println("Credenciais salvas para: " + username);
        }
    }

    private void abrirDashboard() {
        try {
            Stage currentStage = (Stage) btnLogin.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/dashboard.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Dashboard - Sistema Bancário");
            stage.setMaximized(true);

            stage.show();
            currentStage.close();

        } catch (IOException e) {
            AlertUtils.mostrarErro("Erro", "Não foi possível abrir o dashboard: " + e.getMessage());
        }
    }

    private void abrirHomepage(Usuario usuario) {
        try {
            Stage currentStage = (Stage) btnLogin.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/homepage.fxml"));
            Parent root = loader.load();

            // Get the controller and pass the user data
            ClienteHomeController controller = loader.getController();
            controller.initialize(usuario);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Home - Sistema Bancário");
            stage.setMaximized(true);

            stage.show();
            currentStage.close();

        } catch (IOException e) {
            AlertUtils.mostrarErro("Erro", "Não foi possível abrir a homepage: " + e.getMessage());
        }
    }
}