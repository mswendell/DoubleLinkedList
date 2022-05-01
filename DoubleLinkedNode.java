/**
 * Node represents a node in a linked list to both the previous node and the one that follows. 
 *
 * @author Java Foundations, mvail, mwendell
 * @version 4.0
 */
public class DoubleLinkedNode<E> {
	private DoubleLinkedNode<E> next;
	private DoubleLinkedNode<E> prev;
	private E element;

	/**
  	 * Creates an empty node.
  	 */
	public DoubleLinkedNode() {
		next = null;
		prev = null;
		element = null;
	}

	/**
  	 * Creates a node storing the specified element.
 	 *
  	 * @param elem
  	 *            the element to be stored within the new node
  	 */
	public DoubleLinkedNode(E elem) {
		next = null;
		prev = null;
		element = elem;
	}
	
	/**
  	 * Creates a node storing the specified element, the next node, and the previous node.
 	 *
  	 * @param elem
  	 *            the element to be stored within the new node
  	 */
	public DoubleLinkedNode(E elem, DoubleLinkedNode<E> next, DoubleLinkedNode<E> prev) {
		this.next = next;
		this.prev = prev;
		element = elem;
	}

	/**
 	 * Returns the node that follows this one.
  	 *
  	 * @return the node that follows the current one
  	 */
	public DoubleLinkedNode<E> getNext() {
		return next;
	}

	/**
 	 * Sets the node that follows this one.
 	 *
 	 * @param node
 	 *            the node to be set to follow the current one
 	 */
	public void setNext(DoubleLinkedNode<E> node) {
		next = node;
	}

	/**
 	 * Returns the element stored in this node.
 	 *
 	 * @return the element stored in this node
 	 */
	public E getElement() {
		return element;
	}

	/**
 	 * Sets the element stored in this node.
  	 *
  	 * @param elem
  	 *            the element to be stored in this node
  	 */
	public void setElement(E elem) {
		element = elem;
	}

	@Override
	public String toString() {
		return "Element: " + element.toString() + " Has next: " + (next != null);
	}
	
	/**
 	 * Returns the node that precedes this one.
  	 *
  	 * @return the node that precedes the current one
  	 *
  	 */
	public DoubleLinkedNode<E> getPrev() {
		return prev;
	}

	/**
 	 * Sets the node that comes before this one.
 	 *
 	 * @param node the node to be set to precede the current one
 	 */
	public void setPrev(DoubleLinkedNode<E> prev) {
		this.prev = prev;
	}
}

