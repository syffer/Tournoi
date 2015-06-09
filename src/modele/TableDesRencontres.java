package modele;

import java.util.HashMap;

public class TableDesRencontres {

	private HashMap< Joueur, HashMap<Joueur, Integer> > matriceDesRencontres;
	
	
	public TableDesRencontres() {
		
		this.matriceDesRencontres = new HashMap< Joueur, HashMap<Joueur,Integer> >();
		
	}

	
	public void ajouterJoueur( Joueur nouveauJoueur ) throws JoueurDejaExistantException {
		
		if( this.matriceDesRencontres.containsKey(nouveauJoueur) ) throw new JoueurDejaExistantException();
		
		// on crée un nouvelle ligne pour le nouveau joueur.
		HashMap<Joueur, Integer> ligneNouveauJoueur = new HashMap<Joueur, Integer>();
		
		for( Joueur ancienJoueur : this.matriceDesRencontres.keySet() ) {
			
			// pour chaque ancien joueur, on ajoute une nouvelle colonne concernant le nouveau joueur.
			HashMap<Joueur, Integer> ligneAncienJoueur = this.matriceDesRencontres.get(ancienJoueur);
			ligneAncienJoueur.put( nouveauJoueur, 0 );
			
			// pour le nouveau joueur, on ajoute une colonne concernant chaque ancien joueur.
			ligneNouveauJoueur.put( ancienJoueur, 0 );
			
		}
		
		// on ajoute la ligne du nouveau joueur au tableau des rencontres.
		this.matriceDesRencontres.put( nouveauJoueur, ligneNouveauJoueur );
		
	}
	
	
	public void supprimerJoueur( Joueur joueur ) {
		
		// on retire la ligne concernant le joueur à supprimer
		this.matriceDesRencontres.remove(joueur);
		
		for( Joueur j : this.matriceDesRencontres.keySet() ) {
			
			// on retire la colonne concernant le joueur à supprimer
			HashMap<Joueur, Integer> ligneJoueur = this.matriceDesRencontres.get(j);
			ligneJoueur.remove(joueur);
			
		}
				
	}
	
	
	
	

}
