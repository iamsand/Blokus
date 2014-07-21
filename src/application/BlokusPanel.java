package application;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import framework.Action;
import framework.Board;
import framework.Board.PlayerView;
import framework.Color;
import framework.PieceName;

public class BlokusPanel extends JPanel {
	
	private boolean waitingForAction = false;
	private Action action;
	
	private JLabel[][] grid = null;
	
	public void updateBoard(PlayerView view) {
		if (this.grid == null || this.grid.length != view.getHeight() || this.grid[0].length != view.getWidth()) {
			this.removeAll();
			this.setLayout(new GridLayout(view.getHeight(), view.getWidth()));
			this.grid = new JLabel[view.getHeight()][view.getWidth()];
			for (int i = 0; i < view.getHeight(); i++) {
				for (int j = 0; j < view.getWidth(); j++) {
					JLabel label = new JLabel();
					label.setPreferredSize(new Dimension(20, 20));
					label.setOpaque(true);
					label.setBorder(BorderFactory.createLineBorder(java.awt.Color.darkGray));
					label.addMouseListener(new MouseListener() {
						@Override
						public void mouseClicked(MouseEvent e) {
						}

						@Override
						public void mousePressed(MouseEvent e) {
						}

						@Override
						public void mouseReleased(MouseEvent e) {
						}

						@Override
						public void mouseEntered(MouseEvent e) {
							label.setBackground(label.getBackground().brighter().brighter());
						}

						@Override
						public void mouseExited(MouseEvent e) {
							label.setBackground(label.getBackground().darker().darker());
						}
					});
					this.grid[i][j] = label;
					this.add(label);
				}
			}
		}
		
		for (int i = 0; i < view.getHeight(); i++) {
			for (int j = 0; j < view.getWidth(); j++) {
				this.grid[i][j].setBackground(BlokusPanel.convertColor(view.getColor(j, view.getHeight() - 1 - i)).darker().darker());
			}
		}
	}
	
	private static java.awt.Color convertColor(Color color) {
		switch (color) {
			case BLUE: return java.awt.Color.blue;
			case YELLOW: return java.awt.Color.yellow;
			case RED: return java.awt.Color.red;
			case GREEN: return java.awt.Color.green;
			default: return java.awt.Color.darkGray;
		}
	}
	
	public Action getAction(Color color, List<PieceName> hand) {
		this.waitingForAction = true;
		
		synchronized (this) {
			while (waitingForAction) {
				try {
					this.wait();
				} catch (InterruptedException ex) {
				}
			}
		}
		
		return this.action;
	}

	public class HumanPlayer implements IPlayer {

		private Color color;
		
		@Override
		public void startGame(int boardWidth, int boardHeight, int numPlayers,
				Color color) {
			this.color = color;
		}

		@Override
		public Action getAction(PlayerView board, List<PieceName> hand) {
			BlokusPanel.this.updateBoard(board);
			return BlokusPanel.this.getAction(this.color, hand);
		}
		
	}
	
	public static void main(String[] args) {
		testVerticalStripe();
		testHorizontalStripe();
	}
	
	private static void testVerticalStripe() {
		BlokusPanel panel = new BlokusPanel();
		Board board = new Board();
		PlayerView view = board.new PlayerView() {
			@Override
			public Color getColor(int x, int y) {
				switch (x % 4) {
					case 0: return Color.BLUE;
					case 1: return Color.YELLOW;
					case 2: return Color.RED;
					case 3: return Color.GREEN;
				}
					
				return super.getColor(x, y);
			}
		};
		panel.updateBoard(view);
		
		JFrame frame = new JFrame("Blokus - VSTRIPE");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setContentPane(panel);
		frame.pack();
		frame.setVisible(true);
	}
	
	private static void testHorizontalStripe() {
		BlokusPanel panel = new BlokusPanel();
		Board board = new Board();
		PlayerView view = board.new PlayerView() {
			@Override
			public Color getColor(int x, int y) {
				switch (y % 4) {
					case 0: return Color.BLUE;
					case 1: return Color.YELLOW;
					case 2: return Color.RED;
					case 3: return Color.GREEN;
				}
					
				return super.getColor(x, y);
			}
		};
		panel.updateBoard(view);
		
		JFrame frame = new JFrame("Blokus - HSTRIPE");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setContentPane(panel);
		frame.pack();
		frame.setVisible(true);
	}
	
}
