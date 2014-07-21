package framework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

public class Game {

	private static final Color[] PLAY_SEQUENCE	= 
		{ Color.BLUE, Color.YELLOW, Color.RED, Color.GREEN };
    
	private final TreeMap<Color, ArrayList<PieceName>> hands = new TreeMap<Color, ArrayList<PieceName>>();
    private final Board b = new Board();
	private final LinkedList<Color> activePlayers = new LinkedList<Color>();

	private Status status = Status.CREATED;
	
	public Game(int numPlayers) {
		if (numPlayers != 4) {
			throw new UnsupportedOperationException("Game currently only supports 4 players");
		}
		
		List<PieceName> allPieces = Arrays.asList(PieceName.values());
		for (Color color : PLAY_SEQUENCE) {
			this.activePlayers.add(color);
			this.hands.put(color, new ArrayList<PieceName>(allPieces));
		}
	}

	/**
	 * Attempts to play an action.
	 * @param action the action to play
	 * @return <tt>true</tt> if the action was played successfully
	 */
	public boolean doAction(Action action) {
		if (this.status == Status.STOPPED) {
			return false;
		} else if (this.status == Status.CREATED) {
			this.status = Status.STARTED;
		}
		
		if (action.color != this.getColorToPlay()) {
			return false;
		}
		
		if (!this.hands.get(action.color).contains(action.shape.getName())) {
			return false;
		}
		
		if (!this.b.isActionValid(action)) {
			return false;
		}
		
		this.b.doAction(action);
		this.hands.get(action.color).remove(action.shape.getName());
		
		Color played = this.activePlayers.pop();
		this.activePlayers.add(played);
		
		this.evaluateStatus();
		
		return true;
	}
	
	private Status evaluateStatus() {
		for (int i = 0; i < this.activePlayers.size(); i++) {
			if (!this.hasValidMove(this.activePlayers.get(i))) {
				this.activePlayers.remove(i);
				i--;
			}
		}
		
		if (this.activePlayers.isEmpty()) {
			this.status = Status.STOPPED;
		}
		
		return this.status;
	}

	private boolean hasValidMove(Color color) {
		// System.out.println("hasValidMove()..."); // DEBUG ST
		// System.out.println(color);
        List<PieceName> currentHand = this.hands.get(color);
        for (PieceName shape : currentHand) {
            List<Shape> perms = Shape.createShape(shape).getAllPermutations();
			for (Shape permutation : perms) {
                for (int r = 0; r < this.b.getHeight(); r++) {
                    for (int c = 0; c < this.b.getWidth(); c++) {
                        Action tryMe = new Action(permutation, color, r, c);
                        if (b.isActionValid(tryMe)) {
                        	// System.out.println("Found valid move: " + tryMe);
                            return true;
                        }
                    }
                }
			}
		}
        // System.out.println("No valid move.");
		return false;
	}
	
	public Color getColorToPlay() {
		return this.activePlayers.peek();
	}
	
	public Status getStatus() {
		return this.status;
	}
	
	public Board.PlayerView getBoardView() {
		return this.b.new PlayerView();
	}
	
	public List<PieceName> getHand(Color color) {
		return Collections.unmodifiableList(this.hands.get(color));
	}
	
	public static enum Status {
		CREATED,
		STARTED,
		STOPPED
	}
	
}
