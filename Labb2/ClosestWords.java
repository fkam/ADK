/* Labb 2 i DD1352 Algoritmer, datastrukturer och komplexitet    */
/* Se labbanvisning under kurswebben https://www.kth.se/social/course/DD1352 */
/* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se      */
import java.util.LinkedList;
import java.util.List;
import java.lang.Math;

public class ClosestWords {
  static LinkedList<String> closestWords = null;
  int closestDistance = -1;
  int[][] dist = new int[41][41];
  String prevWord = null;

  int partDist1(String w1, String w2, int w1len, int w2len){
    int i, j, res, add, del, start;
    start = checkPrev(w1);
    for (i = start; i <= w1len; i++){
        for (j = start; j <= w2len; j++){
          res = dist[i-1][j-1] + charCompare(w1, w2, i-1, j-1);
          add = dist[i-1][j] + 1;
          del = dist[i][j-1] + 1;
          dist[i][j] = min(res, add, del); 
        }
    }

    return dist[w1len][w2len];
  }

  int checkPrev(String word){
    int counter = 0, len;
    if (prevWord == null){
        prevWord = word;
        return 1;
    }
    len = prevWord.length();
    if (prevWord.length() > word.length())
        len = word.length();
    for (int i = 0; i < len; i++){
        if (charCompare(prevWord, word, i, i) == 0)
            counter++;
        else if (counter == 0){
            prevWord = word;
            return 1; 
        } else 
            return counter;
    }
    return counter; 
  }

  int min(int res, int add, int del){
    return Math.min(Math.min(res, add), del);
  }

  int charCompare(String w1, String w2, int pos1, int pos2){
      return (w1.charAt(pos1) == w2.charAt(pos2) ? 0 : 1);
  }

  int partDist(String w1, String w2, int w1len, int w2len) {
    if (w1len == 0)
      return w2len;
    if (w2len == 0)
      return w1len;
    int res = partDist(w1, w2, w1len - 1, w2len - 1) + 
	(w1.charAt(w1len - 1) == w2.charAt(w2len - 1) ? 0 : 1);
    int addLetter = partDist(w1, w2, w1len - 1, w2len) + 1;
    if (addLetter < res)
      res = addLetter;
    int deleteLetter = partDist(w1, w2, w1len, w2len - 1) + 1;
    if (deleteLetter < res)
      res = deleteLetter;
    return res;
  }

  int Distance(String w1, String w2) {
    return partDist1(w1, w2, w1.length(), w2.length());
    }


  

  public ClosestWords(String w, List<String> wordList) {

    for (int i = 0; i <= 40; i++){
        dist[i][0] = i;
        dist[0][i] = i;
    }

    for (String s : wordList) {
      int dist = Distance(w, s);
      // System.out.println("d(" + w + "," + s + ")=" + dist);
      if (dist < closestDistance || closestDistance == -1) {
        closestDistance = dist;
        closestWords = new LinkedList<String>();
        closestWords.add(s);
      }
      else if (dist == closestDistance)
        closestWords.add(s);
    }
  }

  int getMinDistance() {
    return closestDistance;
  }

  List<String> getClosestWords() {
    return closestWords;
  }


}
