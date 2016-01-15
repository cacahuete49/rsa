package client;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import Util.RSAKeysTools;
import Util.RSAPrivateKey;
import Util.RSAPublicKey;

public class Client {

	private static RSAPublicKey pub;

	private static RSAPrivateKey priv;

	static List<RSAPublicKey> autorizedKey = new ArrayList<RSAPublicKey>();

	public static void main(String[] arg) {

		pub = RSAKeysTools.buildPublicKey();

		priv = RSAKeysTools.buildPrivatesKey(pub);

		int portEcouteServeur = 10302;
		BufferedReader lecteurFichier;
		BufferedReader entree;
		PrintStream sortie;
		String ligne;
		Socket socket;
		String adresse = null;

		try {
			socket = new Socket(adresse, portEcouteServeur);
			entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			sortie = new PrintStream(socket.getOutputStream());
			// send
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(pub);

			// answer
//			System.out.println(entree.readLine());

			sortie.close();
			entree.close();
			socket.close();
		} catch (FileNotFoundException exc) {
			System.out.println("Fichier introuvable");
		} catch (UnknownHostException exc) {
			System.out.println("Destinataire inconnu");
		} catch (IOException exc) {
			System.out.println("Probleme d'entree-sortie");
			exc.printStackTrace();
		}
	}
}
