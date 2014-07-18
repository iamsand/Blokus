package framework;

import Players.*;

public class Game {

	int			numPlayers;
	Board		b;
	IPlayer[]	players;
	Hand[]		hands;

	public Game(Board b, IPlayer[] players) {
		numPlayers = players.length;
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
		// TODO
	}
}
