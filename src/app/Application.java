package app;

import controleur.ControleurUndoable;
import modele.ModeleUndoable;

public class Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ModeleUndoable modele = new ModeleUndoable();
		
		new ControleurUndoable(modele);
		
	}

}
