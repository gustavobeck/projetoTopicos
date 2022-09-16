package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TratamentoDAO extends DAO {
    private static TratamentoDAO instance;

    private TratamentoDAO() {
        getConnection();
        createTeable();
    }

    //Singleton
    public static TratamentoDAO getInstance() {
        return (instance == null ? (instance = new TratamentoDAO()) : instance);
    }

    //CRUD
    public Tratamento create(final String nome, final LocalDate dataInicio, final LocalDate dataFim, final Integer idAnimal,
                             final boolean terminou) {
        try {
            final PreparedStatement stmt;

            stmt = DAO.getConnection()
                    .prepareStatement("INSERT INTO tratamento (id_animal, nome, dataIni, dataFim, terminado) VALUES (?, ?, ?, ?, ?)");
            stmt.setInt(1, idAnimal);
            stmt.setString(2, nome);
            stmt.setString(3, dateFormat.format(dataInicio));
            stmt.setString(4, dateFormat.format(dataFim));
            stmt.setInt(5, terminou ? 1 : 0);
            executeUpdate(stmt);
        } catch (final SQLException e) {
            Logger.getLogger(TratamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return this.retrieveById(lastId("tratamento", "id"));
    }

    private Tratamento buildObject(final ResultSet rs) {
        Tratamento tratamento = null;
        try {
            tratamento =
                    new Tratamento(rs.getInt("id"), rs.getString("nome"), LocalDate.parse(rs.getString("dataInicio"), dateFormat),
                            LocalDate.parse(rs.getString("dataFim"), dateFormat), rs.getInt("id_animal"),
                            rs.getInt("terminado") == 1);
        } catch (final SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return tratamento;
    }

    //Generic Retrieve
    public List<Tratamento> retrieve(final String query) {
        final List<Tratamento> tratamentos = new ArrayList<>();
        final ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                tratamentos.add(this.buildObject(rs));
            }
        } catch (final SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return tratamentos;
    }

    //RetrieveAll
    public List<Tratamento> retrieveAll() {
        return this.retrieve("SELECT * FROM tratamento");
    }

    //RetrieveLast
    public List<Tratamento> retrieveLast() {
        return this.retrieve("SELECT * FROM tratamento WHERE id = " + lastId("tratamento", "id"));
    }

    //RetrieveById
    public Tratamento retrieveById(final Integer id) {
        final List<Tratamento> tratamentos = this.retrieve("SELECT * FROM tratamento WHERE id = " + id);
        return (tratamentos.isEmpty() ? null : tratamentos.get(0));
    }

    //Update
    public void update(final Tratamento tratamento) {
        try {
            final PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE tratamento SET id_animal=?, nome=?, dataIni=?, dataFim=?, terminado=? WHERE id=?");
            stmt.setInt(1, tratamento.getIdAnimal());
            stmt.setString(2, tratamento.getNome());
            stmt.setString(3, dateFormat.format(tratamento.getDataInicio()));
            stmt.setString(4, dateFormat.format(tratamento.getDataFim()));
            stmt.setInt(5, tratamento.isTerminou() ? 1 : 0);
            stmt.setInt(6, tratamento.getId());
            executeUpdate(stmt);
        } catch (final SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    //Delete
    public void delete(final Tratamento tratamento) {
        final PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM tratamento WHERE id = ?");
            stmt.setInt(1, tratamento.getId());
            executeUpdate(stmt);
        } catch (final SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}