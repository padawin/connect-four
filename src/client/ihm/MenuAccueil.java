package client.ihm;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;

import client.ihm.events.GestionSubmitPartiesCours;
import client.ihm.events.GestionSubmitCreerPartie;

/**
 * Classe de l'IHM d'accueil de l'application (une fois le client connecté)
 * @author Carine Kong, Ghislain Rodrigues, Rony Dray
 * @version 1
 */
public class MenuAccueil extends JPanel {

	
	private static final long serialVersionUID = 1L;
	private JButton boutNouvJeu, boutJoindreJeu;
	private UtilisateurListenerImpl cont;
	
	/**
	 * Constructeur pour creer la vue de l'accueil de l'application
	 * @param conteneur
	 * 					Element qui contient la vue
	 */
	public MenuAccueil(UtilisateurListenerImpl conteneur){
		
		this.cont = conteneur;
		boutNouvJeu = new JButton("Nouvelle Partie");
		boutJoindreJeu = new JButton("Rejoindre Partie");
		boutNouvJeu.setPreferredSize(boutJoindreJeu.getPreferredSize());
		
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.CENTER;
		
		c.gridx = c.gridy = 0;
		c.insets = new Insets(50,0,50,0);
		this.add(boutNouvJeu,c);
		
		c.gridy = 1;
		c.insets = new Insets(0,0,0,0);
		this.add(boutJoindreJeu,c);
		

		//evenements
		GestionSubmitCreerPartie eventBoutCreerPartie = new GestionSubmitCreerPartie(cont);
		GestionSubmitPartiesCours eventBoutJoindreJeu = new GestionSubmitPartiesCours(cont);
        
        boutNouvJeu.addActionListener(eventBoutCreerPartie);
        
        boutJoindreJeu.addActionListener(eventBoutJoindreJeu);
       
	}
}
