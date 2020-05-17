package py;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;

import base.Api;
import server.SimpleChildListener;
import util.ClockUtil;

public class Py 
{
	private static PyLooper looper;
	//private static PyHandler handler;
	
	private static PySet<Class<?>> appClasses = new PySet<>();
	
	public static void listenChild(String path, SimpleChildListener listener) {
		FirebaseDatabase.getInstance().getReference(path).addChildEventListener(listener);
	}
	
	public static void initFirebase(String token, String url){
		try {
			FileInputStream serviceAccount = new FileInputStream(token);
		
			Api.log(serviceAccount.toString());
			
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.setDatabaseUrl(url)

					.build();
			
		FirebaseApp.initializeApp(options);
		}catch(IOException e) {
			
		}
	}
	public static HttpURLConnection connect(String url) {
		HttpURLConnection httpConnection = null;
		try {
			httpConnection = (HttpURLConnection) new URL(url).openConnection();
			httpConnection.setRequestMethod("GET");
			httpConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
		
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return httpConnection;
		
	}	
	
	public static void app(PyApp app) {
		if(appClasses.in(app.getClass())) {
			Py.print("app already exists");
			return;
		}
		appClasses.add(app.getClass());
		app.main();
	}
	
	public static void prepare(PyCallback callback) {
		looper();
		handler(callback);
	}
	public static void post(PyMessage message) {
		looper.post(message);
	} 

	private static void handler(PyCallback callback) {
		//handler = new PyHandler(callback, looper);
	}
	
	public static void loop() {
		looper.loop();
	}
	
	public static void print(String value) {
		System.out.println(value);
	}
	public static void prints(String format, Object...args) {
		System.out.printf(format, args);
	}
	public static void print(String...values) {
		for(String l : values)
			System.out.print(l);
		System.out.println();
	}
	public static void sleep(int seconds) {
		try {
			Thread.sleep(1000 * seconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void print(Object para) {
		print(para.toString());
		
	}

	public static PyLooper mainLooper() {
		if(looper == null)
			looper = new PyLooper();
		return looper;
	}
}
