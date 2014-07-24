package blokus.application.players;

import java.util.List;
import java.util.Scanner;

import blokus.application.IPlayer;
import blokus.framework.*;

public class HumanConsolePlayer implements IPlayer {

	private Color color = null;
	
	private static Scanner sc = new Scanner(System.in);
	
	@Override
	public Action getAction(Board.PlayerView board, List<Shape> hand) {
		System.out.println("Begin console input."); // DEBUG ST
		System.out.println("Choose Piece Index.");
		int i = sc.nextInt();
		System.out.println("Rotate clockwise.");
		int rot = sc.nextInt();
		System.out.println("Reflect. [1(y)/0(n)]");
		int ref = sc.nextInt();
		System.out.println("x");
		int x = sc.nextInt();
		System.out.println("y");
		int y = sc.nextInt();
		System.out.println("End console input"); // DEBUG ST
		
		Piece s = Piece.createShape(hand.get(i));
		s = s.rotateCW(rot%4);
		if (ref == 1)
			s = s.reflectHorizontal();
		return new Action(s, this.color, x,y);
	}

	@Override
	public void startGame(int boardWidth, int boardHeight, int numPlayers, Color color) {
		this.color = color;
	}
}
