package framework;

import Players.*;

public class Game {

	private final int			numPlayers = 4;
	private final Board		b;
	private final IPlayer[]	players;
	private final Hand[]		hands;

	public Game(Board b, IPlayer[] players) {
		this.b = b;
		this.players = players;
		hands = new Hand[numPlayers];
		for (int i = 0; i < numPlayers; i++)
			hands[i] = new Hand(players[i].getColor());
	}

	public void run() {
		// TODO
	}

	public void getResult() {
		
		for (int i = 0; i< numPlayers;i++){
		System.out.println(Misc.PLAY_SEQUENCE[i] + " " + hands[i].getScore());	
		}
	}
}
