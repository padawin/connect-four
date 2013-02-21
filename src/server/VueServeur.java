package server;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import server.events.GestionSubmitReboot;


import client.UtilisateurListener;

public class VueServeur extends JPanel {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private static JLabel etat;
	private static JLabel titre;
	private static JFrame fenetre;
	private static JButton boutReboot;
	private static JList listeClients;
	private JTabbedPane ongletsServ;
	private JPanel panelLog, panelClients;
	private JTextArea log;
	private Serveur serveur;
	/**
	 * Contructeur de la vue du serveur
	 * @param serveur 
	 * @param st 
	 */
	public VueServeur(Serveur s){
		this.serveur = s;
		//création de la fenetre du serveur
		fenetre = new JFrame("Serveur de l'application");

		panelLog = new JPanel();
		panelClients = new JPanel();
		
		ongletsServ = new JTabbedPane();
		
		log = new JTextArea(18,22);
		log.setEditable(false);
		listeClients = new JList();
		listeClients.setVisibleRowCount(16);
		JScrollPane scrollPane = new JScrollPane();
	    scrollPane.setViewportView(listeClients);
		JScrollPane scrollPane1 = new JScrollPane();
	    scrollPane1.setViewportView(log);
		etat = new JLabel();
		titre = new JLabel();
		boutReboot = new JButton("Redemarrer");
		GestionSubmitReboot eventBoutReboot = new GestionSubmitReboot(this.serveur);
		boutReboot.addActionListener(eventBoutReboot);
		etat.setText(Globals.DEMARRAGE_HOST);
		titre.setText("Liste des clients");
		
		//panel du log
		panelLog.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = c.gridy = 0;

		c.weightx = 1.0;
		c.weighty = 1.0; 
		panelLog.add(scrollPane1,c);
		/* ********************** */
		
		//ajout du panel aux onglets
		ongletsServ.addTab("Log", panelLog);
		
		//panel de la liste des clients
		panelClients.setLayout(new GridBagLayout());
		GridBagConstraints c1 = new GridBagConstraints();
		c1.fill = GridBagConstraints.BOTH;
		c1.gridx = c1.gridy = 0;
		
		c1.weightx = 1.0;
		c1.weighty = 1.0;
		panelClients.add(scrollPane,c1);
		/* ********************** */
		
		//ajout du panel aux onglets
		ongletsServ.addTab("Liste des clients", panelClients);
		
		
		//fenetre du serveur
		fenetre.setLayout(new GridBagLayout());
		GridBagConstraints c2 = new GridBagConstraints();
		c2.gridx = c2.gridy = 0;
		c.insets = new Insets(0,0,7,0);
		fenetre.add(etat,c2);

		
		c2.gridy = 1;
		c2.weightx = 1.0;
		c2.weighty = 1.0;
		fenetre.add(ongletsServ,c2);
		
		c2.gridy = 2;
		fenetre.add(boutReboot,c2);
		
		fenetre.setSize(280,400);
		fenetre.setResizable(false);
		fenetre.setVisible(true);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.addWindowListener(new WindowListener() {
			public void windowActivated(WindowEvent arg0) {
			}

			public void windowClosed(WindowEvent arg0) {
			}

			public void windowClosing(WindowEvent arg0) {
				serveur.getSt().deconnecterUtilisateurListener();
			}

			public void windowDeactivated(WindowEvent arg0) {
			}

			public void windowDeiconified(WindowEvent arg0) {
			}

			public void windowIconified(WindowEvent arg0) {
			}

			public void windowOpened(WindowEvent arg0) {
			}
		});
	}

	/**
	 * Défini string comme contenu de la barre d'état de la fenetre du serveur
	 * @param string
	 */
	public void setEtat(String string) {
		etat.setText(string);
		logChanged(string);
	}

	/**
	 * Affiche la liste des clients dans la zone prévue à cet effet de la fenetre du serveur
	 * @param liste
	 */
	public static void getListeClients(ArrayList<UtilisateurListener> liste) {
		Vector<String> tmp = new Vector<String>();
		listeClients.removeAll();
		for(int i = 0;i<liste.size();i++){
			try {
				tmp.add(liste.get(i).getName());
			} catch (RemoteException e) {
				liste.remove(i);
			}
		}
		listeClients.setListData(tmp);
	}

	/*
	 /*

	 * Affiche la liste des parties dans la zone prévue à cet effet de la fenetre du serveur
	 * @param liste
	 *
	public static void getListeParties(ArrayList<Parties> liste) {
		Vector<String> tmp = new Vector<String>();
		listeParties.removeAll();
		for(int i = 0;i<liste.size();i++){
			try {
				tmp.add(liste.get(i).getName());
			} catch (RemoteException e) {
				liste.remove(i);
			}
		}
		listeParties.setListData(tmp);
	}*/
	
	/**
	 * Methode pour ajouter un message au log du serveur
	 */
	public void logChanged(String msg){
		log.append(msg+"\n");
		log.setCaretPosition(log.getDocument().getLength());
	}
	
}
