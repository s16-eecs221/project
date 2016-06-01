package filegraph.obj;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class FileGraphNonLeaf extends FileGraphNode {
	protected String graph_str;
	protected List<FileGraphNode> child_nodes;

	public FileGraphNonLeaf() {
		super();
		this.child_nodes = new ArrayList<FileGraphNode>();

	}

	@Override
	public String toString() {
		return this.toJSON().toString();
	}

	@Override
	public JSONObject toJSON() {
		JSONObject fileNode_json = new JSONObject();
		fileNode_json.append("node_id", this.node_id);
		fileNode_json.append("tags", this.tags);
		fileNode_json.append("graph_str", this.graph_str);
		fileNode_json.append("adj_ids", this.adj_idsToJSONArray());
		fileNode_json.append("child_nodes", this.childsToJSONArray());
		return fileNode_json;
	}

	protected JSONArray adj_idsToJSONArray() {
		JSONArray ids = new JSONArray();
		for (Integer single_id : this.adj_ids)
			ids.put(single_id);
		return ids;
	}

	protected JSONArray childsToJSONArray() {
		JSONArray childs = new JSONArray();
		for (FileGraphNode child : child_nodes)
			childs.put(child.toJSON());
		return childs;
	}

	public String getGraph_str() {
		return graph_str;
	}

	public void setGraph_str(String graph_str) {
		this.graph_str = graph_str;
	}

	public List<FileGraphNode> getChild_nodes() {
		return child_nodes;
	}

	public void setChild_nodes(List<FileGraphNode> child_nodes) {
		this.child_nodes = child_nodes;
	}
}
