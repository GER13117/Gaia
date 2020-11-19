package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.UUID;

public class Player extends Thread {
    int x, y;
    Socket client;
    ServerSocket server;
    BufferedReader inputStream;
    PrintWriter outputStream;
    UUID uuid;

    public Player(UUID uuid, ServerSocket server, Socket client) {
        this.client = client;
        this.server = server;
        this.uuid = uuid;

        try {
            inputStream = new BufferedReader(new InputStreamReader(client.getInputStream()));
            outputStream = new PrintWriter(client.getOutputStream());
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    @Override
    public void run() {
        while (true) {
            try {
                String received = inputStream.readLine();
                if (received == null){
                    throw new SocketException("Received is null");
                }
                System.out.println(uuid + " : " + received);
                String[] splitter = received.split(":"); // ["Integer", "x", "421412"]
                if (!splitter[0].equals("Player")) continue;
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
            } catch (SocketException socketException){
                ServerMain.players.remove(uuid);

            } catch (IOException | IllegalAccessException | NoSuchFieldException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
