package android.util;

public class Log {

	public static int i(String tag, String msg){
		System.out.println(String.format("%s, %s", tag, msg));
		return 0;
	}
	
	public static int d(String tag, String msg){
		System.out.println(String.format("%s %s", tag, msg));
		return 0;
	}
}
