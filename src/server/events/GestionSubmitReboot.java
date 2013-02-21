package server.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import server.Serveur;

/**
 * Classe de l'evenement pour redemarrer le serveur
 * 
 * @author Carine Kong, Ghislain Rodrigues, Rony Dray
 * @version 1
 */
public class GestionSubmitReboot implements ActionListener {

	private Serveur serveur;
	
	
	
	public GestionSubmitReboot(Serveur serveur) {
		super();
		this.serveur = serveur;
	}


	/**
	 * Methode qui redemarre le serveur
	 * @see Serveur#demarrer()
	 */
	public void actionPerformed(ActionEvent arg0) {
		serveur.demarrer();
	}

}
