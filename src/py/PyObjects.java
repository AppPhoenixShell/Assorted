package py;

public class PyObjects extends PyList<Object>
{
	public <T> PyObjects filterObjects(Class<T> aclass) {
		PyObjects objects = new PyObjects();
		for(Object o : this)
			if(aclass.isInstance(o))
				objects.add(o);
		
		return objects;
	}
	public <T> PyList<T> filterList(Class<T> aclass){
		PyList<T> newList = new PyList<>();
		for(Object o : this)
			if(aclass.isInstance(o)) {
				T cast = (T)o;
				newList.add(cast);
			}
		return newList;
	}
	public PyStrings filterStrings() {
		PyStrings strings = new PyStrings();
		PyList<String> filtered = filterList(String.class);
		strings.add(filtered);
		return strings;
	}
	
	public boolean isInt(int index) {
		return get(index) instanceof Integer;
	}
	
	public boolean isFloat(int index) {
		return get(index) instanceof Float;
	}
	public boolean isDouble(int index) {
		return get(index) instanceof Double;
	}
	
	public boolean isString(int index) {
		return get(index) instanceof String;
	}
	
	public boolean isShort(int index) {
		return get(index) instanceof Short;
	}
	
	public <T> boolean is(int index, Class<T> aclass) {
		return aclass.isInstance(get(index));
	} 

}
