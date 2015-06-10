package modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class Tournoi {
	

	private TableDesRencontres tableDesRencontres;
	
	private List<Joueur> joueursGagnants;
	private List<Joueur> joueursPerdants;
	private List<Match> matchs;
	
	private Aleatoire aleatoire;
	
	
	
	public Tournoi() {
		
		this.tableDesRencontres = new TableDesRencontres();
		
		this.joueursGagnants = new ArrayList<Joueur>();
		this.joueursPerdants = new ArrayList<Joueur>();
		this.matchs = new ArrayList<Match>();
		
		this.aleatoire = new Aleatoire();
	}
	
	
	public void ajouterJoueur( Joueur nouveauJoueur ) throws JoueurDejaExistantException {
		
		this.tableDesRencontres.ajouterJoueur(nouveauJoueur);
		
		this.joueursPerdants.add(nouveauJoueur);
		
	}
	
	public void supprimerJoueur( Joueur joueur ) {
		
		this.tableDesRencontres.supprimerJoueur(joueur);
		
		this.joueursGagnants.remove(joueur);
		this.joueursPerdants.remove(joueur);
		
	}
	
	
	
	private void genererMatchs( List<Joueur> listejoueurs ) {
		
		while(true) {
			
			int nbJoueurs = listejoueurs.size();
			if( nbJoueurs < 2 ) break;
			
			int indiceJoueur = this.aleatoire.getNombreAleatoire( nbJoueurs - 1 );
			Joueur joueur = listejoueurs.get(indiceJoueur);
			listejoueurs.remove(joueur);
			
			List<Joueur> adversairesPotentiels = this.tableDesRencontres.getAdversairesPotentiel( joueur, listejoueurs ); 
			
			int nbAdversaires = adversairesPotentiels.size();
			int indiceAdversairePotentiel = this.aleatoire.getNombreAleatoire( nbAdversaires - 1 );
			Joueur adversaire = adversairesPotentiels.get(indiceAdversairePotentiel);
			listejoueurs.remove(adversaire);
			
			
			Match match = new Match( joueur, adversaire );
			this.matchs.add(match);
			
		}
		
	}
	
	private void genererMatchsGagnants() {
		this.genererMatchs( this.joueursGagnants );
	}
	
	private void genererMatchsPerdants() {
		this.genererMatchs( this.joueursPerdants );
	}
	
	
	public void genererMatchs() {
		
		this.genererMatchsGagnants();
		
		this.genererMatchsPerdants();
		
		if( this.joueursGagnants.size() > 0 && this.joueursPerdants.size() > 0 ) {
			
			Joueur joueurGagnant = this.joueursGagnants.get(0);
			Joueur joueurPerdant = this.joueursPerdants.get(0);
			
			this.joueursGagnants.remove(joueurGagnant);
			this.joueursPerdants.remove(joueurPerdant);
			
			Match match = new Match( joueurGagnant, joueurPerdant );
			this.matchs.add(match);
		}
		/*
		else if( this.joueursGagnants.size() > 0 ) {
			this.joueursPerdants.add( this.joueursGagnants.get(0) );
			this.joueursGagnants.remove(0);
		}
		*/
	}
	
	public void annulerMatchs() {
		
		for( Iterator<Match> iterateur = this.matchs.iterator(); iterateur.hasNext(); ) {
						
			Match match = iterateur.next();

			for( Joueur joueur : match.getJoueurs() ) {
				
				if( joueur.aGagne() ) this.joueursGagnants.add(joueur);
				else this.joueursPerdants.add(joueur);
				
			}	
			
			iterateur.remove();
			
		}
				
	}
	
	
	
	
	public void ajouterMatch( Joueur joueur1, Joueur joueur2 ) {
		
		this.joueursGagnants.remove(joueur1);
		this.joueursGagnants.remove(joueur2);
		
		this.joueursPerdants.remove(joueur1);
		this.joueursPerdants.remove(joueur2);
		
		Match match = new Match( joueur1, joueur2 );
		this.matchs.add(match);
	}
		
	public void supprimerMatch( Match match ) {
		
		for( Joueur joueur : match.getJoueurs() ) {
			
			if( joueur.aGagne() ) this.joueursGagnants.add(joueur);
			else this.joueursPerdants.add(joueur);
			
		}	
		
		this.matchs.remove(match);
		
	}
	
	
	private void resoudreMatch( Match match, Joueur gagnant, int nbPointsGagnant, int nbPointsPerdant ) throws MatchException {
			
		Joueur perdant = match.getAutreJoueur(gagnant);
		
		gagnant.incrementerNbPoints(nbPointsGagnant);
		perdant.incrementerNbPoints(nbPointsPerdant);
		
		gagnant.gagne();
		perdant.perd();
		
		this.supprimerMatch(match);
		
	}
	
	public void resoudreMatchNormal( Match match, Joueur gagnant ) throws MatchException {
		this.resoudreMatch( match, gagnant, Match.POINTS_GAGNANT, Match.POINTS_PERDANT );
	}
	
	
	public void resoudreMatchParAbandon( Match match, Joueur joueurAbandonne ) throws MatchException {
		Joueur gagnant = match.getAutreJoueur(joueurAbandonne);
		this.resoudreMatch( match, gagnant, Match.POINTS_GAGNANT_PAR_ABANDON, Match.POINTS_PERDANT_PAR_ABANDON );
	}
	
		
	public void resoudreMatchNull( Match match ) {
		
		for( Joueur joueur : match.getJoueurs() ) {
			
			joueur.incrementerNbPoints( Match.POINTS_MATCH_NULL );
			joueur.perd();
			this.joueursPerdants.add(joueur);
		}	
		
		this.supprimerMatch(match);
	}
	
	
	
	
	public static class Test {
		public static void main( String[] args ) {
			
			Tournoi tournoi = new Tournoi();
						
			String[] noms = { "vall", "yann", "vincent", "antoine", "corentin", "cyril", "virgile", "ludo", "quentin", "maxime", "benjamin", "mathieu" };
			
			try {
				
				for( String nom : noms ) {
					tournoi.ajouterJoueur( new Joueur(nom) );
				}
				
				tournoi.genererMatchs();
				
				System.out.println( tournoi.matchs );
				
				
				
				
				System.out.println( tournoi.matchs );
				
			} catch (JoueurDejaExistantException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
						
		}
	}
	
	
	
}
