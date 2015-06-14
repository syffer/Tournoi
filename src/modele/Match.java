package modele;

import java.util.ArrayList;
import java.util.List;

public class Match implements Cloneable {
	
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
		
		this.joueur1.setEnMatch(true);
		this.joueur2.setEnMatch(true);
	}
		
	
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


	public Joueur getAutreJoueur( Joueur joueur ) throws MatchException {
		
		if( joueur != this.joueur1 && joueur != this.joueur2 ) throw new MatchException("Ce joueur ne fait pas partie de ce match.");
		
		return ( joueur == this.joueur1 ) ? this.joueur2 : this.joueur1;
	}
	
	
	public boolean joueAuMatch( Joueur joueur ) {
		return this.joueur1 == joueur || this.joueur2 == joueur;
	}
	
	
	
	
	@Override
	public String toString() {
		return this.joueur1.getNom() + " VS " + this.joueur2.getNom();
	}
	
	@Override
	public Match clone() {
		
		try {
			
			Match match = (Match) super.clone();
			match.joueur1 = (Joueur) this.joueur1.clone();
			match.joueur2 = (Joueur) this.joueur2.clone();
			
			return match;
			
		}
		catch( CloneNotSupportedException e ) {
			throw new InternalError("clonage impossible");
		}
		
	}
	
}
