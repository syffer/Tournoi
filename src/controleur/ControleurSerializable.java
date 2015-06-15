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
            
			/*
			System.out.println("Closed");
            event.getWindow().dispose();
            */
            
            
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
			
			
			/*
			ModeleSerializable modeleSerializable = (ModeleSerializable) modele;
			
			if( modeleSerializable.isModifie() ) {
				
				
				
			}
			
			modeleSerializable.reinitialiserTournoi();
			*/
		
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
				
				VueSerializable vueSerializable = (VueSerializable) vue;
				File fichierCharge = vueSerializable.getFichierCharge();
				
				System.out.println( fichierCharge.getPath() );
				
			} 
			catch( ChoixAnnulerException e ) {
				// on ne fait rien si l'utilisateur a annulé.
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
			
			
			/*
			ModeleSerializable modeleSerializable = (ModeleSerializable) modele;
			
			if( modeleSerializable.possedeUnFichierDeSauvegarde() ) {
				
			}
			else {
				
			}
			*/		
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
			
			
			/*
			try {
				
				VueSerializable vueSerializable = (VueSerializable) vue;
				File fichierDeSauvegarde = vueSerializable.getFichierSauvegarde();
				
				ModeleSerializable modeleSerializable = (ModeleSerializable) modele;
				modeleSerializable.sauvegarderTournoi(fichierDeSauvegarde);
								
			} 
			catch( ChoixAnnulerException e ) {
				// on ne fait rien si l'utilisateur a annulé.
				
			} catch (IOException e) {
								
			}
			*/		
		}
		
	}
	
	
	public void enregistrerTournoi() {
		
		ModeleSerializable modeleSerializable = (ModeleSerializable) this.modele;
		
		try {
			
			modeleSerializable.sauvegarderTournoi();
			
			
			
		} 
		catch( ModeleSerializableException e ) {
			
			VueSerializable vueSerializable = (VueSerializable) this.vue;
			
			try {
				
				File fichier = vueSerializable.getFichierSauvegarde();
				
			} 
			catch( ChoixAnnulerException e1 ) {

				
				
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	

}
