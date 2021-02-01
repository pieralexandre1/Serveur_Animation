//Pier-Alexandre Yale

package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Classe où chaque objet est un thread comprenant un socket. En
 *         s'exécutant, le thread lit le message envoyé par le socket d'échange
 *         du serveur et l'affiche à l'écran. La socket est arrêtée quand le mot
 *         fin est lu à partir du flux d'entrée
 */
public class ClientHandler extends Thread {
	private Socket socketEchange = null;
	private Utilisateur utilisateur[] = new Utilisateur[5];
	private int compteur = 0;
	private String message;
	private boolean NomErroner = false;
	private boolean MotDePasseErroner = false;

	BufferedReader input;// le flux d'entrée de socketEchange
	PrintWriter output;

	/**
	 * Cette méthode construit un thread à partir d'une socket d'échange et d'un
	 * numéro fournis en paramètre. Elle initialise le flux d'entré du socket.
	 * 
	 * @param socket
	 *            : le socket d'échange entre le serveur et le client attaché au
	 *            thread
	 * @param compteur
	 *            : le numéro de la connexion (numéro séquentiel du thread)
	 */
	public ClientHandler(Socket socketEchange, Utilisateur[] utilisateur, String message) {
		this.socketEchange = socketEchange;
		this.utilisateur = utilisateur;
		this.message = message;
		try {
			InputStream is = socketEchange.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			input = new BufferedReader(isr);

			OutputStream os = socketEchange.getOutputStream();
			output = new PrintWriter(os);
		} catch (IOException e) {
			System.out.println("Erreur" + e.getMessage());

		}
	}

	/**
	 * méthode exécutée par le thread elle récupère le message se trouvant dans
	 * le flux d'entré de la socket et l'affiche à la console tant qu'il est
	 * différent de "fin".
	 * 
	 */
	@Override
	public void run() {
		String nom;
		String motdepasse;
		boolean continuer = true;
		try {
			while (continuer && compteur < 3) {
				nom = input.readLine().trim().toLowerCase();
				motdepasse = input.readLine();
				if (VerifierConnexion(nom, motdepasse) && compteur < 3) {
					output.println(message);
					output.println("Déconnexion en cours");
					output.flush();
					socketEchange.close();
					continuer = false;
				} else {
					if (MotDePasseErroner) {
						output.println("Mot de passe incorrect.");
					}
					if (NomErroner) {
						output.println("Usager incorrect.");
					}

					output.flush();
					compteur++;
				}
			}
			if (compteur >= 3) {
				output.println("3 tentative, déconnexion.");
				output.flush();
				socketEchange.close();
			}
		} catch (IOException e) {
			System.out.println("Erreur" + e.getMessage());
		}
		// fermeture du socket
		finally {
			try {
				if (socketEchange != null) {
					socketEchange.close();
				}
			} catch (IOException e) {
				System.out.println("Erreude  de   fermeture de socket" + e.getMessage());
			}
		}
	}

	public boolean VerifierConnexion(String nom, String motdepasse) {
		boolean resultat = false;
		MotDePasseErroner = false;
		NomErroner = false;
		for (int i = 0; i < utilisateur.length && !resultat && !MotDePasseErroner; i++) {
			if (utilisateur[i].getNom().equals(nom)) {
				if (utilisateur[i].getMotdepasse().equals(motdepasse)) {
					resultat = true;
				} else {
					MotDePasseErroner = true;
				}
				NomErroner = false;
			} else {
				NomErroner = true;
			}
		}
		return resultat;
	}

}
