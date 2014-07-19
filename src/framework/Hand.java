package framework;

import java.util.ArrayList;

/**
 * This represents the shapes a player possesses.
 */
public class Hand {

    private final ArrayList<Shape> hand;

    public Hand() {
        this.hand = new ArrayList<Shape>();
        
        for (int[][] pieceData : Misc.mono) { this.hand.add(new Shape(pieceData)); }
        for (int[][] pieceData : Misc.domi) { this.hand.add(new Shape(pieceData)); }
        for (int[][] pieceData : Misc.trom) { this.hand.add(new Shape(pieceData)); }
        for (int[][] pieceData : Misc.tetr) { this.hand.add(new Shape(pieceData)); }
        for (int[][] pieceData : Misc.pent) { this.hand.add(new Shape(pieceData)); }
    }
    
    public int getNumPieces() {
    	return this.hand.size();
    }
    
    public int getScore() {
    	int score = 0;
    	for (Shape s : this.hand) {
    		score += s.getSize();
    	}
    	return score;
    }

    public void remove(int index) {
        try {
            this.hand.remove(index);
        } catch (IndexOutOfBoundsException ex) {}
    }
    
}
