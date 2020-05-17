package testapp;

@PyClass(id= 0)
public class MyRecord
{
	
	@PyField(id = 0) private String fname;
	@PyField(id = 1) private String lname;
	@PyField(id = 2) private int age;
	@PyField(id = 3) private String debug = "";
	@PyField(id = 4) private float popcorn;
	@PyField(id = 5) private double dob;	
	
	private MyRecord() {
		
	}
	
	public static MyRecord values(String fname, String lname, int age) {
		MyRecord rec = new MyRecord();
		rec.fname = fname;
		rec.lname = lname;
		rec.age = age;
		return rec;
	}
}
