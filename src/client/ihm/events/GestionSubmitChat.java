package client.ihm.events;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JTextField;

import server.Serv;


/**
 * Classe de l'action pour appuyer sur le bouton d'envoi du message dans le chat.
 * 
 * @author Carine Kong, Ghislain Rodrigues, Rony Dray
 * @version 1
 * @see EnterKeyChat
 */
public class GestionSubmitChat implements ActionListener {
	
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
    public GestionSubmitChat(String name,JTextField input, Serv service) {
		in = input;
		this.service = service;
		this.name = name;
	}

    /**
	 * Methode a effectuer quand le bouton d'envoi du message est presse.
	 * Envoie le message et le contenu de l'input est reinitialise
	 */
	public void actionPerformed(ActionEvent contexte) {
        try {
			service.nouveauMessage(name+" dit : "+in.getText());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        in.setText("");
    }
}
