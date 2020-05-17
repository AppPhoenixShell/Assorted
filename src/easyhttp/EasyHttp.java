package easyhttp;

import java.util.LinkedList;
import java.util.Queue;

import com.google.gson.JsonObject;

import base.Api;
import os.Looper;
import os.MessageHandler;
import os.MyMessage;
import os.MessageHandler.Callback;

public class EasyHttp {
		
	private static final int WHAT_RESULT = 1;
	
	private Thread httpThread;
	private Queue<HttpRequest<?>> httpStack;
	private Object lock = new Object();
	

	private MessageHandler mainHandler;
	
	private Looper threadLooper;
	
	Callback mainCallback = new Callback() {
		
		@Override
		public void handleMessage(MyMessage message) {
			switch(message.what) {
				case WHAT_RESULT:{
					HttpRequest<?> request = (HttpRequest<?>)message.obj;
					request.fireObserver();
					
				}break;
			}	
		}
	};
	
	Callback threadCallback = new Callback() {
		
		@Override
		public void handleMessage(MyMessage message) {
			
		}
	};
	
	
	//the looper that callbacks will be called on
	public EasyHttp(Looper looper) {
		this.httpThread = new Thread(httpRunnables);
		this.httpStack = new LinkedList<>();
	
		this.mainHandler = new MessageHandler(looper, mainCallback);		
	}
	
	
	//processes http results in order
	private Runnable httpRunnables = new Runnable() {
		
		@Override
		public void run() {
			HttpRequest<?> nextRequest = null;
			Api.log("HTTP Stack Running");
			do {
				synchronized (lock){
					
					nextRequest = httpStack.poll();
					if(nextRequest == null)
						continue;
					//process next request
					Api.log("processing %s on %s", nextRequest.getUrl(), Thread.currentThread().getName());
		
					
				}
				nextRequest.doRequestAndPostResult();
				//Object result =  nextRequest.getResult();//processRequest(nextRequest);
				MyMessage httpmessage = mainHandler.obtain(WHAT_RESULT);
				httpmessage.obj = nextRequest;
				mainHandler.post(httpmessage);
	
			}
			while(nextRequest != null);
			Api.log("HTTP Stack End");
		}
	};
	
	private void enqueue(HttpRequest<?> request) {
		synchronized (lock) {
			httpStack.add(request);
			if(!httpThread.isAlive()) {
				
				httpThread = new Thread(httpRunnables);
				httpThread.start();
			}
		}
	}
	public void request(HttpRequest<?> someRequest) {
		enqueue(someRequest);
	}
	
	public HttpToken requestToken(HttpRequest<?> someRequest) {
		HttpToken tok = new HttpToken(someRequest);
		requestToken(someRequest, tok);
		return tok;
	}
	
	public void requestToken(HttpRequest<?> someRequest, HttpToken token) {
		request(someRequest);
		if(token.getRequest() != someRequest)
			throw new RuntimeException("HttpToken is not the owner of this request");
	}
	
	/*
	public void requestString(StringRequest stringRequest) {
		enqueue(stringRequest);
	}
	
	public void requestString(int what, String url, StringListener callback) {
		StringRequest simpleRequest = new StringRequest(what,url, callback);
		enqueue(simpleRequest);
	}
	
	public void requestJson(int what,String url, JsonListener callback) {
		JsonRequest request = new JsonRequest(what, url, callback);
		enqueue(request);
	}
	*/
	
	
	
	
	
	public interface Listener{
		public void onResult(Object result);
	}

	public void requestJson(int what, HttpRequest<?> request, JsonListener callback) {
		
		
	}
	
	
	
	

}
