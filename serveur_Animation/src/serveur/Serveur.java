//Pier-Alexandre Yale

package serveur;

import java.net.*;
import java.util.Random;
import java.io.*;

/**
 * @author Serveur qui attend la connexion des clients sur le port 100.
 *         Pour chaque connexion avec un client, un thread est créé pour gérer
 *         les échanges de données . De cette façon, un client ne peut pas
 *         monopoliser le serveur.
 *
 */
public class Serveur {

	private static Utilisateur utilisateur[] = new Utilisateur[5];
	private static char lettre[] = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
			'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	private static char nombre[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	public static void main(String[] args) {

		utilisateur[0] = new Utilisateur("lelouch", "lelou");
		utilisateur[1] = new Utilisateur("kururugi", "lelouch");
		utilisateur[2] = new Utilisateur("c2", "pizza");
		utilisateur[3] = new Utilisateur("jeremiah", "orange");
		utilisateur[4] = new Utilisateur("kallen", "seiten");
		
		String message = message();
		try {
			ServerSocket socketServeur = new ServerSocket(100);
			while (true) { // boucle infinie
				Socket socketEchange = socketServeur.accept();// Attend une connexion client

				// Création d'un thread pour géréer les données envoyées par le client en cours de connexion
				ClientHandler processusEchange = new ClientHandler(socketEchange, utilisateur,message);
				processusEchange.start();
			}
		} catch (IOException e) {
			System.out.println("Erreur !" + e.getMessage());
		}
	}
	
	public static String message() {
		String message = "";
		char character = '0';
		Random randomGenerator = new Random();
		for(int i = 0; i < 6; i++){
			if(i == 0 || i == 2 || i == 4){
				character = lettre[randomGenerator.nextInt(lettre.length)];
			}else{
				character = nombre[randomGenerator.nextInt(nombre.length)];
			}
			message += character;
		}
		return message;
	}

}
