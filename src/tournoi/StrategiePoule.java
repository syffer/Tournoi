package tournoi;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class StrategiePoule extends Strategie {
	
	private static final long serialVersionUID = -7005283247704605930L;
	
	
	@Override
	public Strategie getClone( Map<String, Joueur> cloneJoueurs ) throws CloneNotSupportedException {

		return null;
	}

	@Override
	public void ajouterJoueur( Joueur joueur ) throws JoueurDejaExistantException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void supprimerJoueur( Joueur joueur ) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Joueur> getJoueursDisponibles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNbJoueursDisponibles() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	

	@Override
	public List<Match> genererMatchs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void retirerJoueursDisponibles( Joueur... joueurs ) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resoudreMatchNormal( Joueur joueurGagnant, Joueur joueurPerdant ) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resoudreMatchParAbandon( Joueur joueurGagnant, Joueur joueurPerdant ) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resoudreMatchNull( Joueur joueur1, Joueur joueur2 ) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resoudreMatchSupprime( Joueur joueur1, Joueur joueur2 ) {
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
