import java.lang.reflect.Field;

import py.Py;
import testapp.MyRecord;
import testapp.PyField;
import testapp.PyWriter;

public class SerialTest {

	public static void main(String[] args) {
		PyWriter.register(MyRecord.class);
		
		MyRecord rec = MyRecord.values("jason", "linley", 25);
		
		Class<?> aclass = rec.getClass();
		
		Field[] fields = aclass.getDeclaredFields();
		
		for(Field f : fields){
			Py.print(f.getName());
			
			PyField s = f.getAnnotation(PyField.class);
		
			Class<?> t = f.getType();
			
			if(t == Integer.TYPE)
				Py.print("is Integer");
			else if(t == String.class)
				Py.print("is String");

			else if(t.equals(Float.TYPE))
				Py.print("is float");
			else if(t.equals(Double.TYPE))
				Py.print("is Double");
			
			
		
			
			
			if(s != null)
				Py.print("has pyserial");
			else
				Py.print("no Py Serial");
		}
		
		

	}

}
