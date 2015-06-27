package tournoi;

public class JoueurDejaExistantException extends Exception {
	
	private static final long serialVersionUID = 58628906971386718L;

	public JoueurDejaExistantException() {
		super("Un joueur ayant ce nom existe déjà.");
	}
	

}
