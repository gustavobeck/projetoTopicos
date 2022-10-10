package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnimalDAO extends DAO {
    private static AnimalDAO instance;

    private AnimalDAO() {
        getConnection();
        createTeable();
    }

    //Singleton
    public static AnimalDAO getInstance() {
        return (instance == null ? (instance = new AnimalDAO()) : instance);
    }

    //CRUD
    public Animal create(final String nome, final Integer anoNasc, final String sexo, final Integer idEspecie, final Cliente cliente) {
        try {
            final PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO animal (nome, anoNasc, sexo, id_especie, id_cliente) VALUES (?, ?, ?, ?, ?)");
            stmt.setString(1, nome);
            stmt.setInt(2, anoNasc);
            stmt.setString(3, sexo);
            stmt.setInt(4, idEspecie);
            stmt.setInt(5, cliente.getId());
            executeUpdate(stmt);
        } catch (final SQLException e) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return this.retrieveById(lastId("animal", "id"));
    }

    private Animal buildObject(final ResultSet rs) {
        Animal animal = null;
        try {
            animal = new Animal(rs.getInt("id"), rs.getString("nome"), rs.getInt("anoNasc"), rs.getString("sexo"), rs.getInt("id_especie"), rs.getInt("id_cliente"));
        } catch (final SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return animal;
    }

    //Generic Retrieve
    public List<Animal> retrieve(final String query) {
        final List<Animal> animais = new ArrayList<>();
        final ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                animais.add(this.buildObject(rs));
            }
        } catch (final SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return animais;
    }

    //RetrieveAll
    public List<Animal> retrieveAll() {
        return this.retrieve("SELECT * FROM animal");
    }

    //RetrieveLast
    public List<Animal> retrieveLast() {
        return this.retrieve("SELECT * FROM animal WHERE id = " + lastId("animal", "id"));
    }

    //RetrieveById
    public Animal retrieveById(final Integer id) {
        final List<Animal> animais = this.retrieve("SELECT * FROM animal WHERE id = " + id);
        return (animais.isEmpty() ? null : animais.get(0));
    }

    //RetrieveByIdCliente
    public List retrieveByIdCliente(final Integer id) {
        return this.retrieve("SELECT * FROM animal WHERE id_cliente = " + id);
    }

    //RetrieveBySimilarName
    public List retrieveBySimilarName(final Integer id, final String nome) {
        return this.retrieve("SELECT * FROM animal WHERE id_cliente = " + id + " AND nome LIKE '%" + nome + "%'");
    }

    //Update
    public void update(final Animal animal) {
        try {
            final PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE animal SET nome=?, anoNasc=?, sexo=?, id_especie=?, id_cliente=? WHERE id=?");
            stmt.setString(1, animal.getNome());
            stmt.setInt(2, animal.getAnoNasc());
            stmt.setString(3, animal.getSexo());
            stmt.setInt(4, animal.getIdEspecie());
            stmt.setInt(5, animal.getIdCliente());
            stmt.setInt(6, animal.getId());
            executeUpdate(stmt);
        } catch (final SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    //Delete
    public void delete(final Animal animal) {
        final PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM animal WHERE id = ?");
            stmt.setInt(1, animal.getId());
            executeUpdate(stmt);
        } catch (final SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}
