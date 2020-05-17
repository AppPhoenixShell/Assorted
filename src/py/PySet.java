package py;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class PySet<T> implements Iterable<T>
{
	private Set<T> set = new HashSet<>();
	private Queue<T> stack = new LinkedList<>();
	
	public void add(T element) {
		if(notin(element))
			stack.add(element);
		set.add(element);
	}
	public void del(T element) {
		set.remove(element);
		stack.remove(element);
	}
	
	public boolean in(T element) {
		return set.contains(element);
	}
	public boolean notin(T element) {
		return !set.contains(element);
	}
	public boolean notinAdd(T element) {
		boolean notin = notin(element);
		add(element);
		return notin;
	}
	public boolean isEmpty() {
		return set.isEmpty();
	}
	public boolean notEmpty() {
		return !set.isEmpty();
	}
	public T pop() {
		T next = stack.poll();
		set.remove(next);
		
		return next;
	}
	public int size() {
		return set.size();
	}
	@Override
	public Iterator<T> iterator() {
		return stack.iterator();
	}
	
	@Override 
	public String toString() {
		return set.toString();
	}
}
