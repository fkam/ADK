/* Labb 2 i DD1352 Algoritmer, datastrukturer och komplexitet    */
/* Se labbanvisning under kurswebben https://www.kth.se/social/course/DD1352 */
/* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se      */
import java.util.LinkedList;
import java.util.List;
import java.lang.Math;

public class ClosestWords {
  static LinkedList<String> closestWords = null;
  int closestDistance = -1;
  int[][] m ;
  String prevWord = " ";
  int tempMax = 40;
  int start;

  int partDist1(String w1, String w2, int w1len, int w2len){
    int i, j, res, add, del;
//    start = checkPrev(w1);
    for (i = start; i <= w1len; i++){
        for (j = 1; j <= w2len; j++){
          res = m[i-1][j-1] + (w1.charAt(i-1) == w2.charAt(j-1) ? 0 : 1);
          add = m[i-1][j] + 1;
          del = m[i][j-1] + 1;
          m[i][j] = Math.min(del,Math.min(res, add));
        }
    }
    return m[w1len][w2len];
  }

  int Distance(String w1, String w2) {
    return partDist1(w1, w2, w1.length(), w2.length());
    }

  public ClosestWords(String w, List<String> wordList, int[][] matris) {
       
    m = matris;

    for (String s : wordList) {
      if( (Math.abs(w.length() - s.length()) <= tempMax) ){
        start = 0;
        while (start < s.length() && start < prevWord.length() && s.charAt(start) == prevWord.charAt(start))
            start++;
        prevWord = s;
        start++;    
        int dist = Distance(s, w);      
        if (dist < closestDistance || closestDistance == -1) {
            closestDistance = dist;
            tempMax = dist;
            closestWords = new LinkedList<String>();
            closestWords.add(s);
        } else if (dist == closestDistance)
            closestWords.add(s);
      }
    }
  }

  int getMinDistance() {
    return closestDistance;
  }

  List<String> getClosestWords() {
    return closestWords;
  }


}

