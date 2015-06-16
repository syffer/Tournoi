package vue;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.UIDefaults;

import modele.Match;

public class ListRendererCentrerVS extends JPanel implements ListCellRenderer<Match> {
	
	private static final long serialVersionUID = -192683187875319933L;
	
	private static final Color HIGHLIGHT_COLOR = new Color( 184, 207, 229);
	
	private JLabel nomJoueurGauche;
	private JLabel labelVS;
	private JLabel nomJoueurDroite;
	
	public ListRendererCentrerVS() {
		
		
		this.labelVS = new JLabel(" VS ");
		this.nomJoueurGauche = new JLabel( "", JLabel.LEFT );
		this.nomJoueurDroite = new JLabel( "", JLabel.RIGHT );
		
		this.add( this.nomJoueurGauche );
		this.add( this.labelVS );
		this.add( this.nomJoueurDroite );
		
		this.labelVS.setHorizontalTextPosition( JLabel.CENTER );
	}
	
	@Override
	public Component getListCellRendererComponent( JList<? extends Match> list, Match value, int index, boolean isSelected, boolean cellHasFocus ) {
		
		
		this.nomJoueurGauche.setText( value.getJoueur1().getNom() );
		this.nomJoueurDroite.setText( value.getJoueur2().getNom() );
		
		if(isSelected) this.setBackground( HIGHLIGHT_COLOR );
		else this.setBackground( Color.WHITE );
		
		return this;
	}

	

}
