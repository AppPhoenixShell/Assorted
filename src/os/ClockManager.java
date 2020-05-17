package os;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import base.Api;
import os.Token.TokenDestroyedListener;

public class ClockManager implements TokenDestroyedListener{

	protected static final int WHAT_ADD_ALARM = 20;
	protected static final int WHAT_CANCEL_ALARM = 21;
	
	private MessageHandler localHandler;
	private Map<Token, Alarm> alarmMap;
	
	
	private MessageHandler.Callback callback = new MessageHandler.Callback() {
		
		@Override
		public void handleMessage(MyMessage message) {
			switch(message.what) {
				case WHAT_ADD_ALARM:{
					//Api.log("WHAT_ALARM");
					Alarm alarm = (Alarm)message.obj;
					alarm.fireAndResendIfRepeating();
				}break;
				case WHAT_CANCEL_ALARM:{
					Alarm alarm = (Alarm)message.obj;
					Api.log("WHAT_CANCEL_ALARM");
					boolean removed =localHandler.looper.queue.removeFromQueue(alarm.alarmMessage);
					if(removed)
						Api.log("Alarm Message removed");
					alarmMap.remove(alarm.UUID);
					alarm.cancel();
				}break;
			}
		}
	};
	
	
	
	
	public ClockManager(HimawariOS os) {
		this.localHandler= new MessageHandler(os.getLooper(), callback);
		this.alarmMap = new HashMap<>();
	}
	private Token prepareAlarm(Alarm alarm) {
		MyMessage alarmMsg = localHandler.obtain(WHAT_ADD_ALARM, alarm);
		alarmMsg.when = alarm.getWhen();
		
		alarm.alarmMessage = alarmMsg;
		Token token = new Token(Token.TYPE_ALARM, this);
		alarm.UUID = token;
		return token;
	}
	
	private void destoryAlarm(Alarm alarm) {
		
	}
	
	
	private String postAlarm(Token token, Alarm alarm) {
		alarmMap.put(token, alarm);
		alarm.alarmMessage.send();
		return alarm.UUID.getUUID();
	}
	
	public void cancelAlarm(Token uuid) {
		Alarm theAlarm = alarmMap.get(uuid);
		
		MyMessage cancel = localHandler.obtain(WHAT_CANCEL_ALARM, theAlarm);
		cancel.send();
		
	}
	public String setWhenAlarm(int code, Object object, long when, AlarmListener listener) {
		Alarm alarm = new Alarm(listener, code, object);
		alarm.set(Alarm.TYPE_WHEN, when);
		
		Token tok = prepareAlarm(alarm);
		postAlarm(tok,alarm);
		
		
		
		return alarm.UUID.getUUID();
	}
	
	public Token setRepeatingAlarm(int code, Object object, long when, long period, AlarmListener listener) {
		Alarm alarm = new Alarm(listener, code, object);
		alarm.setRepeating(period);
		alarm.set(Alarm.TYPE_WHEN, when);
		
		Token tok = prepareAlarm(alarm);
		postAlarm(tok,alarm);
		return tok; 
	}
	
	public Token setDelayAlarm(int code, Object object, long delay, AlarmListener listener) {
		Alarm alarm = new Alarm(listener,code, object);
		alarm.set(Alarm.TYPE_DELAY, delay);
		Token tok = prepareAlarm(alarm);
		postAlarm(tok,alarm);
		return tok;
		
	}
	@Override
	public void onDestoryToken(Token token) {
		cancelAlarm(token);
		
		Api.log("Destorying alarm token & cancelling alarm");
		
	}
	
	
	
}
