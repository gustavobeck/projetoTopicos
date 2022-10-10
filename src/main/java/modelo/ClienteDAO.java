package modelo;

import org.apache.commons.lang3.StringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteDAO extends DAO {
    private static ClienteDAO instance;

    private ClienteDAO() {
        getConnection();
        createTeable();
    }

    //Singleton
    public static ClienteDAO getInstance() {
        return (instance == null ? (instance = new ClienteDAO()) : instance);
    }

    private Cliente buildObject(final ResultSet rs) {
        Cliente cliente = null;
        try {
            cliente = new Cliente(rs.getInt("id"), rs.getString("nome"), rs.getString("endereco"), rs.getString("telefone"), rs.getString("cep"), rs.getString("email"));
            /*final List<Animal> animais = AnimalDAO.getInstance().retrieveByIdCliente(cliente.getId());
            animais.forEach(cliente::addAnimal);*/
        } catch (final SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return cliente;
    }

    //CRUD
    public Cliente create(final String nome, final String end, final String cep, final String email, final String telefone) {
        try {
            final PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO cliente (nome, endereco, cep, email, telefone) VALUES (?, ?, ?, ?, ?)");
            stmt.setString(1, nome);
            stmt.setString(2, end);
            stmt.setString(3, cep);
            stmt.setString(4, email);
            stmt.setString(5, telefone);
            executeUpdate(stmt);
        } catch (final SQLException e) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return this.retrieveById(lastId("cliente", "id"));
    }

    //Generic Retrieve
    public List<Cliente> retrieve(final String query) {
        final List<Cliente> clientes = new ArrayList<>();
        final ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                clientes.add(this.buildObject(rs));
            }
        } catch (final SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return clientes;
    }

    //RetrieveAll
    public List<Cliente> retrieveAll() {
        return this.retrieve("SELECT * FROM cliente");
    }

    //RetrieveLast
    public List<Cliente> retrieveLast() {
        return this.retrieve("SELECT * FROM cliente WHERE id = " + lastId("cliente", "id"));
    }

    //RetrieveBySimilarName
    public List retrieveBySimilarName(final String nome) {
        return this.retrieve("SELECT * FROM cliente WHERE nome LIKE '%" + nome + "%'");
    }

    public boolean isLastEmpty() {
        final Cliente lastCliente = this.retrieveById(lastId("cliente", "id"));
        if (lastCliente != null) {
            return StringUtils.isBlank(lastCliente.getNome());
        }
        return false;
    }

    //RetrieveById
    public Cliente retrieveById(final Integer id) {
        final List<Cliente> clientes = this.retrieve("SELECT * FROM cliente WHERE id = " + id);
        return (clientes.isEmpty() ? null : clientes.get(0));
    }

    //Update
    public void update(final Cliente cliente) {
        try {
            final PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE cliente SET nome=?, endereco=?, cep=?, email=?, telefone=? WHERE id=?");
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEndereco());
            stmt.setString(3, cliente.getCep());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getTelefone());
            stmt.setInt(6, cliente.getId());
            executeUpdate(stmt);
        } catch (final SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    //Delete
    public void delete(final Cliente cliente) {
        final PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM cliente WHERE id = ?");
            stmt.setInt(1, cliente.getId());
            executeUpdate(stmt);
        } catch (final SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}
