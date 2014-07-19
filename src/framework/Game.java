package framework;

import java.util.LinkedList;

public class Game {

	private static final Color[] PLAY_SEQUENCE	= 
		{ Color.BLUE, Color.YELLOW, Color.RED, Color.GREEN };
    
    private final int numPlayers;
    private final Board b;
    private final IPlayer[] players;
    private final Hand[] hands;

	private final LinkedList<Action> actions;

	public Game(Board b, IPlayer[] players) {
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
		boolean gameOver = false;
        while (!gameOver) {
            IPlayer currentPlayer = this.players[this.actions.size() % this.numPlayers];
            
            Action action;
            do {
                // try until valid
                action = currentPlayer.getAction();
            } while (action.color != this.getColorToPlay() || !(this.actions.size() < this.numPlayers ? this.b.isFirstActionValid(action) : this.b.isActionValid(action)));
            
            this.b.doAction(action);
            this.actions.add(action);
            
            // TODO: check gameOver
            
        }
	}

	private Color getColorToPlay() {
		return Game.PLAY_SEQUENCE[this.actions.size() % 4];
	}

	public void getResult() {
        for (int i = 0; i < this.numPlayers; i++) {
            System.out.println(Game.PLAY_SEQUENCE[i] + " " + this.hands[i].getScore());
        }
    }
}
