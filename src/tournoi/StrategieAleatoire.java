package tournoi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StrategieAleatoire extends Strategie {
	
	private static final long serialVersionUID = -8515687357847290736L;
	
	private Set<Joueur> joueursDisponibles;
	
	
	public StrategieAleatoire( Set<Joueur> joueursDisponibles ) {		
		this.joueursDisponibles = joueursDisponibles;
	}
	
	public StrategieAleatoire() {
		this( new HashSet<Joueur>() );
	}
	

	@Override
	public StrategieAleatoire clone() {
		
		try {
			
			StrategieAleatoire strategie = (StrategieAleatoire) super.clone();
			
			strategie.joueursDisponibles = new HashSet<Joueur>( this.joueursDisponibles.size() );
			
			for( Joueur joueur : this.joueursDisponibles ) {
				
				Joueur clone = joueur.clone();
				strategie.joueursDisponibles.add(clone);
				
			}
			
			return strategie;
			
		}
		catch( CloneNotSupportedException e ) {
			throw new InternalError("clonage impossible");
		}
		
	}
	
	
	@Override
	public void ajouterJoueur( Joueur joueur ) throws JoueurDejaExistantException {
		this.joueursDisponibles.add(joueur);
	}
		
	@Override
	public void supprimerJoueur( Joueur joueur ) {
		this.joueursDisponibles.remove(joueur);
	}

	
	
	@Override
	public Collection<Joueur> getJoueursDisponibles() {
		return this.joueursDisponibles;
	}

	@Override
	public int getNbJoueursDisponibles() {
		return this.joueursDisponibles.size();
	}

	
	
	
	@Override
	public List<Match> genererMatchs() {
		
		List<Match> matchs = new ArrayList<Match>();
		List<Joueur> joueurs = new ArrayList<Joueur>( this.joueursDisponibles );
		
		while(true) {
						
			int nbJoueurs = this.joueursDisponibles.size();
			if( nbJoueurs < 2 ) break;
			
			int indiceJoueur = Aleatoire.getNombreAleatoire( nbJoueurs - 1 );
			Joueur joueur = joueurs.get(indiceJoueur);
			joueurs.remove(indiceJoueur);
			
			
			int indiceAdversairePotentiel = Aleatoire.getNombreAleatoire( nbJoueurs - 2 );	// on a retiré un joueur (donc -2)
			Joueur adversaire = joueurs.get(indiceAdversairePotentiel);
			joueurs.remove(indiceAdversairePotentiel);
			
			Match match = new Match( joueur, adversaire );
			matchs.add(match);
			
			this.joueursDisponibles.remove(joueur);
			this.joueursDisponibles.remove(adversaire);
		}
				
		return matchs;
	
	}
	
	@Override
	public void retirerJoueursDisponibles( Joueur... joueurs ) {	
		this.joueursDisponibles.removeAll( Arrays.asList(joueurs) );
	}
	
	@Override
	public void resoudreMatchNormal( Joueur joueurGagnant, Joueur joueurPerdant ) {
		this.ajouterJoueursDisponibles( joueurGagnant, joueurPerdant );
	}

	@Override
	public void resoudreMatchParAbandon( Joueur joueurGagnant, Joueur joueurPerdant ) {
		this.ajouterJoueursDisponibles( joueurGagnant, joueurPerdant );
	}

	@Override
	public void resoudreMatchNull( Joueur joueur1, Joueur joueur2 ) {
		this.ajouterJoueursDisponibles( joueur1, joueur2 );
	}

	@Override
	public void resoudreMatchSupprime( Joueur joueur1, Joueur joueur2 ) {
		this.ajouterJoueursDisponibles( joueur1, joueur2 );
	}
		
	
	private void ajouterJoueursDisponibles( Joueur... joueurs ) {
		this.ajouterJoueursDisponibles( Arrays.asList(joueurs) );
	}
	
	private void ajouterJoueursDisponibles( Collection<Joueur> joueurs ) {
		this.joueursDisponibles.addAll(joueurs);		
	}
	
	

	@Override
	public int getNbPointsGagnantMatchNormal() {
		return 3;
	}

	@Override
	public int getNbPointsPerdantMatchNormal() {
		return 1;
	}

	@Override
	public int getNbPointsGagnantMatchAbandon() {
		return 3;
	}

	@Override
	public int getNbPointsPerdantMatchAbandon() {
		return 0;
	}

	@Override
	public int getNbPointsMatchNull() {
		return 2;
	}
	
	
	
}
