package pl.trpaslik.gwtmaze.client;

/** 
 * Simple maze model which cells (rooms) are organized as a table
 * 
 * @author trpaslik.pl@gmail.com
 */
public final class TableMazeModel implements MazeModel {

	private final Cell[] cells;
	private final int rows;
	private final int columns;

	public TableMazeModel(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		cells = new Cell[rows * columns];
		clear();
	}

	/** set all cell to closed */
	public void clear() {
		for (int i = 0; i < cells.length; i++) {
			cells[i] = new Cell();
		}
	}

	private final int rowColumn2Index(int row, int column) {
		return (row * columns) + column;
	}

	@Override
	public final Cell get(Pos pos) {
		return cells[rowColumn2Index(pos.y, pos.x)];
	}

	@Override
	public final void set(Pos pos, Cell cell) {
		cells[rowColumn2Index(pos.y, pos.x)] = cell;
	}

	@Override
	public int getRows() {
		return rows;
	}

	@Override
	public int getColumns() {
		return columns;
	}

	@Override
	public boolean isValid(Pos pos) {
		return (pos.x >= 0 && pos.y >= 0 && pos.x < columns && pos.y < rows);
	}

	@Override
	public boolean isValid(Pos pos, Dir dir) {
		if (pos.x <= 0 && dir == Dir.W) {
			return false;
		} else if ((pos.x >= columns - 1) && dir == Dir.E) {
			return false;
		}
		if (pos.y <= 0 && dir == Dir.N) {
			return false;
		} else if ((pos.y >= rows - 1) && dir == Dir.S) {
			return false;
		}
		return true;
	}

}
