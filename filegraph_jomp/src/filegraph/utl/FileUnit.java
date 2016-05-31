package filegraph.utl;

public enum FileUnit {
	K(1024), M(1048576), G(1073741824);

	private int value;

	private FileUnit(int value) {
		this.value = value;
	}

	public int value() {
		return this.value;
	}

	public static FileUnit valueOf(int value) {
		switch (value) {
		case 1024:
			return K;
		case 1048576:
			return M;
		case 1073741824:
			return G;
		default:
			return null;
		}
	}
}
