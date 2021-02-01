//Pier-alexandre Yale

package animation;


import javax.swing.ImageIcon;

public class Coureur extends Thread implements ConstantesAffichages {
	private int vitesse = 25; // simule le sleep
	private int posX; // position X du coureur
	private int posY; // position Y du coureur
	private ImageIcon imageCoureur;// L'image du coureur
	public boolean fini = false;// fini =true quand posX= extrémité fenêtre
	private boolean attente = false;// attente =true quand le bouton arrêter est cliqué
	private int largeur;// largeur de l'image
	private int hauteur;// hauteur de l'image
	private char orientation;// D pour droite, G pour gauche

	public Coureur(char orientation, ImageIcon imageCoureur) {
		// Calculer la largeur et la hauteur du coureur en fonction de la taille de l’image : getIconWidth() et …
		this.largeur = imageCoureur.getIconWidth();
		this.hauteur = imageCoureur.getIconHeight();
		this.orientation = orientation;
		this.imageCoureur = imageCoureur;

		// initialiser la position de départ du coureur
		if (orientation == 'D') {
			posX = 0 - largeur;
			posY = 70;
		} else {
			vitesse -= 5;
			posX = FENETRE_LARGEUR + largeur;
			posY = 30;
		}
	}

	@Override
	public void run() {
		while (!fini) {
			if (!attente) {
				avancer();
				if (orientation == 'D') {
					if (posX >= 500) {
						fini = true;
					}
				} else {
					if (posX <= -200) {
						fini = true;
					}
				}
			}else{
				try {
					sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void avancer() {
		try {
			sleep(vitesse);
			if (orientation == 'D') {
				posX += 10;
			} else {
				posX -= 10;
			}
		} catch (Exception err) {
		}
	}

	public ImageIcon getImageCoureur() {
		return imageCoureur;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setAttente(boolean attente) {
		this.attente = attente;
	}

	public void setFini(boolean fini) {
		this.fini = fini;
	}
	public boolean isFini() {
		return fini;
	}
	public boolean isAttente() {
		return attente;
	}
	public int getHauteur() {
		return hauteur;
	}
	public int getLargeur() {
		return largeur;
	}

	// pour simuler la vitesse de la course si orientation = ‘D’ avancer à droite sinon avancer à gauche
}
