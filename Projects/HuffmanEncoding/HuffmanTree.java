import TreePackage.*;
import java.util.*;
import java.io.*;

public class Huffman
{

	public static void main(String[] args)
	{
		Scanner inScan = new Scanner(System.in);
		String fileName;
		File textFile;
		BufferedReader reader = null;
		while(true)			//keep looping until valid file
		{
			try
			{
				System.out.println("Enter file name:");
				fileName = inScan.nextLine();
				textFile = new File(fileName);
				reader = new BufferedReader(new FileReader(textFile));
				break;
			}
			catch(IOException e)
			{
				System.out.println("IOException: please enter valid file name");
			}
		}

		BinaryNode<Character> rootNode = new BinaryNode<Character>('\0'); //creates null root node
		String currLine;											   	//current text file line
		StringBuilder binaryString = new StringBuilder();	//String for Huffman code for each letter
		ArrayList<Pair> aList = new ArrayList<Pair>();		//Arraylist of Pair (class I made)
		try
		{
			currLine = reader.readLine();								//Read in first line from file
			setUpTree(currLine, rootNode, reader, binaryString, aList);	//Call recursive method to build tree
		}
		catch(IOException e)
		{}

		//displays tree
		rootNode.preOrder();

		//displays table with Huffman codes
		for(int i = 0; i < aList.size(); i++)		//display letters with Huffman strings
			System.out.println(aList.get(i).key + ": " + aList.get(i).value);
	}

	public static void setUpTree(String currLine, BinaryNode<Character> CN, BufferedReader reader, StringBuilder binaryString, ArrayList<Pair> aList)  //recursively builds tree
	{
		BinaryNode<Character> currNode = CN;	//current node
		if(currLine.charAt(0) == 'L')		//base case: leaf - backtrack
		{
			currNode.setData(currLine.charAt(2));	//add the data to current leaf node
			aList.add(new Pair(currLine.charAt(2), binaryString.toString()));
		}
		else if(currLine.charAt(0) == 'I')	//Interior node, make two null children
		{									//then recursively build left then right subtrees
			BinaryNode<Character> temp1 = new BinaryNode<Character>('\0');	//left null child
			BinaryNode<Character> temp2 = new BinaryNode<Character>('\0');	//right null child
			currNode.setLeftChild(temp1);		//connect left child to parent (currNode)
			currNode.setRightChild(temp2);		//connect right child to parent (currNode)

			try												//Recurse left subtree
			{
				if((currLine = reader.readLine()) != null)
				{
					binaryString.append("0");				//add 0 to binary string
					setUpTree(currLine, currNode.getLeftChild(), reader, binaryString, aList);
					binaryString.setLength(binaryString.length() - 1); //delete last char
				}
			}
			catch(IOException e)
			{}
			try												//Recurse right subtree
			{
				if((currLine = reader.readLine()) != null)
				{
					binaryString.append("1");				//add 1 to binary string
					setUpTree(currLine, currNode.getRightChild(), reader, binaryString, aList);
					binaryString.setLength(binaryString.length() - 1);	//delete last char
				}
			}
			catch(IOException e)
			{}
		}
	}

}
