package framework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Game {

	private static final Color[] PLAY_SEQUENCE	= 
		{ Color.BLUE, Color.YELLOW, Color.RED, Color.GREEN };
    
    private final int numPlayers;
    private final Board b;
    private final IPlayer[] players;
    private final Hand[] hands;
	private int turnIndex;

	private final LinkedList<Action> actions;

	public Game(Board b, IPlayer[] players) {
		this.turnIndex = 0;
		this.b = b;
        this.numPlayers = players.length;
		this.players = players;
		this.hands = new Hand[this.numPlayers];
		for (int i = 0; i < this.numPlayers; i++) {
			this.hands[i] = new Hand();
            this.players[i].startGame(Board.WIDTH_STANDARD, Board.HEIGHT_STANDARD, this.numPlayers, Game.PLAY_SEQUENCE[i]);
        }
        this.actions = new LinkedList();
	}

	public void run() {
		int pass = 0;
        while (pass < this.numPlayers) {
            IPlayer currentPlayer = this.players[turnIndex % this.numPlayers]; 
            
            
            Action action;
			if (hasValidMove()) {
				System.out.println("Turn " + turnIndex); // DEBUG ST
				System.out.println(this.getColorToPlay() + "'s turn to play.");
				System.out.println(b.toConsoleMiniString()); // DEBUG ST
				boolean firstTry = true; // DEBUG VAR
				do {
					if (!firstTry)
						System.out.println("Invalid move!");
					action = currentPlayer.getAction(this.b.new PlayerView(), this.hands[turnIndex % this.numPlayers].view());
					firstTry = false;
				} while (action.color != this.getColorToPlay()
						|| this.hands[turnIndex % this.numPlayers].view().contains(action.shape)
						|| !(this.b.isActionValid(action)));

				this.b.doAction(action);
				this.actions.add(action);
				pass = 0;
            } else {
                pass++;
            }
            
			turnIndex++;
        }
	}

	private boolean hasValidMove() {
		// System.out.println("hasValidMove()..."); // DEBUG ST
		Color color = getColorToPlay();
		// System.out.println(color);
        for (int i = 0; i < hands[turnIndex % numPlayers].getNumPieces(); i++) {
            ArrayList<Shape> perms = hands[turnIndex % numPlayers].get(i).getAllPermutations();
			for (int j = 0; j< perms.size();j++){
                for (int r = 0; r < Board.HEIGHT_STANDARD; r++) {
                    for (int c = 0; c < Board.WIDTH_STANDARD; c++) {
                        Action tryMe = new Action(perms.get(j), color, r, c);
                        if (b.isActionValid(tryMe)) {
                        	// System.out.println("Found valid move: " + tryMe); // 
                            return true;
                        }
                    }
                }
			}
		}
        // System.out.println("No valid move.");
		return false;
	}
	
	private Color getColorToPlay() {
		return Game.PLAY_SEQUENCE[turnIndex % this.numPlayers];
	}
	
	public void getResult() {
        for (int i = 0; i < this.numPlayers; i++) {
            System.out.println(Game.PLAY_SEQUENCE[i] + " " + this.hands[i].getScore());
        }
    }
}
