package vue;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import tournoi.Match;


public class ListRendererCentrerVS extends JPanel implements ListCellRenderer<Match> {
	
	private static final long serialVersionUID = -192683187875319933L;
	
	private static final Color HIGHLIGHT_COLOR = new Color( 184, 207, 229);
	private static final Dimension dimensionLabelsNoms = new Dimension( 122, 20 );
	
	private JLabel labelNomJoueurGauche;
	private JLabel labelVS;
	private JLabel labelNomJoueurDroite;
	
	public ListRendererCentrerVS() {
		
		
		this.labelVS = new JLabel(" VS ");
		this.labelNomJoueurGauche = new JLabel( "", JLabel.RIGHT );
		this.labelNomJoueurDroite = new JLabel( "", JLabel.LEFT );
				
		this.labelNomJoueurDroite.setSize( ListRendererCentrerVS.dimensionLabelsNoms );
		this.labelNomJoueurDroite.setPreferredSize( ListRendererCentrerVS.dimensionLabelsNoms );
		
		this.labelNomJoueurGauche.setSize( ListRendererCentrerVS.dimensionLabelsNoms );
		this.labelNomJoueurGauche.setPreferredSize( ListRendererCentrerVS.dimensionLabelsNoms );
		
		this.add( this.labelNomJoueurGauche );
		this.add( this.labelVS );
		this.add( this.labelNomJoueurDroite );
		
		this.labelVS.setHorizontalTextPosition( JLabel.CENTER );
	}
	
	@Override
	public Component getListCellRendererComponent( JList<? extends Match> list, Match value, int index, boolean isSelected, boolean cellHasFocus ) {
		
		
		this.labelNomJoueurGauche.setText( value.getJoueur1().getNom() );
		this.labelNomJoueurDroite.setText( value.getJoueur2().getNom() );
		
		if(isSelected) this.setBackground( HIGHLIGHT_COLOR );
		else this.setBackground( Color.WHITE );
		
		return this;
	}

	

}
