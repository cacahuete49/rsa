package client;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import Util.RSAKeysTools;

public class Client {
	public static void main(String[] arg) {
		int portEcouteServeur = 10302;
		BufferedReader lecteurFichier;
		BufferedReader entree;
		PrintStream sortie;
		String ligne;
		Socket socket;
		String adresse = null ;
		
		RSAKeysTools.buildPrivatesKey(RSAKeysTools.buildPublicKey());
		
		try {
			socket = new Socket(adresse, portEcouteServeur);
			entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			sortie = new PrintStream(socket.getOutputStream());
			// send
			sortie.println("coucou");
			
			// answer
			System.out.println(entree.readLine());
			
			
			sortie.close();
			entree.close();
			socket.close();
		} catch (FileNotFoundException exc) {
			System.out.println("Fichier introuvable");
		} catch (UnknownHostException exc) {
			System.out.println("Destinataire inconnu");
		} catch (IOException exc) {
			System.out.println("Probleme d'entree-sortie");
		}
	}
}
