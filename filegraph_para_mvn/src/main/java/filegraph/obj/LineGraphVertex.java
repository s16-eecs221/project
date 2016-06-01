package filegraph.obj;

public class LineGraphVertex implements Comparable<LineGraphVertex> {
	private String origin_s;
	private String origin_e;

	public LineGraphVertex() {
	}

	public LineGraphVertex(int id1, int id2) {
		if (id1 < id2) {
			this.origin_s = String.valueOf(id1);
			this.origin_e = String.valueOf(id2);
		} else {
			this.origin_s = String.valueOf(id2);
			this.origin_e = String.valueOf(id1);
		}
	}

	public LineGraphVertex(String start, String end) {
		this.origin_s = start;
		this.origin_e = end;
	}

	@Override
	public String toString() {
		return "(" + this.origin_s + "," + this.origin_e + ")";
	}

	@Override
	public boolean equals(Object v) {
		if (!(v instanceof LineGraphVertex))
			return false;
		return this.origin_s.equals(((LineGraphVertex) v).getOrigin_s())
				&& this.origin_e.equals(((LineGraphVertex) v).getOrigin_e());
	}

	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	@Override
	public int compareTo(LineGraphVertex v) {
		return this.toString().compareTo(v.toString());
	}

	public String getOrigin_s() {
		return origin_s;
	}

	public void setOrigin_s(String origin_s) {
		this.origin_s = origin_s;
	}

	public String getOrigin_e() {
		return origin_e;
	}

	public void setOrigin_e(String origin_e) {
		this.origin_e = origin_e;
	}
}
