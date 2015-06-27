package vue;

import internationalisation.Constantes;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tournoi.Joueur;
import tournoi.Match;


public class JDialogCreerMatch extends JDialog {
	
	private static final long serialVersionUID = -9213215540153082100L;
	
	private static final int ANNULER = 1;
	private static final int CREER = 0;
	
	private int retour;
	private Vector<Joueur> joueursDisponibles;
		
	private JComboBox<Joueur> listeGauche;
	private JComboBox<Joueur> listeDroite;
	
	private JButton boutonCreer;
	private JButton boutonAnnuler;
		
	
	public JDialogCreerMatch( JFrame parent, Collection<Joueur> joueursDisponibles ) {
		super( parent, Constantes.getString(Constantes.CREER_UN_MATCH), true );	// true : indique que le dialogue est modal (on attend que la fenêtre se ferme)
		
		this.joueursDisponibles = new Vector<Joueur>(joueursDisponibles);
		this.retour = JDialogCreerMatch.ANNULER;
		
		
		JPanel conteneur = new JPanel();
		conteneur.setLayout( new GridLayout(3, 1, 0, 10) ); 	// grille de 3 lignes 1 colonne
		conteneur.setBorder( BorderFactory.createEmptyBorder(10, 50, 10, 50) );		// création d'une marge
		this.setContentPane(conteneur);

		JLabel labelMessage = new JLabel( Constantes.getString(Constantes.MESSAGE_DIALOGUE_CREER_MATCH_CHOISIR_JOUEUR), JLabel.CENTER );
		conteneur.add(labelMessage);
		
		JPanel conteneurJComboBox = new JPanel();
		conteneurJComboBox.setLayout( new GridLayout(1, 3, 0, 0) );
		this.listeGauche = new JComboBox<Joueur>();
		this.listeDroite = new JComboBox<Joueur>();
		conteneurJComboBox.add( this.listeGauche );
		conteneurJComboBox.add( new JLabel("VS", JLabel.CENTER) );
		conteneurJComboBox.add( this.listeDroite );
		conteneur.add(conteneurJComboBox);
		
		JPanel conteneurJButton = new JPanel();
		this.boutonCreer = new JButton( Constantes.getString(Constantes.CREER) );
		this.boutonAnnuler = new JButton( Constantes.getString(Constantes.ANNULER) );
		conteneurJButton.add( this.boutonCreer );
		conteneurJButton.add( this.boutonAnnuler );
		conteneur.add(conteneurJButton);
		
		
		
		// on remplit les listes
		for( Joueur joueur : joueursDisponibles ) {
			this.listeGauche.addItem(joueur);
			this.listeDroite.addItem(joueur);
		}

		// initialisation des doubles listes.
		this.listeDroite.removeItem( this.listeDroite.getSelectedItem() );
		this.listeGauche.removeItem( this.listeDroite.getSelectedItem());
		//this.listeGauche.setSelectedIndex(0);	// on initialise les sélections des listes.
		
		
		
		ActionCreer actionCreer = new ActionCreer();
		ActionAnnuler actionAnnuler = new ActionAnnuler();
		ActionJComboBox actionJComboBox = new ActionJComboBox();
		
		this.boutonCreer.setAction(actionCreer);
		this.boutonAnnuler.setAction(actionAnnuler);
		
		this.listeGauche.addActionListener(actionJComboBox);
		this.listeDroite.addActionListener(actionJComboBox);
		
		
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setResizable(false);
	}
	
	
	public Match getMatchCreer() {
		Joueur joueur1 = (Joueur) this.listeGauche.getSelectedItem();
		Joueur joueur2 = (Joueur) this.listeDroite.getSelectedItem();
		return new Match( joueur1, joueur2 );		
	}
	
	public int getValeurRetour() {
		return this.retour;
	}
	
	
	public class ActionJComboBox implements ActionListener {
		
		@Override
		public void actionPerformed( ActionEvent event ) {
						
			@SuppressWarnings("unchecked")
			JComboBox<Joueur> source = (JComboBox<Joueur>) event.getSource();
			JComboBox<Joueur> autre = ( source == listeGauche ) ? listeDroite : listeGauche;
						
			Joueur selectionSource = (Joueur) source.getSelectedItem();
			Joueur selectionAutre = (Joueur) autre.getSelectedItem();
			
			autre.removeAllItems();
			for( Joueur joueur : joueursDisponibles ) {
				if( joueur == selectionSource ) continue;
				autre.addItem(joueur);				
			}
			
			autre.setSelectedItem(selectionAutre);
						
		}
		
	}
	
	
	
	public class ActionCreer extends AbstractAction {
		
		private static final long serialVersionUID = -6193454584063309688L;

		public ActionCreer() {
			super( Constantes.getString(Constantes.CREER) );
			
			this.putValue( NAME, Constantes.getString(Constantes.CREER) );
		}
		
		@Override
		public void actionPerformed( ActionEvent event ) {
			retour = JDialogCreerMatch.CREER;
			setVisible(false);
			dispose();
		}
		
	}
	
	public class ActionAnnuler extends AbstractAction {
		
		private static final long serialVersionUID = -3364525202505281107L;

		public ActionAnnuler() {
			super( Constantes.getString(Constantes.ANNULER) );
			
			this.putValue( NAME, Constantes.getString(Constantes.ANNULER) );
		}
		
		@Override
		public void actionPerformed( ActionEvent event ) {
			retour = JDialogCreerMatch.ANNULER;
			setVisible(false);
			dispose();
		}
		
	}
	
	
	public static Match afficherDialogueCreerMatch( JFrame parent, Collection<Joueur> joueursDisponibles ) throws ChoixAnnulerException {
		
		JDialogCreerMatch dialog = new JDialogCreerMatch( parent, joueursDisponibles );
		dialog.setVisible(true);
		
		int retour = dialog.getValeurRetour();
		if( retour != JDialogCreerMatch.CREER ) throw new ChoixAnnulerException("l'utilisateur a annulé la création du match");
		
		return dialog.getMatchCreer();
	}
	
	
	
	public static class Test {
		public static void main( String[] args ) {
			
			String[] noms = { "vall", "vincent", "noémie", "quentin" };
			Set<Joueur> joueursDisponibles = new HashSet<Joueur>();
			for( String nom : noms ) {
				joueursDisponibles.add( new Joueur(nom) );
			}
			
			
			try {
				Match match = JDialogCreerMatch.afficherDialogueCreerMatch( new JFrame(), joueursDisponibles );
				System.out.println( match.toString() );
			} 
			catch( ChoixAnnulerException e ) {
				System.out.println("Annulé par l'utilisateur");
			}
			
			
		}
	}
	
}
