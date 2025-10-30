package com.banco;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Carregar o FXML do login
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
        Parent root = loader.load();
        
        // Configurar a cena
        Scene scene = new Scene(root, 900, 650);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        
        // Configurar a janela principal
        primaryStage.setTitle("Sistema Bancário - POO");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(650);
        
        // Adicionar ícone
        try {
            Image icon = new Image(getClass().getResourceAsStream("/images/bank-icon.png"));
            primaryStage.getIcons().add(icon);
        } catch (Exception e) {
            System.out.println("Ícone não encontrado, continuando sem ícone...");
        }
        
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}