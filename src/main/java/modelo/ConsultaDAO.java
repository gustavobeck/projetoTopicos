package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultaDAO extends DAO {
    private static ConsultaDAO instance;

    private ConsultaDAO() {
        getConnection();
        createTeable();
    }

    //Singleton
    public static ConsultaDAO getInstance() {
        return (instance == null ? (instance = new ConsultaDAO()) : instance);
    }

    //CRUD
    public Consulta create(final LocalDate dataConsulta, final String horario, final String historico, final Integer idAnimal, final Integer idVeterinario,
                           final Integer idTratamento, final boolean terminou) {
        try {
            final PreparedStatement stmt;

            stmt = DAO.getConnection()
                    .prepareStatement("INSERT INTO consulta (data, horario, historico, id_animal, id_vet, id_tratamento, terminado) VALUES (?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, dateFormat.format(dataConsulta));
            stmt.setString(2, horario);
            stmt.setString(3, historico);
            stmt.setInt(4, idAnimal);
            stmt.setInt(5, idVeterinario);
            stmt.setInt(6, idTratamento);
            stmt.setInt(7, terminou ? 1 : 0);
            executeUpdate(stmt);
        } catch (final SQLException e) {
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return this.retrieveById(lastId("consulta", "id"));
    }

    private Consulta buildObject(final ResultSet rs) {
        Consulta consulta = null;
        try {
            consulta =
                    new Consulta(rs.getInt("id"), LocalDate.parse(rs.getString("data"), dateFormat), rs.getInt("horario"), rs.getString("historico"), rs.getInt("id_animal"),
                            rs.getInt("id_vet"), rs.getInt("id_tratamento"), rs.getInt("terminado") == 1);
        } catch (final SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return consulta;
    }

    //Generic Retrieve
    public List<Object> retrieve(final String query) {
        final List<Object> consultas = new ArrayList<>();
        final ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                consultas.add(this.buildObject(rs));
            }
        } catch (final SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return consultas;
    }

    //RetrieveAll
    public List<Object> retrieveAll() {
        return this.retrieve("SELECT * FROM consulta ORDER BY data, horario");
    }

    //RetrieveLast
    public List<Object> retrieveLast() {
        return this.retrieve("SELECT * FROM consulta WHERE id = " + lastId("consulta", "id"));
    }

    //RetrieveById
    public Consulta retrieveById(final Integer id) {
        final List<Object> consultas = this.retrieve("SELECT * FROM consulta WHERE id = " + id);
        return (Consulta)(consultas.isEmpty() ? null : consultas.get(0));
    }

    //Update
    public void update(final Consulta consulta) {
        try {
            final PreparedStatement stmt;
            stmt = DAO.getConnection()
                    .prepareStatement("UPDATE consulta SET data=?, horario=?, historico=?, id_animal=?, id_vet=?, id_tratamento=?, terminado=? WHERE id=?");
            stmt.setString(1, dateFormat.format(consulta.getDataConsulta()));
            stmt.setInt(2, consulta.getHorario());
            stmt.setString(3, consulta.getHistorico());
            stmt.setInt(4, consulta.getIdAnimal());
            stmt.setInt(5, consulta.getIdVeterinario());
            stmt.setInt(6, consulta.getIdTratamento());
            stmt.setInt(7, consulta.isTerminou() ? 1 : 0);
            stmt.setInt(8, consulta.getId());
            executeUpdate(stmt);
        } catch (final SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    //Delete
    public void delete(final Consulta consulta) {
        final PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM consulta WHERE id = ?");
            stmt.setInt(1, consulta.getId());
            executeUpdate(stmt);
        } catch (final SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}
