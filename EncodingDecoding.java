import java.io.*;
import java.math.BigInteger;

public class EncodingDecoding {

    Tree newTree = new Tree();

    //Outputs the the encoded file. This is where the compression occurs.
    //uses string buffer to make things faster, 8 bits at a time are processed
    //adds nyt symbols to remaining bits at end. lets decoder know when to stop decoding.
    //reason why decompressed file slightly bigger as it deals with this
    public void outputEncode(String inputFile, String outputFile) throws Exception {
        try {
            FileInputStream input = new FileInputStream(inputFile);
            FileOutputStream out = new FileOutputStream(outputFile);
            String encodedString = "";
            StringBuffer sb = new StringBuffer();
            while (input.available() > 0) {
                String c = Integer.toString(input.read() & 0xff);
                sb.append(encode(c));
                //writes out to file 8 bits at a time
                while(sb.length() >= 8) {
                    String toWrite = sb.substring(0, 8);
                    out.write(Integer.parseInt(toWrite, 2) & 0xff);
                    sb.delete(0, 8);
                }
            }
            //adds nyt code to the end of file to deal with useless bits
            String nytCode = getCode(newTree.root, newTree.NYT);
            int i =0;
            if(sb.length() < 8) {
                while((sb.length() < 8) && (i+1 < nytCode.length())) {
                    sb.append(nytCode.substring(i, i+1));
                    i++;
                }
                out.write(Integer.parseInt(sb.toString(), 2) & 0xff);
            }
            input.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //helper method to get code
    public String getCoding(String code, Node t) {
        if (t.isRight(t)) {
            code = "1" + code;
        } else {
            code = "0" + code;
        }
        return code;
    }

    //gets the coding from the tree. traverse from node to root
    public String getCode(Node root, Node searchNode){
        String code = "";
        Node t = searchNode;
        while(t != root){
            code = getCoding(code, t);
            t = t.parent;
        }
        return code;
    }

    //converts the ascii to bits
    public String assciiConvertToBit(String symbol){
        int i = Integer.parseInt(symbol);
        String aChar = Character.toString((char) i);
        return new BigInteger(aChar.getBytes()).toString(2);
    }

    //converts ascii to binary
    public String AsciiToBinary(String asciiString){
        int convert = Integer.parseInt(asciiString);
        String s = Character.toString((char)convert);
        byte[] bytes = s.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes)
        {
            int val = b;
            for (int i = 0; i < 8; i++)
            {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        return binary.toString();
    }

    //encodes the the message. gets the codes for the right ascii symbols
    public String encode(String symbol) {
        Node t = newTree.leafTable.get(symbol);
        String code;
        if(t != null){
            code = getCode(newTree.root, t);
            newTree.treeUpdate(symbol);
        }
        else{
            code = getCode(newTree.root, newTree.NYT);
            code += AsciiToBinary(symbol);
            newTree.treeUpdate(symbol);
        }
        return code;
    }

    //converrts binary to ascii
    public String convertBitToAsscii(String symbol){
        int charCode = Integer.parseInt(symbol, 2);
        return Character.toString((char) charCode);
    }


    //Decode method, reads in compressed file
    //uses string buffer again to make program more efficient instead of strings
    //mostly does the opposite to the encoder
    //
    public String decode(String file, String outputFile) throws Exception {
        FileInputStream input = new FileInputStream(file);
        BufferedWriter out = new BufferedWriter(new FileWriter(outputFile));
        StringBuffer sb = new StringBuffer();
        int c = 0;
        String string = "";
        String output = "";
        String bit;
        Tree decodedTree = new Tree();
        Node currentNode = decodedTree.root;
        //reading in characters of 20 in string buffer
        while (sb.length() < 20){
            c = input.read();
            sb.append(String.format("%" + 8 + "s", Integer.toBinaryString(c)).replace(' ', '0'));
        }
            while(c != -1 || sb.length() > 0){
                //reading in characters of 20 in string buffer
                //do this again to make sure whole file is read
                while (sb.length() < 20 && c != -1){
                    c = input.read();
                    sb.append(String.format("%" + 8 + "s", Integer.toBinaryString(c)).replace(' ', '0'));
                }
                //opposite of the encoder, converts the 8 bits to ascii
                //decoding
            if (currentNode.isLeaf(currentNode)) {
                if (currentNode == decodedTree.NYT) {
                    if(sb.length() >= 8) {
                        string = convertBitToAsscii(sb.substring(0, 8));
                        sb.delete(0, 8);
                    }
                    else{
                        break;
                    }
                } else {
                    string = currentNode.symbol;
                }
                //updates tree to original tree to that of the encoder
                decodedTree.treeUpdate(string);
                currentNode = decodedTree.root;
                out.write(string);
                out.flush();
            }
            else {
                //chooses which side of the tree to traverse through to next
                bit = sb.substring(0, 1);
                if(bit.equals("0")){
                    currentNode = currentNode.left;
                }
                else{
                    currentNode = currentNode.right;
                }
                sb.delete(0, 1);
            }
        }
        return output;
    }
}
