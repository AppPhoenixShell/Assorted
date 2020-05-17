package application;

import application.ReminderApp.Input;
import base.Api;
import os.Alarm;
import os.AlarmListener;
import os.ClockManager;
import os.Himawari;
import os.SmallApp;

public class ReminderApp extends SmallApp<ReminderApp.Input, Void>
{
	private static final String APPID = "appid.reminders";
	private static final int WHAT_REMINDER = 1;
	
	
	
	private Input in = new Input() {
		
		@Override
		public void postReminder(Reminder rem) {	
			Himawari os =  getOS();
			ClockManager clock = os.getClockManager();
			clock.setWhenAlarm(WHAT_REMINDER, rem, rem.when, reminderCallback);
		}
	};
	
	@Override
	public void onCreate() {
	
	}

	private AlarmListener reminderCallback = new AlarmListener() {
		
		@Override
		public void onAlarm(Alarm alarm) {
			switch(alarm.getWhat()) {
				case WHAT_REMINDER:{
					Reminder rem = (Reminder) alarm.getObject();
					Api.log(rem.msg);
				}
			}
		}
	};
	
	


	@Override
	public Input getInput() {
		return in;
	}

	@Override
	public String getId() {
		return APPID;
	}
	
	public static interface Input{
		public void postReminder(Reminder rem);
	}
	
	public static class Reminder
	{
		private String msg;
		private long when;
		
		public Reminder(String msg, long when) {
			this.msg =msg;
			this.when = when;
		}
		
		public String getMsg() {
			return msg;
		}
		public long getWhen() {
			return when;
		}
	}

	
}
