This program takes in a .txt file and generates a Huffman tree based on the frequencies of each character in the file. 
The file has to be in a specific format (like Huff.txt in this directory) in that On each line there are two items: an 
L or I that denotes if the node is a leaf or an interior node, and a character (or lack of) that is the value of the node.
Once the tree is built, the user can choose to encode or decode a Huffman string. For example, take a look at the tree below.

            |------|
            |      |
            |------|
         0 /        \ 1
          /          \
  |-------|          |-------|
  |   A   |          |       |
  |-------|          |-------|
                  0 /         \ 1
                   /           \
               |-------|    |--------|
               |   B   |    |   C    |
               |-------|    |--------|
 
               
We can now represent each character by a small bitstring. For example:
  - A: 0
  - B: 10
  - C: 11                                                     
If we want to encode "CAB", the encoded Huffman string would be 11011
