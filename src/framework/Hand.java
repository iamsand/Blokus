package framework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This represents the shapes a player possesses.
 */
public class Hand {

    private final ArrayList<Shape> hand;

    public Hand() {
        this.hand = new ArrayList<Shape>();
        
        PieceName[] pieces = PieceName.values();
        for (PieceName name : pieces) {
            this.hand.add(Shape.createShape(name));
        }
    }
    
    // We could also make "hand" public and just have Game pass copies of hand to IPlayers.
    public List<Shape> view() {
    	return Collections.unmodifiableList(this.hand);
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
    
    public Shape get(int index) {
    	try {
            return this.hand.get(index);
        } catch (IndexOutOfBoundsException ex) {
        	// TODO
        	return null;
        }
    }

    public void remove(int index) {
        try {
            this.hand.remove(index);
        } catch (IndexOutOfBoundsException ex) {
        	// TODO
        }
    }
    
}
