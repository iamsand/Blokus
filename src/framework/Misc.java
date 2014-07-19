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
	
	// G R
	// B Y 
	public static final Color[]		PLAY_SEQUENCE	= 
		{ Color.BLUE, Color.YELLOW, Color.RED, Color.GREEN };
    
}
