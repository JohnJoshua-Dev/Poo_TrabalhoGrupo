package com.banco.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.banco.models.Cliente;
import com.banco.services.ApiService;
import com.banco.utils.AlertUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClienteController implements Initializable {

    @FXML private TableView<Cliente> tabelaClientes;
    @FXML private TableColumn<Cliente, Long> colId;
    @FXML private TableColumn<Cliente, String> colNome;
    @FXML private TableColumn<Cliente, String> colBI;
    @FXML private TableColumn<Cliente, String> colNUIT;
    @FXML private TableColumn<Cliente, String> colTelefone;
    @FXML private TableColumn<Cliente, String> colEmail;
    @FXML private TableColumn<Cliente, Boolean> colStatus;
    
    @FXML private TextField campoBusca;
    @FXML private Button btnNovoCliente;
    @FXML private Button btnEditarCliente;
    @FXML private Button btnEliminarCliente;
    @FXML private Button btnDetalhesCliente;
    
    private ObservableList<Cliente> listaClientes;
    private ApiService apiService = new ApiService();
    // Flag para indicar se um cliente foi salvo/atualizado pelo formulário
    private boolean clienteSalvo = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarTabela();
        carregarClientes();
        configurarBotoes();
    }

    private void configurarTabela() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeCompleto"));
        colBI.setCellValueFactory(new PropertyValueFactory<>("bi"));
        colNUIT.setCellValueFactory(new PropertyValueFactory<>("nuit"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("ativo"));
        
        // Customizar coluna de status
        colStatus.setCellFactory(column -> new TableCell<Cliente, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item ? "Ativo" : "Inativo");
                    if (item) {
                        setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
                    } else {
                        setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
                    }
                }
            }
        });
        
        // Seleção da tabela
        tabelaClientes.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> atualizarBotoes()
        );
    }

    private void carregarClientes() {
        // Dados mock - em produção, buscaria da API
        listaClientes = FXCollections.observableArrayList(
            new Cliente("João António Silva", "123456789LA012", "123456789", "+258 84 123 4567", "joao.silva@email.com", "Av. 25 de Setembro, Maputo"),
            new Cliente("Maria Fernanda Santos", "987654321LA013", "987654321", "+258 85 234 5678", "maria.santos@email.com", "Rua da Sé, Matola"),
            new Cliente("Carlos Alberto Muchanga", "456789123LA014", "456789123", "+258 86 345 6789", "carlos.muchanga@email.com", "Bairro Central, Beira"),
            new Cliente("Ana Paula Tembe", "321654987LA015", "321654987", "+258 87 456 7890", "ana.tembe@email.com", "Zona Verde, Nampula"),
            new Cliente("Pedro Manuel José", "789123456LA016", "789123456", "+258 82 567 8901", "pedro.jose@email.com", "Cidade Alta, Quelimane")
        );
        
        // Adicionar IDs
        for (int i = 0; i < listaClientes.size(); i++) {
            listaClientes.get(i).setId((long) (i + 1));
        }
        
        tabelaClientes.setItems(listaClientes);
        atualizarBotoes();
    }

    private void configurarBotoes() {
        // Busca em tempo real
        campoBusca.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarClientes(newValue);
        });
    }

    private void atualizarBotoes() {
        Cliente selecionado = tabelaClientes.getSelectionModel().getSelectedItem();
        boolean temSelecao = selecionado != null;
        
        btnEditarCliente.setDisable(!temSelecao);
        btnEliminarCliente.setDisable(!temSelecao);
        btnDetalhesCliente.setDisable(!temSelecao);
    }

    @FXML
    private void handleNovoCliente() {
        // Esta função será chamada pelo DashboardController
        System.out.println("Novo cliente solicitado - o DashboardController tratará disso");
    }

    @FXML
    private void handleEditarCliente() {
        Cliente selecionado = tabelaClientes.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            // O DashboardController abrirá o formulário
            System.out.println("Editar cliente: " + selecionado.getNomeCompleto());
        }
    }

    @FXML
    private void handleEliminarCliente() {
        Cliente selecionado = tabelaClientes.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            boolean confirmado = AlertUtils.mostrarConfirmacao(
                "Confirmar Eliminação",
                "Tem certeza que deseja eliminar o cliente:\n" +
                selecionado.getNomeCompleto() + "\n" +
                "BI: " + selecionado.getBi()
            );
            
            if (confirmado) {
                listaClientes.remove(selecionado);
                AlertUtils.mostrarSucesso("Sucesso", "Cliente eliminado com sucesso!");
            }
        }
    }

    @FXML
    private void handleDetalhesCliente() {
        Cliente selecionado = tabelaClientes.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            String detalhes = String.format(
                "Detalhes do Cliente:\n\n" +
                "Nome: %s\n" +
                "BI: %s\n" +
                "NUIT: %s\n" +
                "Telefone: %s\n" +
                "Email: %s\n" +
                "Morada: %s\n" +
                "Status: %s",
                selecionado.getNomeCompleto(),
                selecionado.getBi(),
                selecionado.getNuit(),
                selecionado.getTelefone(),
                selecionado.getEmail(),
                selecionado.getMorada(),
                selecionado.isAtivo() ? "Ativo" : "Inativo"
            );
            
            AlertUtils.mostrarInfo("Detalhes do Cliente", detalhes);
        }
    }

    @FXML
    private void handleLimparBusca() {
        campoBusca.clear();
        tabelaClientes.setItems(listaClientes);
    }

    private void filtrarClientes(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            tabelaClientes.setItems(listaClientes);
            return;
        }
        
        String filtroLower = filtro.toLowerCase();
        ObservableList<Cliente> clientesFiltrados = FXCollections.observableArrayList();
        
        for (Cliente cliente : listaClientes) {
            if (cliente.getNomeCompleto().toLowerCase().contains(filtroLower) ||
                cliente.getBi().toLowerCase().contains(filtroLower) ||
                cliente.getNuit().toLowerCase().contains(filtroLower) ||
                cliente.getTelefone().toLowerCase().contains(filtroLower) ||
                cliente.getEmail().toLowerCase().contains(filtroLower)) {
                clientesFiltrados.add(cliente);
            }
        }
        
        tabelaClientes.setItems(clientesFiltrados);
    }
    
    // Método para o DashboardController acessar a tabela
    public TableView<Cliente> getTabelaClientes() {
        return tabelaClientes;
    }

    /**
     * Marca o cliente selecionado na tabela para edição.
     * Se o cliente estiver presente na lista, seleciona-o na tabela.
     */
    public void setClienteParaEdicao(Cliente cliente) {
        if (cliente == null) return;
        // Tentar selecionar o cliente existente na tabela (comparando por id quando disponível)
        for (Cliente c : listaClientes) {
            if (c.getId() != null && c.getId().equals(cliente.getId())) {
                tabelaClientes.getSelectionModel().select(c);
                return;
            }
        }
        // Se não encontrou, adiciona temporariamente e seleciona
        listaClientes.add(cliente);
        cliente.setId((long) listaClientes.size());
        tabelaClientes.getSelectionModel().select(cliente);
    }

    /**
     * Indica se o formulário de cliente salvou ou não um cliente.
     * O formulário que manipula o Cliente deve chamar setClienteSalvo(true) após persistir os dados.
     */
    public boolean isClienteSalvo() {
        return clienteSalvo;
    }

    public void setClienteSalvo(boolean salvo) {
        this.clienteSalvo = salvo;
    }
}