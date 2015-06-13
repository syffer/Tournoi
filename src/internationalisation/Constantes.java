package internationalisation;

import java.util.Locale;
import java.util.ResourceBundle;

public class Constantes {
	
	public static final String TITRE_FENETRE = "TITRE_FENETRE";
	public static final String TITRE_PANNEAU_JOUEURS = "TITRE_PANNEAU_JOUEURS";
	public static final String TITRE_PANNEAU_MATCHS = "TITRE_PANNEAU_MATCHS";
	public static final String TITRE_MENU_FICHIER = "TITRE_MENU_FICHIER";
	public static final String TITRE_MENU_EDITION = "TITRE_MENU_EDITION";
	public static final String TITRE_TABLEAU_COLONNE_NOM = "TITRE_TABLEAU_COLONNE_NOM";
	public static final String TITRE_TABLEAU_COLONNE_POINTS = "TITRE_TABLEAU_COLONNE_POINTS";
	public static final String TITRE_TABLEAU_COLONNE_ETAT = "TITRE_TABLEAU_COLONNE_ETAT";
	
	public static final String CREER_UN_MATCH = "CREER_UN_MATCH";
	public static final String GENERER_LES_MATCHS = "GENERER_LES_MATCHS";
	public static final String ANNULER_LES_MATCHS = "ANNULER_LES_MATCHS";
	
	public static final String AJOUTER_UN_JOUEUR = "AJOUTER_UN_JOUEUR";
	public static final String SUPPRIMER_UN_JOUEUR = "SUPPRIMER_UN_JOUEUR";
	
	public static final String NOUVEAU_TOURNOI = "NOUVEAU_TOURNOI";
	public static final String CHARGER_TOURNOI = "CHARGER_TOURNOI";
	public static final String SAUVEGARDER_TOURNOI = "SAUVEGARDER_TOURNOI";
	
	public static final String UNDO = "UNDO";
	public static final String REDO = "REDO";
	
	public static final String GAGNE = "GAGNE";
	public static final String ABANDONNE = "ABANDONNE";
	public static final String MATCH_NULL = "MATCH_NULL";
	public static final String SUPPRIMER_LE_MATCH = "SUPPRIMER_LE_MATCH";
	
	public static final String MESSAGE_JOUEUR_EXISTE_DEJA = "MESSAGE_JOUEUR_EXISTE_DEJA";
	public static final String MESSAGE_CONFIRMATION_SUPPRESSION_JOUEUR = "MESSAGE_CONFIRMATION_SUPPRESSION_JOUEUR";
	
	public static final Locale LOCALE = Locale.getDefault();
	
	public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle( "internationalisation.messages", Constantes.LOCALE );
	
	
	public static final String getString( final String cle ) {
		return Constantes.RESOURCE_BUNDLE.getString(cle);
	}
	
}
