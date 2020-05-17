package py;

import java.io.IOException;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import rss.Sha256;
import util.ClockUtil;

public abstract class PyContext
{	
	public abstract void main();
	
	protected PyFile open(String filename, char mode) {
		PyFile file = PyFile.open(filename, mode);
		return file;
	}
	
	protected void loop() {
		PyLooper looper = Py.mainLooper();
		looper.loop();
	}
	
	protected void post(PyMessage message) {
		Py.mainLooper().post(message);
	}
	
	protected void print(String string) {
		System.out.println(string);
	}
	protected void print(Object object) {
		print(object.toString());
	}
	public void printl(String...args) {
		for(String s : args)
			print(s);
	}
	public void printl(Object...args) {
		for(Object o : args)
			print(o);
	}
	public void printf(String format, Object...args) {
		System.out.printf(format, args);
	}
	public void printk(String key, String value) {
		System.out.printf("key: %s\n", value);
	}
	public void printk(String key, Object o) {
		printk(key, o.toString());
	}
	public String timestamp() {
		return ClockUtil.getTimestamp();
	}
	public void logAwaiting() {
		log("...awaiting");
	}
	public void log(String value) {
		System.out.printf("%s - %s\n", timestamp(), value);
	}
	
	public PyDoc docurl(let url) {
		return docurl(url.toString());
	}
	
	public PyDoc docurl(String url) {
		return PyDoc.url(url);
		
	}
	public DatabaseReference fireref(String path) {
		return FirebaseDatabase.getInstance().getReference(path);
	}
	public DatabaseReference fireref() {
		return FirebaseDatabase.getInstance().getReference();
	}
	public void fireinit(String token, String url) {
		Py.initFirebase(token, url);
	}
	
	public void log(Object object) {
		log(object.toString());
	}
	public void sleep(long value, char mode) {
		long sleepVal = 0;
		switch(mode) {
		case 'm':sleepVal = value * 1000 * 60;break;
		case 's':sleepVal = value * 1000;break;
		case 'h':sleepVal = value * 1000 * 60 * 60;break;
		}
		
		try {
			Thread.sleep(sleepVal);
		} catch (InterruptedException e) {
			//TODO
		}
	}
	
	protected PyRss rssurl(String url) {
		return PyRss.url(url);
	}
	
	
	protected void alarm(long delay, PyMessage message, PyHandler handler) {
		message.when = System.currentTimeMillis() + delay;
		message.handler = handler;
		
		Py.post(message);
	}
	
	protected PyString str(String value) {
		return new PyString(value);
	}
	
	protected num num(let value) {
		return (num)value;
	}
	
	protected PyBool bool(boolean val) {
		return new PyBool(val);
	}
	
	protected num num(Number number) {
		return new num(number);
	}
	protected num add(let f1, let f2) {
		if(!(f1 instanceof num ) || !(f2 instanceof num))
			throw new RuntimeException("can only add nums together");
		num n1 = (num)f1;
		num n2 = (num)f2;
		
		if(n1.number instanceof Integer && n2.number instanceof Integer) {
			int nc1 = (int)n1.number;
			int nc2 = (int)n2.number;
			
			return num(nc1 + nc2);
		}
		return num(0);
	}
	
	protected boolean is(let val) {
		if(val instanceof PyBool) {
			PyBool v = (PyBool)val;
			return v.value;
		}
		if(val == null)
			return false;
		return true;
	}
	
	protected boolean not(let val) {
		return !is(val);
	}
	
	protected num add(list numbers) {
		num sum = num(0);
		for(let value : numbers) {
			num val = num(value);
			sum = add(val, sum);
		}
		return sum;
	}
	
	protected let concat(let let1, let let2) {
		return new PyString(let1.toString().concat(let2.toString()));
	}
	protected let concat(let let1, String value) {
		return concat(let1, new PyString(value));
	}
	
	protected rng random() {
		return new rng();
	}
	
	protected let contains(let l, String value) {
		if(l instanceof PyString) {
			PyString s = (PyString)l;
			return bool(s.value.contains(value));
		}
		return bool(false);
	}
	
	protected PyDoc scrap(let url) {
		PyDoc doc = null;
		doc = PyDoc.url(url.toString());
		return doc;
	}
	
	protected let hash(let value) {
		String hash = Sha256.hash(value.toString());
		return new PyString(hash);
	}
	
	
	
	
	protected void write(let file, Object object) {
		PyFile f = (PyFile)file;
		f.write(object);
	}
	
	
	protected void write(let file, let value) {
		PyFile f = (PyFile)file;
		f.write(value);
	}
	protected void close(let file) {
		PyFile f = (PyFile)file;
		f.close();
	}
	
	protected map map() {
		return new PyLetMap();
	}
	
	protected list array() {
		return new PyArray();
	}
	
	protected list set() {
		return new PyLetSet();
	}
}
