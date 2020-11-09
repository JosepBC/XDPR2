package xdpr2.client;

import xdpr2.common.Message;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Classe per que un client pugui interactuar amb el servidor
 */
public class Client {
    /**
     * main amb tota la logica d'un client
     * @param args Arguments d'entrada
     * @throws IOException Si hi ha algun error al obrir el socket o llegit del teclat
     * @throws ClassNotFoundException Si la classe retornada pel Socket no es la esperada
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException{
        Socket connection = new Socket(InetAddress.getLocalHost(), 1234);

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
     * @param toServer Instància per escriure al servidor
     * @throws IOException Si hi ha algun error al ohrir el teclat
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
