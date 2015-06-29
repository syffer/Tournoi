package tournoi;

import internationalisation.Constantes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StrategieGagnantPerdant extends Strategie {
	
	private static final long serialVersionUID = -7435820314389088859L;

	private TableDesRencontres tableDesRencontres;
	private Map<Joueur, Boolean> tableGagnantsPerdants;
	
	private List<Joueur> gagnants;
	private List<Joueur> perdants;
	
	public StrategieGagnantPerdant() {
		
		this.tableDesRencontres = new TableDesRencontres();
		this.tableGagnantsPerdants = new HashMap<Joueur, Boolean>();
		
		this.gagnants = new ArrayList<Joueur>();
		this.perdants = new ArrayList<Joueur>();
	
	}
	
	public String getNom() {
		return Constantes.getString( Constantes.MATCHS_GAGNANT_PERDANT );
	}
	
	@Override
	public StrategieGagnantPerdant getClone( Map<String, Joueur> cloneJoueurs ) throws CloneNotSupportedException {
		
		StrategieGagnantPerdant strategie = (StrategieGagnantPerdant) this.clone();
		
		strategie.tableDesRencontres = this.tableDesRencontres.clone();
		strategie.tableGagnantsPerdants = new HashMap<Joueur, Boolean>( this.tableGagnantsPerdants.size() );
		strategie.gagnants = new ArrayList<Joueur>( this.gagnants.size() );
		strategie.perdants = new ArrayList<Joueur>( this.perdants.size() );
		
		for( Joueur joueur : this.tableGagnantsPerdants.keySet() ) {
			
			Joueur clone = cloneJoueurs.get( joueur.getNom() );
			if( clone == null ) throw new CloneNotSupportedException();
			
			boolean aPrecedemmentGagne = this.tableGagnantsPerdants.get(joueur);
			strategie.tableGagnantsPerdants.put( clone, aPrecedemmentGagne );
							
		}
		
		for( Joueur gagnant : this.gagnants ) {
			Joueur clone = cloneJoueurs.get( gagnant.getNom() );
			if( clone == null ) throw new CloneNotSupportedException();
			strategie.gagnants.add(clone);
		}
		
		for( Joueur perdant : this.perdants ) {
			Joueur clone = cloneJoueurs.get( perdant.getNom() );
			if( clone == null ) throw new CloneNotSupportedException();
			strategie.perdants.add(clone);
		}
		
		return strategie;
	}
	
	
	
	
	@Override
	public void ajouterJoueur( Joueur joueur ) throws JoueurDejaExistantException {
		
		this.tableDesRencontres.ajouterJoueur(joueur);
		this.tableGagnantsPerdants.put( joueur, false );		
		this.perdants.add(joueur);
		
	}

	@Override
	public void supprimerJoueur( Joueur joueur ) {

		this.tableDesRencontres.supprimerJoueur(joueur);
		this.tableGagnantsPerdants.remove(joueur);
		
		this.gagnants.remove(joueur);
		this.perdants.remove(joueur);
		
	}

	
	
	
	@Override
	public Collection<Joueur> getJoueursDisponibles() {
		ArrayList<Joueur> joueursDisponibles = new ArrayList<Joueur>( this.gagnants );
		joueursDisponibles.addAll( this.perdants );
		return joueursDisponibles;
	}

	@Override
	public int getNbJoueursDisponibles() {
		return this.gagnants.size() + this.perdants.size();
	}

	
	
	
	
	
	@Override
	public List<Match> genererMatchs() {
				
		List<Match> matchs = this.genererMatchsGagnants();
				
		matchs.addAll( this.genererMatchsPerdants() );
				
		if( this.gagnants.size() > 0 && this.perdants.size() > 0 ) {
			
			Joueur joueurGagnant = this.gagnants.get(0);
			Joueur joueurPerdant = this.perdants.get(0);
			
			this.gagnants.remove(joueurGagnant);
			this.perdants.remove(joueurPerdant);
			
			Match match = new Match( joueurGagnant, joueurPerdant );
			matchs.add(match);
		}

		return matchs;
	}

	private List<Match> genererMatchsGagnants() {
		return this.genererMatchsPourJoueurs( this.gagnants );
	}
	
	private List<Match> genererMatchsPerdants() {
		return this.genererMatchsPourJoueurs( this.perdants );
	}
	
	
	private List<Match> genererMatchsPourJoueurs( List<Joueur> listejoueurs ) {
		
		List<Match> matchs = new ArrayList<Match>();
				
		while(true) {
			
			int nbJoueurs = listejoueurs.size();
			if( nbJoueurs < 2 ) break;
			
			int indiceJoueur = Aleatoire.getNombreAleatoire( nbJoueurs - 1 );
			Joueur joueur = listejoueurs.get(indiceJoueur);
			listejoueurs.remove(joueur);
			
			List<Joueur> adversairesPotentiels = this.tableDesRencontres.getAdversairesPotentiel( joueur, listejoueurs ); 
			
			int nbAdversaires = adversairesPotentiels.size();
			int indiceAdversairePotentiel = Aleatoire.getNombreAleatoire( nbAdversaires - 1 );
			Joueur adversaire = adversairesPotentiels.get(indiceAdversairePotentiel);
			listejoueurs.remove(adversaire);
								
			Match match = new Match( joueur, adversaire );
			matchs.add(match);
			
		}
				
		return matchs;
	}
	
	
	
	
	@Override
	public void retirerJoueursDisponibles( Joueur... joueurs ) {
		
		for( Joueur joueur : joueurs ) {
			
			this.gagnants.remove(joueur);
			this.perdants.remove(joueur);
			
		}
		
	}
	
	@Override
	public void resoudreMatchNormal( Joueur joueurGagnant, Joueur joueurPerdant ) {
				
		this.tableGagnantsPerdants.put( joueurGagnant, true );
		this.tableGagnantsPerdants.put( joueurPerdant, false );
		
		this.gagnants.add(joueurGagnant);
		this.perdants.add(joueurPerdant);
		
		this.tableDesRencontres.rencontreJoueurs( joueurGagnant, joueurPerdant ); 
	}

	@Override
	public void resoudreMatchParAbandon( Joueur joueurGagnant, Joueur joueurPerdant ) {

		this.tableGagnantsPerdants.put( joueurGagnant, true );
		this.tableGagnantsPerdants.put( joueurPerdant, false );
		
		this.gagnants.add(joueurGagnant);
		this.perdants.add(joueurPerdant);
		
		this.tableDesRencontres.rencontreJoueurs( joueurGagnant, joueurPerdant ); 
		
	}

	@Override
	public void resoudreMatchNull( Joueur joueur1, Joueur joueur2 ) {

		this.tableGagnantsPerdants.put( joueur1, false );
		this.tableGagnantsPerdants.put( joueur2, false );
		
		this.perdants.add(joueur1);
		this.perdants.add(joueur2);
		
		this.tableDesRencontres.rencontreJoueurs( joueur1, joueur2 ); 
		
	}

	@Override
	public void resoudreMatchSupprime( Joueur joueur1, Joueur joueur2 ) {
		this.remettreJoueurDisponible(joueur1);
		this.remettreJoueurDisponible(joueur2);		
	}

	
	private void remettreJoueurDisponible( Joueur joueur ) {
		
		if( this.tableGagnantsPerdants.get(joueur) ) this.gagnants.add(joueur);
		else this.perdants.add(joueur);
		
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
