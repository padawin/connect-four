package client;

public class Globals {

	/**
	 * Classe cote client contenant differentes constantes de l'application
	 */
	//constantes de connexion
	/**
	 * port par defaut
	 */
	public static final String DEFAULT_PORT = "1234";
	/**
	 * host par defaut
	 */
	public static final String DEFAULT_HOST = "127.0.0.1";
	
	//constantes des codes d'erreur
	/**
	 * si la connexion est effectuee
	 */
	public static final String CONNEXION_OK = "Connecte";
	/**
	 * si l'annuaire a ete trouve
	 */
	public static final String ANNUAIRE_TROUVE = "Annuaire trouve";
	/**
	 * Si le serveur est indisponible 
	 */
	public static final String ERROR_ANNUAIRE_CONNEXION = "Connexion au serveur impossible";
	/**
	 * Si l'utilisateur a saisi un mauvais couple login/mot de passe
	 */
	public static final String ERROR_AUTENTIFICATION = "Identifiant ou mot de passe incorrect";
	/**
	 * Si le login que l'utilisateur veut utiliser pour creer un compte est deja utilise
	 */
	public static final String ERROR_CONNEXION_MULTIPLE = "Compte deja utilise.";
	
	/**
	 * Si le joueur veut creer un compte et que la base de donnees est inaccessible
	 */
	public static final String ERROR_ACCESS_DB = "Impossible de se connecter a la base";
	
	/**
	 * Quand un joueur a cree une partie et qu'il attend un adversaires
	 */
	public static String ATTENTE_JOUEUR = "En attente d'un joueur..";
	
	/**
	 * Si l'utilisateur essaye de creer un compte et qu'il n'a pas rempli tout les champs
	 */
	public static String ERROR_MISSING_FIELDS = "Tous les champs doivent etre remplis";
	
	/**
	 * Si le login saisi pour creer un compte existe deja
	 */
	public static String ERROR_DUPLICATE_LOGIN = "Le login '%s' est deja  utilise.";
	
	/**
	 * Si le compte a ete correctement cree
	 */
	public static String CREATION_COMPTE_OK = "Utilisateur cree avec succes";
 }
