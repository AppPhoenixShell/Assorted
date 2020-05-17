package py;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import rss.Sha256;

public class PyRealtimeSet extends PySet<String> {
	
	private String fireRef;
	private boolean loaded = false;
	
	
	public PyRealtimeSet(String fireRef) {
		this.fireRef = fireRef;
	}

	
	public void addNetwork(String element) {
		add(element);
		String hash = Sha256.hash(element);
		FirebaseDatabase.getInstance().getReference(hash).setValueAsync(element);
	}

	public void load(PyDone done) {
		DatabaseReference ref = FirebaseDatabase.getInstance().getReference(fireRef);
		ref.addValueEventListener(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				for(DataSnapshot child : snapshot.getChildren()) {
					String element = child.getValue(String.class);
					add(element);
					Py.prints("HASH: %s - %s\n", child.getKey(), child.getValue(String.class));
				}
				done.onDone();
			}
			@Override
			public void onCancelled(DatabaseError error) {
				
			}
		});
	}
}
