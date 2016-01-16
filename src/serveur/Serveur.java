package serveur;

import java.io.IOException;
import java.net.ServerSocket;

public class Serveur {



	public static void main(String[] arg) {

		int portEcoute = 10302;
		ServerSocket server;

		try {
			//bob
			server = new ServerSocket(portEcoute);
			Thread t = new Thread(new ThreadSocket(server));
			t.start();
		} catch (IOException exc) {
			exc.printStackTrace();
		}
	}
}
