package client;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import Util.Codec;
import Util.RSAKeysTools;
import Util.RSAPrivateKey;
import Util.RSAPublicKey;

public class Client {

	private static RSAPublicKey pub;

	private static RSAPrivateKey priv;

	private static RSAPublicKey autorizedKey;

	public void sendMessage(ObjectOutputStream oos, String message) throws IOException {
		List<BigInteger> encodeMessage = Codec.encode(message, pub);
		oos.writeObject(encodeMessage);
	}

	public static void readSomething(ObjectInputStream ois) throws IOException {
		try {
			Object o = ois.readObject();
			if (o instanceof RSAPublicKey) {
				autorizedKey=(RSAPublicKey) o;
			} else if (o instanceof List<?>) {
				// byte[] tmp = Codec.decode((List<BigInteger>) o, priv);
				// String message = Codec.convertAsciiToString(tmp);
				String message = Codec.decodeToString((List<BigInteger>) o, priv);
				System.out.println("Client read:" + message);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// System.out.println("serveur read something");
	}

	public static void main(String[] arg) throws IOException {

		pub = RSAKeysTools.buildPublicKey();

		priv = RSAKeysTools.buildPrivatesKey(pub);

		int portEcouteServeur = 10302;
		Socket socket = null;
		String adresse = null;

		try {
			// Alice
			socket = new Socket(adresse, portEcouteServeur);
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

			// send
			oos.writeObject(pub);

			// read
			readSomething(ois);

			String message = "Bonjour !";
			// send message
			// byte[] array = Codec.convertStringToAscii(message);
			// oos.writeObject(Codec.encode(array, autorizedKey.get(0)));
			oos.writeObject(Codec.encode(message, autorizedKey));
			while (true) {
				readSomething(ois);
			}
		} catch (FileNotFoundException exc) {
			System.out.println("Fichier introuvable");
		} catch (UnknownHostException exc) {
			System.out.println("Destinataire inconnu");
		} catch (IOException exc) {
			System.out.println("Probleme d'entree-sortie");
			exc.printStackTrace();
		} finally {
			socket.close();
		}
	}
}
