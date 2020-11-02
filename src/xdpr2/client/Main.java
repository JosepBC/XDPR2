package xdpr2.client;

import java.io.IOException;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException {
        Socket connection = new Socket();
        Client c = new Client(connection);
        c.run();

    }
}
