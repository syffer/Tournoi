package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import internationalisation.Constantes;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import modele.Match;



public class Vue extends JFrame {

	private static final long serialVersionUID = 1L;

	// la barre de menu.
	public JMenuItem menuNouveauFichier;
	public JMenuItem menuOuvrirFichier;
	public JMenuItem menuSauvegarder;
	public JMenuItem menuAnnuler;
	public JMenuItem menuRefaire;
	
	// le panneau des joueurs
	public JTable tableauJoueurs;
	public JTextField champAjoutJoueur;
	public JButton boutonAjoutJoueur;
	public JButton boutonSupprimerJoueur;
	
	//
	public JButton boutonCreerMatch;
	public JButton boutonGenererMatchs;
	public JButton boutonAnnulerLesMatchs;
	
	// le panneau des matchs
	public JList<Match> listeMatchs;
	public JButton boutonJoueur1Gagne;
	public JButton boutonJoueur2Gagne;
	public JButton boutonJoueur1Abandonne;
	public JButton boutonJoueur2Abandonne;
	public JButton boutonMatchNull;
	public JButton boutonAnnulerMatch;
	
	
	public Vue() {
		super( Constantes.getString(Constantes.TITRE_FENETRE) );
		
		this.ajouterBarreDesMenus();
		
		this.ajouterPanneauJoueurs();
		
		this.ajouterPanneauIntermediaire();
		
		this.ajouterPanneauMatchs();
		
		
		this.setLayout( new FlowLayout() );		// de gauche à droite
		this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		this.pack();
		this.setLocationRelativeTo(null);	// on place la fenetre au centre de l'écran ( à faire après le JFrame.pack() )
			
	}

	
	private void ajouterBarreDesMenus() {
		
		JMenu menuFichier = new JMenu("Fichier");
		this.menuNouveauFichier = new JMenuItem("Nouveau tournoi");
		this.menuOuvrirFichier = new JMenuItem("Charger un tournoi");
		this.menuSauvegarder = new JMenuItem("Sauvegarder le tournoi");
		menuFichier.add( this.menuNouveauFichier );
		menuFichier.add( this.menuOuvrirFichier );
		menuFichier.add( this.menuSauvegarder );
		
		JMenu menuEditer = new JMenu("Editer");
		this.menuAnnuler = new JMenuItem("Annuler");
		this.menuRefaire = new JMenuItem("Refaire");
		menuEditer.add( this.menuAnnuler );
		menuEditer.add( this.menuRefaire );
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(menuFichier);
		menuBar.add(menuEditer);
		this.setJMenuBar(menuBar);
		
	}
		
	
	private void ajouterPanneauJoueurs() {
		
		JPanel panneauJoueurs = new JPanel();
		
		Border contour = BorderFactory.createEtchedBorder();
		TitledBorder titre = BorderFactory.createTitledBorder( contour, "Joueurs" );
		panneauJoueurs.setBorder( titre );		// faire apparaitre le contour
		
		panneauJoueurs.setLayout( new GridBagLayout() );
		GridBagConstraints contrainte = new GridBagConstraints();
		//panneauJoueurs.setLayout( new BoxLayout(panneauJoueurs, BoxLayout.Y_AXIS) );	// de haut en bas
		this.add(panneauJoueurs);
		
		
		this.tableauJoueurs = new JTable( new TableModelJoueur() );	
		this.tableauJoueurs.setAutoCreateRowSorter(true);	// rendre le tableau triable
		//this.tableauJoueurs.setPreferredSize( new Dimension(300, 400) ); 	// ( largeur , hauteur )
		//this.tableauJoueurs.setSize( new Dimension(300, 400) );
		JScrollPane scrollPane = new JScrollPane( this.tableauJoueurs, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		this.donnerContrainte( contrainte, 0, 0, 1, 1, 100, 100 );
		panneauJoueurs.add( scrollPane, contrainte );
		
		
		JPanel panneauAjoutJoueur = new JPanel();
		panneauAjoutJoueur.setLayout( new FlowLayout() );
		this.donnerContrainte( contrainte, 0, 1, 1, 1, 100, 100 );
		panneauJoueurs.add( panneauAjoutJoueur, contrainte );
		
		this.boutonSupprimerJoueur = new JButton("SUPPRIMER JOUEUR");
		this.donnerContrainte( contrainte, 0, 2, 1, 1, 100, 100 );
		panneauJoueurs.add( this.boutonSupprimerJoueur, contrainte );
		
		
		
		this.champAjoutJoueur = new JTextField();
		this.champAjoutJoueur.setColumns(10);
		panneauAjoutJoueur.add( this.champAjoutJoueur );
		
		this.boutonAjoutJoueur = new JButton("AJOUT DUN JOUEUR");
		panneauAjoutJoueur.add( this.boutonAjoutJoueur );
			
		
	}
	
	
	private void ajouterPanneauIntermediaire() {
		
		JPanel panneauIntermediaire = new JPanel();
		panneauIntermediaire.setLayout( new GridLayout(3, 1, 0, 10) ); 	// grille de 3 lignes 1 colonne
		this.add(panneauIntermediaire);
		
		this.boutonCreerMatch = new JButton("CREER UN MATCH");
		panneauIntermediaire.add( this.boutonCreerMatch );
		
		this.boutonGenererMatchs = new JButton("GENERER LES MATCHS");
		panneauIntermediaire.add( this.boutonGenererMatchs );
		
		this.boutonAnnulerLesMatchs = new JButton("ANNULER LES MATCHS");
		panneauIntermediaire.add( this.boutonAnnulerLesMatchs );
		
	}
	
	private void ajouterPanneauMatchs() {
		
		JPanel panneauMatchs = new JPanel();
		Border contour = BorderFactory.createEtchedBorder();
		TitledBorder titre = BorderFactory.createTitledBorder( contour, "Matchs" );
		panneauMatchs.setBorder( titre );		// faire apparaitre le contour
		
		//panneauMatchs.setLayout( new BoxLayout(panneauMatchs, BoxLayout.Y_AXIS) );
		panneauMatchs.setLayout( new FlowLayout() );
		//panneauMatchs.setPreferredSize( new Dimension(400, 200) );
		//panneauMatchs.setSize( new Dimension(400, 200) );
		this.add(panneauMatchs);
		
		
		JPanel panneauListeMatchs = new JPanel();
		panneauMatchs.add(panneauListeMatchs);
		
		
		JPanel panneauBoutonsMatchs = new JPanel();
		panneauBoutonsMatchs.setLayout( new GridLayout(6, 1, 0, 10) );	// grille de 6 lignes et 1 colonne
		panneauMatchs.add(panneauBoutonsMatchs);
		
		
		
		this.listeMatchs = new JList<Match>();
		//this.listeMatchs.setPreferredSize( new Dimension(200, 500) );
		//this.listeMatchs.setSize( new Dimension(200, 500) );
		JScrollPane scrollPane = new JScrollPane( this.listeMatchs, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		panneauListeMatchs.add(scrollPane);
		
		this.boutonJoueur1Gagne = new JButton("J1 gagne");
		panneauBoutonsMatchs.add( this.boutonJoueur1Gagne );
		
		this.boutonJoueur2Gagne = new JButton("J2 gagne");
		panneauBoutonsMatchs.add( this.boutonJoueur2Gagne );
		
		this.boutonJoueur1Abandonne = new JButton("J1 abandonne");
		panneauBoutonsMatchs.add( this.boutonJoueur1Abandonne );
		
		this.boutonJoueur2Abandonne = new JButton("J2 abandonne");
		panneauBoutonsMatchs.add( this.boutonJoueur2Abandonne );
		
		this.boutonMatchNull = new JButton("null");
		panneauBoutonsMatchs.add( this.boutonMatchNull );
		
		this.boutonAnnulerMatch = new JButton("annuler match");
		panneauBoutonsMatchs.add( this.boutonAnnulerMatch );
		
	}
	
	
	
	private void donnerContrainte( GridBagConstraints gridBagContraints, int gx, int gy, int gw, int gh, int wx, int wy )  {
		this.donnerContrainte( gridBagContraints, gx, gy, gw, gh, wx, wy, GridBagConstraints.BOTH );
	}


	private void donnerContrainte( GridBagConstraints gridBagContraints, int gx, int gy, int gw, int gh, int wx, int wy, int constraint ) {  
		gridBagContraints.gridx = gx;
		gridBagContraints.gridy = gy;
		gridBagContraints.gridwidth = gw;
		gridBagContraints.gridheight = gh;
		gridBagContraints.weightx = wx;
		gridBagContraints.weighty = wy;
		gridBagContraints.fill = constraint;
	}
	
	
	
	
	public static class Test {
		public static void main( String[] args ) {
			
			Vue vue = new Vue();
			vue.setVisible(true);
			
		}
	}
	
}
