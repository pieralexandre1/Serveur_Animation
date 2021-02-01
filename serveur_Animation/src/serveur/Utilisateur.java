//Pier-Alexandre Yale

package serveur;

public class Utilisateur {
	
	private String nom;
	private String motdepasse;
	
	public Utilisateur(String nom,String motdepasse){
		this.nom = nom;
		this.motdepasse = motdepasse;
	}
	
	public String getMotdepasse() {
		return motdepasse;
	}
	public String getNom() {
		return nom;
	}

}
