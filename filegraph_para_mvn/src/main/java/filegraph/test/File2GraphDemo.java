package filegraph.test;

import java.io.FileOutputStream;
import java.io.IOException;

import filegraph.FileGraphIndexing;
import filegraph.FileGraphIndexingP2;
import filegraph.FileGraphIndexingP1;
import filegraph.obj.FileGraphRoot;
import filegraph.obj.FileHashProperty;
import filegraph.obj.LineGraph;

import filegraph.utl.ByteHashFunction;
import filegraph.utl.ByteHashFunctionP;

public class File2GraphDemo {
	public void file2GraphParaSimpleTest(String file_dir, String filename, int files) {
		// System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		Runtime runtime = Runtime.getRuntime();
		int pNum = runtime.availableProcessors();
		System.out.println("Number of processors available to the Java Virtual Machine: " + pNum);
		FileHashProperty hashProp = new FileHashProperty(files / 2, files / 4);
		// seq
		long startTime = System.currentTimeMillis();
		FileGraphIndexing f2g = new FileGraphIndexing(new ByteHashFunction(hashProp));
		FileGraphRoot root = f2g.filechunks2graph(file_dir, filename, 0, files - 1);
		// System.out.println(root.toString());
		LineGraph linegraph = f2g.Graph2LineGraph(root);
		String sigature = linegraph.getEdges().toString();
		// System.out.println(sigature);
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Sequential time: " + totalTime + "ms");
		// System.out.println(root.getChild_nodes());

		// para 1 bytehash
		startTime = System.currentTimeMillis();
		FileGraphIndexingP1 f2gp1 = new FileGraphIndexingP1(new ByteHashFunctionP(hashProp));
		FileGraphRoot rootcp1 = f2gp1.filechunks2graphP(file_dir, "cp" + filename, 0, files - 1);
		// System.out.println(rootcp1.toString());
		LineGraph linegraphcp1 = f2gp1.Graph2LineGraphP(rootcp1);
		String sigaturecp1 = linegraphcp1.getEdges().toString();
		// System.out.println(sigaturecp1);

		endTime = System.currentTimeMillis();
		totalTime = endTime - startTime;
		System.out.println("Parallet time on bytehash: " + totalTime + "ms");

		// result compare
		System.out.println("If result same: " + sigature.equals(sigaturecp1));

		// para 2 filechunk
		startTime = System.currentTimeMillis();
		FileGraphIndexingP2 f2gp2 = new FileGraphIndexingP2(new ByteHashFunctionP(hashProp));
		FileGraphRoot rootcp2 = f2gp2.filechunks2graphP(file_dir, "cp" + filename, 0, files - 1);
		// System.out.println(rootcp2.toString());
		LineGraph linegraphcp2 = f2gp2.Graph2LineGraphP(rootcp2);
		String sigaturecp2 = linegraphcp2.getEdges().toString();
		// System.out.println(sigaturecp2);

		endTime = System.currentTimeMillis();
		totalTime = endTime - startTime;
		System.out.println("Parallet time on bytehash and filechunk: " + totalTime + "ms");

		// result compare
		System.out.println("If result same: " + sigature.equals(sigaturecp2));

		// write adj info to file
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream("result/adj_info.txt");
			byte[] adj_info = root.getChild_nodes().toString().getBytes();
			fos.write(adj_info);
			fos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String args[]) {
		File2GraphDemo f2gTest = new File2GraphDemo();
		f2gTest.file2GraphParaSimpleTest("files", "file", 50);

	}
}
