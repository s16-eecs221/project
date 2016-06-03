package filegraph.test;

import filegraph.utl.LocalFile;

public class File2GraphCreatFiles {
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

	public static void main(String args[]) {
		File2GraphCreatFiles f2gTest = new File2GraphCreatFiles();
		f2gTest.fileCreateTest(50, 4, "files", "file", "M");
	}
}
