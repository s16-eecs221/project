package filegraph.utl.parallel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveAction;

import filegraph.obj.FileGraphLeaf;
import filegraph.obj.FileGraphRoot;
import filegraph.utl.ByteHashFunctionP;

public class SingleFileHashPTask extends RecursiveAction {
	private static final long serialVersionUID = 5233398939024406654L;
	private int start_chunk_num;
	private int end_chunk_num;
	private int chunk_num;
	private int chunk_size;
	private int availP;
	private ByteHashFunctionP bytehfP;
	private Map<Integer, List<Integer>> hash_values;
	private FileGraphRoot root;
	private byte[] file_Data;

	public SingleFileHashPTask(int start_chunk_num, int end_chunk_num, int chunk_num, int chunk_size, int availP,
			ByteHashFunctionP bytehfP, Map<Integer, List<Integer>> hash_values, FileGraphRoot root, byte[] file_Data) {
		this.start_chunk_num = start_chunk_num;
		this.end_chunk_num = end_chunk_num;
		this.chunk_num = chunk_num;
		this.chunk_size = chunk_size;
		this.availP = availP;
		this.bytehfP = bytehfP;
		this.hash_values = hash_values;
		this.root = root;
		this.file_Data = file_Data;
	}

	@Override
	protected void compute() {
		if (availP < 4) {
			for (int i = start_chunk_num, start_local = start_chunk_num * chunk_size; i < end_chunk_num; i++) {
				FileGraphLeaf leaf = new FileGraphLeaf();
				leaf.setNode_id(i);
				if (i < chunk_num - 1)
					leaf.getAdj_ids().add(i + 1);
				if (i > 0)
					leaf.getAdj_ids().add(i - 1);
				int hash_value;
				if (i < chunk_num - 1) {
					hash_value = bytehfP.hashBytes(file_Data, start_local, start_local + chunk_size, availP);
					start_local += chunk_size;
				} else
					hash_value = bytehfP.hashBytes(file_Data, start_local, file_Data.length, availP);
				// System.out.println(file_name + i + " adjs:" +
				// leaf.getAdj_ids());
				leaf.setHash_value(hash_value);
				if (!hash_values.containsKey(hash_value))
					hash_values.put(hash_value, new ArrayList<Integer>());
				hash_values.get(hash_value).add(i);
				root.getChild_nodes().add(leaf);
			}
		} else {
			int middle = (start_chunk_num + end_chunk_num) / 2;
			new SingleFileHashPTask(start_chunk_num, middle, chunk_num, chunk_size, availP / 2, bytehfP, hash_values,
					root, file_Data).fork();
			new SingleFileHashPTask(middle, end_chunk_num, chunk_num, chunk_size, availP / 2, bytehfP, hash_values,
					root, file_Data).fork();
		}
	}

}
