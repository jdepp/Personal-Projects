//***********************************************
// NAME: Jeremy Deppen
// CLASS: CS 445  9:30am lecture; 3pm Tues lab
// ASSIGNMENT: number 2
// DUE DATE: 2/14/2017
// THIS FILE: LinkedListPlus.java
//***********************************************




// CS 0445 Spring 2017 
// LinkedListPlus<T> class partial implementation

// See the commented methods below.  You must complete this class by
// filling in the method bodies for the remaining methods.  Note that you
// may NOT add any new instance variables, but you may use method variables
// as needed.

public class LinkedListPlus<T> extends A2LList<T>
{
	// Default constructor simply calls super()
	public LinkedListPlus()
	{
		super();
	}
	
	// Copy constructor.  This is a "deepish" copy so it will make new
	// Node objects for all of the nodes in the old list.  However, it
	// is not totally deep since it does NOT make copies of the objects
	// within the Nodes -- rather it just copies the references.
	public LinkedListPlus(LinkedListPlus<T> oldList)
	{
		super();
		if (oldList.getLength() > 0)
		{
			// Special case for first Node since we need to set the
			// firstNode instance variable.
			Node temp = oldList.firstNode;		// front of old list
			Node newNode = new Node(temp.data);	// copy the data
			firstNode = newNode;				// set front of new list
			
			// Now we traverse the old list, appending a new Node with
			// the correct data to the end of the new list for each Node
			// in the old list.  Note how the loop is done and how the
			// Nodes are linked.
			Node currNode = firstNode;
			temp = temp.next;
			while (temp != null)
			{
				currNode.next = new Node(temp.data);
				temp = temp.next;
				currNode = currNode.next;
			}
			numberOfEntries = oldList.numberOfEntries;
		}			
	}

	// Make a StringBuilder then traverse the nodes of the list, appending the
	// toString() of the data for each node to the end of the StringBuilder.
	// Finally, return the StringBuilder as a String.
	public String toString()
	{
		StringBuilder b = new StringBuilder();
		for (Node curr = firstNode; curr != null; curr = curr.next)
		{
			b.append(curr.data.toString());
			b.append(" ");
		}
		return b.toString();
	}

	// Shift left num positions.  This will shift out the first num Nodes
	// of the list, with Node num+1 now being at the front.
	public void leftShift(int num)
	{
		if (num >= this.getLength())	// If we shift more than the number of
		{								// Nodes, just initialize to empty
			firstNode = null;
			numberOfEntries = 0;
		}
		else if (num > 0)
		{
			Node temp = firstNode;		// Start at front
			for (int i = 0; i < num-1; i++)	 // Get to node BEFORE the one that 
			{								 // should be the new firstNode
				temp = temp.next;
			}
			firstNode = temp.next;		// Set firstNode to Node after temp
			temp.next = null;		    // Disconnect old prefix of list (just
										// to be extra cautious)
			numberOfEntries = numberOfEntries - num;	// Update logical size
		}
	}

	// Remove num items from the end of the list
	public void rightShift(int num)
	{
		if(num >= this.getLength())
		{
			firstNode = null;
			numberOfEntries = 0;
		}
		else if(num > 0)
		{
			Node temp = firstNode;
			for(int i = 0; i < numberOfEntries - num - 1; i++) //get to node two before
			{											   //the nodes we want to 	
				temp = temp.next;						   //start deleting
			}
			temp.next = null;		//breaks the reference leading to the nodes to be deleted
			numberOfEntries = numberOfEntries - num;
		}
	}

	// Remove from the front and add at the end.  Note that this method should
	// not create any new Node objects -- it is simply moving them.  If num
	// is negative it should still work, actually doing a right rotation.
	public void leftRotate(int num)
	{
		if(num > 0)  //positive number: rotate left
		{
			if(num > numberOfEntries)  //mod by numberOfEntries
				num = num % numberOfEntries;
			for(int i = 0; i < num; i++)    //each iteration shifts by one
			{								//iterate num times
				Node startNode = firstNode;
				Node lastNode = firstNode;
				
				while(lastNode.next != null)  //gets lastNode to the last node
					lastNode = lastNode.next;
					
				firstNode = firstNode.next;			
			
				startNode.next = null;
				lastNode.next = startNode;
				lastNode = startNode;
			}	
		}
		else if(num < 0)  //negative number: call rightRotate
		{
			if(num > numberOfEntries)  
				num = num % numberOfEntries;
			rightRotate(Math.abs(num));  //simply call rightRotate() method
										 //using the positive version of num
		}
	}

	// Remove from the end and add at the front.  Note that this method should
	// not create any new Node objects -- it is simply moving them.  If num
	// is negative it should still work, actually doing a left rotation.
	public void rightRotate(int num)
	{
		if(num > 0)  //if positive num
		{
			if(num > numberOfEntries)  //mod num by numberOfEntries
				num = num % numberOfEntries;
			for(int i = 0; i < num; i++)  //each iteration of the loop shifts items one to the right
			{
				Node startNode = firstNode;  //pointer starting at first node
				Node lastNode = firstNode;  //pointer at last node
				Node secondToLast = firstNode; //pointer at second to last node
				
				for(int j = 0; j < numberOfEntries - 2; j++) //loop until secondToLast
					secondToLast = secondToLast.next;  		 //is one before last node
					
				lastNode = secondToLast;	   
				lastNode = secondToLast.next;  //get last node
				
				firstNode = lastNode;     //assign the firstNode to lastNode
				secondToLast.next = null;  //secondToLast becomes the last node
				lastNode.next = startNode;  //assign lastNode to point to startNode
				lastNode = startNode;
			}	
		}
		else if(num < 0)  //if negative num: call leftRotate
		{
			if(num > numberOfEntries) 
				num = num % numberOfEntries;
			leftRotate(Math.abs(num));	//cast num to positive
		}
	}
	
	// Reverse the nodes in the list.  Note that this method should not create
	// any new Node objects -- it is simply moving them.
	public void reverse()
	{
		Node prev = null;
		Node curr = firstNode;
		while(curr != null)
		{
			Node next = curr.next;  //increments next
			curr.next = prev;  //points current node to the previous node
			prev = curr;  //increments prev
			curr = next;  //increments curr
		}
		firstNode = prev;	//assign firstNode to the last node
	}				
}