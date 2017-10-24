/*
 *  AUTHOR:  Jeremy Deppen
 *
 *  SUMMARY: A class that compresses a file by giving characters shorter
 *           bitlength representations of themselves. This algorithm reads
 *           through the file once taking note of how many times each character
 *           appears, and then makes a binary tree based off of this. For example,
 *           if a character appears a lot in the file, it will have a shorter
 *           bitlenth than a character that only appears less frequently.
 */

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

 public class Huffman
 {
     String fileName;
     Node rootNode;
     ArrayList<Triple> charFreqs = new ArrayList<Triple>();
     Node biggestNode = new Node('\u0000', 0);
     HashMap<Character,String> dictionary = new HashMap<Character,String>();
     String binaryString = "";

     /* Constructor */
     public Huffman(String fName)
     {
         fileName = fName;
         generateWeights();
         sortWeights();
         buildTree();
         generateDictionary(rootNode);
     }

     /* Traverses the tree recursively from left to right and builds
        a binary string depending on which child it takes (visit left child
        adds a 0, visit right child adds a 1). Once a leaf node is hit,
        add the character and binary string to a hashmap called
        dictionary and then backtrack to visit other nodes getting their binary strings */
     public void generateDictionary(Node currNode)
     {
         if(currNode.leftChild == null & currNode.rightChild == null)
         {
             dictionary.put(currNode.value, binaryString);
             return;
         }
         else
         {
             binaryString += "0";
             generateDictionary(currNode.leftChild);
             binaryString = binaryString.substring(0, binaryString.length()-1);
             binaryString += "1";
             generateDictionary(currNode.rightChild);
             binaryString = binaryString.substring(0, binaryString.length()-1);
         }
     }

     /* Builds the tree utilizing an ArrayList of custom type Triple which holds
        a character, an int to represent how many times we saw this character,
        and this characters node in the binary tree. This ArrayList is sorted
        at every recursive call so that the smallest weighted items at indexes 1
        and 2 for easy access to add a parent node of their combined size  */
     public void buildTree()
     {
         // base case - two items left to connect - assign this node as root node
         if(charFreqs.size() == 2)
         {
             Triple leftChild = charFreqs.get(0);
             Triple rightChild = charFreqs.get(1);
             Node newParentNode = new Node('\u0000', leftChild.count+rightChild.count, leftChild.node, rightChild.node);
             rootNode = newParentNode;
             return;
         }
         else
         {
             Triple leftChild = charFreqs.get(0);
             Triple rightChild = charFreqs.get(1);
             Node newParentNode = new Node('\u0000', leftChild.count+rightChild.count, leftChild.node, rightChild.node);
             Triple newTriple = new Triple('\u0000', newParentNode.weight, newParentNode);
             charFreqs.add(newTriple);
             charFreqs.remove(0);
             charFreqs.remove(0);

             sortWeights();
             buildTree();
         }
     }

     /* Scans through the file and marks how many times a char
        is seen in the file by incrementing the int at the current
        characters index in the ascii array. Then iterate through
        that array and make a new ArrayList entry with the character,
        how many times it's seen in the file, and a new Node for it */
     private void generateWeights()
     {
         FileInputStream inputStream = null;
         Scanner fileScan = null;
         int[] asciiArray = new int[256];
         try
         {
             inputStream = new FileInputStream(fileName);
             fileScan = new Scanner(inputStream, "UTF-8");

             // loop through file marking how many times a char is seen
             while(fileScan.hasNextLine())
             {
                 String currentLine = fileScan.nextLine();
                 for(int i = 0; i < currentLine.length(); i++)
                 {
                    char currentChar = currentLine.charAt(i);
                    if(asciiArray[currentChar] == 0)
                        asciiArray[currentChar] = 1;
                    else
                    {
                        int currentCharFreq = asciiArray[currentChar];
                        asciiArray[currentChar] = currentCharFreq+1;
                    }
                 }
             }

             // create new Triple object and add it to the ArrayList for each char
             for(int i = 0; i < asciiArray.length; i++)
             {
                 // if val at i <= 0, this char hasn't been seen in the file
                 if(asciiArray[i] > 0)
                 {
                     Triple newTriple = new Triple((char)i, asciiArray[i], new Node((char)i, asciiArray[i]));
                     charFreqs.add(newTriple);
                 }
             }

             if(fileScan.ioException() != null)
                throw fileScan.ioException();
         }
         catch(IOException e) {}
         finally
         {
             if(inputStream != null)
             {
                 try { inputStream.close(); }  catch(IOException e) {}
             }
             if(fileScan != null)
                fileScan.close();
         }
     }

    /* Sorts the ArrayList by how many times a char has been seen (count).
       sorts in ascending order */
    public void sortWeights()
    {
        Collections.sort(charFreqs, new Comparator<Triple>() {
            @Override public int compare(Triple p1, Triple p2) {
                return p1.count - p2.count;
            }
        });
    }


     /* Inner class that gives access to Nodes */
     public class Node
     {
         public char value;
         public int weight;
         public Node leftChild;
         public Node rightChild;

         /* Constructor for a leaf node */
         public Node(char val, int w)
         {
             this(val, w, null, null);
         }
         /* Constructor for an interior node */
         public Node(char val, int w, Node left, Node right)
         {
             value = val;
             weight = w;
             leftChild = left;
             rightChild = right;
         }
     }

     /* Class that stores a character, how many times it's seen in
        the file, and the node associated with it in the binary tree */
     public class Triple
     {
         char letter;
         int count;
         Node node;

         public Triple(char l, int c, Node n)
         {
             letter = l;
             count = c;
             node = n;
         }
     }

     /* MAIN class used for testing purposes */
     public static void main(String[] args)
     {
         Scanner inScan = new Scanner(System.in);
         System.out.println("Enter file name");
         String fileName = inScan.nextLine();

         Huffman huff = new Huffman(fileName);
     }

 }
