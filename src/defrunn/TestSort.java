package defrunn;

public class TestSort implements Comparable<TestSort>
{
	private int number;
	public String msg;
	
	public TestSort(int number, String msg) {
		this.number = number;
		this.msg = msg;
	}

	@Override
	public int compareTo(TestSort o) {
		return number - o.number;
	}
}
