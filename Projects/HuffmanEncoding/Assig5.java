import TreePackage.*;
import java.util.*;
import java.io.*;

public class Assig5
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
		
		System.out.println("\nThe Huffman Tree has been restored");
		//**** Huffman tree made: go into main program loop ****//
		
		boolean quit = false;
		while(quit == false)
		{
			System.out.println("\nPlease choose from the following:");
			System.out.println("1.) Encode a text string \n2.) Decode a Huffman string \n3.) Quit");
			int decesion = inScan.nextInt();
			
			//ENCODE	
			if(decesion == 1)			
			{
				System.out.println("Enter a String from the following characters (USE UPPERCASE)");
				for(int i = 0; i < aList.size(); i++)
				{
					System.out.print(aList.get(i).key);		//show user the letters
				}
				System.out.println();
				String userString = inScan.next();
				char[] charArr = userString.toCharArray();
				StringBuilder SB = new StringBuilder();
				boolean valid = true;
				for(int i = 0; i < charArr.length; i++)
				{
					boolean inRange = false;
					for(int j = 0; j < aList.size(); j++)
					{
						Character tmp = new Character(charArr[i]);
						if(tmp.compareTo(aList.get(j).key) == 0)	//test if equal
						{
							SB.append(aList.get(j).value + "\n");
							inRange = true;
						}
					}
					if(inRange == false)
					{
						valid = false;
						break;
					}	
				}
				
				if(valid == true)
				{
					System.out.println("Huffman String:");
					System.out.print(SB.toString());
				}
				else
					System.out.println("There was an error in your text string");
			}
			
			//DECODE
			else if(decesion == 2)		
			{
				System.out.println("Here is the encoding table:");
				for(int i = 0; i < aList.size(); i++)		//display letters with Huffman strings
					System.out.println(aList.get(i).key + ": " + aList.get(i).value);
				System.out.println("Please enter a Huffman string (one line, no spaces)");
				String userHuffmanString = inScan.next();
				
				int index = 0;
				boolean valid = true;
				StringBuilder decodedHuffman = new StringBuilder();	//will display decoded string
				while(true)						//loop until all letters are decoded
				{			
					String currLetter = binaryToLetter(userHuffmanString, rootNode, index, aList);
					
					if(currLetter.equals(""))	//invalid user inputted Huffman code
					{						
						valid = false;
						break;
					}
					
					decodedHuffman.append(currLetter.charAt(0));	//adds the letter to StringBuilder
					String[] arr = currLetter.split(" ");	//currLetter sends back letter+space+new index
					String tempString = arr[1];				//to get new index, it is at position 1
					index = Integer.parseInt(tempString);	//set new index
					
					if(index >= userHuffmanString.length())		//indicates all letters decoded
						break;
				}
				if(valid == true)
					System.out.println(decodedHuffman.toString());
			}
			
			//QUIT PRGRAM
			else if(decesion == 3)	
			{
				System.out.println("Good-bye!");
				System.exit(0);						//quits program
			}
			
			//INVALID NUMERICAL INPUT
			else
			{
				System.out.println("INVALID INPUT - select either 1, 2, or 3");
			}
		}
		
	}
	
	//METHOD THAT RECURSIVELY BUILDS THE TREE FROM THE TEXT FILE
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
	
	//METHOD THAT TAKES THE USER INPUTTED BINARY HUFFMAN CODE AND RETURNS
	//A STRING THAT HAS THE DECODED LETTER *SPACE* NUMBER THAT INDICATES
	//THE NEW STARTING INDEX WHERE THE NEXT LETTER'S HUFFMAN CODE BEGINS
	//RETURNS AN EMPTY STRING IF USER INPUTTED HUFFMAN STRING IS INVALID
	public static String binaryToLetter(String huffmanCode, BinaryNode<Character> currNode, int index, ArrayList<Pair> aList)
	{
		while(currNode.getData().equals('\0'))	//loop until you reach a node with a 
		{										//value in it
			if(index >= huffmanCode.length())	//error with user inputted Huffman string
			{										
				System.out.println("There was an error in your Huffman string");
				return "";
			}
			if(huffmanCode.charAt(index) == '0')
				currNode = currNode.getLeftChild();
			else if(huffmanCode.charAt(index) == '1')
				currNode = currNode.getRightChild();	
			index++;
		}
		StringBuilder SB = new StringBuilder();					//return String to store char
		SB.append(currNode.getData().toString() + " " + index);	//and index of binary string
		return SB.toString();									//where the next huffman code 															
	}															
}