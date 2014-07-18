package framework;

public enum Color {
	NULL(" "), BLUE("B"), YELLOW("Y"), RED("R"), GREEN("G");

	private final String first;

	private Color(String first) {
		this.first = first;
	}

	@Override
	public String toString() {
		return first;
	}
    
}
