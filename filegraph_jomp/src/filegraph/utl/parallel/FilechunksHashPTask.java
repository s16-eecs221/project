package filegraph.utl.parallel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveAction;

import filegraph.obj.FileGraphLeaf;
import filegraph.obj.FileGraphRoot;
import filegraph.utl.ByteHashFunctionP;
import filegraph.utl.LocalFile;

public class FilechunksHashPTask extends RecursiveAction {
	private static final long serialVersionUID = 5233398939024406654L;
	private int start;
	private int end;
	private int min;
	private int max;
	private int availP;
	private ByteHashFunctionP bytehfP;
	private Map<Integer, List<Integer>> hash_values;
	private FileGraphRoot root;
	private String chunck_dir;
	private String file_name;

	public FilechunksHashPTask(int start, int end, int min, int max, int availP, ByteHashFunctionP bytehfP,
			Map<Integer, List<Integer>> hash_values, FileGraphRoot root, String chunck_dir, String file_name) {
		this.start = start;
		this.end = end;
		this.min = min;
		this.max = max;
		this.availP = availP;
		this.bytehfP = bytehfP;
		this.hash_values = hash_values;
		this.root = root;
		this.chunck_dir = chunck_dir;
		this.file_name = file_name;
	}

	@Override
	protected void compute() {
		if (availP < 4) {
			for (int i = start; i < end; i++) {
				FileGraphLeaf leaf = new FileGraphLeaf();
				leaf.setNode_id(i);
				if (i < max - 1)
					leaf.getAdj_ids().add(i + 1);
				if (i > min)
					leaf.getAdj_ids().add(i - 1);
				int hash_value = bytehfP.hashBytes(LocalFile.readBinaryFile(chunck_dir + "/" + file_name + i + ".bin"),
						availP);
				// System.out.println(file_name + i + " adjs:" +
				// leaf.getAdj_ids());
				leaf.setHash_value(hash_value);
				if (!hash_values.containsKey(hash_value))
					hash_values.put(hash_value, new ArrayList<Integer>());
				hash_values.get(hash_value).add(i);
				root.getChild_nodes().add(leaf);
			}
		} else {
			int middle = (start + end) / 2;
			new FilechunksHashPTask(start, middle, min, max, availP / 2, bytehfP, hash_values, root, chunck_dir,
					file_name).fork();
			new FilechunksHashPTask(middle, end, min, max, availP / 2, bytehfP, hash_values, root, chunck_dir,
					file_name).fork();
		}
	}

}
