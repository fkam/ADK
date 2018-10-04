/* Labb 2 i DD1352 Algoritmer, datastrukturer och komplexitet    */
/* Se labbanvisning under kurswebben https://www.kth.se/social/course/DD1352 */
/* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se      */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.lang.StringBuilder;
import java.io.OutputStream;

public class Main {

  static StringBuilder buffer = new StringBuilder();
  static int[][] m = new int[41][41];

  public static List<String> readWordList(BufferedReader input) throws IOException {
    LinkedList<String> list = new LinkedList<String>();
    while (true) {
      String s = input.readLine();
      if (s.equals("#"))
        break;
      list.add(s);
    }
    return list;
  }

  public static void main(String args[]) throws IOException {
    //    long t1 = System.currentTimeMillis();
    for (int i = 0; i <= 40; i++){
        m[i][0] = i;
        m[0][i] = i;
    }
    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
    // Säkrast att specificera att UTF-8 ska användas, för vissa system har annan
    // standardinställning för teckenkodningen.
    List<String> wordList = readWordList(stdin);
    String word;
    while ((word = stdin.readLine()) != null) {
      ClosestWords closestWords = new ClosestWords(word, wordList, m);
      buffer.append(word + " (" + closestWords.getMinDistance() + ")");
//      System.out.print(word + " (" + closestWords.getMinDistance() + ")");
      for (String w : closestWords.getClosestWords())
     //   System.out.print(" " + w);
          buffer.append(" " + w);   
      //System.out.println();
      buffer.append("\n");
    }
    String s = buffer.toString();
    System.out.println(s);

//        long tottime = (System.currentTimeMillis() - t1);
  //      System.out.println("CPU time: " + tottime + " ms");
      
  }
}
