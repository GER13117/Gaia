import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;

public class SocketConnection {
    Socket client;
    PrintWriter output;
    BufferedReader input;
    Receiver receiver;
    UUID uuid;


    public SocketConnection(String host, int port){
        try {
            uuid = UUID.randomUUID();
            client = new Socket(host, port);
            output = new PrintWriter(client.getOutputStream());
            input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.println(1);
            receiver = new Receiver(this);
            receiver.start();
            System.out.println(2);




        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void send(String str){
        output.write(str);
        output.flush();
    }


    public static class Receiver extends Thread{
        SocketConnection parent;

        public Receiver(SocketConnection parent) {
            this.parent = parent;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    String received = parent.input.readLine();
                    if (received.equals("init")){
                        parent.send("init:" + parent.uuid.toString());
                        continue;
                    }
                    String[] splitter = received.split(":"); // ["Integer", "x", "421412"]

                    if (!(splitter.length == 4)) continue;
                    String object = splitter[0];
                    String datatype = splitter[1];
                    String varName = splitter[2];
                    String value = splitter[3];
                    Class<?> c = Class.forName(getClass().getPackage()+object);
                    switch (datatype) {
                        case "Integer":
                            c.getField(varName).setInt(this, Integer.parseInt(value));
                            break;
                        case "String":
                            c.getField(varName).set(this, value);
                            break;
                        case "Double":
                            c.getField(varName).setDouble(this, Double.parseDouble(value));
                    }
                } catch (IOException | IllegalAccessException | NoSuchFieldException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
