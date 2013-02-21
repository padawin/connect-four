package client.ihm.events;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import client.Client;
import client.Globals;

public class EnterKeyConnexion implements KeyListener {


	private Client client;
	private JTextField host;
	private JTextField port;
	private JTextField login;
	private JFrame fenetre;
	private JLabel message;
	private JPasswordField pass;

	public EnterKeyConnexion(Client client, JTextField host, JTextField port,
			JTextField login, JFrame fenetre, JLabel message,
			JPasswordField pass) {
		super();
		this.client = client;
		this.host = host;
		this.port = port;
		this.login = login;
		this.fenetre = fenetre;
		this.message = message;
		this.pass = pass;
	}
	
	public void keyPressed(KeyEvent arg0) {}

	public void keyReleased(KeyEvent evt) {
		char car = evt.getKeyChar();
		if(car == '\n'){
			String result_connect = client.connexion(host.getText(),port.getText(), login.getText(),new String(pass.getPassword()));
			if (result_connect.equals(Globals.CONNEXION_OK)) {
				fenetre.dispose();
			} else {
				message.setText(result_connect);
			}
		}
	}

	public void keyTyped(KeyEvent arg0) {}

}
