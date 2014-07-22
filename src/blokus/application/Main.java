package blokus.application;

import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.JFrame;

import blokus.application.players.RandomAI;
import blokus.framework.Action;
import blokus.framework.Color;
import blokus.framework.Game;
import blokus.framework.Game.Status;

public class Main {

	public static void main(String[] args) {
		BlokusPanel panel = new BlokusPanel();
		Game blokus = new Game(4);
		
		TreeMap<Color, IPlayer> players = new TreeMap<Color, IPlayer>();
		
		// ----- Set players. -----
		players.put(Color.BLUE,   panel.new HumanPlayer());
		players.put(Color.YELLOW, new RandomAI(blokus));
		players.put(Color.RED,    new RandomAI(blokus));
		players.put(Color.GREEN,  new RandomAI(blokus));
		// ------------------------
		
		JFrame frame = new JFrame("Blokus");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(panel);
		frame.setVisible(true);
		
		for (Entry<Color, IPlayer> entry : players.entrySet()) {
			entry.getValue().startGame(blokus.getBoardView().getWidth(), blokus.getBoardView().getHeight(), players.size(), entry.getKey());
		}

		while (blokus.getStatus() != Status.STOPPED) {
			panel.updateBoard(blokus.getBoardView());
			frame.pack();
			Color color = blokus.getColorToPlay();
			frame.setTitle("Blokus - " + color.name() + " to play");
			IPlayer player = players.get(color);
			Action action = player.getAction(blokus.getBoardView(), blokus.getHand(color));
			blokus.doAction(action);
		}
		
		frame.setTitle("Blokus - Game over");
	}

}
