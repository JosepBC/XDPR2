package com.client.xdpr2;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket connection;
    public Client(Socket c){
        this.connection=c;
    }
    public void run () throws IOException {
        String region;
        String new_positives, dead;
        String new_inputs, new_outputs;

        /**Read of keyboard*/
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        /**Read of socket*/
        DataOutput to_server = new DataOutputStream(connection.getOutputStream());
        BufferedReader from_server = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        region = input.readLine();
        while (!region .equals("-1")) {
            new_positives = input.readLine();
            dead = input.readLine();
            new_inputs = input.readLine();
            new_outputs = input.readLine();
            Message message = new Message(region,Integer.parseInt(new_positives),Integer.parseInt(dead),Integer.parseInt(new_inputs),Integer.parseInt(new_outputs));
            to_server.writeBytes(message.getSanaitaryRegion() + message.getPositives() + message.getDeaths() + message.getNewICU() +message.getReleasesICU()+"\0");
            region = input.readLine();
        }
        connection.close();
    }
}
