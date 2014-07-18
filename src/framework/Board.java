package framework;

public class Board {

	// This will represent the visual board.
	Colors[][]	b;

	// This will keep track of the individual pieces.
	// TODO: put some sort of map here

	public Board() {
		b = new Colors[20][20];
		for (int r = 0; r < 20; r++)
			for (int c = 0; c < 20; c++) 
				b[r][c] = Colors.NULL;
	}

	public boolean addShape(int[][] s, int x, int y) {
		// TODO
		return false;
	}

	public void conPrint() {
		for (int r = 0; r < 20; r++) {
			for (int c = 0; c < 20; c++) {
				System.out.print("[" + b[r][c] + "] ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
