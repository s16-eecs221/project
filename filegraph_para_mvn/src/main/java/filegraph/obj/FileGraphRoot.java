package filegraph.obj;

import org.json.JSONObject;

public class FileGraphRoot extends FileGraphNonLeaf {
	private int levels;
	private int max_level_child_size;
	private FileHashProperty hashProp;

	public FileGraphRoot() {
		super();
	}

	@Override
	public String toString() {
		JSONObject fileNode_root = new JSONObject();
		fileNode_root.append("tags", this.tags);
		fileNode_root.append("graph_str", this.graph_str);
		fileNode_root.append("levels", this.levels);
		fileNode_root.append("max_level_child_size", this.max_level_child_size);
		fileNode_root.append("child_nodes", this.childsToJSONArray());
		fileNode_root.append("hashProp", hashProp.toString());
		return fileNode_root.toString();
	}

	public int getLevels() {
		return levels;
	}

	public void setLevels(int levels) {
		this.levels = levels;
	}

	public int getMax_level_child_size() {
		return max_level_child_size;
	}

	public void setMax_level_child_size(int max_level_child_size) {
		this.max_level_child_size = max_level_child_size;
	}

	public FileHashProperty getHashProp() {
		return hashProp;
	}

	public void setHashProp(FileHashProperty hashProp) {
		this.hashProp = hashProp;
	}
}
