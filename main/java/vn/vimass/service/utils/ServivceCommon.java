package vn.vimass.service.utils;

import vn.vimass.service.log.FileManager;
import vn.vimass.service.log.Log;

import java.io.File;
import java.io.FilenameFilter;
import java.security.MessageDigest;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServivceCommon {
	
	public static String getStringThongThuong(String input) {
		Pattern p = Pattern.compile("[ .,:-_0-9a-zA-Z]+");
		Matcher m = p.matcher(input);
//		Log.logServices("input:" + input);
		StringBuilder kq = new StringBuilder();
		while (m.find()) {

			// Log.logServices("m.group():" + m.group());
			// int n = Integer.parseInt(m.group());
			kq.append(m.group());
		}
		if (kq.toString().length() > 0) {
			return kq.toString();
		}
		return "0";
	}
	
	
	public static String getStringThongThuong09azAZ(String input) {
		Pattern p = Pattern.compile("[0-9a-zA-Z]+");
		Matcher m = p.matcher(input);
//		Log.logServices("input:" + input);
		StringBuilder kq = new StringBuilder();
		while (m.find()) {

			// Log.logServices("m.group():" + m.group());
			// int n = Integer.parseInt(m.group());
			kq.append(m.group());
		}
		if (kq.toString().length() > 0) {
			return kq.toString();
		}
		return "0";
	}
	
	  public static String hexToAscii(String hexStr) {
		    StringBuilder output = new StringBuilder();
		     
		    try
		    {
		    	hexStr = hexStr.replace(" ", "").trim();
			    for (int i = 0; i < hexStr.length(); i += 2) {
			        String str = hexStr.substring(i, i + 2);
			        output.append((char) Integer.parseInt(str, 16));
			    }	
		    }
		    catch (Exception e) {
				e.printStackTrace();
			}
		     
		    return output.toString();
		}

	public static String bamMD5(String input)
	{
		StringBuffer sb = new StringBuffer();
		try {
			input = input.trim();
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(input.getBytes());

			byte[] byteData = md.digest();

			// convert the byte to hex format method 1

			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
		} catch (Exception e) {

		}
		return sb.toString();
	}
	
	

	
	public static String generateSessionKey(int length) {
//		String alphabet = new String(
//				"0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"); // 9
//		int n = alphabet.length(); // 10
//
//		String result = new String();
//		Random r = new Random(); // 11
//
//		for (int i = 0; i < length; i++)
//			// 12
//			result = result + alphabet.charAt(r.nextInt(n)); // 13
//
//		return result;
		
		
		return generateSessionKeyLowestCase(length);
	}
	
	

	public static String generateSessionKeyLowestCase(int length) {
		String alphabet = "0123456789abcdefghijklmnopqrstuvwxyz"; // 9
		int n = alphabet.length(); // 10

		String result = "";
		Random r = new Random(); // 11

		for (int i = 0; i < length; i++)
			// 12
			result = result + alphabet.charAt(r.nextInt(n)); // 13

		return result;
	}
	


	public static String getNumber(String input)
	{
		Pattern p = Pattern.compile("[0-9]+");
		Matcher m = p.matcher(input);
		Log.logServices("input:" +input);
		StringBuilder kq = new StringBuilder();
		while (m.find()) {

//			Log.logServices("m.group():" + m.group());
//		    int n = Integer.parseInt(m.group());
		    kq.append(m.group());
		}
		if(kq.toString().length() > 0)
		{
			return kq.toString();
		}
		return "0";
	}

	public static String generateSessionNumberKey(int length) {
		String alphabet = "0123456789"; // 9
		int n = alphabet.length(); // 10

		String result = "";
		Random r = new Random(); // 11

		for (int i = 0; i < length; i++)
			// 12
			result = result + alphabet.charAt(r.nextInt(n)); // 13

		return result;
	}
	
	public static String generateSessionKeyUpCase(int length) {
		String alphabet = "0123456789ABCDEF"; // 9
		int n = alphabet.length(); // 10

		String result = "";
		Random r = new Random(); // 11

		for (int i = 0; i < length; i++)
			// 12
			result = result + alphabet.charAt(r.nextInt(n)); // 13

		return result;
	}
	

	public static void delay(int minisec) {
		try {
			Thread.sleep(minisec);
		}
//		catch(InterruptedException ee)
//		{
//			
//		}
		catch (Exception e) {
		}
	}
	

	
	public static void delayVer2(int minisec)
	{
		try {
//			int sec = minisec/1000;
//			if(sec == 0)
//			{
//				sec = 1;
//			}
//			TimeUnit.SECONDS.sleep(sec);
//			Log.logServices("delay:");
			TimeUnit.MILLISECONDS.sleep(minisec);
//			TimeUnit.MICROSECONDS.sleep(minisec);

        } catch (InterruptedException e) {
//            Log.logServices("IOException:" + e.getMessage());
        }
	}



	private static boolean checkFileDirectory(String root) {
		boolean check = false;
		// Đường dẫn của thư mục cần tìm kiếm
		String directoryPath = root+"\\VPass13122023\\data";

		final String fileName = "path.txt";

		File directory = new File(directoryPath);

		if (directory.exists() && directory.isDirectory()) {

			File[] matchingFiles = directory.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.equals(fileName);
				}
			});

			if (matchingFiles != null && matchingFiles.length > 0) {
				Log.logServices("Tìm thấy file " + fileName + " trong thư mục " + directoryPath);
				VimassData.PATH_CONFIG = directoryPath;

				Log.logServices(" VimassData.PATH_CONFIG :  " +  VimassData.PATH_CONFIG );

				check = true;
				return check;

			} else {
				Log.logServices("Không tìm thấy file " + fileName + " trong thư mục " + directoryPath);
			}
		} else {
			Log.logServices("Thư mục không tồn tại hoặc không phải là thư mục");
		}

		return check;

	}

	public static String FileDirectory() {
		String data = "";
		File[] roots = File.listRoots();
		for (File root : roots) {

			Log.logServices("Đang kiểm tra ổ đĩa: " + root.toString());

			if( checkFileDirectory(root.toString())) {
				String directoryPath = root+"\\VPass13122023\\data";
				VimassData.PATH_FILE_CONFIG_DB = root +"\\data\\configDB.text";
				VimassData.LOG_PATH = root + "\\VPass13122023\\log\\";
				readFileApiSendData(directoryPath);

				File file = new File(directoryPath +"\\path.txt");

				if (file.exists()) {
					if(file.length() == 0) {

						Log.logServices("file is empty");// tập tin trống
						return data;
					}else {
						data =  FileManager.readFileNormal(directoryPath +"\\path.txt");
						Log.logServices("data: " + data);
					}
				} else {
					Log.logServices("File not found!");
				}
			}

		}

		return data;
	}

	public static String readFileApiSendData(String path) {

		String data = "";
		File file = new File(path+VimassData.PATH_API_SEND_DATA);
		if (file.exists()) {
			if(file.length() == 0) {

				Log.logServices("file is empty");

			}else {
//		    		Log.logServices("file not is empty");
				data =  FileManager.readFileNormal(path+VimassData.PATH_API_SEND_DATA);

				VimassData.ARRAY_URL_SEND = data;
			}

		} else {
			Log.logServices("File not found!");
		}
		return data;
	}

}
