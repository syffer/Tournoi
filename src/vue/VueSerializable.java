package vue;

import java.io.File;

import internationalisation.Constantes;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class VueSerializable extends Vue {
	
	private static final long serialVersionUID = 7863749255941519681L;

	public JFileChooser fileChooserOpen;
	public JFileChooser fileChooserSave;
	
	public JMenuItem menuNouveauFichier;
	public JMenuItem menuOuvrirFichier;
	public JMenuItem menuSauvegarder;
	
	
	public VueSerializable() {
		super();
		
		this.ajouterFenettreSelectionFichier();
		this.ajouterMenuFichier();
		
		this.pack();
	}
	
	
	private void ajouterFenettreSelectionFichier() {
		
		this.fileChooserOpen = new JFileChooser();
		this.fileChooserSave = new JFileChooser();
		
		FileFilter filter = new FileNameExtensionFilter( "Tournoi file", "tournoi" );
		
		this.fileChooserOpen.setFileFilter(filter);
		this.fileChooserSave.setFileFilter(filter);
		
	}
	
	private void ajouterMenuFichier() {
		
		JMenu menuFichier = new JMenu( Constantes.getString(Constantes.TITRE_MENU_FICHIER) );
		this.menuNouveauFichier = new JMenuItem( Constantes.getString(Constantes.NOUVEAU_TOURNOI) );
		this.menuOuvrirFichier = new JMenuItem( Constantes.getString(Constantes.CHARGER_TOURNOI) );
		this.menuSauvegarder = new JMenuItem( Constantes.getString(Constantes.SAUVEGARDER_TOURNOI) );
		menuFichier.add( this.menuNouveauFichier );
		menuFichier.add( this.menuOuvrirFichier );
		menuFichier.add( this.menuSauvegarder );
		
		JMenuBar menuBar = this.getJMenuBar();
		menuBar.add(menuFichier);
		
	}
	
	
	
	
	public File getFichierCharge() throws ChoixAnnulerException {
		
		int resultat = this.fileChooserOpen.showOpenDialog(this);
		
		if( resultat != JFileChooser.APPROVE_OPTION ) throw new ChoixAnnulerException("changement annul�");
		
		File fichier = this.fileChooserOpen.getSelectedFile();
		
		return fichier;
	}
	
	
	public File getFichierSauvegarde() throws ChoixAnnulerException {
		
		int resultat = this.fileChooserSave.showSaveDialog(this);
		
		if( resultat != JFileChooser.APPROVE_OPTION ) throw new ChoixAnnulerException("sauvegarde annul�e");
		
		File fichier = this.fileChooserSave.getSelectedFile();
		
		return fichier;
	}
	
	
}
