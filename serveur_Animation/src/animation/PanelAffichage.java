//Pier-alexandre Yale

package animation;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class PanelAffichage extends JPanel implements ConstantesAffichages {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Coureur coureurD;
	private Coureur coureurG;
	Image tamponImage = null;// permet de dessiner dans une image en arrière plan

	public PanelAffichage(Coureur coureur1, Coureur coureur2) {
		this.coureurD = coureur1;
		this.coureurG = coureur2;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// créer une image tampon de dimension égale au panneau
		
		tamponImage = createImage(getWidth(), getHeight());
		// obtenir le contexte graphique du tampon
		
		Graphics bg = tamponImage.getGraphics();
		// Dessiner dans l'image tampon en arrière plan
		// dessiner l’image du ciel dans le tampon
		bg.drawImage(IMAGE_SKY.getImage(), (int)g.getClipBounds().getX(), (int)g.getClipBounds().getY(), this);
		// dessiner la String dans le tampon
		
		bg.setFont(new Font("Verdana", Font.BOLD, 18));
		bg.drawString("Cliquez sur le bouton Démarrer", 100, 20);
		// dessiner l’image de l’eau dans le tampon
		
		bg.drawImage(IMAGE_EAU.getImage(), (int)g.getClipBounds().getX(), (int)g.getClipBounds().getY() + 195, this);
		// dessiner l’image dynamique du coureurD
		
		bg.drawImage(coureurD.getImageCoureur().getImage(), coureurD.getPosX(), coureurD.getPosY(), this);
		// dessiner l’image dynamique du coureurG dans le tampon
		
		bg.drawImage(coureurG.getImageCoureur().getImage(), coureurG.getPosX(), coureurG.getPosY(), this);
		// dessiner l'image du tampon dans le panneau en dernier
		g.drawImage(tamponImage, 0, 0, this);
		/*
		 * L'acquisition explicite d'un contexte graphique doit être * accompagnée d'une libération explicite par la méthode * dispose().
		 */
		g.dispose();
	}
	
	public void setCoureurD(Coureur coureurD) {
		this.coureurD = coureurD;
	}
	public void setCoureurG(Coureur coureurG) {
		this.coureurG = coureurG;
	}
}