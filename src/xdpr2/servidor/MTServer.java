package xdpr2.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MTServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(1234);
        } catch (IOException e) {
            System.out.println("Error al fer la connexio");
            e.printStackTrace();
        }

        Socket connectionSocket = null;

        for (;;) {
            try {
                connectionSocket = serverSocket.accept();
                new AttendPetition(connectionSocket).start();
            } catch (IOException e) {
                System.out.println("Error al aceptar la peticio");
                e.printStackTrace();
            }
        }
    }
}
