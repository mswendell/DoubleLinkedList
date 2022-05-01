import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * The IUDoubleLinkedList is an implementation of IndexedUnsortedList that uses DoubleLinkedNodes 
 * to store and access list elements. It also features an Iterator and ListIterator class to navigate the list.
 * @version CS 221 Michael Wendell
 * @author mwendell
 * @param <T> Any element T to be stored by the List
 */
public class IUDoubleLinkedList<T> implements IndexedUnsortedList<T> {
	private DoubleLinkedNode<T> head, tail;
	private int size;
	private int modCount;
	
	/**
	 * Default constructor that creates an empty list and initializes all variables.
	 */
	public IUDoubleLinkedList() {
		head = tail = null;
		size = 0;
		modCount = 0;
	}

	@Override
	public void addToFront(T element) {
		ListIterator<T> lit = listIterator();
		lit.add(element);
	}

	@Override
	public void addToRear(T element) {
		ListIterator<T> lit = listIterator(size);
		lit.add(element);
	}

	@Override
	public void add(T element) {
		addToRear(element);		
	}

	@Override
	public void addAfter(T element, T target) {
		ListIterator<T> lit = listIterator();
		boolean foundIt = false;
		while (lit.hasNext() && !foundIt) {
			if (lit.next().equals(target)) {
				lit.add(element);
				foundIt = true;
			}
		}
		if (!foundIt)
			throw new NoSuchElementException();
	}

	@Override
	public void add(int index, T element) {
		ListIterator<T> lit = listIterator(index);
		lit.add(element);
	}

	@Override
	public T removeFirst() {
		ListIterator<T> lit = listIterator();
		T retVal = lit.next();
		lit.remove();
		
		return retVal;
	}

	@Override
	public T removeLast() {
		ListIterator<T> lit = listIterator(size);
		T retVal = lit.previous();
		lit.remove();
		
		return retVal;
	}

	@Override
	public T remove(T element) {
		ListIterator<T> lit = listIterator();
		T retVal = null;
		boolean foundIt = false;
		while (lit.hasNext() && !foundIt) {
			if (lit.next().equals(element)) {
				retVal = lit.previous();
				lit.remove();
				foundIt = true;
			}
		}
		if (!foundIt)
			throw new NoSuchElementException();
		return retVal;
	}

	@Override
	public T remove(int index) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException();
		ListIterator<T> lit = listIterator(index);
		T retVal = lit.next();
		lit.remove();
		return retVal;
	}

	@Override
	public void set(int index, T element) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException();
		ListIterator<T> lit = listIterator(index);
		lit.next();
		lit.set(element);
	}

	@Override
	public T get(int index) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException();
		ListIterator<T> lit = listIterator(index);
		return lit.next();
	}

	@Override
	public int indexOf(T element) {
		int index = -1;
		boolean indexFound = false;
		DoubleLinkedNode<T> current = head;
		while(current != null && indexFound != true) {
			if(current.getElement().equals(element))
				indexFound = true;
			index++;
			current = current.getNext();
		}
		if (indexFound == false)
			index = -1;
		return index;
	}

	@Override
	public T first() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return head.getElement();
	}

	@Override
	public T last() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return tail.getElement();
	}

	@Override
	public boolean contains(T target) {
		return indexOf(target) != -1;
	}

	@Override
	public boolean isEmpty() {
		return head == null;
	}

	@Override
	public int size() {
		return size;
	}
	
	/**
	 * toString method to return a string of comma separated elements currently inside the array with surrounding brackets.
	 * @return String a string of elements inside the array.
	 */
	@Override
	public String toString() {
		ListIterator<T> lit = listIterator();
		StringBuilder str = new StringBuilder();
		str.append("[");
		while (lit.hasNext()) {
			str.append(lit.next());
			str.append(", ");
		}
		if(size > 0)
			str.delete(str.length() - 2, str.length());
		str.append("]");
		return str.toString();
	}

	@Override
	public Iterator<T> iterator() {
		return new DLLIterator();
	}

	@Override
	public ListIterator<T> listIterator() {
		return new DLLIterator();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		return new DLLIterator(startingIndex);
	}
	
	/** 
	 * Iterator for IUDoulbeLinkedList that also acts as a basic iterator. 
	 */
	private class DLLIterator implements ListIterator<T> {
		private DoubleLinkedNode<T> nextNode;
		private int iterModCount;
		private int nextIndex;
		private DoubleLinkedNode<T> returnedNode;
		
		/** 
		 * Creates a new iterator for the list that starts before the first index.
		 */
		public DLLIterator() {
			nextNode = head;
			iterModCount = modCount;
			nextIndex = 0;
			returnedNode = null;
		}
		
		/**
		 * Creates a new iterator for the list that starts from the indicated index.
		 * @param index the starting index for the iterator
		 */
		public DLLIterator(int startingIndex) {
			if (startingIndex < 0 || startingIndex > size)
				throw new IndexOutOfBoundsException();
			iterModCount = modCount;
			nextNode = head;
			if (startingIndex < size / 2) {
				for (int i = 0; i < startingIndex; i++) {
					nextNode = nextNode.getNext();
				}
			}
			else {
				if (startingIndex == size)
					nextNode = null;
				else {
					nextNode = tail; 
					for (int i = 0; i < size-startingIndex - 1; i++) {
						nextNode = nextNode.getPrev();
					}
				}
			}
			nextIndex = startingIndex;
			returnedNode = null;
		}

		@Override
		public boolean hasNext() {
			if (iterModCount != modCount)
				throw new ConcurrentModificationException();
			return nextNode != null;
		}

		@Override
		public T next() {
			if (!hasNext())
				throw new NoSuchElementException();
			T retVal = nextNode.getElement();
			returnedNode = nextNode;
			nextNode = nextNode.getNext();
			nextIndex++;
			return retVal;
		}

		@Override
		public boolean hasPrevious() {
			if (iterModCount != modCount)
				throw new ConcurrentModificationException();
			return nextNode != head;
		}

		@Override
		public T previous() {
			if (!hasPrevious())
				throw new NoSuchElementException();
			if (nextNode == null)
				nextNode = tail;
			else
				nextNode = nextNode.getPrev();
			returnedNode = nextNode;
			nextIndex--;
			return nextNode.getElement();
		}

		@Override
		public int nextIndex() {
			if (iterModCount != modCount)
				throw new ConcurrentModificationException();
			return nextIndex;
		}

		@Override
		public int previousIndex() {
			if (iterModCount != modCount)
				throw new ConcurrentModificationException();
			return nextIndex - 1;
		}
	
		@Override
		public void remove() {
			if (iterModCount != modCount)
				throw new ConcurrentModificationException();
			if (returnedNode == null)
				throw new IllegalStateException();
			if (returnedNode != tail) {
				returnedNode.getNext().setPrev(returnedNode.getPrev());
			}
			else {
				tail = returnedNode.getPrev();
			}
			if (returnedNode != head) {
				returnedNode.getPrev().setNext(returnedNode.getNext());
			}
			else {
				head = returnedNode.getNext();
			}
			if (returnedNode != nextNode) {
				nextIndex--;
			}
			else {
				nextNode = nextNode.getNext();
			}
			returnedNode = null;
			size--;
			iterModCount++;
			modCount++;
		}
		
		@Override
		public void set(T e) {
			if (iterModCount != modCount)
				throw new ConcurrentModificationException();
			if (returnedNode == null)
				throw new IllegalStateException();
			DoubleLinkedNode<T> newNode = new DoubleLinkedNode<T>(e);
			newNode.setNext(returnedNode.getNext());
			newNode.setPrev(returnedNode.getPrev());
			if(returnedNode.getNext() != null) {
				returnedNode.getNext().setPrev(newNode);
			}
			else {
				tail = newNode;
			}
			if (returnedNode.getPrev() != null) {
				returnedNode.getPrev().setNext(newNode);		
			}
			else {
				head = newNode;
			}
			returnedNode = null;
			modCount++;
			iterModCount++;
		}

		@Override
		public void add(T e) {
			if (iterModCount != modCount)
				throw new ConcurrentModificationException();
			DoubleLinkedNode<T> newNode = new DoubleLinkedNode<T>(e);
			if (nextNode != null) {
				newNode.setPrev(nextNode.getPrev());
				nextNode.setPrev(newNode);
				newNode.setNext(nextNode);
			}
			else {
				newNode.setPrev(tail);
				tail = newNode;
			}
			if(newNode.getPrev() != null) {
				newNode.getPrev().setNext(newNode);
			}
			else {
				head = newNode;
			}
			size++;
			nextIndex++;
			modCount++;
			iterModCount++;
		}
	}
}
