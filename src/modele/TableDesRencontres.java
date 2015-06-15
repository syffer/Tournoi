package modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TableDesRencontres implements Cloneable, Serializable {
	
	private static final long serialVersionUID = 7677186960372700538L;
	
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
	
	
	public void rencontreJoueurs( Joueur joueur1, Joueur joueur2 ) {
		
		int nbRencontreDuJoueur1 = this.matriceDesRencontres.get(joueur1).get(joueur2);
		int nbRencontreDuJoueur2 = this.matriceDesRencontres.get(joueur2).get(joueur1);
		
		nbRencontreDuJoueur1++;
		nbRencontreDuJoueur2++;
		
		this.matriceDesRencontres.get(joueur1).put( joueur2, nbRencontreDuJoueur1 );
		this.matriceDesRencontres.get(joueur2).put( joueur1, nbRencontreDuJoueur2 );
		
		System.out.println( this.matriceDesRencontres );
	}
	
	
	
	public List<Joueur> getJoueurs() {
		return new ArrayList<Joueur>( this.matriceDesRencontres.keySet() );
	}
	
	
	public List<Joueur> getAdversairesPotentiel( Joueur joueur, List<Joueur> adversairesPossibles ) {
		
		// pas de ligne pour le joueur dans la table des rencontres.
		//if( ! this.matriceDesRencontres.containsKey(joueur) ) throw new TableDesRencontresException("Le joueur n'existe pas dans la table des rencontres (il ne possède pas de ligne qui lui est associée).");
		
		// on récupère la ligne concernant le joueur
		Map<Joueur, Integer> ligneJoueur = this.matriceDesRencontres.get(joueur);
		
		
		List<Joueur> adversairesPotentiels = new ArrayList<Joueur>();
		
		if( ligneJoueur.isEmpty() ) {
			System.out.println("PAS ICI");
			return adversairesPotentiels;
		}
		
		Integer nbRencontresMin = null; //ligneJoueur.values().iterator().next(); 	// permet de récupérer la 1ère valeur
		
		System.out.println( "---------------- joueur : " + joueur );
		
		
		for( Joueur adversaire : adversairesPossibles ) {
			
			System.out.println( "ADVERSAIRE : " + adversaire.getNom() );
			
			// aucune colonne concernant cet adversaire 
			if( ! ligneJoueur.containsKey(adversaire) ) {
				System.out.println(" ne contient pas : " + adversaire);
				continue;
			}
			
			Integer nbRencontres = ligneJoueur.get(adversaire);
			System.out.println(" LE MIN ACTUEL : " + nbRencontresMin );
			
			if( nbRencontresMin == null || nbRencontres < nbRencontresMin ) {
				System.out.println("IL Y A PLUS PETIT : " + nbRencontres );
				nbRencontresMin = nbRencontres;	
				adversairesPotentiels.removeAll(adversairesPotentiels);		// on supprime les anciens adversaires potentiels
			}
			
			if( nbRencontresMin == nbRencontres ) {
				System.out.println("Nouvel adversaire : " + adversaire );
				adversairesPotentiels.add(adversaire);	
			}
			
		}
		
		System.out.println( joueur + " " + joueur.aGagne() + " " + adversairesPossibles + " " + adversairesPotentiels );
		
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
	
	
	
	@Override
	public TableDesRencontres clone() {
		
		try {
			
			TableDesRencontres tableDesRencontres = (TableDesRencontres) super.clone();
			
			tableDesRencontres.matriceDesRencontres = new HashMap< Joueur, Map<Joueur,Integer> >( this.matriceDesRencontres.size() );
			
			for( Joueur joueur : this.matriceDesRencontres.keySet() ) {
				
				Map<Joueur, Integer> ligneJoueur = this.matriceDesRencontres.get(joueur);
				
				Map<Joueur, Integer> copieLigne = new HashMap<Joueur, Integer>( ligneJoueur.size() );
				for( Joueur adversaire : ligneJoueur.keySet() ) {
					
					Joueur copieAdversaire = adversaire.clone();
					Integer copieEntier = new Integer( ligneJoueur.get(adversaire) );
					copieLigne.put( copieAdversaire, copieEntier );	
					
				}
				
				tableDesRencontres.matriceDesRencontres.put( joueur.clone(), copieLigne );
				
			}
			
			return tableDesRencontres;
			
		}
		catch( CloneNotSupportedException e ) {
			throw new InternalError("clonage impossible");
		}
		
	}
	

}
