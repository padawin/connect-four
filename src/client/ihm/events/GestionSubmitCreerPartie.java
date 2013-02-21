package client.ihm.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.ihm.UtilisateurListenerImpl;


/**
 * Classe de l'evenement de creation d'une partie
 * 
 * @author Carine Kong, Ghislain Rodrigues, Rony Dray
 * @version 1
 */
public class GestionSubmitCreerPartie implements ActionListener {


	private UtilisateurListenerImpl cont;

	public GestionSubmitCreerPartie(UtilisateurListenerImpl cont) {
		super();
		this.cont = cont;
	}
	
	/**
	 * @see UtilisateurListenerImpl#creerPartie()
	 */
	public void actionPerformed(ActionEvent arg0) {
		cont.creerPartie();
	}

}
