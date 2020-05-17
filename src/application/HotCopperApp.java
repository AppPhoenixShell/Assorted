package application;

import java.io.IOException;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import base.Api;
import os.Alarm;
import os.AlarmListener;
import os.SmallApp;
import rss.Sha256;
import util.ClockUtil;

public class HotCopperApp extends SmallApp<Void, Void>{

	private static final String APPID ="appid.hotcopper";
	private static final String COPPER_URL = "https://hotcopper.com.au/announcements/asx/";
	
	private static final int WHAT_UPDATE = 1;
	
	private AlarmListener alarms = new AlarmListener() {
		
		@Override
		public void onAlarm(Alarm alarm) {
			if(alarm.getWhat() == WHAT_UPDATE) {
				updateHotCopper();
				
				Random random = new Random();
				float randpart = random.nextFloat();
				
				
				long delay = (long) (ClockUtil.getMinutes(5) + (randpart * ClockUtil.getMinutes(5)));
				
				//Api.log("delay: %d ", delay);
				
				
				getOS().getClockManager().setDelayAlarm(WHAT_UPDATE, null, delay, this);
			}
		}
	};
	
	private void updateHotCopper() {
		//Api.log("updating hot copper");
		
		try {
			Document doc = Jsoup.connect(COPPER_URL).get();
			
			Elements els = doc.getElementsByClass("stock-td");
			
			String dochash = Sha256.hash(els.toString());
			Api.log("webhash %s", dochash);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	@Override
	public void onCreate() {
		getOS().getClockManager().setDelayAlarm(WHAT_UPDATE, null, 0, alarms);
	
	}



	@Override
	public String getId() {
		return APPID;
	}

}
