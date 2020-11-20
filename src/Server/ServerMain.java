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
			server.setSoTimeout(Integer.MAX_VALUE);
			System.out.println("Starting Server...");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void waitForReady(Socket client) {
		new Thread(() -> {
			System.out.println("New Client");
			Player p = new Player(server, client);
			long m = System.currentTimeMillis();
			while (!p.ready && !p.disconnected && System.currentTimeMillis() - m < 1000) {
			}
			if (p.ready) {
				players.put(p.uuid, p);
			} else if (!p.disconnected) {
				System.out.println("Waiting canceled");
			}
		}).start();
	}

	public void run() {
		System.out.println("Server started");
		while (true) {
			try {
				Socket client = server.accept();
				waitForReady(client);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		ServerMain serverMain = new ServerMain(1337);
		serverMain.run();
	}

}
