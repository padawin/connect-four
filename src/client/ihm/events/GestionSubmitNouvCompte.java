package client.ihm.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import server.ClientWebService;

import client.Globals;

/**
 * Classe de l'evenement de creation de compte d'un nouveau joueur
 * 
 * @author Carine Kong, Ghislain Rodrigues, Rony Dray
 * @version 1
 */
public class GestionSubmitNouvCompte implements ActionListener {

	private JTextField nomIN;
	private JTextField prenomIN;
	private JTextField loginIN2;
	private JPasswordField passIN2;
	private JLabel message2;
	private ClientWebService wsClient;


	public GestionSubmitNouvCompte(JTextField nomIN, JTextField prenomIN,
			JTextField loginIN2, JPasswordField passIN2, JLabel message2,
			ClientWebService wsClient) {
		super();
		this.nomIN = nomIN;
		this.prenomIN = prenomIN;
		this.loginIN2 = loginIN2;
		this.passIN2 = passIN2;
		this.message2 = message2;
		this.wsClient = wsClient;
	}

	
	/**
	 * si tous les champs ne sont pas remplis une erreur est affichee, sinon l'application tente d'inscrire le joueur.
	 * Si l'inscription renvoie -1, la base de donnees n'est pas accessible,
	 * Si l'inscription renvoie 0, le login selectionne existe deja
	 * Si l'inscription renvoie autre chose, l'inscription a ete effectuee et la valeur retournee est l'id de l'enregistrement
	 */
	public void actionPerformed(ActionEvent arg0) {
		if(nomIN.getText().equals("") || prenomIN.getText().equals("") || loginIN2.getText().equals("") || new String(passIN2.getPassword()).equals("")){
			message2.setText(Globals.ERROR_MISSING_FIELDS);
		}
		else{
			int i = -1;
			i = wsClient.createJoueurFull(nomIN.getText(), prenomIN.getText(), loginIN2.getText(), new String(passIN2.getPassword()));
			if(i == -1){
				message2.setText(Globals.ERROR_ACCESS_DB);
			}
			else if (i == 0) {
				message2.setText(String.format(Globals.ERROR_DUPLICATE_LOGIN,loginIN2.getText()));
			} else {
				message2.setText(Globals.CREATION_COMPTE_OK);
			}
			
		}
	}

}
