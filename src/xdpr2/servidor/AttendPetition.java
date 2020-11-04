package xdpr2.servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import xdpr2.common.Message;

public class AttendPetition extends Thread {
    private Socket connectionSocket;
    private DataBase db;

    public AttendPetition(Socket connectionSocket, DataBase db) {
        this.connectionSocket = connectionSocket;
        this.db = db;
    }

    public void run() {
        try {
            ObjectOutputStream objectOutput = new ObjectOutputStream(connectionSocket.getOutputStream());
            ObjectInputStream objectInput = new ObjectInputStream(connectionSocket.getInputStream());

            Message<Float> avg = new Message();
            List<Message<Integer>> msgList = new LinkedList<>();
            Message<Integer> act;
            String region = "";

            //Rebre tots els msg del client fins que envii el centinella (null)
            do {
                act = (Message) objectInput.readObject();
                if(act != null) {
                    msgList.add(act);
                    db.updateDataOfSanitaryRegion(act);
                    region = act.getSanitaryRegion();
                }
            } while(act != null);

            //Si el client envia algun msg calculo la mitja i escric a fitxer, sino retorno l'objecte amb tot a 0 i nom null
            String fileName = ".\\logs\\"+region+ LocalDateTime.now().toString() + ".txt";
            fileName = fileName.replace(":", ".");
            for(Message<Integer> msg : msgList) {
                msg.writeToFile(fileName);
            }

            if(msgList.size() > 0) {
                avg = db.getAvgLast24h(region);
            }

            //Retornar al client
            objectOutput.writeObject(avg);

            objectInput.close();
            objectOutput.close();
            this.connectionSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("La classe enviada pel socket es desconeguda");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
