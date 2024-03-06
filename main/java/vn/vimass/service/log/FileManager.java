package vn.vimass.service.log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.apache.commons.io.filefilter.WildcardFileFilter;

public class FileManager {
	
	public static String getCheckSumFile(String path) {
		String kq = "";
		 MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
		    FileInputStream fis = new FileInputStream(path);

		    byte[] dataBytes = new byte[1024];

		    int nread = 0;
		    while ((nread = fis.read(dataBytes)) != -1) {
		        md.update(dataBytes, 0, nread);
		    }
            byte[] mdbytes = md.digest();
		    StringBuffer sb = new StringBuffer();
		    for (int i = 0; i < mdbytes.length; i++) {
		        sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
		    }
		    kq = sb.toString();
		    Log.logServices("Digest(in hex format):: " + kq);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return kq;
	}
	public static String getFileExtension(File file) {
	    String name = file.getName();
	    try {
	        return name.substring(name.lastIndexOf(".") + 1);
	    } catch (Exception e) {
	        return "";
	    }
	}
	
	public static void ghiFile(String sPath, String sContent, boolean append) {
		BufferedWriter writer = null;
		try {
			File logFile = new File(sPath);

			Log.logServices(logFile.getCanonicalPath());

			writer = new BufferedWriter(new FileWriter(logFile, append));
			writer.write(sContent);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// Close the writer regardless of what happens...
				writer.close();
			} catch (Exception e) {
			}
		}
	}

	public static boolean checkFileExist(String input) {
		try {
			File f = new File(input);
			if (f.exists()) {
				return true;
			}
		} catch (Exception e) {

		}
		return false;
	}

	public static String readFile(String path) {
		// String text = "";
		// int read, N = 1024 * 1024;
		// char[] buffer = new char[N];
		//
		// try {
		// File f = new File(path);
		// FileReader fr = new FileReader(f);
		// BufferedReader br = new BufferedReader(fr);
		//
		// while (true) {
		// read = br.read(buffer, 0, N);
		// text += new String(buffer, 0, read);
		//
		// if (read < N) {
		// break;
		// }
		// }
		// br.close();
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// }
		//
		// return text;

		StringBuilder text = new StringBuilder();
        try {
			File fileDir = new File(path);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileDir), StandardCharsets.UTF_8));
			
			String str;

			while ((str = in.readLine()) != null) {
				// Log.logServices(str);
				if (text.toString().length() > 0) {
					text.append("\n");
				}
				text.append(str);
			}

			in.close();
		} catch (UnsupportedEncodingException e) {
			Log.logServices(e.getMessage());
		} catch (IOException e) {
			Log.logServices(e.getMessage());
		} catch (Exception e) {
			Log.logServices(e.getMessage());
		}
		return text.toString();
	}
	
	

	public static String readFileFromLine(String path, long startLine) {
		// String text = "";
		// int read, N = 1024 * 1024;
		// char[] buffer = new char[N];
		//
		// try {
		// File f = new File(path);
		// FileReader fr = new FileReader(f);
		// BufferedReader br = new BufferedReader(fr);
		//
		// while (true) {
		// read = br.read(buffer, 0, N);
		// text += new String(buffer, 0, read);
		//
		// if (read < N) {
		// break;
		// }
		// }
		// br.close();
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// }
		//
		// return text;

		StringBuilder text = new StringBuilder();
        try {
			File fileDir = new File(path);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileDir), StandardCharsets.UTF_8));

			String str;
			int line = 0;
			while ((str = in.readLine()) != null) {
				if(line >= startLine)
				{

					if (text.toString().length() > 0) {
						text.append("\n");
					}
					text.append(str);	
				}
				line++;
			}

			in.close();
		} catch (UnsupportedEncodingException e) {
			Log.logServices(e.getMessage());
		} catch (IOException e) {
			Log.logServices(e.getMessage());
		} catch (Exception e) {
			Log.logServices(e.getMessage());
		}
		return text.toString();
	}
	
	

	public static ArrayList<String> readFileGetListLineAppend(String path, long startLine) {
		// String text = "";
		// int read, N = 1024 * 1024;
		// char[] buffer = new char[N];
		//
		// try {
		// File f = new File(path);
		// FileReader fr = new FileReader(f);
		// BufferedReader br = new BufferedReader(fr);
		//
		// while (true) {
		// read = br.read(buffer, 0, N);
		// text += new String(buffer, 0, read);
		//
		// if (read < N) {
		// break;
		// }
		// }
		// br.close();
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// }
		//
		// return text;

		
		ArrayList<String> text = new ArrayList<>();
		
		try {
			File fileDir = new File(path);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileDir), StandardCharsets.UTF_8));

			String str;
			int line = 0;
			while ((str = in.readLine()) != null) {
				if(line >= startLine)
				{					
					text.add(str);	
				}
				line++;
			}

			in.close();
		} catch (UnsupportedEncodingException e) {
			Log.logServices(e.getMessage());
		} catch (IOException e) {
			Log.logServices(e.getMessage());
		} catch (Exception e) {
			Log.logServices(e.getMessage());
		}
		return text;
	}

	public static String deCodeURL(String input) {
		try {
			return URLDecoder.decode(input, "utf-8");
		} catch (Exception e) {

		}
		return input;
	}

	public static String enCodeURL(String input) {
		try {
			return URLEncoder.encode(input, "utf-8");
		} catch (Exception e) {

		}
		return input;
	}

	public static String readFileNormal(String path) {
		String text = "";
		int read, N = 1024 * 1024;
		char[] buffer = new char[N];

		try {
			File f = new File(path);
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);

			while (true) {
				read = br.read(buffer, 0, N);
				text += new String(buffer, 0, read);

				if (read < N) {
					break;
				}
			}
			br.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return text;
	}    

	public static void writeFile(String path, String content, boolean append) {
		BufferedWriter writer = null;
		try {
			File f = new File(path);
			writer = new BufferedWriter(new FileWriter(f, append));
			// writer = new BufferedWriter(new OutputStreamWriter(new
			// FileOutputStream(f, append), "UTF-8"));
			writer.write(content);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (Exception e) {
			}
		}

	}
	
	
	public static void writeFile_input(String path, String content) {
		BufferedWriter writer = null;
		try {
			File f = new File(path);
			writer = new BufferedWriter(new FileWriter(f));
			// writer = new BufferedWriter(new OutputStreamWriter(new
			// FileOutputStream(f, append), "UTF-8"));
			writer.write(content);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (Exception e) {
			}
		}

	}

	public static String boDauTiengViet(String sDuLieu) {
		if (sDuLieu != null) {
			sDuLieu = deAccent(sDuLieu);
			
			sDuLieu = sDuLieu
					.replaceAll(
							"á|à|ả|ã|ạ|ă|ắ|ằ|ẳ|ẵ|ặ|â|ấ|ầ|ẩ|ẫ|ậ|Á|À|Ả|Ã|Ạ|Ă|Ắ|Ằ|Ẳ|Ẵ|Ặ|Â|Ấ|Ầ|Ẩ|Ẫ|Ậ",
							"a");
			sDuLieu = sDuLieu.replaceAll("đ|Đ", "d");
			sDuLieu = sDuLieu.replaceAll(
					"é|è|ẻ|ẽ|ẹ|ê|ế|ề|ể|ễ|ệ|É|È|Ẻ|Ẽ|Ẹ|Ê|Ế|Ề|Ể|Ễ|Ệ", "e");
			sDuLieu = sDuLieu.replaceAll("í|ì|ỉ|ĩ|ị|Í|Ì|Ỉ|Ĩ|Ị", "i");
			sDuLieu = sDuLieu
					.replaceAll(
							"ó|ò|ỏ|õ|ọ|ô|ố|ồ|ổ|ỗ|ộ|ơ|ớ|ờ|ở|ỡ|ợ|Ó|Ò|Ỏ|Õ|Ọ|Ô|Ố|Ồ|Ổ|Ỗ|Ộ|Ơ|Ớ|Ờ|Ở|Ỡ|Ợ",
							"o");
			sDuLieu = sDuLieu.replaceAll(
					"ú|ù|ủ|ũ|ụ|ư|ứ|ừ|ử|ữ|ự|Ú|Ù|Ủ|Ũ|Ụ|Ư|Ứ|Ừ|Ử|Ữ|Ự", "u");
			sDuLieu = sDuLieu.replaceAll("ý|ỳ|ỷ|ỹ|ỵ|Ý|Ỳ|Ỷ|Ỹ|Ỵ", "y");
		} else {
			sDuLieu = "";
		}

		return sDuLieu;
	}


	public static String deAccent(String str) {
	    String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD); 
	    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
	    return pattern.matcher(nfdNormalizedString).replaceAll("");
	}
	public static void deleteSubFolder(String path) {
		File f = new File(path);
		if (f.exists()) {
			String[] list = f.list();
			if (list.length == 0) {
            } else {
				for (int i = 0; i < list.length; i++) {
					File f1 = new File(path + "\\" + list[i]);
					if (f1.isFile() && f1.exists()) {
						f1.delete();
					}
					if (f1.isDirectory()) {
						folderdel(String.valueOf(f1));
					}
				}
				// folderdel(path);
			}
		}
	}

	public static void folderdel(String path) {
		File f = new File(path);
		if (f.exists()) {
			String[] list = f.list();
			if (list.length == 0) {
				if (f.delete()) {
					Log.logServices("folder deleted");
                }
			} else {
				for (int i = 0; i < list.length; i++) {
					File f1 = new File(path + "\\" + list[i]);
					if (f1.isFile() && f1.exists()) {
						f1.delete();
					}
					if (f1.isDirectory()) {
						folderdel(String.valueOf(f1));
					}
				}
				folderdel(path);
			}
		}
	}

	public static ArrayList<String> layDsFileCuaThuMuc(String input) {
		ArrayList<String> kq = new ArrayList<>();
		try {
			File dir = new File(input);
			FileFilter fileFilter = new WildcardFileFilter("*");
			File[] files = dir.listFiles(fileFilter);
			for (int i = 0; i < files.length; i++) {
				try {
					kq.add(files[i].getName());
				} catch (Exception e) {

				}
			}
		} catch (Exception e) {

		}
		return kq;
	}

	public static void copyFileUsingFileStreams(File source, File dest)

	throws IOException {

		InputStream input = null;

		OutputStream output = null;

		try {

			input = new FileInputStream(source);

			output = new FileOutputStream(dest);

			byte[] buf = new byte[1024];

			int bytesRead;

			while ((bytesRead = input.read(buf)) > 0) {

				output.write(buf, 0, bytesRead);

			}

		} finally {

			input.close();

			output.close();

		}

	}

	public static long getFileSize(String pathFile) {
		long kq = 0;
		
		try {
			File f = new File(pathFile);
			kq = f.length();
		} catch (Exception e) {

		} 
		
		return kq;
	}
	
	

	public static long getFileLine(String pathFile) {
		long kq = 0;
		
		try {
			FileInputStream stream = new FileInputStream(pathFile);
			byte[] buffer = new byte[8192];
			int n;
			while ((n = stream.read(buffer)) > 0) {
			    for (int i = 0; i < n; i++) {
			    	if (buffer[i] == '\n')
			    	{
			    		kq++;
			    	}
			    }
			}
			stream.close();
		} catch (Exception e) {

		} 
		
		return kq;
	}
	

	public static void xoaFileCuaThuMuc(String input) {
		try
		{
			File file = new File(input);
				try
				{
					file.delete();
				}
				catch(Exception e)
				{
					
				}
		}
		catch(Exception e)
		{
			
		}
	}

	

	public static void createDir(String pATH_SAVE) {
		File file = new File(pATH_SAVE);
		
		Log.logServices("createDir:" + pATH_SAVE);
		
//		if (!file.exists()) {
			if (file.mkdirs()) {
				Log.logServices("Directory is created!");
			} else {
				Log.logServices("Failed to create directory!");
			}
//		}
	}
	
	public static void moveFile(String pathA, String pathB)
	{
		try{

	    	   File afile =new File(pathA);

	    	   if(afile.renameTo(new File(pathB))){
	    		Log.logServices("File is moved successful!");
	    	   }else{
	    		Log.logServices("File is failed to move!");
	    	   }

	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	}


	public static String getActionString(String tenGa) {
		String kq = tenGa;
		if(tenGa == null)
		{
			kq = "";
		}
		else
		{
			String khongDau = boDauTiengViet(kq);
			StringBuilder finalText = new StringBuilder();
			if(khongDau != null && khongDau.length() == kq.length())
			{
				for(int i = 0; i < khongDau.length(); i++)
				{
					if(khongDau.charAt(i) == kq.charAt(i))
					{
						finalText.append(khongDau.charAt(i));
					}
					else
					{
						finalText.append("*");
					}
					kq = finalText.toString().replace(" ", "<SP>").replace("**", "*");
				}
				kq = finalText.toString().replace(" ", "<SP>").replace("**", "*");
			}
		}
		return kq;
	}

public static String readByStreamTokenizerOnBufferedReader(String path) throws Exception {
	
	StringBuilder kq = new StringBuilder();
	
    
    FileInputStream inputStream = null;
    Scanner sc = null;
    try {
        inputStream = new FileInputStream(path);
        sc = new Scanner(inputStream, "UTF-8");
        int i = 0;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if(i > 0)
            {
            	kq.append("\n");
            }
            kq.append(line);
            i++;
            // Log.logServices(line);
        }
        // note that Scanner suppresses exceptions
        if (sc.ioException() != null) {
            throw sc.ioException();
        }
    } finally {
        if (inputStream != null) {
            inputStream.close();
        }
        if (sc != null) {
            sc.close();
        }
    }
    
    
    
    
    
    

//  long startTime = System.currentTimeMillis();

//  StreamTokenizer st = new StreamTokenizer(new BufferedReader(new FileReader(path)));
//  int array[] = new int[1000000];
//
//  for (int i = 0; st.nextToken() != StreamTokenizer.TT_EOF; i++) {
//      array[i] = (int) st.nval;
//  }
  

//    long endTime = System.currentTimeMillis();
//    Log.logServices(String.format("Total time by StreamTokenizer with BufferedReader: %d ms", endTime - startTime));
    
    return kq.toString();
    
}

}
