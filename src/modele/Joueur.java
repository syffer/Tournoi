package modele;

import java.io.Serializable;

public class Joueur implements Comparable<Joueur>, Cloneable, Serializable {
	
	private static final long serialVersionUID = 6879263410820631136L;
	
	private String nom;
	private int nbPoints;
	
	private enum Etat {
		Gagnant,
		Perdant,
	}
	
	private Etat etat;
	private boolean enMatch;
	
	public Joueur( String nom, int nbPoints ) {
		this.nom = nom;
		this.nbPoints = nbPoints;
		
		this.etat = Etat.Perdant;
		this.enMatch = false;
	}
	
	public Joueur( String nom ) {
		this( nom, 0 );
	}
	
	
	public void incrementerNbPoints( int nbPoints ) {
		this.nbPoints += nbPoints;
	}
	
	
	public void gagne() {
		this.etat = Etat.Gagnant;
	}
	
	public void perd() {
		this.etat = Etat.Perdant;
	}
	
	
	public boolean aGagne() {
		return this.etat == Etat.Gagnant;
	}
	
	public boolean aPerdu() {
		return this.etat == Etat.Perdant;
	}
	
	
	public boolean estEnMatch() {
		return this.enMatch;
	}
	
	public void setEnMatch( boolean enMatch ) {
		this.enMatch = enMatch;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public int getNbPoints() {
		return this.nbPoints;
	}
	
	
	@Override
	public String toString() {
		return this.nom;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Joueur other = (Joueur) obj;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		return true;
	}

	@Override
	public int compareTo( Joueur joueur ) {
		/* a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
		 * this > joueur :  1 (positif integer)
		 * this = joueur :  0
		 * this < joueur : -1 (negatif integer)
		 */
		
		return this.getNom().compareTo( joueur.getNom() );
		
		/*
		if( this.nbPoints == joueur.getNbPoints() ) return 0;
		else if( this.nbPoints > joueur.getNbPoints() ) return 1;
		else return -1;
		*/
	}
	
	
	@Override
	public Joueur clone() {
		
		try {
			
			Joueur joueur = (Joueur) super.clone();
			
			return joueur;	
		}
		catch( CloneNotSupportedException e ) {
			throw new InternalError("clonage impossible");
		}
		
	}
	
}
