package framework;

public interface IPlayer {
	
	void startGame(Board b, Color c);
	
	Color getColor();
	
	void getAction();

}
