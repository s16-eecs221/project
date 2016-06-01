package filegraph.utl;

import filegraph.obj.FileHashProperty;
import filegraph.utl.parallel.ByteHashFuctionParaTask;

public class ByteHashFunctionP {

	private FileHashProperty hashProp;

	public ByteHashFunctionP(FileHashProperty hashProp) {
		this.hashProp = hashProp;
	}

	public ByteHashFunctionP(int array_length) {
		hashProp = new FileHashProperty(array_length);

	}

	public ByteHashFunctionP(int size, int array_length) {
		hashProp = new FileHashProperty(size, array_length);
	}

	public int hashBytes(byte[] data, int pNum) {
		int value = 0;
		if (pNum > 2) {
			ByteHashFuctionParaTask left = new ByteHashFuctionParaTask(0, data.length / 2, data, pNum / 2, hashProp);
			ByteHashFuctionParaTask right = new ByteHashFuctionParaTask(data.length / 2, data.length, data, pNum / 2,
					hashProp);
			left.fork();
			right.fork();
			value = (left.join() + right.join()) % hashProp.getHash_size();
		} else {
			int array_length = hashProp.getArray_length();
			int length = data.length;
			int[] arrayA = hashProp.getHash_arrayA();
			int[] arrayB = hashProp.getHash_arrayB();
			int prime = hashProp.getPrime();
			int hash_size = hashProp.getHash_size();

			for (int i = 0; i < length; i++) {
				int v = arrayA[i % array_length] * data[i] + arrayB[i % array_length];
				while (v < 0)
					v += prime;
				v = (v % prime) % hash_size;
				value = (value + v) % hash_size;
			}
		}
		return value;
	}

	public FileHashProperty getHashProp() {
		return hashProp;
	}
}
