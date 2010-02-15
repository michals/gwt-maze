package pl.trpaslik.gwtmaze.client;

import java.util.EnumSet;

/**
 * Represents the cell in the maze
 * 
 * @author trpaslik.pl@gmail.com
 */
public class Cell {

	/**
	 * EnumSet of Direction representing opened doors
	 */
	private EnumSet<Dir> opened = EnumSet.noneOf(Dir.class);

	/** open the door for a given direction */
	public void open(Dir dir) {
		opened.add(dir);
	}

	/** close the door for a given direction */
	public void close(Dir dir) {
		opened.remove(dir);
	}

	/** close all doors in this cell */
	public void close() {
		opened.clear();
	}

	/** is the door open for a given direction */
	public final boolean isOpened(Dir dir) {
		return opened.contains(dir);
	}

	/** is the door close for a given direction */
	public final boolean isClosed(Dir dir) {
		return !opened.contains(dir);
	}

	@Override
	public String toString() {
		return opened.toString();
	}

	/** returns closed doors in this cell */
	public EnumSet<Dir> closed() {
		return EnumSet.complementOf(opened);
	}

	/** returns opened doors in this cell */
	public EnumSet<Dir> opened() {
		return opened.clone();
	}

	/** was this cell visited */
	public boolean visited() {
		return !opened.isEmpty();
	}

	/** calculates CSS style name to use for this cell */
	public String getStyleName() {
		String ret = "c";
		if (!isOpened(Dir.N))
			ret += "N";
		if (!isOpened(Dir.E))
			ret += "E";
		if (!isOpened(Dir.S))
			ret += "S";
		if (!isOpened(Dir.W))
			ret += "W";
		return ret;
	}

}
