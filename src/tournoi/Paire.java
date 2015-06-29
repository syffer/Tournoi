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
	
	public String toString() {
		return "( " + u + " ; " + v + " )";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((u == null) ? 0 : u.hashCode());
		result = prime * result + ((v == null) ? 0 : v.hashCode());
		return result;
	}

	@Override
	public boolean equals( Object obj ) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Paire<?, ?> other = (Paire<?, ?>) obj;
		if (u == null) {
			if (other.u != null)
				return false;
		} else if (!u.equals(other.u))
			return false;
		if (v == null) {
			if (other.v != null)
				return false;
		} else if (!v.equals(other.v))
			return false;
		return true;
	}
	
	
	
}
