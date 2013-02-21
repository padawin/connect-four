package client.ihm.events;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JList;

import client.ihm.UtilisateurListenerImpl;

public class EnterKeyRejoindrePartie implements KeyListener {
	
	private JList listeParties;
	private UtilisateurListenerImpl cont;
	

	public EnterKeyRejoindrePartie(JList listeParties,
			UtilisateurListenerImpl cont) {
		super();
		this.listeParties = listeParties;
		this.cont = cont;
	}

	public void keyPressed(KeyEvent arg0) {
		
	}

	public void keyReleased(KeyEvent evt) {
		char car = evt.getKeyChar();
		if(car == '\n'){
			if(!listeParties.isSelectionEmpty()){
				this.cont.rejoindrePartie();
			}
		}
	}

	public void keyTyped(KeyEvent arg0) {
		
	}

}
