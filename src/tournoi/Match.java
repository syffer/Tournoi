package tournoi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Match implements Serializable {
	
	private static final long serialVersionUID = -1929689348083618910L;
	
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
	
	
	public boolean joueurJoueAuMatch( Joueur joueur ) {
		return this.joueur1 == joueur || this.joueur2 == joueur;
	}
	
	public void finir() {
		this.joueur1.setEnMatch(false);
		this.joueur2.setEnMatch(false);
	}
	
	
	@Override
	public String toString() {
		return this.joueur1 + " VS " + this.joueur2;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((joueur1 == null) ? 0 : joueur1.hashCode());
		result = prime * result + ((joueur2 == null) ? 0 : joueur2.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Match other = (Match) obj;
		if (joueur1 == null) {
			if (other.joueur1 != null)
				return false;
		} else if (!joueur1.equals(other.joueur1))
			return false;
		if (joueur2 == null) {
			if (other.joueur2 != null)
				return false;
		} else if (!joueur2.equals(other.joueur2))
			return false;
		return true;
	}
	
	/*
	@Override
	public Match clone() {
		
		try {
			
			Match match = (Match) super.clone();
			//match.joueur1 = (Joueur) this.joueur1.clone();
			//match.joueur2 = (Joueur) this.joueur2.clone();
			
			return match;
			
		}
		catch( CloneNotSupportedException e ) {
			throw new InternalError("clonage impossible");
		}
		
	}
	*/
	
}