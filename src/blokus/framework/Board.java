package blokus.framework;

import java.util.Arrays;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Board {
	
	public static final Logger LOGGER = Logger.getLogger(Board.class.getName());
	{
		Board.LOGGER.setUseParentHandlers(false);
		
		ConsoleHandler handler = new ConsoleHandler();
		Board.LOGGER.addHandler(handler);

		handler.setLevel(Level.ALL);
		Board.LOGGER.setLevel(Level.ALL);
	}

	private static final int  WIDTH_STANDARD = 20;
	private static final int HEIGHT_STANDARD = 20;

	private final BlokusColor[][] b;
    private final int width;
    private final int height;
    
	public Board() {
        this.width = Board.WIDTH_STANDARD;
        this.height = Board.HEIGHT_STANDARD;
        
		this.b = new BlokusColor[this.height][this.width];
		for (BlokusColor[] row : this.b) {
			Arrays.fill(row, BlokusColor.NULL);
		}
	}
    
    public int getWidth() { return this.width; }
    public int getHeight() { return this.height; }
    
    private static final int[][] CORNER_OFFSET = {
        { -1,  1 },
        {  1,  1 },
        {  1, -1 },
        { -1, -1 }
    };
    
    private static final int[][] EDGE_OFFSET = {
        { -1,  0 },
        {  0,  1 },
        {  1,  0 },
        {  0, -1 }
    };

	public boolean isActionValid(Action action) {
		Board.LOGGER.fine("Testing action: " + action);
		
		int[][] coordinates = action.piece.getCoordinates();
        for (int[] coordinate : coordinates) {
            int newX = action.x + coordinate[0];
            int newY = action.y + coordinate[1];
			if (!isOnBoard(newX, newY)) {
				Board.LOGGER.info("Invalid action: oversteps board");
				return false;
			}
        }

		// First action test
		int[] myCorner = this.startingCoordinate(action.color);
		if (this.b[myCorner[1]][myCorner[0]] == BlokusColor.NULL) {
			Board.LOGGER.fine(action.color.name() + " corner is null, first action");
			int[] corner = this.startingCoordinate(action.color);
	        for (int[] coordinate : coordinates) {
	            int newX = action.x + coordinate[0];
	            int newY = action.y + coordinate[1];
	            if (newX == corner[0] && newY == corner[1]) {
					Board.LOGGER.info("Valid action: first " + action.color.name() + " piece touches correct corner");
	                return true;
	            }
	        }
			Board.LOGGER.info("Invalid action: first " + action.color.name() + " piece does not touch correct corner");
	        return false;
		} else {
			Board.LOGGER.fine(action.color.name() + " corner is not null");
		}

        boolean cornerConnected = false;
        
        for (int[] coordinate : coordinates) {
            int newX = action.x + coordinate[0];
            int newY = action.y + coordinate[1];
            
            // verify that the piece does not overlap an existing piece
			if (this.b[newY][newX] != BlokusColor.NULL) {
				Board.LOGGER.info("Invalid action: overlaps existing piece");
                return false;
			}
            
            // verify that piece is not adjacent to pieces of the same color
            for (int[] offset : Board.EDGE_OFFSET) {
                int edgeX = newX + offset[0];
                int edgeY = newY + offset[1];
                
				if (this.isOnBoard(edgeX, edgeY) && this.b[edgeY][edgeX] == action.color) {
					Board.LOGGER.info("Invalid action: adjacent to friendly piece");
                    return false;
				}
            }
            
            // verify that one corner of the piece touches the corner of a piece of the same color
            if (!cornerConnected) {
                for (int i = 0; i < Board.CORNER_OFFSET.length && !cornerConnected; i++) {
                    int[] offset = Board.CORNER_OFFSET[i];
                    
                    int cornerX = newX + offset[0];
                    int cornerY = newY + offset[1];
                    
                    if (isOnBoard(cornerX, cornerY) && this.b[cornerY][cornerX] == action.color)
                        cornerConnected = true;
                }
            }
        }
		
		if (!cornerConnected) {
			Board.LOGGER.info("Invalid action: does not connect to friendly corner");
			return false;
		}
		
		return true;
	}
    
    /**
     * 
     * @param x
     * @param y
     * @return <tt>true</tt> if the specified (x, y) pair is a valid coordinate pair for this board
     */
    private boolean isOnBoard(int x, int y) {
        return x < this.width && x >= 0 && y < this.height && y >= 0;
    }
	
    
    // The starting corners for each color.
    private int[] startingCoordinate(BlokusColor color) {
        switch (color) {
            case BLUE:
                return new int[] { 0, this.height - 1 };
            case YELLOW:
                return new int[] { 0, 0 };
            case RED:
                return new int[] { this.width - 1, 0};
            case GREEN:
                return new int[] { this.width - 1, this.height - 1 };
            default:
                throw new IllegalArgumentException("color cannot be null");
        }
    }

	/**
	 * Plays an action.
	 * 
	 * @param action The action to be played on the board. The action must be valid.
	 */
	public void doAction(Action action) {
        int[][] coordinates = action.piece.getCoordinates();
        for (int[] coordinate : coordinates) {
            this.b[action.y + coordinate[1]][action.x + coordinate[0]] = action.color;
        }
	}

	public String toConsoleString() {
		StringBuilder sb = new StringBuilder();
		for (BlokusColor[] row : this.b) {
			for (BlokusColor c : row) {
				sb.append("[").append(c == BlokusColor.NULL ? ' ' : c.name().charAt(0)).append("]");
			}

			sb.append('\n');
		}

		return sb.toString();
	}
    
    public class PlayerView {
        
        public BlokusColor getColor(int x, int y) {
            return Board.this.b[y][x];
        }
        
        public BlokusColor[][] getFullBoard() {
            BlokusColor[][] r = new BlokusColor[Board.this.height][];
            
            for (int i = 0; i < r.length; i++) {
                r[i] = Arrays.copyOf(Board.this.b[i], Board.this.width);
            }
            
            return r;
        }
        
        public int getWidth() {
        	return Board.this.getWidth();
        }
        
        public int getHeight() {
        	return Board.this.getHeight();
        }
        
    }
    
}
