package tournoi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Tournoi implements Cloneable, Serializable {
	
	private static final long serialVersionUID = 1034215432029688768L;
	
	private Map<String, Joueur> joueurs;	
	private List<Match> matchs;
	private Strategie strategie;
			
	public Tournoi() {
		
		this.joueurs = new HashMap<String, Joueur>();		
		this.matchs = new ArrayList<Match>();
		this.strategie = new StrategieGagnantPerdant();
		
	}
		
	
	@Override
	public Tournoi clone() {
		
		try {
			
			Tournoi tournoi = (Tournoi) super.clone();
			
			tournoi.joueurs = new HashMap<String, Joueur>( this.joueurs.size() );
			tournoi.matchs = new ArrayList<Match>( this.matchs.size() );
						
			for( String nomJoueur : this.joueurs.keySet() ) {
				
				Joueur joueur = this.joueurs.get(nomJoueur);
				Joueur clone = joueur.clone();
				tournoi.joueurs.put( nomJoueur, clone );
								
			}
					
			for( Match match : this.matchs ) {
				
				Joueur joueur1 = match.getJoueur1();
				Joueur joueur2 = match.getJoueur2();
				
				Joueur cloneJoueur1 = tournoi.joueurs.get( joueur1.getNom() );
				Joueur cloneJoueur2 = tournoi.joueurs.get( joueur2.getNom() );
				
				Match clone =  new Match( cloneJoueur1, cloneJoueur2 );
				tournoi.matchs.add(clone);
				
			}

			tournoi.strategie = this.strategie.getClone( tournoi.joueurs );
									
			return tournoi;
			
		}
		catch( CloneNotSupportedException e ) {
			throw new InternalError("clonage impossible");
		}
		
	}
	
	
	// par défaut, le fait bien automatiquement.
	// si l'un des attribut n'est pas sérializable, il est à "null", ce qui génère des erreurs.
	/*	
	private void writeObject( ObjectOutputStream out ) throws IOException {
		
		out.writeObject( this.tableDesRencontres );
		out.writeObject( this.joueursGagnants );
		out.writeObject( this.joueursPerdants );
		out.writeObject( this.matchs );
		
	}
	
	private void readObject( ObjectInputStream in ) throws IOException, ClassNotFoundException {
		
		this.tableDesRencontres = (TableDesRencontres) in.readObject();
		this.joueursGagnants = (List<Joueur>) in.readObject();
		this.joueursPerdants = (List<Joueur>) in.readObject();
		this.matchs = (List<Match>) in.readObject();
		
	}
	*/
	
	
		
	public void ajouterJoueur( String nomJoueur ) throws JoueurDejaExistantException {
		this.ajouterJoueur( new Joueur(nomJoueur) );
	}
	
	public void ajouterJoueur( Joueur nouveauJoueur ) throws JoueurDejaExistantException {
		
		if( this.joueurs.containsKey( nouveauJoueur.getNom() ) ) throw new JoueurDejaExistantException();
		
		this.joueurs.put( nouveauJoueur.getNom(), nouveauJoueur );
		this.strategie.ajouterJoueur(nouveauJoueur);
		
	}
	
	public void supprimerJoueur( Joueur joueur ) {
		
		this.annulerMatchs(joueur);
				
		this.joueurs.remove( joueur.getNom() );
		
		this.strategie.supprimerJoueur(joueur);
				
	}
	
	
	
	
	public Collection<Joueur> getJoueurs() {	
		return this.joueurs.values();
	}
	
	
	public Collection<Joueur> getJoueursDisponibles() {
		return this.strategie.getJoueursDisponibles();
	}
	
	public boolean isJoueursDisponibles() {
		return this.strategie.isJoueursDisponibles();
	}
	
	
		
	public void genererMatchs() {
		
		List<Match> matchsGeneres = this.strategie.genererMatchs();
		
		this.matchs.addAll(matchsGeneres);
		
	}
	
	public void annulerMatchs( Joueur joueur ) {
		
		for( Iterator<Match> iterateur = this.matchs.iterator(); iterateur.hasNext(); ) {
			
			Match match = iterateur.next();
			
			if( ! match.joueurJoueAuMatch(joueur) ) continue;
			
			match.finir();
			
			Joueur joueur1Tournoi = this.joueurs.get( match.getJoueur1().getNom() );
			Joueur joueur2Tournoi = this.joueurs.get( match.getJoueur2().getNom() );
			
			joueur1Tournoi.setEnMatch(false);
			joueur2Tournoi.setEnMatch(false);
			
			this.strategie.resoudreMatchSupprime( match.getJoueur1(), match.getJoueur2() );	
						
			iterateur.remove();
			
		}
		
	}
	
	
	public void annulerMatchs() {
		
		for( Iterator<Match> iterateur = this.matchs.iterator(); iterateur.hasNext(); ) {
						
			Match match = iterateur.next();

			match.finir();
			
			Joueur joueur1Tournoi = this.joueurs.get( match.getJoueur1().getNom() );
			Joueur joueur2Tournoi = this.joueurs.get( match.getJoueur2().getNom() );
			
			joueur1Tournoi.setEnMatch(false);
			joueur2Tournoi.setEnMatch(false);
			
			this.strategie.resoudreMatchSupprime( match.getJoueur1(), match.getJoueur2() );	
			
			iterateur.remove();
			
		}
		
	}
	
	
	
	
	public void ajouterMatch( Joueur joueur1, Joueur joueur2 ) {
		
		this.strategie.retirerJoueursDisponibles( joueur1, joueur2 );
				
		Match match = new Match( joueur1, joueur2 );
		this.matchs.add(match);
		
	}
		
	public List<Match> getMatchs() {
		return this.matchs;
	}
	
	public boolean possedeMatchsGeneres() {
		return ! this.matchs.isEmpty();
	}
	
	
	
	
	// le joueur 1 gagne
	public void resoudreMatchNormalGagnantJoueur1( Match match ) {
		this.resoudreMatchNormal( match, match.getJoueur1(), match.getJoueur2() );
	}
	
	// le joueur 2 gagne
	public void resoudreMatchNormalGagnantJoueur2( Match match ) {
		this.resoudreMatchNormal( match, match.getJoueur2(), match.getJoueur1() );
		
	}
	
	private void resoudreMatchNormal( Match match, Joueur gagnant, Joueur perdant ) {
		
		this.resoudreMatch( match, gagnant, perdant, this.strategie.getNbPointsGagnantMatchNormal(), this.strategie.getNbPointsPerdantMatchNormal() );
		
		this.strategie.resoudreMatchNormal( gagnant, perdant );
	}
	
	
	// le joueur 1 abandonne
	public void resoudreMatchAbandonJoueur1( Match match ) {
		this.resoudreMatchAbandon( match, match.getJoueur2(), match.getJoueur1() );
	}
	
	// le joueur 2 abandonne
	public void resoudreMatchAbandonJoueur2( Match match ) {
		this.resoudreMatchAbandon( match, match.getJoueur1(), match.getJoueur2() );
	}
	
	private void resoudreMatchAbandon( Match match, Joueur gagnant, Joueur perdantAbandon ) {
		
		this.resoudreMatch( match, gagnant, perdantAbandon, this.strategie.getNbPointsGagnantMatchAbandon(), this.strategie.getNbPointsPerdantMatchAbandon() );
		
		this.strategie.resoudreMatchParAbandon( gagnant, perdantAbandon );
		
	}
	
	public void resoudreMatchNull( Match match ) {
			
		Joueur joueur1 = match.getJoueur1();
		Joueur joueur2 = match.getJoueur2();
		int nbPointsMatchNull = this.strategie.getNbPointsMatchNull();
		
		this.resoudreMatch( match, joueur1, joueur2, nbPointsMatchNull, nbPointsMatchNull );
		
		this.strategie.resoudreMatchNull( joueur1, joueur2 );
		
	}
	
	
	private void resoudreMatch( Match match, Joueur gagnant, Joueur perdant, int nbPointsGagnant, int nbPointsPerdant ) {
		
		
		Joueur gagnantTournoi = this.joueurs.get( gagnant.getNom() );
		Joueur perdantTournoi = this.joueurs.get( perdant.getNom() );
				
		gagnantTournoi.incrementerNbPoints(nbPointsGagnant);
		perdantTournoi.incrementerNbPoints(nbPointsPerdant);
		
		match.finir();
		
		gagnantTournoi.setEnMatch(false);
		perdantTournoi.setEnMatch(false);
		
		
		this.matchs.remove(match);
				
	}
	
	public void resoudreMatchSupprime( Match match ) {
		
		match.finir();
		
		this.strategie.resoudreMatchSupprime( match.getJoueur1(), match.getJoueur2() );	
		
		this.matchs.remove(match);
		
	}
	
	
	
	public void setStrategie( Strategie strategie ) {
		
		this.annulerMatchs();
		
		this.strategie = strategie;
		
		for( Joueur joueur : this.joueurs.values() ) {
			
			try {
				this.strategie.ajouterJoueur(joueur);
			} 
			catch( JoueurDejaExistantException e ) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	public String getNomStrategie() {
		return this.strategie.getNom();
	}
	
	
	public static class Test {
		public static void main( String[] args ) {
			
			Tournoi tournoi = new Tournoi();
						
			Joueur ludo = new Joueur("ludo");
			Joueur max = new Joueur("max");
			Joueur vall = new Joueur("vall");
			
			try {
				
				tournoi.ajouterJoueur(vall);
				tournoi.ajouterJoueur(max);
				tournoi.ajouterJoueur(ludo);
				
				
				//tournoi.ajouterMatch( vall, max );
				tournoi.genererMatchs();
				System.out.println( tournoi.matchs );
				
				tournoi.supprimerJoueur(ludo);
				System.out.println( tournoi.matchs );
				
				if( tournoi.matchs.size() == 0 ) return;
				
				Match match = tournoi.matchs.get(0);
				tournoi.resoudreMatchNormal( match, match.getJoueur1(), match.getJoueur2() );				
				System.out.println( tournoi.matchs );
				
				tournoi.genererMatchs();
				System.out.println( tournoi.matchs );
				
				
			} catch (JoueurDejaExistantException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
						
		}
	}
	
	
	
}
