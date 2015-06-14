package modele;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ModeleSerializable extends Modele implements Serializable {
	
	private static final long serialVersionUID = 4839704101091397361L;
	
	private boolean modifie;

	public ModeleSerializable() {
		super();
		
		this.modifie = false;		
	}
	
	
	public void reinitialiserTournoi() {
				
		this.tournoi = new Tournoi();
		
		this.setChanged();
		this.notifyObservers();
	}
	
	
	public boolean isModifie() {
		return this.modifie;
	}
	
	
	
	public void chargerTournoi( File fichier ) {
		
		this.modifie = false;
		
		
	}
	
	public void sauvegarderTournoi( File fichierDeSauvegarde ) throws IOException {
		
		
		FileOutputStream fos = new FileOutputStream(fichierDeSauvegarde);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		oos.writeObject( this.tournoi );
		
		oos.close();
		fos.close();		
		
		this.modifie = false;
		
	}
	
	
}
