package com.client.xdpr2;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket connection;
    private String region, newPositives, deaths, newInputs, newOutputs;
    public Client(Socket c){
        this.connection=c;
    }

    /**
     * Metode que executa el Client
     * @throws IOException
     */
    public void run () throws IOException {


        /**Read of keyboard*/
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        /**Read of socket*/
        DataOutputStream toServer = new DataOutputStream(connection.getOutputStream());
        BufferedReader from_server = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        readData(input, toServer);
        connection.close();
    }

    /**
     * Metode que llegeix les dades del teclat, crea un missatge i li passa al servidor
     * @param input
     * @param toServer
     * @throws IOException
     */
    public void readData(BufferedReader input, DataOutputStream toServer) throws IOException
    {
        System.out.println("Enter sanitary region: ");
        region = input.readLine();
        while (!region .equals("-1")) {
            System.out.println("Enter number of new positives: ");
            newPositives = input.readLine();
            System.out.println("Enter number of new deaths: ");
            deaths = input.readLine();
            System.out.println("Enter number of new ICU: ");
            newInputs = input.readLine();
            System.out.println("Enter number of new realises ICU: ");
            newOutputs = input.readLine();
            Message message = new Message(region,Integer.parseInt(newPositives),Integer.parseInt(deaths),Integer.parseInt(newInputs),Integer.parseInt(newOutputs));
            toServer.writeBytes(message.getSanaitaryRegion() + message.getPositives() + message.getDeaths() + message.getNewICU() +message.getReleasesICU());
            region = input.readLine();
        }
    }
}
