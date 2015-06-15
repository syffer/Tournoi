package vue;

import internationalisation.Constantes;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class JFileChooserConfirm extends JFileChooser {
	
	private static final long serialVersionUID = 61185538418586982L;
	
	public JFileChooserConfirm() {
		super();
		
	}
	
	@Override
    public void approveSelection() {
		
        File fichier = getSelectedFile();
        
        if( fichier.exists() && getDialogType() == SAVE_DIALOG ) {
        	
        	String titrePopup = Constantes.getString( Constantes.TITRE_POPUP_FICHIER_DEJA_EXISTANT );
        	String message = Constantes.getString( Constantes.MESSAGE_QUESTION_REMPLACER_FICHIER_EXISTANT );
            int result = JOptionPane.showConfirmDialog( this, message, titrePopup, JOptionPane.YES_NO_CANCEL_OPTION );
            
            switch(result){
                case JOptionPane.YES_OPTION:
                    super.approveSelection();
                    return;
                case JOptionPane.NO_OPTION:
                    return;
                case JOptionPane.CLOSED_OPTION:
                    return;
                case JOptionPane.CANCEL_OPTION:
                    cancelSelection();
                    return;
            }
        }
        
        super.approveSelection();
        
    } 
	
	
}
