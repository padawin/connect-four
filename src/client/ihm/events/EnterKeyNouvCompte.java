package client.ihm.events;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import server.ClientWebService;

import client.Globals;

public class EnterKeyNouvCompte implements KeyListener {



	private JTextField nomIN;
	private JTextField prenomIN;
	private JTextField loginIN2;
	private JPasswordField passIN2;
	private JLabel message2;
	private ClientWebService wsClient;

	public EnterKeyNouvCompte(JTextField nomIN, JTextField prenomIN,
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
	
	public void keyPressed(KeyEvent arg0) {}

	public void keyReleased(KeyEvent evt) {
		char car = evt.getKeyChar();
		if(car == '\n'){
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

	public void keyTyped(KeyEvent arg0) {}

}
