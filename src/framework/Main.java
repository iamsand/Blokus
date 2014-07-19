package framework;

import Players.*;

public class Main {

	// Change arguments here.
	static IPlayer[]	players	= { new Human(), new RandomAI(), new RandomAI(), new RandomAI() };

	//

	public static void main(String args[]) {
		Game g = new Game(new Board(), players);
		g.run();
		g.getResult();
	}
}
