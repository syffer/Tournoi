package controleur;

import internationalisation.Constantes;

import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import modele.Joueur;
import modele.JoueurDejaExistantException;
import modele.Match;
import modele.Tournoi;
import vue.ListModelMatch;
import vue.TableModelJoueur;
import vue.Vue;

public class Controleur {

	private Tournoi modele;
	private Vue vue;
	
	public Controleur( Tournoi tournoi ) {
		
		this.modele = tournoi;
		this.vue = new Vue();
		
		Update update = new Update();
		ActionAjouterJoueur actionAjouterJoueur = new ActionAjouterJoueur();
		ActionSupprimerJoueur actionSupprimerJoueur = new ActionSupprimerJoueur();
		
		ActionCreerMatch actionCreerMatch = new ActionCreerMatch();
		ActionGenererMatchs actionGenererMatchs = new ActionGenererMatchs();
		ActionAnnulerMatchs actionAnnulerMatchs = new ActionAnnulerMatchs();
		
		ActionSupprimerMatch actionSupprimerMatch = new ActionSupprimerMatch();
		
		this.vue.boutonAjoutJoueur.setAction(actionAjouterJoueur);
		this.vue.boutonSupprimerJoueur.setAction(actionSupprimerJoueur);
		
		this.vue.boutonCreerMatch.setAction(actionCreerMatch);
		this.vue.boutonGenererMatchs.setAction(actionGenererMatchs);
		this.vue.boutonAnnulerLesMatchs.setAction(actionAnnulerMatchs);
		
		this.vue.boutonAnnulerMatch.setAction(actionSupprimerMatch);

		this.modele.addObserver(update);
		this.modele.addObserver(actionAnnulerMatchs);
				
		this.modele.initialiser();
		this.vue.setVisible(true);
		
	}
	
	
	public class Update implements Observer {

		@Override
		public void update( Observable observable, Object args ) {
			// TODO Auto-generated method stub
			
			List<Joueur> joueurs = modele.getJoueurs();
			Collections.sort( joueurs, Collections.reverseOrder() );
						
			TableModelJoueur modelTableau = (TableModelJoueur) vue.tableauJoueurs.getModel();
			
			modelTableau.setJoueurs(joueurs);
			
			
			List<Match> matchs = modele.getMatchs();
			ListModelMatch modelListe = (ListModelMatch) vue.listeMatchs.getModel();
			modelListe.setMatchs(matchs);
			
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
			catch (JoueurDejaExistantException e) {
				String message = Constantes.getString( Constantes.MESSAGE_JOUEUR_EXISTE_DEJA ) + " : " + nomJoueur;
				JOptionPane.showMessageDialog( vue, message );
			}
			
		}
		
	}

	
	public class ActionSupprimerJoueur extends AbstractAction {
		
		private static final long serialVersionUID = 7385240649004707996L;

		public ActionSupprimerJoueur() {
			super( Constantes.getString(Constantes.SUPPRIMER_UN_JOUEUR) );
			
			this.putValue( NAME, Constantes.getString(Constantes.SUPPRIMER_UN_JOUEUR) );
			
		}
		
		@Override
		public void actionPerformed( ActionEvent event ) {
			throw new RuntimeException("NON IMPLEMENTE!!!");
		}
		
	}
	
	
	public class ActionCreerMatch extends AbstractAction {
		
		private static final long serialVersionUID = -1076399474003965663L;

		public ActionCreerMatch() {
			super( Constantes.getString(Constantes.CREER_UN_MATCH) );
			
			this.putValue( NAME, Constantes.getString(Constantes.CREER_UN_MATCH) );
			
		}
		
		@Override
		public void actionPerformed( ActionEvent event ) {
			throw new RuntimeException("NON IMPLEMENTE!!!");
		}
		
	}
	
	public class ActionGenererMatchs extends AbstractAction implements Observer {
		
		private static final long serialVersionUID = -1076399474003965663L;

		public ActionGenererMatchs() {
			super( Constantes.getString(Constantes.GENERER_LES_MATCHS) );
			
			this.putValue( NAME, Constantes.getString(Constantes.GENERER_LES_MATCHS) );
			
		}
		
		@Override
		public void actionPerformed( ActionEvent event ) {
			modele.genererMatchs();
		}

		@Override
		public void update(Observable arg0, Object arg1) {
					
		}
		
	}



	public class ActionAnnulerMatchs extends AbstractAction implements Observer {
		
		private static final long serialVersionUID = 5351141276130893274L;

		public ActionAnnulerMatchs() {
			super( Constantes.getString(Constantes.ANNULER_LES_MATCHS) );
			
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


	

	public class ActionSupprimerMatch extends AbstractAction implements Observer {
		
		private static final long serialVersionUID = -3586680284444075521L;

		public ActionSupprimerMatch() {
			super( Constantes.getString(Constantes.SUPPRIMER_LE_MATCH) );
			
			this.putValue( NAME, Constantes.getString(Constantes.SUPPRIMER_LE_MATCH) );
		}
		
		@Override
		public void actionPerformed( ActionEvent event ) {
			
		}

		@Override
		public void update( Observable observable, Object args ) {
			//this.setEnabled( ! vue.listeMatchs.isSelectionEmpty() );
		}
	
	}
	
}
