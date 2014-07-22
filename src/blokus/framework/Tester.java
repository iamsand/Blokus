package blokus.framework;

public class Tester {

	public static void main(String[] args) {
		Shape s = Shape.createShape(PieceName.Q2);
		System.out.println(s.toConsoleString());
		s = s.rotateCW(1);
		System.out.println(s.toConsoleString());
		s = s.rotateCW(1);
		System.out.println(s.toConsoleString());
		s = s.rotateCW(1);
		System.out.println(s.toConsoleString());

		Board b = new Board();
        Shape.createShape(PieceName.Q2);
		b.doAction(new Action(s, Color.BLUE, 3, 3));
		System.out.println(b.toConsoleString());
		s = s.reflectHorizontal();
		b.doAction(new Action(s, Color.BLUE, 10, 10));
		System.out.println(b.toConsoleString());
	}

}
