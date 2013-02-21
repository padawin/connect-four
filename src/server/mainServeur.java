package server;

import java.rmi.RemoteException;

/**
 * Classe de lancement de l'application serveur
 * @author Carine Kong, Ghislain Rodrigues, Rony Dray
 * @version 1
 */
public class mainServeur {

	/**
	 * Classe d'entree de l'application serveur
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			@SuppressWarnings("unused")
			Serveur s = new Serveur();
		}
		catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
