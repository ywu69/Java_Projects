import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class SearchServlet extends BaseServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		prepareResponse("Search Result", resp);

		String queryWord = req.getParameter("queryword");
		String privateSearch = req.getParameter("privatesearch");
		String username = getUsername(req);

		if (privateSearch == null)
			db.insertSearchHistory(queryWord, username);

		String[] queryWords = separateWordByLine(queryWord);
		long timestart = System.currentTimeMillis();
		ArrayList<SearchResult> results = Driver.index.partialSearch(queryWords);
		long timeend = System.currentTimeMillis();

		PrintWriter out = resp.getWriter();
		out.printf("%s search result: Hit %d records in %d ms<br>", queryWord, results.size(), timeend - timestart);
		for (SearchResult result : results)
		{
			String url = result.getLocation();
			out.println(result.toString());
			printVisitFrom(out, url);
		}
		finishResponse(req, resp);
	}

	private void printVisitFrom(PrintWriter out, String url)
	{
		out.println("<a href=\"/visit?url=" + url + "\">visit</a><br>");
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
}
