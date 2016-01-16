package client;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import Util.Codec;
import Util.RSAKeysTools;
import Util.RSAPrivateKey;
import Util.RSAPublicKey;

public class Client {

	private static RSAPublicKey pub;

	private static RSAPrivateKey priv;

	static List<RSAPublicKey> autorizedKey = new ArrayList<RSAPublicKey>();
	
	public static void readSomething(ObjectInputStream ois) {
		try {
			Object o = ois.readObject();
			if (o instanceof RSAPublicKey) {
				autorizedKey.add((RSAPublicKey) o);
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

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
			//Alice
			socket = new Socket(adresse, portEcouteServeur);
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

			// send
			oos.writeObject(pub);
			
			// read
			readSomething(ois);
			
			String message = "Bonjour !";
			//send message
			byte[] array = Codec.convertStringToAscii(message);
			System.out.println("client send:"+ message);
			oos.writeObject(Codec.encode(array, autorizedKey.get(0)));
			
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
