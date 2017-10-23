/*
 *  NAME:       Jeremy Deppen
 *  PROJECT:    #2
 *  CLASS:      CS 1501  1pm-2:15pm
 *  RECITATION: Friday Noon
 */

public class MyLZW {
    private static final int R = 256;        // number of input chars
    private static int L = 512;              // number of codewords = 2^W
    private static int W = 9;                // codeword width
    private static final int max = 65536;    // 2^16 = 65536

    public static void compress(String compressMode) {

        if(compressMode.equals("n"))               // write the mode type
            BinaryStdOut.write('n',8);             // to beginning of file
        else if(compressMode.equals("r"))
            BinaryStdOut.write('r',8);
        else if(compressMode.equals("m"))
            BinaryStdOut.write('m',8);

        double oldRatio = 0;            // bitsRead/bitsCompressed at time of full dictionary
        double currRatio = 0;           // bitsRead/bitsCompressed currently
        double ratioOfRatios = 0;
        int bitsRead = 0;               // bits read in so far
        int bitsCompressed = 0;         // bits compressed so far
        boolean monitoring = false;     // monitoring or not

        String input = BinaryStdIn.readString();
        TST<Integer> st = new TST<Integer>();
        int i;
        for (i = 0; i < R; i++)
            st.put("" + (char) i, i);
        int code = R+1;                             // R is codeword for EOF

        while (input.length() > 0) {

            String s = st.longestPrefixOf(input);  // Find max prefix match s.
            bitsRead += 8 * s.length();
            BinaryStdOut.write(st.get(s), W);      // Print s's encoding.
            bitsCompressed += W;
            int t = s.length();
            if (t < input.length() && code < L) {   // Add s to symbol table.
                st.put(input.substring(0, t + 1), code++);
            }

            if(W < 16 && code >= L) {               // if limit L is reached,
                L = (int)Math.pow(2, ++W);          // increment bitlength by 1
                st.put(input.substring(0, t + 1), code++);
            }

            // RESET MODE
            if(compressMode.equals("r")) {
                if(code == max) {
                    W = 9;                               // reset bitlength
                    L = 512;
                    st = new TST<Integer>();             // make new TST object
                    for (i = 0; i < R; i++)  // repopulate TST with 256 ASCII vals
                        st.put("" + (char) i, i);
                    code = R+1;                          // EOF indicator reset
                }
            }

            if(compressMode.equals("m")) {
                if(code == max || monitoring) {
                    if(bitsCompressed != 0) currRatio = bitsRead/(double)bitsCompressed;
                    if(currRatio != 0) ratioOfRatios = oldRatio/currRatio;
                    if(!monitoring) {   // set oldRatio and start monitoring
                        oldRatio = bitsRead / (double)bitsCompressed;
                        monitoring = true;
                    }
                    if(ratioOfRatios > 1.1) {  // RESET
                        W = 9;
                        L = 512;
                        st = new TST<Integer>();
                        for(i = 0; i < R; i++)
                            st.put("" + (char)i, i);
                        code = R+1;
                        oldRatio = 0;        // reset ratios
                        currRatio = 0;
                        ratioOfRatios = 0;
                        monitoring = false;  // stop monitoring
                    }
                }
            }

            input = input.substring(t);            // Scan past s in input.
        }
        BinaryStdOut.write(R, W);
        BinaryStdOut.close();
    }


    public static void expand() {
        char compressMode = BinaryStdIn.readChar(8); // denotes compression mode

        String[] st = new String[max];
        int i; // next available codeword value

        // initialize symbol table with all 1-character strings
        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;
        st[i++] = "";                        // (unused) lookahead for EOF

        int codeword = BinaryStdIn.readInt(W);
        if (codeword == R) return;           // expanded message is empty string
        String val = st[codeword];

        int bitsRead = 0;             // bits read in so far
        int bitsCompressed = 0;       // bits compressed so far
        double oldRatio = 0;          // bitsRead/bitsCompressed at time if full dictionary
        double currRatio = 0;         // bitsRead/bitsCompressed at current time
        double ratioOfRatios = 0;     // oldRatio/currRatio
        boolean monitoring = false;   // indicates if monitoring or not

        while (true) {

            BinaryStdOut.write(val);
            bitsRead += 8 * val.length();
            codeword = BinaryStdIn.readInt(W);
            bitsCompressed += W;
            if (codeword == R) break;
            String s = st[codeword];
            if (i == codeword) s = val + val.charAt(0);   // special case hack
            if (i < L) st[i++] = val + s.charAt(0);

            if(W < 16 && i >= L-1) {         // if count is equal to max bitlength,
                st[i++] = val + s.charAt(0);
                L = (int)Math.pow(2, ++W);   // increase bitlength by 1
            }
            val = s;

            // RESET MODE
            if(compressMode == 'r') {
                if(i == max) {      // count equals 65563 - reset string array
                    W = 9;
                    L = 512;
                    st = new String[max];    // new string array
                    for (i = 0; i < R; i++)  // refill with 0-255 ASCII vals
                        st[i] = "" + (char) i;
                    st[i++] = "";                        // (unused) lookahead for EOF
                    codeword = BinaryStdIn.readInt(W);
                    if(codeword == R) return;
                    val = st[codeword];
                }
            }

            // MONITOR MODE
            if(compressMode == 'm') {
                if(i == max || monitoring) {
                    if(bitsCompressed != 0) currRatio = bitsRead/(double)bitsCompressed; //calculate currRatio
                    if(currRatio != 0) ratioOfRatios = oldRatio/currRatio; //calculate ratioOfRatios
                    if(!monitoring) {  // if not monitoring, calculate oldRatio and start monitoring
                        oldRatio = bitsRead / (double)bitsCompressed;
                        monitoring = true;
                    }
                    if(ratioOfRatios > 1.1) {   // RESET
                        W = 9;
                        L = 512;
                        st = new String[max];
                        for (i = 0; i < R; i++)
                            st[i] = "" + (char) i;
                        st[i++] = "";                        // (unused) lookahead for EOF
                        codeword = BinaryStdIn.readInt(W);
                        int codeword2 = BinaryStdIn.readInt(W);
                        if(codeword == R) return;
                        val = st[codeword];
                        oldRatio = 0;       // reset ratios
                        currRatio = 0;
                        ratioOfRatios = 0;
                        monitoring = false; // stop monitoring
                    }
                }
            }
        }
        BinaryStdOut.close();
    }



    public static void main(String[] args) {
        if(args[0].equals("-")) {
            String compressMode = args[1];  // only accepts n, r, and m for the second argument
            if(compressMode.equals("n") || compressMode.equals("r") || compressMode.equals("m"))
                compress(compressMode);
            else
                throw new IllegalArgumentException("Did not specify n, r, or m for compression type");
        }
        else if(args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }

}
