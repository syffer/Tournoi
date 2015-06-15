package modele;

import java.util.List;
import java.util.Observable;

public class Modele extends Observable {

	protected Tournoi tournoi;
	
	private boolean joueurSelectionne;
	private boolean matchSelectionne;
	
	public Modele() {
		
		this.tournoi = new Tournoi();
		
		this.joueurSelectionne = false;
		this.matchSelectionne = false;
	}
	

	public void initialiser() {
		this.setChanged();
		this.notifyObservers();
	}
	
	
	
	
	public boolean isJoueurSelectionne() {
		return this.joueurSelectionne;
	}
	
	public boolean isMatchSelectionne() {
		return this.matchSelectionne;
	}
	
	public void setJoueurSelectionne( boolean joueurSelectionne ) {
		this.joueurSelectionne = joueurSelectionne;
	
		this.setChanged();
		this.notifyObservers(false);
	}
	
	public void setMatchSelectionne( boolean matchSelectionne ) {
		this.matchSelectionne = matchSelectionne;
		
		this.setChanged();
		this.notifyObservers(false);
	}
	
	
	
	
	public void ajouterJoueur( String nomJoueur ) throws JoueurDejaExistantException {
		this.ajouterJoueur( new Joueur(nomJoueur) );
	}
	
	public void ajouterJoueur( Joueur joueur ) throws JoueurDejaExistantException {
		this.tournoi.ajouterJoueur(joueur);
				
		this.setChanged();
		this.notifyObservers();		
	}
	
	public void supprimerJoueur( Joueur joueur ) {
		this.tournoi.supprimerJoueur(joueur);
		
		this.setChanged();
		this.notifyObservers();
	}
	
	
	
	
	public List<Joueur> getJoueurs() {	
		return this.tournoi.getJoueurs();		
	}
		
	public List<Joueur> getJoueursDisponibles() {
		return this.tournoi.getJoueursDisponibles();
	}
	
	public boolean isJoueursDisponibles() {
		return this.tournoi.isJoueursDisponibles();
	}
	
	
	
	
	
	public void genererMatchs() {
		this.tournoi.genererMatchs();
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void annulerMatchs( Joueur joueur ) {
		this.tournoi.annulerMatchs(joueur);
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void annulerMatchs() {
		this.tournoi.annulerMatchs();
		
		this.setChanged();
		this.notifyObservers();
	}
	
	
	
	
	public void ajouterMatch( Joueur joueur1, Joueur joueur2 ) {
		this.tournoi.ajouterMatch( joueur1, joueur2 );
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void supprimerMatch( Match match ) {
		this.tournoi.supprimerMatch(match);
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public List<Match> getMatchs() {
		return this.tournoi.getMatchs();
	}
	
	public boolean possedeMatchsGeneres() {
		return this.tournoi.possedeMatchsGeneres();
	}
	
	
	public void resoudreMatchNormal( Match match, Joueur gagnant ) throws MatchException {
		this.tournoi.resoudreMatchNormal( match, gagnant );
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void resoudreMatchParAbandon( Match match, Joueur joueurAbandonne ) throws MatchException {
		this.tournoi.resoudreMatchParAbandon( match, joueurAbandonne );
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void resoudreMatchNull( Match match ) {
		this.tournoi.resoudreMatchNull(match);
		
		this.setChanged();
		this.notifyObservers();
	}
	
	
	
}
