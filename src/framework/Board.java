package framework;

import java.util.Arrays;

public class Board {

	private static final int WIDTH_STANDARD = 20;
	private static final int HEIGHT_STANDARD = 20;

	private final Color[][] b;
    private final int width;
    private final int height;
    
	public Board() {
        this.width = Board.WIDTH_STANDARD;
        this.height = Board.HEIGHT_STANDARD;
        
		this.b = new Color[HEIGHT_STANDARD][WIDTH_STANDARD];
		for (Color[] row : this.b) {
			Arrays.fill(row, Color.NULL);
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
		// System.out.println("testing action: " + action);
		
		// System.out.println(1); // DEBUG ST
		int[][] coordinates = action.shape.getCoordinates();

		// verify the piece doesn't go off the board.
		for (int i = 0; i < coordinates.length; i++) {
			int newX = action.x + coordinates[i][0];
			int newY = action.y + coordinates[i][1];
			if (!isOnBoard(newX, newY))
				return false;
		}
		// System.out.println(2); // DEBUG ST

		// First action test
		int[] myCorner = this.startingCoordinate(action.color);
		// System.out.println("Checking corner for null " + myCorner[0] + " " + myCorner[1]); // DEBUG ST
		if (this.b[this.height - myCorner[1] - 1][myCorner[0]] == Color.NULL){
			// System.out.println("Corner is null. This is first action"); // DEBUG ST
			int[] corner = this.startingCoordinate(action.color);
	        for (int[] coordinate : coordinates) {
	            int newX = action.x + coordinate[0];
	            int newY = action.y + coordinate[1];
	            if (newX == corner[0] && newY == corner[1]) {
	            	// System.out.println("Placement of piece will touch correct corner"); // DEBUG ST
	                return true;
	            }
	        }
	        // System.out.println("Placement of piece will NOT touch correct corner"); // DEBUG ST
	        return false;
		}
        
		// System.out.println(3); // DEBUG ST
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
                
                if (isOnBoard(edgeX, edgeY) && this.b[this.height - 1 - edgeY][edgeX] == action.color)
                    return false;
            }
            
            // verify that one corner of the piece touches the corner of a piece of the same color
            if (!cornerConnected) {
                for (int i = 0; i < Board.CORNER_OFFSET.length && !cornerConnected; i++) {
                    int[] offset = Board.CORNER_OFFSET[i];
                    
                    int cornerX = newX + offset[0];
                    int cornerY = newY + offset[1];
                    
                    if (isOnBoard(cornerX, cornerY) && this.b[this.height - 1 - cornerY][cornerX] == action.color)
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
        return x < this.width && x >= 0 && y < this.height && y >= 0;
    }
	
    
    // The starting corners for each color.
    private int[] startingCoordinate(Color color) {
        switch (color) {
            case BLUE:
                return new int[] { 0, 0 };
            case YELLOW:
                return new int[] { 0, this.height - 1 };
            case RED:
                return new int[] { this.width - 1, this.height - 1 };
            case GREEN:
                return new int[] { this.width - 1, 0};
            default:
                throw new IllegalArgumentException("color cannot be null");
        }
    }

	/**
	 * Plays an action.
	 * 
	 * @param action The action to be played on the board. The action must be valid.
	 * @return <tt>true</tt> if the action was played successfully
	 */
	public void doAction(Action action) {

        int[][] coordinates = action.shape.getCoordinates();
        for (int[] coordinate : coordinates) {
            this.b[this.height - 1 - action.y + coordinate[1]][action.x + coordinate[0]] = action.color;
        }
	}

	public String toConsoleString() {
		StringBuilder sb = new StringBuilder();
		for (Color[] row : this.b) {
			for (Color c : row) {
				sb.append("[").append(c == Color.NULL ? ' ' : c.name().charAt(0)).append("]");
			}

			sb.append('\n');
		}

		return sb.toString();
	}
    
    public class PlayerView {
        
        public Color getColor(int x, int y) {
            return Board.this.b[Board.this.height - 1 - y][x];
        }
        
        public Color[][] getFullBoard() {
            Color[][] r = new Color[Board.this.height][];
            
            for (int i = 0; i < r.length; i++) {
                r[i] = Arrays.copyOf(Board.this.b[i], Board.this.width);
            }
            
            return r;
        }
        
    }
    
}
