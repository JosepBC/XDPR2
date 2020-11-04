package xdpr2.client;

import xdpr2.common.Message;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        Socket connection = new Socket(InetAddress.getLocalHost(), 1234);
        /**Read of socket*/
        ObjectOutputStream toServer = new ObjectOutputStream(connection.getOutputStream());
        ObjectInputStream from_server = new ObjectInputStream(connection.getInputStream());
        readData(toServer);
        Message<Float> avg = (Message) from_server.readObject();
        System.out.println("Average received from server:");
        System.out.println(avg);
        connection.close();
        toServer.close();
        from_server.close();
    }

    /**
     * Metode que llegeix les dades del teclat, crea un missatge i li passa al servidor
     * @param toServer
     * @throws IOException
     */
    private static void readData(ObjectOutputStream toServer) throws IOException {
        Scanner keyboard = new Scanner(System.in);
        String region;
        int newPositives, deaths, newInputs, newOutputs;
        System.out.println("Enter sanitary region or -1 to exit: ");
        region = keyboard.nextLine();
        while (!region .equals("-1")) {
            System.out.println("Enter number of new positives: ");
            newPositives = keyboard.nextInt();
            System.out.println("Enter number of new deaths: ");
            deaths = keyboard.nextInt();
            System.out.println("Enter number of new ICU: ");
            newInputs = keyboard.nextInt();
            System.out.println("Enter number of new realises ICU: ");
            newOutputs = keyboard.nextInt();
            Message<Integer> message = new Message(region, newPositives, deaths, newInputs, newOutputs);
            toServer.writeObject(message);
            keyboard.nextLine();
            System.out.println("Enter sanitary region or -1 to exit: ");
            region = keyboard.nextLine();
        }

        keyboard.close();
        //Centinela perque el servido sapigue que ja ha acabat la comunicacio
        toServer.writeObject(null);

    }
}
