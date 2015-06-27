package tournoi;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StrategieGagnantPerdant extends Strategie {
	
	private static final long serialVersionUID = -7435820314389088859L;

	private TableDesRencontres tableDesRencontres;
	
	private Set<Joueur> gagnants;
	private Set<Joueur> perdants;
	
	public StrategieGagnantPerdant() {
		
		this.tableDesRencontres = new TableDesRencontres();
		
		this.gagnants = new HashSet<Joueur>();
		this.perdants = new HashSet<Joueur>();
	
	}
	
	
	
	@Override
	public void ajouterJoueur( Joueur joueur ) throws JoueurDejaExistantException {
		
		//this.tableDesRencontres.ajouterJoueur(joueur); ------------------------------------------------------
		
		this.perdants.add(joueur);
		
	}

	@Override
	public void supprimerJoueur(Joueur joueur) {
		// TODO Auto-generated method stub
		
	}

	
	
	@Override
	public void retirerJoueursDisponibles(Joueur... joueurs) {
		// TODO Auto-generated method stub
		
	}
	
	
	

	@Override
	public Collection<Joueur> getJoueursDisponibles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNbJoueursDisponibles() {
		return this.gagnants.size() + this.perdants.size();
	}

	
	
	
	
	
	@Override
	public List<Match> genererMatchs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void resoudreMatchNormal(Joueur joueurGagnant, Joueur joueurPerdant) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resoudreMatchParAbandon(Joueur joueurGagnant,
			Joueur joueurPerdant) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resoudreMatchNull(Joueur joueur1, Joueur joueur2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resoudreMatchSupprime(Joueur joueur1, Joueur joueur2) {
		// TODO Auto-generated method stub
		
	}

	
	
	
	
	@Override
	public int getNbPointsGagnantMatchNormal() {
		return 3;
	}

	@Override
	public int getNbPointsPerdantMatchNormal() {
		return 1;
	}

	@Override
	public int getNbPointsGagnantMatchAbandon() {
		return 3;
	}

	@Override
	public int getNbPointsPerdantMatchAbandon() {
		return 0;
	}

	@Override
	public int getNbPointsMatchNull() {
		return 2;
	}

}
