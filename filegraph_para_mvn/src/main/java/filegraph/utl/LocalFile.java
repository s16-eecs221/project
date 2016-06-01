package filegraph.utl;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LocalFile {
	/***
	 * create file with size of specific length
	 * @param fileName
	 * @param length
	 * @param unit K or M or G
	 */
	public static void create(String fileName, int length, String unit) {   
		int len = 0;
		if(unit.equals("K")) len = length*1024;
		else if(unit.equals("M")) len = length*1024*1024;
		else if(unit.equals("G")) len = length*1024*1024*1024;
		else
		{
			System.out.println("Error: Unit of file size is 'K', 'M', or 'G'.");
			return;
		}
		byte[] data = new byte[len];  
		for(int i=0;i<len;i++)
			data[i] = (byte)(Math.random()*Math.pow(2, 8));
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fileName);
			fos.write(data);  
		    fos.flush();   
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  finally {
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
					e.printStackTrace();
			}
		}
} 
	
	/***
	 * delete file
	 * @param fileName
	 * @return
	 */
	public static boolean deleteFile(String fileName){     
		File file = new File(fileName);     
		if(!file.exists()){     
		  //  System.out.println("Error: "+fileName+" doesn't exist");     
		    return false;     
		}
		if(!file.isFile()) {
			System.out.println("Error: "+fileName+" is a directory");     
			return false;
		}
		file.delete();         
		return true; 
	} 
	
	/***
	 * delete files in directory
	 * @param dirName
	 * @return
	 */
	public static boolean deleteDir(String dirName) {
		File dir = new File(dirName);
	    if (dir.isDirectory())  
	    {  
	        File[] listFiles = dir.listFiles();  
	        for (int i = 0; i < listFiles.length; i++)  {
	        	deleteFile(listFiles[i].toString());
	        }  
	        return true;
	    }
	    System.out.println("Error: "+dirName+" is not a directory");   
	    return false;
	}
	
	/***
	 * read file 
	 * @param fileName
	 * @return String 
	 */
	public static String readFile(String fileName) {
		 String str="";
		 BufferedReader br = null;
		 try {
				String currentLine;
				br = new BufferedReader(new FileReader(fileName));
				while ((currentLine = br.readLine()) != null) {
					str += currentLine+"\n";
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (br != null)br.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		 return str;
	 }
	 
	/***
	 * write file
	 * @param fileName
	 * @param str
	 */
	 public static void writeFile(String fileName, String str) {
		 BufferedWriter bufferWritter=null;
		 try{
			FileWriter fileWritter = new FileWriter(fileName,true);
			bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(str);
		 	}catch(IOException e){
				e.printStackTrace();
			} finally {
				try {
					if (bufferWritter != null)bufferWritter.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
	}	
		
	 /***
	  * read binary file
	  * @param fileName
	  * @return byte[]
	  */
	public static byte[] readBinaryFile(String fileName) {
	    Path path = Paths.get(fileName);
	    byte[] bytes = null;
	    try {
			bytes =  Files.readAllBytes(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return bytes;
	}
			 
	/***
	 * write binay file
	 * @param fileName
	 * @param data
	 */
	 public static void writeBinaryFile(String fileName, byte[] data) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fileName);
			fos.write(data);//2.д��  
		    fos.flush();   
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  finally {
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	 }
		 
	 /***
	  * copy file from oldFile to newFile
	  * @param oldFile
	  * @param newFile
	  * @return
	  */
	 public static boolean copyFile(String oldFile, String newFile) {
		File file = new File(oldFile);     
		if(!file.exists()){     
		    System.out.println("Error(copyFile): "+oldFile+" doesn't exist");     
		    return false;     
		}
		byte[] fileContent = readBinaryFile(oldFile);
		writeBinaryFile(newFile, fileContent);
		return true;
	 }   
	 
	 /***
	  * compare two binary files, and return the num of differences
	  * @param file1
	  * @param file2
	  * @return
	  */
	 public static int compare(String file1, String file2) {
		File file = new File(file1);     
        if(!file.exists()){     
            System.out.println("Error(compare): "+file1+" doesn't exist");     
            return -1;     
        }
        file = new File(file2);     
        if(!file.exists()){     
            System.out.println("Error(compare): "+file2+" doesn't exist");     
            return -1;     
        }
		 byte[] bytes1 =readBinaryFile(file1);
		 byte[] bytes2 =readBinaryFile(file2);
		 int len1 = bytes1.length;
		 int len2 = bytes2.length;
		 if(len1 != len2)
			 System.out.println("Different length: "+len1+" and "+len2);
		 int len = len1;
		 if(len2<len1) len = len2;
		 int count=0;
		 for(int i=0; i<len; i++)
		 {
			 if(bytes1[i] != bytes2[i]) 
			 {
				 count++;
				 if(count<10)
					System.out.println(i+": "+bytes1[i]+" != "+bytes2[i]);
				 if(count==10)
					 System.out.println("......");
			 }
		 }

		 System.out.println("There are "+count+" differents.");
		 return count;
	 }
}
