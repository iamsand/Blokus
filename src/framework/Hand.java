package framework;

import java.util.ArrayList;

/**
 * This represents the shapes a player possesses.
 */
public class Hand {

    private Color c;  // <-- is this necessary? seems like Color is bound to a player, not the player's hand
    
    private final ArrayList<Shape> hand;

    public Hand(Color c) {
        this.c = c;
        this.hand = new ArrayList();
        
        for (int[][] pieceData : Misc.mono) { this.hand.add(new Shape(pieceData)); }
        for (int[][] pieceData : Misc.domi) { this.hand.add(new Shape(pieceData)); }
        for (int[][] pieceData : Misc.trom) { this.hand.add(new Shape(pieceData)); }
        for (int[][] pieceData : Misc.tetr) { this.hand.add(new Shape(pieceData)); }
        for (int[][] pieceData : Misc.pent) { this.hand.add(new Shape(pieceData)); }
    }

    public void remove(int index) {
        try {
            hand.remove(index);
        } catch (IndexOutOfBoundsException ex) {}
    }
    
}