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
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import vue.ChoixAnnulerException;
import vue.VueSerializable;
import modele.ModeleSerializable;
import modele.ModeleSerializableException;

public class ControleurSerializable extends Controleur {

		
	public ControleurSerializable( ModeleSerializable modeleSerializable, VueSerializable vueSerializable ) {
		super( modeleSerializable, vueSerializable );
		
		ActionNouveauTournoi actionNouveauTournoi = new ActionNouveauTournoi();
		ActionChargerTournoi actionChargerTournoi = new ActionChargerTournoi();
		ActionSauvegarderTournoi actionSauvegarderTournoi = new ActionSauvegarderTournoi();
		ActionSauvegarderTournoiSous actionSauvegarderTournoiSous = new ActionSauvegarderTournoiSous();
		
		vueSerializable.menuNouveauFichier.setAction(actionNouveauTournoi);
		vueSerializable.menuOuvrirFichier.setAction(actionChargerTournoi);
		vueSerializable.menuSauvegarder.setAction(actionSauvegarderTournoi);
		vueSerializable.menuSauvegarderSous.setAction(actionSauvegarderTournoiSous);
		
		vueSerializable.addWindowListener( new ActionFermetureFenetre() );
		
		modeleSerializable.initialiser();
		
	}
	
	
	public ControleurSerializable( ModeleSerializable modeleSerializable ) {
		this( modeleSerializable, new VueSerializable() );		
	}
	
	
	
	
	public class ActionFermetureFenetre extends WindowAdapter {
		
		@Override
        public void windowClosing( WindowEvent event ) {

			try {
				enregistrerSiModifie();
			} 
			catch( ChoixAnnulerException e ) {
				// l'utilisateur a annuler la sauvegarde, on ne fait rien.
			}
						
			event.getWindow().dispose();
			
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
				
				VueSerializable vueSerializable = (VueSerializable) vue;
				File fichierCharge = vueSerializable.getFichierCharge();
				
				System.out.println( fichierCharge.getAbsolutePath() );
				
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
		int resultat = this.vue.afficherDialogue( titrePopup, message ); //JOptionPane.showConfirmDialog( vue, "Voulez vous enregistrer les modification ?", "Fichié modifié", JOptionPane.YES_NO_CANCEL_OPTION );
		
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
		VueSerializable vueSerializable = (VueSerializable) this.vue;
		
		try {
			
			File fichierSauvegarde = vueSerializable.getFichierSauvegarde();
			
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
