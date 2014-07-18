package framework;

// http://en.wikipedia.org/wiki/File:BlokusTiles.svg
public enum Shape {
	M1(Misc.mono[0]), D1(Misc.domi[0]); // T1, T2, T3, T4, T5, P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12;

	// [coordinate index][x,y coordinate]
	private int[][]	coords;

	Shape(int[][] coords) {
		this.coords = coords;
	}

	private int[][] rotate(int[][] rotateme) {
		return null;
	}

}
