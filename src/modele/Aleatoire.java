package modele;

import java.util.Random;

public class Aleatoire {
	
	private static Random random = new Random();
	
	public static int getNombreAleatoire( int min, int max ) {
		// min et max compris
		return Aleatoire.random.nextInt( (max - min) + 1) + min;
	}
	
	public static int getNombreAleatoire( int max ) {
		return Aleatoire.getNombreAleatoire( 0, max );
	}
	
}
