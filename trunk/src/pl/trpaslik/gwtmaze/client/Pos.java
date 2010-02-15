package pl.trpaslik.gwtmaze.client;

/**
 * Represents immutable position.
 * Use Pos.get() instead of constructor.
 *
 * @author trpaslik.pl@gmail.com
 */
class Pos {
	final int x;
	final int y;

	static Pos get(int x, int y) {
		return new Pos(x, y);
	}

	private Pos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/** return new position, when you go to given direction */
	Pos go(Dir dir) {
		switch (dir) {
		case N:
			return Pos.get(x, y - 1);
		case E:
			return Pos.get(x + 1, y);
		case S:
			return Pos.get(x, y + 1);
		case W:
			return Pos.get(x - 1, y);
		default:
			return this;
		}
	}

	@Override
	public String toString() {
		return "[" + x + "," + y + "]";
	}
}
