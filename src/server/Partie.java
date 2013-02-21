package server;

import java.io.Serializable;
import java.rmi.RemoteException;

import client.UtilisateurListener;

public class Partie implements Serializable {

	private static final long serialVersionUID = 1L;
	private UtilisateurListener joueur1;
	private UtilisateurListener joueur2;
	private int num;
	private String mdp;
	private String name;
	private char[][] grille = new char[6][7];
	private char joueurQuiJoue = 'V';
	private int statuPartie = 0; // 0 en cours, 1 Terminee avec gagnant , 2 Terminee avec Match nul;

	/**
	 * Contructeur de la classe pour instancier une partie creee par joueur
	 * 
	 * @param i
	 * 			Numero de la partie
	 * @param joueur
	 * 			Createur de la partie
	 * @param nom
	 * 			Nom de la partie
	 * @param mdp
	 * 			Mot de passe de la partie
	 * @param num
	 * 			Numero de la partie
	 * @throws RemoteException
	 */
	public Partie(UtilisateurListener joueur, String nom, String mdp, int num) throws RemoteException {
		this.num = num;
		this.joueur1 = joueur;
		this.name = nom;
		this.mdp = mdp;
		this.joueur1.effacer();
		this.effacer();
	}
	
	/**
	 * Ajoute le joueur "joueur" a la partie
	 * @param joueur
	 * @throws RemoteException
	 */
	public void rejoindrePartie(UtilisateurListener joueur) throws RemoteException{
		this.joueur2 = joueur;
		this.joueur2.effacer();
		this.assignerCouleurJ2();		
		this.quiCommence();
	}
	
	/**
	 * 
	 * @throws RemoteException
	 */
	public void assignerCouleurJ2() throws RemoteException{
		if(this.joueur1.getCouleur() == 'R'){
			this.joueur2.setCouleur("Jaune");
		}else{
			this.joueur2.setCouleur("Rouge");
		}
	}
	
	/**
	 * retourne la couleur du joueur qui doit jouer
	 * @return un char 'R' si c'est au joueur Rouge et 'J' si c'est au joueur Jaune
	 */
	public char aQuiDeJouer() {
		return this.joueurQuiJoue;
	}
	
	/**
	 * Determine aleatoirement le joueur qui joue le premier
	 * @return le caractere du joueur qui doit commencer
	 */
	public char quiCommence(){
		char ret;
		if(Math.random() <= 0.5){
			this.joueurQuiJoue = 'R';
			ret = 'R';
		}else {
			this.joueurQuiJoue = 'J';
			ret = 'J';
		}
		return ret;
	}
	
	/**
	 * Changement de tour. Si "joueur" est le rouge, alors c'est au jaune de joueur et vice-versa
	 * @param joueur
	 */
	public void finiDeJouer(char joueur){
		if(joueur == 'R'){
			this.joueurQuiJoue = 'J';
		}else{
			this.joueurQuiJoue = 'R';
		}
	}
	
	/**
	 * Renvoie vrai si "joueur" est le joueur 1 ou le joueur 2
	 * @param joueur
	 * 			joueur dont la presence doit etre testee
	 * @return
	 */
	public boolean estPresent(UtilisateurListener joueur){
		if(this.joueur1.equals(joueur) || this.joueur2.equals(joueur)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Efface la grille de jeu
	 */
	public void effacer() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				grille[i][j] = 'V';
			}
		}
	}

	/**
	 * Ajoute un jeton de la couleur du joueur "joueur" a la case de coordonnees (colonne,ligne)
	 * @param ligne
	 * @param colonne
	 * @param joueur
	 * @throws RemoteException
	 */
	public void ajouterJeton(int ligne, int colonne, char joueur) throws RemoteException {
		if ((ligne >= 0) && (ligne < 6) && (colonne >= 0) && (colonne < 7)) {
			grille[ligne][colonne] = joueur;
			if(joueur == 'R'){
				joueur1.affecterCase(ligne, colonne, true);
				joueur2.affecterCase(ligne, colonne, true);
			}else{
				joueur1.affecterCase(ligne, colonne, false);
				joueur2.affecterCase(ligne, colonne, false);
			}
		}		
	}

	/**
	 * 
	 * @return true si la grille est pleine, false sinon
	 */
	public boolean estPleine() {
		boolean plein = true;
		for (int j = 0; j < 7; j++) {
			plein = plein && (grille[5][j] != 'V');
		}
		return plein;
	}

	/**
	 * Recherche une case ligne à la colonne passee en parametre.
	 * @param colonne
	 * @return la ligne de la case libre
	 */
	public int rechercheCaseLibre(int colonne) {
		int ligne = -1;
		if ((colonne >= 0) && (colonne <= 6)) {
			for (int i = 0; i < 6; i++) {
				if (grille[i][colonne] == 'V') {
					ligne = i;
					break;
				}
			}
		}
		return ligne;
	}

	/**
	 * Teste si le joueur passe en parametre a joue un coup gagnant aux coordonnees (colonne, ligne)
	 * @param ligne
	 * @param colonne
	 * @param joueur
	 * @return true si le joueur a gagne
	 */
	public boolean estGagnant(int ligne, int colonne, char joueur) {
		int n, i, j;

		/*
		 * Aligne sur une ligne ?
		 */
		n = 1;
		j = colonne + 1;
		while (j < 7) {
			if (grille[ligne][j++] == joueur) {
				n++;
			} else {
				break;
			}
		}

		j = colonne - 1;
		while (j >= 0) {
			if (grille[ligne][j--] == joueur) {
				n++;
			} else {
				break;
			}
		}

		if (n >= 4) {
			return true;
		}

		/*
		 * Aligne sur une colonne ?
		 */
		n = 1;
		i = ligne + 1;
		while (i < 6) {
			if (grille[i++][colonne] == joueur) {
				n++;
			} else {
				break;
			}
		}

		i = ligne - 1;
		while (i >= 0) {
			if (grille[i--][colonne] == joueur) {
				n++;
			} else {
				break;
			}
		}

		if (n >= 4) {
			return true;
		}

		/*
		 * Aligne sur une diagonale ?
		 */
		n = 1;
		i = ligne + 1;
		j = colonne + 1;
		while ((i < 6) && (j < 7)) {
			if (grille[i++][j++] == joueur) {
				n++;
			} else {
				break;
			}
		}

		i = ligne - 1;
		j = colonne - 1;
		while ((i >= 0) && (j >= 0)) {
			if (grille[i--][j--] == joueur) {
				n++;
			} else {
				break;
			}
		}

		if (n >= 4) {
			return true;
		}

		/*
		 * Aligne sur une diagonale opposee ?
		 */
		n = 1;
		i = ligne - 1;
		j = colonne + 1;
		while ((i >= 0) && (j < 7)) {
			if (grille[i--][j++] == joueur) {
				n++;
			} else {
				break;
			}
		}

		i = ligne + 1;
		j = colonne - 1;
		while ((i < 6) && (j >= 0)) {
			if (grille[i++][j--] == joueur) {
				n++;
			} else {
				break;
			}
		}

		if (n >= 4) {
			return true;
		}

		return false;
	}
	
	/**
	 * 
	 * @return true si la partie n'a pas de deuxieme joueur
	 */
	public boolean enAttente(){
		return this.joueur2 == null;
	}
	
	/**
	 * 
	 * @return le nom de la partie
	 */
	public String getNom(){
		return name;
	}
	
	/**
	 * 
	 * @return true si la partie a un mot de passe
	 */
	public boolean protegee(){
		return !mdp.equals("");
	}

	/**
	 * 
	 * @return le nom du joueur 1
	 */
	public String getJ1Name() {
		String tmp = "";
		try {
			tmp = joueur1.getName();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return tmp;
	}

	/**
	 * 
	 * @return le nom du joueur 2
	 */
	public String getJ2Name() {
		String tmp = "";
		try {
			tmp = joueur2.getName();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return tmp;
	}
	
	/**
	 * 
	 * @return le joueur 1
	 */
	public UtilisateurListener getJ1(){
		return joueur1;
	}
	
	/**
	 * 
	 * @return le joueur 2
	 */
	public UtilisateurListener getJ2(){
		return joueur2;
	}

	/**
	 * 
	 * @return le numero de la partie
	 */
	public int getNum() {
		return num;
	}

	/**
	 * 
	 * @return le mot de passe de la partie
	 */
	public String getMdp() {
		return mdp;
	}
	
	/**
	 * Indique si une partie est terminee
	 * @return 0 en cours, 1 gagnee par une joueur, 2 match nul
	 */
	public int estTerminee() {
		return statuPartie;
	}
}
