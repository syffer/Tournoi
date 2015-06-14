package modele;

import java.io.File;
import java.io.Serializable;

public class ModeleSerializable extends Modele implements Serializable {
	
	private static final long serialVersionUID = 4839704101091397361L;
	

	public ModeleSerializable() {
		super();
		
		
		
		
	}
	
	
	public void reinitialiserTournoi() {
		this.tournoi = new Tournoi();
		
		this.setChanged();
		this.notifyObservers();
	}
	
	
	public void chargerTournoi( File fichier ) {
		
		
	}
	
	public void sauvegarderTournoi( File fichier ) {
		
	}
	
	
}
