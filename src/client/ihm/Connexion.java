package client.ihm;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import server.ClientWebService;
import client.Client;
import client.ihm.events.EnterKeyConnexion;
import client.ihm.events.EnterKeyNouvCompte;
import client.ihm.events.GestionSubmitConnexion;
import client.ihm.events.GestionSubmitNouvCompte;

/**
 * Classe de la fenetre de connexion d'un client au serveur pour jouer
 * 
 * @author Carine Kong, Ghislain Rodrigues, Rony Dray
 * @version 1
 */
public class Connexion extends JPanel {

	private static final long serialVersionUID = 1L;
	private JFrame fenetre;
	private JButton butConnexion;
	private JButton butNouvCompte;
	private JLabel loginLab1;
	private JLabel passLab1;
	private JLabel hostLab;
	private JLabel portLab;
	private JLabel loginLab2;
	private JLabel passLab2;
	private JLabel prenomLab;
	private JLabel nomLab;
	private JLabel message;
	private JLabel message2;
	private JPanel vueConnexion;
	private JPanel vueNouvCompte;
	private JTabbedPane jTabbedPane1;
	private JTextField loginIN1;
	private JPasswordField passIN1;
	// private JTextField passIN1;
	private JTextField hostIN;
	private JTextField portIN;
	private JPasswordField passIN2;
	private JTextField loginIN2;
	// private JTextField passIN2;
	private JTextField prenomIN;
	private JTextField nomIN;
	private ClientWebService wsClient = new ClientWebService();

	/**
	 * Methode qui cree la fenetre pour se connecter a l'application ou pour
	 * creer un compte
	 * 
	 * @param client
	 *            Client pour faire appel a la methode connexion de la classe
	 *            Client
	 * 
	 */
	public Connexion(final Client client) {
		fenetre = new JFrame("Connexion");

		// onglets
		jTabbedPane1 = new JTabbedPane();
		// panels
		vueConnexion = new JPanel();
		vueNouvCompte = new JPanel();
		// labels
		loginLab1 = new JLabel();
		passLab1 = new JLabel();
		hostLab = new JLabel();
		portLab = new JLabel();
		loginLab2 = new JLabel();
		passLab2 = new JLabel();
		nomLab = new JLabel();
		prenomLab = new JLabel();
		message = new JLabel();
		message2 = new JLabel();
		// textfields
		loginIN1 = new JTextField(23);
		passIN1 = new JPasswordField(23);
		passIN2 = new JPasswordField(23);
		hostIN = new JTextField(23);
		portIN = new JTextField(23);
		loginIN2 = new JTextField(23);
		nomIN = new JTextField(23);
		prenomIN = new JTextField(23);
		// boutons
		butConnexion = new JButton();
		butNouvCompte = new JButton();

		setLayout(new java.awt.BorderLayout());

		loginLab1.setText("Login");

		passLab1.setText("Mot de passe");

		hostLab.setText("Serveur");

		portLab.setText("Port");

		butConnexion.setText("Connexion");
		
		//evenements de la connexion
		GestionSubmitConnexion eventBoutConn = new GestionSubmitConnexion(client, hostIN, portIN, loginIN1, fenetre, message, passIN1);
		EnterKeyConnexion eventKeyConn = new EnterKeyConnexion(client, hostIN, portIN, loginIN1, fenetre, message, passIN1);

		butConnexion.addActionListener(eventBoutConn);
		loginIN1.addKeyListener(eventKeyConn);
		passIN1.addKeyListener(eventKeyConn);
		hostIN.addKeyListener(eventKeyConn);
		portIN.addKeyListener(eventKeyConn);

		vueConnexion.setLayout(new GridBagLayout());
		GridBagConstraints c1 = new GridBagConstraints();
		c1.anchor = GridBagConstraints.NORTHWEST;
		c1.weighty = 1;
		c1.gridx = c1.gridy = 0;
		c1.gridwidth = 1;
		c1.insets = new Insets(10, 0, 0, 0);
		vueConnexion.add(loginLab1, c1);

		c1.insets = new Insets(30, 0, 0, 0);
		vueConnexion.add(loginIN1, c1);

		c1.insets = new Insets(60, 0, 0, 0);
		vueConnexion.add(passLab1, c1);

		c1.insets = new Insets(80, 0, 0, 0);
		vueConnexion.add(passIN1, c1);

		c1.insets = new Insets(110, 0, 0, 0);
		vueConnexion.add(hostLab, c1);

		c1.insets = new Insets(130, 0, 0, 0);
		vueConnexion.add(hostIN, c1);

		c1.insets = new Insets(160, 0, 0, 0);
		vueConnexion.add(portLab, c1);

		c1.insets = new Insets(180, 0, 0, 0);
		vueConnexion.add(portIN, c1);

		c1.insets = new Insets(100, 0, 0, 0);
		c1.anchor = GridBagConstraints.CENTER;
		vueConnexion.add(butConnexion, c1);

		c1.insets = new Insets(160, 0, 0, 0);
		vueConnexion.add(message, c1);

		jTabbedPane1.addTab("Utilisateur existant", vueConnexion);

		//Creation de compte
		
		loginLab2.setText("Login");

		passLab2.setText("Mot de passe");

		nomLab.setText("Nom");

		prenomLab.setText("Prenom");

		butNouvCompte.setText("Creer compte");

		//evenements de la creation de compte
		GestionSubmitNouvCompte evenBoutNouvCompt = new GestionSubmitNouvCompte(nomIN, prenomIN, loginIN2, passIN2, message2, wsClient);
		EnterKeyNouvCompte evenKeyNouvCompt = new EnterKeyNouvCompte(nomIN, prenomIN, loginIN2, passIN2, message2, wsClient);
		
		butNouvCompte.addActionListener(evenBoutNouvCompt);
		
		nomIN.addKeyListener(evenKeyNouvCompt);
		prenomIN.addKeyListener(evenKeyNouvCompt);
		loginIN2.addKeyListener(evenKeyNouvCompt);
		passIN2.addKeyListener(evenKeyNouvCompt);

		vueNouvCompte.setLayout(new GridBagLayout());
		GridBagConstraints c2 = new GridBagConstraints();
		c2.anchor = GridBagConstraints.NORTHWEST;
		c2.weighty = 1;
		c2.gridx = c2.gridy = 0;
		c2.gridwidth = 1;
		c2.insets = new Insets(10, 0, 0, 0);
		vueNouvCompte.add(nomLab, c2);
		// +20 de marge
		c2.insets = new Insets(30, 0, 0, 0);
		vueNouvCompte.add(nomIN, c2);
		// +30 de marge
		c2.insets = new Insets(60, 0, 0, 0);
		vueNouvCompte.add(prenomLab, c2);
		// +20 de marge
		c2.insets = new Insets(80, 0, 0, 0);
		vueNouvCompte.add(prenomIN, c2);
		// +30 de marge
		c2.insets = new Insets(110, 0, 0, 0);
		vueNouvCompte.add(loginLab2, c2);
		// +20 de marge
		c2.insets = new Insets(130, 0, 0, 0);
		vueNouvCompte.add(loginIN2, c2);
		// +30 de marge
		c2.insets = new Insets(160, 0, 0, 0);
		vueNouvCompte.add(passLab2, c2);
		// +20 de marge
		c2.insets = new Insets(180, 0, 0, 0);
		vueNouvCompte.add(passIN2, c2);

		c2.insets = new Insets(110, 0, 0, 0);
		c2.anchor = GridBagConstraints.CENTER;
		vueNouvCompte.add(butNouvCompte, c2);
		
		c2.insets = new Insets(170, 0, 0, 0);
		vueNouvCompte.add(message2, c2);

		jTabbedPane1.addTab("Nouvel utilisateur", vueNouvCompte);
		fenetre.add(jTabbedPane1);
		fenetre.setSize(300, 400);
		fenetre.setResizable(false);
		fenetre.setVisible(true);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
