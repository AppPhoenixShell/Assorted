import java.io.UnsupportedEncodingException;

public class SizeCalc
{
	private int bytes;
	
	
	public SizeCalc putInt(int number) {
		bytes += number * Integer.BYTES;
		return this;
	}
	public SizeCalc putShort(int number) {
		bytes += number * Short.BYTES;
		return this;
	}
	public SizeCalc putBytes(byte[] array) {
		bytes += array.length;
		return this;
	}
	public SizeCalc putLong(int number) {
		bytes += number * Long.BYTES;
		return this;
	}
	public SizeCalc putString(String value) {
		
		try {
			byte[] utf = value.getBytes("UTF-8");
			bytes += utf.length;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return this;
	}
	public int size() {
		// TODO Auto-generated method stub
		return bytes;
	}
}
