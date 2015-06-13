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
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import modele.Match;



public class Vue extends JFrame {
	
	private static final long serialVersionUID = -219340788187650081L;
	
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
	
	private static int LARGEUR_PANNEAU = 450;
	private static int HAUTEUR_PANNEAU = 250;
	
	public Vue() {
		super( Constantes.getString(Constantes.TITRE_FENETRE) );
		
		this.ajouterBarreDesMenus();
		
		this.ajouterPanneauJoueurs();
		
		this.ajouterPanneauIntermediaire();
		
		this.ajouterPanneauMatchs();
		
		
		this.setLayout( new FlowLayout() );		// de gauche � droite
		this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		this.pack();
		this.setLocationRelativeTo(null);	// on place la fenetre au centre de l'�cran ( � faire apr�s le JFrame.pack() )
		this.setMinimumSize( this.getSize() );	// la taille minimale de la fen�tre est la taille actuelle
	}

	
	private void ajouterBarreDesMenus() {
		
		JMenu menuFichier = new JMenu( Constantes.getString(Constantes.TITRE_MENU_FICHIER) );
		this.menuNouveauFichier = new JMenuItem( Constantes.getString(Constantes.NOUVEAU_TOURNOI) );
		this.menuOuvrirFichier = new JMenuItem( Constantes.getString(Constantes.CHARGER_TOURNOI) );
		this.menuSauvegarder = new JMenuItem( Constantes.getString(Constantes.SAUVEGARDER_TOURNOI) );
		menuFichier.add( this.menuNouveauFichier );
		menuFichier.add( this.menuOuvrirFichier );
		menuFichier.add( this.menuSauvegarder );
		
		JMenu menuEditer = new JMenu( Constantes.getString(Constantes.TITRE_MENU_EDITION) );
		this.menuAnnuler = new JMenuItem( Constantes.getString(Constantes.UNDO) );
		this.menuRefaire = new JMenuItem( Constantes.getString(Constantes.REDO) );
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
		TitledBorder titre = BorderFactory.createTitledBorder( contour, Constantes.getString(Constantes.TITRE_PANNEAU_JOUEURS) );
		panneauJoueurs.setBorder( titre );		// faire apparaitre le contour
		
		panneauJoueurs.setPreferredSize( new Dimension(LARGEUR_PANNEAU, HAUTEUR_PANNEAU) );
		panneauJoueurs.setSize( new Dimension(LARGEUR_PANNEAU, HAUTEUR_PANNEAU) );
		panneauJoueurs.setLayout( new GridBagLayout() );
		GridBagConstraints contrainte = new GridBagConstraints();
		//panneauJoueurs.setLayout( new BoxLayout(panneauJoueurs, BoxLayout.Y_AXIS) );	// de haut en bas
		this.add(panneauJoueurs);
		
		
		this.tableauJoueurs = new JTable( new TableModelJoueur() );	
		this.tableauJoueurs.setAutoCreateRowSorter(true);	// rendre le tableau triable
		this.tableauJoueurs.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );	// on ne peut s�lectionner qu'une et une seule ligne � la fois
		this.tableauJoueurs.getTableHeader().setReorderingAllowed(false);	// colonnes non d�placable
		
		JScrollPane scrollPane = new JScrollPane( this.tableauJoueurs, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		scrollPane.getViewport().setBackground(Color.WHITE); 		// on change la couleur de fond de la barre de d�filement 
		this.donnerContrainte( contrainte, 0, 0, 1, 1, 100, 80 );
		panneauJoueurs.add( scrollPane, contrainte );
		
		
		JPanel panneauAjoutJoueur = new JPanel();
		panneauAjoutJoueur.setLayout( new FlowLayout() );
		this.donnerContrainte( contrainte, 0, 1, 1, 1, 100, 5 );
		panneauJoueurs.add( panneauAjoutJoueur, contrainte );
		
		this.boutonSupprimerJoueur = new JButton( Constantes.getString(Constantes.SUPPRIMER_UN_JOUEUR) );
		this.donnerContrainte( contrainte, 0, 2, 1, 1, 100, 5, GridBagConstraints.NONE );
		panneauJoueurs.add( this.boutonSupprimerJoueur, contrainte );
		
		
		
		this.champAjoutJoueur = new JTextField();
		this.champAjoutJoueur.setColumns(20);
		panneauAjoutJoueur.add( this.champAjoutJoueur );
		
		this.boutonAjoutJoueur = new JButton( Constantes.getString(Constantes.AJOUTER_UN_JOUEUR) );
		panneauAjoutJoueur.add( this.boutonAjoutJoueur );
				
	}
	
	
	private void ajouterPanneauIntermediaire() {
		
		JPanel panneauIntermediaire = new JPanel();
		panneauIntermediaire.setLayout( new GridLayout(3, 1, 0, 10) ); 	// grille de 3 lignes 1 colonne
		this.add(panneauIntermediaire);
		
		this.boutonCreerMatch = new JButton( Constantes.getString(Constantes.CREER_UN_MATCH) );
		panneauIntermediaire.add( this.boutonCreerMatch );
		
		this.boutonGenererMatchs = new JButton( Constantes.getString(Constantes.GENERER_LES_MATCHS) );
		panneauIntermediaire.add( this.boutonGenererMatchs );
		
		this.boutonAnnulerLesMatchs = new JButton( Constantes.getString(Constantes.ANNULER_LES_MATCHS) );
		panneauIntermediaire.add( this.boutonAnnulerLesMatchs );
		
	}
	
	private void ajouterPanneauMatchs() {
		
		JPanel panneauMatchs = new JPanel();
		Border contour = BorderFactory.createEtchedBorder();
		TitledBorder titre = BorderFactory.createTitledBorder( contour, Constantes.getString(Constantes.TITRE_PANNEAU_MATCHS) );
		panneauMatchs.setBorder( titre );		// faire apparaitre le contour
		
		panneauMatchs.setPreferredSize( new Dimension(LARGEUR_PANNEAU, HAUTEUR_PANNEAU) );
		panneauMatchs.setSize( new Dimension(LARGEUR_PANNEAU, HAUTEUR_PANNEAU) );
		panneauMatchs.setLayout( new GridLayout(1, 2, 10, 0) );		// grille de 1 ligne et 2 colonnes
		GridBagConstraints contrainte = new GridBagConstraints();
		this.add(panneauMatchs);
		
		
		JPanel panneauListeMatchs = new JPanel();
		panneauListeMatchs.setLayout( new BorderLayout(0, 0) );
		this.donnerContrainte( contrainte, 0, 0, 1, 1, 50, 100 );
		panneauMatchs.add( panneauListeMatchs, contrainte );
				
		JPanel panneauBoutonsMatchs = new JPanel();
		panneauBoutonsMatchs.setLayout( new GridLayout(6, 1, 0, 10) );	// grille de 6 lignes et 1 colonne
		this.donnerContrainte( contrainte, 2, 0, 1, 1, 20, 100 );
		panneauMatchs.add( panneauBoutonsMatchs, contrainte );
		
		
		
		this.listeMatchs = new JList<Match>( new ListModelMatch() );
		this.listeMatchs.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		JScrollPane scrollPane = new JScrollPane( this.listeMatchs, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		panneauListeMatchs.add( scrollPane, BorderLayout.CENTER );
		
		this.boutonJoueur1Gagne = new JButton( "J1 " + Constantes.getString(Constantes.GAGNE) );
		panneauBoutonsMatchs.add( this.boutonJoueur1Gagne );
		
		this.boutonJoueur2Gagne = new JButton( "J2 " + Constantes.getString(Constantes.GAGNE) );
		panneauBoutonsMatchs.add( this.boutonJoueur2Gagne );
		
		this.boutonJoueur1Abandonne = new JButton( "J1 " + Constantes.getString(Constantes.ABANDONNE));
		panneauBoutonsMatchs.add( this.boutonJoueur1Abandonne );
		
		this.boutonJoueur2Abandonne = new JButton( "J2 " + Constantes.getString(Constantes.ABANDONNE) );
		panneauBoutonsMatchs.add( this.boutonJoueur2Abandonne );
		
		this.boutonMatchNull = new JButton( Constantes.getString(Constantes.MATCH_NULL) );
		panneauBoutonsMatchs.add( this.boutonMatchNull );
		
		this.boutonAnnulerMatch = new JButton( Constantes.getString(Constantes.SUPPRIMER_LE_MATCH) );
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
