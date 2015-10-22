package tournoi;

import internationalisation.Constantes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class StrategiePoule extends StrategieAleatoire {
	
	private static final long serialVersionUID = -7005283247704605930L;
		
	
	public StrategiePoule() {
		super();
	}
	
	
	public String getNom() {
		return Constantes.getString( Constantes.MATCHS_POULE );
	}
	
	
	@Override
	public void resoudreMatchNormal( Joueur joueurGagnant, Joueur joueurPerdant ) {
		this.joueursDisponibles.add(joueurGagnant);
	}

	@Override
	public void resoudreMatchParAbandon( Joueur joueurGagnant, Joueur joueurPerdant ) {
		this.joueursDisponibles.add(joueurGagnant);
	}

	@Override
	public void resoudreMatchNull( Joueur joueur1, Joueur joueur2 ) {
		this.joueursDisponibles.add(joueur1);
		this.joueursDisponibles.add(joueur2);
	}

	@Override
	public void resoudreMatchSupprime( Joueur joueur1, Joueur joueur2 ) {
		this.joueursDisponibles.add(joueur1);
		this.joueursDisponibles.add(joueur2);
	}
	
		
	

}
