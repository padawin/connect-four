package client.ihm.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.ihm.UtilisateurListenerImpl;

/**
 * Classe de l'evenement pour afficher la liste des parties en cours
 * 
 * @author Carine Kong, Ghislain Rodrigues, Rony Dray
 * @version 1
 */
public class GestionSubmitPartiesCours implements ActionListener  {

	public UtilisateurListenerImpl cont;
	
	public GestionSubmitPartiesCours(UtilisateurListenerImpl cont) {
		super();
		this.cont = cont;
	}

	/** 
	 * 
	 * @see UtilisateurListenerImpl#partiesEnCours()
	 */
	public void actionPerformed(ActionEvent arg0) {
		cont.partiesEnCours();
	}

}
