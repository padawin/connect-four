package client.ihm.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.ihm.UtilisateurListenerImpl;

/**
 * Classe de l'evenement de creation d'une nouvelle partie
 * 
 * @author Carine Kong, Ghislain Rodrigues, Rony Dray
 * @version 1
 */
public class GestionSubmitNouvJeu implements ActionListener {


	private UtilisateurListenerImpl cont;

	public GestionSubmitNouvJeu(UtilisateurListenerImpl cont) {
		super();
		this.cont = cont;
	}
	
	/**
	 * @see UtilisateurListenerImpl#nouvJeu()
	 */
	public void actionPerformed(ActionEvent arg0) {
		cont.nouvJeu();
	}

}
