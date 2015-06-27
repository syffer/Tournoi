package tournoi;

import java.io.Serializable;

public class Paire<U, V> implements Serializable {
	
	private static final long serialVersionUID = -3184589629775586892L;
	
	public U u;
	public V v;
	
	public Paire( U u, V v ) {
		this.u = u;
		this.v = v;
	}
	
}
