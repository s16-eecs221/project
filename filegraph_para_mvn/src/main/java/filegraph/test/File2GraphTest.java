package filegraph.test;

import java.util.ArrayList;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;

import filegraph.FileGraphIndexing;
import filegraph.FileGraphIndexingP2;
import filegraph.FileGraphIndexingP1;
import filegraph.obj.FileGraphRoot;
import filegraph.obj.FileHashProperty;
import filegraph.obj.LineGraph;
import filegraph.obj.LineGraphEdge;
import filegraph.obj.LineGraphVertex;
import filegraph.utl.ByteHashFunction;
import filegraph.utl.ByteHashFunctionP;
import filegraph.utl.FileGraphUtli;
import filegraph.utl.LocalFile;
import filegraph.utl.parallel.ParaTaskTest;
import filegraph.utl.parallel.ParaTaskTest2;

public class File2GraphTest {
	public void fileCreateTest(int total, int file_size, String file_dir, String file_name, String file_unit) {
		for (int i = 0; i < total; i++) {
			String fileName = file_dir + "/" + file_name + i + ".bin";
			LocalFile.create(fileName, file_size, file_unit);
		}
		for (int i = 0; i < total; i++) {
			String fileName = file_dir + "/cp" + file_name + i + ".bin";
			String oldFile = file_dir + "/" + file_name + i + ".bin";
			LocalFile.copyFile(oldFile, fileName);
		}
	}

	public void byteHashTest() {
		ByteHashFunction hf = new ByteHashFunction(70, 50);
		String doc = "files";
		for (int i = 0; i < 100; i++) {
			String fileName = doc + "/file" + i + ".bin";
			byte[] data = LocalFile.readBinaryFile(fileName);
			System.out.println(hf.hashBytes(data, 0, data.length) + "\t" + fileName);
		}
		for (int i = 0; i < 100; i++) {
			String fileName = doc + "/cpfile" + i + ".bin";
			byte[] data = LocalFile.readBinaryFile(fileName);
			System.out.println(hf.hashBytes(data, 0, data.length) + "\t" + fileName);
		}
	}

	public void fileHashPropertyTest() {
		FileHashProperty hashProp = new FileHashProperty(1024, 1024);
		int[] array = hashProp.getHash_arrayA();
		System.out.println("Origin int array:");
		for (int i = 0; i < array.length; i++)
			System.out.print(array[i] + " ");
		System.out.println("\nOrigin converted byte array:");
		byte[] encode = FileGraphUtli.int2byte(array);
		for (int i = 0; i < encode.length; i++)
			System.out.print(encode[i] + " ");
		String hash_arrayA_str = Base64.encodeBase64String(encode);
		System.out.println("\nBase64 string, length=" + hash_arrayA_str.length());
		System.out.println(hash_arrayA_str);
		System.out.println("Decoded byte array:");
		byte[] decode = Base64.decodeBase64(hash_arrayA_str);
		for (int i = 0; i < decode.length; i++)
			System.out.print(decode[i] + " ");
		System.out.println("\nConverted int array:");
		int[] de_array = FileGraphUtli.byte2int(decode);
		for (int i = 0; i < de_array.length; i++)
			System.out.print(de_array[i] + " ");
		boolean ifsame = true;
		for (int i = 0; i < array.length; i++)
			if (array[i] != de_array[i])
				ifsame = false;
		System.out.println("\nIf decode correct: " + ifsame);
		System.out.println("Hash property json:\n" + hashProp.toString());
	}

	public void lineGraphTest() {
		System.out.println("-----------------------------String List Remove Duplication-----------------------------");
		ArrayList<String> str_list = new ArrayList<String>();
		str_list.add("aaa");
		str_list.add("bbb");
		str_list.add("aaa");
		str_list.add("ccc");
		System.out.println("String list bfter remove duplication:" + str_list);
		str_list = new ArrayList<String>(new HashSet<String>(str_list));
		System.out.println("String list after remove duplication:" + str_list);
		System.out.println("-----------------------------Line Graph Vertex Test-----------------------------");
		LineGraphVertex v1 = new LineGraphVertex(1, 2);
		LineGraphVertex v2 = new LineGraphVertex(1, 2);
		LineGraphVertex v3 = new LineGraphVertex(1, 3);
		LineGraphVertex v4 = new LineGraphVertex(0, 3);

		Set<LineGraphVertex> v_set = new HashSet<LineGraphVertex>();
		List<LineGraphVertex> v_list = new ArrayList<LineGraphVertex>();
		System.out.println("v1-" + v1.toString() + " equals to v2-" + v2.toString() + ":" + v1.equals(v2));
		System.out.println("v1-" + v1.toString() + " equals to v3-" + v3.toString() + ":" + v1.equals(v3));
		System.out.println("v1-" + v1.toString() + " compare to v2-" + v2.toString() + ":" + v1.compareTo(v2));
		System.out.println("v1-" + v1.toString() + " compare to v3-" + v3.toString() + ":" + v1.compareTo(v3));
		System.out.println("v1-" + v1.toString() + " compare to v4-" + v4.toString() + ":" + v1.compareTo(v4));

		v_set.add(v1);
		v_set.add(v2);
		v_set.add(v3);
		v_set.add(v4);
		v_list.addAll(v_set);
		System.out.println("V list before sort:" + v_list);
		Collections.sort(v_list);
		System.out.println("V list after sort:" + v_list);

		LineGraphEdge e1 = new LineGraphEdge(v1, v3);
		LineGraphEdge e2 = new LineGraphEdge(v1, v3);
		LineGraphEdge e3 = new LineGraphEdge(v1, v4);
		LineGraphEdge e4 = new LineGraphEdge(v3, v4);
		System.out.println("e1-" + e1.toString() + " equals to e2-" + e2.toString() + ":" + e1.equals(e2));
		System.out.println("e1-" + e1.toString() + " equals to e3-" + e2.toString() + ":" + e1.equals(e3));
		System.out.println("e1-" + e1.toString() + " compare to e2-" + e2.toString() + ":" + e1.compareTo(e2));
		System.out.println("e1-" + e1.toString() + " compare to e3-" + e3.toString() + ":" + e1.compareTo(e3));
		System.out.println("e4-" + e4.toString() + " compare to e1-" + e1.toString() + ":" + e4.compareTo(e1));

		Set<LineGraphEdge> e_set = new HashSet<LineGraphEdge>();
		List<LineGraphEdge> e_list = new ArrayList<LineGraphEdge>();
		e_set.add(e1);
		e_set.add(e2);
		e_set.add(e3);
		e_set.add(e4);
		e_list.addAll(e_set);
		System.out.println("E list before sort:" + e_list);
		Collections.sort(e_list);
		System.out.println("e list after sort:" + e_list);
	}

	public void file2GraphSimpleTest() {
		long startTime = System.currentTimeMillis();
		FileGraphIndexing f2g = new FileGraphIndexing();
		FileGraphRoot root = f2g.filechunks2graph("files", "file", 0, 99);
		System.out.println(root.toString());
		LineGraph linegraph = f2g.Graph2LineGraph(root);
		String sigature = linegraph.getEdges().toString();
		System.out.println(sigature);

		FileGraphRoot rootcp = f2g.filechunks2graph("files", "cpfile", 0, 99);
		System.out.println(rootcp.toString());
		LineGraph linegraphcp = f2g.Graph2LineGraph(rootcp);
		String sigaturecp = linegraphcp.getEdges().toString();
		System.out.println(sigaturecp);

		System.out.println(sigature.equals(sigaturecp));

		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(totalTime);
	}

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
	}

	public void joinforkTest() {
		Runtime runtime = Runtime.getRuntime();
		int pNum = runtime.availableProcessors();
		System.out.println("Number of processors available to the Java Virtual Machine: " + pNum);
		byte[] data = new byte[10];
		ParaTaskTest left = new ParaTaskTest("left", 0, data.length / 2, data);
		ParaTaskTest right = new ParaTaskTest("right", data.length / 2, data.length, data);
		left.fork();
		right.fork();
		left.join();
		right.join();
		System.out.println("Result data:");
		for (int i = 0; i < data.length; i++)
			System.out.print(data[i] + " ");
	}

	public void joinforkTest2() {
		try {
			Runtime runtime = Runtime.getRuntime();
			int pNum = runtime.availableProcessors();
			System.out.println("Number of processors available to the Java Virtual Machine: " + pNum);
			ForkJoinPool forkJoinPool = new ForkJoinPool();
			long startTime = System.currentTimeMillis();
			for (int i = 0; i < 16; i++)
				forkJoinPool.submit(new ParaTaskTest2(String.valueOf(i)));

			forkJoinPool.shutdown();
			forkJoinPool.awaitTermination(1000, TimeUnit.SECONDS);

			long endTime = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println("Total time: " + totalTime + "ms");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void singlefile2GraphTest(String file_dir, String filename) {
		long startTime = System.currentTimeMillis();
		FileGraphIndexing f2g = new FileGraphIndexing();
		FileGraphRoot root = f2g.singlefile2graph(file_dir, filename + "0", 100);
		System.out.println(root.toString());
		LineGraph linegraph = f2g.Graph2LineGraph(root);
		String sigature = linegraph.getEdges().toString();
		System.out.println(sigature);

		FileGraphRoot rootcp = f2g.singlefile2graph(file_dir, "cp" + filename + "0", 100);
		System.out.println(rootcp.toString());
		LineGraph linegraphcp = f2g.Graph2LineGraph(rootcp);
		String sigaturecp = linegraphcp.getEdges().toString();
		System.out.println(sigaturecp);

		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Evaluation time:" + totalTime + "ms");
		System.out.println("If result same: " + sigature.equals(sigaturecp));
	}

	public void singlefile2GraphParaTest(String file_dir, String filename, int chunk_size) {
		// System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		Runtime runtime = Runtime.getRuntime();
		int pNum = runtime.availableProcessors();
		System.out.println("Number of processors available to the Java Virtual Machine: " + pNum);
		FileHashProperty hashProp = new FileHashProperty(chunk_size / 2, chunk_size / 4);
		// seq
		long startTime = System.currentTimeMillis();
		FileGraphIndexing f2g = new FileGraphIndexing(new ByteHashFunction(hashProp));
		FileGraphRoot root = f2g.singlefile2graph(file_dir, filename + "0", chunk_size);
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
		FileGraphRoot rootcp1 = f2gp1.singlefile2graphP(file_dir, filename + "0", chunk_size);
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
		FileGraphRoot rootcp2 = f2gp2.singlefile2graphP(file_dir, filename + "0", chunk_size);
		// System.out.println(rootcp2.toString());
		LineGraph linegraphcp2 = f2gp2.Graph2LineGraphP(rootcp2);
		String sigaturecp2 = linegraphcp2.getEdges().toString();
		// System.out.println(sigaturecp2);

		endTime = System.currentTimeMillis();
		totalTime = endTime - startTime;
		System.out.println("Parallet time on bytehash and filechunk: " + totalTime + "ms");

		// result compare
		System.out.println("If result same: " + sigature.equals(sigaturecp2));

	}

	public static void main(String args[]) {
		File2GraphTest f2gTest = new File2GraphTest();
		f2gTest.fileCreateTest(128, 1, "test_files/files-128m", "file", "M");
		// f2gTest.byteHashTest();
		// f2gTest.fileHashPropertyTest();
		// f2gTest.lineGraphTest();
		// f2gTest.joinforkTest();
		// f2gTest.joinforkTest2();
		f2gTest.file2GraphParaSimpleTest("test_files/files-128m", "file", 128);
		// f2gTest.singlefile2GraphParaTest("single_files", "1G", 1024);
	}
}
