package xdpr2.servidor;

import java.sql.*;
import xdpr2.common.Message;

/**
 * Classe per fer d'interficie entrre les dades del Servidor, classe Message, i una base de dades MySql
 */
public class DataBase {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/?serverTimezone=Europe/Madrid";

    //Database credentials
    private static final String USER = "root";
    private static final String PASS = "root";

    private Connection con = null;
    private Statement st = null;

    /**
     * Metode per crear una nova taula a la base de dades
     * @param sanitaryRegion Nom de la taula a crear
     * @throws SQLException Si l'execucio de la comanda SQL falla
     */
    private void createTable(String sanitaryRegion) throws SQLException {
        String sqlSt = "CREATE TABLE " + sanitaryRegion + " " +
                        " (positives int, " +
                        " deaths int, " +
                        " newICU int, " +
                        " releasesICU int, " +
                        " insDate timestamp )";

        st.executeUpdate(sqlSt);
    }

    /**
     * Comprobar si una taula amb un nom en particualr existeix a la base de dades
     * @param name Nom de la taula a cercar
     * @return True si existeix, false en cas contrari
     * @throws SQLException Si l'execucio de la comanda SQL falla
     */
    private boolean tableExists(String name) throws SQLException {
        String checkName = "SELECT count(*) " +
                           "AS nElem " +
                           "FROM information_schema.TABLES " +
                           "WHERE (TABLE_SCHEMA = 'server') AND (TABLE_NAME = '" + name + "')";
        ResultSet res = st.executeQuery(checkName);
        int nElem = 0;
        if(res.next()) nElem = res.getInt("nElem");
        return nElem == 1;
    }

    /**
     * Afegeix un nou msg a la taula adient de la base de dades
     * @param newMsg Nou msg a afegir a la base de dades
     * @throws SQLException Si l'execucio de la comanda SQL falla
     */
    public void updateDataOfSanitaryRegion(Message newMsg) throws SQLException {
        if(!tableExists(newMsg.getSanitaryRegion())) createTable(newMsg.getSanitaryRegion());
        String sqlSt =  "INSERT INTO " + newMsg.getSanitaryRegion() +
                        " VALUES (" + newMsg.getPositives() + ", " + newMsg.getDeaths() + ", "+ newMsg.getNewICU() + ", " + newMsg.getReleasesICU() + ", CURRENT_TIMESTAMP )";
        st.executeUpdate(sqlSt);
     }

    /**
     * Donada una regio sanitaria, existent a la base de dades, retorna un objecte de la classe Message amb les mitjanes de les dades d'aquella regio per les ultimes 24 hores
     * @param sanitaryRegion Regio sanitaria de la cual volem obtenir la mitjana
     * @return null si la taula no existeix sino, objecte amb les mitjanes de les ultimes 24h en cas contrari
     * @throws SQLException Si l'execucio de la comanda SQL falla
     */
    public Message getAvgLast24h(String sanitaryRegion) throws  SQLException {
        if(!tableExists(sanitaryRegion)) return null;

        String sqlSt =  "SELECT AVG(positives) AS positives, " +
                            "AVG(deaths) AS deaths, " +
                            "AVG(newICU) AS newICU, " +
                            "AVG(releasesICU) AS releasesICU " +
                        "FROM " + sanitaryRegion +
                        " WHERE TIMESTAMPDIFF(HOUR, insDate , CURRENT_TIMESTAMP) < 24";
        ResultSet rs = st.executeQuery(sqlSt);
        Message msg = new Message();

        if(rs.next()) {
            msg.setSanitaryRegion(sanitaryRegion);
            msg.setDeaths(rs.getFloat("deaths"));
            msg.setPositives(rs.getFloat("positives"));
            msg.setNewICU(rs.getFloat("newICU"));
            msg.setReleasesICU(rs.getFloat("releasesICU"));
        }

        return msg;
    }

    /**
     * Tancar corrrectament la connexio amb la base de dades
     * @throws SQLException Si l'execucio de la comanda SQL falla
     */
    public void close() throws SQLException {
        con.close();
    }

    /**
     * Constructor de la base de dades. Fa la connexió i crea la BD server en cas de que no existeixi
     */
    public DataBase() {
        try {
            //Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Conect to DB
            con = DriverManager.getConnection(DB_URL, USER, PASS);
            //Create DB
            st = con.createStatement();
            String sqlSt = "CREATE DATABASE IF NOT EXISTS server";
            st.executeUpdate(sqlSt);
            //select DB to use
            st.execute("USE server");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
