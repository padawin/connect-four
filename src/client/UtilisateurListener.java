package client;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import client.ihm.Chat;


import server.Partie;

/**
 * Interface de la fenetre principale de l'application cliente
 * @author Carine Kong, Ghislain Rodrigues, Rony Dray
 * @version 1
 */
public interface UtilisateurListener extends Remote {

	/**
	 * Getter du login du client de la classe
	 */
	public String getName() throws RemoteException;

	/**
	 * Methode pour effacer la grille
	 */
	public void effacer() throws RemoteException;

	/**
	 * Methode pour attribuer une couleur au joueur
	 */
	public void setCouleur(String c) throws RemoteException;

	/**
	 * Methode pour mettre un pion a la case de coordonnees (colonne, ligne)
	 */
	public void affecterCase(int ligne, int colonne, boolean b) throws RemoteException;

	/**
	 * Methode pour ajouter un message au chat
	 */
	public void messageChanged(String message) throws RemoteException;

	/**
	 * Methode pour ajouter un message au log de jeu
	 */
	public void logChanged(String message) throws RemoteException;

	/**
	 * Getter de la couleur du client
	 */
	public char getCouleur() throws RemoteException;

	/**
	 * Methode pour activer le bouton de jeu du client
	 * 
	 * @throws RemoteException
	 */
	public void enableBoutJouer() throws RemoteException;

	/**
	 * Methode pour actualiser la liste des parties
	 */
	public void actualiserPartie(ArrayList<Partie> a) throws RemoteException;

	/**
	 * Getter du chat
	 * 
	 * @return le panel du chat de l'application
	 */
	public Chat getChat() throws RemoteException;

	/**
	 * Getter du client de la classe
	 */
	public Client getClient() throws RemoteException;

	/**
	 * Affiche la liste des clients dans la zone pr�vue � cet effet de la
	 * fenetre du serveur
	 * 
	 * @param liste
	 */
	public void getListeClients(ArrayList<UtilisateurListener> liste) throws RemoteException;

	/**
	 * Une fois la partie terminee, cette methode est appellee pour qui l'IHM de
	 * la partie enleve les boutons de jeu pour les remplacer par un bouton de
	 * retour a l'accueil
	 */
	public void partieFinie() throws RemoteException;

	/**
	 * Met a false le champ "joue" du client
	 */
	public void finJeu() throws RemoteException;

	/**
	 * 
	 * @return la partie a laquelle l'utilisateur participe
	 */
	public int getPartie() throws RemoteException;
	
	/**
	 * Methode pour actualiser la liste des statistiques du joueur connecte
	 * @throws RemoteException
	 */
	public void afficherStats() throws RemoteException;
	

	/**
	 * deconnecte le client
	 */
	public void deconnecter() throws RemoteException;
}
