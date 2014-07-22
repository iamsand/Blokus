package blokus.application.players;

import java.util.List;
import java.util.Random;

import blokus.application.IPlayer;
import blokus.framework.Action;
import blokus.framework.Board;
import blokus.framework.Color;
import blokus.framework.Game;
import blokus.framework.PieceName;

public class RandomAI implements IPlayer {

	private Random r = new Random();
	private Game game;
	private Color color;
	
	public RandomAI(Game game) {
		this.game = game;
	}
	
    public void startGame(int boardWidth, int boardHeight, int numPlayers, Color color) {
        this.color = color;
    }

    public Action getAction(Board.PlayerView board, List<PieceName> hand) {
    	List<Action> actions = game.allValidMoves(this.color);
    	return actions.get(r.nextInt(actions.size()));
    }

}
