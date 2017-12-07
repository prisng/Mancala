
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 
 * *** View / Controller ***
 * Controller takes user input (e.g. clicking the pits) and updates the Mancala
 * model. View outputs the Mancala model with changes made accordingly.
 * 
 * @author Group 2
 *
 */
@SuppressWarnings("serial")
public class MancalaView extends JFrame implements ChangeListener, MouseListener {

	private final int WIDTH = 500;
	private final int HEIGHT = 350;
	private MancalaModel mancala;
	private Board board;
	private JButton undoButton;
	private JLabel turns;

	/**
	 * Constructs a MancalaView that creates an initial dialog that allows players
	 * to choose the layout and number of stones.
	 */
	public MancalaView() {
		// Create an opening dialog where players can set up the game
		InitialDialog initialDialog = new InitialDialog(this);
		initialDialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		initialDialog.setVisible(true);
		createGame(initialDialog.getStones(), initialDialog.getMyLayout());
		this.setTitle("Mancala");	// Set frame header as "Mancala"
	}

	/**
	 * The frame of the game is created here. The board layout is added, along
	 * with the undo button, and its data.
	 * 
	 * @param stones
	 */
	private void createGame(int stones, MyLayout layout) {
		mancala = new MancalaModel(stones);	// Create a Mancala model with intial stones
		mancala.attach(this);	// Attach this change listener to the Mancala model

		// Create the Mancala board
		board = new Board(layout);
		board.setBoardSize(WIDTH, HEIGHT);
		board.addMouseListener(this);
		board.setData(mancala.getPits(), mancala.getMancalas());

		// Create the undo button
		undoButton = new JButton("Undo: " + mancala.getUndoCount());
		undoButton.setBorder(BorderFactory.createRaisedBevelBorder());
		undoButton.setPreferredSize(new Dimension(100, 50));
		undoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mancala.undo();
				turns.setText(mancala.displayCurrentPlayer());
				undoButton.setText("Undo: " + mancala.getUndoCount());
			}
		});
		undoButton.addMouseListener(this);
		
		// Display whose turn it is currently
		turns = new JLabel(mancala.displayCurrentPlayer());
		turns.setFont(new Font("Monospaced", Font.BOLD, 20));
		turns.setPreferredSize(new Dimension(HEIGHT, 20));

		// Set the frame components and layout
		this.setLayout(new FlowLayout());
		this.add(board);
		this.add(turns);
		this.add(undoButton);
		this.setSize(new Dimension(700, 450));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
	}

	/**
	 * (Method overridden from implementing ChangeListener)
	 * Updates the Mancala View when information from the Mancala Model gets changed.
	 */
	public void stateChanged(ChangeEvent e) {
		// Change the board view based on the Mancala model changes
		board.setData(mancala.getPits(), mancala.getMancalas());
		board.repaint();
		if (mancala.isGameOver()) {
			if (mancala.getCurrentPlayer() < 0) {
				JOptionPane.showMessageDialog(this, "Draw", "Game Over", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(this, "Player " + (mancala.getCurrentPlayer() + 1) + " wins!",
						"Game Over", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	/**
	 * Update pit data in model if pit is clicked on.
	 * @param event	the event from pressing the mouse
	 */
	@Override
	public void mousePressed(MouseEvent event) {
		if (mancala.isGameOver()) {
			return;
		}
		Rectangle2D.Double[][] rects = board.getPitRectangles();

		// Update pit data based on which pit is clicked
		for (int row = 0; row < rects.length; row++) {
			for (int col = 0; col < rects[row].length; col++) {
				if (rects[row][col].contains(event.getPoint())) {
					mancala.move(row, col);
					turns.setText(mancala.displayCurrentPlayer());
					undoButton.setText("Undo: " + mancala.getUndoCount());
				}	
			}
		}
	}

	/**
	 * Overridden method stub for MouseListener interface.
	 */
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	/**
	 * Overridden method stub for MouseListener interface.
	 */
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub	
	}
	
	/**
	 * Overridden method stub for MouseListener interface.
	 */
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	/**
	 * Overridden method stub for MouseListener interface.
	 */
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}


}
