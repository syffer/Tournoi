package controleur;

import internationalisation.Constantes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import modele.Modele;
import tournoi.ComparateurJoueurNbPoints;
import tournoi.Joueur;
import tournoi.JoueurDejaExistantException;
import tournoi.Match;
import vue.ChoixAnnulerException;
import vue.ListModelMatch;
import vue.TableModelJoueur;
import vue.Vue;

public class Controleur {

	protected Modele modele;
	protected Vue vue;
	
	private ActionSupprimerJoueur actionSupprimerJoueur;
	
	private ActionResoudreMatchGagnant actionResoudreMatchJoueur1;
	private ActionResoudreMatchGagnant actionResoudreMatchJoueur2;
	private ActionResoudreMatchAbandon actionResoudreMatchAbandonJoueur1;
	private ActionResoudreMatchAbandon actionResoudreMatchAbandonJoueur2;		
	private ActionMatchNull actionMatchNull;
	private ActionSupprimerMatch actionSupprimerMatch;
	
	
	public Controleur( Modele modele, Vue vue ) {
		
		this.modele = modele;
		this.vue = vue;
		
		Update update = new Update();
		
		ActionSelectionnerTableauJoueurs actionSelectionnerTableau = new ActionSelectionnerTableauJoueurs();
		ActionAjouterJoueur actionAjouterJoueur = new ActionAjouterJoueur();
		this.actionSupprimerJoueur = new ActionSupprimerJoueur();
		ActionChampTexteEntrer actionChampTexteEntrer = new ActionChampTexteEntrer();
		
		ActionCreerMatch actionCreerMatch = new ActionCreerMatch();
		ActionGenererMatchs actionGenererMatchs = new ActionGenererMatchs();
		ActionAnnulerMatchs actionAnnulerMatchs = new ActionAnnulerMatchs();
		
		ActionSelectionListeMatch actionSelectionListe = new ActionSelectionListeMatch();
		this.actionResoudreMatchJoueur1 = new ActionResoudreMatchGagnant(true);
		this.actionResoudreMatchJoueur2 = new ActionResoudreMatchGagnant(false);
		this.actionResoudreMatchAbandonJoueur1 = new ActionResoudreMatchAbandon(true);
		this.actionResoudreMatchAbandonJoueur2 = new ActionResoudreMatchAbandon(false);		
		this.actionMatchNull = new ActionMatchNull();
		this.actionSupprimerMatch = new ActionSupprimerMatch();
		
		
		this.vue.tableauJoueurs.getSelectionModel().addListSelectionListener(actionSelectionnerTableau);
		this.vue.boutonAjoutJoueur.setAction(actionAjouterJoueur);
		this.vue.boutonSupprimerJoueur.setAction(actionSupprimerJoueur);
		this.vue.champAjoutJoueur.addActionListener(actionChampTexteEntrer);
					
		
		this.vue.boutonCreerMatch.setAction(actionCreerMatch);
		this.vue.boutonGenererMatchs.setAction(actionGenererMatchs);
		this.vue.boutonAnnulerLesMatchs.setAction(actionAnnulerMatchs);
		this.vue.menuGenererMatchs.setAction(actionGenererMatchs);
		
		this.vue.listeMatchs.addListSelectionListener(actionSelectionListe);
		this.vue.boutonJoueur1Gagne.setAction(actionResoudreMatchJoueur1);
		this.vue.boutonJoueur2Gagne.setAction(actionResoudreMatchJoueur2);
		this.vue.boutonJoueur1Abandonne.setAction(actionResoudreMatchAbandonJoueur1);
		this.vue.boutonJoueur2Abandonne.setAction(actionResoudreMatchAbandonJoueur2);
		this.vue.boutonMatchNull.setAction(actionMatchNull);
		this.vue.boutonAnnulerMatch.setAction(actionSupprimerMatch);
		
		
		// raccourci supprimer joueur sélectionné
        InputMap inputMap = this.vue.tableauJoueurs.getInputMap( JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT );
        ActionMap actionMap = this.vue.tableauJoueurs.getActionMap();
        KeyStroke enterKey = KeyStroke.getKeyStroke( KeyEvent.VK_DELETE, 0 );
        inputMap.put( enterKey, "Action.delete" );
        actionMap.put( "Action.delete", actionSupprimerJoueur );
		
		// raccourci supprimer match sélectionné
		inputMap = this.vue.listeMatchs.getInputMap( JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT );
        actionMap = this.vue.listeMatchs.getActionMap();
        enterKey = KeyStroke.getKeyStroke( KeyEvent.VK_DELETE, 0 );
        inputMap.put( enterKey, "Action.delete" );
        actionMap.put( "Action.delete", actionSupprimerMatch );
		
		
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
			
			// on ne met pas à jour les listes si on sélectionne l'un de leurs items
			if( args instanceof Boolean && (boolean) args == false ) return;
			// on ne met pas à jour le tableau et la liste si l'argument passé en paramètre est un booléen à faux.
			// permet d'éviter la déselection après avoir modifier les listes    (on sélectionne, modifie le model, qui modifie la liste, qui déselectionne ses items)
			
			// mise à jour du tableau des joueurs
			List<Joueur> joueurs = new ArrayList<Joueur>( modele.getJoueurs() );
			Collections.sort( joueurs, Collections.reverseOrder( ComparateurJoueurNbPoints.getComparateur() ) );
			TableModelJoueur modelTableau = (TableModelJoueur) vue.tableauJoueurs.getModel();
			modelTableau.setJoueurs(joueurs);
			
			// mise à jour de la liste des matchs
			List<Match> matchs = modele.getMatchs();
			ListModelMatch modelListe = (ListModelMatch) vue.listeMatchs.getModel();
			modelListe.setMatchs(matchs);
			
			// on ne sélectionne rien dans la nouvelle liste
			vue.listeMatchs.setSelectedIndex(-1);
			vue.listeMatchs.setSelectedIndices( new int[0] );
			
		}
		
		
	}
	
	
	
	
	public class ActionSelectionnerTableauJoueurs implements ListSelectionListener {
		
		@Override
		public void valueChanged( ListSelectionEvent event ) {
			
			if ( event.getValueIsAdjusting() ) return;
			
			// on indique au modele quel élément a été sélectionné.
			boolean joueurSelectionne = ! vue.tableauJoueurs.getSelectionModel().isSelectionEmpty();			
			
			actionSupprimerJoueur.setEnabled(joueurSelectionne);
			
		}
		
	}
	
	public class ActionSelectionListeMatch implements ListSelectionListener {
		
		@Override
		public void valueChanged( ListSelectionEvent event ) {
			
			if ( event.getValueIsAdjusting() ) return;
			
			boolean matchSelectionne = ! vue.listeMatchs.isSelectionEmpty();
			
			actionResoudreMatchJoueur1.setEnabled(matchSelectionne);
			actionResoudreMatchJoueur2.setEnabled(matchSelectionne);
			actionResoudreMatchAbandonJoueur1.setEnabled(matchSelectionne);
			actionResoudreMatchAbandonJoueur2.setEnabled(matchSelectionne);
			actionMatchNull.setEnabled(matchSelectionne);
			actionSupprimerMatch.setEnabled(matchSelectionne);
			
			String nomActionJoueur1 = "J1";
			String nomActionJoueur2 = "J2";
			if( matchSelectionne ) {
				Match match = vue.listeMatchs.getSelectedValue();
				nomActionJoueur1 = match.getJoueur1().getNom();
				nomActionJoueur2 = match.getJoueur2().getNom();
			}
			
			actionResoudreMatchJoueur1.putValue( AbstractAction.NAME, nomActionJoueur1 + " " + Constantes.getString(Constantes.GAGNE) );
			actionResoudreMatchJoueur2.putValue( AbstractAction.NAME, nomActionJoueur2 + " " + Constantes.getString(Constantes.GAGNE) );
			actionResoudreMatchAbandonJoueur1.putValue( AbstractAction.NAME, nomActionJoueur1 + " " + Constantes.getString(Constantes.ABANDONNE) );
			actionResoudreMatchAbandonJoueur2.putValue( AbstractAction.NAME, nomActionJoueur2 + " " + Constantes.getString(Constantes.ABANDONNE) );
			
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
			ajouterJoueur();			
		}
		
	}
	
	
	public class ActionChampTexteEntrer implements ActionListener {

		@Override
		public void actionPerformed( ActionEvent event ) {	// déclancher lors de l'appui sur la touche "ENTRER"
			ajouterJoueur();	
		}
		
	}

	
	private void ajouterJoueur() {
		
		String nomJoueur = vue.champAjoutJoueur.getText();
		if( nomJoueur.equals("") ) return;
		
		try {
			modele.ajouterJoueur(nomJoueur);	// lance une exception si le joueur existe déjà
			vue.champAjoutJoueur.setText("");
		} 
		catch( JoueurDejaExistantException e ) {
			String message = Constantes.getString( Constantes.MESSAGE_JOUEUR_EXISTE_DEJA ) + " : " + nomJoueur;
			vue.afficherMessage(message);
		}
		
	}
	
	public class ActionSupprimerJoueur extends AbstractAction {
		
		private static final long serialVersionUID = 7385240649004707996L;

		public ActionSupprimerJoueur() {
			super( Constantes.getString(Constantes.SUPPRIMER_UN_JOUEUR) );
			
			this.setEnabled(false);			
			this.putValue( NAME, Constantes.getString(Constantes.SUPPRIMER_UN_JOUEUR) );
		}
		
		@Override
		public void actionPerformed( ActionEvent event ) {
			
			int ligneSelectionnee = vue.tableauJoueurs.getSelectedRow();
			if( ligneSelectionnee == -1 ) return;	// pas de ligne sélectionnée

			// on demande confirmation
			int reponse = vue.afficherDialogueAvertissement( Constantes.getString(Constantes.MESSAGE_QUESTION_SUPPRESSION_JOUEUR) );
			if( reponse != JOptionPane.YES_OPTION ) return;
			
			TableModelJoueur modelTableau = (TableModelJoueur) vue.tableauJoueurs.getModel();
			Joueur joueurSelectionne = modelTableau.getJoueur(ligneSelectionnee);
			modele.supprimerJoueur(joueurSelectionne);
			
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
			
			try {
				Collection<Joueur> joueursDisponibles = modele.getJoueursDisponibles();
				Match match = vue.afficherDialogueCreerMatch(joueursDisponibles);
				modele.ajouterMatch( match.getJoueur1(), match.getJoueur2() );
			} 
			catch( ChoixAnnulerException e ) {
				// l'utilisateur a annuler la sauvegarde, on ne fait rien.
			}
			
		}

		@Override
		public void update( Observable observable, Object args ) {
			this.setEnabled( modele.isJoueursDisponibles() );
		}
		
	}
	
	public class ActionGenererMatchs extends AbstractAction implements Observer {
		
		private static final long serialVersionUID = -1076399474003965663L;

		public ActionGenererMatchs() {
			super( Constantes.getString(Constantes.GENERER_LES_MATCHS) );
			
			modele.addObserver(this);
			
			this.putValue( NAME, Constantes.getString(Constantes.GENERER_LES_MATCHS) );
			this.putValue( AbstractAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_G, KeyEvent.CTRL_DOWN_MASK) );	// Ctrl + G
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
			this.setEnabled( modele.possedeMatchsGeneres() );
		}
	
	}

	
	
	
	public class ActionResoudreMatchGagnant extends AbstractAction {
		
		private static final long serialVersionUID = 6091986863951471213L;
		
		private boolean concerneJoueur1;
		
		public ActionResoudreMatchGagnant( boolean concerneJoueur1 ) {
			super( ( (concerneJoueur1) ? "J1 " : "J2 " ) + Constantes.getString(Constantes.GAGNE) );
			
			this.concerneJoueur1 = concerneJoueur1;
			
			this.setEnabled(false);
			this.putValue( NAME, ( (concerneJoueur1) ? "J1 " : "J2 " ) + Constantes.getString(Constantes.GAGNE) );
		}
		
		@Override
		public void actionPerformed( ActionEvent event ) {
			
			Match matchSelectionne = vue.listeMatchs.getSelectedValue();		
			
			if( this.concerneJoueur1 ) modele.resoudreMatchNormalGagnantJoueur1(matchSelectionne);
			else modele.resoudreMatchNormalGagnantJoueur2(matchSelectionne);
						
		}
		
	}
	
	
	public class ActionResoudreMatchAbandon extends AbstractAction {
		
		private static final long serialVersionUID = 6091986863951471213L;
		
		private boolean concerneJoueur1;
		
		public ActionResoudreMatchAbandon( boolean concerneJoueur1 ) {
			super( ( (concerneJoueur1) ? "J1 " : "J2 " ) + Constantes.getString(Constantes.ABANDONNE) );
			
			this.concerneJoueur1 = concerneJoueur1;
			
			this.setEnabled(false);
			this.putValue( NAME, ( (concerneJoueur1) ? "J1 " : "J2 " ) + Constantes.getString(Constantes.ABANDONNE) );
		}
		
		@Override
		public void actionPerformed( ActionEvent event ) {
			
			Match matchSelectionne = vue.listeMatchs.getSelectedValue();			

			if( this.concerneJoueur1 ) modele.resoudreMatchNormalGagnantJoueur1(matchSelectionne);
			else modele.resoudreMatchNormalGagnantJoueur2(matchSelectionne);
			
		}
		
	}
	
	
	
	public class ActionMatchNull extends AbstractAction {

		private static final long serialVersionUID = 838869417950214551L;

		public ActionMatchNull() {
			super( Constantes.getString(Constantes.MATCH_NULL) );
			
			this.setEnabled(false);
			this.putValue( NAME, Constantes.getString(Constantes.MATCH_NULL) );
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Match match = vue.listeMatchs.getSelectedValue();
			modele.resoudreMatchNull(match);
		}
		
	}
	
	

	public class ActionSupprimerMatch extends AbstractAction {
		
		private static final long serialVersionUID = -3586680284444075521L;

		public ActionSupprimerMatch() {
			super( Constantes.getString(Constantes.SUPPRIMER_LE_MATCH) );
			
			this.setEnabled(false);
			this.putValue( NAME, Constantes.getString(Constantes.SUPPRIMER_LE_MATCH) );
		}
		
		@Override
		public void actionPerformed( ActionEvent event ) {
			Match match = vue.listeMatchs.getSelectedValue();
			modele.supprimerMatch(match);	
		}
	
	}
	
}
