package Players;

import framework.*;
import java.util.List;
import java.util.Scanner;

public class HumanConsolePlayer implements IPlayer {

	private Color color = null;
	
	@Override
	public Action getAction(Board.PlayerView board, List<Shape> hand) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Choose Piece Index.");
		int i = sc.nextInt();
		System.out.println("Rotate clockwise.");
		int rot = sc.nextInt()%4;
		System.out.println("Reflect. [1(y)/0(n)]");
		int ref = sc.nextInt();
		System.out.println("x");
		int x = sc.nextInt();
		System.out.println("y");
		int y = sc.nextInt();
		sc.close();
		
		Shape s = hand.get(i);
		s = s.rotateCW(rot);
		if (ref == 1)
			s.reflectHorizontal();
		return new Action(s, this.color, x,y);
	}

	@Override
	public void startGame(int boardWidth, int boardHeight, int numPlayers, Color color) {
		this.color = color;
	}
}
