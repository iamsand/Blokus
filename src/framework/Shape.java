package framework;

import java.util.Arrays;

/**
 * This class represents a game piece shape.
 * Shape objects are immutable.
 */
public final class Shape {

    // [coordinate index][0] -> x
    // [coordinate index][1] -> y
    private final int[][] coords;

    public Shape(int[][] coords) {
        this.coords = new int[coords.length][];
        for (int i = 0; i < coords.length; i++) {
            this.coords[i] = Arrays.copyOf(coords[i], 2);
        }
    }
    
    public int[][] getCoordinates() {
        // necessary to return a deep copy because arrays are not read-only
        int[][] c = new int[this.coords.length][2];
        
        for (int i = 0; i < c.length; i++) {
            c[i] = Arrays.copyOf(this.coords[i], 2);
        }
        
        return c;
    }
    
    public Shape rotateCW(int n) {
        return new Shape(Shape.rotateCW(coords, n));
    }
    
    private static int[][] rotateCW(int[][] a, int n) {
        if (n < 1) {
            return a;
        }
        
        return Shape.rotateCW(Shape.rotateCW(a), n % 4 - 1);
    }

    private static int[][] rotateCW(int[][] rotateme) {
        int[][] r = new int[rotateme.length][2];
        for (int i = 0; i < r.length; i++) {
            // (x,y) --> (y,-x)
            r[i][0] =  rotateme[i][1];
            r[i][1] = -rotateme[i][0];
        }
        return r;
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
                if (b) {
                    sb.append('X');
                } else {
                    sb.append('.');
                }
            }
            sb.append('\n');
        }
        
        return sb.toString();
    }
    
}
