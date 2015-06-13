package controleur;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;

import vue.VueUndoable;
import modele.ModeleUndoable;

public class ControleurUndoable extends Controleur {

	public ControleurUndoable( ModeleUndoable modeleUndoable ) {
		super( modeleUndoable, new VueUndoable() );
		
		
		ActionUndo actionUndo = new ActionUndo();
		ActionRedo actionRedo = new ActionRedo();
		
		
		
		
	}
	
	
	public class ActionUndo extends AbstractAction implements Observer {
		
		private static final long serialVersionUID = -3578057936698716615L;

		public ActionUndo() {
			
		}
		
		@Override
		public void actionPerformed( ActionEvent event ) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void update( Observable observable, Object args ) {
			// TODO Auto-generated method stub
			
		}
		
	}
	

	public class ActionRedo extends AbstractAction implements Observer {
		
		private static final long serialVersionUID = -3578057936608716615L;

		public ActionRedo() {
			
		}
		
		@Override
		public void actionPerformed( ActionEvent event ) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void update( Observable observable, Object args ) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	
}
