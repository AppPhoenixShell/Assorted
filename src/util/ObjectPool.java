package util;

public class ObjectPool<T extends Poolable> 
{
	private PoolRef<T>[] array;
	private int index;
	
	@SuppressWarnings("unchecked")
	public ObjectPool(int capacity, Class<T> clazz) throws InstantiationException, IllegalAccessException {
		array = new PoolRef[capacity];
		for(int i=0; i <array.length; i++){
			array[i] = new PoolRef<T>(clazz.newInstance(), i);
		}
	}
	private void returnToPool(PoolRef<T> ref) {
		
	}
	public PoolRef<T> obtain(){
		return array[index++];
	}
	
	public static class PoolRef<T extends Poolable>
	{
		private T  ref;
		private ObjectPool<T> owner;
		private int index;
		private boolean isUse;
		
		public PoolRef(T object, int index) {
			this.ref = object;
			this.index = index;
		}
		
		public T get() {
			return ref;
		}
		private void acquire() {
			isUse = true;
		}
		
		public void relase() {
			owner.returnToPool(this);
		}
	}
}
