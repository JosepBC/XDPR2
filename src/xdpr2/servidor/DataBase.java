package xdpr2.servidor;

import java.sql.*;

public class DataBase {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/server?serverTimezone=Europe/Madrid";

    //Database credentials
    private static final String USER = "root";
    private static final String PASS = "root";

    private Connection con = null;
    private Statement st = null;

    private void createTable(String sanitaryRegion) throws SQLException {
        String sqlSt = "CREATE TABLE " + sanitaryRegion + " " +
                        " (positives int, " +
                        " deaths int, " +
                        " newICU int, " +
                        " releasesICU int, " +
                        " insDate timestamp )";

        st.executeUpdate(sqlSt);
    }

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
     * Afegeix un nou msg a la taula adient de la base de dades.
     * @param newMsg Nou msg a afegir a la base de dades
     */
    public void updateDataOfSanitaryRegion(Message newMsg) throws SQLException {
        if(!tableExists(newMsg.getSanaitaryRegion())) createTable(newMsg.getSanaitaryRegion());
        String sqlSt =  "INSERT INTO " + newMsg.getSanaitaryRegion() +
                        " VALUES (" + newMsg.getPositives() + ", " + newMsg.getDeaths() + ", "+ newMsg.getNewICU() + ", " + newMsg.getReleasesICU() + ", CURRENT_TIMESTAMP )";
        st.executeUpdate(sqlSt);
     }

    /**
     * Donada una regió sanitaria, existent a la base de dades, retorna un objecte de la classe Message amb les mitjanes de les dadess d'aquella regió per les últimes 24 hores
     * @param sanitaryRegion Regió sanitaria de la cual volem obtenir la mitjana
     * @return null si la taula no existeix sino, objecte amb les mitjanes de les ultimes 24h
     * @throws SQLException
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
            msg.setSanaitaryRegion(sanitaryRegion);
            msg.setDeaths(Math.round(rs.getFloat("deaths")));
            msg.setPositives(Math.round(rs.getFloat("positives")));
            msg.setNewICU(Math.round(rs.getFloat("newICU")));
            msg.setReleasesICU(Math.round(rs.getFloat("releasesICU")));
        }

        return msg;
    }

    public void close() throws SQLException {
        con.close();
    }

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
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
