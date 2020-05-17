package py;

public abstract class PyApp extends PyContext
{
	private static final int WHAT_ALARM = 1;
	private String appName;
	private PyHandler handler;
	
	public abstract void onCreate();
	public abstract String appid();
	public abstract PyCallback callback();
	
	public void main() {
		create();
	}
	
	public void create() {
		String appName = appid();
		this.appName = appName;
		this.handler = new PyHandler(callback(), Py.mainLooper());
		onCreate();
	}
	
	protected PyFile open(String name, char mode) {
		PyFile file = PyFile.open("apps/"+appName +"/"+name, mode);
		return file;
				
	}
	
	protected void alarm(long delay, PyMessage message) {
		message.when = System.currentTimeMillis() + delay;
		message.handler = handler;
		
		handler.post(message);
	}
}
