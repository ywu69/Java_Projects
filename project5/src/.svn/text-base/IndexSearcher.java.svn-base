import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

public class IndexSearcher
{

	private LinkedHashMap<String, ArrayList<SearchResult>> searchResultsMap = new LinkedHashMap<String, ArrayList<SearchResult>>();

	/*
	 * Read the query files line by line and separate the words by separateWordByLine() function. Then call partialSearch function to search the queryWords in map, then get the searchResults, then store them to a LinkedHashMap
	 */
	public void parseQueryFile(String location, InvertedIndex index)
	{
		ArrayList<SearchResult> result;
		String queryLine;
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;

		try
		{
			fis = new FileInputStream(new File(location));
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);

			while ((queryLine = br.readLine()) != null)
			{
				String[] queryWords = separateWordByLine(queryLine);
				result = index.partialSearch(queryWords);
				searchResultsMap.put(queryLine, result);
			}

		} catch (IOException e)
		{
			e.getMessage();
		} finally
		{
			if (fis != null)
			{
				try
				{
					fis.close();
				} catch (IOException e)
				{
					e.getMessage();
				}
			}
		}
	}
	
	/*
	 * add searchResult to map
	 */
	protected void addResult(String line, ArrayList<SearchResult> result)
	{
		searchResultsMap.put(line, result);
	}
	
	/* 
	 * separate a file into words by line
	 */
	protected String[] separateWordByLine(String str)
	{
		String[] queryWords = str.split("[ ]+");

		for (int i = 0; i < queryWords.length; i++)
		{
			queryWords[i] = formatWord(queryWords[i]);
		}

		return queryWords;
	}

	/* 
	 * ignore all characters except letters and digits and to be case-insensitive
	 */
	private String formatWord(String queryWord)
	{

		queryWord = queryWord.trim().replaceAll("[^a-zA-Z0-9\\s]", "").toLowerCase();

		return queryWord;
	}

	/*
	 *  Out the queryWords and its searchResults to .txt files as requested format
	 */
	public void outputQuerySearchResults(String name) {

		File file = new File(name);

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.getMessage();
			}
		}

		FileWriter fw = null;
		PrintWriter pw = null;

		try {

			fw = new FileWriter(file);
			pw = new PrintWriter(fw);

			Set<String> keys = searchResultsMap.keySet();

			for (Iterator<String> iter = keys.iterator(); iter.hasNext();) {
				String queryWords = iter.next();
				pw.println(queryWords);

				for (Iterator<SearchResult> it = searchResultsMap.get(
						queryWords).iterator(); it.hasNext();) {
					SearchResult s = it.next();
					pw.print(s.toString());
					pw.print("\n");
				}
				pw.print("\n");
			}
			pw.flush();
		} catch (IOException e) {
			e.getMessage();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
}