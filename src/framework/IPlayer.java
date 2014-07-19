package framework;

import java.util.ArrayList;

public interface IPlayer {
	
	void startGame(Board b, Color c);
	
	Color getColor();
	
	Action getAction(ArrayList<Shape> al);

}
