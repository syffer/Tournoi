package modele;

import java.util.Observable;

import javax.swing.undo.StateEdit;
import javax.swing.undo.UndoManager;

public class ModeleUndoManager extends Observable {

	private UndoManager undoManager;
	
	public ModeleUndoManager() {
		
		this.undoManager = new UndoManager();
		
	}
	
	public void undo() {

		this.undoManager.undo();
		
		this.setChanged();
		this.notifyObservers();
	}
	
	
	public void redo() {
		
		this.undoManager.redo();
		
		this.setChanged();
		this.notifyObservers();
		
	}
	
	public boolean canUndo() {
		return this.undoManager.canUndo();
	}
	
	public boolean canRedo(){
		return this.undoManager.canRedo();
	}
	
	
	public void ajouterEtat( StateEdit etat ) {
		
		this.undoManager.addEdit(etat);
		
		// on a ajouté un état, on prévient les observeurs (les boutons)
		this.setChanged();
		this.notifyObservers();
	}
	
}
