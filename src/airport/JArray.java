package airport;

import java.util.ArrayList;
import java.util.List;

public class JArray {
	private List<JMap> array = new ArrayList<>();
	
	public void add(JMap map) {
		array.add(map);
	}	
	@Override
	public String toString() {
		return array.toString();
	}
}
