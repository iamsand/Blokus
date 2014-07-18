package framework;

import java.util.Arrays;
import java.util.LinkedList;

public class Board {
    
    public static final int WIDTH_STANDARD = 20;
    public static final int HEIGHT_STANDARD = 20;

	// This will represent the visual board.
	private Color[][] b;

	// This will keep track of the individual pieces.
    private LinkedList<Action> actions;

	public Board() {
		this.b = new Color[HEIGHT_STANDARD][WIDTH_STANDARD];
        for (Color[] row : this.b) {
            Arrays.fill(row, Color.NULL);
        }
        
        this.actions = new LinkedList();
	}

	public boolean isActionLegal(Action action) {
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
