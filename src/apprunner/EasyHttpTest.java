package apprunner;

import easyhttp.EasyHttp;
import easyhttp.HttpRequest;
import easyhttp.HttpToken;
import easyhttp.StringListener;
import easyhttp.StringRequest;
import os.Looper;
import os.MessageHandler;
import os.MyMessage;

public class EasyHttpTest {

	public static void main(String[] args) 
	{	
		final Looper mainLooper = Looper.registerThreadAsMainLopper();
		
		final MessageHandler.Callback mainCallback= new MessageHandler.Callback() {
			
			@Override
			public void handleMessage(MyMessage message) {
			
			}
		};
		
		final MessageHandler mainHandler=  MessageHandler.registerAsMainHandler(mainCallback);
		
		
		final EasyHttp http = new EasyHttp(mainLooper);
		
		TestApi api = new TestApi();
		api.run(http);
		
		
		
		
		
		//final HttpToken searchToken = http.requestToken(request);  
		
		
		
		
		
		
		
		
		
		
		mainLooper.loop();
		
		
		
		
		
		
		
		
		

	}
}
