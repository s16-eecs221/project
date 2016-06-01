package filegraph.obj;

import org.json.JSONObject;

public class FileGraphLeaf extends FileGraphNode {
	private int hash_value;

	public FileGraphLeaf() {
		super();
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
		fileNode_json.append("hash_value", this.hash_value);
		fileNode_json.append("adj_ids", this.adj_idsToJSONArray());
		return fileNode_json;
	}

	public int getHash_value() {
		return hash_value;
	}

	public void setHash_value(int hash_value) {
		this.hash_value = hash_value;
	}

}
