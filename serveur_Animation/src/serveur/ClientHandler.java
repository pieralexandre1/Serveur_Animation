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
 * @author Classe o� chaque objet est un thread comprenant un socket. En
 *         s'ex�cutant, le thread lit le message envoy� par le socket d'�change
 *         du serveur et l'affiche � l'�cran. La socket est arr�t�e quand le mot
 *         fin est lu � partir du flux d'entr�e
 */
public class ClientHandler extends Thread {
	private Socket socketEchange = null;
	private Utilisateur utilisateur[] = new Utilisateur[5];
	private int compteur = 0;
	private String message;
	private boolean NomErroner = false;
	private boolean MotDePasseErroner = false;

	BufferedReader input;// le flux d'entr�e de socketEchange
	PrintWriter output;

	/**
	 * Cette m�thode construit un thread � partir d'une socket d'�change et d'un
	 * num�ro fournis en param�tre. Elle initialise le flux d'entr� du socket.
	 * 
	 * @param socket
	 *            : le socket d'�change entre le serveur et le client attach� au
	 *            thread
	 * @param compteur
	 *            : le num�ro de la connexion (num�ro s�quentiel du thread)
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
	 * m�thode ex�cut�e par le thread elle r�cup�re le message se trouvant dans
	 * le flux d'entr� de la socket et l'affiche � la console tant qu'il est
	 * diff�rent de "fin".
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
					output.println("D�connexion en cours");
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
				output.println("3 tentative, d�connexion.");
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
