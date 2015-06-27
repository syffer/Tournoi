package tournoi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class TableDesRencontres implements Cloneable, Serializable {
	
	private static final long serialVersionUID = 7677186960372700538L;
	
	private Set<String> joueurs;
	private Map< Paire<String, String>, Integer > matrice;

	
	public TableDesRencontres() {
		
		this.joueurs = new HashSet<String>();
		this.matrice = new HashMap< Paire<String, String>, Integer >();
				
	}

	
	public void ajouterJoueur( Joueur nouveauJoueur ) throws JoueurDejaExistantException {
		
		String nomNouveauJoueur = nouveauJoueur.getNom();
		if( this.joueurs.contains(nomNouveauJoueur) ) throw new JoueurDejaExistantException();
		
		// pour chaque joueur déjà existant, on ajoute une nouvelle ligne entre lui et le nouveau joueur
		for( String nomJoueur : this.joueurs ) {
			
			Paire<String, String> paire = TableDesRencontres.getPaire( nomJoueur, nomNouveauJoueur );
			this.matrice.put( paire,  0 );
			
		}
		
		// on ajoute le joueur à la liste des joueurs
		this.joueurs.add(nomNouveauJoueur);
			
	}
	
	
	public void supprimerJoueur( Joueur joueur ) {
		
		String nomJoueur = joueur.getNom();
		this.joueurs.remove(nomJoueur);
		
		Iterator< Paire<String, String> > iterateur = this.matrice.keySet().iterator();
		while( iterateur.hasNext() ) {
			
			Paire<String, String> paire = iterateur.next();
			if( nomJoueur.equals( paire.u ) || nomJoueur.equals( paire.v ) ) this.matrice.remove(paire);
			
		}
		
	}
	
	
	public void rencontreJoueurs( Joueur joueur1, Joueur joueur2 ) {
		
		Paire<String, String> paire = TableDesRencontres.getPaire( joueur1, joueur2 );
				
		int nbRencontre = this.matrice.get(paire);
		
		nbRencontre++;
		
		this.matrice.put( paire, nbRencontre );
		
	}
	
	
	public int getNbRencontres( Joueur joueur1, Joueur joueur2 ) {
		Paire<String, String> paire = TableDesRencontres.getPaire( joueur1, joueur2 );
		return this.matrice.get(paire);
	}
	
	
	
	private static Paire<String, String> getPaire( Joueur joueur1, Joueur joueur2 ) {
		return TableDesRencontres.getPaire( joueur1.getNom(), joueur2.getNom() );
	}
	
	private static Paire<String, String> getPaire( String nomJoueur1, String nomJoueur2 ) {
		if( nomJoueur1.compareTo(nomJoueur2) < 0 ) return new Paire<String, String>( nomJoueur1, nomJoueur2 );
		else return new Paire<String, String>( nomJoueur2, nomJoueur1 );
	}
	
	
	public List<Joueur> getAdversairesPotentiel( Joueur joueur, List<Joueur> adversairesPossibles ) {
		
		// pas de ligne pour le joueur dans la table des rencontres.
		//if( ! this.matriceDesRencontres.containsKey(joueur) ) throw new TableDesRencontresException("Le joueur n'existe pas dans la table des rencontres (il ne possède pas de ligne qui lui est associée).");
		
		List<Joueur> adversairesPotentiels = new ArrayList<Joueur>();
		Integer nbRencontresMin = null;
		
		for( Joueur adversaire : adversairesPossibles ) {
			
			Paire<String, String> paire = TableDesRencontres.getPaire( joueur, adversaire );
			
			Integer nbRencontres = this.matrice.get(paire);
			
			if( nbRencontresMin == null || nbRencontres < nbRencontresMin ) {
				nbRencontresMin = nbRencontres;	
				adversairesPotentiels.removeAll(adversairesPotentiels);		// on supprime les anciens adversaires potentiels
			}
			
			if( nbRencontresMin == nbRencontres ) adversairesPotentiels.add(adversaire);	
			
		}
		
		return adversairesPotentiels;
				
	}
	
	
	
	@Override
	public TableDesRencontres clone() {
		
		try {
			
			TableDesRencontres table = (TableDesRencontres) super.clone();
			
			table.joueurs = new HashSet<String>( this.joueurs.size() );
			table.matrice = new HashMap< Paire<String,String>, Integer >( this.matrice.size() );
			
			for( String nomJoueur : this.joueurs ) {
				table.joueurs.add( new String(nomJoueur) );
			}
			
			for( Paire<String, String> paire : this.matrice.keySet() ) {
				int nbRenconInteger = this.matrice.get(paire);
				table.matrice.put( paire, nbRenconInteger );
			}
									
			return table;
			
		}
		catch( CloneNotSupportedException e ) {
			throw new InternalError("clonage impossible");
		}
		
	}
	

}
