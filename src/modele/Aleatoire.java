package modele;

import java.util.Random;

public class Aleatoire {
	
	private Random random;

	public Aleatoire() {
		this.random = new Random();
	}

	
	public int getNombreAleatoire( int min, int max ) {
		// min et max compris
		return this.random.nextInt( (max - min) + 1) + min;
	}
	
	public int getNombreAleatoire( int max ) {
		return this.getNombreAleatoire( 0, max );
	}
	
}
