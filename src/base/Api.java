package base;
import util.ClockUtil;

public class Api {
	
	public static void log(Object ob) {
		log(ob.toString());
	}
	
	public static void log(String msg) {
		System.out.printf("%s - %s\n", ClockUtil.getTimestamp(), msg);
	}
	public static void log(String format, Object...args) {
		String msg = String.format(format, args);
		log(msg);
	}
	public static void threadReadout(String string) {
		Api.log("%s|%s", Thread.currentThread().getName(), string);
		
	}
}
