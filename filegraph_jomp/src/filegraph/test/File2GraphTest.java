package filegraph.test;

import java.util.ArrayList;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;

import filegraph.FileGraphIndexing;
import filegraph.obj.FileGraphRoot;
import filegraph.obj.FileHashProperty;
import filegraph.obj.LineGraph;
import filegraph.obj.LineGraphEdge;
import filegraph.obj.LineGraphVertex;
import filegraph.utl.ByteHashFunction;
import filegraph.utl.FileGraphUtli;
import filegraph.utl.LocalFile;

public class File2GraphTest {
	public void fileCreateTest(int total, int file_size, String file_unit) {
		String doc = "files";
		for (int i = 0; i < total; i++) {
			String fileName = doc + "/file" + i + ".bin";
			LocalFile.create(fileName, file_size, file_unit);
		}
		for (int i = 0; i < total; i++) {
			String fileName = doc + "/cpfile" + i + ".bin";
			String oldFile = doc + "/file" + i + ".bin";
			LocalFile.copyFile(oldFile, fileName);

		}
	}

	public void byteHashTest() {
		ByteHashFunction hf = new ByteHashFunction(70, 50);
		String doc = "files";
		for (int i = 0; i < 100; i++) {
			String fileName = doc + "/file" + i + ".bin";
			byte[] data = LocalFile.readBinaryFile(fileName);
			System.out.println(hf.hashBytes(data) + "\t" + fileName);
		}
		for (int i = 0; i < 100; i++) {
			String fileName = doc + "/cpfile" + i + ".bin";
			byte[] data = LocalFile.readBinaryFile(fileName);
			System.out.println(hf.hashBytes(data) + "\t" + fileName);
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

		LineGraphEdge e1 = new LineGraphEdge(v1, v2);
		LineGraphEdge e2 = new LineGraphEdge(v1, v2);
		// System.out.println(e1.compareTo(e2));
	}

	public void file2GraphSimpleTest() {
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
	}

	public static void main(String args[]) {
		File2GraphTest f2gTest = new File2GraphTest();
		f2gTest.fileCreateTest(100, 2, "M");
		f2gTest.byteHashTest();
		f2gTest.fileHashPropertyTest();
		f2gTest.lineGraphTest();
		f2gTest.file2GraphSimpleTest();
	}
}
