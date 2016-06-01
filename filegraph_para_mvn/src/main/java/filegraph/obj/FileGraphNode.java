package filegraph.obj;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

abstract public class FileGraphNode implements Comparable<FileGraphNode> {
	protected int node_id;
	/** Tags obtained by this node, json format. */
	protected String tags;
	protected List<Integer> adj_ids;

	public FileGraphNode() {
		this.adj_ids = new ArrayList<Integer>();
	}

	@Override
	abstract public String toString();

	abstract public JSONObject toJSON();

	protected JSONArray adj_idsToJSONArray() {
		JSONArray ids = new JSONArray();
		for (Integer single_id : this.adj_ids)
			ids.put(single_id);
		return ids;
	}

	@Override
	public int compareTo(FileGraphNode n) {
		if (this.node_id == n.getNode_id())
			return 0;
		if (this.node_id > n.getNode_id())
			return 1;
		return -1;
	}

	public int getNode_id() {
		return node_id;
	}

	public void setNode_id(int node_id) {
		this.node_id = node_id;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public List<Integer> getAdj_ids() {
		return adj_ids;
	}

	public void setAdj_ids(List<Integer> adj_ids) {
		this.adj_ids = adj_ids;
	}

}
