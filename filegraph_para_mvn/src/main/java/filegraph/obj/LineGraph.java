package filegraph.obj;

import java.util.ArrayList;
import java.util.List;

public class LineGraph {
	private List<LineGraphVertex> vertex;
	private List<LineGraphEdge> edges;

	public LineGraph() {
		vertex = new ArrayList<LineGraphVertex>();
		edges = new ArrayList<LineGraphEdge>();
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Vertex:\n");
		for (LineGraphVertex v : this.vertex)
			str.append(v.toString() + " ");
		str.append("\nEdges:\n");
		for (LineGraphEdge e : this.edges)
			str.append(e.toString() + " ");
		return str.toString();
	}

	public List<LineGraphVertex> getVertex() {
		return vertex;
	}

	public void setVertex(List<LineGraphVertex> vertex) {
		this.vertex = vertex;
	}

	public List<LineGraphEdge> getEdges() {
		return edges;
	}

	public void setEdges(List<LineGraphEdge> edges) {
		this.edges = edges;
	}
}
