package modelo;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DAO {

    public static final String DBURL = "jdbc:sqlite:vet.db";
    protected static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static Connection con;

    public static Connection getConnection() {
        if (con == null) {
            try {
                con = DriverManager.getConnection(DBURL);
                if (con != null) {
                    final DatabaseMetaData metaData = con.getMetaData();
                }
            } catch (final SQLException e) {
                System.err.println("Exception: " + e.getMessage());
            }
        }
        return con;
    }

    protected static ResultSet getResultSet(final String query) {
        final Statement s;
        ResultSet rs = null;
        try {
            s = con.createStatement();
            rs = s.executeQuery(query);
        } catch (final SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return rs;
    }

    protected static int executeUpdate(final PreparedStatement queryStatement) throws SQLException {
        return queryStatement.executeUpdate();
    }

    protected static int lastId(final String tableName, final String primaryKey) {
        final Statement s;
        int lastId = -1;
        try {
            s = con.createStatement();
            final ResultSet rs = s.executeQuery("SELECT MAX(" + primaryKey + ") AS id FROM " + tableName);
            if (rs.next()) {
                lastId = rs.getInt("id");
            }
        } catch (final SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return lastId;
    }

    public static void terminar() {
        try {
            DAO.getConnection().close();
        } catch (final SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    protected static boolean createTeable() {
        try {
            PreparedStatement stmt;

            //Table cliente
            stmt = DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS cliente( \n" +
                    "id INTEGER PRIMARY KEY, \n" +
                    "nome VARCHAR, \n" +
                    "endereco VARCHAR, \n" +
                    "cep VARCHAR, \n" +
                    "email VARCHAR, \n" +
                    "telefone VARCHAR); \n");
            executeUpdate(stmt);

            //Table animal
            stmt = DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS animal( \n" +
                    "id INTEGER PRIMARY KEY, \n" +
                    "nome VARCHAR, \n" +
                    "anoNasc INTEGER, \n" +
                    "sexo VARCHAR, \n" +
                    "id_especie INTEGER, \n" +
                    "id_cliente INTEGER); \n");
            executeUpdate(stmt);

            //Table especie
            stmt = DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS especie( \n" +
                    "id INTEGER PRIMARY KEY, \n" +
                    "nome VARCHAR);, \n");
            executeUpdate(stmt);

            //Table vet
            stmt = DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS vet( \n" +
                    "id INTEGER PRIMARY KEY, \n" +
                    "nome VARCHAR, \n" +
                    "email VARCHAR, \n" +
                    "telefone VARCHAR); \n");
            executeUpdate(stmt);

            //Table tratamento
            stmt = DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS tratamento( \n" +
                    "id INTEGER PRIMARY KEY, \n" +
                    "id_animal INTEGER, \n" +
                    "nome VARCHAR, \n" +
                    "dataIni TEXT, \n" +
                    "dataFim TEXT, \n" +
                    "terminado INTEGER); \n");
            executeUpdate(stmt);

            //Table consulta
            stmt = DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS consulta( \n" +
                    "id INTEGER PRIMARY KEY, \n" +
                    "data DATE, \n" +
                    "horario VARCHAR, \n" +
                    "historico VARCHAR, \n" +
                    "id_animal INTEGER, \n" +
                    "id_vet INTEGER, \n" +
                    "id_tratamento INTEGER, \n" +
                    "terminado INTEGER); \n");
            executeUpdate(stmt);

            //Table exame
            stmt = DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS exame( \n" +
                    "id INTEGER PRIMARY KEY, \n" +
                    "nome VARCHAR, \n" +
                    "id_consulta INTEGER); \n");
            executeUpdate(stmt);

            //Default element for species
            stmt = DAO.getConnection().prepareStatement("INSERT OR IGNORE INTO especie (id, nome) VALUES (1, 'Cachorro');");
            executeUpdate(stmt);
            return true;
        } catch (final SQLException e) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }
}
