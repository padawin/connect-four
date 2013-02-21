package server;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import client.UtilisateurListener;

/**
 * Interface du service
 * @author Carine Kong, Ghislain Rodrigues, Rony Dray
 * @version 1
 */
public interface Serv extends Remote {

	/**
	 * Ajoute l'utilisateur passe en parametre ï¿½ la liste des utilisateurs
	 * connectes
	 * 
	 * @param listener
	 */
	public void addUtilisateur(UtilisateurListener listener) throws RemoteException;

	/**
	 * Supprime l'utilisateur passe en parametre de la liste des utilisateurs
	 * connectes. Toutes les parties oï¿½ l'utilisateur jouait sont egalement
	 * supprimees. Vu que des parties sont supprimees, la liste des parties
	 * disponibles doit etre actualisee chez tous les clients
	 * 
	 * @param listener
	 */
	public void removeUtilisateur(UtilisateurListener listener, boolean b) throws RemoteException;
	
	/**
	 * Ajout d'une partie a la liste des partie. Envoi d'un message sur le chat
	 * pour avertir de la creation d'une nouvelle partie. La liste des parties
	 * disponible est actualisee chez tous les clients
	 * 
	 * @param client
	 *            nom du client qui cree la partie
	 * @param nomPartie
	 *            Nom de la partie creee
	 * @param mdpPartie
	 *            Mot de passe de la partie
	 */
	public int addPartie(UtilisateurListener client, String nom, String mdp) throws RemoteException;

	/**
	 * Methode pour que l'utilisateur stub rejoigne la partie d'id idPartie
	 * 
	 * @param idPartie
	 *            partie voulue
	 * @param stub
	 *            utilisateur qui veut rejoindre la partie
	 */
	public void rejoindrePartie(int i, UtilisateurListener stub) throws RemoteException;

	/**
	 * Supprime la partie p de la liste des parties et actualise la liste des
	 * parties disponibles.
	 * 
	 * @param p
	 *            Partie à supprimer
	 */
	public void removePartie(Partie p) throws RemoteException;

	/**
	 * Methode qui renvoie la liste des parties en attente. CAD les parties qui
	 * n'ont pas de deuxieme joueur
	 */
	public ArrayList<Partie> getListeParties() throws RemoteException;

	/**
	 * Methode pour afficher la liste des clients sur le serveur
	 * 
	 * @param vueServ
	 */
	public void afficherListeClients(VueServeur vueServ) throws RemoteException;

	/**
	 * Actualise la liste des clients de l'application cote client
	 * 
	 */
	public void refreshListUtilisateurListener() throws RemoteException;

	/**
	 * Methode pour verifier le couple login/mdp entres pour se connecter a
	 * l'application
	 * 
	 * @param login
	 *            Login saisi
	 * @param pass
	 *            Mot de passe saisi
	 * @return -3 si quelqu'un est deja connecte avec ce compte, -2 si l'utilisateur est un invite, -1 si le couple login/pass est incorrect et l'id du joueur sinon
	 */
	public int checkID(String login,String pass) throws RemoteException;

	/**
	 * Renvoie le dernier message du chat
	 */
	public String getMessage() throws RemoteException;

	/**
	 * Ajoute un message au chat
	 * 
	 * @param msg
	 *            nouveau message
	 */
	public void nouveauMessage(String msg) throws RemoteException;

	/**
	 * Renvoie la partie dont le numaro est ind
	 * 
	 * @param ind
	 */
	public int getIndexPartie(int idPartie) throws RemoteException;

	/**
	 * Recherche la partie dont le numero est id.
	 * 
	 * @param id
	 *            id de la partie a rechercher
	 * @return la partie si elle existe, null sinon
	 */
	public Partie recherchePartie(int indexPartie) throws RemoteException;

	/**
	 * Met a jour la grille en ajoutant un jeton
	 * 
	 * @param partie
	 *            partie en question
	 * @param ligne
	 *            ligne ou on doit ajouter le jeton
	 * @param colonne
	 *            colonne ou on doit ajouter le pion
	 * @param joueur
	 *            joueur qui a joue
	 */
	public void ajouterJeton(int idPartie, int ligne, int colonne, char couleur) throws RemoteException;

	/**
	 * retourne vrai si la grille est pleine
	 * 
	 * @param partie
	 *            partie de la grille
	 * 
	 */
	public boolean estPleine(int idPartie) throws RemoteException;

	/**
	 * Indique si une partie est terminee
	 * 
	 * @param integer
	 *            id de la partie
	 * @return 0 non terminee, 1 gagnee par un joueur, 2 match nul
	 */
	public int estTerminee(int idPartie) throws RemoteException;

	/**
	 * recherche la premiere case libre pour une colonne donnee
	 * 
	 * @param colonne
	 *            numero de colonne
	 * @param partie
	 * 
	 * @return numero de ligne entre 0 et 5 ; -1 si la colonne est pleine
	 */
	public int rechercheCaseLibre(int idPartie, int colonne) throws RemoteException;

	/**
	 * Regarde a partir d'une position si le coup aux coordonnees
	 * (colonne,ligne) du joueur "joueur" est gagnant pour la partie "partie"
	 * 
	 * @param partie
	 * @param ligne
	 * @param colonne
	 * @param joueur
	 * 
	 * @return vrai si le coup est gagnant
	 * 
	 */
	public boolean estGagnant(int idPartie, int ligne, int colonne, char couleur) throws RemoteException;

	/**
	 * Indique a l'autre joueur que l'on a fini de jouer.
	 * 
	 * @param integer
	 *            partie
	 * @param char joueur qui vient de jouer
	 */
	public void finiDeJouer(int idPartie, char couleur) throws RemoteException;

	/**
	 * initialisation de la partie id
	 * 
	 * @param id
	 *            id de la partie qui debute
	 */
	public void initPartie(int i) throws RemoteException;

	/**
	 * Methode pour gerer la fin d'un tour de la partie "partie" avec un coup
	 * place aux coordonnees (colonne, ligne) par le joueur "couleur"
	 * 
	 */
	public void finTour(int partie, int i, int j, char c) throws RemoteException;
	
}
