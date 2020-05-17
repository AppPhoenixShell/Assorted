package defrunn;

public class HttpSleep implements HttpBlockingAlgorithm
{

	private long sometime;
	
	public HttpSleep(long sleep) {
		sometime = sleep;
	}
	
	@Override
	public void bytesDownloaded(int bytes) {
		
	}

	@Override
	public void startRequest() {
		
	}

	@Override
	public void endRequest() {
		try {
			Thread.sleep(sometime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
