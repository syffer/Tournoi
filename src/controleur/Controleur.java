package controleur;

import internationalisation.Constantes;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import modele.Joueur;
import modele.JoueurDejaExistantException;
import modele.Tournoi;
import vue.Vue;

public class Controleur {

	private Tournoi modele;
	private Vue vue;
	
	public Controleur( Tournoi tournoi ) {
		
		this.modele = tournoi;
		this.vue = new Vue();
		
		
		ActionAjouterJoueur actionAjouterJoueur = new ActionAjouterJoueur();
		
		this.vue.boutonAjoutJoueur.setAction(actionAjouterJoueur);
		
		this.vue.setVisible(true);
		
	}
	
	
		
	public class ActionAjouterJoueur extends AbstractAction {
		
		private static final long serialVersionUID = 8459953774867361663L;

		public ActionAjouterJoueur() {
			super( Constantes.getString(Constantes.AJOUTER_UN_JOUEUR) );
			
			this.putValue( NAME,  Constantes.getString(Constantes.AJOUTER_UN_JOUEUR) );
			
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

}
