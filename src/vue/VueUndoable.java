package vue;

import internationalisation.Constantes;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class VueUndoable extends Vue {
	
	private static final long serialVersionUID = -5326637994371445805L;

	public JMenuItem menuAnnuler;
	public JMenuItem menuRefaire;
	
	
	public VueUndoable() {
		super();
				
		JMenu menuEditer = new JMenu( Constantes.getString(Constantes.TITRE_MENU_EDITION) );
		this.menuAnnuler = new JMenuItem( Constantes.getString(Constantes.UNDO) );
		this.menuRefaire = new JMenuItem( Constantes.getString(Constantes.REDO) );
		menuEditer.add( this.menuAnnuler );
		menuEditer.add( this.menuRefaire );
		
		JMenuBar menuBar = this.getJMenuBar();
		menuBar.add(menuEditer);
		
		this.pack();
	}
	
	

}
