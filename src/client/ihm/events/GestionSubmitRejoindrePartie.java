package client.ihm.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;

import client.ihm.UtilisateurListenerImpl;

/**
 * Evenement pour rejoindre une partie
 * 
 * @author Carine Kong, Ghislain Rodrigues, Rony Dray
 * @version 1
 */
public class GestionSubmitRejoindrePartie implements ActionListener {

	private JList listeParties;
	private UtilisateurListenerImpl cont;
	

	/**
	 * @param listeParties
	 * @param cont
	 */
	public GestionSubmitRejoindrePartie(JList listeParties,
			UtilisateurListenerImpl cont) {
		super();
		this.listeParties = listeParties;
		this.cont = cont;
	}


	/**
	 * Si une partie est selectionnee, la methode rejoindrePartie est executee et la partie commence
	 */
	public void actionPerformed(ActionEvent arg0) {
		if(!listeParties.isSelectionEmpty()){
			this.cont.rejoindrePartie();
		}
	}

}
