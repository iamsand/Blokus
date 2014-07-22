package application;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import framework.Action;
import framework.Board;
import framework.Board.PlayerView;
import framework.Color;
import framework.PieceName;

public class BlokusPanel extends JPanel {
	
	private static final int CELL_SIZE = 20;
	private static final int  GAP_SIZE =  1;
	
	private boolean waitingForAction = false;
	private Action action;
	
	private JLabel[][] grid = null;
	
	private final DefaultListModel<PieceName> shapeListModel = new DefaultListModel<PieceName>();
	private final JList<PieceName> shapeList = new JList<PieceName>(shapeListModel);
	private final JButton btnRotate = new JButton("CW90");
	
	private final JPanel boardPanel = new JPanel();
	private final JPanel selectionPanel = new JPanel();
	
	public BlokusPanel() {
		this.setLayout(new BorderLayout());
		
		this.selectionPanel.setLayout(new BorderLayout());
		
		this.selectionPanel.add(this.shapeList, BorderLayout.CENTER);
		this.selectionPanel.add(this.btnRotate, BorderLayout.PAGE_END);
		
		this.add(this.boardPanel, BorderLayout.CENTER);
		this.add(this.selectionPanel, BorderLayout.LINE_END);
	}
	
	public void updateBoard(PlayerView view) {
		if (this.grid == null || this.grid.length != view.getHeight() || this.grid[0].length != view.getWidth()) {
			this.boardPanel.removeAll();
			this.boardPanel.setLayout(new GridLayout(view.getHeight(), view.getWidth(), BlokusPanel.GAP_SIZE, BlokusPanel.GAP_SIZE));
			this.grid = new JLabel[view.getHeight()][view.getWidth()];
			for (int i = 0; i < view.getHeight(); i++) {
				for (int j = 0; j < view.getWidth(); j++) {
					JLabel label = new JLabel();
					label.setPreferredSize(new Dimension(BlokusPanel.CELL_SIZE, BlokusPanel.CELL_SIZE));
					label.setOpaque(true);
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
					this.boardPanel.add(label);
				}
			}
		}
		
		for (int i = 0; i < view.getHeight(); i++) {
			for (int j = 0; j < view.getWidth(); j++) {
				this.grid[i][j].setBackground(this.convertColor(view.getColor(j, view.getHeight() - 1 - i)).darker().darker());
			}
		}
	}
	
	private java.awt.Color convertColor(Color color) {
		switch (color) {
			case BLUE: return java.awt.Color.blue;
			case YELLOW: return java.awt.Color.yellow;
			case RED: return java.awt.Color.red;
			case GREEN: return java.awt.Color.green;
			default: return this.getBackground();
		}
	}
	
	public Action getAction(Color color, List<PieceName> hand) {
		this.waitingForAction = true;
		this.shapeListModel.clear();
		for (PieceName piece : hand) {
			this.shapeListModel.addElement(piece);
		}
		
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
