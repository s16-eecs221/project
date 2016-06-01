package filegraph.obj;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

import filegraph.utl.FileGraphUtli;

public class FileHashProperty {
	private int hash_size;
	private int prime;
	private int array_length;
	private int[] hash_arrayA;
	private int[] hash_arrayB;

	public FileHashProperty(int array_length) {
		this.hash_size = 1024;
		this.array_length = array_length;
		init();
	}

	public FileHashProperty(int size, int array_length) {
		this.hash_size = size;
		this.array_length = array_length;
		init();
	}

	public FileHashProperty(int size, int prime, int array_length, int[] arrayA, int[] arrayB) {
		this.hash_size = size;
		this.prime = prime;
		this.array_length = array_length;
		this.hash_arrayA = arrayA;
		this.hash_arrayB = arrayB;
	}

	private void init() {
		this.prime = FileGraphUtli.nextPrime(this.hash_size);
		this.hash_arrayA = FileGraphUtli.initHashArray(this.array_length, this.prime);
		this.hash_arrayB = FileGraphUtli.initHashArray(this.array_length, this.prime);
	}

	@Override
	public String toString() {
		JSONObject file_hash_prop = new JSONObject();
		file_hash_prop.put("hash_size", this.hash_size);
		file_hash_prop.put("prime", this.prime);
		file_hash_prop.put("array_length", this.array_length);
		String hash_arrayA_str = Base64.encodeBase64String(FileGraphUtli.int2byte(hash_arrayA));
		String hash_arrayB_str = Base64.encodeBase64String(FileGraphUtli.int2byte(hash_arrayB));
		file_hash_prop.put("hash_arrayA", hash_arrayA_str);
		file_hash_prop.put("hash_arrayB", hash_arrayB_str);
		return file_hash_prop.toString();
	}

	public int getHash_size() {
		return hash_size;
	}

	public int getPrime() {
		return prime;
	}

	public int getArray_length() {
		return array_length;
	}

	public int[] getHash_arrayA() {
		return hash_arrayA;
	}

	public int[] getHash_arrayB() {
		return hash_arrayB;
	}

}
