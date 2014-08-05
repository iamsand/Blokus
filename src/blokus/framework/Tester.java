package blokus.framework;

public class Tester {

	public static void main(String[] args) {
		Piece s = Piece.createShape(Shape.Q2);
		System.out.println(s.toConsoleString());
		s = s.rotateCW(1);
		System.out.println(s.toConsoleString());
		s = s.rotateCW(1);
		System.out.println(s.toConsoleString());
		s = s.rotateCW(1);
		System.out.println(s.toConsoleString());

		Board b = new Board();
        Piece.createShape(Shape.Q2);
		b.doAction(new Action(s, BlokusColor.BLUE, 3, 3));
		System.out.println(b.toConsoleString());
		s = s.reflectHorizontal();
		b.doAction(new Action(s, BlokusColor.BLUE, 10, 10));
		System.out.println(b.toConsoleString());
	}

}
