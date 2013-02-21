package fr.iut.tc6.services;

import fr.iut.tc6.services.JeuServiceStub.Joueur;


/**
 * Sauvegarde des r&eacute;sultats des parties et statistiques des joueurs.
 *
 * @author kongc
 */
public interface IJeuService {
    /**
     * Donne l'identifiant du joueur ayant un login donn&eacute;.
     *
     * @param login String le login
     * @return l'identifiant du joueur (-1 si joueur non trouv&eacute;).
     */
    public int getIdJoueurByLogin(String login);
    
    /**
     * Donne le joueur ayant l'id donn&eacute;e.
     * @param id
     * @return un Joueur
     */
    public Joueur getJoueurById(int id);
    
    /**
     * Donne le joueur ayant le login donn&eacute;.
     * @param login
     * @return un Joueur
     */
    public Joueur getJoueurByLogin(String login);

    /***
     * Indique si le couple login/password est correct.
     *
     * @param login
     *            le login
     * @param password
     *            le mot de passe
     * @return un boolean indiquant si le couple login/password est correct.
     */
    public boolean authenticate(String login, String password);

    /**
     * Ins&egrave;re un joueur en base de donn&eacute;e.
     *
     * @param login
     *            le login du joueur
     * @param password
     *            le mot de passe du joueur
     * @return un int de l'identifiant du joueur cr&eacute;&eacute; (0 si echec
     *         de la cr&eacute;ation).
     */
    public int createJoueur(String login, String password);

    /**
     * Ins&egrave;re un joueur en base de donn&eacute;e.
     *
     * @param nom
     *            le nom du joueur
     * @param prenom
     *            le pr&eacute;nom du joueur
     * @param login
     *            le login du joueur
     * @param password
     *            le mot de passe du joueur
     * @return un int de l'identifiant du joueur cr&eacute;&eacute; (0 si echec
     *         de la cr&eacute;ation).
     */
    public int createJoueurFull(String nom, String prenom, String login,
        String password);

    /**
     * Ins&egrave;re un joueur en base de donn&eacute;e.
     *
     * @param nom
     *            le nom du joueur
     * @param prenom
     *            le pr&eacute;nom du joueur
     * @param login
     *            le login du joueur
     * @param password
     *            le mot de passe du joueur
     * @return le Joueur cr&eacute;&eacute; (null si echec
     *         de la cr&eacute;ation).
     */
    public Joueur createJoueurFullObjet(String nom, String prenom, String login,
        String password);

    /**
     * Ins&egrave;re une partie et son participant en base de donn&eacute;es.
     *
     * @param idJoueur
     *            l'identifiant du joueur qui cr&eacute;&eacute; la partie
     * @param libelle
     *            le libell&eacute; de la partie
     * @param password
     *            le mot de passe (null si la partie est publique)
     * @return un int de l'identifiant de la partie c&eacute;&eacute; (0 si
     *         echec de la cr&eacute;ation).
     */
    public int createPartie(int idJoueur, String libelle, String password);

    /**
     * Ajoute un participant &agrave; une partie.
     *
     * @param idJoueur
     *            l'identifiant du joueur qui rejoint la partie
     * @param idPartie
     *            l'identifiant de la partie
     * @return un boolean indiquant si le joueur a bien &eacute;t&eacute;
     *         ajout&eacute; &agrave; la partie.
     */
    public boolean rejoindrePartie(int idJoueur, int idPartie);

    /**
     * D&eacute;clare un joueur comme vainqueur de la partie. (Il faut
     * d&eacute;clarer les autres joueurs comme perdant en utilisant la
     * m&eacute;thode <code>declarerPerdant</code> et d&eacute;clarer la partie
     * termin&eacute;, car ce n'est pas fait automatiquement)
     *
     * @param idJoueur
     *            int
     * @param idPartie
     *            int
     * @return boolean
     * @deprecated utiliser sauvegardeResultat &agrave; la place
     */
    public boolean declarerVainqueur(int idJoueur, int idPartie);

    /**
     * D&eacute;clare un joueur comme perdant de la partie. (Il faut
     * d&eacute;clarer les autres joueurs comme vainqueur/perdant en utilisant
     * la m&eacute;thode <code>declarerVainqueur</code> et d&eacute;clarer la
     * partie termin&eacute;, car ce n'est pas fait automatiquement)
     *
     * @param idJoueur
     *            int
     * @param idPartie
     *            int
     * @return boolean
     * @deprecated utiliser sauvegardeResultat &agrave; la place
     */
    public boolean declarerPerdant(int idJoueur, int idPartie);

    /**
     * D&eacute;clare match nul, pour tous les participants de la partie.
     *
     * @param idPartie
     * @return un boolean indiquant si les r&eacute;sultats ont bien
     *         &eacute;t&eacute; enregistr&eacute;s.
     */
    public boolean declarerNul(int idPartie);

    /**
     * Enregistre le r&eacute;sultat d'une partie &agrave; 2 joueurs : avec un
     * vainqueur et un perdant.
     *
     * @param idPartie
     *            int
     * @param idVainqueur
     *            int
     * @param idPerdant
     *            int
     * @return un boolean indiquant si les r&eacute;sultats ont bien
     *         &eacute;t&eacute; enregistr&eacute;s.
     */
    public boolean sauvegardeResultat(int idPartie, int idVainqueur,
        int idPerdant);

    /**
     * Nombre de victoires pour un joueur.
     *
     * @param idJoueur
     *            int
     * @return int
     */
    public int getStatVictoireByJoueur(int idJoueur);

    /**
     * Nombre de d&eacute;faites pour un joueur.
     *
     * @param idJoueur
     *            int
     * @return int
     */
    public int getStatDefaiteByJoueur(int idJoueur);

    /**
     * Nombre de parties d&eacute;clar&eacute;es nulles pour un joueur.
     *
     * @param idJoueur
     *            int
     * @return int
     */
    public int getStatMatchNulByJoueur(int idJoueur);

    /**
     * Nombre de parties jou&eacute;es pour un joueur.
     *
     * @param idJoueur
     *            int
     * @return int
     */
    public int getStatPartiesByJoueur(int idJoueur);

    /**
     * Indique si le login est d&eacute;j&agrave; utilis&eacute; en base
     *
     * @param login
     * @return boolean indique si le login existe.
     */
    public boolean checkLoginExist(String login);

    /**
     * Indique si le webservice est accessible
     * @return true
     */
    public boolean estAccessible();
}
