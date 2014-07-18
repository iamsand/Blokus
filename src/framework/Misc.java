package framework;

public class Misc {
	// This is the hardcoding of the pieces. The indexes are [piece number][coordinate index][x,y coordinate]
    // http://en.wikipedia.org/wiki/File:BlokusTiles.svg
	public final static int[][][]	mono	= new int[][][] { { { 0, 0 } } };
	public final static int[][][]	domi	= new int[][][] { { { 0, 0 }, { 1, 0 } } };
	public final static int[][][]	trom	= new int[][][] { { { 0, 0 }, { 1, 0 }, { 1, -1 } }, { { 0, 0 }, { 1, 0 }, { 2, 0 } } };
	// TODO
	public final static int[][][]	tetr	= new int[][][] { { {} } };
	public final static int[][][]	pent	= new int[][][] { { {} } };
}
