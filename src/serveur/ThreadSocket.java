package serveur;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import Util.Codec;
import Util.RSAPrivateKey;
import Util.RSAPublicKey;

public class ThreadSocket implements Runnable {

	private ServerSocket serverSocket;

	private RSAPublicKey pub;

	private RSAPrivateKey priv;

	List<RSAPublicKey> autorizedKey = new ArrayList<RSAPublicKey>();
	
	List<ObjectOutputStream> outputSockets = new ArrayList<ObjectOutputStream>();
	
	public void sendMessage(ObjectOutputStream oos, String message) throws IOException {
		for (RSAPublicKey publicKey : autorizedKey) {
			System.err.println(oos);
			List<BigInteger> encodeMessage = Codec.encode(message, publicKey);
			oos.writeObject(encodeMessage);
		}
	}
	
	public void readSomething(ObjectInputStream ois, ObjectOutputStream oos) {
		try {
			Object o = ois.readObject();
			if (o instanceof RSAPublicKey) {
				autorizedKey.add((RSAPublicKey) o);
			} else if (o instanceof List<?>) {
				String message = Codec.decodeToString((List<BigInteger>) o, priv);
				System.out.println("Serveur read:" + message);
				sendMessage(oos, message);
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		// System.out.println("serveur read something");
	}

	public ThreadSocket(ServerSocket s, RSAPublicKey pub, RSAPrivateKey priv) {
		this.serverSocket = s;
		this.pub = pub;
		this.priv = priv;
	}

	@Override
	public void run() {

		try {
			while (!serverSocket.isClosed()) {
				Socket socket = serverSocket.accept();
				

				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

				outputSockets.add(oos);
				
				// read key pub
//				readSomething(ois,oos);

				// send key pub
				oos.writeObject(pub);

				// read and send all
//				new Thread() {
//					public void run() {
//						while (true) {
//							readSomething(ois,oos);
//						}
//					}
//				}.start();
				new Thread(new ThreadCommunicate(oos,ois,priv,this)).start();
				
//				// read
//				readSomething(ois);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
