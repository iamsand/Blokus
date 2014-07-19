package framework;

public class Tester {

	public static void main(String[] args) {
		Shape s = new Shape(Misc.tetr[2]);
		System.out.println(s.toConsoleString());
		s = s.rotateCCW(1);
		System.out.println(s.toConsoleString());
		s = s.rotateCCW(1);
		System.out.println(s.toConsoleString());
		s = s.rotateCCW(1);
		System.out.println(s.toConsoleString());

		Board b = new Board();
		System.out.println(b.toConsoleMiniString());

		b.doAction(new Action(s, Color.BLUE, 3, 3));
		System.out.println(b.toConsoleMiniString());
	}

}
