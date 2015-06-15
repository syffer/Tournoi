package modele;

import java.util.Comparator;

public class ComparateurJoueurNbPoints implements Comparator<Joueur> {

	private static ComparateurJoueurNbPoints comparateur = new ComparateurJoueurNbPoints();
	
	@Override
	public int compare( Joueur joueur1, Joueur joueur2 ) {
		/* a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second.
		 * first > second :  1 (positif integer)
		 * first = second :  0
		 * first < second : -1 (negatif integer)
		 */
		return joueur1.getNbPoints() - joueur2.getNbPoints();
	}
	
	public static ComparateurJoueurNbPoints getComparateur() {
		return ComparateurJoueurNbPoints.comparateur;
	}
	
}