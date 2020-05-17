package py;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Iterator;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import rss.Sha256;

public class PyRssItem implements let
{
	protected String title;
	private String link;
	private String hash;
	protected String desc;
	protected String guid;
	protected long pubdate;
	protected String author;
	
	public String getTitle() {
		return title;
	}
	public String getLink() {
		return link;
	}
	public String getDesc() {
		return desc;
	}
	
	public void setPubdate(String pubdate) {
		DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
		try {
			Date date = formatter.parse(pubdate);
			this.pubdate = date.getTime();
		} catch (ParseException e) {
			this.pubdate = 0;
			e.printStackTrace();
		}
	}
	
	public void setLink(String link) {
		this.link = link;
		this.hash = Sha256.hash(link);
	}
	
	@Override
    public String toString() {
		DateTime time = new DateTime(pubdate);
		String stamp = DateTimeFormat.mediumDateTime().print(time);
        return "FeedMessage [title=" + title + ", description=" + desc
                + ", link=" + link + ", guid=" + guid
                + ", pubdate " +stamp +"]";
    }
	@Override
	public Iterator<let> iterator() {
		return null;
	}
}
