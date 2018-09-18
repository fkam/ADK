/* Labb 2 i DD1352 Algoritmer, datastrukturer och komplexitet    */
/* Se labbanvisning under kurswebben https://www.kth.se/social/course/DD1352 */
/* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se      */
import java.util.LinkedList;
import java.util.List;

public class ClosestWords {
  static LinkedList<String> closestWords = new LinkedList<String>();

  int closestDistance = -1;

  int[][] partDist1(String w1, String w2, int w1len, int w2len){
    int[][] dist = new int[w1len+1][w2len+1];
    int i, j, res, add, del;

    for (i = 0; i <= w1len; i++)
        dist[i][0] = i;
    for (j = 0; j <= w2len; j++)
        dist[0][j] = j;    

    for (i = 1; i <= w1len; i++){
        for (j = 1; j <= w2len; j++){
          res = dist[i-1][j-1] + charCompare(w1, w2, i-1, j-1);
          add = dist[i-1][j] + 1;
          del = dist[i][j-1] + 1;
          dist[i][j] = min(res, add, del); 
        }
    }

    return dist;
  }

  int min(int res, int add, int del){
    if (add < res)
        return add;
    else if(del < res)
        return del;
    return res;
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
//    return partDist(w1, w2, w1.length(), w2.length());
    int v = partDist(w1, w2, w1.length(), w2.length());
    System.out.println("OLD: ");
    for (int i = 0; i <= w1.length(); i++){
        for (int j = 0; j <= w2.length(); j++){
            System.out.println(partDist(w1, w2, i, j)); //+ " " + w1.substring(0,i) + " " + w2.substring(0,j));
        }

        System.out.println();
    }
    System.out.println("NEW: ");
    int test[][] = partDist1(w1, w2, w1.length(), w2.length());
    for (int i = 0; i <=w1.length(); i++){
        for (int j = 0; j <= w2.length(); j++){
            System.out.println(test[i][j]);
        }
        System.out.println();
    }


  
    return v;
  }

  public ClosestWords(String w, List<String> wordList) {
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

  public static void main(String[] args){
    closestWords.add(0, args[0].toLowerCase());
    new ClosestWords(args[1].toLowerCase(), closestWords);
  }

}
