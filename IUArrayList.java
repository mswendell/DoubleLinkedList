import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Array-based implementation of IndexedUnsortedList.
 * An Iterator with working remove() method is implemented, but
 * ListIterator is unsupported. 
 * 
 * @author Michael Wendell - CS221
 *
 * @param <T> type of element to store
 */
public class IUArrayList<T> implements IndexedUnsortedList<T> {
	private static final int DEFAULT_CAPACITY = 10;
	private static final int NOT_FOUND = -1;
	
	private T[] array;
	private int rear;
	private int modCount;
	
	/** Creates an empty list with default initial capacity */
	public IUArrayList() {
		this(DEFAULT_CAPACITY);
	}
	
	/** 
	 * Creates an empty list with the given initial capacity
	 * @param initialCapacity
	 */
	@SuppressWarnings("unchecked")
	public IUArrayList(int initialCapacity) {
		array = (T[])(new Object[initialCapacity]);
		rear = 0;
		modCount = 0;
	}
	
	/** Double the capacity of array */
	private void expandCapacity() {
		array = Arrays.copyOf(array, array.length*2);	
	}
	
	/**
	 * Check if the current array will need to be expanded before adding another element.
	 * If it does, it will call the method to expand it.  
	 */
	private void expandIfNecessary() {
		if (rear == array.length)
			expandCapacity();
	}

	@Override
	public void addToFront(T element) {
		expandIfNecessary();
		for(int i = rear; i > 0; i--) {
			array[i] = array[i - 1];
		}
		rear++;
		array[0] = element;
		modCount++;
	}

	@Override
	public void addToRear(T element) {
		expandIfNecessary();
		array[rear] = element;
		rear++;
		modCount++;
	}

	@Override
	public void add(T element) {
		addToRear(element);
	}

	@Override
	public void addAfter(T element, T target) {
		int index = indexOf(target);
		if (index == NOT_FOUND) {
			throw new NoSuchElementException();
		}
		add(index + 1, element);
	}

	@Override
	public void add(int index, T element) { 
		if (index < 0 || index > rear) {
			throw new IndexOutOfBoundsException();
		}
		expandIfNecessary();
		for(int i = rear; i > index; i--) {
			array[i] = array[i - 1];
		}
		array[index] = element;
		rear++;
		modCount++;
	}

	@Override
	public T removeFirst() {
		if (rear == 0)
			throw new NoSuchElementException();
		T retVal = remove(array[0]);
		return retVal;
	}

	@Override
	public T removeLast() {
		if (rear == 0)
			throw new NoSuchElementException();
		T retVal = remove(array[rear-1]);
		return retVal;
	}

	@Override
	public T remove(T element) {
		int index = indexOf(element);
		if (index == NOT_FOUND) {
			throw new NoSuchElementException();
		}
		T retVal = array[index];
		rear--;
		for (int i = index; i < rear; i++) {
			array[i] = array[i+1];
		} 
		array[rear] = null;
		modCount++;
		return retVal;
	}

	@Override
	public T remove(int index) {
		if (index < 0 || index >= rear)
			throw new IndexOutOfBoundsException();
		T retVal = array[index];
		rear--;
		for (int i = index; i < rear; i++) {
			array[i] = array[i+1];
		} 
		array[rear] = null;
		modCount++;
		return retVal;
	}

	@Override
	public void set(int index, T element) {
		if (index < 0 || index >= rear)
			throw new IndexOutOfBoundsException();
		array[index] = element;
		modCount++;
	}

	@Override
	public T get(int index) {
		if (index < 0 || index >= rear)
			throw new IndexOutOfBoundsException();
		return array[index];
	}

	@Override
	public int indexOf(T element) {
		int index = NOT_FOUND;
		
		if (!isEmpty()) {
			int i = 0;
			while (index == NOT_FOUND && i < rear) {
				if (element.equals(array[i])) {
					index = i;
				} else {
					i++;
				}
			}
		}
		
		return index;
	}

	@Override
	public T first() {
		if (rear == 0)
			throw new NoSuchElementException();
		return array[0];
	}

	@Override
	public T last() {
		if (rear == 0)
			throw new NoSuchElementException();
		return array[rear-1];
	}

	@Override
	public boolean contains(T target) {
		return (indexOf(target) != NOT_FOUND);
	}

	@Override
	public boolean isEmpty() {
		return rear == 0;
	}

	@Override
	public int size() {
		return rear;
	}
	
	/**
	 * toString method to return a string of comma separated elements currently inside the array with surrounding brackets.
	 * @return String a string of elements inside the array.
	 */
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("[");
		for (int i = 0; i < rear; i++) {
			str.append(array[i]);
			str.append(", ");
		}
		if(rear > 0)
			str.delete(str.length() - 2, str.length());
		str.append("]");
		return str.toString();
	}

	@Override
	public Iterator<T> iterator() {
		return new ALIterator();
	}

	@Override
	public ListIterator<T> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		throw new UnsupportedOperationException();
	}

	/** Iterator for IUArrayList */
	private class ALIterator implements Iterator<T> {
		private int nextIndex;
		private int iterModCount;
		private boolean canRemove;
		
		public ALIterator() {
			nextIndex = 0;
			iterModCount = modCount;
			canRemove = false;
		}

		@Override
		public boolean hasNext() {
			if (iterModCount != modCount)
				throw new ConcurrentModificationException();
			return nextIndex < rear;
		}

		@Override
		public T next() {
			if (!hasNext())
				throw new NoSuchElementException();
			nextIndex++;
			canRemove = true;
			return array[nextIndex - 1];
		}
		
		/**
		 * remove method will remove a value after next() is called.
		 * @throws ConcurrentModificationException if the array has been modified while the iterator was created.
		 * @throws IllegalStateException if next has not been called before remove was called.
		 */
		@Override
		public void remove() {
			if (iterModCount != modCount)
				throw new ConcurrentModificationException();
			if (canRemove == false)
				throw new IllegalStateException();
			rear--;
			for (int i = nextIndex - 1; i < rear; i++) {
				array[i] = array[i+1];
			}
			array[rear] = null;
			modCount++;		
			iterModCount++;
			canRemove = false;
			nextIndex = nextIndex-1;
		}
	}
}
