package py;

public interface let extends Iterable<let> 
{
	public static final let FALSE = new PyBool(false);
	public static final let TRUE = new PyBool(true);
	
}
