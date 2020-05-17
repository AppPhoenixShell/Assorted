package compliance;

public class NonCompliance extends Exception{
	
	private static final long serialVersionUID = 3302567114515540206L;
	
	public Runnable handler;
	
	public NonCompliance(Runnable handler) {
		this.handler = handler;
	}

}
