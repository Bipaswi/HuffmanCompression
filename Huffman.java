import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class Huffman {

    public long initialEncodeTime;
    public long endEncodeTime;

    public long initialDecodeTime;
    public long endDecodeTime;

    public long time;
    public long endTime;

    public static void main(String[] args) throws Exception {

//        String encodeFile = "Duel on Syrtis.txt";
//        //String encodeFile = "test.jpg";
//        String compressedFile = "CompressedFile.txt";
//        String outputDecode = "decode.txt";
//
//        EncodingDecoding ed = new EncodingDecoding();
//        ed.outputEncode(encodeFile, compressedFile);
//        ed.decode(compressedFile, outputDecode);
//
        Huffman h = new Huffman();
        String readFile = "ExperimentFiles.csv";
        //String readFile = "test.csv";
        String comma = ",";

        Experiment[] array = new Experiment[30];
        BufferedReader reader = new BufferedReader(new FileReader(readFile));
        String line = "";
        String output = "";
        int x=0;
        while ((line = reader.readLine()) != null) {
            String[] contents = line.split(comma);
            array[x] = new Experiment(contents[0], contents[1], contents[2], contents[3]);
            x++;
        }

        for(int i = 0; i < x; i++){
            h.experiment(array[i].csvOutput, array[i].Encode, array[i].Compress, array[i].Decode);
            System.out.println("here");
        }
        System.out.println("experiment done");
    }

    //will need to overwrite files
    public void experiment(String fileName, String encodeFile, String compressedFile, String outputDecode) throws Exception {

        PrintWriter writer = new PrintWriter(fileName);
        writer.println("Original File Name" + ","
                + "Original File Size" + ","
                + "Compressed File Name" + ","
                + "Compression File Size" + ","
                + "Decompressed File Name" + ","
                + "Decompressed File Size" + ","
                + "Time for Encoding " + ","
                + "Time for Decoding" + ","
                + "Time for All" + ","
                + "Compression ratio (%)" + ","
                + "Byte/Nanosecond Speed (encode)" + ","
                + "Byte/Nanosecond Speed (decode)" + ","
                + "Byte/Nanosecond Speed (all)");

        for (int i = 0; i < 5; i++) {
            EncodingDecoding ed = new EncodingDecoding();
            time = System.nanoTime();

            initialEncodeTime = System.nanoTime();
            ed.outputEncode(encodeFile, compressedFile);
            endEncodeTime = System.nanoTime();

            initialDecodeTime = System.nanoTime();
            ed.decode(compressedFile, outputDecode);
            endDecodeTime = System.nanoTime();

            endTime = System.nanoTime();

            double encodeTime = (endEncodeTime - initialEncodeTime) / 1000000000.0;
            double decodeTime = (endDecodeTime - initialDecodeTime) / 1000000000.0;
            double finalTotalTime = (endTime - time) / 1000000000.0;

            //System.out.println("Encode Time " + encodeTime);
            //System.out.println("Decode Time " + decodeTime);
            //System.out.println("Final Time: " + finalTotalTime);

            double originalFileSize = new File(encodeFile).length();
            double compressFileSize = new File(compressedFile).length();
            double decodedFileSize = new File(outputDecode).length();
            double compressRatio = Math.round((100 - ((compressFileSize / originalFileSize) * 100)));
            double speedEncode = originalFileSize/encodeTime;
            double speedDecode = compressFileSize/decodeTime;
            double totalSpeed = (originalFileSize+compressFileSize)/finalTotalTime;

            //System.out.println(compressRatio + "%");
            writer.println(encodeFile + ","
                    + originalFileSize + ","
                    +  compressedFile + ","
                    + compressFileSize + ","
                    +  outputDecode + ","
                    + decodedFileSize + ","
                    + encodeTime + ","
                    + decodeTime + ","
                    + finalTotalTime + ","
                    + compressRatio + ","
                    + speedEncode + ","
                    + speedDecode + ","
                    + totalSpeed);
            }
			writer.flush();
			writer.close();
        }
    }

    class Experiment{

        public String csvOutput = "";
        public String Encode = "";
        public String Compress = "";
        public String Decode = "";

        public Experiment(String csvOutput, String encode, String compress, String decode) {
            this.csvOutput = csvOutput;
            this.Encode = encode;
            this.Compress = compress;
            this.Decode = decode;
        }
    }

//            array[0] = new Experiment("CSVFiles/test.csv", "Duel on Syrtis.txt", "CompressedFile.txt", "decode.txt");
//            array[1] = new Experiment("CSVFiles/test.csv", "Duel on Syrtis.txt", "CompressedFile.txt", "decode.txt");
