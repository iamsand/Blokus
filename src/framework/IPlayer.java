package framework;

import java.util.List;

public interface IPlayer {
	
	void startGame(Board b, Color c);
	
	Color getColor();
	
	Action getAction(List<Shape> hand);

}
