//Pier-Alexandre Yale

package serveur;

import java.io.*;
import java.net.*;

/**
 * @author Client qui se connecte � un serveur et lui envoie une s�rie de
 *         chaines de caract�res La connexion est arr�t�e quand le mot fin est
 *         tap� au clavier
 *
 */
public class Client {

	private static int compteur = 0;

	public static void main(String[] args) {

		try (// Cr�er la socket client et la connecter au serveur Socket socketClient = new Socket (InetAddress.getByName("192.168.0.100"),100);
				Socket socketClient = new Socket(InetAddress.getLocalHost(), 100);) {
			// cr�er le flux de sortie rattach� au socket client
			OutputStream os = socketClient.getOutputStream();
			PrintWriter sortieVersServeur = new PrintWriter(os);

			// cr�er le flux d'entr�e
			InputStream is = socketClient.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader input = new BufferedReader(isr);

			// Cr�er le flux d'entr�e du clavier
			BufferedReader entreeClavier = new BufferedReader(new InputStreamReader(System.in));
			// Lire les phrases � partir du clavier et les envoyer au serveur
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
