package framework;

import java.util.List;

public interface IPlayer {
	
	void startGame(int boardWidth, int boardHeight, int numPlayers, Color color);
	
	Action getAction(Board.PlayerView board, List<Shape> hand);

}
