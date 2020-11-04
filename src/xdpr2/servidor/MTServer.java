package xdpr2.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class MTServer {
    public static void main(String[] args) throws SQLException {
        ServerSocket serverSocket = null;
        DataBase db = new DataBase();

        try {
            serverSocket = new ServerSocket(1234);
        } catch (IOException e) {
            System.out.println("Error al fer la connexio");
            e.printStackTrace();
        }

        Socket connectionSocket = null;

        try {
            for (;;) {
                connectionSocket = serverSocket.accept();
                new AttendPetition(connectionSocket, db).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

    }
}
