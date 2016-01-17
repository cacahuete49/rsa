package serveur;

import java.io.IOException;
import java.net.ServerSocket;

import Util.RSAKeysTools;
import Util.RSAPrivateKey;
import Util.RSAPublicKey;

public class Serveur {



	public static void main(String[] arg) {

		int portEcoute = 10302;
		ServerSocket server;
		
		RSAPublicKey pub = RSAKeysTools.buildPublicKey();

		RSAPrivateKey priv = RSAKeysTools.buildPrivatesKey(pub);

		try {
			//bob
			server = new ServerSocket(portEcoute);
			Thread t = new Thread(new ThreadSocket(server,pub,priv));
			t.start();
		} catch (IOException exc) {
			exc.printStackTrace();
		}
	}
}
