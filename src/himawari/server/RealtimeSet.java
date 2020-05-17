package himawari.server;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.math3.util.Pair;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.ValueEventListener;

import rss.FeedMessage;
import rss.Sha256;

public class RealtimeSet {

	private Set<String> hashes;
	private boolean isLoaded;
	private DatabaseReference root;
	
	private boolean pendingLoad;
	private OnLoadedListener loadListener;
	
	private ValueEventListener loadHash = new ValueEventListener() {
		
		@Override
		public void onDataChange(DataSnapshot snapshot) {
			hashes.clear();
			Iterable<DataSnapshot> children = snapshot.getChildren();
			
			List<Pair<String,String>> valueList= new ArrayList<>();
			
			for(DataSnapshot item : children) {
				String hash = item.getKey();
				String value = item.getValue(String.class);
				
				Pair<String, String> hashKey = new Pair<>(hash, value);
				valueList.add(hashKey);
				//System.out.println(msg);
				hashes.add(hash);
			}
			
			loadListener.onLoaded(valueList);
			isLoaded = true;
		}
		
		@Override
		public void onCancelled(DatabaseError error) {
			
		}
	};
	
	private CompletionListener hashComplete = new CompletionListener() {
		
		@Override
		public void onComplete(DatabaseError error, DatabaseReference ref) {
			
			
		}
	};
	
	
	public void put(String key) {
		String hash = Sha256.hash(key);
		hashes.add(hash);
		root.child(hash).setValue(key, hashComplete);
		
		
	}
	public boolean exists(String key)
	{
		return hashes.contains(Sha256.hash(key));
	}
	
	public RealtimeSet(DatabaseReference root) {
		hashes = new HashSet<String>();
		this.root= root;
	
	}

	public boolean isLoaded() {return isLoaded;}
	
	public void load(OnLoadedListener listener) {
		if(pendingLoad || isLoaded)
			throw new IllegalStateException("Previous load already pending or already loaded");
	
		pendingLoad = true;
		this.loadListener = listener;
		
		
		root.addListenerForSingleValueEvent(loadHash);
	}
	
	public interface OnLoadedListener{
		void onLoaded(List<Pair<String, String>> hashmap);
	}
}
