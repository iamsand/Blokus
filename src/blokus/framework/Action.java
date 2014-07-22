package blokus.framework;

/**
 * This class represents a game move.
 * Action objects are immutable.
 */
public final class Action {
    
    public final Shape shape;
    public final Color color;
    
    /**
     * X-coordinate of action (left is zero).
     */
    public final int x;
    
    /**
     * Y-coordinate of action (top is zero).
     */
    public final int y;
    
    public Action(Shape shape, Color color, int x, int y) {
        this.shape = shape;
        this.color = color;
        this.x = x;
        this.y = y;
    }
    
    @Override
    public String toString(){
    	return "[" + shape.toString() + " " + color + " (" + x + ", " + y + ")" + "]";
    }
    
}
