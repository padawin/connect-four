package server;

public class Globals {

	/**
	 * Si un deuxieme serveur est lance
	 */
	public static final String DUPLICATE_HOST = "Un seul serveur peut etre lance a la fois";


	/**
	 * Port par defaut.
	 */
	public static String DEFAULT_PORT = "1234";
	
	/**
	 * Adresse par defaut du webservice
	 */
	public static String DEFAULT_WS_HOST = "localhost:8080";
	//	public static String DEFAULT_WS_HOST = "192.168.15.130:8080";

	/**
	 * Message quand on lance le serveur
	 */
	public static String DEMARRAGE_HOST = "Demarrage du serveur";
	
	/**
	 * Message quand le serveur tente de se connecter au webservice
	 */
	public static String CONNEXION_WS = "Connexion au webservice...";
	
	/**
	 * Message quand le serveur est demarre
	 */
	public static String HOST_OK = "Serveur demarre";
	
	/**
	 * Message si le service est inaccessible
	 */
	public static String UNAVAILABLE_WS = "Service inaccessible";
	
	/**
	 * Message si le service est trouve
	 */
	public static final String AVAILABLE_WS = "Service trouve";
	
	/**
	 * Message lors de la creation du registre
	 */
	public static final String CREATE_REGISTRY = "Creation du registre";

	/**
	 * Message lors de l'abonnement
	 */
	public static final String ABONNEMENT = "Abonnement a l'annuaire";
}
