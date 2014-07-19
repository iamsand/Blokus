package framework;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class represents a game piece shape. Shape objects are immutable.
 */
public final class Shape implements Comparable<Shape> {

	private final PieceName name;
	// [coordinate index][0] -> x
	// [coordinate index][1] -> y
	private final int[][]	coords;

	public Shape(PieceName name, int[][] coords) {
		this.name = name;
		this.coords = new int[coords.length][];
		for (int i = 0; i < coords.length; i++) {
			this.coords[i] = Arrays.copyOf(coords[i], 2);
		}
	}

	public int[][] getCoordinates() {
		int[][] c = new int[this.coords.length][2];
		for (int i = 0; i < c.length; i++) {
			c[i] = Arrays.copyOf(this.coords[i], 2);
		}
		return c;
	}
	
	public int getSize() {
		return coords.length;
	}
	
	public Shape reflectHorizontal() {
		int[][] r = new int[coords.length][2];
		for (int i = 0; i < r.length; i++) {
			// (x,y) --> (-x,y)
			r[i][0] = -this.coords[i][0];
			r[i][1] =  this.coords[i][1];
		}
		return new Shape(this.name,r);
	}
	
	public Shape rotateCW(int n) {
		return new Shape(this.name,Shape.rotateCW(coords, n));
	}

	private static int[][] rotateCW(int[][] a, int n) {
		if (n < 1) 
			return a;
		return Shape.rotateCW(Shape.rotateCW(a), n % 4 - 1);
	}

	private static int[][] rotateCW(int[][] rotateme) {
		int[][] r = new int[rotateme.length][2];
		for (int i = 0; i < r.length; i++) {
			// (x,y) --> (y,-x)
			r[i][0] = rotateme[i][1];
			r[i][1] = -rotateme[i][0];
		}
		return r;
	}
	
	@Override
	public String toString() {
		return name.toString();
	}
	
	@Override
	public int compareTo(Shape s) {
		if (this.name==s.name)
			return 1;
		return 0;
	}

	public ArrayList<Shape> getAllPermutations() {
        ArrayList<Shape> perms = new ArrayList<Shape>();
        perms.add(this);
        for (int i = 0; i < 3; i++) {
            perms.add(perms.get(perms.size() - 1).rotateCW(1));
        }
        perms.add(this.reflectHorizontal());
        for (int i = 0; i < 3; i++) {
            perms.add(perms.get(perms.size() - 1).rotateCW(1));
        }
        return perms;
	}
	
	public String toConsoleString() {
		// all pieces should fit in a 5x5 grid
		boolean[][] map = new boolean[5][5];

		for (int[] coordinate : this.coords) {
			map[2 - coordinate[1]][2 + coordinate[0]] = true;
		}

		StringBuilder sb = new StringBuilder();
		for (boolean[] row : map) {
			for (boolean b : row) {
                sb.append('[');
                sb.append(b ? 'X' : ' ');
                sb.append(']');
			}
			sb.append('\n');
		}
		return sb.toString();
	}
    
}
