package modele;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import tournoi.Joueur;
import tournoi.JoueurDejaExistantException;
import tournoi.Match;
import tournoi.Tournoi;


public class ModeleSerializable extends Modele implements Serializable {
	
	private static final long serialVersionUID = 4839704101091397361L;
	
	private boolean modifie;
	private File fichierDeSauvegarde;	// null si aucun fichier
	
	public ModeleSerializable() {
		super();
		
		this.modifie = false;	
		this.fichierDeSauvegarde = null;
	}
	
	
	public void reinitialiserTournoi() {
		
		this.tournoi = new Tournoi();
		
		this.modifie = false;
		this.fichierDeSauvegarde = null;
		
		this.setChanged();
		this.notifyObservers();
	}
	
	
	public boolean isModifie() {
		return this.modifie;
	}
	
	public boolean possedeUnFichierDeSauvegarde() {
		return this.fichierDeSauvegarde != null;
	}
	
	
	public void chargerTournoi( File fichier ) throws IOException, ClassNotFoundException {
		
		FileInputStream fis = null;
		ObjectInputStream in = null;
		
		try {
			
			fis = new FileInputStream(fichier);
			in = new ObjectInputStream(fis);
			
			Tournoi tournoi = (Tournoi) in.readObject();
			
			this.tournoi = tournoi;
			this.modifie = false;
			this.fichierDeSauvegarde = fichier;
			
			this.setChanged();
			this.notifyObservers();
			
		}
		finally {
			in.close();
			fis.close();
		}
		
	}
	
	public void sauvegarderTournoi( File fichier ) throws IOException {
		
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		
		try {
			
			fos = new FileOutputStream(fichier);
			out = new ObjectOutputStream(fos);
					
			out.writeObject( this.tournoi );
			
			this.modifie = false;
			this.fichierDeSauvegarde = fichier;
					
			this.setChanged();
			this.notifyObservers();
			
		}
		finally {
			out.close();
			fos.close();
		}
		
	}
	
	public void sauvegarderTournoi() throws ModeleSerializableException, IOException {
		
		if( ! this.possedeUnFichierDeSauvegarde() ) throw new ModeleSerializableException("pas de fichier de sauvegarde");
		
		this.sauvegarderTournoi( this.fichierDeSauvegarde );
		
	}
	
	
	
	
	
	
	
	public void ajouterJoueur( String nomJoueur ) throws JoueurDejaExistantException {
		this.ajouterJoueur( new Joueur(nomJoueur) );
	}
	
	public void ajouterJoueur( Joueur nouveauJoueur ) throws JoueurDejaExistantException {
		this.modifie = true;
		super.ajouterJoueur(nouveauJoueur);
	}
	
	public void supprimerJoueur( Joueur joueur ) {
		this.modifie = true;
		super.supprimerJoueur(joueur);
	}
	
	
	public void genererMatchs() {
		this.modifie = true;
		super.genererMatchs();
	}
	
	public void annulerMatchs( Joueur joueur ) {
		this.modifie = true;
		super.annulerMatchs(joueur);
	}
	
	public void annulerMatchs() {
		this.modifie = true;
		super.annulerMatchs();
	}
	
	
	public void ajouterMatch( Joueur joueur1, Joueur joueur2 ) {
		this.modifie = true;
		super.ajouterMatch( joueur1, joueur2 );
	}
	
	public void supprimerMatch( Match match ) {
		this.modifie = true;
		super.supprimerMatch(match);
	}
	
	
	
	
	
	public void resoudreMatchNormalGagnantJoueur1( Match match ) {
		this.modifie = true;
		super.resoudreMatchNormalGagnantJoueur1(match);
	}
	
	public void resoudreMatchNormalGagnantJoueur2( Match match ) {
		this.modifie = true;
		super.resoudreMatchNormalGagnantJoueur2(match);
	}

	public void resoudreMatchAbandonJoueur1( Match match ) {
		this.modifie = true;
		super.resoudreMatchAbandonJoueur1(match);
	}
	
	public void resoudreMatchAbandonJoueur2( Match match ) {
		this.modifie = true;
		super.resoudreMatchAbandonJoueur2(match);
	}
		
	public void resoudreMatchNull( Match match ) {
		this.modifie = true;
		super.resoudreMatchNull(match);
	}
	
}
