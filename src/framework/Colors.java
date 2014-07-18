package framework;

public enum Colors {
	NULL(" "), BLUE("B"), YELLOW("Y"), RED("R"), GREEN("G");

	private String	first;

	Colors(String first) {
		this.first = first;
	}

	@Override
	public String toString() {
		return first;
	}
}
