package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur {
	
	
	public static void main(String[] arg) {
		int portEcoute = 10302;
		ServerSocket server;
		Socket socket;

		try {
			server = new ServerSocket(portEcoute);
			while (true) {
				socket = server.accept();
				System.out.println("connect");
				
				BufferedReader entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintStream sortie = new PrintStream(socket.getOutputStream());
				System.out.println(entree.readLine());
				sortie.println("output");
				System.out.println(sortie.toString());
			}
		} catch (IOException exc) {
			System.out.println("probleme de connexion");
		}
	}
}
