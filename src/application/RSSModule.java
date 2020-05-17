
package application;

import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import com.google.api.client.util.ArrayMap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import base.Api;
import himawari.HWConst;
import himawari.server.HSORssFeed;
import himawari.server.RealtimeSet;
import os.Alarm;
import os.AlarmListener;
import os.ClockManager;
import os.Himawari;
import os.SmallApp;
import rss.Feed;
import rss.FeedMessage;
import rss.RSSFeedParser;
import server.SimpleChildListener;
import util.ClockUtil;

public class RSSModule extends SmallApp<Void,Void>{
	
	private static final int WHAT_UPDATE = 22;
	
	private static final String APPID = "appid.rss";
	
	
	
	/**a list of feeds downloaded from firebase*/
	private ArrayMap<String, HSORssFeed> feedmap = new ArrayMap<>();
	private Set<String> rsshashes = new HashSet<String>();
	private DatabaseReference root;

	private RealtimeSet hasher;
	
	private boolean isRssLoaded = false;
	private boolean isFeedLoaded = false;
	
	private AlarmListener callback = new AlarmListener() {
		
		@Override
		public void onAlarm(Alarm alarm) {
			getOS().getClient().sendSMS("update RSS");
			updateRss();
		}
	};


	String soma = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n" + 
			"<rss version=\"2.0\">\r\n" + 
			"  <channel>\r\n" + 
			"    <title>Horimiya</title>\r\n" + 
			"    <description>Latest manga chapters for Horimiya</description>\r\n" + 
			"    <link>https://fanfox.net/</link>\r\n" + 
			"    <lastBuildDate>Thu, 04 Apr 2019 17:25:02 GMT</lastBuildDate>\r\n" + 
			"    <generator>FeedCreator 1.7.2</generator>\r\n" + 
			"    <image>\r\n" + 
			"      <url>//c.mfcdn.net/media/logo.gif</url>\r\n" + 
			"      <title>Manga Fox</title>\r\n" + 
			"      <link>https://fanfox.net/</link>\r\n" + 
			"      <description>Feed provided by MangaFoxClick to visit.</description>\r\n" + 
			"    </image>\r\n" + 
			"    <item>\r\n" + 
			"      <title>Horimiya Vol.12 Ch.097</title>\r\n" + 
			"      <link>https://fanfox.net/manga/horimiya/v12/ksskdksjjaiw sjdjsjds/1.html</link>\r\n" + 
			"      <description/>\r\n" + 
			"      <pubDate>Sun, 31 Mar 2019 15:35:09 GMT</pubDate>\r\n" + 
			"    </item>\r\n" + 
			"    <item>\r\n" + 
			"      <title>TEST</title>\r\n" + 
			"      <link>https://fanfox.net/manga/horimiya/v12/TESTER/1.html</link>\r\n" + 
			"      <description/>\r\n" + 
			"      <pubDate>Sun, 31 Mar 2019 00:00:09 GMT</pubDate>\r\n" + 
			"    </item>\r\n" +
			"    <item>\r\n" + 
			"      <title>,sdkjchjh hhnhvu</title>\r\n" + 
			"      <link>https://fanfox.net/ldksdjscknckxcjxhjchj/v12/c096/1.html</link>\r\n" + 
			"      <description/>\r\n" + 
			"      <pubDate>Tue, 12 Feb 2019 06:36:07 GMT</pubDate>\r\n" + 
			"    </item>\r\n" + 
			"  </channel>\r\n" + 
			"</rss>";
	
	/**download the feed from firebase and update*/
	private SimpleChildListener feedlistener = new SimpleChildListener() {

		@Override
		public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
			HSORssFeed value = snapshot.getValue(HSORssFeed.class);
			feedmap.add(snapshot.getKey(), value);
			//Api.log("adding %s to feed", value.getRsslink());
			RSSFeedParser parser;
			try {
				parser = RSSFeedParser.fromUrl(value.getRsslink());
				publishFeed(parser.readFeed());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void onChildRemoved(DataSnapshot snapshot) {
			feedmap.remove(snapshot.getKey());
		}
	};
	
	/**download the full RSS list from firebase and hash all items*/
	private ValueEventListener fullrss = new ValueEventListener() {
		
		@Override
		public void onDataChange(DataSnapshot snapshot) {
			Iterable<DataSnapshot> children = snapshot.getChildren();
		
			for(DataSnapshot item : children) {
				FeedMessage msg = item.getValue(FeedMessage.class);
				hasher.put(msg.getLink());
			}
			root.child("rssraw").addChildEventListener(pendingRss);
			Api.log("RSS List loaded");
			isRssLoaded = true;
			
			RSSFeedParser rss= RSSFeedParser.fromString(soma);
			
			Feed feed = rss.readFeed();
			publishFeed(feed);
			
		}
		
		@Override
		public void onCancelled(DatabaseError error) {
			
		}
	};
	
	private SimpleChildListener pendingRss = new SimpleChildListener() {

		@Override
		public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
			FeedMessage message = snapshot.getValue(FeedMessage.class);
			publishMessage(message);
			snapshot.getRef().removeValueAsync();
			//System.out.println(message.getHash() + " " + message.getTitle());
		}
	};
	
	private void publishMessage(FeedMessage message) {
		if(!hasher.exists(message.getLink())) {
			root.child(HWConst.REF_RSSLIST).push().setValueAsync(message);
			String msg = "adding " + message.getLink() + " to rsslist";
			getOS().getClient().sendSMS(message.getLink());
			Api.log(msg);
			
			
			
			hasher.put(message.getLink());
		}
	}
	
	private void publishFeed(Feed rssfeed) {
		//RSSFeedParser feedparser = new RSSFeedParser(rsslink);
		//Feed rssfeed = feedparser.readFeed();
			
		List<FeedMessage> messages = rssfeed.getMessages();
		//Collections.sort(messages);
		for(FeedMessage msg : messages) {
			publishMessage(msg);
		}
	}
	
	public void updateRss() {
		Api.log("Updating RSS");
		for(int i=0; i < feedmap.size(); i++) {
			HSORssFeed feed = feedmap.getValue(i);
			RSSFeedParser parser;
			try {
				parser = RSSFeedParser.fromUrl(feed.getRsslink());
				publishFeed(parser.readFeed());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}


	@Override
	public void onCreate() {
		this.root = FirebaseDatabase.getInstance().getReference();
		root.child(HWConst.REF_RSSLIST).addListenerForSingleValueEvent(fullrss);
		root.child(HWConst.REF_RSSFEED).addChildEventListener(feedlistener);
		hasher =new RealtimeSet(root.child("rsshash"));
		
		getOS().getClockManager().setRepeatingAlarm(WHAT_UPDATE, null, System.currentTimeMillis(), ClockUtil.HOUR, callback);
		
	}

	@Override
	public String getId() {
		return APPID;
	}

}
