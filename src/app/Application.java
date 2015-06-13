package app;

import controleur.Controleur;
import modele.Modele;

public class Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Modele modele = new Modele();
		
		new Controleur(modele);
		
	}

}
