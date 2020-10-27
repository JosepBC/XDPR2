package xdpr2.servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;

public class AttendPetition extends Thread {
    Socket connectionSocket;

    public AttendPetition(Socket connectionSocket) {
        this.connectionSocket = connectionSocket;
    }

    public void run() {
        try {
            ObjectInputStream objectInput = new ObjectInputStream(connectionSocket.getInputStream());

            Message avg = new Message();
            Message act;
            int nMessages = 0;

            //Rebre tots els msg del client fins que envii el centinella (null)
            do {
                act = (Message) objectInput.readObject();
                if(act != null) {
                    avg.addAndSetAll(act);
                    nMessages++;
                }
            } while(act != null);

            //Si el client envia algun msg calculo la mitja i escric a fitxer, sino retorno l'objecte amb tot a 0 i nom null
            if(nMessages > 0) {
                avg.average(nMessages);
                String fileName = avg.getSanaitaryRegion() + "_" + LocalDateTime.now().toString() + ".txt";
                fileName.replace(":", ".");
                avg.writeToFile(fileName);
            }

            //Retornar al client
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
