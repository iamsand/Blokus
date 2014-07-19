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
			players[i].startGame(b, PLAY_SEQUENCE[i]);
        }
        this.actions = new LinkedList();
	}

	public void run() {
		int pass = 0;
        while (pass < this.numPlayers) {
            IPlayer currentPlayer = this.players[turnIndex % this.numPlayers];
            
            Action action;
			if (hasValidMove()) {
				System.out.println("Turn " + turnIndex);
				System.out.println(b.toConsoleMiniString());
				do {
					action = currentPlayer.getAction(hands[turnIndex % this.numPlayers].view());
				} while (action.color != this.getColorToPlay()
						|| hands[turnIndex % this.numPlayers].view().contains(action.shape)
						|| !(this.actions.size() < this.numPlayers ? this.b.isFirstActionValid(action) : this.b.isActionValid(action)));

				this.b.doAction(action);
				this.actions.add(action);
				pass = 0;
            } else {
                pass++;
            }
            
			turnIndex++;
        }
	}

	// This is intended for the current player only.
	// This method does not update turnIndex. This will be done in the main game loop.
	// This is easily modified to get all legal moves.
	private boolean hasValidMove() {
		// System.out.println("Call to isValid");
		Color color = getColorToPlay();
        for (int i = 0; i < hands[turnIndex % 4].getNumPieces(); i++) {
            ArrayList<Shape> perms = hands[turnIndex % 4].get(i).getAllPermutations();
			for (int j = 0; j< perms.size();j++){
                for (int r = 0; r < Board.HEIGHT_STANDARD; r++) {
                    for (int c = 0; c < Board.WIDTH_STANDARD; c++) {
                        Action tryMe = new Action(perms.get(j), color, r, c);
                        // System.out.println(tryMe);
                        if (b.isActionValid(tryMe)) {
                            // System.out.println("true");
                            return true;
                        }
                    }
                }
			}
		}
		// System.out.println("false");
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
