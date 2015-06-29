package modele;

import java.util.Hashtable;

import javax.swing.undo.StateEdit;
import javax.swing.undo.StateEditable;

import tournoi.Joueur;
import tournoi.JoueurDejaExistantException;
import tournoi.Match;
import tournoi.Tournoi;

public class ModeleUndoable extends ModeleSerializable implements StateEditable {
	
	private static final long serialVersionUID = 996735530260433305L;

	private ModeleUndoManager undoManager;
	
	private static final String CLE_TOURNOI = "tournoi";
	
	public ModeleUndoable() {
		super();
		
		this.undoManager = new ModeleUndoManager();
	}

	@Override
	public void restoreState( Hashtable<?, ?> state ) {

		Tournoi tournoi = (Tournoi) state.get( ModeleUndoable.CLE_TOURNOI );
		
		this.tournoi = tournoi;
		
		// on viens de charger un nouveau tournoi, il faut prévenir les actions / la vue
		this.setChanged();
		this.notifyObservers();
		
	}

	@Override
	public void storeState( Hashtable<Object, Object> state ) {
		
		state.put( ModeleUndoable.CLE_TOURNOI, this.tournoi.clone() );
		
	}
	
	
	public ModeleUndoManager getModeleUndoManager() {	
		return this.undoManager;
	}
	
	
	public void reinitialiserTournoi() {
		
		// on réinitialise le tournoi
		super.reinitialiserTournoi();
		
		// on vide la pile des états undo/redo
		this.undoManager.vider();
		
	}
	
		
	public void ajouterJoueur( String nomJoueur ) throws JoueurDejaExistantException {
		this.ajouterJoueur( new Joueur(nomJoueur) );
	}
	
	public void ajouterJoueur( Joueur nouveauJoueur ) throws JoueurDejaExistantException { 
		
		StateEdit nouvelEtat = new StateEdit(this);
		
		super.ajouterJoueur(nouveauJoueur);
		
		nouvelEtat.end();
		
		this.undoManager.ajouterEtat(nouvelEtat);
		
	}
	
	public void supprimerJoueur( Joueur joueur ) {
		
		StateEdit nouvelEtat = new StateEdit(this);
		
		super.supprimerJoueur(joueur);
		
		nouvelEtat.end();
		
		this.undoManager.ajouterEtat(nouvelEtat);
		
	}
	
	
	
	
	
	public void genererMatchs() {

		StateEdit nouvelEtat = new StateEdit(this);
		
		super.genererMatchs();
		
		nouvelEtat.end();
		
		this.undoManager.ajouterEtat(nouvelEtat);
		
	}
	
	public void annulerMatchs( Joueur joueur ) {

		StateEdit nouvelEtat = new StateEdit(this);
		
		super.annulerMatchs(joueur);
		
		nouvelEtat.end();
		
		this.undoManager.ajouterEtat(nouvelEtat);
		
	}
	
	public void annulerMatchs() {

		StateEdit nouvelEtat = new StateEdit(this);
		
		super.annulerMatchs();
		
		nouvelEtat.end();
		
		this.undoManager.ajouterEtat(nouvelEtat);
		
	}
	
	
	
	
	
	
	public void ajouterMatch( Joueur joueur1, Joueur joueur2 ) {

		StateEdit nouvelEtat = new StateEdit(this);
		
		super.ajouterMatch( joueur1, joueur2 );
		
		nouvelEtat.end();
		
		this.undoManager.ajouterEtat(nouvelEtat);
		
	}
	
	
	
	
	
	
	
	public void resoudreMatchNormalGagnantJoueur1( Match match ) {

		StateEdit nouvelEtat = new StateEdit(this);
		
		super.resoudreMatchNormalGagnantJoueur1(match);
		
		nouvelEtat.end();
		
		this.undoManager.ajouterEtat(nouvelEtat);
		
	}
	
	public void resoudreMatchNormalGagnantJoueur2( Match match ) {

		StateEdit nouvelEtat = new StateEdit(this);
		
		super.resoudreMatchNormalGagnantJoueur2(match);
		
		nouvelEtat.end();
		
		this.undoManager.ajouterEtat(nouvelEtat);
		
	}

	public void resoudreMatchAbandonJoueur1( Match match ) {

		StateEdit nouvelEtat = new StateEdit(this);
		
		super.resoudreMatchAbandonJoueur1(match);
		
		nouvelEtat.end();
		
		this.undoManager.ajouterEtat(nouvelEtat);
				
	}
	
	public void resoudreMatchAbandonJoueur2( Match match ) {

		StateEdit nouvelEtat = new StateEdit(this);
		
		super.resoudreMatchAbandonJoueur2(match);
		
		nouvelEtat.end();
		
		this.undoManager.ajouterEtat(nouvelEtat);
		
	}
	
	public void resoudreMatchNull( Match match ) {
		
		StateEdit nouvelEtat = new StateEdit(this);
		
		super.resoudreMatchNull(match);
		
		nouvelEtat.end();
		
		this.undoManager.ajouterEtat(nouvelEtat);
		
	}
	
	public void resoudreMatchSupprime( Match match ) {
		
		StateEdit nouvelEtat = new StateEdit(this);
		
		super.resoudreMatchSupprime(match);
		
		nouvelEtat.end();
		
		this.undoManager.ajouterEtat(nouvelEtat);
		
	}

}
