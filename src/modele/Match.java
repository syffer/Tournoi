package modele;

import java.util.ArrayList;
import java.util.List;

public class Match {
	
	private Joueur joueur1;
	private Joueur joueur2;
	
	private boolean fini;
	
	public Match( Joueur joueur1, Joueur joueur2 ) {
		this.joueur1 = joueur1;
		this.joueur2 = joueur2;
		
		this.fini = true;
	}
	
	
	public List<Joueur> getJoueurs() {
		ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
		joueurs.add( this.joueur1 );
		joueurs.add( this.joueur2 );
		return joueurs;
	}
	
	
	
	public String toString() {
		return this.joueur1.toString() + " VS " + this.joueur2.toString();
	}
	
	
	
}
