package application.players;

import java.util.List;
import java.util.Random;

import application.IPlayer;
import framework.Action;
import framework.Board;
import framework.Color;
import framework.Game;
import framework.PieceName;

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
