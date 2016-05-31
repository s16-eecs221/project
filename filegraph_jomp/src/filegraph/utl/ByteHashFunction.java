package filegraph.utl;

import filegraph.obj.FileHashProperty;

public class ByteHashFunction {

	private FileHashProperty hashProp;

	public ByteHashFunction(FileHashProperty hashProp) {
		this.hashProp = hashProp;
	}

	public ByteHashFunction(int array_length) {
		hashProp = new FileHashProperty(array_length);

	}

	public ByteHashFunction(int size, int array_length) {
		hashProp = new FileHashProperty(size, array_length);
	}

	public int hashBytes(byte[] data) {
		int value = 0;
		int length = hashProp.getArray_length();
		int[] arrayA = hashProp.getHash_arrayA();
		int[] arrayB = hashProp.getHash_arrayB();
		int prime = hashProp.getPrime();
		int hash_size = hashProp.getHash_size();

		for (int i = 0; i < length; i++) {
			int v = arrayA[i] * data[i] + arrayB[i];
			while (v < 0)
				v += prime;
			v = (v % prime) % hash_size;
			value = (value + v) % hash_size;
		}
		return value;
	}

	public FileHashProperty getHashProp() {
		return hashProp;
	}
}
