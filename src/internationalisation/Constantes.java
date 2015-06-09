package internationalisation;

import java.util.Locale;
import java.util.ResourceBundle;

public class Constantes {
	
	public static final String TITRE_FENETRE = "TITRE_FENETRE";
	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
	
	
	public static final Locale LOCALE = Locale.getDefault();
	
	public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle( "internationalisation.messages", Constantes.LOCALE );
	
	
	public static final String getString( final String cle ) {
		return Constantes.RESOURCE_BUNDLE.getString(cle);
	}
	
}
