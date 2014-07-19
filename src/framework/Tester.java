package framework;

public class Tester {

	public static void main(String[] args) {
		Shape s = new Shape(PieceName.Q2,Misc.tetr[2]);
		System.out.println(s.toConsoleString());
		s = s.rotateCW(1);
		System.out.println(s.toConsoleString());
		s = s.rotateCW(1);
		System.out.println(s.toConsoleString());
		s = s.rotateCW(1);
		System.out.println(s.toConsoleString());

		Board b = new Board();
		s = new Shape(PieceName.Q2, Misc.tetr[2]);
		b.doAction(new Action(s, Color.BLUE, 3, 3));
		System.out.println(b.toConsoleMiniString());
		s = s.reflectHorizontal();
		b.doAction(new Action(s, Color.BLUE, 10, 10));
		System.out.println(b.toConsoleMiniString());
	}

}
