package app;

import controleur.Controleur;
import modele.Tournoi;

public class Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Tournoi tournoi = new Tournoi();
		
		new Controleur(tournoi);
				
	}

}
