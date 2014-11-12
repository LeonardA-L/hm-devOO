package utils;

public class Misc {
	public static int parseTimeStrToSec(String str) {
		int res = 0;
		String tab [] = str.split(":");
		res += Integer.valueOf(tab[0])*3600;
		res += Integer.valueOf(tab[1])*60; 
		return res;
	}
	public static String parseTimeSecToStr(int sec) {
		String res = "";
		res += String.valueOf(sec/3600)+":";
		res += String.valueOf((sec%3600)/60)+":";
		res += "0";
		return res;
	}
}
