package Players;

import framework.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Human implements IPlayer {

	private Color c = null;
	
	@Override
	public Action getAction(ArrayList<Shape> al) {
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
		
		Shape s = al.get(i);
		s = s.rotateCW(rot);
		if (ref == 1)
			s.reflectHorizontal();
		return new Action(s, c, x,y);
	}

	@Override
	public void startGame(Board b, Color c) {
		this.c = c;
		
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}

}
