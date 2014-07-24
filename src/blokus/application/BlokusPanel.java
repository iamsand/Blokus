package blokus.application;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import blokus.framework.Action;
import blokus.framework.Board;
import blokus.framework.Color;
import blokus.framework.Shape;
import blokus.framework.Piece;
import blokus.framework.Board.PlayerView;

public class BlokusPanel extends JPanel {
	
	private static final int CELL_SIZE = 24;
	private static final int  GAP_SIZE =  1;
	
	private boolean waitingForAction = false;
	private Color actionColor = Color.NULL;
	private Action action;
	
	private BlokusCell[][] grid = null;
	
	private final DefaultListModel<Shape> shapeListModel = new DefaultListModel<Shape>();
	private final JList<Shape> shapeList = new JList<Shape>(shapeListModel);
	private final JButton btnRotate = new JButton("CW90");
	private final JButton btnReflect = new JButton("Refl.");
	
	private final JPanel boardPanel = new JPanel();
	private final JPanel selectionPanel = new JPanel();
	
	private final LinkedList<BlokusCell> highlighted = new LinkedList<BlokusCell>();
	
	private int pieceRotation = 0;
	private boolean pieceReflected = false;
	
	public BlokusPanel() {
		this.setLayout(new BorderLayout());
		
		this.shapeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.btnRotate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BlokusPanel.this.pieceRotation = (BlokusPanel.this.pieceRotation + 1) % 4; 
			}
		});
		this.btnReflect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BlokusPanel.this.pieceReflected = !BlokusPanel.this.pieceReflected;
			}
		});
		
		this.selectionPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 1.0;
		
		this.selectionPanel.add(this.shapeList, c);

		c.gridy = 1;
		c.weighty = 0;
		this.selectionPanel.add(this.btnRotate, c);
		
		c.gridy = 2;
		this.selectionPanel.add(this.btnReflect, c);
		
		this.add(this.boardPanel, BorderLayout.CENTER);
		this.add(this.selectionPanel, BorderLayout.LINE_END);
	}
	
	public void updateBoard(final PlayerView view) {
		if (this.grid == null || this.grid.length != view.getHeight() || this.grid[0].length != view.getWidth()) {
			this.boardPanel.removeAll();
			this.boardPanel.setLayout(new GridLayout(view.getHeight(), view.getWidth(), BlokusPanel.GAP_SIZE, BlokusPanel.GAP_SIZE));
			this.grid = new BlokusCell[view.getHeight()][view.getWidth()];
			for (int i = 0; i < view.getHeight(); i++) {
				for (int j = 0; j < view.getWidth(); j++) {
					final BlokusCell cell = new BlokusCell(i, j);
					cell.setPreferredSize(new Dimension(BlokusPanel.CELL_SIZE, BlokusPanel.CELL_SIZE));
					cell.setOpaque(true);
					cell.addMouseListener(new MouseListener() {
						@Override
						public void mouseClicked(MouseEvent e) {
							if (BlokusPanel.this.waitingForAction) {
								BlokusPanel.this.waitingForAction = false;
								synchronized(BlokusPanel.this) {
									BlokusPanel.this.notify();
								}
							}
						}

						@Override
						public void mousePressed(MouseEvent e) {
						}

						@Override
						public void mouseReleased(MouseEvent e) {
							this.mouseExited(null);
						}

						@Override
						public void mouseEntered(MouseEvent e) {
							Shape selected = BlokusPanel.this.shapeList.getSelectedValue();
							if (selected != null) {
								Piece shape = Piece.createShape(selected);
								shape = shape.rotateCW(BlokusPanel.this.pieceRotation);
								if (BlokusPanel.this.pieceReflected) {
									shape = shape.reflectHorizontal();
								}
								
								int[][] coordinates = shape.getCoordinates();
								for (int[] coordinate : coordinates) {
									int r = cell.row + coordinate[1];
									int c = cell.column + coordinate[0];
									
									if (r >= 0 && r < view.getHeight() && c >= 0 && c < view.getWidth()) {
										BlokusCell highlightedCell = BlokusPanel.this.grid[r][c];
										BlokusPanel.this.highlighted.add(highlightedCell);
										highlightedCell.setBackground(highlightedCell.getBackground().brighter().brighter());
									}
								}
								
								BlokusPanel.this.action = new Action(shape, BlokusPanel.this.actionColor, cell.column, cell.row);
							}
						}

						@Override
						public void mouseExited(MouseEvent e) {
							while (!BlokusPanel.this.highlighted.isEmpty()) {
								BlokusCell highlightedCell = BlokusPanel.this.highlighted.pop();
								highlightedCell.setBackground(BlokusPanel.this.convertColor(view.getColor(highlightedCell.column, highlightedCell.row)).darker().darker());
							}
						}
					});
					this.grid[i][j] = cell;
					this.boardPanel.add(cell);
				}
			}
		}
		
		for (int i = 0; i < view.getHeight(); i++) {
			for (int j = 0; j < view.getWidth(); j++) {
				this.grid[i][j].setBackground(this.convertColor(view.getColor(j, i)).darker().darker());
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
	
	public Action getAction(Color color, List<Shape> hand) {
		this.waitingForAction = true;
		this.shapeListModel.clear();
		for (Shape piece : hand) {
			this.shapeListModel.addElement(piece);
		}
		this.shapeList.setSelectedIndex(0);
		this.actionColor = color;
		
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
	
	private static class BlokusCell extends JLabel {
		
		public final int row;
		public final int column;
		
		public BlokusCell(int row, int column) {
			super();
			
			this.row = row;
			this.column = column;
		}
		
	}
	
	public class HumanPlayer implements IPlayer {

		private Color color;
		
		@Override
		public void startGame(int boardWidth, int boardHeight, int numPlayers,
				Color color) {
			this.color = color;
		}

		@Override
		public Action getAction(PlayerView board, List<Shape> hand) {
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
