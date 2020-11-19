package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ServerMain {
    static Map<UUID, Player> players = new HashMap<>();

    private ServerSocket server;

    public ServerMain(int port) {
        try {
            server = new ServerSocket(port);
            server.setSoTimeout(500000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void waitForReady(Socket client) {
        new Thread(() -> {
            try {
                PrintWriter writer = new PrintWriter(client.getOutputStream());
                writer.write("init");
                writer.flush();
                writer.close();

                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                System.out.println(3);

                int i = 0;
                while (!reader.ready() && i < 1000000000) {
                    i++;
                }

                if (i == 1000000000) System.out.println("too large. abort");
                String init;
                while (!(init = reader.readLine()).startsWith("init"))
                    System.out.println(init);

                UUID uuid = UUID.fromString(init.split(":")[1]);
                System.out.println("Connected to Server: " + uuid);
                players.put(
                        uuid,
                        new Player(uuid, server, client)
                );

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }

    public void run() {
        while (true) {
            try {
                Socket client = server.accept();
                waitForReady(client);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args) {
        ServerMain serverMain = new ServerMain(1337);
        serverMain.run();
    }

}
