package py;

public class PyStrings extends PyList<String>
{
	public PyStrings matches(PyReg regex) {
		PyStrings found = new PyStrings();
		
		for(String item : this)
			if(regex.matches(item))
				found.add(item);
		return found;
	}
	
	
}
