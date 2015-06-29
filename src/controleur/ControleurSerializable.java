package controleur;

import internationalisation.Constantes;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import vue.ChoixAnnulerException;
import vue.Vue;
import modele.ModeleSerializable;
import modele.ModeleSerializableException;

public class ControleurSerializable extends Controleur {

		
	public ControleurSerializable( ModeleSerializable modeleSerializable, Vue vue ) {
		super( modeleSerializable, vue );
		
		ActionNouveauTournoi actionNouveauTournoi = new ActionNouveauTournoi();
		ActionChargerTournoi actionChargerTournoi = new ActionChargerTournoi();
		ActionSauvegarderTournoi actionSauvegarderTournoi = new ActionSauvegarderTournoi();
		ActionSauvegarderTournoiSous actionSauvegarderTournoiSous = new ActionSauvegarderTournoiSous();
		
		vue.menuNouveauFichier.setAction(actionNouveauTournoi);
		vue.menuOuvrirFichier.setAction(actionChargerTournoi);
		vue.menuSauvegarder.setAction(actionSauvegarderTournoi);
		vue.menuSauvegarderSous.setAction(actionSauvegarderTournoiSous);
		
		vue.addWindowListener( new ActionFermetureFenetre() );
		vue.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
		
		modeleSerializable.initialiser();
		
	}
	
	
	public ControleurSerializable( ModeleSerializable modeleSerializable ) {
		this( modeleSerializable, new Vue() );		
	}
	
	
	
	
	public class ActionFermetureFenetre extends WindowAdapter {
		
		@Override
        public void windowClosing( WindowEvent event ) {

			try {
				
				enregistrerSiModifie();
				
				event.getWindow().dispose();	// fermeture de la fenetre
				
			} 
			catch( ChoixAnnulerException e ) {
				// l'utilisateur a annuler la sauvegarde, on ne fait rien.
			}
						
        }
		
	}
	
	
	
	public class ActionNouveauTournoi extends AbstractAction {
		
		private static final long serialVersionUID = 3410816670237243192L;

		public ActionNouveauTournoi() {
			super( Constantes.getString(Constantes.NOUVEAU_TOURNOI) );
						
			this.putValue( NAME, Constantes.getString(Constantes.NOUVEAU_TOURNOI) );
			this.putValue( AbstractAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK) );	// Ctrl + N
		}
		
		@Override
		public void actionPerformed( ActionEvent event ) {
			
			try {
				
				enregistrerSiModifie();
				
				ModeleSerializable modeleSerializable = (ModeleSerializable) modele;			
				modeleSerializable.reinitialiserTournoi();
				
			} 
			catch( ChoixAnnulerException e ) {
				// action annulée, on en fait rien.
			}
			
		}
		
	}
	
		
	public class ActionChargerTournoi extends AbstractAction {
		
		private static final long serialVersionUID = -2232094801513093661L;

		public ActionChargerTournoi() {
			super( Constantes.getString(Constantes.CHARGER_TOURNOI) );
						
			this.putValue( NAME, Constantes.getString(Constantes.CHARGER_TOURNOI) );
			this.putValue( AbstractAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK) );	// Ctrl + O
		}
		
		@Override
		public void actionPerformed( ActionEvent event ) {
					
			try {
				
				enregistrerSiModifie();
				
				File fichierCharge = vue.getFichierCharge();
								
				ModeleSerializable modeleSerializable = (ModeleSerializable) modele;
				modeleSerializable.chargerTournoi(fichierCharge);
				
			} 
			catch( ChoixAnnulerException e ) {
				// on ne fait rien si l'utilisateur a annulé.
			}
			catch( IOException | ClassNotFoundException e ) {
				// erreur, impossible de lire le fichier sélectionné.
				
				String titrePopup = Constantes.getString( Constantes.TITRE_POPUP_ERREUR_LECTURE_FICHIER );
				String message = Constantes.getString( Constantes.MESSAGE_ERREUR_LECTURE_FICHIER );
				vue.afficherMessageErreur( titrePopup, message );
			}
						
		}
		
	}
	
	
	
	public class ActionSauvegarderTournoi extends AbstractAction implements Observer {
		
		private static final long serialVersionUID = -2054402669043277270L;
		
		
		public ActionSauvegarderTournoi() {
			super( Constantes.getString(Constantes.SAUVEGARDER_TOURNOI) );
			
			ModeleSerializable modeleSerializable = (ModeleSerializable) modele;
			modeleSerializable.addObserver(this);
			
			this.putValue( NAME, Constantes.getString(Constantes.SAUVEGARDER_TOURNOI) );
			this.putValue( AbstractAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK) );	// Ctrl + S
		}
		
		@Override
		public void actionPerformed( ActionEvent event ) {
			
			try {
				enregistrer();
			} 
			catch( ChoixAnnulerException e ) {
				// on ne fait rien
			}
			
		}

		@Override
		public void update( Observable observable, Object args ) {
			
			ModeleSerializable modeleSerializable = (ModeleSerializable) modele;
			this.setEnabled( modeleSerializable.isModifie() );
			
		}
		
	}
	
	
	public class ActionSauvegarderTournoiSous extends AbstractAction {
		
		private static final long serialVersionUID = -2054402669043277270L;

		public ActionSauvegarderTournoiSous() {
			super( Constantes.getString(Constantes.SAUVEGARDER_TOURNOI_SOUS) );
						
			this.putValue( NAME, Constantes.getString(Constantes.SAUVEGARDER_TOURNOI_SOUS) );
		}
		
		@Override
		public void actionPerformed( ActionEvent event ) {
			
			try {
				enregistrerSous();
			} 
			catch( ChoixAnnulerException e ) {
				// on ne fait rien
			}
			
			
		}
		
	}
	
	
	private void enregistrerSiModifie() throws ChoixAnnulerException {
		
		ModeleSerializable modeleSerializable = (ModeleSerializable) this.modele;
		
		if( ! modeleSerializable.isModifie() ) return;
		
		String titrePopup = Constantes.getString( Constantes.TITRE_POPUP_FICHIER_MODIFIER );
		String message = Constantes.getString( Constantes.MESSAGE_QUESTION_ENREGISTRER_MODIFICATIONS );
		int resultat = this.vue.afficherDialogue( titrePopup, message ); 
		
		if( resultat == JOptionPane.CANCEL_OPTION || resultat == JOptionPane.CLOSED_OPTION ) throw new ChoixAnnulerException("action annulée par l'utilisateur");
		
		if( resultat != JOptionPane.YES_OPTION ) return;
		
		this.enregistrer();
		
	}
	
	
	private void enregistrer() throws ChoixAnnulerException {
		
		ModeleSerializable modeleSerializable = (ModeleSerializable) this.modele;
		
		try {
			modeleSerializable.sauvegarderTournoi();
		} 
		catch( ModeleSerializableException e ) {
			//
			this.enregistrerSous();
		} 
		catch( IOException e ) {
			// erreur d'écriture
			e.printStackTrace();
			String titrePopup = Constantes.getString( Constantes.TITRE_POPUP_ERREUR_ECRITURE_FICHIER );
			String message = Constantes.getString( Constantes.MESSAGE_ERREUR_ECRITURE_FICHIER );
			vue.afficherMessageErreur( titrePopup, message );
		}
		
	}
	
	
	private void enregistrerSous() throws ChoixAnnulerException {
		
		ModeleSerializable modeleSerializable = (ModeleSerializable) this.modele;
		
		try {
			File fichierSauvegarde = this.vue.getFichierSauvegarde();
			modeleSerializable.sauvegarderTournoi(fichierSauvegarde);
		}
		catch( IOException e ) {
			// erreur d'écriture.
			e.printStackTrace();
			String titrePopup = Constantes.getString( Constantes.TITRE_POPUP_ERREUR_ECRITURE_FICHIER );
			String message = Constantes.getString( Constantes.MESSAGE_ERREUR_ECRITURE_FICHIER );
			vue.afficherMessageErreur( titrePopup, message );
		}
		
	}
	

}
