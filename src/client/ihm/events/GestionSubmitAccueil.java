package client.ihm.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.ihm.UtilisateurListenerImpl;


/**
 * Classe de l'evenement pour afficher la page d'accueil de l'application
 * 
 * @author Carine Kong, Ghislain Rodrigues, Rony Dray
 * @version 1
 */
public class GestionSubmitAccueil implements ActionListener {


	private UtilisateurListenerImpl cont;

	public GestionSubmitAccueil(UtilisateurListenerImpl cont) {
		super();
		this.cont = cont;
	}
	
	/**
	 * @see UtilisateurListenerImpl#accueil()
	 */
	public void actionPerformed(ActionEvent arg0) {
		cont.accueil();
	}

}
