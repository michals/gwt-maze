package pl.trpaslik.gwtmaze.client;

import java.util.EnumSet;
import java.util.LinkedHashSet;

import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;

/**
 * Maze Widget class
 * 
 * @author trpaslik.pl@gmail.com
 */
public class TableMazeWidget extends Composite {

	/**
	 * CSS class name
	 */
	private static final String MAZE_WIDGET_STYLE = "table-maze-widget";

	/**
	 * rows in this maze
	 */
	private final int rows;

	/**
	 * columns in this maze
	 */
	private final int columns;

	/**
	 * internal Grid widget
	 */
	private Grid grid;

	private MazeModel model;

	/** for measuring genPath() time */
	// private long genPathTime;

	/** for measuring findNextPathStart() time */
	// private long findNextPathStartTime;

	/** set of points where from we can start a new path */
	private LinkedHashSet<Pos> startPoints = new LinkedHashSet<Pos>();

	/**
	 * Main constructor for MazeWidget
	 * 
	 * @param rows
	 *            number of rows in this maze
	 * @param columns
	 *            number of columns in this maze
	 */
	public TableMazeWidget(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		grid = new Grid(rows, columns);
		initWidget(grid);
		setStyleName(MAZE_WIDGET_STYLE);
		setModel(new TableMazeModel(rows, columns));
		long t0 = System.currentTimeMillis();
		generate();
		long t1 = System.currentTimeMillis();
		draw();
		long t2 = System.currentTimeMillis();
		debug("gennerate time: " + (t1 - t0) + ", draw time: " + (t2 - t1));
	}

	private void debug(String str) {
		// System.out.println(str);
		// Window.alert(str);
		RootPanel root = RootPanel.get("debug");
		if (root != null) {
			root.getElement().setInnerText(str);
		}
	}

	/** generate a random maze */
	private void generate() {
		Pos start = Pos.get(0, 0);
		// genPathTime = 0;
		// findNextPathStartTime = 0;
		while (start != null) {
			// long t0 = System.currentTimeMillis();
			genPath(start);
			// long t1 = System.currentTimeMillis();
			start = findNextPathStart();
			// long t2 = System.currentTimeMillis();
			// genPathTime += (t1 - t0);
			// findNextPathStartTime += (t2 - t1);
		}
		// open entrance
		model.get(Pos.get(0, 0)).open(Dir.N);
		// open exit
		model.get(Pos.get(columns - 1, rows - 1)).open(Dir.S);
	}

	/** set model representing the maze structure */
	public void setModel(MazeModel model) {
		this.model = model;
	}

	/** Draw the maze by applying a proper style for each cell */
	private void draw() {
		CellFormatter cf = grid.getCellFormatter();
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < columns; col++) {
				String style = model.get(Pos.get(col, row)).getStyleName();
				cf.getElement(row, col).setClassName(style);
			}
		}
	}

	/**
	 * Generate a single random path.
	 * 
	 * @param start
	 *            starting position
	 * @return end position
	 */
	private Pos genPath(Pos start) {
		Pos curPos = start;
		// set of directions where we can dig (open door)
		EnumSet<Dir> possible;
		while (!(possible = canBeOpened(curPos)).isEmpty()) {
			// get random direction from set of possible
			Dir[] possibleArr = possible.toArray(new Dir[possible.size()]);
			Dir dir = possibleArr[Random.nextInt(possibleArr.length)];
			if (possibleArr.length > 1) {
				// if there is a more options, remember the starting point
				startPoints.add(curPos);
			}
			// open the door in current cell
			model.get(curPos).open(dir);
			// go to new cell
			curPos = curPos.go(dir);
			// open the door in the new cell
			model.get(curPos).open(dir.opposite());
		}
		return curPos;
	}

	/** set of doors which can be opened */
	private EnumSet<Dir> canBeOpened(Pos pos) {
		EnumSet<Dir> ret = EnumSet.allOf(Dir.class);
		// consider only closed doors
		ret.removeAll(model.get(pos).opened());
		for (Dir dir : ret) {
			if ((!model.isValid(pos, dir)) || model.get(pos.go(dir)).visited()) {
				// remove if this direction would take us out of the model
				// or if we ware there already
				ret.remove(dir);
			}
		}
		return ret;
	}

	/** Return next starting point for genPath() */
	private Pos findNextPathStart() {
		if (startPoints.isEmpty()) {
			return null;
		}
		Pos ret = startPoints.iterator().next();
		startPoints.remove(ret);
		return ret;
	}
}
