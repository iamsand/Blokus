package blokus.framework;

/**
 * This class represents a game move.
 * Action objects are immutable.
 */
public final class Action {
    
    public final Piece piece;
    public final BlokusColor color;
    
    /**
     * X-coordinate of action (left is zero).
     */
    public final int x;
    
    /**
     * Y-coordinate of action (top is zero).
     */
    public final int y;
    
    public Action(Piece piece, BlokusColor color, int x, int y) {
        this.piece = piece;
        this.color = color;
        this.x = x;
        this.y = y;
    }
    
    @Override
    public String toString(){
    	return "[" + piece.toString() + " " + color + " (" + x + ", " + y + ")" + "]";
    }
    
}
