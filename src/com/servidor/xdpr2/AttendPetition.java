package com.servidor.xdpr2;

import javax.imageio.IIOException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

public class AttendPetition extends Thread {
    Socket connectionSocket;

    public AttendPetition(Socket connectionSocket) {
        this.connectionSocket = connectionSocket;
    }

    private  Message average(List<Message> in) {
        Message avg = new Message();
        avg.setSanaitaryRegion(in.get(0).getSanaitaryRegion());
        for(Message it : in) {
            avg.setDeaths(avg.getDeaths() + it.getDeaths());
            avg.setNewICU(avg.getNewICU() + it.getNewICU());
            avg.setPositives(avg.getPositives() + it.getPositives());
            avg.setReleasesICU(avg.getReleasesICU() + it.getReleasesICU());
        }

        avg.setDeaths(avg.getDeaths() / in.size());
        avg.setNewICU(avg.getNewICU() / in.size());
        avg.setPositives(avg.getPositives() / in.size());
        avg.setReleasesICU(avg.getReleasesICU() / in.size());

        return avg;
    }

    public void run() {
        Message received;
        List<Message> msg = new LinkedList<>();

        try {
            ObjectInputStream objectInput = new ObjectInputStream(connectionSocket.getInputStream());

            //Rebre tots els msg del client fins que envii el centinella (null)
            do {
                received = (Message) objectInput.readObject();
                if(received != null) msg.add(received);
            } while(received != null);

            Message avg = new Message();

            //Si el client envia algun msg calculo la mitja i escric a fitxer, sino retorno l'objecte amb tot a 0
            if(msg.size() > 0) {
                avg = average(msg);
                String fileName = avg.getSanaitaryRegion() + "_" + LocalDateTime.now().toString() + ".txt";
                fileName.replace(":", ".");
                avg.writeToFile(fileName);
            }

            //Retornaar al client
            ObjectOutputStream objectOutput = new ObjectOutputStream(connectionSocket.getOutputStream());
            objectOutput.writeObject(avg);

            this.connectionSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("La classe enviada pel socket es desconeguda");
            e.printStackTrace();
        }
    }

}
