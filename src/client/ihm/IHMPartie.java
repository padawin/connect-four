package client.ihm;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import client.ihm.events.GestionSubmitAccueil;
import client.ihm.events.GestionSubmitJouer;

/**
 * Classe de l'IHM du déroulement de la partie avec l'historique des coups joués, les éléments pour jouer...
 * @author Carine Kong, Ghislain Rodrigues, Rony Dray
 * @version 1
 */
public class IHMPartie extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel titre,colonne;
	private JTextArea log;
	private JScrollPane scrollPaneLog;
	private JComboBox colonneIN;
	private JButton boutJouer, boutAccueil;
	private String[] colsDispo = {"1","2","3","4","5","6","7"};
	private GridBagConstraints c;
	private UtilisateurListenerImpl cont;

	/**
	 * Creer le panel pour jouer dans le conteneur "conteneur"
	 * @param conteneur
	 */
	public IHMPartie(UtilisateurListenerImpl conteneur){
		this.cont = conteneur;
		this.titre = new JLabel();
		this.titre.setText("Deroulement de la partie");
		this.log = new JTextArea();
		this.log.setColumns(23);
		this.log.setRows(16);
		this.log.setEditable(false);
		scrollPaneLog = new JScrollPane();
		scrollPaneLog.setViewportView(log);
		this.colonne = new JLabel();
		this.colonne.setText("Placer le pion à la colonne");
		this.colonneIN = new JComboBox(colsDispo);
		this.boutJouer = new JButton("Jouer");
		this.boutAccueil = new JButton("Retour a l'accueil");
		boutJouer.setEnabled(false);
		
		
		this.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = c.gridy = 0;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.WEST;
		this.add(this.titre,c);
		
		c.gridy = 1;
		c.weightx = c.weighty = 1.0;
		this.add(this.scrollPaneLog,c);

		c.fill = GridBagConstraints.NONE;
		c.gridy = 2;
		c.gridwidth = 1;
		this.add(this.colonne,c);
		
		c.gridx = 1;
		c.insets = new Insets(0,10,0,0); 
		this.add(this.colonneIN,c);

		c.gridy = 3;
		c.gridx = 0;
		c.gridwidth = 2;
		c.insets = new Insets(10,0,0,0);
		c.anchor = GridBagConstraints.CENTER;
		this.add(this.boutJouer,c);
	}
	
	/**
	 * Fonction pour remplir la combo box pour choisir une colonne pour jouer (en fonction des coups disponibles)
	 * @param liste
	 * 				Liste des numéros des colonnes disponibles
	 */
	public void setColsDispo(String[] liste){
		colsDispo = liste;
	}
	
	/**
	 * Getter du log
	 * @return le log de jeu
	 */
	public JTextArea getLog(){
		return log;
	}

	/**
	 * Methode pour activer le bouton de jeu quand c'est le tour du joueur de jouer
	 * @param actBoutJouer
	 * 				Action a faire lorsqu'on clique sur le bouton
	 * @see UtilisateurListenerImpl
	 */
	public void votreTour(GestionSubmitJouer actBoutJouer) {
		boutJouer.setEnabled(true);
		boutJouer.addActionListener(actBoutJouer);
	}

	/**
	 * Methode pour desactiver le bouton de jeu quand ce n'est pas le tour du joueur de jouer
	 * @param actBoutJouer
	 * 				Action a enlever du bouton
	 * @see UtilisateurListenerImpl
	 */
	public void pasVotreTour(GestionSubmitJouer actBoutJouer) {
		boutJouer.setEnabled(false);
		boutJouer.removeActionListener(actBoutJouer);
	}

	/**
	 * Getter de la colonne a jouer
	 * @return
	 */
	public int getColonne() {
		return colonneIN.getSelectedIndex();
	}
	
	
	/**
	 * Methode pour enlever les elements de jeu pour les remplacer par un bouton de retour vers l'accueil
	 */
	public void finPartie(){
		this.remove(colonne);
		this.remove(colonneIN);
		this.remove(boutJouer);
		c.gridy = 2;
		c.gridx = 0;
		c.gridwidth = 2;
		c.insets = new Insets(10,0,0,0);
		c.anchor = GridBagConstraints.CENTER;
		this.add(boutAccueil,c);
		this.updateUI();
		
		boutAccueil.addActionListener(new GestionSubmitAccueil(cont));
	}
	
	
}
