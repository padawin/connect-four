package client.ihm.events;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import client.ihm.UtilisateurListenerImpl;


public class EnterKeyNouvJeu implements KeyListener {

	private UtilisateurListenerImpl cont;
	
	public EnterKeyNouvJeu(UtilisateurListenerImpl cont) {
		super();
		this.cont = cont;
	}

	public void keyPressed(KeyEvent arg0) {}

	public void keyReleased(KeyEvent evt) {
		char car = evt.getKeyChar();
		if(car == '\n'){
			cont.nouvJeu();
		}
	}

	public void keyTyped(KeyEvent arg0) {}

}
