package controleur;

import internationalisation.Constantes;

import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import modele.Joueur;
import modele.JoueurDejaExistantException;
import modele.Match;
import modele.MatchException;
import modele.Modele;
import vue.ListModelMatch;
import vue.TableModelJoueur;
import vue.Vue;

public class Controleur {

	protected Modele modele;
	protected Vue vue;
		
	public Controleur( Modele modele, Vue vue ) {
		
		this.modele = modele;
		this.vue = vue;
		
		Update update = new Update();
		
		ActionSelectionnerTableauJoueurs actionSelectionnerTableau = new ActionSelectionnerTableauJoueurs();
		ActionAjouterJoueur actionAjouterJoueur = new ActionAjouterJoueur();
		ActionSupprimerJoueur actionSupprimerJoueur = new ActionSupprimerJoueur();
		
		ActionCreerMatch actionCreerMatch = new ActionCreerMatch();
		ActionGenererMatchs actionGenererMatchs = new ActionGenererMatchs();
		ActionAnnulerMatchs actionAnnulerMatchs = new ActionAnnulerMatchs();
		
		ActionSelectionListeMatch actionSelectionListe = new ActionSelectionListeMatch();
		ActionResoudreMatchGagnant actionResoudreMatchJoueur1 = new ActionResoudreMatchGagnant(true);
		ActionResoudreMatchGagnant actionResoudreMatchJoueur2 = new ActionResoudreMatchGagnant(false);
		ActionResoudreMatchAbandon actionResoudreMatchAbandonJoueur1 = new ActionResoudreMatchAbandon(true);
		ActionResoudreMatchAbandon actionResoudreMatchAbandonJoueur2 = new ActionResoudreMatchAbandon(false);		
		ActionMatchNull actionMatchNull = new ActionMatchNull();
		ActionSupprimerMatch actionSupprimerMatch = new ActionSupprimerMatch();
		
		this.vue.tableauJoueurs.getSelectionModel().addListSelectionListener(actionSelectionnerTableau);
		this.vue.boutonAjoutJoueur.setAction(actionAjouterJoueur);
		this.vue.boutonSupprimerJoueur.setAction(actionSupprimerJoueur);
				
		this.vue.boutonCreerMatch.setAction(actionCreerMatch);
		this.vue.boutonGenererMatchs.setAction(actionGenererMatchs);
		this.vue.boutonAnnulerLesMatchs.setAction(actionAnnulerMatchs);
		
		this.vue.listeMatchs.addListSelectionListener(actionSelectionListe);
		this.vue.boutonJoueur1Gagne.setAction(actionResoudreMatchJoueur1);
		this.vue.boutonJoueur2Gagne.setAction(actionResoudreMatchJoueur2);
		this.vue.boutonJoueur1Abandonne.setAction(actionResoudreMatchAbandonJoueur1);
		this.vue.boutonJoueur2Abandonne.setAction(actionResoudreMatchAbandonJoueur2);
		this.vue.boutonMatchNull.setAction(actionMatchNull);
		this.vue.boutonAnnulerMatch.setAction(actionSupprimerMatch);
		
		this.modele.addObserver(update);
		
		this.modele.initialiser();
		this.vue.setVisible(true);
	
	}
	
	public Controleur( Modele modele ) {
		this( modele, new Vue() );	
	}
	
	
	
	public class Update implements Observer {

		@Override
		public void update( Observable observable, Object args ) {
			
			// on ne met pas à jour le tableau et la liste si l'argument passé en paramètre est un booléen à faux.
			if( args instanceof Boolean && (boolean) args == false ) return;
			
			List<Joueur> joueurs = modele.getJoueurs();
			Collections.sort( joueurs, Collections.reverseOrder() );
			TableModelJoueur modelTableau = (TableModelJoueur) vue.tableauJoueurs.getModel();
			modelTableau.setJoueurs(joueurs);
			
			
			List<Match> matchs = modele.getMatchs();
			ListModelMatch modelListe = (ListModelMatch) vue.listeMatchs.getModel();
			modelListe.setMatchs(matchs);
			
			vue.listeMatchs.setSelectedIndex(-1);
			vue.listeMatchs.setSelectedIndices( new int[0] );	// on ne sélectionne rien dans la nouvelle liste
			
		}
		
		
	}
	
	
	
	
	public class ActionSelectionnerTableauJoueurs implements ListSelectionListener {
		
		public ActionSelectionnerTableauJoueurs() {
			
		}
		
		@Override
		public void valueChanged( ListSelectionEvent event ) {
			
			if ( event.getValueIsAdjusting() ) return;
						
			ListSelectionModel lsm = (ListSelectionModel) event.getSource();
			boolean joueurSelectionne = ! lsm.isSelectionEmpty();
			
			modele.setJoueurSelectionne(joueurSelectionne);
						
		}
		
	}
	
	public class ActionSelectionListeMatch implements ListSelectionListener {
				
		public ActionSelectionListeMatch() {
			
		}
		
		@Override
		public void valueChanged( ListSelectionEvent event ) {
			
			if ( event.getValueIsAdjusting() ) return;
			
			boolean matchSelectionne = ! vue.listeMatchs.isSelectionEmpty();
			
			modele.setMatchSelectionne(matchSelectionne);
			
		}
		
	}
	
	
		
	public class ActionAjouterJoueur extends AbstractAction {
		
		private static final long serialVersionUID = 8459953774867361663L;

		public ActionAjouterJoueur() {
			super( Constantes.getString(Constantes.AJOUTER_UN_JOUEUR) );
			
			this.putValue( NAME, Constantes.getString(Constantes.AJOUTER_UN_JOUEUR) );
			
		}
		
		@Override
		public void actionPerformed( ActionEvent event ) {
			
			String nomJoueur = vue.champAjoutJoueur.getText();
			if( nomJoueur.equals("") ) return;
			
			try {
				modele.ajouterJoueur(nomJoueur);
				vue.champAjoutJoueur.setText("");
			} 
			catch( JoueurDejaExistantException e ) {
				String message = Constantes.getString( Constantes.MESSAGE_JOUEUR_EXISTE_DEJA ) + " : " + nomJoueur;
				vue.afficherMessage(message);
			}
			
		}
		
	}

	
	public class ActionSupprimerJoueur extends AbstractAction implements Observer {
		
		private static final long serialVersionUID = 7385240649004707996L;

		public ActionSupprimerJoueur() {
			super( Constantes.getString(Constantes.SUPPRIMER_UN_JOUEUR) );
			
			modele.addObserver(this);
			
			this.putValue( NAME, Constantes.getString(Constantes.SUPPRIMER_UN_JOUEUR) );
			
		}
		
		@Override
		public void actionPerformed( ActionEvent event ) {
			
			int ligneSelectionnee = vue.tableauJoueurs.getSelectedRow();
			if( ligneSelectionnee == -1 ) return;	// pas de ligne sélectionnée

			// on demande confirmation
			int reponse = vue.afficherDialogueAvertissement( Constantes.getString(Constantes.MESSAGE_CONFIRMATION_SUPPRESSION_JOUEUR) );
			if( reponse != 0 ) return;
			
			TableModelJoueur modelTableau = (TableModelJoueur) vue.tableauJoueurs.getModel();
			Joueur joueurSelectionne = modelTableau.getJoueur(ligneSelectionnee);
			modele.supprimerJoueur(joueurSelectionne);
			
		}

		@Override
		public void update( Observable observable, Object args ) {
			this.setEnabled( modele.isJoueurSelectionne() );
		}
		
	}
	
	
	
	
	
	
	public class ActionCreerMatch extends AbstractAction implements Observer {
		
		private static final long serialVersionUID = -1076399474003965663L;

		public ActionCreerMatch() {
			super( Constantes.getString(Constantes.CREER_UN_MATCH) );
			
			modele.addObserver(this);
			
			this.putValue( NAME, Constantes.getString(Constantes.CREER_UN_MATCH) );
		}
		
		@Override
		public void actionPerformed( ActionEvent event ) {
			throw new RuntimeException("NON IMPLEMENTE!!!");
		}

		@Override
		public void update( Observable observable, Object args ) {
			//this.setEnabled( modele.isJoueursDisponibles() );
			this.setEnabled(false);		//--------------------------------------------------------- ancre
		}
		
	}
	
	public class ActionGenererMatchs extends AbstractAction implements Observer {
		
		private static final long serialVersionUID = -1076399474003965663L;

		public ActionGenererMatchs() {
			super( Constantes.getString(Constantes.GENERER_LES_MATCHS) );
			
			modele.addObserver(this);
			
			this.putValue( NAME, Constantes.getString(Constantes.GENERER_LES_MATCHS) );
		}
		
		@Override
		public void actionPerformed( ActionEvent event ) {
			modele.genererMatchs();
		}

		@Override
		public void update( Observable observable, Object args ) {
			this.setEnabled( modele.isJoueursDisponibles() );
		}
		
	}

	public class ActionAnnulerMatchs extends AbstractAction implements Observer {
		
		private static final long serialVersionUID = 5351141276130893274L;

		public ActionAnnulerMatchs() {
			super( Constantes.getString(Constantes.ANNULER_LES_MATCHS) );
			
			modele.addObserver(this);
			
			this.putValue( NAME, Constantes.getString(Constantes.ANNULER_LES_MATCHS) );
		}
		
		@Override
		public void actionPerformed( ActionEvent event ) {
			modele.annulerMatchs();	
		}

		@Override
		public void update( Observable observable, Object args ) {
			this.setEnabled( ! modele.getMatchs().isEmpty() );
		}
	
	}

	
	
	
	public class ActionResoudreMatchGagnant extends AbstractAction implements Observer {
		
		private static final long serialVersionUID = 6091986863951471213L;
		
		private boolean concerneJoueur1;
		
		public ActionResoudreMatchGagnant( boolean concerneJoueur1 ) {
			super( ( (concerneJoueur1) ? "J1 " : "J2 " ) + Constantes.getString(Constantes.GAGNE) );
			
			this.concerneJoueur1 = concerneJoueur1;
			modele.addObserver(this);
			
			this.putValue( NAME, ( (concerneJoueur1) ? "J1 " : "J2 " ) + Constantes.getString(Constantes.GAGNE) );
		}
		
		@Override
		public void actionPerformed( ActionEvent event ) {
			
			Match matchSelectionne = vue.listeMatchs.getSelectedValue();		
			Joueur gagnant = ( this.concerneJoueur1 ) ? matchSelectionne.getJoueur1() : matchSelectionne.getJoueur2();
			
			try {
				modele.resoudreMatchNormal( matchSelectionne, gagnant );
			} 
			catch( MatchException e ) {
				e.printStackTrace();
				vue.afficherMessageErreur( Constantes.getString(Constantes.MESSAGE_ERREUR_RESOLUTION_MATCH) );
			}
			
		}

		@Override
		public void update( Observable observer, Object args ) {
			
			String nomBouton = null;
			if( modele.isMatchSelectionne() ) {
				
				Match match = vue.listeMatchs.getSelectedValue();
				Joueur joueur = ( this.concerneJoueur1 ) ? match.getJoueur1() : match.getJoueur2();
				nomBouton = joueur.getNom() + " " + Constantes.getString(Constantes.GAGNE);
			
			}
			else nomBouton = ( ( this.concerneJoueur1 ) ? "J1 " : "J2 " ) + Constantes.getString(Constantes.GAGNE);
			
			
			this.setEnabled( modele.isMatchSelectionne() );
			this.putValue( NAME, nomBouton );
			
		}
		
	}
	
	
	public class ActionResoudreMatchAbandon extends AbstractAction implements Observer {
		
		private static final long serialVersionUID = 6091986863951471213L;
		
		private boolean concerneJoueur1;
		
		public ActionResoudreMatchAbandon( boolean concerneJoueur1 ) {
			super( ( (concerneJoueur1) ? "J1 " : "J2 " ) + Constantes.getString(Constantes.ABANDONNE) );
			
			this.concerneJoueur1 = concerneJoueur1;
			modele.addObserver(this);
			
			this.putValue( NAME, ( (concerneJoueur1) ? "J1 " : "J2 " ) + Constantes.getString(Constantes.ABANDONNE) );
		}
		
		@Override
		public void actionPerformed( ActionEvent event ) {
			
			Match matchSelectionne = vue.listeMatchs.getSelectedValue();			
			Joueur abandonne = ( this.concerneJoueur1 ) ? matchSelectionne.getJoueur1() : matchSelectionne.getJoueur2();
			
			try {
				modele.resoudreMatchParAbandon( matchSelectionne, abandonne );
			} 
			catch( MatchException e ) {
				e.printStackTrace();
				vue.afficherMessageErreur( Constantes.getString(Constantes.MESSAGE_ERREUR_RESOLUTION_MATCH) );
			}
			
		}

		@Override
		public void update( Observable observer, Object args ) {
			
			String nomBouton = null;
			if( modele.isMatchSelectionne() ) {
								
				Match match = vue.listeMatchs.getSelectedValue();
				Joueur joueur = ( this.concerneJoueur1 ) ? match.getJoueur1() : match.getJoueur2();
				nomBouton = joueur.getNom() + " " + Constantes.getString(Constantes.ABANDONNE);
			
			}
			else nomBouton = ( ( this.concerneJoueur1 ) ? "J1 " : "J2 " ) + Constantes.getString(Constantes.ABANDONNE);
			
			
			this.setEnabled( modele.isMatchSelectionne() );
			this.putValue( NAME, nomBouton );
			
		}
		
	}
	
	
	
	public class ActionMatchNull extends AbstractAction implements Observer {

		private static final long serialVersionUID = 838869417950214551L;

		public ActionMatchNull() {
			super( Constantes.getString(Constantes.MATCH_NULL) );
			
			modele.addObserver(this);
			
			this.putValue( NAME, Constantes.getString(Constantes.MATCH_NULL) );
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			Match match = vue.listeMatchs.getSelectedValue();
			modele.resoudreMatchNull(match);
			
		}

		@Override
		public void update(Observable arg0, Object arg1) {
			this.setEnabled( modele.isMatchSelectionne() );
		}
		
		
	}
	
	

	public class ActionSupprimerMatch extends AbstractAction implements Observer {
		
		private static final long serialVersionUID = -3586680284444075521L;

		public ActionSupprimerMatch() {
			super( Constantes.getString(Constantes.SUPPRIMER_LE_MATCH) );
			
			modele.addObserver(this);
			
			this.putValue( NAME, Constantes.getString(Constantes.SUPPRIMER_LE_MATCH) );
		}
		
		@Override
		public void actionPerformed( ActionEvent event ) {
			
			Match match = vue.listeMatchs.getSelectedValue();
			modele.supprimerMatch(match);
			
		}

		@Override
		public void update( Observable observable, Object args ) {
			this.setEnabled( modele.isMatchSelectionne() );
		}
	
	}
	
}
