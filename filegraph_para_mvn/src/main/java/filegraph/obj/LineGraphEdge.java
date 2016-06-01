package filegraph.obj;

public class LineGraphEdge implements Comparable<LineGraphEdge> {
	private LineGraphVertex start;
	private LineGraphVertex end;

	public LineGraphEdge() {
	}

	public LineGraphEdge(LineGraphVertex start, LineGraphVertex end) {
		if (start.compareTo(end) <= 0) {
			this.start = start;
			this.end = end;
		} else {
			this.start = end;
			this.end = start;
		}
	}

	@Override
	public int compareTo(LineGraphEdge e) {
		if (this.equals(e))
			return 0;
		if (this.start.equals(e.getStart()))
			return this.end.compareTo(e.getEnd());
		else
			return this.start.compareTo(e.getStart());
	}

	@Override
	public String toString() {
		return "(" + start.toString() + "," + end.toString() + ")";
	}

	@Override
	public boolean equals(Object e) {
		if (!(e instanceof LineGraphEdge))
			return false;
		return this.start.equals(((LineGraphEdge) e).getStart()) && this.end.equals(((LineGraphEdge) e).getEnd());
	}

	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	public LineGraphVertex getStart() {
		return start;
	}

	public void setStart(LineGraphVertex start) {
		this.start = start;
	}

	public LineGraphVertex getEnd() {
		return end;
	}

	public void setEnd(LineGraphVertex end) {
		this.end = end;
	}

}
