package xdpr2.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

/**
 * Classe per gestionar el servidor multithread
 */
public class MTServer {
    /**
     * Crea un servidor multithread per atendre les peticions dels clients
     * @param args Arguments d'entrada
     * @throws SQLException Si hi ha algun error al tancar la base de dades
     */
    public static void main(String[] args) throws SQLException {
        ServerSocket serverSocket = null;
        DataBase db = new DataBase();

        try {
            serverSocket = new ServerSocket(1234);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Socket connectionSocket = null;

        try {
            for (;;) {
                System.out.println("Esperant connexio....");
                connectionSocket = serverSocket.accept();
                System.out.println("Connexio establerta amb un nou client");
                new AttendPetition(connectionSocket, db).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Tancant connexio amb la base de dades");
            db.close();
        }

    }
}
