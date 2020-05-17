package rss;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import serial.LightSerial;
import serial.ObjectSerial;
import util.ClockUtil;

/*
 * Represents one RSS message
 */
public class FeedMessage implements Comparable<FeedMessage> {

    String title;
    String description;
    String link;
    String author;
    String guid;
    String pubdate;
    
    long date;
    
    String hash;
    String timestamp;
    
    public String getTimestamp() {
    	return timestamp;
    }
    public void setTimestamp(String stamp) {
    	this.timestamp = stamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
        this.hash= Sha256.hash(link);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    @Override
    public String toString() {
        return "FeedMessage [title=" + title + ", description=" + description
                + ", link=" + link + ", author=" + author + ", guid=" + guid
                + "]";
    }
    
    public String getHash() {
    	return hash;
    }
    public void setDate(long date) {
    	this.date = date;
    }
    public long getDate() {
    	return date;
    }

	public void setPubDate(String pubdate) {
		this.pubdate = pubdate;
		
	}

	@Override
	public int compareTo(FeedMessage o) {
	
		return (int) (date - o.date);
	}
	
	
	
	public static class FeedSerial extends ObjectSerial<FeedMessage>{
		private static final int WHAT_TITLE = 0;
		private static final int WHAT_DESC = 1;
		private static final int WHAT_LINK = 2;
		private static final int WHAT_AUTHOR = 3;
		private static final int WHAT_GUID = 4;
		private static final int WHAT_PUBDATE = 5;
		
		@Override
		public void serialize(LightSerial serial, FeedMessage object) {
			serial.write(WHAT_TITLE, object.title);
		}
		@Override
		public FeedMessage deserialise(int what, LightSerial serial) {
			FeedMessage m = new FeedMessage();
			
			switch(what) {
				case WHAT_TITLE:m.title = serial.readString();break;
				case WHAT_DESC:m.description = serial.readString();break;
				case WHAT_LINK:m.link = serial.readString();break;
				case WHAT_AUTHOR:m.author = serial.readString();break;
			}
			
			
			
			return m;
			
		}
		
		
		
		
	}
	
	
	

}