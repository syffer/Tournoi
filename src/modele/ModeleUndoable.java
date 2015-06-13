package modele;

import java.util.Hashtable;

import javax.swing.undo.StateEdit;
import javax.swing.undo.StateEditable;

public class ModeleUndoable extends Modele implements StateEditable {

	private ModeleUndoManager undoManager;
	
	public ModeleUndoable() {
		super();
		
		this.undoManager = new ModeleUndoManager();
	}

	@Override
	public void restoreState( Hashtable<?, ?> state ) {

		
	}

	@Override
	public void storeState( Hashtable<Object, Object> state ) {

		
	}
	
	
	
	public void ajouterJoueur( String nomJoueur ) throws JoueurDejaExistantException {
		
	}
	
	public void ajouterJoueur( Joueur nouveauJoueur ) throws JoueurDejaExistantException { 
		
	}
	
	public void supprimerJoueur( Joueur joueur ) {
		
		StateEdit nouvelEtat = new StateEdit(this);
		
		super.supprimerJoueur(joueur);
		
		nouvelEtat.end();
		
		this.undoManager.ajouterEtat(nouvelEtat);
	}
	
	
	
	public void genererMatchs() {
		
	}
	
	public void annulerMatchs( Joueur joueur ) {
		
	}
	
	public void annulerMatchs() {
		
	}
	
	
	
	public void ajouterMatch( Joueur joueur1, Joueur joueur2 ) {
		
	}
	
	public void supprimerMatch( Match match ) {
		
	}
	
	
	
	public void resoudreMatchNormal( Match match, Joueur gagnant ) throws MatchException {
		
	}
	
	public void resoudreMatchParAbandon( Match match, Joueur joueurAbandonne ) throws MatchException {
		
		
	}
	
	public void resoudreMatchNull( Match match ) {
		
	}
	

}
