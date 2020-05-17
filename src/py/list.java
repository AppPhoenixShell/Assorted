package py;

public interface list extends Iterable<let> {
	public void add(let o);
	public boolean in(let o);
	public boolean notin(let o);
	public void del(let o);
	public void add(Number o);
	public boolean isEmpty();
	public boolean notEmpty();
	public let pop();
	public int size();
	public let len();
}
