****************
* DoubleLinkedList
* CS 221
* 4/8/2022
* Michael Wendell
**************** 

OVERVIEW:

The IUDoubleLinkedList is an implementation of an IndexedUnsortedList that uses DoubleLinkedNodes to store and access 
list elements. It also features an Iterator and ListIterator class to navigate the list.

INCLUDED FILES:

* IUDoubleLinkedList.java - source file
* IndexedUnsortedList.java - source file
* DoubleLinkedNode.java - source file
* ListTester.java - source file
* README - this file

COMPILING AND RUNNING:

From the directory containing all source files, compile the test
 class (and all dependent classes) with the command:
 $ javac ListTester.java

 Run the compiled ListTester class with the command:
 $ java ListTester

Console output will report which tests the IUDoubleLinkedList passed or failed. A fully functional list with 
ListIterator will pass 100% of the tests. 


PROGRAM DESIGN AND IMPORTANT CONCEPTS:

The IUDoubleLinkedList class is an implementation of the list class that can manage storage for any number of 
objects for a user to store and access. The IUDoubleLinkedList, unlike other implementations of the IndexedUnsortedList, 
uses a DoubleLinkedNode class that can store references to the desired element, the next node in the list, and the 
previous node in the list. This functionality allows the IUDoubleLinkedList to be used efficiently as a stack, queue, or
 list. 

The IUDoubleLinkedList will also store references to the first and last Nodes in a list, which allows the algorithm to 
have a constant amount of operations needed for adding and removing from either the front or rear of the list. However, 
the IUDoubleLinkedList can be slower than other list implementations for getting or setting any elements in the middle 
of the list since the algorithm will need to iterate through each element to get to a desired index. This would make 
the methods for adding or removing an element or at an index an O(n/2) operation. This is the same for the get, set,  
and indexing methods.

The IUDoubleLinkedList utilizes a ListIterator that will allow for quick navigation of the list as well as reducing the 
amount of statements needed to access the elements in the middle of the list. The ListIterator adds the ability to navigate 
through the list from the front or the back and allows for quicker adding, removing, or changing elements at each point.
For this functionality, every method that modifies the IUDoubleLinkedList uses the ListIterator class to make changes to 
the list. The ListIterator also includes a constructor to start at any chosen index and the included algorithm can iterate
from the rear or front of the list based on index location. This reduces the average amount of statements for most methods 
from O(n/2) to O(n/4).

The included ListTester class confirms that the IUDoubleLinkedList methods will operate correctly for a variety of change 
scenarios expected in operation. The ListTester tests each method for 1 to 3 element lists to simulate adding, removing, or 
changing elements in the list. The current ListTester is only configured to run with an IUDoubleLinkedList.

TESTING:

The primary testing for the IUDoubleLinkedList was done using the ListTester class. This was Test-Driven development being 
that the tester and scenarios were written beforehand and then the IUDoubleLinkedList class was created and debugged using 
the tester. This helped ensure that the list met all functionality needed to operate under most conditions. The testing 
includes method calls from 1 element lists up to 3 element lists.

The ListTester currently tests scenarios using all the available methods at least once for each a 1 element list, 2 element 
list and 3 element list. The tester also includes tests that result in cases where exceptions will be thrown to ensure the 
list will act correctly when given bad input. All 9000 tests from the tester currently pass for my version of the 
IUDoubleLinkedList, so I can assume with a fair amount of certainty there are little to no bugs remaining in my list. 


