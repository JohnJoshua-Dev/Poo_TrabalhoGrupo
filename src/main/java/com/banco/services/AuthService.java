package com.banco.services;

import com.banco.config.ApiConfig;
import com.banco.models.Usuario;
import com.banco.models.Cliente;
import com.banco.models.Conta;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Usuario autenticar(String username, String password) {
        // Para desenvolvimento, vamos simular autenticação
        // Em produção, isso se conectaria ao Spring Boot

        System.out.println("Tentando autenticar: " + username);

        // Simulação - em produção, faria request HTTP
        if ("admin".equals(username) && "admin".equals(password)) {
            System.out.println("Autenticação bem-sucedida como admin!");
            Usuario admin = new Usuario(username, password, "ADMIN");
            admin.setId(1L);
            return admin;
        } else if ("cliente".equals(username) && "cliente".equals(password)) {
            System.out.println("Autenticação bem-sucedida como cliente!");
            Usuario cliente = new Usuario(username, password, "CLIENT");
            cliente.setId(2L);

            // Criando cliente de teste com dados mais completos
            Cliente dadosCliente = new Cliente();
            dadosCliente.setId(1L);
            dadosCliente.setNomeCompleto("João da Silva");
            dadosCliente.setBi("123456789");
            dadosCliente.setNuit("987654321");
            dadosCliente.setEmail("joao.silva@email.com");
            dadosCliente.setTelefone("+258 84 123 4567");
            dadosCliente.setMorada("Av. Julius Nyerere, 1234");
            dadosCliente.setAtivo(true);
            dadosCliente.setDataCriacao(LocalDateTime.now());

            cliente.setCliente(dadosCliente);

            return cliente;
        }

        // Tentativa com HTTP real (descomente quando o backend estiver pronto)
        /*
         * try (CloseableHttpClient client = HttpClients.createDefault()) {
         * HttpPost post = new HttpPost(ApiConfig.getBaseUrl() + "/auth/login");
         * 
         * Map<String, String> credenciais = new HashMap<>();
         * credenciais.put("username", username);
         * credenciais.put("password", password);
         * 
         * String json = objectMapper.writeValueAsString(credenciais);
         * post.setEntity(new StringEntity(json));
         * post.setHeader("Content-type", "application/json");
         * 
         * try (CloseableHttpResponse response = client.execute(post)) {
         * return response.getCode() == 200;
         * }
         * } catch (Exception e) {
         * System.err.println("Erro na autenticação: " + e.getMessage());
         * return false;
         * }
         */

        return null; // Authentication failed
    }
}