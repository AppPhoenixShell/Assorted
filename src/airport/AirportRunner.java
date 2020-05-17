package airport;

import py.Py;
import py.PyMap;
import py.let;
import py.map;

public class AirportRunner {

	public static void main(String[] args) {
		
		JArray array = new JArray();
		
		JMap map = new JMap();
		map.put("airport_name", "Melbourne");
		map.put("location/lng", 12f);
		map.put("location/lng", 89f);
		
		array.add(map);
		
		Py.print(array);
		
	}

}
