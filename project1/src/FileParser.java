
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

class FileParser {
	/*Read the files line by line and separate the words by separateWordByLine() function. 
     * Then add the word and its filePath and location to map*/
	public void parseFile(String filePath, InvertedIndex index)
	{
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;

		String str = "";
		String[] words = null;
		int location = 1;
		try {
			fis = new FileInputStream(new File(filePath));
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);

			while ((str = br.readLine()) != null) {
				words = separateWordByLine(str);

				for (String word : words) {
					if (!word.equals("")) {
						index.addWordToMap(word, filePath, location);
						location = location + 1;
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	// separate a file into words by any whitespace,including spaces, tabs, and new line characters
	private String[] separateWordByLine(String str) 
	{
		String[] words = str.split("[ ]+");

		for (int i = 0; i < words.length; i++) {
			words[i] = formatWord(words[i]);
		}

		return words;
	}

	// ignore all characters except letters and digits and to be case-insensitive
	private String formatWord(String word)
	{

		word = word.trim().replaceAll("[^a-zA-Z0-9\\s]", "").toLowerCase();

		return word;
	}
}
