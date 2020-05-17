package py;

public class PyLetSet extends PySet<let> implements list {

	@Override
	public void add(Number o) {
		add(new num(o));
		
	}

	@Override
	public let len() {
		// TODO Auto-generated method stub
		return new num(size());
	}

}
