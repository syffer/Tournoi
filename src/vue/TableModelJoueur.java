package vue;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import modele.Joueur;

public class TableModelJoueur extends AbstractTableModel {

	private static final long serialVersionUID = -3888414836915932619L;
	

	private final String[] titresColonnes = { "NOMS", "POINTS", "ETAT" };
	private List<Joueur> joueurs;
	
	public TableModelJoueur() {
		this.joueurs = new ArrayList<Joueur>();		
	}
	
	public void setJoueurs( List<Joueur> joueurs ) {
		this.joueurs = joueurs;
		
		this.fireTableDataChanged();
	}
	
	public Joueur getJoueur( int index ) {
		return this.joueurs.get(index);
	}
	
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return this.titresColonnes.length;
	}
	
	@Override
	public String getColumnName( int columnIndex ) {
		return this.titresColonnes[ columnIndex ];
	}
	

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.joueurs.size();
	}

	@Override
	public Object getValueAt( int rowIndex, int columnIndex ) {
		
		switch (columnIndex) {
	
			case 0:		// le nom du joueur
				return this.joueurs.get(rowIndex).getNom();
			
			case 1:		// le nombre de points du joueur
				return this.joueurs.get(rowIndex).getNbPoints();
				
			case 2:
				return ( this.joueurs.get(rowIndex).estEnMatch() ) ? "En match" : "Attend";
				
			default:
				throw new IllegalArgumentException();
		}
	}

}
