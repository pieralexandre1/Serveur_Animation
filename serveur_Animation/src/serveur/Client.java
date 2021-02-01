//Pier-Alexandre Yale

package serveur;

import java.io.*;
import java.net.*;

/**
 * @author Client qui se connecte à un serveur et lui envoie une série de
 *         chaines de caractères La connexion est arrêtée quand le mot fin est
 *         tapé au clavier
 *
 */
public class Client {

	private static int compteur = 0;

	public static void main(String[] args) {

		try (// Créer la socket client et la connecter au serveur Socket socketClient = new Socket (InetAddress.getByName("192.168.0.100"),100);
				Socket socketClient = new Socket(InetAddress.getLocalHost(), 100);) {
			// créer le flux de sortie rattaché au socket client
			OutputStream os = socketClient.getOutputStream();
			PrintWriter sortieVersServeur = new PrintWriter(os);

			// créer le flux d'entrée
			InputStream is = socketClient.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader input = new BufferedReader(isr);

			// Créer le flux d'entrée du clavier
			BufferedReader entreeClavier = new BufferedReader(new InputStreamReader(System.in));
			// Lire les phrases à partir du clavier et les envoyer au serveur
			boolean continuer = true;
			while (continuer && compteur < 3) {
				System.out.println();
				System.out.print("Client:>> Entrer nom d'usager : ");
				String nom = entreeClavier.readLine();
				System.out.println();
				System.out.print("Client:>> Entrer mot de passe : ");
				String motdepasse = entreeClavier.readLine();
				System.out.println();

				sortieVersServeur.println(nom);
				sortieVersServeur.println(motdepasse);
				sortieVersServeur.flush();

				String phraseClient = input.readLine();
				System.out.println("serveur:>> " + phraseClient);

				if (!phraseClient.equals("Mot de passe incorrect.") && !phraseClient.equals("Usager incorrect.")) {
					phraseClient = input.readLine();
					System.out.println("\nserveur:>> " + phraseClient);
					continuer = false;
				}
				compteur++;

				if (compteur >= 3) {
					phraseClient = input.readLine();
					if (phraseClient != null) {
						System.out.println("\nserveur:>> " + phraseClient);
					}
					continuer = false;
				}
			}

		} catch (IOException e) {
			System.out.println("Erreur Client ! " + e.getMessage());
		}
	}
}
