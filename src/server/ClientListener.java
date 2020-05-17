package server;

import com.google.firebase.database.DataSnapshot;

public interface ClientListener{
	public void onResponse(String uuid, int type, DataSnapshot object);
}
