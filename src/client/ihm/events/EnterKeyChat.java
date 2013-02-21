package client.ihm.events;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.RemoteException;

import javax.swing.JTextField;

import server.Serv;


/**
 * Classe de l'action pour appuyer sur Entree dans le chat.
 * Quand l'utilisateur appuie sur "Entree", le message tape doit etre envoye
 * 
 * @author Carine Kong, Ghislain Rodrigues, Rony Dray
 * @version 1
 * @see GestionSubmitChat
 */
public class EnterKeyChat implements KeyListener  {

	private JTextField in;
	private Serv service;
	private String name;
	
	/**
	 * Constructeur de l'action.
	 * @param name
	 * 			Nom de l'utilisateur qui est connecte
	 * @param input
	 * 			JTextField ou l'utilisateur tape le message
	 * @param service
	 * 			Service auquel l'application est connectee
	 */
    public EnterKeyChat(String name,JTextField input, Serv service) {
		this.in = input;
		this.service = service;
		this.name = name;
	}
	
    /**
     * 
     */
	public void keyPressed(KeyEvent arg0) {}

	/**
	 * Methode a effectuer quand une touche est relachee.
	 * Si la touche tapee est "entree" (\n) alors le message est envoye et le contenu de l'input est reinitialise
	 */
	public void keyReleased(KeyEvent evt) {
		char car = evt.getKeyChar();
		if(car == '\n'){
			try {
				service.nouveauMessage(name+" dit : "+in.getText());
			} catch (RemoteException e) {
				e.printStackTrace();
			}
	        in.setText("");
		}
		
	}

	public void keyTyped(KeyEvent arg0) {}

}
