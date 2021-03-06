package blokus.framework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

public class Game {

	private static final BlokusColor[] PLAY_SEQUENCE	= 
		{ BlokusColor.BLUE, BlokusColor.YELLOW, BlokusColor.RED, BlokusColor.GREEN };
    
	private final TreeMap<BlokusColor, ArrayList<Shape>> hands = new TreeMap<BlokusColor, ArrayList<Shape>>();
    private final Board b = new Board();
	private final LinkedList<BlokusColor> activePlayers = new LinkedList<BlokusColor>();

	private Status status = Status.CREATED;
	
	public Game(int numPlayers) {
		if (numPlayers != 4) {
			throw new UnsupportedOperationException("Game currently only supports 4 players");
		}
		
		List<Shape> allPieces = Arrays.asList(Shape.values());
		for (BlokusColor color : PLAY_SEQUENCE) {
			this.activePlayers.add(color);
			this.hands.put(color, new ArrayList<Shape>(allPieces));
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
		
		if (!this.hands.get(action.color).contains(action.piece.getShape())) {
			return false;
		}
		
		if (!this.b.isActionValid(action)) {
			return false;
		}
		
		this.b.doAction(action);
		this.hands.get(action.color).remove(action.piece.getShape());
		
		BlokusColor played = this.activePlayers.pop();
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

	private boolean hasValidMove(BlokusColor color) {
		// System.out.println("hasValidMove()..."); // DEBUG ST
		// System.out.println(color);
        List<Shape> currentHand = this.hands.get(color);
        for (Shape shape : currentHand) {
            List<Piece> perms = Piece.createShape(shape).getAllPermutations();
			for (Piece permutation : perms) {
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
	
	public List<Action> allValidMoves(BlokusColor color) {
		LinkedList<Action> allActions = new LinkedList<Action>();

		List<Shape> currentHand = this.hands.get(color);
		for (Shape shape : currentHand) {
			List<Piece> perms = Piece.createShape(shape).getAllPermutations();
			for (Piece permutation : perms) {
				for (int r = 0; r < this.b.getHeight(); r++) {
					for (int c = 0; c < this.b.getWidth(); c++) {
						Action tryMe = new Action(permutation, color, r, c);
						if (b.isActionValid(tryMe)) {
							allActions.add(tryMe);
						}
					}
				}
			}
		}

		return allActions;
	}
	
	public BlokusColor getColorToPlay() {
		return this.activePlayers.peek();
	}
	
	public Status getStatus() {
		return this.status;
	}
	
	public Board.PlayerView getBoardView() {
		return this.b.new PlayerView();
	}
	
	public List<Shape> getHand(BlokusColor color) {
		return Collections.unmodifiableList(this.hands.get(color));
	}
	
	public static enum Status {
		CREATED,
		STARTED,
		STOPPED
	}
	
}
