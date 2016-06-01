package filegraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import filegraph.obj.FileGraphNode;
import filegraph.obj.FileGraphRoot;
import filegraph.obj.LineGraph;
import filegraph.obj.LineGraphEdge;
import filegraph.obj.LineGraphVertex;
import filegraph.utl.ByteHashFunctionP;

import filegraph.utl.parallel.FilechunksHashPTask;

public class FileGraphIndexingP {
	private ByteHashFunctionP bytehfP;

	public FileGraphIndexingP() {
		this.bytehfP = new ByteHashFunctionP(50, 32);
	}

	public FileGraphIndexingP(ByteHashFunctionP bytehf) {
		this.bytehfP = bytehf;
	}

	public FileGraphRoot filechunks2graphP(String chunck_dir, String file_name, int start_chunck, int end_chunk) {
		Map<Integer, List<Integer>> hash_values = new HashMap<Integer, List<Integer>>();
		// create file graph structure
		FileGraphRoot root = new FileGraphRoot();
		Runtime runtime = Runtime.getRuntime();
		int pNum = runtime.availableProcessors();

		try {
			ForkJoinPool forkJoinPool = new ForkJoinPool();

			forkJoinPool.submit(new FilechunksHashPTask(start_chunck, end_chunk, start_chunck, end_chunk, pNum, bytehfP,
					hash_values, root, chunck_dir, file_name));

			forkJoinPool.shutdown();
			forkJoinPool.awaitTermination(1000, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Collections.sort(root.getChild_nodes());

		// for every hash_value, add adj
		Iterator<Entry<Integer, List<Integer>>> iter = hash_values.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<Integer, List<Integer>> entry = iter.next();
			List<Integer> ids = entry.getValue();
			// System.out.println("hash:" + entry.getKey() + ", list:" + ids);
			for (Integer id : ids) {
				List<Integer> adjs = root.getChild_nodes().get(id).getAdj_ids();
				adjs.addAll(ids);
				adjs = new ArrayList<Integer>(new HashSet<Integer>(adjs));
				adjs.remove(id);
				root.getChild_nodes().get(id).setAdj_ids(adjs);
			}
		}

		root.setHashProp(bytehfP.getHashProp());
		/**
		 * for (int i = 0; i < root.getChild_nodes().size(); i++) {
		 * System.out.println("Node " +
		 * root.getChild_nodes().get(i).getNode_id() + " adjs:" +
		 * root.getChild_nodes().get(i).getAdj_ids()); }
		 **/
		return root;
	}

	public LineGraph Graph2LineGraphP(FileGraphRoot root) {
		LineGraph lg = new LineGraph();
		for (FileGraphNode node : root.getChild_nodes()) {
			int node_id = node.getNode_id();
			List<LineGraphVertex> ith_vertex_list = new ArrayList<LineGraphVertex>();
			for (Integer adj : node.getAdj_ids()) {
				ith_vertex_list.add(new LineGraphVertex(node_id, adj));
			}
			Collections.sort(ith_vertex_list);
			lg.getVertex().addAll(ith_vertex_list);
			for (int i = 0; i < ith_vertex_list.size() - 1; i++)
				for (int j = i + 1; j < ith_vertex_list.size(); j++)
					lg.getEdges().add(new LineGraphEdge(ith_vertex_list.get(i), ith_vertex_list.get(j)));
		}

		// remove duplicate
		List<LineGraphVertex> v_list = lg.getVertex();
		v_list = new ArrayList<LineGraphVertex>(new HashSet<LineGraphVertex>(v_list));
		Collections.sort(v_list);
		List<LineGraphEdge> e_list = lg.getEdges();
		e_list = new ArrayList<LineGraphEdge>(new HashSet<LineGraphEdge>(e_list));
		Collections.sort(e_list);
		lg.setVertex(v_list);
		lg.setEdges(e_list);
		return lg;
	}

	public ByteHashFunctionP getBytehf() {
		return bytehfP;
	}

	public void setBytehf(ByteHashFunctionP bytehfP) {
		this.bytehfP = bytehfP;
	}
}
