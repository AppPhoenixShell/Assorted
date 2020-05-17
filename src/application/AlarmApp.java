package application;

import java.util.Random;



import base.Api;
import os.Alarm;
import os.AlarmListener;
import os.ClockManager;
import os.Himawari;
import os.SmallApp;
import os.Token;
import rss.Sha256;
import util.ClockUtil;
import util.PerfBlock;

public class AlarmApp extends SmallApp<Void,AlarmApp.Observer>{
	
	public static int WHAT_DAY_ELAPSED = 0; //24 hours since the app started running
	public static int WHAT_NEXT_DAY = 1; //the next day based on epoch time
	public static int WHAT_FIFTEEN_MINUTES = 1;
	public static int WHAT_HOUR = 2;
	public static int WHAT_HALF_HOUR = 3;
	public static int WHAT_MINUTE = 4;
	
	private String alarmDayEl;
	private String alarmNextDay;
	private String alarmHour;
	private String alarm30;
	
	private Token alarm15;
	private Token alarmMin;
	
	private int count;
	
	static int PERF_LOOP = 0;
	PerfBlock perf = new PerfBlock(10);
	
	int LOOP_COUNT = 1000000;
	
	Random random = new Random();
	byte[] randomArray = new byte[64];
	
	private AlarmListener alarms = new AlarmListener() {
		
		@Override
		public void onAlarm(Alarm alarm) {
			
		}
	};
	
	
	
	
	@Override
	public void doUnitOfWork(Himawari os) {
		
		
		
		
	}





	@Override
	public void onCreate() {
		ClockManager clock = getOS().getClockManager();
		
		alarmMin = clock.setRepeatingAlarm(WHAT_MINUTE, null, System.currentTimeMillis(), ClockUtil.SECOND, alarms);
		alarm15 = clock.setDelayAlarm(WHAT_FIFTEEN_MINUTES, null, ClockUtil.MINUTE * 15, alarms);
	
	}





	public interface Observer{
		public void onAlarmID(int what);
	}





	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}
}
