package client;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JOptionPane;

import client.ihm.Connexion;
import client.ihm.UtilisateurListenerImpl;
import server.Partie;
import server.Serv;

/**
 * Classe de gestion d'un client
 * 
 * @author Carine Kong, Ghislain Rodrigues, Rony Dray
 * @version 1
 */

public class Client implements Serializable  {

	
	private static final long serialVersionUID = 1L;
	private String login;
	private int id;
	private char couleur;
	private String host = Globals.DEFAULT_HOST;
	private String port = Globals.DEFAULT_PORT;
	private Serv service;
	public UtilisateurListener stub;
	public String pass;
	private boolean joue = false;

	/**
	 * Contructeur de la classe Client. Si des arguments ont ete passes en
	 * parametre (un login, un mot de passe et un serveur), la fonction les
	 * analyse et lance la fonction connexion qui va tenter de se connecter avec
	 * les informations fournies. Sinon on cree un objet Connexion qui va creer
	 * une fenetre o� l'utilisateur pourra soit creer un nouveau compte soit se
	 * connecter avec ses identifiants.
	 * 
	 * @param args
	 *            si ils ont ete passes, il doit y avoir le serveur (host), le
	 *            login (login) et le mot de passe (pass)
	 */
	public Client(String[] args){
		// analyse arguments
		// si le client a ete lance avec des arguments (host, port, login, pass) on se
		// connecte directement avec
		if (args.length == 6) {
			for (int argu = 0; argu < args.length; argu++) {
				if ((args[argu].equals("-host") || args[argu].equals("-h")
						|| args[argu].equals("host") || args[argu].equals("h"))
						&& argu < args.length - 1) {
					host = args[argu + 1];
				}
				if ((args[argu].equals("-port") || args[argu].equals("-p")
						|| args[argu].equals("port") || args[argu].equals("p"))
						&& argu < args.length - 1) {
					port = args[argu + 1];
				}
				if ((args[argu].equals("-login") || args[argu].equals("-l")
						|| args[argu].equals("login") || args[argu].equals("l"))
						&& argu < args.length - 1) {
					login = args[argu + 1];
				}
				if ((args[argu].equals("-pass") || args[argu].equals("-p")
						|| args[argu].equals("pass") || args[argu].equals("p"))
						&& argu < args.length - 1) {
					// pass = args[argu+1].toCharArray();
					pass = args[argu = 1];
				}
			}
			connexion(host, port, login, pass);
		} else {// sinon on affiche la fenetre de connexion
			@SuppressWarnings("unused")
			Connexion vueConnexion = new Connexion(this);
		}
	}

	/**
	 * Fonction qui va tenter de connecter le client au serveur distant avec les
	 * outils de RMI. Si le service est trouve, celui-ci va tester le couple
	 * login/mot de passe pour savoir s'il s'agit d'un utilisateur existant Si
	 * c'est le cas l'utilisateur s'abonne au service. Sinon la methode renvoit
	 * false
	 * 
	 * @param host
	 *            serveur sur lequel se trouve le service recherche
	 * @param login
	 *            login de l'utilisateur
	 * @param pass
	 *            mot de passe de l'utilisateur
	 * @return true si la connexion a pu etre effectuee, false sinon
	 */
	public String connexion(String host, String port, String login, String pass) {
		if(!host.equals("")){
			this.host = host;
		}
		if(!port.equals("")){
			this.port = port;
		}
		try {
			String ra = rechercheAnnuaire();
			if(ra.equals(Globals.ANNUAIRE_TROUVE)){
				// creation de l'ihm
				int c = service.checkID(login, pass);
				/*
				 * c == -2 -> invite
				 * c == -1 -> couple login/pass incorrect
				 * autrement couple trouve
				 */
				if (c == -1) {
					return Globals.ERROR_AUTENTIFICATION;
				}
				else if(c == -3){
					return Globals.ERROR_CONNEXION_MULTIPLE;
				}
				else{
					this.login = login;
					this.pass = pass;
					this.id = c;
					UtilisateurListenerImpl moniteur = new UtilisateurListenerImpl(
							service, this);
					stub = (UtilisateurListener) UnicastRemoteObject.exportObject(
							moniteur, 0);
					service.addUtilisateur(stub);
					return Globals.CONNEXION_OK;
				}
			}else{
				return ra;
			}

		}
		catch (RemoteException e) {
			return Globals.ERROR_ANNUAIRE_CONNEXION;
		}
	}
	
	/**
	 * Methode pour rechercher l'annuaire de l'application a l'adresse "host" et au port "port"
	 * @return Une chaine contenant le resultat de la recherche. Le message est une constante de la classe Globals
	 * @see Globals 
	 */
	public String rechercheAnnuaire(){
		Registry annuaire;
		String result = "";
		try {
			annuaire = LocateRegistry.getRegistry(this.host, Integer.parseInt(this.port));
			service = (Serv) annuaire.lookup("puissance4");
			result = Globals.ANNUAIRE_TROUVE;
		}catch(java.rmi.ConnectException e){
			result =  Globals.ERROR_ANNUAIRE_CONNEXION;
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		return result;

	}

	
	/**
	 * Getter de l'id du client
	 * 
	 * @return id du client
	 */
	public int getId() {
		return id;
	}

	/**
	 * Deconnecte l'utilisateur du service en le desabonnant
	 */
	public void deconnexion() {
		try {
			service.removeUtilisateur(stub,this.joue);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Getter de lu login du client
	 * @return le login du client
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Setter du login du client
	 * @param login
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Setter de la couleur du client
	 * @param c
	 */
	public void setCouleur(String c) {
		if (c.toUpperCase().equals("JAUNE")) {
			this.couleur = 'J';
		} else if (c.toUpperCase().equals("ROUGE")) {
			this.couleur = 'R';
		}
	}

	/**
	 * Getter du service du client
	 * @return
	 */
	public Serv getService() {
		return service;
	}

	/**
	 * Getter de la couleur du client
	 * @return
	 */
	public char getCouleur() {
		return this.couleur;
	}

	/**
	 * Methode pour rejoindre une partie identifiee par l'ID id.
	 * On recupere la partie aupres du service, on recupere son mdp
	 * si le mdp de la partie n'est pas vide, alors on le demande au client 
	 * @param id
	 * 			Identifiant de la partie a rejoindre
	 * @throws RemoteException
	 */
	public void rejoindrePartie(int id) throws RemoteException {
		// on recupere les infos de la partie
		Partie p = service.recherchePartie(id);
		//Partie p = service.getPartie(i);
		String nomPartie = p.getNom();
		// on recupere le mdp de la partie
		String mdp = p.getMdp();
		String reponse = "";
		// s'il y a un mot de passe
		if (!mdp.equals("")) {
			String message = "Mot de passe pour la partie " + nomPartie;
			// on boucle tant que le joueur a entre le bon mot de passe
			do {
				reponse = JOptionPane.showInputDialog(this, message);
			} while (!reponse.equals(mdp));
		}
		// on a le mot de passe s'il y en a un
		// p.rejoindrePartie(stub);
		service.rejoindrePartie(id, stub);
		service.recherchePartie(id).assignerCouleurJ2();
		service.initPartie(id);
	}


	/**
	 * Methode pour placer un pion a la colonne passee en parametre.
	 * @param colonne
	 * 			Colonne ou on veut placer le pion
	 * @return les coordonnees du pion place ou null si le pion n'a pas pu etre place (plus de place dans la colonne
	 */
	public int[] saisieColonne(int colonne, int partie) {
		int ligne = -1;
		int[] coord = new int[2];
		try {
			//on recherche la premiere ligne disponible dont la colonne est colonne et la partie est
			ligne = this.service.rechercheCaseLibre(partie, colonne);
			//si la ligne est != 1 (on a trouve une ligne valide) on ajoute le jeton aux coordonnees definies et on les renvoie
			if (ligne != -1) {
				this.service.ajouterJeton(partie,ligne, colonne, stub.getCouleur());
				coord[0] = ligne;
				coord[1] = colonne;
				return coord;
			}
			else{
				return null;
			}
		} catch (RemoteException e) {
			return null;
		}
	}

	/**
	 * Defini si le joueur connecte joue
	 * @param b true s'il joue, false sinon
	 */
	public void setJoue(boolean b) {
		this.joue =b;
	}
}
