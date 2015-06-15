package modele;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Tournoi implements Cloneable, Serializable {
	
	private static final long serialVersionUID = 1034215432029688768L;

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
	
	
	
	@Override
	public Tournoi clone() {
		
		try {
			
			Tournoi tournoi = (Tournoi) super.clone();
			
			
			tournoi.joueursGagnants = new ArrayList<Joueur>( this.joueursGagnants.size() );
			for( Joueur gagnant : this.joueursGagnants ) {
				tournoi.joueursGagnants.add( gagnant.clone() );
			}
			
			tournoi.joueursPerdants = new ArrayList<Joueur>( this.joueursPerdants.size() );
			for( Joueur perdant : this.joueursPerdants ) {
				tournoi.joueursPerdants.add( perdant.clone() );
			}
			
			tournoi.matchs = new ArrayList<Match>( this.matchs.size() );
			for( Match match : this.matchs ) {
				tournoi.matchs.add( match.clone() );
			}
			
			tournoi.tableDesRencontres = this.tableDesRencontres.clone();
						
			return tournoi;
			
		}
		catch( CloneNotSupportedException e ) {
			throw new InternalError("clonage impossible");
		}
	}
	
	
	private void writeObject( ObjectOutputStream out ) throws IOException {
		
	}
	
	private void readObject( ObjectInputStream in ) throws IOException, ClassNotFoundException {
		
	}
	
	
		
	public void ajouterJoueur( String nomJoueur ) throws JoueurDejaExistantException {
		this.ajouterJoueur( new Joueur(nomJoueur) );
	}
	
	public void ajouterJoueur( Joueur nouveauJoueur ) throws JoueurDejaExistantException {
		
		this.tableDesRencontres.ajouterJoueur(nouveauJoueur);
		
		this.joueursPerdants.add(nouveauJoueur);
		
	}
	
	public void supprimerJoueur( Joueur joueur ) {
		
		this.annulerMatchs(joueur);
		
		this.tableDesRencontres.supprimerJoueur(joueur);
		
		this.joueursGagnants.remove(joueur);
		this.joueursPerdants.remove(joueur);
		
	}
	
	
	
	
	
	public List<Joueur> getJoueurs() {	
		return this.tableDesRencontres.getJoueurs();
	}
	
	
	public List<Joueur> getJoueursDisponibles() {
		ArrayList<Joueur> joueurs = new ArrayList<Joueur>( this.joueursGagnants );
		joueurs.addAll( this.joueursPerdants );
		return joueurs;
	}
	
	public boolean isJoueursDisponibles() {
		return this.joueursGagnants.size() + this.joueursPerdants.size() >= 2;
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
	
	public void annulerMatchs( Joueur joueur ) {
		
		for( Iterator<Match> iterateur = this.matchs.iterator(); iterateur.hasNext(); ) {
			
			Match match = iterateur.next();
			
			if( ! match.joueAuMatch(joueur) ) continue;
			
			for( Joueur j : match.getJoueurs() ) {
				
				if( j.aGagne() ) this.joueursGagnants.add(j);
				else this.joueursPerdants.add(j);
				
				j.setEnMatch(false);
			}	
			
			iterateur.remove();
			
		}
		
	}
	
	
	public void annulerMatchs() {
		
		for( Iterator<Match> iterateur = this.matchs.iterator(); iterateur.hasNext(); ) {
						
			Match match = iterateur.next();

			for( Joueur joueur : match.getJoueurs() ) {
				
				if( joueur.aGagne() ) this.joueursGagnants.add(joueur);
				else this.joueursPerdants.add(joueur);
				
				joueur.setEnMatch(false);
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
			
			joueur.setEnMatch(false);
		}	
		
		this.matchs.remove(match);
		
	}
	
	public List<Match> getMatchs() {
		return this.matchs;
	}
	
	
	
	
	
	private void resoudreMatch( Match match, Joueur gagnant, int nbPointsGagnant, int nbPointsPerdant ) throws MatchException {
			
		Joueur perdant = match.getAutreJoueur(gagnant);
		
		gagnant.incrementerNbPoints(nbPointsGagnant);
		perdant.incrementerNbPoints(nbPointsPerdant);
		
		gagnant.gagne();
		perdant.perd();
		
		this.tableDesRencontres.rencontreJoueurs( gagnant, perdant );
		
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
			
		Joueur joueur1 = match.getJoueur1();
		Joueur joueur2 = match.getJoueur2();
		
		joueur1.incrementerNbPoints( Match.POINTS_MATCH_NULL );
		joueur2.incrementerNbPoints( Match.POINTS_MATCH_NULL );
		
		joueur1.perd();
		joueur2.perd();
		
		this.tableDesRencontres.rencontreJoueurs( joueur1, joueur2 );
		
		this.supprimerMatch(match);
		
		//this.afficher();
	}
	
	
	
	
	/*
	private void afficher() {
		System.out.println();
		System.out.println( this.matchs );
		System.out.println( this.joueursGagnants );
		System.out.println( this.joueursPerdants );
	}
	*/
	
	
	public static class Test {
		public static void main( String[] args ) {
			
			Tournoi tournoi = new Tournoi();
						
			String[] noms = { "vall", "yann", "vincent", "antoine", "corentin", "cyril", "virgile", "quentin", "maxime", "benjamin", "mathieu" };
			
			Joueur ludo = new Joueur("ludo");
			
			try {
				
				for( String nom : noms ) {
					tournoi.ajouterJoueur( new Joueur(nom) );
				}
				
				tournoi.ajouterJoueur(ludo);
				
				tournoi.genererMatchs();
				
				System.out.println( tournoi.matchs );
				System.out.println( tournoi.joueursGagnants );
				System.out.println( tournoi.joueursPerdants );
				System.out.println();
				
				tournoi.supprimerJoueur(ludo);
				//tournoi.supprimerMatch( tournoi.matchs.get(0) );
				Match match = tournoi.matchs.get(0);
				tournoi.resoudreMatchNormal( match, match.getJoueur1() );
				
				
				System.out.println( tournoi.matchs );
				System.out.println( tournoi.joueursGagnants );
				System.out.println( tournoi.joueursPerdants );
				
				tournoi.genererMatchs();
				
				System.out.println( tournoi.matchs );
				System.out.println( tournoi.joueursGagnants );
				System.out.println( tournoi.joueursPerdants );
				
			} catch (JoueurDejaExistantException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MatchException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
						
		}
	}
	
	
	
}
