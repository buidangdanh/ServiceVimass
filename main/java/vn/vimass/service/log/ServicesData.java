package vn.vimass.service.log;

import java.util.Random;

public class ServicesData {
	public static String LOG_PATH = "D://servicesLog/";
	
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

}
