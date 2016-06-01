package filegraph.utl.parallel;

import java.util.concurrent.RecursiveTask;

import filegraph.obj.FileHashProperty;

public class ByteHashFuctionParaTask extends RecursiveTask<Integer> {
	private static final long serialVersionUID = -4711392742083339418L;
	private static final int THRESHOLD = 1024;
	private byte[] data;
	private int start;
	private int end;
	private int availP;
	private FileHashProperty hashProp;

	public ByteHashFuctionParaTask(int start, int end, byte[] data, int availP, FileHashProperty hashProp) {
		this.start = start;
		this.end = end;
		this.data = data;
		this.availP = availP;
		this.hashProp = hashProp;
	}

	@Override
	protected Integer compute() {
		if (availP < 2 || end - start < THRESHOLD) {
			int value = 0;
			int array_length = hashProp.getArray_length();
			int[] arrayA = hashProp.getHash_arrayA();
			int[] arrayB = hashProp.getHash_arrayB();
			int prime = hashProp.getPrime();
			int hash_size = hashProp.getHash_size();

			for (int i = start; i < end; i++) {
				int v = arrayA[i % array_length] * data[i] + arrayB[i % array_length];
				while (v < 0)
					v += prime;
				v = (v % prime) % hash_size;
				value = (value + v) % hash_size;
			}

			return value;
		} else {
			int middle = (start + end) / 2;
			ByteHashFuctionParaTask left = new ByteHashFuctionParaTask(start, middle, data, availP / 2, hashProp);
			ByteHashFuctionParaTask right = new ByteHashFuctionParaTask(middle, end, data, availP / 2, hashProp);
			left.fork();
			right.fork();

			int value = (left.join() + right.join()) % hashProp.getHash_size();

			return value;
		}
	}
}
