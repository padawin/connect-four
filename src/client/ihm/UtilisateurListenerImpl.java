package client.ihm;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import server.ClientWebService;
import server.Partie;
import server.Serv;
import client.Client;
import client.Globals;
import client.UtilisateurListener;
import client.ihm.events.GestionDeconnexion;
import client.ihm.events.GestionSubmitJouer;

import fr.iut.tc6.services.JeuServiceStub.Joueur;

/**
 * Classe de l'IHM de la fenetre principale de l'application cliente. Separee en
 * deux partie : a droite une grille de jeu et a gauche la gestion des parties
 * (nouvelle, rejoindre), les actions pour jouer...
 * 
 * @author Carine Kong, Ghislain Rodrigues, Rony Dray
 * @version 1
 */
public class UtilisateurListenerImpl extends JPanel implements
		UtilisateurListener, Serializable {

	private static final long serialVersionUID = 1L;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JPanel panelJeu;
	private javax.swing.JPanel panelMenu;
	private javax.swing.JScrollPane scrollPaneStats;
	private javax.swing.JTabbedPane ongletsApp;
	private javax.swing.JTextArea stats;
	private javax.swing.JPanel mainPanel;
	private JMenuBar menuBar;
	private JMenu fileMenu, helpMenu;
	private JMenuItem exitMenuItem, disconnectMenuItem, aboutMenuItem;

	private Chat chat;

	private Puissance4IHM ihm = new Puissance4IHM();
	private MenuAccueil accueil;
	private MenuNouvJeu creerPartie;
	private IHMPartie ihmPartie;
	private MenuRejoindrePartie joindrePartie;
	private GestionSubmitJouer actBoutJouer;

	private int partie = -1;

	private int cmp = 1;
	
	private JFrame fenetre;
	
	private GestionDeconnexion gd;



	private Client client;
	private ClientWebService wsClient = new ClientWebService();
	
	/**
	 * Constructeur pour creer la fenetre de l'application avec la grille de
	 * jeu, les informations sur le joueur et les actions possibles
	 * 
	 * @param service
	 *            Service auquel le client est abonne
	 * @param client
	 *            Client de l'application
	 */
	public UtilisateurListenerImpl(Serv service, final Client client) {
		this.client = client;
		joindrePartie = new MenuRejoindrePartie(this);
		fenetre = new JFrame("Puissance 4 - " + this.client.getLogin());

		chat = new Chat(service, client);

		// action du bouton de jeu
		actBoutJouer = new GestionSubmitJouer(this.client,ihmPartie, partie,this);

		// menu de l'application
		menuBar = new JMenuBar();
		fileMenu = new JMenu("Fichier");
		menuBar.add(fileMenu);
		exitMenuItem = new JMenuItem("Quitter", KeyEvent.VK_T);
		exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,
				ActionEvent.ALT_MASK));
		exitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.deconnexion();
				System.exit(0);
			}
		});
		disconnectMenuItem = new JMenuItem("Se deconnecter");
		gd = new GestionDeconnexion(this);
		disconnectMenuItem.addActionListener(gd);
		fileMenu.add(disconnectMenuItem);
		fileMenu.add(exitMenuItem);

		// deuxieme menu dans la barre de menu
		helpMenu = new JMenu("Aide");
		menuBar.add(helpMenu);

		aboutMenuItem = new JMenuItem("A propos...");
		aboutMenuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JOptionPane
						.showMessageDialog(fenetre,
								"Application creee par Rony Dray, Carine Kong et Ghislain Rodrigues.");
			}

		});
		helpMenu.add(aboutMenuItem);

		// fin du menu de l'application

		// creation du GUI principal
		mainPanel = new javax.swing.JPanel();
		panelMenu = new javax.swing.JPanel();
		panelMenu.setLayout(new BorderLayout());
		
		accueil = new MenuAccueil(this);
		ongletsApp = new javax.swing.JTabbedPane();
		panelJeu = new javax.swing.JPanel();
		panelMenu.add("North",accueil);
		jLabel1 = new javax.swing.JLabel();
		scrollPaneStats = new javax.swing.JScrollPane();
		stats = new javax.swing.JTextArea();

		ongletsApp.setMaximumSize(new java.awt.Dimension(300, 520));
		ongletsApp.setMinimumSize(new java.awt.Dimension(300, 520));
		ongletsApp.setName("jTabbedPane1");

		jLabel1.setText("Statistiques du joueur");

		scrollPaneStats.setName("jScrollPane1");

		stats.setColumns(20);
		stats.setRows(5);
		stats.setEditable(false);
		// Affichage des stats
		afficherStats();
		
		scrollPaneStats.setViewportView(stats);

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(
				panelJeu);
		panelJeu.setLayout(jPanel2Layout);
		jPanel2Layout
				.setHorizontalGroup(jPanel2Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel2Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel2Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING,false)
														.addComponent(panelMenu,
																275,
																275,
																Short.MAX_VALUE)
														.addComponent(
																scrollPaneStats,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																275,
																Short.MAX_VALUE)
														.addComponent(jLabel1))
										.addContainerGap()));
		jPanel2Layout
				.setVerticalGroup(jPanel2Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel2Layout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(jLabel1)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												scrollPaneStats,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												100,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(panelMenu)));

		ongletsApp.addTab("Jeu", panelJeu);

		ongletsApp.addTab("Chat", chat);

		javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(
				mainPanel);
		mainPanel.setLayout(mainPanelLayout);
		mainPanelLayout.setHorizontalGroup(mainPanelLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				javax.swing.GroupLayout.Alignment.TRAILING,
				mainPanelLayout.createSequentialGroup().addGap(6).addComponent(
						ongletsApp).addGap(5).addComponent(ihm)));
		mainPanelLayout.setVerticalGroup(mainPanelLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				ongletsApp).addComponent(ihm));
		mainPanel.add(ongletsApp);

		fenetre.setJMenuBar(menuBar);
		fenetre.add(mainPanel);
		fenetre.setSize(930, 585);
		fenetre.setResizable(false);
		fenetre.setVisible(true);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.addWindowListener(new WindowListener() {
			public void windowActivated(WindowEvent arg0) {
			}

			public void windowClosed(WindowEvent arg0) {
			}

			public void windowClosing(WindowEvent arg0) {
				client.deconnexion();
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
	 * 
	 * @return la gestion de deconnexion du client
	 */
	public GestionDeconnexion getGd() {
		return gd;
	}

	/* (non-Javadoc)
	 * @see client.UtilisateurListener#afficherStats()
	 */
	public void afficherStats() {
		if(getClient().getId()==-2){
			stats.setText("");
			stats.append("Vous devez etre inscrit\npour avoir des statistiques");
		}
		else{
			stats.setText("");
			Joueur j = wsClient.getJoueurById(client.getId());
			stats.append("Joueur en cours : " + j.getPrenom() + " " + j.getNom() + "\n");
			stats.append("Nb victoires : "
					+ wsClient.getStatVictoireByJoueur(client.getId()) + "\n");
			stats.append("Nb parties : "
					+ wsClient.getStatPartiesByJoueur(client.getId()) + "\n");
		}
	}
	
	/* (non-Javadoc)
	 * @see client.UtilisateurListener#getChat()
	 */
	public Chat getChat() {
		return chat;
	}

	/* (non-Javadoc)
	 * @see client.UtilisateurListener#getClient()
	 */
	public Client getClient() {
		return client;
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#getName()
	 */
	public String getName() {
		return client.getLogin();
	}

	/**
	 * Methode qui enleve la vue courante et affiche la vue de l'accueil
	 */
	public void accueil() {
		panelMenu.removeAll();
		panelMenu.add("North",accueil);
		this.ihm.effacer();
		panelMenu.updateUI();
	}

	/**
	 * Methode qui affiche la vue de creation d'une partie
	 */
	public void creerPartie() {
		if(client.getId()!=-2){
			panelMenu.removeAll();
			creerPartie = new MenuNouvJeu(this);
			panelMenu.add("North",creerPartie);
			panelMenu.updateUI();
		}
		else{
			if(cmp == 1){
				JOptionPane.showMessageDialog(fenetre,"Vous devez avoir un compte pour creer une partie.");
				cmp++;
			}
			else if(cmp == 2){
				JOptionPane.showMessageDialog(fenetre,"Vous etes bouché ?? VOUS DEVEZ AVOIR UN COMPTE POUR CREER UNE PARTIE.");
				cmp++;
			}
			else if(cmp == 3){
				JOptionPane.showMessageDialog(fenetre,"Non mais vous etes vraiment pas doué la c'est pas possible !!!");
				cmp++;
			}
			else{
				JOptionPane.showMessageDialog(fenetre,"Tant pis pour vous...");
				fenetre.dispose();
				client.deconnexion();
				@SuppressWarnings("unused")
				Connexion vueConnexion = new Connexion(client);
			}
			
		}
	}

	/**
	 * Methode qui affiche la vue de jeu, une fois la partie creee
	 */
	public void nouvJeu() {
		panelMenu.removeAll();
		ihmPartie = new IHMPartie(this);
		try {
			String nomPartie = creerPartie.getNomPartie();
			String mdpPartie = creerPartie.getMdpPartie();
			client.setCouleur(creerPartie.getCouleur());
			partie = client.getService().addPartie(this, nomPartie, mdpPartie);
			client.setJoue(true);
			actBoutJouer.setPartie(partie);
			actBoutJouer.setIhmPartie(ihmPartie);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		panelMenu.add("North",ihmPartie);
		logChanged(Globals.ATTENTE_JOUEUR);
		panelMenu.updateUI();
	}

	/**
	 * Methode qui affiche la vue pour visualiser la liste des parties en cours
	 */
	public void partiesEnCours() {
		panelMenu.removeAll();
		try {
			actualiserPartie(client.getService().getListeParties());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		panelMenu.add("North",joindrePartie);
		panelMenu.updateUI();
	}

	/**
	 * Methode qui rejoind le joueur a la partie selectionnee et affiche la vue
	 * de jeu
	 */
	public void rejoindrePartie() {
		panelMenu.removeAll();
		ihmPartie = new IHMPartie(this);
		try {
			// partie = numero de la partie selectionnee (numero juste avant les
			// : dans la liste des parties
			partie = joindrePartie.getIDPartie();
			client.rejoindrePartie(partie);
			client.setJoue(true);
			actBoutJouer.setPartie(partie);
			actBoutJouer.setIhmPartie(ihmPartie);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		panelMenu.add("North",ihmPartie);
		panelMenu.updateUI();
	}

	/* (non-Javadoc)
	 * @see client.UtilisateurListener#actualiserPartie(java.util.ArrayList)
	 */
	public void actualiserPartie(ArrayList<Partie> a) {
		joindrePartie.getListeParties(a);
	}

	/* (non-Javadoc)
	 * @see client.UtilisateurListener#affecterCase(int, int, boolean)
	 */
	public void affecterCase(int ligne, int colonne, boolean b) {
		ihm.affecterCase(ligne, colonne, b);
	}

	/* (non-Javadoc)
	 * @see client.UtilisateurListener#effacer()
	 */
	public void effacer() {
		ihm.effacer();
	}

	/* (non-Javadoc)
	 * @see client.UtilisateurListener#setCouleur(java.lang.String)
	 */
	public void setCouleur(String c) {
		client.setCouleur(c);
		ihm.setCouleur(c);
	}

	/* (non-Javadoc)
	 * @see client.UtilisateurListener#messageChanged(java.lang.String)
	 */
	public void messageChanged(String message) throws RemoteException {
		if(!message.equals("")){
			chat.addText(message + "\n");
		}
	}

	/* (non-Javadoc)
	 * @see client.UtilisateurListener#logChanged(java.lang.String)
	 */
	public void logChanged(String message) {
		ihmPartie.getLog().append(message + "\n");
		ihmPartie.getLog().setCaretPosition(
				ihmPartie.getLog().getDocument().getLength());
	}

	/* (non-Javadoc)
	 * @see client.UtilisateurListener#getCouleur()
	 */
	public char getCouleur() throws RemoteException {
		return client.getCouleur();
	}

	/**
	 * Methode pour desactiver le bouton de jeu du client
	 * 
	 * @throws RemoteException
	 */
	public void disableBoutJouer() throws RemoteException {
		ihmPartie.pasVotreTour(actBoutJouer);
	}

	/* (non-Javadoc)
	 * @see client.UtilisateurListener#enableBoutJouer()
	 */
	public void enableBoutJouer() throws RemoteException {
		ihmPartie.votreTour(actBoutJouer);
	}

	/* (non-Javadoc)
	 * @see client.UtilisateurListener#getListeClients(java.util.ArrayList)
	 */
	public void getListeClients(ArrayList<UtilisateurListener> liste) {
		Vector<String> tmp = new Vector<String>();
		chat.getListeUtilisateurs().removeAll();
		for (int i = 0; i < liste.size(); i++) {
			try {
				tmp.add(liste.get(i).getName());
			} catch (RemoteException e) {
				liste.remove(i);
			}
		}
		chat.getListeUtilisateurs().setListData(tmp);
	}

	/* (non-Javadoc)
	 * @see client.UtilisateurListener#partieFinie()
	 */
	public void partieFinie() throws RemoteException {
		this.partie = -1;
		ihmPartie.finPartie();
	}

	/* (non-Javadoc)
	 * @see client.UtilisateurListener#finJeu()
	 */
	public void finJeu(){
		client.setJoue(false);
	}

	/* (non-Javadoc)
	 * @see client.UtilisateurListener#getPartie()
	 */
	public int getPartie() {
		return partie;
	}

	
	/* (non-Javadoc)
	 * @see client.UtilisateurListener#deconnecter()
	 */
	public void deconnecter(){
		fenetre.dispose();
		client.deconnexion();
		@SuppressWarnings("unused")
		Connexion vueConnexion = new Connexion(client);
	}
}
