package client.ihm;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import client.ihm.events.EnterKeyNouvJeu;
import client.ihm.events.GestionSubmitAccueil;
import client.ihm.events.GestionSubmitNouvJeu;

/**
 * Classe de l'IHM de la creation d'une partie de puissance 4
 * @author Carine Kong, Ghislain Rodrigues, Rony Dray
 * @version 1
 */
public class MenuNouvJeu extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JLabel titre, partie, pass, couleur;
	private JTextField partieIN;
	private JPasswordField passIN;
	private JButton boutCreerPartie, boutRetour;
	private UtilisateurListenerImpl cont;
	private JComboBox couleurIN;
	
	/**
	 * Constructeur pour creer la vue de la creation d'une partie de puissance 4
	 * @param conteneur
	 * 					Element qui contient la vue
	 */
	public MenuNouvJeu(UtilisateurListenerImpl conteneur){
		this.cont = conteneur;
		boutCreerPartie = new JButton("Créer Partie");
		boutRetour = new JButton("Retour");
		titre = new JLabel();
		titre.setText("Nouveau Jeu");
		partie = new JLabel();
		partie.setText("Nom de la partie");
		pass = new JLabel();
		pass.setText("Mot de passe (laisser vide si partie publique)");
		partieIN = new JTextField(20);
		passIN = new JPasswordField(20);
		String[] couleurs = { "jaune", "rouge"};
		couleur = new JLabel();
		couleur.setText("Votre couleur");
		couleurIN = new JComboBox(couleurs);
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.LINE_START;
		
		c.gridx = c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 2;
		c.insets = new Insets(0,0,10,0);
		this.add(titre,c);

		c.insets = new Insets(0,0,5,0);
		c.gridheight = 1;
		c.gridy = 3;
		this.add(partie,c);
		
		c.gridy = 4;
		this.add(partieIN,c);

		c.gridy = 5;
		this.add(pass,c);
		
		c.gridy = 6;
		this.add(passIN,c);
		
		c.gridy = 7;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.BASELINE;
		this.add(couleur,c);
		
		c.gridx = 1;
		c.insets = new Insets(0,10,0,0);
		this.add(couleurIN,c);
		
		c.anchor = GridBagConstraints.CENTER;
		c.gridy = 8;
		c.gridx = 0;
		c.gridwidth = 1;
		c.insets = new Insets(10,0,0,0);
		this.add(boutCreerPartie,c);

		c.gridx = 1;
		c.insets = new Insets(10,0,0,0);
		this.add(boutRetour,c);
		
		//evenements
		GestionSubmitNouvJeu eventBoutNouvJeu = new GestionSubmitNouvJeu(cont);
		GestionSubmitAccueil eventBoutAccueil = new GestionSubmitAccueil(cont);
		
        boutCreerPartie.addActionListener(eventBoutNouvJeu);
        
        boutRetour.addActionListener(eventBoutAccueil);
        
        EnterKeyNouvJeu eventKeyNouvJeu = new EnterKeyNouvJeu(cont);
		
        partieIN.addKeyListener(eventKeyNouvJeu);
        passIN.addKeyListener(eventKeyNouvJeu);
        couleurIN.addKeyListener(eventKeyNouvJeu);
		
	}

	/**
	 * Getter le nom de la partie a creer
	 * @return le nom de la partie
	 */
	public String getNomPartie(){
		return partieIN.getText();
	}

	/**
	 * Getter le mot de passe de la partie a creer
	 * @return le mot de passe de la partie
	 */
	public String getMdpPartie() {
		return new String(passIN.getPassword());
	}

	/**
	 * Getter de la couleur desiree pour le createur de la partie
	 * @return couleur choisie
	 */
	public String getCouleur() {
		return (String) couleurIN.getSelectedItem();
	}
}
