package pl.trpaslik.gwtmaze.client;

/** 
 * Represents directions in which we can go 
 * 
 * @author trpaslik.pl@gmail.com 
 */
public enum Dir {
	N, E, S, W;

	/** return next direction (E for N, N for W ...) */
	public Dir next() {
		switch (this) {
		case N:
			return E;
		case E:
			return S;
		case S:
			return W;
		case W:
			return N;
		default:
			throw new IllegalStateException("impossible");
		}
	}
	
	/** returns the opposite direction */
	public Dir opposite() {
		switch (this) {
		case N:
			return S;
		case E:
			return W;
		case S:
			return N;
		case W:
			return E;
		default:
			throw new IllegalStateException("impossible");
		}
	}
}
