package pyapps;

import py.PyContext;
import py.PyDone;
import py.PyRealtimeSet;
import py.PyRss;
import py.let;

public class PyMain extends PyContext
{
	private static final String TOKEN = "files/token.json";
	private static final String URL = "https://geofire-game.firebaseio.com/";
	
	public static void main(String[] args) {
		PyContext script = new PyMain();
		
		
		script.main();
	
	}
	
	String xml = 
			"<?xml version=\"1.0\" encoding=\"utf-8\"?><rss version=\"2.0\"><channel><title>Seitokai Tantei Kirika</title><description>Latest manga chapters for Seitokai Tantei Kirika</description><link>https://fanfox.net/</link><lastBuildDate>Wed, 15 May 2019 14:11:03 GMT</lastBuildDate><generator>FeedCreator 1.7.2</generator><image><url>//c.mfcdn.net/media/logo.gif</url><title>Manga Fox</title><link>https://fanfox.net/</link><description>Feed provided by MangaFoxClick to visit.</description></image><item><title>Seitokai Tantei Kirika Vol.07 Ch.031</title><link>https://fanfox.net/manga/seitokai_tantei_kirika/v07/c031/1.html</link><description></description><pubDate>Wed, 20 Mar 2019 15:34:00 GMT</pubDate></item><item><title>Seitokai Tantei Kirika Vol.07 Ch.030</title><link>https://fanfox.net/manga/seitokai_tantei_kirika/v07/c030/1.html</link><description>Love Comedy</description><pubDate>Wed, 20 Mar 2019 17:58:03 GMT</pubDate></item><item><title>Seitokai Tantei Kirika Vol.07 Ch.029</title><link>https://fanfox.net/manga/seitokai_tantei_kirika/v07/c029/1.html</link><description>Dispelled</description><pubDate>Mon, 01 May 2017 05:13:02 GMT</pubDate></item><item><title>Seitokai Tantei Kirika Vol.07 Ch.028</title><link>https://fanfox.net/manga/seitokai_tantei_kirika/v07/c028/1.html</link><description></description><pubDate>Mon, 20 Mar 2017 16:47:03 GMT</pubDate></item><item><title>Seitokai Tantei Kirika Vol.07 Ch.027</title><link>https://fanfox.net/manga/seitokai_tantei_kirika/v07/c027/1.html</link><description></description><pubDate>Mon, 06 Mar 2017 06:11:02 GMT</pubDate></item><item><title>Seitokai Tantei Kirika Vol.07 Ch.026</title><link>https://fanfox.net/manga/seitokai_tantei_kirika/v07/c026/1.html</link><description></description><pubDate>Wed, 02 Nov 2016 17:40:02 GMT</pubDate></item><item><title>Seitokai Tantei Kirika Vol.07 Ch.025</title><link>https://fanfox.net/manga/seitokai_tantei_kirika/v07/c025/1.html</link><description></description><pubDate>Thu, 03 Nov 2016 09:06:01 GMT</pubDate></item><item><title>Seitokai Tantei Kirika Vol.07 Ch.024</title><link>https://fanfox.net/manga/seitokai_tantei_kirika/v07/c024/1.html</link><description>Negotiator</description><pubDate>Thu, 21 Jul 2016 15:22:05 GMT</pubDate></item><item><title>Seitokai Tantei Kirika Vol.07 Ch.023</title><link>https://fanfox.net/manga/seitokai_tantei_kirika/v07/c023/1.html</link><description>Promise</description><pubDate>Mon, 11 Jul 2016 16:58:01 GMT</pubDate></item><item><title>Seitokai Tantei Kirika Vol.07 Ch.022</title><link>https://fanfox.net/manga/seitokai_tantei_kirika/v07/c022/1.html</link><description></description><pubDate>Thu, 16 Jun 2016 12:07:02 GMT</pubDate></item><item><title>Seitokai Tantei Kirika Vol.07 Ch.021</title><link>https://fanfox.net/manga/seitokai_tantei_kirika/v07/c021/1.html</link><description></description><pubDate>Tue, 10 May 2016 12:05:02 GMT</pubDate></item><item><title>Seitokai Tantei Kirika Vol.06 Ch.020</title><link>https://fanfox.net/manga/seitokai_tantei_kirika/v06/c020/1.html</link><description>Gratitude</description><pubDate>Fri, 18 Sep 2015 15:20:39 GMT</pubDate></item><item><title>Seitokai Tantei Kirika Vol.05 Ch.019</title><link>https://fanfox.net/manga/seitokai_tantei_kirika/v05/c019/1.html</link><description>Idol</description><pubDate>Wed, 19 Aug 2015 14:44:56 GMT</pubDate></item><item><title>Seitokai Tantei Kirika Vol.05 Ch.018</title><link>https://fanfox.net/manga/seitokai_tantei_kirika/v05/c018/1.html</link><description>Syun</description><pubDate>Wed, 19 Aug 2015 14:45:34 GMT</pubDate></item><item><title>Seitokai Tantei Kirika Vol.05 Ch.017</title><link>https://fanfox.net/manga/seitokai_tantei_kirika/v05/c017/1.html</link><description>Swindle</description><pubDate>Fri, 17 Jul 2015 15:14:19 GMT</pubDate></item><item><title>Seitokai Tantei Kirika Vol.05 Ch.016</title><link>https://fanfox.net/manga/seitokai_tantei_kirika/v05/c016/1.html</link><description>Predecessor</description><pubDate>Thu, 02 Jul 2015 14:32:02 GMT</pubDate></item><item><title>Seitokai Tantei Kirika Vol.04 Ch.015</title><link>https://fanfox.net/manga/seitokai_tantei_kirika/v04/c015/1.html</link><description>Cake Lover</description><pubDate>Wed, 04 Mar 2015 14:29:57 GMT</pubDate></item><item><title>Seitokai Tantei Kirika Vol.04 Ch.014</title><link>https://fanfox.net/manga/seitokai_tantei_kirika/v04/c014/1.html</link><description>Hikage</description><pubDate>Sun, 08 Feb 2015 13:29:14 GMT</pubDate></item><item><title>Seitokai Tantei Kirika Vol.04 Ch.013</title><link>https://fanfox.net/manga/seitokai_tantei_kirika/v04/c013/1.html</link><description>Big Game</description><pubDate>Sun, 08 Feb 2015 13:29:04 GMT</pubDate></item><item><title>Seitokai Tantei Kirika Vol.04 Ch.012</title><link>https://fanfox.net/manga/seitokai_tantei_kirika/v04/c012/1.html</link><description>Persuasion</description><pubDate>Wed, 14 Jan 2015 09:23:55 GMT</pubDate></item><item><title>Seitokai Tantei Kirika Vol.03 Ch.011</title><link>https://fanfox.net/manga/seitokai_tantei_kirika/v03/c011/1.html</link><description>Runaway</description><pubDate>Wed, 14 Jan 2015 09:24:07 GMT</pubDate></item><item><title>Seitokai Tantei Kirika Vol.03 Ch.010</title><link>https://fanfox.net/manga/seitokai_tantei_kirika/v03/c010/1.html</link><description>Magna Carta</description><pubDate>Wed, 14 Jan 2015 09:24:14 GMT</pubDate></item><item><title>Seitokai Tantei Kirika Vol.03 Ch.009</title><link>https://fanfox.net/manga/seitokai_tantei_kirika/v03/c009/1.html</link><description>Family</description><pubDate>Wed, 14 Jan 2015 09:24:24 GMT</pubDate></item><item><title>Seitokai Tantei Kirika Vol.03 Ch.008</title><link>https://fanfox.net/manga/seitokai_tantei_kirika/v03/c008/1.html</link><description>Princess</description><pubDate>Wed, 14 Jan 2015 09:24:31 GMT</pubDate></item><item><title>Seitokai Tantei Kirika Vol.02 Ch.007</title><link>https://fanfox.net/manga/seitokai_tantei_kirika/v02/c007/1.html</link><description>Place</description><pubDate>Wed, 14 Jan 2015 09:24:42 GMT</pubDate></item><item><title>Seitokai Tantei Kirika Vol.02 Ch.006</title><link>https://fanfox.net/manga/seitokai_tantei_kirika/v02/c006/1.html</link><description>Ensemble</description><pubDate>Wed, 14 Jan 2015 09:24:48 GMT</pubDate></item><item><title>Seitokai Tantei Kirika Vol.02 Ch.005</title><link>https://fanfox.net/manga/seitokai_tantei_kirika/v02/c005/1.html</link><description>Invitation</description><pubDate>Wed, 14 Jan 2015 09:24:56 GMT</pubDate></item><item><title>Seitokai Tantei Kirika Vol.02 Ch.004</title><link>https://fanfox.net/manga/seitokai_tantei_kirika/v02/c004/1.html</link><description>Auditor</description><pubDate>Wed, 14 Jan 2015 09:25:03 GMT</pubDate></item><item><title>Seitokai Tantei Kirika Vol.01 Ch.003</title><link>https://fanfox.net/manga/seitokai_tantei_kirika/v01/c003/1.html</link><description>General Clerk</description><pubDate>Wed, 14 Jan 2015 09:23:49 GMT</pubDate></item><item><title>Seitokai Tantei Kirika Vol.01 Ch.002</title><link>https://fanfox.net/manga/seitokai_tantei_kirika/v01/c002/1.html</link><description>Warriors</description><pubDate>Wed, 14 Jan 2015 09:23:42 GMT</pubDate></item><item><title>Seitokai Tantei Kirika Vol.01 Ch.001</title><link>https://fanfox.net/manga/seitokai_tantei_kirika/v01/c001/1.html</link><description>Detective</description><pubDate>Wed, 14 Jan 2015 09:23:35 GMT</pubDate></item><item><title>Seitokai Tantei Kirika Vol.01 Ch.000</title><link>https://fanfox.net/manga/seitokai_tantei_kirika/v01/c000/1.html</link><description>Access</description><pubDate>Wed, 14 Jan 2015 09:23:28 GMT</pubDate></item></channel></rss>";

	@Override
	public void main() {
		
		fireinit(TOKEN, URL);
		
		PyRss rss = PyRss.parse(xml);
		
		for(let item : rss)
			print(item);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
		
		
		

		
		
		
		
		
		
		

		
		
	}

}
