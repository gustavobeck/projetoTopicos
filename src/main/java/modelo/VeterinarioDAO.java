package modelo;

import org.apache.commons.lang3.StringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VeterinarioDAO extends DAO {
    private static VeterinarioDAO instance;

    private VeterinarioDAO() {
        getConnection();
        createTeable();
    }

    //Singleton
    public static VeterinarioDAO getInstance() {
        return (instance == null ? (instance = new VeterinarioDAO()) : instance);
    }

    //CRUD
    public Veterinario create(final String nome, final String email, final String telefone) {
        try {
            final PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO veterinario (nome, email, telefone) VALUES (?, ?, ?)");
            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, telefone);
            executeUpdate(stmt);
        } catch (final SQLException e) {
            Logger.getLogger(VeterinarioDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return this.retrieveById(lastId("veterinario", "id"));
    }

    public boolean isLastEmpty() {
        final Veterinario lastVeterinario = this.retrieveById(lastId("veterinario", "id"));
        if (lastVeterinario != null) {
            return StringUtils.isBlank(lastVeterinario.getNome());
        }
        return false;
    }

    private Veterinario buildObject(final ResultSet rs) {
        Veterinario veterinario = null;
        try {
            veterinario = new Veterinario(rs.getInt("id"), rs.getString("nome"), rs.getString("email"), rs.getString("telefone"));
        } catch (final SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return veterinario;
    }

    //Generic Retrieve
    public List<Veterinario> retrieve(final String query) {
        final List<Veterinario> veterinarios = new ArrayList<>();
        final ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                veterinarios.add(this.buildObject(rs));
            }
        } catch (final SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return veterinarios;
    }

    //RetrieveAll
    public List<Veterinario> retrieveAll() {
        return this.retrieve("SELECT * FROM veterinario");
    }

    //RetrieveLast
    public List<Veterinario> retrieveLast() {
        return this.retrieve("SELECT * FROM veterinario WHERE id = " + lastId("animal", "id"));
    }

    //RetrieveById
    public Veterinario retrieveById(final Integer id) {
        final List<Veterinario> veterinarios = this.retrieve("SELECT * FROM veterinario WHERE id = " + id);
        return (veterinarios.isEmpty() ? null : veterinarios.get(0));
    }

    //RetrieveBySimilarName
    public List<Veterinario> retrieveBySimilarName(final String nome) {
        return this.retrieve("SELECT * FROM veterinario WHERE nome LIKE '%" + nome + "%'");
    }

    //Update
    public void update(final Veterinario veterinario) {
        try {
            final PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE veterinario SET nome=?, email=?, telefone=? WHERE id=?");
            stmt.setString(1, veterinario.getNome());
            stmt.setString(2, veterinario.getEmail());
            stmt.setString(3, veterinario.getTelefone());
            stmt.setInt(4, veterinario.getId());
            executeUpdate(stmt);
        } catch (final SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    //Delete
    public void delete(final Veterinario veterinario) {
        final PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM veterinario WHERE id = ?");
            stmt.setInt(1, veterinario.getId());
            executeUpdate(stmt);
        } catch (final SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}
