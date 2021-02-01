//Pier-alexandre Yale

package animation;

import java.awt.Button;
import java.awt.event.ActionEvent;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionListener;

public final class FenetreCourse extends JFrame implements Runnable {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	// déclarez ici les boutons
	private Button boutonDemarrer = new Button("Démarrer");
	private Button boutonArreter = new Button("Arrêter");
	private Button boutonContinuer = new Button("Continuer");
	// déclarez ici les images des coureurs
	private ImageIcon imageCoureurD = new ImageIcon(FenetreCourse.class.getResource("/images/coureur.gif"));
	private ImageIcon imageCoureurG = new ImageIcon(FenetreCourse.class.getResource("/images/cycliste.gif"));
	// déclarer et initialiser deux coureurs
	private Coureur coureur1 = new Coureur('D', imageCoureurD);
	private Coureur coureur2 = new Coureur('G', imageCoureurG);
	// déclares ici le panneau du milieu (objet PanelAffichage)
	private PanelAffichage panneauMilieu;
	private final JPanel panel = new JPanel();
	private boolean music = false;

	;

	/**
	 * Constructeur la fenêtre
	 */
	public FenetreCourse() {
		setSize(490, 350);
		setTitle("Course");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 484, 0 };
		gridBagLayout.rowHeights = new int[] { 273, 39, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);
		panneauMilieu = new PanelAffichage(coureur1, coureur2);

		GridBagConstraints gbc_panneauMilieu = new GridBagConstraints();
		gbc_panneauMilieu.fill = GridBagConstraints.BOTH;
		gbc_panneauMilieu.insets = new Insets(0, 0, 5, 0);
		gbc_panneauMilieu.gridx = 0;
		gbc_panneauMilieu.gridy = 0;
		getContentPane().add(panneauMilieu, gbc_panneauMilieu);
		panneauMilieu.setLayout(null);

		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 153, 59, 60, 49, 0 };
		gbl_panel.rowHeights = new int[] { 26, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);
		GridBagConstraints gbc_boutonDemarrer = new GridBagConstraints();
		gbc_boutonDemarrer.anchor = GridBagConstraints.NORTHWEST;
		gbc_boutonDemarrer.insets = new Insets(0, 0, 0, 5);
		gbc_boutonDemarrer.gridx = 1;
		gbc_boutonDemarrer.gridy = 0;
		boutonDemarrer.addActionListener(new BoutonDemarrerActionListener());
		panel.add(boutonDemarrer, gbc_boutonDemarrer);
		GridBagConstraints gbc_boutonContinuer = new GridBagConstraints();
		gbc_boutonContinuer.anchor = GridBagConstraints.NORTHWEST;
		gbc_boutonContinuer.insets = new Insets(0, 0, 0, 5);
		gbc_boutonContinuer.gridx = 2;
		gbc_boutonContinuer.gridy = 0;
		boutonContinuer.addActionListener(new BoutonContinuerActionListener());
		panel.add(boutonContinuer, gbc_boutonContinuer);
		GridBagConstraints gbc_boutonArreter = new GridBagConstraints();
		gbc_boutonArreter.anchor = GridBagConstraints.NORTHWEST;
		gbc_boutonArreter.gridx = 3;
		gbc_boutonArreter.gridy = 0;
		boutonArreter.addActionListener(new BoutonArreterActionListener());
		panel.add(boutonArreter, gbc_boutonArreter);
		
		boutonContinuer.setEnabled(false);
		boutonArreter.setEnabled(false);
		
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	/**
	 * méthode qui crée et démarre un thread qui a pour objectif de rafraichir
	 * le panneau central
	 */
	@Override
	public void run() {

		while (true) {
			panneauMilieu.repaint();
			// ajoutez ici le code pour désactiver le bouton continuer et
			// arrêter si coureur1 a fini la course et coureur2 a fini la course
			
			Rectangle coureurRectangle1 = new Rectangle( coureur1.getPosX(), coureur1.getPosY(), coureur1.getLargeur(), coureur1.getHauteur());
			Rectangle coureurRectangle2 = new Rectangle( coureur2.getPosX(), coureur2.getPosY(), coureur2.getLargeur(), coureur2.getHauteur());
			

			if (coureurRectangle1.intersects(coureurRectangle2) && !music){

				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem
							.getAudioInputStream(Coureur.class.getResourceAsStream("/sound/boom.wav"));
					clip.open(inputStream);
					clip.start();
					music = true;
				} catch (Exception e) {
					System.out.println("erreur");
				}

			}

			if (coureur1.isFini() || coureur2.isFini()) {
				boutonContinuer.setEnabled(false);
				boutonArreter.setEnabled(false);
			}

			if (coureur1.isFini() && coureur2.isFini()) {
				boutonDemarrer.setEnabled(true);
			}

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}
	}

	private class BoutonDemarrerActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (coureur1.isFini()) {
				coureur1 = new Coureur('D', imageCoureurD);
				coureur2 = new Coureur('G', imageCoureurG);

				panneauMilieu.setCoureurD(coureur1);
				panneauMilieu.setCoureurG(coureur2);
				music = false;
			}

			coureur1.setAttente(false);
			coureur1.setFini(false);

			coureur2.setAttente(false);
			coureur2.setFini(false);

			boutonArreter.setEnabled(true);

			// Démarrez le coureur1
			coureur1.start();
			// Démarrez le coureur2
			coureur2.start();
			/*
			 * désactiver le bouton démarrer, pour ne pas lancer le thread des
			 * coureurs plusieurs fois, car cela provoque une erreur
			 */
			boutonDemarrer.setEnabled(false);
			boutonContinuer.setEnabled(false);

		}
	}

	private class BoutonContinuerActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// changer la condition pour faire avancer les 2 coureurs (variable
			// attente de coureur1 et de coureur2 à false)
			coureur1.setAttente(false);
			coureur2.setAttente(false);
			boutonArreter.setEnabled(true);
			boutonContinuer.setEnabled(false);
		}
	}

	private class BoutonArreterActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// changer la condition pour empêcher les coureurs d'avancer
			// (variable attente de coureur1 et de coureur2 à true)
			coureur1.setAttente(true);
			coureur2.setAttente(true);
			boutonArreter.setEnabled(false);
			boutonContinuer.setEnabled(true);
		}
	}
}
