package vue;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

import modele.Joueur;
import modele.Match;

public class ListModelMatch extends AbstractListModel<Match> {
	
	private static final long serialVersionUID = 3556334624945303786L;

	
	private List<Match> matchs;
	
	public ListModelMatch() {
		this.matchs = new ArrayList<Match>();
	}
	
	public void setMatchs( List<Match> matchs ) {
		this.matchs = matchs;
		
		this.fireContentsChanged( this, 0, matchs.size() - 1 );
	}
	
	
	@Override
	public Match getElementAt( int numeroLigne ) {
		// TODO Auto-generated method stub
		return this.matchs.get(numeroLigne);
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return this.matchs.size();
	}

}
