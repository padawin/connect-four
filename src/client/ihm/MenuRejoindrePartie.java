package client.ihm;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import client.ihm.events.EnterKeyRejoindrePartie;
import client.ihm.events.GestionSubmitAccueil;
import client.ihm.events.GestionSubmitRejoindrePartie;

import server.Partie;

/**
 * Classe de l'IHM pour rejoindre une partie de puissance 4
 * @author Carine Kong, Ghislain Rodrigues, Rony Dray
 * @version 1
 */
public class MenuRejoindrePartie extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel titre;
	private JScrollPane scrollPanelListeParties;
	private static JList listeParties;
	private JButton boutJoindre, boutRetour;
	private UtilisateurListenerImpl cont;

	/**
	 * Constructeur de la vue
	 * @param conteneur
	 */
	public MenuRejoindrePartie(UtilisateurListenerImpl conteneur){
		
		this.cont = conteneur;
		titre = new JLabel();
		titre.setText("Liste des parties en attente d'un joueur");
		listeParties = new JList();
		listeParties.setVisibleRowCount(15);
		listeParties.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
		scrollPanelListeParties = new JScrollPane();
		scrollPanelListeParties.setViewportView(listeParties);
		boutJoindre = new JButton("Rejoindre");
		//evenements
		GestionSubmitRejoindrePartie eventBoutRejoindrePartie = new GestionSubmitRejoindrePartie(listeParties,cont);
		boutJoindre.addActionListener(eventBoutRejoindrePartie);
		EnterKeyRejoindrePartie eventKeyRejoindrePartie = new EnterKeyRejoindrePartie(listeParties, cont);
		listeParties.addKeyListener(eventKeyRejoindrePartie);
		
		boutRetour = new JButton("Retour");
		GestionSubmitAccueil eventBoutAccueil = new GestionSubmitAccueil(cont);
		boutRetour.addActionListener(eventBoutAccueil);
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = c.gridy = 0;
		c.gridwidth = 2;
		this.add(titre,c);
		
		c.gridy = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		this.add(scrollPanelListeParties,c);
		
		c.gridy = 2;
		c.gridwidth = 1;
		c.insets = new Insets(10,0,0,0);
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		this.add(boutJoindre,c);
		
		c.gridx = 1;
		this.add(boutRetour,c);
		
		
		
	}
	
	/**
	 * Methode pour afficher la liste des parties dans l'element JList
	 * @param liste
	 * 			Liste des parties
	 */
	public void getListeParties(ArrayList<Partie> liste) {
		Vector<String> tmp = new Vector<String>();
		//listeParties.removeAll();
		for(int i = 0;i<liste.size();i++){
			tmp.add(liste.get(i).getNum()+" : "+liste.get(i).getNom()+" creee par "+liste.get(i).getJ1Name()+(liste.get(i).protegee()?" (protegee)":""));
		}
		listeParties.setListData(tmp);
	}

	/**
	 * Methode pour recuperer le nom de la partie selectionnee
	 * @return Nom de la partie
	 */
	public String getNomPartie() {
		return (String) listeParties.getSelectedValue();
	}

	/**
	 * Methode pour recuperer l'id de la partie selectionnee (premiere partie du nom de la partie
	 * @return id de la partie
	 */
	public int getIDPartie() {
		String[] tmp = ((String)listeParties.getSelectedValue()).split(" :");
		int numPartie = Integer.parseInt(tmp[0]);
		return numPartie;
	}
	
}
