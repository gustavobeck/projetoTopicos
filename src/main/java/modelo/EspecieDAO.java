package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EspecieDAO extends DAO {
    private static EspecieDAO instance;

    private EspecieDAO() {
        getConnection();
        createTeable();
    }

    //Singleton
    public static EspecieDAO getInstance() {
        return (instance == null ? (instance = new EspecieDAO()) : instance);
    }

    //CRUD
    public Especie create(final String nome) {
        try {
            final PreparedStatement stmt;

            stmt = DAO.getConnection()
                    .prepareStatement("INSERT INTO especie (nome) VALUES (?)");
            stmt.setString(1, nome);
            executeUpdate(stmt);
        } catch (final SQLException e) {
            Logger.getLogger(EspecieDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return this.retrieveById(lastId("especie", "id"));
    }

    private Especie buildObject(final ResultSet rs) {
        Especie especie = null;
        try {
            especie = new Especie(rs.getInt("id"), rs.getString("nome"));
        } catch (final SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return especie;
    }

    //Generic Retrieve
    public List<Especie> retrieve(final String query) {
        final List<Especie> especies = new ArrayList<>();
        final ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                especies.add(this.buildObject(rs));
            }
        } catch (final SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return especies;
    }

    //RetrieveAll
    public List retrieveAll() {
        return this.retrieve("SELECT * FROM especie");
    }

    //RetrieveById
    public Especie retrieveById(final Integer id) {
        final List<Especie> especies = this.retrieve("SELECT * FROM especie WHERE id = " + id);
        return (especies.isEmpty() ? null : especies.get(0));
    }

    //RetrieveByName
    public Especie retrieveByName(final String nome) {
        final List<Especie> especies = this.retrieve("SELECT * FROM especie WHERE nome = '" + nome + "'");
        return (especies.isEmpty() ? null : especies.get(0));
    }

    //RetrieveBySimilarName
    public List retrieveBySimilarName(final String nome) {
        return this.retrieve("SELECT * FROM especie WHERE nome LIKE '%" + nome + "%'");
    }

    //Update
    public void update(final Especie especie) {
        try {
            final PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE especie SET nome=? WHERE id=?");
            stmt.setString(1, especie.getNome());
            stmt.setInt(2, especie.getId());
            executeUpdate(stmt);
        } catch (final SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    //Delete
    public void delete(final Especie especie) {
        final PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM especie WHERE id = ?");
            stmt.setInt(1, especie.getId());
            executeUpdate(stmt);
        } catch (final SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}
