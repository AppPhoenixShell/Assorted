package py;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXFP extends DefaultHandler
{
	static final int STATE_START = 1;
	static final int STATE_NEW_ITEM = 3;
	static final int STATE_END_ITEM = 4;
	
	
	int state = STATE_START;
	int level;
	
	String value = null;
	
	PyRss theFeed = new PyRss();
	
	PyRssItem rssItem;
	
	
	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
	}

	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		value = new String(ch, start, length);
	
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {	
		++level;
		if(qName.equals("item")) 
		{
			state = STATE_NEW_ITEM;
			
			rssItem = new PyRssItem();
		}
	}
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException 
	{
		if(state == STATE_NEW_ITEM){
			if(qName.equals("title"))
				rssItem.title = value;
			else if(qName.equals("description"))
				rssItem.desc = value;
			else if(qName.equals("pubDate"))
				rssItem.setPubdate(value);
			else if(qName.equals("link"))
				rssItem.setLink(value);
			else if(qName.equals("quid"))
				rssItem.guid = value;
			else if(qName.equals("author"))
				rssItem.author= value;
			value = null;
		}
		else if(state == STATE_START && level == 3) {
			if(qName.equals("title"))
				theFeed.title = value;
			else if(qName.equals("link"))
				theFeed.link = value;
			else if(qName.equals("description"))
				theFeed.description = value;
		}
		
		if(qName.equals("item")) {
			state = STATE_END_ITEM;
			theFeed.addItem(rssItem);
		}
		
		--level;
	}

	public PyRss getFeed() {
		return theFeed;
	}
}
