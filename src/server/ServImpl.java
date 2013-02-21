package server;

import java.rmi.RemoteException;
import java.util.ArrayList;

import client.UtilisateurListener;

/**
 * Classe de gestion du serveur avec les clients abonnes
 * 
 * @author Carine Kong, Ghislain Rodrigues, Rony Dray
 * @version 1
 */
public class ServImpl extends Thread implements Serv {

	private ArrayList<UtilisateurListener> listeUtilisateurs = new ArrayList<UtilisateurListener>();
	private ArrayList<Partie> listeParties = new ArrayList<Partie>();
	private String message = "";
	private int lastPartie = 0;
	private ClientWebService wsClient = new ClientWebService();



	/* (non-Javadoc)
	 * @see server.Serv#addUtilisateur(client.UtilisateurListener)
	 */
	public synchronized void addUtilisateur(UtilisateurListener listener)
			throws RemoteException {
		// changer en interrogation a la BDD pour les login/pass
		listeUtilisateurs.add(listener);
		nouveauMessage("***| " + listener.getName()
				+ " vient de se connecter |***");
		VueServeur.getListeClients(listeUtilisateurs);
		refreshListUtilisateurListener();
	}

	/* (non-Javadoc)
	 * @see server.Serv#removeUtilisateur(client.UtilisateurListener, boolean)
	 */
	public synchronized void removeUtilisateur(UtilisateurListener listener, boolean joue)
			throws RemoteException {
		
		/*pour retirer la partie de la liste des parties
		* on recherche l'index de la partie jouée par le joueur "listener" et on la retire de la liste
		*/
		//numero de la partie du joueur qui s'est deconnecte
		int p = listener.getPartie();
		//si le joueur jouait on averti sont adversaire qu'il a gagne et on arrete la partie
		if(p!=-1){
			int gagnant,perdant;
			if(recherchePartie(p).getJ1().equals(listener)){ //j2 a gagne
				if(recherchePartie(p).getJ2()!=null){
					//on averti j2
					listeUtilisateurs.get(listeUtilisateurs.indexOf(recherchePartie(p).getJ2())).logChanged(listener.getName()+" a abandonne la partie");
					//TODO debut test si invite
					//si le joueur 2 est un invite
					if(recherchePartie(p).getJ2().getName().equals("Guest")){
						gagnant = -1;
					}
					else{
						gagnant = recherchePartie(p).getJ2().getClient().getId();
					}
					perdant = recherchePartie(p).getJ1().getClient().getId();
					//seulement si au moins un des joueur est inscrit on enregistre le resultat
					if(gagnant!= -1 || perdant != -1){
						wsClient.sauvegardeResultat(p, gagnant, perdant);
					}
					//TODO fin test si invite
					//version de base
					//wsClient.sauvegardeResultat(p, recherchePartie(p).getJ2().getClient().getId(), recherchePartie(p).getJ1().getClient().getId());
					//fin version de base
					listeUtilisateurs.get(listeUtilisateurs.indexOf(recherchePartie(p).getJ2())).afficherStats();
					listeUtilisateurs.get(listeUtilisateurs.indexOf(recherchePartie(p).getJ2())).partieFinie();
				}
			}
			else if(recherchePartie(p).getJ2().equals(listener)){//j1 a gagne
				//on averti j1
				listeUtilisateurs.get(listeUtilisateurs.indexOf(recherchePartie(p).getJ1())).logChanged(listener.getName()+" a abandonne la partie");
				//TODO debut test si invite
				//si le joueur 2 est un invite
				if(recherchePartie(p).getJ2().getName().equals("Guest")){
					perdant = -1;
				}
				else{
					perdant = recherchePartie(p).getJ2().getClient().getId();
				}
				gagnant = recherchePartie(p).getJ1().getClient().getId();
				//seulement si au moins un des joueur est inscrit on enregistre le resultat
				if(gagnant!= -1 || perdant != -1){
					wsClient.sauvegardeResultat(p, gagnant, perdant);
				}
				//TODO fin test si invite
				//version de base
				//wsClient.sauvegardeResultat(p, recherchePartie(p).getJ2().getClient().getId(), recherchePartie(p).getJ1().getClient().getId());
				//fin version de base
				listeUtilisateurs.get(listeUtilisateurs.indexOf(recherchePartie(p).getJ1())).afficherStats();
				listeUtilisateurs.get(listeUtilisateurs.indexOf(recherchePartie(p).getJ1())).partieFinie();
			}
			
			listeParties.remove(getIndexPartie(listener.getPartie()));
		}
		listeUtilisateurs.remove(listener);
		nouveauMessage("***| " + listener.getName()
				+ " vient de se deconnecter |***");
		VueServeur.getListeClients(listeUtilisateurs);
		// actualisation de la liste des parties
		for (int i = 0; i < listeUtilisateurs.size(); i++) {
			listeUtilisateurs.get(i).actualiserPartie(getListeParties());
		}
		refreshListUtilisateurListener();
	}


	/* (non-Javadoc)
	 * @see server.Serv#addPartie(client.UtilisateurListener, java.lang.String, java.lang.String)
	 */
	public synchronized int addPartie(UtilisateurListener client,
			String nomPartie, String mdpPartie) throws RemoteException {
		// Creation de la partie en base
		int idPartie = wsClient.createPartie(client.getClient().getId(),nomPartie, mdpPartie);
		lastPartie = idPartie;

		// Le numero de la nouvelle partie est le numero le plus eleve
		// de partie
		// creee + 1
		Partie p = new Partie(client, nomPartie, mdpPartie, lastPartie);
		//int tmp = lastPartie;
		//lastPartie = lastPartie + 1;
		nouveauMessage("***| " + client.getName()
				+ " vient de creer la partie " + nomPartie + " |***");
		listeParties.add(p);
		for (int i = 0; i < listeUtilisateurs.size(); i++) {
			listeUtilisateurs.get(i).actualiserPartie(getListeParties());
		}
		//return tmp;
		return lastPartie;
		// a changer en actualisation de la liste des parties si existe
		// VueServeur.getListeParties(listeParties);
	}

	/* (non-Javadoc)
	 * @see server.Serv#rejoindrePartie(int, client.UtilisateurListener)
	 */
	public void rejoindrePartie(int idPartie, UtilisateurListener stub)
			throws RemoteException {
		// ajout en base du nouveau participant
		wsClient.rejoindrePartie(stub.getClient().getId(), idPartie);
	
		recherchePartie(idPartie).rejoindrePartie(stub);
		// envoyer un message a l'autre joueur de la partie i
		UtilisateurListener j1 = recherchePartie(idPartie).getJ1();
		int s1 = listeUtilisateurs.indexOf(j1);
		int s2 = listeUtilisateurs.indexOf(stub);
		listeUtilisateurs.get(s1).logChanged(
				stub.getName() + " a accepte de jouer contre vous\nVous etes "
						+ (stub.getCouleur() == 'R' ? "JAUNE" : "ROUGE"));
		listeUtilisateurs.get(s2).logChanged(
				"Debut de la partie contre " + j1.getName() + "\nVous etes "
						+ (stub.getCouleur() == 'R' ? "ROUGE" : "JAUNE"));
		for (int i = 0; i < listeUtilisateurs.size(); i++) {
			listeUtilisateurs.get(i).actualiserPartie(getListeParties());
		}
	}

	/* (non-Javadoc)
	 * @see server.Serv#removePartie(server.Partie)
	 */
	public synchronized void removePartie(Partie p) throws RemoteException {
		listeParties.remove(p);
		for (int i = 0; i < listeUtilisateurs.size(); i++) {
			listeUtilisateurs.get(i).actualiserPartie(getListeParties());
		}
		// a changer en actualisation de la liste des parties si existe
		// VueServeur.getListeParties(listeParties);
	}

	/**
	 * 
	 */
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/* (non-Javadoc)
	 * @see server.Serv#getListeParties()
	 */
	public ArrayList<Partie> getListeParties() {
		ArrayList<Partie> tmp = new ArrayList<Partie>();
		for (int i = 0; i < listeParties.size(); i++) {
			if (listeParties.get(i).enAttente()) {
				tmp.add(listeParties.get(i));
			}
		}
		return tmp;
	}

	/* (non-Javadoc)
	 * @see server.Serv#afficherListeClients(server.VueServeur)
	 */
	@SuppressWarnings("static-access")
	public void afficherListeClients(VueServeur vueServ) {
		vueServ.getListeClients(listeUtilisateurs);
	}

	/* (non-Javadoc)
	 * @see server.Serv#refreshListUtilisateurListener()
	 */
	public synchronized void refreshListUtilisateurListener() {
		for (int i = 0; i < listeUtilisateurs.size(); i++) {
			try {
				listeUtilisateurs.get(i).getListeClients(listeUtilisateurs);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	/* (non-Javadoc)
	 * @see server.Serv#checkID(java.lang.String, java.lang.String)
	 */
	public int checkID(String login, String pass) {
		// changer en interrogation de la bdd et retour : l'id du joueur
		//si le login est guest, on se connecte en invite
		if(login.equals("Guest") && pass.equals("")){
			return -2;
		}
		//sinon si le couple login/pass est trouve en base on renvoi l'id de l'enregistrement
		if (wsClient.authenticate(login, pass)) {
			// if (login.equals("login") && pass.equals("pass")) {
			// return listeUtilisateurs.size();
			if(rechercheClient(login)==-1){
				return wsClient.getIdJoueurByLogin(login);
			}
			else{
				return -3;
			}
		}
		//sinon le couple est incorrect on renvoie -1
		else {
			return -1;
		}
	}

	/* (non-Javadoc)
	 * @see server.Serv#getMessage()
	 */
	public String getMessage() throws RemoteException {
		return message;
	}

	/* (non-Javadoc)
	 * @see server.Serv#nouveauMessage(java.lang.String)
	 */
	public synchronized void nouveauMessage(String msg) {
		message = msg;
		for (int i = 0; i < listeUtilisateurs.size(); i++) {
			try {
				listeUtilisateurs.get(i).messageChanged(message);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	/* (non-Javadoc)
	 * @see server.Serv#getIndexPartie(int)
	 */
	public int getIndexPartie(int id) throws RemoteException {
		int index = -1;
		for (int p = 0; p < listeParties.size(); p++) {
			if (listeParties.get(p).getNum() == id) {
				index = p;
			}
		}
		return index;
	}

	/* (non-Javadoc)
	 * @see server.Serv#recherchePartie(int)
	 */
	public Partie recherchePartie(int id) {
		int index = -1;
		try {
			index = getIndexPartie(id);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if (index != -1) {
			return listeParties.get(index);
		} else {
			return null;
		}
	}

	/**
	 * Recherche le client dont le login est "login".
	 * 
	 * @param login
	 *            login du client a rechercher
	 * @return l'index du client s'il est trouve, -1 sinon
	 */
	public int rechercheClient(String login) {
		int index = -1;
		for(int i = 0; i < listeUtilisateurs.size() ; i ++){
			try {
				if(listeUtilisateurs.get(i).getName().equals(login)){
					index = i;
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return index;
	}

	/* (non-Javadoc)
	 * @see server.Serv#ajouterJeton(int, int, int, char)
	 */
	public void ajouterJeton(int partie, int ligne, int colonne, char joueur)
			throws RemoteException {
		recherchePartie(partie).ajouterJeton(ligne, colonne, joueur);
	}

	/* (non-Javadoc)
	 * @see server.Serv#estPleine(int)
	 */
	public boolean estPleine(int partie) {
		return recherchePartie(partie).estPleine();
	}

	/* (non-Javadoc)
	 * @see server.Serv#estTerminee(int)
	 */
	public int estTerminee(int partie) {
		return listeParties.get(partie).estTerminee();
	}

	/* (non-Javadoc)
	 * @see server.Serv#rechercheCaseLibre(int, int)
	 */
	public int rechercheCaseLibre(int partie, int colonne) {
		return recherchePartie(partie).rechercheCaseLibre(colonne);
	}

	/* (non-Javadoc)
	 * @see server.Serv#estGagnant(int, int, int, char)
	 */
	public boolean estGagnant(int partie, int ligne, int colonne, char joueur) {
		return recherchePartie(partie).estGagnant(ligne, colonne, joueur);
	}

	/* (non-Javadoc)
	 * @see server.Serv#finiDeJouer(int, char)
	 */
	public void finiDeJouer(int partie, char joueur) throws RemoteException {
		recherchePartie(partie).finiDeJouer(joueur);
	}

	/* (non-Javadoc)
	 * @see server.Serv#initPartie(int)
	 */
	public void initPartie(int id) throws RemoteException {
		// on recherche la partie dans la liste
		Partie p = recherchePartie(id);
		// on recupere le joueur qui commence
		char com = p.quiCommence();
		String phrase = "Au joueur " + (com == 'R' ? "Rouge" : "Jaune")
				+ " de commencer";
		int j1 = listeUtilisateurs.indexOf(p.getJ1());
		int j2 = listeUtilisateurs.indexOf(p.getJ2());
		// si c'est le joueur 1 qui commence on lui dit et on active son bouton
		// de jeu
		if (p.getJ1().getCouleur() == com) {
			listeUtilisateurs.get(j1).logChanged("A vous de jouer");
			listeUtilisateurs.get(j1).enableBoutJouer();
			listeUtilisateurs.get(j2).logChanged(phrase);
		} else if (p.getJ2().getCouleur() == com) {
			// sinon si c'est le joueur 2 qui commence on lui dit et on active
			// son bouton de jeu
			listeUtilisateurs.get(j2).logChanged("A vous de jouer");
			listeUtilisateurs.get(j2).enableBoutJouer();
			listeUtilisateurs.get(j1).logChanged(phrase);
		}
	}

	/* (non-Javadoc)
	 * @see server.Serv#finTour(int, int, int, char)
	 */
	public void finTour(int partie, int ligne, int colonne, char couleur) {
		try {
			// partie en question
			Partie p = recherchePartie(partie);
			int j1 = listeUtilisateurs.indexOf(p.getJ1());
			int j2 = listeUtilisateurs.indexOf(p.getJ2());
			finiDeJouer(partie, couleur);
			char com = p.aQuiDeJouer();
			// s'il y a un gagnant on averti les joueurs
			if (estGagnant(partie, ligne, colonne, couleur)) { // si je joueur
				// qui vient de
				// jouer a gagnï¿½
				if (p.getJ1().getCouleur() == couleur) {
					listeUtilisateurs.get(j1).logChanged(
							"Vous avez gagne la partie");

					listeUtilisateurs.get(j2).logChanged(
							"Vous avez perdu la partie");

					// sauvegarde du rÃ©sultat en base
					wsClient.sauvegardeResultat(p.getNum(), p.getJ1().getClient().getId(), p.getJ2().getClient().getId());

				} else if (p.getJ2().getCouleur() == couleur) {
					listeUtilisateurs.get(j1).logChanged(
							"Vous avez perdu la partie");
					listeUtilisateurs.get(j1).finJeu();
					listeUtilisateurs.get(j2).logChanged(
							"Vous avez gagne la partie");
					listeUtilisateurs.get(j2).finJeu();

					// sauvegarde du rÃ©sultat en base
					wsClient.sauvegardeResultat(p.getNum(), p.getJ2().getClient().getId(), p.getJ1().getClient().getId());
				}
				/*
				 * MAJ STATS BASE
				 */
				listeParties.remove(p);

				// changer vue pour retourner à l'accueil
				listeUtilisateurs.get(j1).afficherStats();
				listeUtilisateurs.get(j1).partieFinie();
				// changer vue pour retourner à l'accueil
				listeUtilisateurs.get(j2).afficherStats();
				listeUtilisateurs.get(j2).partieFinie();
			} else if (estPleine(partie)) { // si la grille est pleine == match
				// null
				listeUtilisateurs.get(j1).logChanged("Match Nul");
				listeUtilisateurs.get(j2).logChanged("Match Nul");

				/*
				 * MAJ STATS BASE
				 */
				// sauvegarde du rÃ©sultat en base
				//version de base
				wsClient.declarerNul(p.getNum());
				listeParties.remove(p);

				// changer vue pour retourner à l'accueil
				listeUtilisateurs.get(j1).partieFinie();
				listeUtilisateurs.get(j1).afficherStats();
				// changer vue pour retourner à l'accueil
				listeUtilisateurs.get(j2).partieFinie();
				listeUtilisateurs.get(j2).afficherStats();

			} else {// tour suivant
				String phrase = "Au joueur " + (com == 'R' ? "Rouge" : "Jaune")
						+ " de jouer";
				if (p.getJ1().getCouleur() == com) {
					listeUtilisateurs.get(j1).logChanged("A vous de jouer");
					listeUtilisateurs.get(j1).enableBoutJouer();
					listeUtilisateurs.get(j2).logChanged(phrase);
				} else if (p.getJ2().getCouleur() == com) {
					listeUtilisateurs.get(j2).logChanged("A vous de jouer");
					listeUtilisateurs.get(j2).enableBoutJouer();
					listeUtilisateurs.get(j1).logChanged(phrase);
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return le web service
	 */
	public ClientWebService getWsClient() {
		return wsClient;
	}
	
	/**
	 * Methode pour deconnecter tous les clients si le serveur est eteint
	 */
	public void deconnecterUtilisateurListener(){
		System.out.println(listeUtilisateurs.size());
		int i = 0;
		while(listeUtilisateurs.size()>0){
			try {
				listeUtilisateurs.get(i).deconnecter();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
}
