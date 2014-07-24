package blokus.application;

import java.util.List;

import blokus.framework.Action;
import blokus.framework.Board;
import blokus.framework.Color;
import blokus.framework.Shape;

public interface IPlayer {
	
	void startGame(int boardWidth, int boardHeight, int numPlayers, Color color);
	
	Action getAction(Board.PlayerView board, List<Shape> hand);

}
