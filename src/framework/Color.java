package framework;

public enum Color {
	NULL(" "), BLUE("B"), YELLOW("Y"), RED("R"), GREEN("G");

	private String	first;

	Color(String first) {
		this.first = first;
	}

	@Override
	public String toString() {
		return first;
	}
}
