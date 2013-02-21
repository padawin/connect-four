package client.ihm.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import client.Client;
import client.ihm.IHMPartie;
import client.ihm.UtilisateurListenerImpl;


/**
 * Classe de l'evenement pour jouer un tour
 * 
 * @author Carine Kong, Ghislain Rodrigues, Rony Dray
 * @version 1
 */
public class GestionSubmitJouer implements ActionListener {

	private Client client;
	private IHMPartie ihmPartie;
	private int partie;
	private UtilisateurListenerImpl cont;

	public GestionSubmitJouer(Client client, IHMPartie ihmPartie, int partie,
			UtilisateurListenerImpl cont) {
		super();
		this.client = client;
		this.ihmPartie = ihmPartie;
		this.partie = partie;
		this.cont = cont;
	}


	
	/**
	 * setteur de l'ihm de la partie
	 * @param ihmPartie
	 */
	public void setIhmPartie(IHMPartie ihmPartie) {
		this.ihmPartie = ihmPartie;
	}



	/**
	 * Setter de la partie
	 * @param partie
	 */
	public void setPartie(int partie) {
		this.partie = partie;
	}
	
	

	/** (non-Javadoc)
	 * Methode pour jouer. Regarde s'il reste une case libre dans la colonne selectionnee. si oui, insere le pion dedans, sinon ne fait rien
	 */
	public void actionPerformed(ActionEvent arg0) {
		int[] coordonnees = new int[2];
		coordonnees[0] = -1;
		coordonnees[1] = -1;
		coordonnees = client.saisieColonne(ihmPartie.getColonne(),partie);
		if (coordonnees != null) {
			try {
				cont.disableBoutJouer();
				client.getService().finTour(partie, coordonnees[0],
						coordonnees[1], client.getCouleur());
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

}
