package application;

import java.util.List;

import framework.Action;
import framework.Board;
import framework.Color;
import framework.PieceName;

public interface IPlayer {
	
	void startGame(int boardWidth, int boardHeight, int numPlayers, Color color);
	
	Action getAction(Board.PlayerView board, List<PieceName> hand);

}
