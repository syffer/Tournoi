package modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TableDesRencontres {

	private Map< Joueur, Map<Joueur, Integer> > matriceDesRencontres;
	
	
	public TableDesRencontres() {
		
		this.matriceDesRencontres = new HashMap< Joueur, Map<Joueur,Integer> >();
		
	}

	
	public void ajouterJoueur( Joueur nouveauJoueur ) throws JoueurDejaExistantException {
		
		if( this.matriceDesRencontres.containsKey(nouveauJoueur) ) throw new JoueurDejaExistantException();
		
		// on crée un nouvelle ligne pour le nouveau joueur.
		HashMap<Joueur, Integer> ligneNouveauJoueur = new HashMap<Joueur, Integer>();
		
		for( Joueur ancienJoueur : this.matriceDesRencontres.keySet() ) {
			
			// pour chaque ancien joueur, on ajoute une nouvelle colonne concernant le nouveau joueur.
			Map<Joueur, Integer> ligneAncienJoueur = this.matriceDesRencontres.get(ancienJoueur);
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
			Map<Joueur, Integer> ligneJoueur = this.matriceDesRencontres.get(j);
			ligneJoueur.remove(joueur);
			
		}
				
	}
	
	
	public List<Joueur> getAdversairesPotentiel( Joueur joueur, List<Joueur> adversairesPossibles ) {
		
		// pas de ligne pour le joueur dans la table des rencontres.
		//if( ! this.matriceDesRencontres.containsKey(joueur) ) throw new TableDesRencontresException("Le joueur n'existe pas dans la table des rencontres (il ne possède pas de ligne qui lui est associée).");
		
		Map<Joueur, Integer> ligneJoueur = this.matriceDesRencontres.get(joueur);
		
		
		List<Joueur> adversairesPotentiels = new ArrayList<Joueur>();
		
		if( ligneJoueur.isEmpty() ) return adversairesPotentiels;
		
		Integer nbRencontresMin = ligneJoueur.values().iterator().next();
		
		for( Joueur adversaire : adversairesPossibles ) {
			
			if( ! ligneJoueur.containsKey(adversaire) ) continue;
			
			Integer nbRencontres = ligneJoueur.get(adversaire);
			
			if( nbRencontresMin == null || nbRencontres < nbRencontresMin ) {
				nbRencontresMin = nbRencontres;
				adversairesPotentiels.removeAll(adversairesPotentiels);
			}
			
			if( nbRencontresMin == nbRencontres ) adversairesPotentiels.add(adversaire);	
			
		}
		
		return adversairesPotentiels;
		
		/*
		Map< Integer, List<Joueur> > listeAdversaires = new HashMap< Integer, List<Joueur> >();
		
		Map<Joueur, Integer> ligneJoueur = this.matriceDesRencontres.get(joueur);
				
		for( Joueur adversaire : adversairesPossibles ) {
			
			if( ! ligneJoueur.containsKey(adversaire) ) continue;
			
			Integer nbRencontres = ligneJoueur.get(adversaire);
			
			if( ! listeAdversaires.containsKey(nbRencontres) ) listeAdversaires.put( nbRencontres, new ArrayList<Joueur>() );
			List<Joueur> adversaires = listeAdversaires.get(nbRencontres);
			adversaires.add(adversaire);
			
		}
		
		if( listeAdversaires.isEmpty() ) return new ArrayList<Joueur>();
		
		Integer nbRencontresMin = Collections.min( listeAdversaires.keySet() );
		
		return listeAdversaires.get(nbRencontresMin);
		*/
	}
	
	
	

}
