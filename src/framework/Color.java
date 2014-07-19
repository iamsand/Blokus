package framework;

public enum Color {
    
	NULL, BLUE, YELLOW, RED, GREEN;
    
	@Override
	public String toString() {
		if (this == Color.NULL)
			return " ";
		else
			return this.name().substring(0, 1);
	}
    
}
