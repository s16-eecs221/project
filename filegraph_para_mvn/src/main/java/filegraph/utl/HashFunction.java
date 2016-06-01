package filegraph.utl;

public class HashFunction {

	private int size;
	private int p;
	private int a;
	private int b;
	private int h;
	private int[] arrayA = new int[0];
	private int[] arrayB = new int[0];

	public HashFunction() {
		size = 1024;
		p = nextPrime(size);
		a = randomOdd(p);
		b = randomOdd(p);
		h = randomOdd(p);
	}

	public HashFunction(int s) {
		size = s;
		p = nextPrime(size);
		a = randomOdd(p);
		b = randomOdd(p);
		h = randomOdd(p);
	}

	/***
	 * get the first prime number which >=num
	 * 
	 * @param num
	 * @return
	 */
	private int nextPrime(int num) {
		int seed = num;
		if (num % 2 == 0)
			seed = num + 1;
		boolean find = false;
		while (!find) {
			find = true;
			for (int i = 3; i < seed / 2; i++) {
				if (seed % i == 0) {
					find = false;
					seed += 2;
					break;
				}
			}
		}
		return seed;
	}

	/***
	 * rendom odd num in (0, max)
	 * 
	 * @param max
	 * @return
	 */
	private int randomOdd(int max) {
		max = max / 2;
		int r = (int) (Math.random() * max);
		while (r <= 0) {
			r = (int) (Math.random() * max);
		}
		return r * 2 - 1;
	}

	/***
	 * hash for string
	 * 
	 * @param str
	 * @return
	 */
	public int hashString(String str) {
		int value = h;
		for (int i = 0; i < str.length(); i++)
			value = (value * a + str.charAt(i)) % p;
		value = (a * value + b) % p;
		value = value % size;
		return value;
	}

	private int[] extendArray(int[] array, int length, int max) {
		int len = array.length;
		int[] newArray = new int[length];
		for (int i = 0; i < len; i++)
			newArray[i] = array[i];
		for (int i = len; i < length; i++)
			newArray[i] = randomOdd(max);
		return newArray;
	}

	public int hashBytes(byte[] data) {
		int value = 0;
		int length = data.length;
		if (arrayA.length < length)
			arrayA = extendArray(arrayA, length, p);
		if (arrayB.length < length)
			arrayB = extendArray(arrayB, length, p);
		for (int i = 0; i < length; i++) {
			int v = arrayA[i] * data[i] + arrayB[i];
			while (v < 0)
				v += p;
			v = (v % p) % size;
			value = (value + v) % size;
		}
		return value;
	}

	public static void main(String args[]) {
		HashFunction hf = new HashFunction();
		String doc = "files";
		for (int i = 1; i < 10; i++) {
			String fileName = doc + "/file" + i + ".bin";
			LocalFile.create(fileName, 2, "M");
			byte[] data = LocalFile.readBinaryFile(fileName);
			System.out.println(hf.hashBytes(data) + "\t" + fileName);
		}
		for (int i = 1; i < 10; i++) {
			String fileName = doc + "/cpfile" + i + ".bin";
			String oldFile = doc + "/file" + i + ".bin";
			LocalFile.copyFile(oldFile, fileName);
			byte[] data = LocalFile.readBinaryFile(fileName);
			System.out.println(hf.hashBytes(data) + "\t" + fileName);
		}
	}
}
