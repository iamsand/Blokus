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

	public boolean isActionValid(Action action) {
		int[][] coordinates = action.shape.getCoordinates();

		// verify the piece doesn't go off the board.
		for (int i = 0; i < coordinates.length; i++) {
			int newX = action.x + coordinates[i][0];
			int newY = action.y + coordinates[i][1];
			if (newX >= 20 || newX < 0 || newY >= 20 || newY < 0)
				return false;
		}
        
        for (int[] coordinate : coordinates) {
            int newX = action.x + coordinate[0];
            int newY = action.y + coordinate[1];
            if (b[newX][newY] != Color.NULL)
                return false;
        }

		// TODO: verify that piece is not adjacent to pieces of the same color

		// TODO: verify that one corner of the piece touches the corner of a piece of the same color

		return true;
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
