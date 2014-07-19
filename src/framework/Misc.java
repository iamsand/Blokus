package framework;

public class Misc {
    
	// This is the data for the shapes. The indexes are [piece number][coordinate index][x,y coordinate]
    // http://en.wikipedia.org/wiki/File:BlokusTiles.svg
	public final static int[][][] mono = new int[][][] {
        { { 0, 0 } }
    };
    
	public final static int[][][] domi = new int[][][] {
        { { 0, 0 }, { 1, 0 } }
    };
    
	public final static int[][][] trom = new int[][][] {
        { { 0, 0 }, { 0, -1 }, { -1, 0 } },
        { { 0, 0 }, { 1, 0 }, { -1, 0 } }
    };
    
	public final static int[][][] tetr = new int[][][] {
        { { 0, 0 }, { 1, 0 }, { 1, 1 }, { 0, 1 } },
        { { 0, 0 }, { 1, 0 }, { -1, 0 }, { 0, 1 } },
        { { -1, 0 }, { 0, 0 }, { 1, 0 }, { 1, 1 } },
        { { 0, 0 }, { 1, 0 }, { 2, 0 }, { -1, 0 } },
        { { 0, 0 }, { -1, 0 }, { 0, 1 }, { 1, 1 } }
    };
	
	public final static int[][][] pent = new int[][][] {
        { {0,0},{0,1},{1,0},{2,0},{3,0} },
        { {0,0},{-1,0},{1,0},{0,1},{0,2} },
        { {0,0},{0,1},{0,2},{1,0},{2,0} },
        { {0,0},{1,0},{1,1},{2,1},{3,1} },
        { {0,0},{0,1},{1,1},{2,1},{2,2} },
        { {0,0},{0,-1},{0,-2},{0,1},{0,2} },
        { {0,0},{0,1},{0,2},{1,0},{1,1} },
        { {0,0},{0,1},{1,1},{1,2},{2,2} },
        { {0,0},{1,0},{0,1},{0,2},{1,2} },
        { {0,0},{0,-1},{-1,0},{0,1},{1,1} },
        { {0,0},{0,1},{1,0},{0,-1},{-1,0} },
        { {0,0},{-1,0},{0,1},{1,0},{2,0} },
    };
	
	// The starting corners for each color.
	public static int[] startCoord(Color c){
		switch (c){
		case BLUE:
			return new int[] {0,0};
		case YELLOW:
			return new int[] {19,0};
		case RED:
			return new int[] {19,19};
		case GREEN:
			return new int[] {0,19};
		default:
			// TODO maybe there is a better way of doing this
			System.out.println("Tried to get startCoord of Null!");
			System.exit(0);
			return null; 
			
		}
	}
}
