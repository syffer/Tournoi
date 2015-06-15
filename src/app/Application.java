package app;

import controleur.ControleurUndoable;
import modele.Joueur;
import modele.ModeleUndoable;

public class Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ModeleUndoable modele = new ModeleUndoable();
		
		new ControleurUndoable(modele);
		
		Joueur joueur = new Joueur("e");
		Joueur j2 = new Joueur("e");
		
		System.out.println( joueur + " " + j2 );
		
	}

}
