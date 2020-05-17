package testapp;

public class PyWriter {
	
	
	public static void register(Class<?> aclass) {
		PyClass def = aclass.getAnnotation(PyClass.class);
		if(def == null)
			throw new RuntimeException("cannot serialise a class with not pyclass annotation");
		
		
	}
}
