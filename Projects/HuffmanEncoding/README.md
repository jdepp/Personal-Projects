SUMMARY:
  - This program takes in a .txt file and generates a Huffman tree based on the frequencies of each character in the file. 
  - The file has to be in a specific format (like Huff.txt in this directory) in that on each line there are two items: an 
   L or I that denotes if the node is a leaf or an interior node, and a character (or lack of) that is the value of the node.
  - Once the tree is built, the user can choose to encode or decode a Huffman string. A Huffman string is made up of     characters' binary paths from rootnode to leafnode. For example, if we are at the rootnode and go to its left child and its a leafnode with value 'A', A's binary representation is 0. 

RUNTIME:
  - Build tree: O(nw) where n is the number of characters to place and k is the bitlength of the longest binary path in the tree
