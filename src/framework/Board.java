package framework;

import java.util.Arrays;

public class Board {

	public static final int				WIDTH_STANDARD	= 20;
	public static final int				HEIGHT_STANDARD	= 20;

	private final Color[][] b;
    
	public Board() {
		this.b = new Color[HEIGHT_STANDARD][WIDTH_STANDARD];
		for (Color[] row : this.b) {
			Arrays.fill(row, Color.NULL);
		}
	}
    
    public boolean isFirstActionValid(Action action) {
        // verify that the starting piece touches the correct corner.
        int[][] coordinates = action.shape.getCoordinates();
        int[] corner = this.startingCoordinate(action.color);
        for (int[] coordinate : coordinates) {
            int newX = action.x + coordinate[0];
            int newY = action.y + coordinate[1];
            if (newX == corner[0] && newY == corner[1]) {
                return isActionValid(action);
            }
        }
        
        return false;
    }
    
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
		int[][] coordinates = action.shape.getCoordinates();

		// verify the piece doesn't go off the board.
		for (int i = 0; i < coordinates.length; i++) {
			int newX = action.x + coordinates[i][0];
			int newY = action.y + coordinates[i][1];
			if (!isOnBoard(newX, newY))
				return false;
		}
        
        boolean cornerConnected = false;
        
        for (int[] coordinate : coordinates) {
            int newX = action.x + coordinate[0];
            int newY = action.y + coordinate[1];
            
            // verify that the piece does not overlap an existing piece
            if (this.b[newX][newY] != Color.NULL)
                return false;
            
            // verify that piece is not adjacent to pieces of the same color
            for (int[] offset : Board.EDGE_OFFSET) {
                int edgeX = newX + offset[0];
                int edgeY = newY + offset[1];
                
                if (isOnBoard(edgeX, edgeY) && this.b[this.b.length - 1 - edgeY][edgeX] == action.color)
                    return false;
            }
            
            // verify that one corner of the piece touches the corner of a piece of the same color
            if (!cornerConnected) {
                for (int i = 0; i < Board.CORNER_OFFSET.length && !cornerConnected; i++) {
                    int[] offset = Board.CORNER_OFFSET[i];
                    
                    int cornerX = newX + offset[0];
                    int cornerY = newY + offset[1];
                    
                    if (isOnBoard(cornerX, cornerY) && this.b[this.b.length - 1 - cornerY][cornerX] == action.color)
                        cornerConnected = true;
                }
            }
        }

        return cornerConnected;
	}
    
    /**
     * 
     * @param x
     * @param y
     * @return <tt>true</tt> if the specified (x, y) pair is a valid coordinate pair for this board
     */
    private boolean isOnBoard(int x, int y) {
        return x < this.b[0].length && x >= 0 && y < this.b.length && y >= 0;
    }
	
    // The starting corners for each color.
    private int[] startingCoordinate(Color color) {
        switch (color) {
            case BLUE:
                return new int[] { 0, 0 };
            case YELLOW:
                return new int[] { this.b[0].length - 1, 0 };
            case RED:
                return new int[] { this.b[0].length - 1, this.b.length - 1 };
            case GREEN:
                return new int[] { 0, this.b.length - 1 };
            default:
                throw new IllegalArgumentException("color cannot be null");
        }
    }

	/**
	 * Plays an action.
	 * 
	 * @param action The action to be played on the board.
	 * @return <tt>true</tt> if the action was played successfully
	 */
	public boolean doAction(Action action) {
		// TODO: uncomment this once done.
//		if (!isActionLegal(action)) {
//			return false;
//		}

        int[][] coordinates = action.shape.getCoordinates();
        for (int[] coordinate : coordinates) {
            this.b[this.b.length - 1 - action.y + coordinate[1]][action.x + coordinate[0]] = action.color;
        }

		return true;
	}


	public String toConsoleMiniString() {
		StringBuilder sb = new StringBuilder();
		for (Color[] row : this.b) {
			for (Color c : row) {
				sb.append("[").append(c == Color.NULL ? ' ' : c.name().charAt(0)).append("]");
			}

			sb.append('\n');
		}

		return sb.toString();
	}
    
}
