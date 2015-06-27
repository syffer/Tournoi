package tournoi;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public abstract class Strategie implements Cloneable, Serializable {
	
	private static final long serialVersionUID = 7429957283780152493L;
	
	@Override
	public Strategie clone() throws CloneNotSupportedException {
		
		Strategie strategie = (Strategie) super.clone();			
		return strategie;
		
	}
	
	public abstract void ajouterJoueur( Joueur joueur ) throws JoueurDejaExistantException;
	public abstract void supprimerJoueur( Joueur joueur );
	
	
	public abstract Collection<Joueur> getJoueursDisponibles();
	public abstract int getNbJoueursDisponibles();
	
	public boolean isJoueursDisponibles() {
		return this.getNbJoueursDisponibles() >= 2;
	}
	
	
	// création et résolution de match
	public abstract List<Match> genererMatchs();
	public abstract void retirerJoueursDisponibles( Joueur... joueurs );
	public abstract void resoudreMatchNormal( Joueur joueurGagnant, Joueur joueurPerdant );
	public abstract void resoudreMatchParAbandon( Joueur joueurGagnant, Joueur joueurPerdant );
	public abstract void resoudreMatchNull( Joueur joueur1, Joueur joueur2 );
	public abstract void resoudreMatchSupprime( Joueur joueur1, Joueur joueur2 );
	
	// les nombres de points accordés suite à une victoire / défaite
	public abstract int getNbPointsGagnantMatchNormal();
	public abstract int getNbPointsPerdantMatchNormal();
	public abstract int getNbPointsGagnantMatchAbandon();
	public abstract int getNbPointsPerdantMatchAbandon();
	public abstract int getNbPointsMatchNull();
	
}
