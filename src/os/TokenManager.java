package os;

public class TokenManager{
	
	private MessageHandler handler;
	
	public TokenManager(HimawariOS os) {
		handler = os.getHandler();
	} 
	
}
