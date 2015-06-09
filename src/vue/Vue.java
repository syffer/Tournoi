package vue;

import java.awt.FlowLayout;

import internationalisation.Constantes;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class Vue extends JFrame {

	private static final long serialVersionUID = 1L;

	public JTextField champAjoutJoueur;
	public JButton boutonAjoutJoueur;
	
	public JButton boutonSupprimerJoueur;
	
	public JButton boutonAjouterMatch;
	public JButton boutonGenererMatchs;
	public JButton boutonAnnulerMatchs;
	
	
	public Vue() {
		super( Constantes.getString(Constantes.TITRE_FENETRE) );
		
		
		JPanel panneauJoueurs = new JPanel();
		panneauJoueurs.setLayout( new BoxLayout(panneauJoueurs, BoxLayout.Y_AXIS) );	// de haut en bas
		panneauJoueurs.setBorder( BorderFactory.createEtchedBorder() );		// faire apparaitre le contour
		this.add(panneauJoueurs);
		
		
		
		
		
		// ajout du tableau des joueurs
		
		JPanel panneauAjoutJoueur = new JPanel();
		panneauAjoutJoueur.setLayout( new FlowLayout() );	// de gauche à droite
		panneauJoueurs.add(panneauAjoutJoueur);
		
		
		this.champAjoutJoueur = new JTextField();
		this.champAjoutJoueur.setColumns(5);
		//panneauAjoutJoueur.add( this.champAjoutJoueur );
		
		this.boutonAjoutJoueur = new JButton("AJOUTER JOUEUR !!!!!");
		panneauAjoutJoueur.add( this.boutonAjoutJoueur );
		
		
		
		
		this.boutonSupprimerJoueur = new JButton("SUPPRIMER JOUEUR !!!");
		panneauJoueurs.add( this.boutonSupprimerJoueur );
		
		
		
		
		JPanel panneauBoutonsMatchs = new JPanel();
		panneauBoutonsMatchs.setLayout( new BoxLayout(panneauBoutonsMatchs, BoxLayout.Y_AXIS) );	// de haut en bas
		panneauBoutonsMatchs.setBorder( BorderFactory.createEtchedBorder() );		// faire apparaitre le contour
		this.add(panneauBoutonsMatchs);
		
		
		this.boutonAjouterMatch = new JButton("AJOUTER MATCH !!!!!");
		panneauBoutonsMatchs.add( this.boutonAjouterMatch );
		
		this.boutonGenererMatchs = new JButton("GENERER MATCH ");
		panneauBoutonsMatchs.add( this.boutonGenererMatchs );
		
		this.boutonAnnulerMatchs = new JButton("Annuler les MATCHS");
		panneauBoutonsMatchs.add( this.boutonAnnulerMatchs );
		
		
		
		
		
		
		this.setLayout( new FlowLayout() );		// de gauche à droite
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.pack();
	}

	
	
	public static class Test {
		public static void main( String[] args ) {
			
			Vue vue = new Vue();
			vue.setVisible(true);
			
		}
	}
	
}
