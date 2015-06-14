package modele;

import java.util.Hashtable;

import javax.swing.undo.StateEdit;
import javax.swing.undo.StateEditable;

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
		
		if( tournoi != null ) this.tournoi = tournoi;
		
		
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
		
		super.reinitialiserTournoi();
		
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
	
	public void supprimerMatch( Match match ) {
		
		StateEdit nouvelEtat = new StateEdit(this);
		
		super.supprimerMatch(match);
		
		nouvelEtat.end();
		
		this.undoManager.ajouterEtat(nouvelEtat);
		
	}
	
	
	
	
	
	public void resoudreMatchNormal( Match match, Joueur gagnant ) throws MatchException {

		StateEdit nouvelEtat = new StateEdit(this);
		
		super.resoudreMatchNormal( match, gagnant );
		
		nouvelEtat.end();
		
		this.undoManager.ajouterEtat(nouvelEtat);
	}
	
	public void resoudreMatchParAbandon( Match match, Joueur joueurAbandonne ) throws MatchException {

		StateEdit nouvelEtat = new StateEdit(this);
		
		super.resoudreMatchParAbandon( match, joueurAbandonne );
		
		nouvelEtat.end();
		
		this.undoManager.ajouterEtat(nouvelEtat);
		
	}
	
	public void resoudreMatchNull( Match match ) {
		
		StateEdit nouvelEtat = new StateEdit(this);
		
		super.resoudreMatchNull(match);
		
		nouvelEtat.end();
		
		this.undoManager.ajouterEtat(nouvelEtat);
		
	}
	

}
