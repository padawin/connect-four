package client.ihm.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.ihm.UtilisateurListenerImpl;


/**
 * Classe de l'evenement de creation de compte d'un nouveau joueur
 * 
 * @author Carine Kong, Ghislain Rodrigues, Rony Dray
 * @version 1
 */
public class GestionDeconnexion implements ActionListener {

	private UtilisateurListenerImpl utilisateurListenerImpl;


	public GestionDeconnexion(UtilisateurListenerImpl utilisateurListenerImpl) {
		this.utilisateurListenerImpl = utilisateurListenerImpl;
	}

	/**
	 * @see UtilisateurListenerImpl#deconnecter()
	 */
	public void actionPerformed(ActionEvent e) {
		utilisateurListenerImpl.deconnecter();
	}

}
