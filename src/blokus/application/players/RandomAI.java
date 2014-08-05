package blokus.application.players;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import blokus.application.IPlayer;
import blokus.framework.Action;
import blokus.framework.Board;
import blokus.framework.BlokusColor;
import blokus.framework.Game;
import blokus.framework.Shape;

public class RandomAI implements IPlayer {

	private Random r = new Random();
	private Game game;
	private BlokusColor color;
	
	public RandomAI(Game game) {
		this.game = game;
	}
	
    public void startGame(int boardWidth, int boardHeight, int numPlayers, BlokusColor color) {
        this.color = color;
    }

    public Action getAction(Board.PlayerView board, List<Shape> hand) {
		Board.LOGGER.setLevel(Level.OFF);
		
    	List<Action> actions = game.allValidMoves(this.color);
		
		Board.LOGGER.setLevel(Level.ALL);
		
    	return actions.get(r.nextInt(actions.size()));
    }

}
