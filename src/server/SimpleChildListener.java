package server;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public abstract class SimpleChildListener implements ChildEventListener {

	@Override
	public abstract void onChildAdded(DataSnapshot snapshot, String previousChildName);

	@Override
	public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChildRemoved(DataSnapshot snapshot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCancelled(DatabaseError error) {
		// TODO Auto-generated method stub
		
	}

}
