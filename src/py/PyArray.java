package py;

public class PyArray extends PyList<let> implements list {

	@Override
	public void add(Number o) {
		num n = new num(o);
		add(n);
		
	}

	@Override
	public let pop() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public let len() {
		// TODO Auto-generated method stub
		return null;
	}

}
