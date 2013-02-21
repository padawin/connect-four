package client.ihm;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import client.Client;
import client.ihm.events.EnterKeyChat;
import client.ihm.events.GestionSubmitChat;
import server.Serv;

/**
 * @author Ghislain
 *
 */
public class Chat extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private JLabel utilLab;
	private JTextArea affichage;
	private JList listeUtilisateurs;

	private JTextField input;
	private JButton submit;

	/**
	 * Constructeur du Chat. Cree le JPanel qui contient l'affichage des messages, l'entree de texte pour saisir le message, le bouton d'envoi et la liste des clients
	 * @param service 
	 * 			Service auquel l'application se connecte
	 * @param client
	 * 			Client qui est connecte
	 */
	public Chat(Serv service, final Client client) {

		this.name = client.getLogin();

		this.setLayout(new BorderLayout());
		this.affichage = new JTextArea(16,26);
		this.utilLab = new JLabel();
		this.utilLab.setText("Joueurs connectes");
		this.listeUtilisateurs = new JList();
		this.input = new JTextField(18);
		this.affichage.setEditable(false);
		JScrollPane scrollPaneAff = new JScrollPane();
		scrollPaneAff.setViewportView(affichage);
		JScrollPane scrollPaneUtil = new JScrollPane();
		scrollPaneUtil.setViewportView(listeUtilisateurs);

		this.submit = new JButton("Envoyer");

		// création de l'évenement pour envoyer le msg
		GestionSubmitChat gestionSubmit = new GestionSubmitChat(name, input,service);
		EnterKeyChat enterKey = new EnterKeyChat(name, input, service);
		// liaison de l'évenement au bouton
		this.submit.addActionListener(gestionSubmit);
		this.input.addKeyListener(enterKey);

		// conteneur principal
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(0,1,0,1);
		c.fill = GridBagConstraints.BOTH;
		
		c.gridx = c.gridy = 0;
		c.gridwidth = 2;
		c.weightx = 1.0;
		c.weighty = 1.0;
		this.add(scrollPaneAff,c);
		
		c.gridy = 1;
		c.gridwidth = 1;
		c.weighty = 0.0;		
		c.insets = new Insets(10,1,10,1);
		this.add(input,c);
		
		c.gridx = 1;
		this.add(submit,c);
		
		c.gridy = 2;
		c.gridx = 0;
		c.gridwidth = 2;
		c.insets = new Insets(0,1,0,1);
		this.add(utilLab,c);
		
		c.gridy = 3;
		c.weighty = 1.0;
		this.add(scrollPaneUtil,c);
	}

	/**
	 * Methode pour ajouter un message dans le chat
	 * @param string
	 * 			Message envoye par un utilisateur
	 */
	public void addText(String string) {
			affichage.append(string);
			affichage.setCaretPosition(affichage.getDocument().getLength());
	}
	

	/**
	 * Getter de l'objet JList qui contient la liste des utilisateurs
	 * @return la liste des utilisateurs
	 */
	public JList getListeUtilisateurs() {
		return listeUtilisateurs;
	}

}
