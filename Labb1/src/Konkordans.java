import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.*;

/**
 * Created by xg on 2018-09-12.
 */
public class Konkordans {

    Lab1 hash = new Lab1();
    long index;
    static File wordIndex;
    static File next_wordIndex;
    static File counter;
    static File ut;
    static File korpus;
    static File prev;
    RandomAccessFile prevPos;
    RandomAccessFile outFile;
    RandomAccessFile nextOutFile;
    RandomAccessFile countFile;
    RandomAccessFile utFile;
    RandomAccessFile korpusFile;
    static Scanner in;

    public Konkordans() throws FileNotFoundException {

        wordIndex = new File("src/wordIndex");
        next_wordIndex = new File("src/nextwordIndex");
        ut = new File("src/ut");
        counter = new File("src/counter");
        korpus = new File("src/korpus");

        outFile = new RandomAccessFile(wordIndex, "r");
        nextOutFile = new RandomAccessFile(next_wordIndex, "r");
        countFile = new RandomAccessFile(counter, "r");
        utFile = new RandomAccessFile(ut, "r");
        korpusFile = new RandomAccessFile(korpus, "r");

    }

    public static void main(String[] args) throws IOException{
        if (args.length >= 2){
            System.out.println("Fel inmatning");
            System.exit(0);    
        }
            
        new Konkordans().start(args[0].toLowerCase());
    }

    public void start(String word) throws IOException {
        index = hash.getHash(word);
        outFile.seek(index);
        nextOutFile.seek(index);

        long i = outFile.readLong();
        long j = nextOutFile.readLong();
        System.out.printf("I: %d & J: %d \n", i, j); 
        if( i == j ) {
            System.out.println("Ordet existerar inte");
            System.exit(0);
        }
        Long p = search(i, j, word);

        if ( p == (long)-1 ){
            System.out.println("Ordet existerar inte");
            System.exit(0);
        }

        utFile.seek(p);
        countFile.seek(p);
        outputKorpus(word);

        outFile.close();
        nextOutFile.close();
        countFile.close();
        utFile.close();
        korpusFile.close();
     


    }

    public void outputKorpus(String word) throws IOException {

        int limit = countFile.readInt();
        String[] line;
        long pos;
        byte[] bytes = new byte[30 + word.length() + 30];;
        System.out.printf("Det förekommer %d \n", limit);
        String rowLine;
        if (limit > 25){
            System.out.print("Vill du skriva ut alla? (j/n)");
            in = new Scanner(System.in);
            String choice = in.next().toLowerCase();
            if (choice.equals("n"))
                System.exit(0);
        }
        for (int i = 0; i < limit; i++) {
            line = utFile.readLine().split(" ");
            pos = Long.parseLong(line[1]);
            korpusFile.seek(pos-30);
            korpusFile.read(bytes);
            rowLine = new String(bytes, "ISO-8859-1").trim();
            rowLine = rowLine.replace("\n", " ");
            System.out.println(rowLine);
        }
    }

    public long search(long i, long j, String word) throws IOException {
        long m;
        String[] foundWord;
        String line;
        while(j - i > 1000) {
            m = (i+j)/2;
            utFile.seek(m);
            utFile.readLine();
            foundWord = utFile.readLine().split(" ");
            System.out.printf("ORD: %s & POS: %s \n", foundWord[0], foundWord[1]);
            System.out.printf("I: %d & J: %d \n", i, j);
            if (foundWord[0].compareTo(word) < 0 ){
                i = m;
            } else {
                j = m;
            }
        }
        utFile.seek(i);
        System.out.printf("I: %d \n", i);
        while ( (line = utFile.readLine()) != null) {

            foundWord = line.split(" ");

            if (foundWord[0].equals(word)){
                return i;
            } else if ( (foundWord[0].compareTo(word)) > 0) {
                return Long.parseLong("-1");
            }
            i += line.length() +1;

        }
        return Long.parseLong("-1");
    }


}
