package py;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class PyList<T> implements Iterable<T>
{
	private List<T> array = new ArrayList<>();
	private T defaultElement;
	
	private List<T> markfordel = new LinkedList<>();
	
	public PyList(T defaultElement) {
		this.defaultElement = defaultElement;
	}
	public PyList()	{
		this(null);
	}
	private void clearmarked() {
		if(markfordel.size() == 0)
			return;
		array.removeAll(markfordel);
		markfordel.clear();
	}
	public boolean isEmpty() {
		clearmarked();
		return array.isEmpty();
	}
	public boolean notEmpty() {
		return !isEmpty();
	}
	public void add(PyList<T> another) {
		for(T el : another)
			add(el);
	}
	public void add(List<T> list) {
		for(T el : list)
			add(el);
	}
	
	public void add(T...ts) {
		for(T o : ts)
			add(o);
	}
	public void add(T element) {
		array.add(element);
	}
	public boolean in(T element) {
		for(T item : array)
			if(item.equals(element))
				return true;
		return false;
	}
	public boolean inAdd(T element) {
		boolean in = in(element);
		add(element);
		return in;
	}
	public int count(T element) {
		int count = 0;
		for(T s : array)
			if(s.equals(element))
				++count;
		return count;
	}
	
	public void del(T element) {
		markfordel.add(element);
	}
	
	public boolean notinAdd(T element) {
		boolean notin = notin(element);
		if(notin)
			add(element);
		return notin;
	}
	
	public boolean notin(T element) {
		return !in(element);
	}
	
	public boolean isDef(T element) {
		return defaultElement == element;
	}
	
	public T getSafe(int index) {
		if(index < 0 || index >= array.size())
			return defaultElement;
		return get(index);
	}
	
	public T get(int index) {
		clearmarked();
		return array.get(index);
	}
	public int size() {
		clearmarked();
		return array.size();
	}
	
	@Override
	public Iterator<T> iterator() {
		clearmarked();
		return array.iterator();
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append('[');
		if(size() > 0)
			builder.append(get(0));
		for(int i=1; i < size(); i++) {
			builder.append(", ");
			builder.append(get(i));
		}
		builder.append(']');
		return builder.toString();
	}
	
}
