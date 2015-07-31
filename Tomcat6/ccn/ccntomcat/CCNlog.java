package ccntomcat;

import java.io.File;

public final class CCNlog {
	
	public final static int FILE_NUM = 10;
	public static File []file = new File[FILE_NUM];
	public static void log(String hint, String str){
		System.out.println(hint+str);
	}
}
