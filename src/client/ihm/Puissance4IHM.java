/*
** --- fichier ----------------------------------------------------------------
**
** Nom : Puissance4IHM.java
** Role : implantation de l'IHM du puissance 4
**
** ----------------------------------------------------------------------------
*/ 
package client.ihm;
/*
** --- Dependances ------------------------------------------------------------
*/ 
import java.awt.Color;
import java.awt.Panel;
import java.awt.Graphics;
import java.awt.Dimension;

/**
* --- Classe -----------------------------------------------------------------
*
* Nom : Puissance4IHM
*
*IHM de la grille de jeu du puissance 4
* ----------------------------------------------------------------------------
*/ 

public class Puissance4IHM extends Panel
{
 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/*
 ** --- Constantes ------------------------------------------------------------
 */
 private static final int VIDE = 0;
 private static final int ROUGE = 1;
 private static final int JAUNE = 2;
 private static final int WIDTH = 600;
 private static final int HEIGHT = 520;
 private static final Dimension dim = new Dimension(WIDTH, HEIGHT );
 private char CouleurJoueur = 'V'; // couleur du joueur courant


 /*
 ** --- Variables globales ----------------------------------------------------
 */
 
 /*
 ** Etat de la grille
 */
 private int [][] grille = new int[6][7]; 

 /*
 ** --- Contructeur -----------------------------------------------------------
 **
 ** Nom : Puissance4IHM
 ** Arguments : aucun
 **
 ** ---------------------------------------------------------------------------
 */ 
 public Puissance4IHM() {
  /*
  ** Initialisation de la grille
  */
  for(int j = 0; j < 7; j++)
   for(int i = 0; i < 6; i++) {
    grille[i][j] = VIDE;
   }
 }

 /*
 ** --- Methode ---------------------------------------------------------------
 **
 ** Nom  : affecterCase
 ** Role : affecter un jeton de couleur a une case
 ** Arguments :
 **  - ligne : numero de ligne (entre 0 et 5)
 **  - colonne : numero de colonne (entre 0 et 6)
 **  - rouge : jeton rouge si vrai, sinon jeton jaune
 **
 ** ---------------------------------------------------------------------------
 */ 
 public boolean affecterCase(int ligne, int colonne, boolean rouge) {
  boolean res = false;
  
  if (   (ligne >= 0) && (ligne < 6)
      && (colonne >= 0) && (colonne < 7)) {
   
   grille[ligne][colonne] = (rouge ? ROUGE : JAUNE);   
   res = true;   
   
   repaint();
  }
  
  return res;
 }

 /*
 ** --- Methode ---------------------------------------------------------------
 **
 ** Nom  : effacer
 ** Role : efface le contenu de la grille
 ** Arguments : aucun
 **
 ** ---------------------------------------------------------------------------
 */ 
 public void effacer() {
 
   for(int j = 0; j < 7; j++)
     for(int i = 0; i < 6; i++) {
       grille[i][j] = VIDE;
     }
   
   repaint();
 }

 /*
 ** --- Methode ---------------------------------------------------------------
 **
 ** Nom  : paint
 ** Role : dessiner la grille
 ** Arguments :
 **  - g : zone de dessin
 **
 ** ---------------------------------------------------------------------------
 */  
 public void paint(Graphics g) {
 
  int x, y;
  Dimension size = getSize();
 
  /*
  ** Calcul des coordonnees pour centrer la grille
  */
  x = (int) ((size.getWidth() - dim.getWidth()) / 2);
  y = (int) ((size.getHeight() - dim.getHeight()) / 2);
  
  /*
  ** Dessin de la grille
  */
  g.setColor(Color.blue);
  g.fillRect(x, y, WIDTH, HEIGHT);
  g.setColor(Color.black);
  g.drawRect(x, y, WIDTH, HEIGHT);
  
  /*
  ** Dessin des jetons
  */
  for(int i = 0; i < 6; i++)
   for(int j = 0; j < 7; j++) {
   
    if (grille[i][j] == VIDE) { g.setColor(Color.white); }
    else if (grille[i][j] == ROUGE) { g.setColor(Color.red); }
    else { g.setColor(Color.yellow); }    
    g.fillOval(x + 5 + j * 85, y + 5 + (5 - i) * 85, 80, 80);    
   }
 }

 /*
 ** --- Methode ---------------------------------------------------------------
 **
 ** Nom  : getPreferredSize, getMinimumSize, getMaximumSize
 ** Role : indiquer la taille ideale de la zone de dessin
 ** Arguments : aucun
 **
 ** ---------------------------------------------------------------------------
 */    
 public Dimension getPreferredSize() { return dim; }
 public Dimension getMinimumSize()   { return dim; }
 public Dimension getMaximumSize()   { return dim; }

public void setCouleur(String couleur){
	if(couleur.equals("Jaune")){
		this.CouleurJoueur='J';
	}
	else if(couleur.equals("Rouge")){
		this.CouleurJoueur='R';
	}
}

public char getCouleur() {
	return this.CouleurJoueur;
}
}