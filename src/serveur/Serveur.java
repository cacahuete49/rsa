package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import Util.RSAKeysTools;
import Util.RSAPrivateKey;
import Util.RSAPublicKey;

public class Serveur {

	private static RSAPublicKey pub;

	private static RSAPrivateKey priv;

	static List<RSAPublicKey> autorizedKey = new ArrayList<RSAPublicKey>();

	public static void main(String[] arg) {

		pub = RSAKeysTools.buildPublicKey();

		priv = RSAKeysTools.buildPrivatesKey(pub);

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

				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				try {
					RSAPublicKey pub = (RSAPublicKey) ois.readObject();
					System.out.println("message recu :\n"+pub.getE()+ "\n"+pub.getM()+ "\n"+pub.getN());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				sortie.println("output");
				System.out.println(sortie.toString());
			}
		} catch (IOException exc) {
			System.out.println("probleme de connexion");
		}
	}
}
