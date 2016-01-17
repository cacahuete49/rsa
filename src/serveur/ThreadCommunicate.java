package serveur;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.util.List;

import Util.Codec;
import Util.RSAPrivateKey;
import Util.RSAPublicKey;

public class ThreadCommunicate implements Runnable {

	private ObjectOutputStream oos;

	private ObjectInputStream ois;

	private RSAPublicKey autorizedKey;

	private RSAPrivateKey priv;

	private final ThreadSocket threadSocket;

	public ThreadCommunicate(ObjectOutputStream oos, ObjectInputStream ois, RSAPrivateKey priv,
			ThreadSocket threadSocket) {
		this.ois = ois;
		this.oos = oos;
		this.priv = priv;
		this.threadSocket = threadSocket;
	}

	public void sendMessage(ObjectOutputStream oos, String message) throws IOException {
		// for (ObjectOutputStream oos2 : threadSocket.outputSockets) {
		for (int i = 0; i < threadSocket.outputSockets.size(); i++) {
			ObjectOutputStream oos2 = threadSocket.outputSockets.get(i);
			RSAPublicKey autorizedKey = threadSocket.autorizedKey.get(i);
			List<BigInteger> encodeMessage = Codec.encode(message, autorizedKey);
			oos2.writeObject(encodeMessage);
			// oos.writeObject(encodeMessage);
		}
	}

	public void readSomething(ObjectInputStream ois, ObjectOutputStream oos) {
		try {
			Object o = ois.readObject();
			if (o instanceof RSAPublicKey) {
				autorizedKey = ((RSAPublicKey) o);
				threadSocket.autorizedKey.add(autorizedKey);
			} else if (o instanceof List<?>) {
				String message = Codec.decodeToString((List<BigInteger>) o, priv);
				System.out.println("Serveur read:" + message);
				sendMessage(oos, message);
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			readSomething(ois, oos);
		}
	}

}
