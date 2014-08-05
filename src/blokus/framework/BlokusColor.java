package blokus.framework;

public enum BlokusColor {
    
	NULL, BLUE, YELLOW, RED, GREEN;
    
	@Override
	public String toString() {
		if (this == BlokusColor.NULL)
			return " ";
		else
			return this.name().substring(0, 1);
	}
    
}
