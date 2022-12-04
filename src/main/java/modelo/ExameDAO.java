package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExameDAO extends DAO {
    private static ExameDAO instance;

    private ExameDAO() {
        getConnection();
        createTeable();
    }

    //Singleton
    public static ExameDAO getInstance() {
        return (instance == null ? (instance = new ExameDAO()) : instance);
    }

    //CRUD
    public Exame create(final String nome, final Integer idConsulta) {
        try {
            final PreparedStatement stmt;

            stmt = DAO.getConnection()
                    .prepareStatement("INSERT INTO exame (nome, id_consulta) VALUES (?,?)");
            stmt.setString(1, nome);
            stmt.setInt(2, idConsulta);
            executeUpdate(stmt);
        } catch (final SQLException e) {
            Logger.getLogger(ExameDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return this.retrieveById(lastId("exame", "id"));
    }

    private Exame buildObject(final ResultSet rs) {
        Exame exame = null;
        try {
            exame = new Exame(rs.getInt("id"), rs.getString("nome"), rs.getInt("id_consulta"));
        } catch (final SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return exame;
    }

    //Generic Retrieve
    public List<Exame> retrieve(final String query) {
        final List<Exame> exames = new ArrayList<>();
        final ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                exames.add(this.buildObject(rs));
            }
        } catch (final SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return exames;
    }

    //RetrieveAll
    public List<Exame> retrieveAll() {
        return this.retrieve("SELECT * FROM exame");
    }

    //RetrieveById
    public Exame retrieveById(final Integer id) {
        final List<Exame> exames = this.retrieve("SELECT * FROM exame WHERE id = " + id);
        return (exames.isEmpty() ? null : exames.get(0));
    }
    
    //RetrieveByIdConsulta
    public List retrieveByIdConsulta(final Integer id) {
       return this.retrieve("SELECT * FROM exame WHERE id_consulta = " + id);
    }

    //Update
    public void update(final Exame exame) {
        try {
            final PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE exame SET nome=?, id_consulta=? WHERE id=?");
            stmt.setString(1, exame.getNome());
            stmt.setInt(2, exame.getIdConsulta());
            stmt.setInt(3, exame.getId());
            executeUpdate(stmt);
        } catch (final SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    //Delete
    public void delete(final Exame exame) {
        final PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM exame WHERE id = ?");
            stmt.setInt(1, exame.getId());
            executeUpdate(stmt);
        } catch (final SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}