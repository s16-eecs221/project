package filegraph.utl;

public class FileGraphUtli {
	public static byte[] int2byte(int[] src) {
		int srcLength = src.length;
		byte[] dst = new byte[srcLength << 2];

		for (int i = 0; i < srcLength; i++) {
			int x = src[i];
			int j = i << 2;
			dst[j++] = (byte) ((x >> 0) & 0xff);
			dst[j++] = (byte) ((x >> 8) & 0xff);
			dst[j++] = (byte) ((x >> 16) & 0xff);
			dst[j++] = (byte) ((x >> 24) & 0xff);
		}
		return dst;
	}

	public static int[] byte2int(byte src[]) {
		int srcLength = src.length;
		int[] dst = new int[srcLength >> 2];

		for (int i = 0, j = 0; i < dst.length; i++) {
			dst[i] = 0;
			dst[i] = dst[i] | (0x000000FF & src[j++]);
			dst[i] = dst[i] | ((0x000000FF & src[j++]) << 8);
			dst[i] = dst[i] | ((0x000000FF & src[j++]) << 16);
			dst[i] = dst[i] | ((0x000000FF & src[j++]) << 24);
		}

		return dst;
		
		/**
		 * int i;
		int v;
		int idxOrg;
		int maxDst;
		//
		maxDst = maxOrg / 4;
		//
		if (arrayDst == null)
			return 0;
		if (arrayOrg == null)
			return 0;
		if (arrayDst.length < maxDst)
			return 0;
		if (arrayOrg.length < maxOrg)
			return 0;
		//
		idxOrg = 0;
		for (i = 0; i < maxDst; i++) {
			arrayDst[i] = 0;
			//
			v = 0x000000FF & arrayOrg[idxOrg];
			arrayDst[i] = arrayDst[i] | v;
			idxOrg++;
			//
			v = 0x000000FF & arrayOrg[idxOrg];
			arrayDst[i] = arrayDst[i] | (v << 8);
			idxOrg++;
			//
			v = 0x000000FF & arrayOrg[idxOrg];
			arrayDst[i] = arrayDst[i] | (v << 16);
			idxOrg++;
			//
			v = 0x000000FF & arrayOrg[idxOrg];
			arrayDst[i] = arrayDst[i] | (v << 24);
			idxOrg++;
		}
		//
		return maxDst;
		 */
	}

	public static int randomOdd(int max) {
		max = max / 2;
		int r = (int) (Math.random() * max);
		while (r <= 0) {
			r = (int) (Math.random() * max);
		}
		return r * 2 - 1;
	}

	/***
	 * get the first prime number which >=num
	 * 
	 * @param num
	 * @return
	 */
	public static int nextPrime(int num) {
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

	public static int[] initHashArray(int length, int max) {
		int[] newArray = new int[length];
		for (int i = 0; i < length; i++)
			newArray[i] = FileGraphUtli.randomOdd(max);
		return newArray;
	}
}
