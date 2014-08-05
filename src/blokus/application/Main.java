package blokus.application;

import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.JFrame;

import blokus.application.players.RandomAI;
import blokus.framework.Action;
import blokus.framework.BlokusColor;
import blokus.framework.Game;
import blokus.framework.Game.Status;

public class Main {

	public static void main(String[] args) {
		BlokusPanel panel = new BlokusPanel();
		Game blokus = new Game(4);
		
		TreeMap<BlokusColor, IPlayer> players = new TreeMap<BlokusColor, IPlayer>();
		
		// ----- Set players. -----
		players.put(BlokusColor.BLUE,   panel.new HumanPlayer());
		players.put(BlokusColor.YELLOW, new RandomAI(blokus));
		players.put(BlokusColor.RED,    new RandomAI(blokus));
		players.put(BlokusColor.GREEN,  new RandomAI(blokus));
		// ------------------------
		
		JFrame frame = new JFrame("Blokus");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(panel);
		frame.setVisible(true);
		
		for (Entry<BlokusColor, IPlayer> entry : players.entrySet()) {
			entry.getValue().startGame(blokus.getBoardView().getWidth(), blokus.getBoardView().getHeight(), players.size(), entry.getKey());
		}

		while (blokus.getStatus() != Status.STOPPED) {
			panel.updateBoard(blokus.getBoardView());
			frame.pack();
			BlokusColor color = blokus.getColorToPlay();
			frame.setTitle("Blokus - " + color.name() + " to play");
			IPlayer player = players.get(color);
			Action action = player.getAction(blokus.getBoardView(), blokus.getHand(color));
			blokus.doAction(action);
		}
		
		panel.updateBoard(blokus.getBoardView());
		frame.pack();
		frame.setTitle("Blokus - Game over");
	}

}
