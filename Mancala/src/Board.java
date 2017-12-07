
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import javax.swing.JComponent;

/**
 * Represents the board for the Mancala game.
 * 
 * @author Group 2
 *
 */
@SuppressWarnings("serial")
public class Board extends JComponent {

	private int[][] pits;
	private int[] mancalaPits;
	private MyLayout layout;

	/**
	 * Constructor for the board
	 * @param layout
	 */
	public Board(MyLayout layout) {
		this.layout = layout;
	}

	/**
	 * Gets the two Mancala pits.
	 * @return
	 */
	public Rectangle2D.Double[][] getPitRectangles() {
		return layout.getPitRects();
	}

	/**
	 * Sets the size of the board.
	 * 
	 * @param width		the height to be set
	 * @param height	the width to be set
	 */
	public void setBoardSize(int width, int height) {
		setPreferredSize(new Dimension(width, height));
		layout.setSize(width, height);
	}

	/**
	 * Sets the pit data of the board.
	 * @param pits			the pit data to be set
	 * @param manacalaPits	the Mancala pit data to be set
	 */
	public void setData(int[][] pits, int[] mancalaPits) {
		this.pits = pits;
		this.mancalaPits = mancalaPits;
	}

	/**
	 * Draws the board and layout.
	 */
	public void paintComponent(Graphics g) {
		layout.redraw(g, this, pits, mancalaPits);
	}
}
