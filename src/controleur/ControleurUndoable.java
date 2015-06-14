package controleur;

import internationalisation.Constantes;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import vue.VueUndoable;
import modele.ModeleUndoManager;
import modele.ModeleUndoable;

public class ControleurUndoable extends Controleur {

	public ControleurUndoable( ModeleUndoable modeleUndoable ) {
		super( modeleUndoable, new VueUndoable() );
		
		
		ActionUndo actionUndo = new ActionUndo();
		ActionRedo actionRedo = new ActionRedo();
		
		VueUndoable vueUndoable = (VueUndoable) this.vue;
		
		vueUndoable.menuAnnuler.setAction(actionUndo);
		vueUndoable.menuRefaire.setAction(actionRedo);
		
		modeleUndoable.getModeleUndoManager().initialiser();
	}
	
	
	public class ActionUndo extends AbstractAction implements Observer {
		
		private static final long serialVersionUID = -3573057936698716615L;
		
		private ModeleUndoManager undoManager;
		
		public ActionUndo() {
			super( Constantes.getString(Constantes.UNDO) );
			
			ModeleUndoable modeleUndoable = (ModeleUndoable) ControleurUndoable.this.modele;
			this.undoManager = modeleUndoable.getModeleUndoManager();
			this.undoManager.addObserver(this);
			
			this.putValue( AbstractAction.NAME, Constantes.getString(Constantes.UNDO) );
			this.putValue( AbstractAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK) ); 	// Ctrl + S
		}
		
		@Override
		public void actionPerformed( ActionEvent event ) {
			this.undoManager.undo();
		}

		@Override
		public void update( Observable observable, Object args ) {
			this.setEnabled( this.undoManager.canUndo() );
		}
		
	}
	

	public class ActionRedo extends AbstractAction implements Observer {
		
		private static final long serialVersionUID = -3578057936608716615L;

		private ModeleUndoManager undoManager;
		
		public ActionRedo() {
			super( Constantes.getString(Constantes.REDO) );
			
			ModeleUndoable modeleUndoable = (ModeleUndoable) ControleurUndoable.this.modele;
			this.undoManager = modeleUndoable.getModeleUndoManager();
			this.undoManager.addObserver(this);		
									
			this.putValue( AbstractAction.NAME, Constantes.getString(Constantes.REDO) );
			this.putValue( AbstractAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK) ); 	// Ctrl + Y
		}
		
		@Override
		public void actionPerformed( ActionEvent event ) {
			this.undoManager.redo();
		}

		@Override
		public void update( Observable observable, Object args ) {
			this.setEnabled( this.undoManager.canRedo() );
		}
		
	}
	
	
	
}
