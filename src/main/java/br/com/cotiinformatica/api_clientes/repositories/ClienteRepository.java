package br.com.cotiinformatica.api_clientes.repositories;

import br.com.cotiinformatica.api_clientes.entities.Cliente;
import br.com.cotiinformatica.api_clientes.enums.StatusCliente;
import br.com.cotiinformatica.api_clientes.enums.TipoCliente;
import br.com.cotiinformatica.api_clientes.factories.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ClienteRepository {

    @Autowired
    private ConnectionFactory connectionFactory;

    /*
        Método para inserir um cliente no banco de dados
     */
    public void create(Cliente cliente) throws Exception {

        try (var connection = connectionFactory.getConnection()) {

            var statement = connection.prepareStatement("""
                    INSERT INTO clientes(nome, email, telefone, tipo, status)
                    VALUES(?,?,?,?,?)
                """);

            statement.setString(1, cliente.getNome());
            statement.setString(2, cliente.getEmail());
            statement.setString(3, cliente.getTelefone());
            statement.setString(4, cliente.getTipo().toString());
            statement.setString(5, cliente.getStatus().toString());
            statement.execute();
        }
    }

    /*
        Método para retornar uma lista com todos os clientes
        que estão cadastrados e ATIVOS no banco de dados
     */
    public List<Cliente> findAll() throws Exception {

        try (var connection = connectionFactory.getConnection()) {

            var statement = connection.prepareStatement("""
                SELECT * FROM CLIENTES WHERE STATUS = 'ATIVO' ORDER BY NOME
            """);
            var result = statement.executeQuery();

            var lista = new ArrayList<Cliente>();
            while(result.next()) {

                var cliente = new Cliente();

                cliente.setId(result.getInt("id"));
                cliente.setNome(result.getString("nome"));
                cliente.setEmail(result.getString("email"));
                cliente.setTelefone(result.getString("telefone"));
                cliente.setStatus(StatusCliente.valueOf(result.getString("status")));
                cliente.setTipo(TipoCliente.valueOf(result.getString("tipo")));

                lista.add(cliente); //adicionando o cliente na lista
            }

            return lista;
        }
    }

    /*
        Método para atualizar os dados do cliente no banco de dados
     */
    public boolean update(Cliente cliente) throws Exception {

        try (var connection = connectionFactory.getConnection()) {

            var statement = connection.prepareStatement("""
                UPDATE CLIENTES 
                SET NOME=?, EMAIL=?, TELEFONE=?, TIPO=?
                WHERE ID=?
            """);
            statement.setString(1, cliente.getNome());
            statement.setString(2, cliente.getEmail());
            statement.setString(3, cliente.getTelefone());
            statement.setString(4, cliente.getTipo().toString());
            statement.setInt(5, cliente.getId());
            var rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;
        }
    }

    /*
        Método para fazer a exclusão lógica de um cliente
        no banco de dados -> Status = INATIVO
     */
    public boolean delete(Integer id) throws Exception {

        try (var connection = connectionFactory.getConnection()) {

            var statement = connection.prepareStatement("""
                UPDATE CLIENTES SET STATUS = 'INATIVO' WHERE ID=?
            """);
            statement.setInt(1, id);
            var rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;

        }
    }

    public Cliente findBy(Integer id) throws Exception {

        try(var connection = connectionFactory.getConnection()) {

            var statement = connection.prepareStatement("SELECT * FROM CLIENTES WHERE ID=? AND STATUS = 'ATIVO'");
            statement.setInt(1, id);
            var result = statement.executeQuery();

            Cliente cliente = null;

            if(result.next()) {
                cliente = new Cliente();

                cliente.setId(result.getInt("id"));
                cliente.setNome(result.getString("nome"));
                cliente.setEmail(result.getString("email"));
                cliente.setTelefone(result.getString("telefone"));
                cliente.setStatus(StatusCliente.valueOf(result.getString("status")));
                cliente.setTipo(TipoCliente.valueOf(result.getString("tipo")));

            }

            return cliente;
        }
    }

}
