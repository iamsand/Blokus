package framework;

import java.util.Arrays;
import java.util.LinkedList;

public class Board {
    
    public static final int WIDTH_STANDARD = 20;
    public static final int HEIGHT_STANDARD = 20;
    
    private static final Color[] PLAY_SEQUENCE = {
        Color.BLUE, Color.YELLOW, Color.RED, Color.GREEN
    };

	// This will represent the visual board.
	private final Color[][] b;

	// This will keep track of the individual pieces played.
    private final LinkedList<Action> actions;
    
	public Board() {
		this.b = new Color[HEIGHT_STANDARD][WIDTH_STANDARD];
        for (Color[] row : this.b) {
            Arrays.fill(row, Color.NULL);
        }
        
        this.actions = new LinkedList();
	}
    
    private Color getColorToPlay() {
        return Board.PLAY_SEQUENCE[this.actions.size() % 4];
    }

	public boolean isActionLegal(Action action) {
        if (action.color != this.getColorToPlay()) {
            return false;
        }
        
        if (this.actions.size() < PLAY_SEQUENCE.length) {
            // TODO: verify that piece contacts the starting corner of the respective color on that player's first turn
            
        }
        
        // TODO: verify that piece will not overlap with existing pieces
        
        // TODO: verify that piece is not adjacent to pieces of the same color
        
        // TODO: verify that one corner of the piece touches the corner of a piece of the same color
        
		return true;
	}
	
    /**
     * Plays an action.
     * @param action The action to be played on the board.
     * @return <tt>true</tt> if the action was played successfully
     */
	public boolean doAction(Action action) {
		if (!isActionLegal(action)) {
            return false;
        }
        
        int[][] coordinates = action.shape.getCoordinates();
        for (int[] coordinate : coordinates) {
            this.b[action.y - coordinate[1]][action.x + coordinate[0]] = action.color;
        }
        
        this.actions.add(action);
        
		return true;
	}

	public void conPrint() {
        for (Color[] row : this.b) {
            for (Color c : row) {
				System.out.print("[" + c + "] ");
			}
            
			System.out.println();
		}
		System.out.println();
	}
    
    public String toConsoleMiniString() {
        StringBuilder sb = new StringBuilder();
        for (Color[] row : this.b) {
            for (Color c : row) {
                sb.append(c == Color.NULL ? '.' : c.name().charAt(0));
            }
            
            sb.append('\n');
        }
        
        return sb.toString();
    }
    
}
