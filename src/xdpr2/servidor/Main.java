package xdpr2.servidor;

import java.util.LinkedList;
import java.util.List;
import xdpr2.common.Message;

public class Main {

    public static void main(String[] args) throws Exception {
        List<Message> listMsg = new LinkedList<>();

        listMsg.add(new Message("A", 2, 2, 2, 2 ));
        listMsg.add( new Message("A", 2, 2, 2,2 ));
        listMsg.add( new Message("A", 2, 2, 2,2 ));
        listMsg.add( new Message("A", 7, 2, 2,2 ));

        listMsg.add( new Message("B", 2, 2, 2,2 ));
        listMsg.add( new Message("B", 2, 2, 2,2 ));
        listMsg.add( new Message("B", 2, 2, 2,2 ));

        DataBase db = new DataBase();
        for(Message msg : listMsg) {
            db.updateDataOfSanitaryRegion(msg);
        }

        System.out.println(db.getAvgLast24h("A"));
        System.out.println(db.getAvgLast24h("B"));
        System.out.println(db.getAvgLast24h("C"));

        db.close();
    }
}
