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
     HashMap<Character,Integer> charFreqs = new HashMap<Character,Integer>();
     ArrayList<Pair> sortedCharFreqs = new ArrayList<Pair>();

     /* Constructor */
     public Huffman(String fName)
     {
         fileName = fName;
         generateWeights();
         System.out.println(charFreqs);
         sortWeights();


         buildTree();
         //printTree(rootNode);
     }

     public void printTree(Node currNode)
     {
         if(currNode.leftChild == null & currNode.rightChild == null)
         {
             System.out.println(currNode.value + " " + currNode.weight);
             return;
         }
         else
         {
             printTree(currNode.rightChild);
             printTree(currNode.leftChild);
         }
     }

     public void buildTree()
     {
         for(Pair pair : sortedCharFreqs)
            System.out.print(pair.letter + " " + pair.count + "  ");
         System.out.println("done");
         if(sortedCharFreqs.size() == 2)
         {
             Node newRightNode = new Node(sortedCharFreqs.get(0).letter, sortedCharFreqs.get(0).count);
             Node newLeftNode = new Node(sortedCharFreqs.get(1).letter, sortedCharFreqs.get(1).count);
             Node newParentNode = new Node('\u0000', newLeftNode.weight+newRightNode.weight, newLeftNode, newRightNode);
            //  System.out.println(newParentNode.weight);
            //  System.out.println(rootNode.weight);
             rootNode = newParentNode;
             return;
         }
         else
         {
             Node newRightNode = new Node('\u0000', sortedCharFreqs.get(0).count);
             Node newLeftNode = new Node('\u0000', sortedCharFreqs.get(1).count);
             if(sortedCharFreqs.get(0).letter != '\u0000' && sortedCharFreqs.get(0).letter != '\u0000')
             {
                newRightNode.value = sortedCharFreqs.get(0).letter;
                newLeftNode.value = sortedCharFreqs.get(1).letter;
             }
             Node newParentNode = new Node('\u0000', newLeftNode.weight+newRightNode.weight, newLeftNode, newRightNode);
             System.out.println(newRightNode.value + " " + newRightNode.weight + " " + newLeftNode.value + " " + newRightNode.weight);
             //System.out.println(newParentNode.value + " " + newParentNode.weight);
             charFreqs.remove(newLeftNode.value);
             charFreqs.remove(newRightNode.value);
             charFreqs.put('\u0000', newLeftNode.weight+newRightNode.weight);
             sortedCharFreqs.clear();
             sortWeights();
             buildTree();
         }
     }

     /* Scans through the file and marks how many times a char
        is seen in the file. Uses a HashMap that stores a char
        as the key, and an int as the value that represents how many
        times the char has been seen (frequency) */
     private void generateWeights()
     {
         FileInputStream inputStream = null;
         Scanner fileScan = null;
         try
         {
             inputStream = new FileInputStream(fileName);
             fileScan = new Scanner(inputStream, "UTF-8");
             while(fileScan.hasNextLine())
             {
                 String currentLine = fileScan.nextLine();
                 for(int i = 0; i < currentLine.length(); i++)
                 {
                    char currentChar = currentLine.charAt(i);
                    if(charFreqs.containsKey(currentChar))
                        charFreqs.put(currentChar, charFreqs.get(currentChar)+1);
                    else
                        charFreqs.put(currentChar, 1);
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

    /* Sorts the hashmap and uses an ArrayList of object Pair that
       stores a letter and its frequency (an int). It is sorted from
       decreasing order with the char with the biggest frequency at
       the beginning of the ArrayList */
    public void sortWeights()
    {
        sortedCharFreqs.clear();
        Set<Entry<Character, Integer>> set = charFreqs.entrySet();
        List<Entry<Character, Integer>> list = new ArrayList<Entry<Character, Integer>>(set);
        Collections.sort( list, new Comparator<Map.Entry<Character, Integer>>()
        {
            public int compare( Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2 )
            {
                int val = o2.getValue().compareTo( o1.getValue());
                if(val > 0) return -1;
                else if(val < 0) return 1;
                return 0;
            }
        });

        // Store the sorted char/int items into a Pair object and add to ArrayList
        for(Map.Entry<Character, Integer> entry:list)
        {
            Pair pair = new Pair(entry.getKey(), entry.getValue());
            sortedCharFreqs.add(pair);
        }
    }


     /* Inner class that gives Huffman.java access to Nodes */
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

     public static void main(String[] args)
     {
         Scanner inScan = new Scanner(System.in);
         System.out.println("Enter file name");
         String fileName = inScan.nextLine();

         Huffman huff = new Huffman(fileName);
     }

 }
