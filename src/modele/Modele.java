package modele;

import java.util.Collection;
import java.util.List;
import java.util.Observable;

import tournoi.Joueur;
import tournoi.JoueurDejaExistantException;
import tournoi.Match;
import tournoi.Tournoi;

public class Modele extends Observable {

	protected Tournoi tournoi;
		
	public Modele() {
		
		this.tournoi = new Tournoi();
		
	}
	

	public void initialiser() {
		this.setChanged();
		this.notifyObservers();
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
	
	
	
	
	public Collection<Joueur> getJoueurs() {	
		return this.tournoi.getJoueurs();		
	}
		
	public Collection<Joueur> getJoueursDisponibles() {
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
		
	public List<Match> getMatchs() {
		return this.tournoi.getMatchs();
	}
	
	public boolean possedeMatchsGeneres() {
		return this.tournoi.possedeMatchsGeneres();
	}
	
	
	
	
	public void resoudreMatchNormalGagnantJoueur1( Match match ) {
		this.tournoi.resoudreMatchNormalGagnantJoueur1(match);
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void resoudreMatchNormalGagnantJoueur2( Match match ) {
		this.tournoi.resoudreMatchNormalGagnantJoueur2(match);
		
		this.setChanged();
		this.notifyObservers();
	}

	public void resoudreMatchAbandonJoueur1( Match match ) {
		this.tournoi.resoudreMatchAbandonJoueur1(match);
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void resoudreMatchAbandonJoueur2( Match match ) {
		this.tournoi.resoudreMatchAbandonJoueur2(match);
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void resoudreMatchNull( Match match ) {
		this.tournoi.resoudreMatchNull(match);
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void resoudreMatchSupprime( Match match ) {
		this.tournoi.resoudreMatchSupprime(match);
		
		this.setChanged();
		this.notifyObservers();
	}
	
}
