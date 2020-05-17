package collection;

import java.lang.reflect.Array;

public class ClockArray<T>
{
	private T[] array;
	private int index;
	private int count;
	private int indexStart = 0;
	
	@SuppressWarnings("unchecked")
	public ClockArray(int size, Class<T> arrayClass) {
		array = (T[]) Array.newInstance(arrayClass, size);
	}
	
	public int length() {
		return array.length;
	}
	
	public T get(int index) {
		index = (index + indexStart) % array.length;
		return array[index];
	}
	
	public void add(T element) {
		array[index] = element;
		
		if(count == array.length) {
			indexStart = (indexStart + 1) % array.length; 
		}	
		index = (index + 1) % array.length;
		count = count + 1 > array.length ? array.length : count + 1;
	}
	
	
	public void cpy(T[] destArray) {
		int cpysize = Math.min(array.length, destArray.length);
		System.arraycopy(array, 0, destArray, 0, cpysize);
	}


	public int size() {
		return count;
	}
}
