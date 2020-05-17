package pyapps;

import java.io.IOException;
import py.Py;
import py.PyAlarmCallback;
import py.PyApp;
import py.PyCallback;
import py.PyDoc;
import py.PyFile;
import py.PyHandler;
import py.PyLooper;
import py.PyMessage;

public class CarApp extends PyApp {

	private static final String NAME = "carapp";
	
	private PyCallback callback = new PyCallback() {
		
		@Override
		public void handleMessage(PyMessage message) {
			
			alarm(1000 * 10, message);
			log(message.debug);
		}
	};
	
	
	
	@Override
	public void onCreate() {
		log("onCreate()");
		
		PyMessage message = PyMessage.what(1);
		message.debug = "hello debug";
		
		alarm(1000 * 10, message);
	}
	
	
	public String appid() {
		return NAME;
	}


	@Override
	public PyCallback callback() {
		return callback;
	}

}
