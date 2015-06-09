package modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class Tournoi {
	
	private ArrayList<Joueur> joueursGagnants;
	private ArrayList<Joueur> joueursPerdants;
	private TableDesRencontres tableDesRencontres;;
	
	private Random random;
	
	
	
	public Tournoi() {
		
		this.joueursGagnants = new ArrayList<Joueur>();
		this.joueursPerdants = new ArrayList<Joueur>();
		this.tableDesRencontres = new TableDesRencontres();
		
		this.random = new Random();
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
	
	
	
	private void genererMatchs( ArrayList<Joueur> listejoueurs ) {
		
		
		while(true) {
			
			int nbJoueurs = listejoueurs.size();
			if( nbJoueurs < 2 ) break;
			
			int indice = this.random.nextInt( (nbJoueurs - 0) + 1) + 0;
			Joueur joueur = listejoueurs.get(indice);
			
			
			
			
			
			
		}
		
		
		
	}
	
	
	public void genererMatchs() {
		
			
		/*
		if( this.joueursDisponibles.size() > 0 ) {
			Joueur dernierJoueur = this.joueursDisponibles.get(0);
			dernierJoueur.aPerdu();
		}
		*/
		
		
	}
	
	public void annulerMatchs() {
		
	}
	
	
	
	
	
	
	
	public void ajouterMatch( Match match ) {
		
	}
	
	public void supprimerMatch( Match match ) {
		
	}
	
	
	
	
	
	
	
	public static class Test {
		public static void main( String[] args ) {
			
			Tournoi tournoi = new Tournoi();
			
			Joueur toto = new Joueur("toto");
			Joueur toto2 = new Joueur("toto");
			Joueur tata = new Joueur("tata", 10);
			
			try {
				tournoi.ajouterJoueur(toto);
			} catch (JoueurDejaExistantException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				tournoi.ajouterJoueur(tata);
			} catch (JoueurDejaExistantException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				tournoi.ajouterJoueur(toto2);
			} catch (JoueurDejaExistantException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	
	
}
