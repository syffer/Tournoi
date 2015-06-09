package modele;

import java.util.ArrayList;
import java.util.List;

public class Match {
	
	public static final int POINTS_GAGNANT = 3;
	public static final int POINTS_PERDANT = 1;
	public static final int POINTS_GAGNANT_PAR_ABANDON = 3;
	public static final int POINTS_PERDANT_PAR_ABANDON = 0;
	public static final int POINTS_MATCH_NULL = 2;
	
	private Joueur joueur1;
	private Joueur joueur2;
		
	
	public Match( Joueur joueur1, Joueur joueur2 ) {
		this.joueur1 = joueur1;
		this.joueur2 = joueur2;
	}
	
	
	
	/*
	public void matchGagnant( Joueur gagnant ) {
		
		Joueur perdant = ( this.joueur1 == gagnant ) ? this.joueur2 : this.joueur1;
		
		gagnant.incrementerNbPoints( Match.POINTS_GAGNANT );
		perdant.incrementerNbPoints( Match.POINTS_PERDANT );
		
		gagnant.aGagne();
		perdant.aPerdu();
		
	}
	
	public void matchNull() {
		
		this.joueur1.incrementerNbPoints( Match.POINTS_MATCH_NULL );
		this.joueur2.incrementerNbPoints( Match.POINTS_MATCH_NULL );
	
		this.joueur1.aPerdu();
		this.joueur2.aPerdu();
		
	}
	*/
	
	
	public List<Joueur> getJoueurs() {
		ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
		joueurs.add( this.joueur1 );
		joueurs.add( this.joueur2 );
		return joueurs;
	}
	
	
	
	public Joueur getJoueur1() {
		return joueur1;
	}

	public Joueur getJoueur2() {
		return joueur2;
	}



	public String toString() {
		return this.joueur1.toString() + " VS " + this.joueur2.toString();
	}
	
	
	
}
