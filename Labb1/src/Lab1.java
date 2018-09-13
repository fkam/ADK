
import java.io.RandomAccessFile;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class Lab1{

	static File wordIndex;
	static File next_wordIndex;
	static File counter;
	static File ut; 

	public static void main(String[] args) throws IOException{
		new Lab1().start();

	}

	public void start() throws IOException{
		wordIndex = new File("src/wordIndex");
		next_wordIndex = new File("src/nextwordIndex");
		ut = new File("src/ut");
		counter = new File("src/counter");
		CreateWordIndex();
	}
	/**
	 * Metoden skapar två filer där de innehåller byte-positionen
	 * för ut filen , för varje unika tre bokstäver
	 * wordIndex innehåller den första positionen i ut filen
	 * next_wordIndex innehåller den kommande positionen
	*/
	private static void CreateWordIndex() throws IOException{
		String line, current, old = "", tmp_word = "a";
		String[] words;
		long index, pos = 0, old_pos = 0, tmp_pos = 0;
		int count = 0;

		InputStream in = new FileInputStream("src/ut");
		BufferedReader r = new BufferedReader(new InputStreamReader(in, "ISO-8859-1"));
		
		RandomAccessFile out = new RandomAccessFile(wordIndex, "rw");
		RandomAccessFile next_out = new RandomAccessFile(next_wordIndex, "rw");
		RandomAccessFile countFile = new RandomAccessFile(counter, "rw");
		RandomAccessFile utFile = new RandomAccessFile(ut, "r");

		countFile.setLength(utFile.length());
		out.setLength(20*20*20*8);
		next_out.setLength(20*20*20*8);

		while( (line = r.readLine()) != null){

			words = line.split(" ");

			if(!words[0].equals(tmp_word)){
				countFile.seek(tmp_pos);
				countFile.writeInt(count);
				tmp_pos = pos;
				tmp_word = words[0];
				count = 0;
			}
            

			current = words[0];
			if (current.length() > 3)
				current = current.substring(0,3);

			if (!current.equals(old)){
				if (old.equals("")){
					;
				}else {
					index = getHash(old);
					out.seek(index);
					out.writeLong(old_pos);

					next_out.seek(index);
					next_out.writeLong(pos);
				}

				old_pos = pos;
				old = current;
			
			}

			pos += line.length() + 1;
			count++;
		}
		//last index 
		index = getHash(old);

		out.seek(index);
		out.writeLong(old_pos);

		next_out.seek(index);
		next_out.writeLong(pos);
		if (count > 0){
			countFile.seek(tmp_pos);
			countFile.writeInt(count);
		}

		utFile.close();
		countFile.close();
		out.close();
        next_out.close();
	}

	
	public static long getHash(String word){
		long pos = 0;
		if (word.length() > 3) 
			word = word.substring(0,3);
		char[] chars = word.toLowerCase().toCharArray();
		for(int i=0; i < chars.length; i++){
			if ( (int)chars[i] == 229 )    		// å
				pos += 8 * Math.pow(20, 3-i);
			else if ( (int)chars[i] == 228 )    // ä
				pos += 9 * Math.pow(20, 3-i);
			else if ( (int)chars[i] == 246 )  	// ö
				pos += 10 * Math.pow(20, 3-i);
			else if ( (int)chars[i] > 96 && (int)chars[i] < 123 )	// a - z
				pos += (int)chars[i]*Math.pow(20, 3-i);
		}
		return pos;
	}


}
