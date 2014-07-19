package framework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Game {

	private static int turnIndex;
	private static final Color[] PLAY_SEQUENCE	= 
		{ Color.BLUE, Color.YELLOW, Color.RED, Color.GREEN };
    
    private final int numPlayers;
    private final Board b;
    private final IPlayer[] players;
    private final Hand[] hands;

	private final LinkedList<Action> actions;

	public Game(Board b, IPlayer[] players) {
		turnIndex = 0;
		this.b = b;
        this.numPlayers = players.length;
		this.players = players;
		this.hands = new Hand[this.numPlayers];
		for (int i = 0; i < this.numPlayers; i++) {
			this.hands[i] = new Hand();
        }
        this.actions = new LinkedList();
	}

	public void run() {
		int pass = 0;
        while (pass < this.numPlayers) {
            IPlayer currentPlayer = this.players[turnIndex % this.numPlayers];
            
            Action action;
			if (hasValidMove()) {
				do {
					action = currentPlayer.getAction();
				} while (action.color != this.getColorToPlay()
						|| Arrays.asList(hands[turnIndex%this.numPlayers].view()).contains(action.shape)
						|| !(this.actions.size() < this.numPlayers ? this.b.isFirstActionValid(action) : this.b.isActionValid(action)));

				this.b.doAction(action);
				this.actions.add(action);

				pass = 0;
			}
            else{
            	turnIndex++;
            	pass++;
            }
        }
	}

	// This is intended for the current player only.
	// This method does not update turnIndex. This will be done in the main game loop.
	// This is easily modified to get all legal moves.
	private boolean hasValidMove(){
		Color co = getColorToPlay();
		for (int i = 0; i < hands[i].getNumPieces();i++){
			ArrayList<Shape> perms = hands[i].get(i).getAllPermutations();
			for (int j = 0; j< perms.size();j++){
				for (int r = 0; r<Board.HEIGHT_STANDARD;r++){
					for (int c = 0; c<Board.WIDTH_STANDARD;c++){
						Action tryMe = new Action(perms.get(j), co, r, c);
						if (b.isActionValid(tryMe))
							return true;
					}
				}	
			}
		}
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
