package os;

import java.util.UUID;

public class Token 
{
	public static final int UNKNOWN =-1;
	public static final int TYPE_ALARM = 0;
	public static final int TYPE_GEOFENCE = 1;
	
	
	private String uuid;
	private int type;
	private TokenDestroyedListener dlistener;
	private boolean isDestoryed = false;
	
	public Token(String token) {
		this.type = UNKNOWN;
		this.uuid = token;
	}
	
	protected Token(int type, TokenDestroyedListener listener) {
		this.uuid = UUID.randomUUID().toString();
		this.type= type;
		this.dlistener = listener;
	}
	
	

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof String) {
			String sob = (String)obj;
			return uuid.equals(sob);
		}
		else if(obj instanceof Token) {
			Token tok = (Token)obj;
			return tok.uuid.equals(uuid);
		}
		return uuid.equals(obj);
	}

	@Override
	public int hashCode() {
		return uuid.hashCode();
	}
	public String toString() {
		return uuid;
	}
	
	public void destroy() {
		if(isDestoryed)
			return;
		if(dlistener != null)
			dlistener.onDestoryToken(this);
		isDestoryed = true;
		dlistener = null;
	}
	
	
	public static interface TokenDestroyedListener{
		public void onDestoryToken(Token token);
	}


	public String getUUID() {
		return uuid;
	}
}
