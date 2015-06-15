package vue;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class JFileChooserConfirm extends JFileChooser {
	
	private static final long serialVersionUID = 61185538418586982L;
	
	@Override
    public void approveSelection() {
		
        File fichier = getSelectedFile();
        
        if( fichier.exists() && getDialogType() == SAVE_DIALOG ) {
            int result = JOptionPane.showConfirmDialog( this, "The file exists, overwrite?", "Existing file", JOptionPane.YES_NO_CANCEL_OPTION );
            
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
