package framework;

/**
 * This class represents a game move.
 * Action objects are immutable.
 */
public final class Action {
    
    public final Shape shape;
    public final Color color;
    public final int x;
    public final int y;
    
    public Action(Shape shape, Color color, int x, int y) {
        this.shape = shape;
        this.color = color;
        this.x = x;
        this.y = y;
    }
    
}
