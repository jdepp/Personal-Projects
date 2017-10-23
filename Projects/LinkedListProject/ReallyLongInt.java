//***********************************************
// NAME: Jeremy Deppen
// CLASS: CS 445  9:30am lecture; 3pm Tues lab
// ASSIGNMENT: number 2
// DUE DATE: 2/14/2017
// THIS FILE: ReallyLongInt.java
//***********************************************



// CS 0445 Spring 2017
// This is a partial implementation of the ReallyLongInt class.  You need to
// complete the implementations of the remaining methods.  Also, for this class
// to work, you must complete the implementation of the LinkedListPlus class.
// See additional comments below.

public class ReallyLongInt 	extends LinkedListPlus<Integer> 
							implements Comparable<ReallyLongInt>
{
	// Instance variables are inherited.  You may not add any new instance variables
	
	// Default constructor
	private ReallyLongInt()
	{
		super();
	}

	// Note that we are adding the digits here in the FRONT. This is more efficient
	// (no traversal is necessary) and results in the LEAST significant digit first
	// in the list.  It is assumed that String s is a valid representation of an
	// unsigned integer with no leading zeros.
	public ReallyLongInt(String s)
	{
		super();
		char c;
		int digit;
		// Iterate through the String, getting each character and converting it into
		// an int.  Then make an Integer and add at the front of the list.  Note that
		// the add() method (from A2LList) does not need to traverse the list since
		// it is adding in position 1.  Note also the the author's linked list
		// uses index 1 for the front of the list.
		for (int i = 0; i < s.length(); i++)
		{
			c = s.charAt(i);
			if (('0' <= c) && (c <= '9'))
			{
				digit = c - '0';
				this.add(1, new Integer(digit));
			}
			else throw new NumberFormatException("Illegal digit " + c);
		}
	}

	// Simple call to super to copy the nodes from the argument ReallyLongInt
	// into a new one.
	public ReallyLongInt(ReallyLongInt rightOp)
	{
		super(rightOp);
	}
	
	// Method to put digits of number into a String.  Since the numbers are
	// stored "backward" (least significant digit first) we first reverse the
	// number, then traverse it to add the digits to a StringBuilder, then
	// reverse it again.  This seems like a lot of work, but given the
	// limitations of the super classes it is what we must do.
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		if (numberOfEntries > 0)
		{
			this.reverse();
			for (Node curr = firstNode; curr != null; curr = curr.next)
			{
				sb.append(curr.data);
			}
			this.reverse();
		}
		return sb.toString();
	}

	// You must implement the methods below.  See the descriptions in the
	// assignment sheet

	public ReallyLongInt add(ReallyLongInt rightOp)
	{
		ReallyLongInt RLI = new ReallyLongInt(); //create a RLI to store sums
		
		Node curr1 = firstNode;
		Node curr2 = rightOp.firstNode;
		int sum = curr1.data + curr2.data;
		int carry = 0;
		if(sum > 9)   //if sum is double digit. can never be more than 18 though
		{
			sum = sum % 10;
			carry = 1;
		}
		
		RLI.add(sum);
		curr1 = curr1.next;
		curr2 = curr2.next;
		
		while((curr1 != null) && (curr2 != null)) //loop til one is out
		{
			sum = curr1.data + curr2.data + carry;
			if(sum > 9)
			{
				sum = sum % 10;
				carry = 1;
			}
			else
				carry = 0;
			
			RLI.add(sum);
			curr1 = curr1.next;
			curr2 = curr2.next;
		}
		while(curr1 != null)  //if first LL still has stuff, add it to RLI
		{
			sum = curr1.data + carry;
			if(sum > 9)
			{
				sum = sum % 10;
				carry = 1;
			}
			else
				carry = 0;
			RLI.add(sum);
			curr1 = curr1.next;
		}
		while(curr2 != null)  //if second LL still has stuff, add it to RLI
		{
			sum = curr2.data + carry;
			if(sum > 9)
			{
				sum = sum % 10;
				carry = 1;
			}
			else
				carry = 0;
			RLI.add(sum);
			curr2 = curr2.next;
		}
		if(carry == 1)  //if both LLs are out and theres still a carry, add 1
		{
			RLI.add(1);
		}
		
		return RLI;
	}
	
	public ReallyLongInt subtract(ReallyLongInt rightOp)
	{
		ReallyLongInt RLI = new ReallyLongInt(); //create a RLI to store sums
		ReallyLongInt temp = new ReallyLongInt(this); //so this LL doesnt get messed up
		
		int equal = compareTo(rightOp);
		if(equal == -1)
			throw new ArithmeticException("Invalid Difference -- Negative Number!");
		
		int carry = 0;
		int difference = 0;
		Node curr1 = temp.firstNode;
		Node curr2 = rightOp.firstNode;
		
		if(curr1.data < curr2.data)   //if sum is double digit. can never be more than 18 though
		{
			curr1.data = curr1.data + 10;
			difference = curr1.data - curr2.data;
			carry = -1;
		}
		else
		{
			difference = curr1.data - curr2.data;
		}
		
		RLI.add(difference);
		curr1 = curr1.next;
		curr2 = curr2.next;
		
		while((curr1 != null) && (curr2 != null)) //loop til one is out
		{
			if(curr1.data < curr2.data)
			{
				curr1.data = curr1.data + 10 + carry;
				difference = curr1.data - curr2.data;
				carry = -1;
			}
			else
			{
				difference = curr1.data - curr2.data + carry;
				carry = 0;
			}
			
			RLI.add(difference);
			curr1 = curr1.next;
			curr2 = curr2.next;
		}
		while(curr1 != null)  //if first LL still has stuff, add it to RLI
		{
			difference = curr1.data + carry;
			if(difference < 0)
			{
				difference = difference + 10;
				carry = -1;
			}
			else
				carry = 0;
			RLI.add(difference);
			curr1 = curr1.next;
		}
		
		RLI.reverse();                  //shift out leading 0s
		do
		{
			if(RLI.firstNode.data == 0)
			{
				RLI.leftShift(1);
			}	
		}while(RLI.firstNode.data == 0);
		
		RLI.reverse();
		
		
		return RLI;	
	}

	public int compareTo(ReallyLongInt rOp)
	{
		boolean curr1Greater = false;
		boolean curr2Greater = false;
		
		reverse();  //reverse current RLI
		rOp.reverse();	//reverse rOp
		
		Node curr1 = firstNode;
		Node curr2 = rOp.firstNode;
			
		if(numberOfEntries > rOp.numberOfEntries)
		{
			reverse();
			rOp.reverse();
			return 1;
		}
		else if(numberOfEntries < rOp.numberOfEntries)
		{
			reverse();
			rOp.reverse();
			return -1;
		}
			
		for(int i = 0; i < numberOfEntries; i++)
		{
			if(curr1.data > curr2.data)
			{
				curr1Greater = true;
				curr2Greater = false;
				break;
			}
			else if(curr1.data < curr2.data)
			{
				curr2Greater = true;
				curr1Greater = false;
				break;
			}
			else 
			{
				curr1 = curr1.next;
				curr2 = curr2.next;
			}
		}
		
		reverse();
		rOp.reverse();
		
		if(curr1Greater == true)
			return 1;
		else if(curr2Greater == true)
			return -1;
		
			
		return 0;
	}

	public boolean equals(ReallyLongInt rightOp)
	{
		ReallyLongInt RLI = new ReallyLongInt(rightOp);
		
		int num = compareTo(RLI);
		if(num == 0)
			return true;
	
		return false;
	}

	public void multTenToThe(int num)
	{
		//add num 0s to the end of the LL
		reverse();
		for(int i = 0; i < num; i++)
		{
			add(0);
		}	
		reverse();
	}

	public void divTenToThe(int num)
	{
		//delete num Nodes from the end
		leftShift(num);	
	}
}