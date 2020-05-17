package himawari.server;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public abstract class ServerModule {

	private HimawariServer server;
	
	public abstract void onCreate(DatabaseReference root);
	
	public void create(HimawariServer server) {
		this.server = server;
		DatabaseReference root = FirebaseDatabase.getInstance().getReference();
		onCreate(root);
	}
	
	public HimawariServer getServer() {return server;}
}
