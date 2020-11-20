package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.time.OffsetDateTime;
import java.util.UUID;

public class Player extends Thread {
	int x, y;
	Socket client;
	ServerSocket server;
	BufferedReader inputStream;
	PrintStream outputStream;
	UUID uuid;
	boolean ready = false;
	boolean disconnected = false;

	public Player(ServerSocket server, Socket client) {
		this.client = client;
		this.server = server;

		try {
			inputStream = new BufferedReader(new InputStreamReader(client.getInputStream()));
			outputStream = new PrintStream(client.getOutputStream());

			new Thread(() -> {
				try {
					System.out.println("Waiting for client init message");
					String received = inputStream.readLine();
					System.out.println("Message received: " + received);
					if (received.startsWith("Init")) {
						String uuidString = received.split(":")[1];
						uuid = UUID.fromString(uuidString);
						ready = true;
						start();
						System.out.println("Connected: " + uuidString);
					} else {
						disconnected = true;
						System.out.println("Connection failed: " + received);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void send(String str){
		outputStream.println(str);
		outputStream.flush();
	}

	@Override
	public void run() {
		while (true) {
			if (!ready) continue;
			try {
				String received = inputStream.readLine();
				if (received == null) {
					throw new SocketException("Received is null");
				}
				System.out.println("Message received from " + uuid + ": " + received);
				if(received.equals("test")){
					send("jatest");
					continue;
				}

				String[] splitter = received.split(":"); // ["Integer", "x", "421412"]
				if (!splitter[0].equals("Player")) continue;
				String object = splitter[0];
				String datatype = splitter[1];
				String varName = splitter[2];
				String value = splitter[3];
				Class<?> c = Class.forName(getClass().getPackage() + object);
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
			} catch (SocketException socketException) {
				System.out.println("Player disconnected: " + uuid);
				ServerMain.players.remove(uuid);
				break;
			} catch (IOException | IllegalAccessException | NoSuchFieldException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
