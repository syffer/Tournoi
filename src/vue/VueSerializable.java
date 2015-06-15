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
	public JFileChooserConfirm fileChooserSave;
	
	public JMenuItem menuNouveauFichier;
	public JMenuItem menuOuvrirFichier;
	public JMenuItem menuSauvegarder;
	public JMenuItem menuSauvegarderSous;
	
	public VueSerializable() {
		super();
		
		this.ajouterFenettreSelectionFichier();
		this.ajouterMenuFichier();
		
		this.pack();
	}
	
	
	private void ajouterFenettreSelectionFichier() {
		
		this.fileChooserOpen = new JFileChooser();
		this.fileChooserSave = new JFileChooserConfirm();
		
		// permet de ne sélectionner que les fichier "*.tournoi" et "*.TOURNOI" dans les JFileChooser
		FileFilter filter = new FileNameExtensionFilter( Constantes.getString(Constantes.EXTENSION_FICHIER_TOURNOI), "tournoi", "TOURNOI" );
		
		this.fileChooserOpen.setFileFilter(filter);
		this.fileChooserSave.setFileFilter(filter);
		
		this.fileChooserOpen.setLocale( Constantes.LOCALE );
		this.fileChooserSave.setLocale( Constantes.LOCALE );
		
	}
	
	private void ajouterMenuFichier() {
		
		JMenu menuFichier = new JMenu( Constantes.getString(Constantes.TITRE_MENU_FICHIER) );
		this.menuNouveauFichier = new JMenuItem( Constantes.getString(Constantes.NOUVEAU_TOURNOI) );
		this.menuOuvrirFichier = new JMenuItem( Constantes.getString(Constantes.CHARGER_TOURNOI) );
		this.menuSauvegarder = new JMenuItem( Constantes.getString(Constantes.SAUVEGARDER_TOURNOI) );
		this.menuSauvegarderSous = new JMenuItem( Constantes.getString(Constantes.SAUVEGARDER_TOURNOI_SOUS) );
		menuFichier.add( this.menuNouveauFichier );
		menuFichier.add( this.menuOuvrirFichier );
		menuFichier.add( this.menuSauvegarder );
		menuFichier.add( this.menuSauvegarderSous );
		
		JMenuBar menuBar = this.getJMenuBar();
		menuBar.add(menuFichier);
		
	}
	
	
	
	
	public File getFichierCharge() throws ChoixAnnulerException {
		
		int resultat = this.fileChooserOpen.showOpenDialog(this);
		
		if( resultat != JFileChooser.APPROVE_OPTION ) throw new ChoixAnnulerException("changement annulé");
		
		File fichier = this.fileChooserOpen.getSelectedFile();
				
		return fichier;
		
	}
	
	
	public File getFichierSauvegarde() throws ChoixAnnulerException {
		
		int resultat = this.fileChooserSave.showSaveDialog(this);
		
		if( resultat != JFileChooser.APPROVE_OPTION ) throw new ChoixAnnulerException("sauvegarde annulée");
		
		File fichier = this.fileChooserSave.getSelectedFile();
		
		// on ajoute l'extention ".tournoi" si elle n'y est pas
		String cheminFichier = fichier.getAbsolutePath();
		if( ! cheminFichier.endsWith(".tournoi") && ! cheminFichier.endsWith(".TOURNOI") ) {
			fichier = new File( fichier.getAbsoluteFile() + ".tournoi" );
		}
				
		return fichier;
		
	}
	
	
}
