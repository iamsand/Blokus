package framework;

import java.util.Arrays;
import java.util.LinkedList;

public class Board {

	public static final int				WIDTH_STANDARD	= 20;
	public static final int				HEIGHT_STANDARD	= 20;

	

	private final Color[][]				b;
	private final LinkedList<Action>	actions;

	public Board() {
		this.b = new Color[HEIGHT_STANDARD][WIDTH_STANDARD];
		for (Color[] row : this.b) {
			Arrays.fill(row, Color.NULL);
		}

		this.actions = new LinkedList();
	}

	private Color getColorToPlay() {
		return Misc.PLAY_SEQUENCE[this.actions.size() % 4];
	}

	public boolean isActionLegal(Action action) {
		if (action.color != this.getColorToPlay()) {
			return false;
		}

		int[][] cords = action.shape.getCoordinates();

		// verify the piece doesn't go off the board.
		for (int i = 0; i < cords.length; i++) {
			int newX = action.x + cords[i][0];
			int newY = action.y + cords[i][1];
			if (newX >= 20 || newX < 0 || newY >= 20 || newY < 0)
				return false;
		}

		// verify that the starting piece touches the correct corner.
		if (this.actions.size() < Misc.PLAY_SEQUENCE.length) {
			int[] corner = Misc.startCoord(action.color);
			for (int i = 0; i < cords.length; i++) {
				int newX = action.x + cords[i][0];
				int newY = action.y + cords[i][1];
				if (newX == corner[0] && newY == corner[1])
					break;
				if (i == cords.length - 1)
					return false;
			}
		}

		// verify that piece will not overlap with existing pieces
		for (int i = 0; i < cords.length; i++) {
			int newX = action.x + cords[i][0];
			int newY = action.y + cords[i][1];
			if (b[newX][newY] != Color.NULL)
				return false;
		}

		// TODO: verify that piece is not adjacent to pieces of the same color

		// TODO: verify that one corner of the piece touches the corner of a piece of the same color

		return true;
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
			this.b[19-action.y + coordinate[1]][action.x + coordinate[0]] = action.color;
		}

		this.actions.add(action);

		return true;
	}


	public String toConsoleMiniString() {
		StringBuilder sb = new StringBuilder();
		for (Color[] row : this.b) {
			for (Color c : row) {
				sb.append("[" + (c == Color.NULL ? ' ' : c.name().charAt(0)) + "]");
			}

			sb.append('\n');
		}

		return sb.toString();
	}

}
