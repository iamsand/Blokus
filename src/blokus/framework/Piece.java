package blokus.framework;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a game piece shape. Shape objects are immutable.
 */
public final class Piece {

	private final Shape shape;
	
	// [coordinate index][0] -> x
	// [coordinate index][1] -> y
	private final int[][]	coords;

	private Piece(Shape shape, int[][] coords) {
		this.shape = shape;
		this.coords = new int[coords.length][];
		for (int i = 0; i < coords.length; i++) {
			this.coords[i] = Arrays.copyOf(coords[i], 2);
		}
	}
    
    public static Piece createShape(Shape shape) {
        if (shape == null) {
            return null;
        }
        
        return new Piece(shape, Piece.PIECE_DATA[shape.ordinal()]);
    }

	public int[][] getCoordinates() {
		int[][] c = new int[this.coords.length][2];
		for (int i = 0; i < c.length; i++) {
			c[i] = Arrays.copyOf(this.coords[i], 2);
		}
		return c;
	}
	
	public int getSize() {
		return this.coords.length;
	}
	
	public Shape getShape() {
		return this.shape;
	}
	
	public Piece reflectHorizontal() {
		int[][] r = new int[coords.length][2];
		for (int i = 0; i < r.length; i++) {
			// (x,y) --> (-x,y)
			r[i][0] = -this.coords[i][0];
			r[i][1] =  this.coords[i][1];
		}
		return new Piece(this.shape,r);
	}
	
	public Piece rotateCW(int n) {
		return new Piece(this.shape,Piece.rotateCW(coords, n));
	}

	private static int[][] rotateCW(int[][] a, int n) {
		if (n < 1) 
			return a;
		return Piece.rotateCW(Piece.rotateCW(a), n % 4 - 1);
	}

	private static int[][] rotateCW(int[][] rotateme) {
		int[][] r = new int[rotateme.length][2];
		for (int i = 0; i < r.length; i++) {
			// (x,y) --> (-y,x)
			r[i][0] = -rotateme[i][1];
			r[i][1] =  rotateme[i][0];
		}
		return r;
	}
	
	@Override
	public String toString() {
		return shape.toString();
	}
	
	public List<Piece> getAllPermutations() {
        LinkedList<Piece> perms = new LinkedList<Piece>();
        perms.add(this);
        for (int i = 0; i < 3; i++) {
            perms.add(perms.getLast().rotateCW(1));
        }
        perms.add(this.reflectHorizontal());
        for (int i = 0; i < 3; i++) {
            perms.add(perms.getLast().rotateCW(1));
        }
        return perms;
	}
	
	public String toConsoleString() {
		// all pieces should fit in a 5x5 grid
		boolean[][] map = new boolean[5][5];

		for (int[] coordinate : this.coords) {
			map[2 - coordinate[1]][2 + coordinate[0]] = true;
		}

		StringBuilder sb = new StringBuilder(30);
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
    
    private static final int[][][] PIECE_DATA = new int[][][] {
        {{0, 0}},
        {{0, 0}, {1, 0}},
        {{0, 0}, {0, -1}, {-1, 0}},
        {{0, 0}, {1, 0}, {-1, 0}},
        {{0, 0}, {1, 0}, {1, 1}, {0, 1}},
        {{0, 0}, {1, 0}, {-1, 0}, {0, 1}},
        {{-1, 0}, {0, 0}, {1, 0}, {1, 1}},
        {{0, 0}, {1, 0}, {2, 0}, {-1, 0}},
        {{0, 0}, {-1, 0}, {0, 1}, {1, 1}},
        {{0, 0}, {0, 1}, {1, 0}, {2, 0}, {3, 0}},
        {{0, 0}, {-1, 0}, {1, 0}, {0, 1}, {0, 2}},
        {{0, 0}, {0, 1}, {0, 2}, {1, 0}, {2, 0}},
        {{0, 0}, {1, 0}, {1, 1}, {2, 1}, {3, 1}},
        {{0, 0}, {0, 1}, {1, 1}, {2, 1}, {2, 2}},
        {{0, 0}, {0, -1}, {0, -2}, {0, 1}, {0, 2}},
        {{0, 0}, {0, 1}, {0, 2}, {1, 0}, {1, 1}},
        {{0, 0}, {0, 1}, {1, 1}, {1, 2}, {2, 2}},
        {{0, 0}, {1, 0}, {0, 1}, {0, 2}, {1, 2}},
        {{0, 0}, {0, -1}, {-1, 0}, {0, 1}, {1, 1}},
        {{0, 0}, {0, 1}, {1, 0}, {0, -1}, {-1, 0}},
        {{0, 0}, {-1, 0}, {0, 1}, {1, 0}, {2, 0}}
    };
    
}
