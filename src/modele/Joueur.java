package modele;

public class Joueur {
	
	private String nom;
	private int nbPoints;
	
	public Joueur( String nom, int nbPoints ) {
		this.nom = nom;
		this.nbPoints = nbPoints;
	}
	
	public Joueur( String nom ) {
		this( nom, 0 );
	}
	
	
	
	public void incrementerNbPoints( int nbPoints ) {
		this.nbPoints += nbPoints;
	}
	
	
	public String toString() {
		return this.nom;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public int getNbPoints() {
		return this.nbPoints;
	}
	
	
	
}
