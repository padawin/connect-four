package server;

import java.rmi.RemoteException;

import fr.iut.tc6.services.IJeuService;
import fr.iut.tc6.services.JeuServiceStub;
import fr.iut.tc6.services.JeuServiceStub.Joueur;

public class ClientWebService implements IJeuService {
	private JeuServiceStub service = null;

	public ClientWebService() {
		try {
			service = new JeuServiceStub("http://"+Globals.DEFAULT_WS_HOST+"/axis2/services/JeuService");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.iut.tc6.services.IJeuService#getIdJoueurByLogin(java.lang.String)
	 */
	public int getIdJoueurByLogin(String login) {
		JeuServiceStub.GetIdJoueurByLogin requete;
		JeuServiceStub.GetIdJoueurByLoginResponse reponse;

		requete = new JeuServiceStub.GetIdJoueurByLogin();
		reponse = new JeuServiceStub.GetIdJoueurByLoginResponse();

		int res = -1;

		if (service != null) {
			try {
				requete.setLogin(login);

				reponse = service.getIdJoueurByLogin(requete);

				res = reponse.get_return();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.iut.tc6.services.IJeuService#authenticate(java.lang.String,
	 * java.lang.String)
	 */
	public boolean authenticate(String login, String password) {
		JeuServiceStub.Authenticate requete;
		JeuServiceStub.AuthenticateResponse reponse;

		requete = new JeuServiceStub.Authenticate();
		reponse = new JeuServiceStub.AuthenticateResponse();

		boolean res = false;

		if (service != null) {
			try {
				requete.setLogin(login);
				requete.setPassword(password);

				reponse = service.authenticate(requete);

				res = reponse.get_return();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.iut.tc6.services.IJeuService#createJoueur(java.lang.String,
	 * java.lang.String)
	 */
	public int createJoueur(String login, String password) {
		JeuServiceStub.CreateJoueur requete;
		JeuServiceStub.CreateJoueurResponse reponse;

		requete = new JeuServiceStub.CreateJoueur();
		reponse = new JeuServiceStub.CreateJoueurResponse();

		int generatedId = 0;

		if (service != null) {
			try {
				requete.setLogin(login);
				requete.setPassword(password);

				reponse = service.createJoueur(requete);

				generatedId = reponse.get_return();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return generatedId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.iut.tc6.services.IJeuService#createJoueurFull(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	public int createJoueurFull(String nom, String prenom, String login,
			String password) {
		JeuServiceStub.CreateJoueurFull requete;
		JeuServiceStub.CreateJoueurFullResponse reponse;

		requete = new JeuServiceStub.CreateJoueurFull();
		reponse = new JeuServiceStub.CreateJoueurFullResponse();

		int generatedId = 0;

		if (service != null) {
			try {
				requete.setNom(nom);
				requete.setPrenom(prenom);
				requete.setLogin(login);
				requete.setPassword(password);
				try{
					reponse = service.createJoueurFull(requete);
					generatedId = reponse.get_return();
				}catch(Exception e){
					generatedId = -1;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return generatedId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.iut.tc6.services.IJeuService#createPartie(fr.iut.tc6.entities.Joueur,
	 * java.lang.String, java.lang.String, int)
	 */
	public int createPartie(int idJoueur, String libelle, String password) {
		JeuServiceStub.CreatePartie requete;
		JeuServiceStub.CreatePartieResponse reponse;

		requete = new JeuServiceStub.CreatePartie();
		reponse = new JeuServiceStub.CreatePartieResponse();

		int generatedId = 0;

		if (service != null) {
			try {
				// Création de la partie & liaison joueur/partie
				requete.setIdJoueur(idJoueur);
				requete.setLibelle(libelle);
				requete.setPassword(password);

				reponse = service.createPartie(requete);

				generatedId = reponse.get_return();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return generatedId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.iut.tc6.services.IJeuService#rejoindrePartie(int, int)
	 */
	
	public boolean rejoindrePartie(int idJoueur, int idPartie) {
		JeuServiceStub.RejoindrePartie requete;
		JeuServiceStub.RejoindrePartieResponse reponse;

		requete = new JeuServiceStub.RejoindrePartie();
		reponse = new JeuServiceStub.RejoindrePartieResponse();

		boolean res = false;

		if (service != null) {
			try {
				// Création de la partie & liaison joueur/partie
				requete.setIdJoueur(idJoueur);
				requete.setIdPartie(idPartie);

				reponse = service.rejoindrePartie(requete);

				res = reponse.get_return();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.iut.tc6.services.IJeuService#declarerNul(int)
	 */
	
	public boolean declarerNul(int idPartie) {
		JeuServiceStub.DeclarerNul requete;
		JeuServiceStub.DeclarerNulResponse reponse;

		requete = new JeuServiceStub.DeclarerNul();
		reponse = new JeuServiceStub.DeclarerNulResponse();

		boolean res = false;

		if (service != null) {
			try {
				requete.setIdPartie(idPartie);

				reponse = service.declarerNul(requete);

				res = reponse.get_return();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.iut.tc6.services.IJeuService#declarerPerdant(int, int)
	 */
	
	public boolean declarerPerdant(int idJoueur, int idPartie) {
		JeuServiceStub.DeclarerPerdant requete;
		JeuServiceStub.DeclarerPerdantResponse reponse;

		requete = new JeuServiceStub.DeclarerPerdant();
		reponse = new JeuServiceStub.DeclarerPerdantResponse();

		boolean res = false;

		if (service != null) {
			try {
				requete.setIdJoueur(idJoueur);
				requete.setIdPartie(idPartie);

				reponse = service.declarerPerdant(requete);

				res = reponse.get_return();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.iut.tc6.services.IJeuService#declarerVainqueur(int, int)
	 */
	
	public boolean declarerVainqueur(int idJoueur, int idPartie) {
		JeuServiceStub.DeclarerVainqueur requete;
		JeuServiceStub.DeclarerVainqueurResponse reponse;

		requete = new JeuServiceStub.DeclarerVainqueur();
		reponse = new JeuServiceStub.DeclarerVainqueurResponse();

		boolean res = false;

		if (service != null) {
			try {
				requete.setIdJoueur(idJoueur);
				requete.setIdPartie(idPartie);

				reponse = service.declarerVainqueur(requete);

				res = reponse.get_return();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.iut.tc6.services.IJeuService#sauvegardeResultat(int, int, int)
	 */
	
	public boolean sauvegardeResultat(int idPartie, int idVainqueur,
			int idPerdant) {
		JeuServiceStub.SauvegardeResultat requete;
		JeuServiceStub.SauvegardeResultatResponse reponse;

		requete = new JeuServiceStub.SauvegardeResultat();
		reponse = new JeuServiceStub.SauvegardeResultatResponse();

		boolean res = false;

		if (service != null) {
			try {
				requete.setIdVainqueur(idVainqueur);
				requete.setIdPerdant(idPerdant);
				requete.setIdPartie(idPartie);

				reponse = service.sauvegardeResultat(requete);

				res = reponse.get_return();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.iut.tc6.services.IJeuService#getStatDefaiteByJoueur(int)
	 */
	
	public int getStatDefaiteByJoueur(int idJoueur) {
		JeuServiceStub.GetStatDefaiteByJoueur requete;
		JeuServiceStub.GetStatDefaiteByJoueurResponse reponse;

		requete = new JeuServiceStub.GetStatDefaiteByJoueur();
		reponse = new JeuServiceStub.GetStatDefaiteByJoueurResponse();

		int res = 0;

		if (service != null) {
			try {
				requete.setIdJoueur(idJoueur);

				reponse = service.getStatDefaiteByJoueur(requete);

				res = reponse.get_return();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.iut.tc6.services.IJeuService#getStatMatchNulByJoueur(int)
	 */
	
	public int getStatMatchNulByJoueur(int idJoueur) {
		JeuServiceStub.GetStatMatchNulByJoueur requete;
		JeuServiceStub.GetStatMatchNulByJoueurResponse reponse;

		requete = new JeuServiceStub.GetStatMatchNulByJoueur();
		reponse = new JeuServiceStub.GetStatMatchNulByJoueurResponse();

		int res = 0;

		if (service != null) {
			try {
				requete.setIdJoueur(idJoueur);

				reponse = service.getStatMatchNulByJoueur(requete);

				res = reponse.get_return();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.iut.tc6.services.IJeuService#getStatPartiesByJoueur(int)
	 */
	
	public int getStatPartiesByJoueur(int idJoueur) {
		JeuServiceStub.GetStatPartiesByJoueur requete;
		JeuServiceStub.GetStatPartiesByJoueurResponse reponse;

		requete = new JeuServiceStub.GetStatPartiesByJoueur();
		reponse = new JeuServiceStub.GetStatPartiesByJoueurResponse();

		int res = 0;

		if (service != null) {
			try {
				requete.setIdJoueur(idJoueur);

				reponse = service.getStatPartiesByJoueur(requete);

				res = reponse.get_return();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.iut.tc6.services.IJeuService#getStatVictoireByJoueur(int)
	 */
	
	public int getStatVictoireByJoueur(int idJoueur) {
		JeuServiceStub.GetStatVictoireByJoueur requete;
		JeuServiceStub.GetStatVictoireByJoueurResponse reponse;

		requete = new JeuServiceStub.GetStatVictoireByJoueur();
		reponse = new JeuServiceStub.GetStatVictoireByJoueurResponse();

		int res = 0;

		if (service != null) {
			try {
				requete.setIdJoueur(idJoueur);

				reponse = service.getStatVictoireByJoueur(requete);

				res = reponse.get_return();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.iut.tc6.services.IJeuService#checkLoginExist(java.lang.String)
	 */
	
	public boolean checkLoginExist(String login) {
		JeuServiceStub.CheckLoginExist requete;
		JeuServiceStub.CheckLoginExistResponse reponse;

		requete = new JeuServiceStub.CheckLoginExist();
		reponse = new JeuServiceStub.CheckLoginExistResponse();

		boolean res = false;

		if (service != null) {
			try {
				requete.setLogin(login);

				reponse = service.checkLoginExist(requete);

				res = reponse.get_return();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return res;
	}

	
	/* (non-Javadoc)
	 * @see fr.iut.tc6.services.IJeuService#estAccessible()
	 */
	
	public boolean estAccessible() {
		JeuServiceStub.EstAccessibleResponse reponse;

		reponse = new JeuServiceStub.EstAccessibleResponse();

		boolean res = false;

		if (service != null) {
			try {

				reponse = service.estAccessible();

				res = reponse.get_return();
			}catch(RemoteException e){
			}
		}

		return res;
	}
	
	/* (non-Javadoc)
	 * @see fr.iut.tc6.services.IJeuService#createJoueurFullObjet(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	
	public Joueur createJoueurFullObjet(String nom, String prenom,
			String login, String password) {
		JeuServiceStub.CreateJoueurFullObjet requete;
		JeuServiceStub.CreateJoueurFullObjetResponse reponse;

		requete = new JeuServiceStub.CreateJoueurFullObjet();
		reponse = new JeuServiceStub.CreateJoueurFullObjetResponse();

		Joueur joueur = new JeuServiceStub.Joueur();

		if (service != null) {
			try {
				requete.setNom(nom);
				requete.setPrenom(prenom);
				requete.setLogin(login);
				requete.setPassword(password);

				reponse = service.createJoueurFullObjet(requete);

				joueur = reponse.get_return();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return joueur;
	}

	/* (non-Javadoc)
	 * @see fr.iut.tc6.services.IJeuService#getJoueurById(int)
	 */
	
	public Joueur getJoueurById(int id) {
		JeuServiceStub.GetJoueurById requete;
		JeuServiceStub.GetJoueurByIdResponse reponse;

		requete = new JeuServiceStub.GetJoueurById();
		reponse = new JeuServiceStub.GetJoueurByIdResponse();

		Joueur joueur = new JeuServiceStub.Joueur();

		if (service != null) {
			try {
				requete.setId(id);

				reponse = service.getJoueurById(requete);

				joueur = reponse.get_return();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return joueur;
	}

	/* (non-Javadoc)
	 * @see fr.iut.tc6.services.IJeuService#getJoueurByLogin(java.lang.String)
	 */
	
	public Joueur getJoueurByLogin(String login) {
		JeuServiceStub.GetJoueurByLogin requete;
		JeuServiceStub.GetJoueurByLoginResponse reponse;

		requete = new JeuServiceStub.GetJoueurByLogin();
		reponse = new JeuServiceStub.GetJoueurByLoginResponse();

		Joueur joueur = new JeuServiceStub.Joueur();

		if (service != null) {
			try {
				requete.setLogin(login);

				reponse = service.getJoueurByLogin(requete);

				joueur = reponse.get_return();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return joueur;
	}

}
