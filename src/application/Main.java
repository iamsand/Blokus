package application;

import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.JFrame;

import framework.Action;
import framework.Color;
import framework.Game;
import framework.Game.Status;

public class Main {

	public static void main(String[] args) {
		BlokusPanel panel = new BlokusPanel();
		Game blokus = new Game(4);
		
		TreeMap<Color, IPlayer> players = new TreeMap<Color, IPlayer>();
		
		// ----- Set players. -----
		players.put(Color.BLUE,   panel.new HumanPlayer());
		players.put(Color.YELLOW, panel.new HumanPlayer());
		players.put(Color.RED,    panel.new HumanPlayer());
		players.put(Color.GREEN,  panel.new HumanPlayer());
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