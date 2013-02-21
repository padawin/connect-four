package client.ihm.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import client.Client;
import client.Globals;


/**
 * Classe de l'evenement de connexion
 * 
 * @author Carine Kong, Ghislain Rodrigues, Rony Dray
 * @version 1
 */
public class GestionSubmitConnexion implements ActionListener {


	private Client client;
	private JTextField host;
	private JTextField port;
	private JTextField login;
	private JFrame fenetre;
	private JLabel message;
	private JPasswordField pass;

	
	public GestionSubmitConnexion(Client client, JTextField hostIN, JTextField portIN, JTextField loginIN, JFrame fenetre, JLabel message, JPasswordField passIN) {
		super();
		this.client = client;
		this.host = hostIN;
		this.port = portIN;
		this.login = loginIN;
		this.fenetre = fenetre;
		this.message = message;
		this.pass = passIN;
	}

	/**
	 * Methode qui tente une connexion aupres du serveur
	 */
	public void actionPerformed(ActionEvent arg0) {
		String result_connect = client.connexion(host.getText(),port.getText(), login.getText(),new String(pass.getPassword()));
		if (result_connect.equals(Globals.CONNEXION_OK)) {
			fenetre.dispose();
		} else {
			message.setText(result_connect);
		}
	}

}
