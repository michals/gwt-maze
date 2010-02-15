package pl.trpaslik.gwtmaze.client;

/**
 * @author trpaslik.pl@gmail.com
 */
public interface MazeModel {
	/** get cell for given coordinates */
	Cell get(Pos pos);

	/** set cell for given coordinates */	
	void set(Pos pos, Cell cell);

	/** get number of rows */
	int getRows();

	/** get number of columns */
	int getColumns();
	
	/** is given position valid? (in model, in bounds) */
	boolean isValid(Pos pos);

	/** will be position valid if I go from pos in dir */
	boolean isValid(Pos pos, Dir dir);
}
