package com.daily.english.app.util;

/**
 * 
 * @author gin
 *
 */
public class RandomStringUtil {
	
	private static int getRandom(int count) {
		return (int) Math.round(Math.random() * (count));
	}

	private static String str = "abcdefghijklmnopqrstuvwxyz";

	private static String string1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";

	/**
	 * 随机生成字符串
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length){
		StringBuffer sb = new StringBuffer();
		int len = str.length();
		for (int i = 0; i < length; i++) {
			sb.append(str.charAt(getRandom(len-1)));
		}
		return sb.toString();
	}

	/**
	 * 随机生成字符串
	 * @param length
	 * @return
	 */
	public static String getRandomPartnerCodeString(int length){
		StringBuffer sb = new StringBuffer();
		int len = string1.length();
		for (int i = 0; i < length; i++) {
			sb.append(string1.charAt(getRandom(len-1)));
		}
		return sb.toString();
	}
}
