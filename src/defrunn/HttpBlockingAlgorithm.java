package defrunn;

public interface HttpBlockingAlgorithm
{
	public void bytesDownloaded(int bytes);

	public void startRequest();
	public void endRequest();
}
