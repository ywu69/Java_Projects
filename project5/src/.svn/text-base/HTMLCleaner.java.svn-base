import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * This class does not take a particularly efficient approach, but this
 * simplifies the process of retrieving and cleaning HTML code for your
 * web crawler project later.
 */

/*
 * A helper class with several static methods that will help fetch a webpage, strip out all of the HTML, and parse the resulting plain text into
 * words. Meant to be used for the web crawler project.
 */
public class HTMLCleaner
{
	
	
	public static ArrayList<String> parseURL(String urlString, InvertedIndex index)
	{
		HTMLFetcher fetcher = null;
		ArrayList<String> list = new ArrayList<String>();
		try
		{
			fetcher = new HTMLFetcher(urlString);
			String text = fetcher.fetch();

			// get all link in the page
			ArrayList<String> tmpList = HTMLLinkParser.listLinks(text);
			for (String url : tmpList)
			{
				URL base = new URL(urlString);
				URL absolute = new URL(base, url);
				list.add(absolute.toString());
			}

			// clean html elements
			text = HTMLCleaner.cleanHTML(text);
			
			// separate words and add them to inverted index
			String[] words = separateWordByLine(text);
			int location = 1;
			for (String word : words)
			{
				if (!word.isEmpty())
				{
					index.addWordToMap(word, urlString, location);
					location = location + 1;
				}
			}
		} catch (IOException e)
		{
			System.out.println(e.getMessage());
		}

		return list;
	}

	/*
	 * Removes all style and script tags (and any text in between those tags), all HTML tags, and all special characters/entities.
	 * @param - html code to parse
	 * @return plain text
	 */
	public static String cleanHTML(String html)
	{
		String text = html;
		text = stripElement("script", text);
		text = stripElement("style", text);
		text = stripTags(text);
		text = stripEntities(text);
		return text;
	}

	/*
	 * Removes everything between the element tags, and the element tags themselves. For example, consider the html code:
	 * 
	 * <pre>
	 * &lt;style type="text/css"&gt;body { font-size: 10pt; }&lt;/style&gt;
	 * </pre>
	 * 
	 * If removing the "style" element, all of the above code will be removed, and replaced with the empty string.
	 * 
	 * @param name
	 *            - name of the element to strip, like style or script
	 * @param html
	 *            - html code to parse
	 * @return html code without the element specified
	 */
	public static String stripElement(String name, String html)
	{
		String regax = "<" + name + "[^>]*?>[\\s\\S]*?</" + name + ">";

		Pattern p = Pattern.compile(regax, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(html);
		html = m.replaceAll(" ");

		return html;
	}

	/*
	 * Removes all HTML tags, which is essentially anything between the < and > symbols. The tag will be replaced by the empty string.
	 * @param - html code to parse
	 * @return text without any html tags
	 */
	public static String stripTags(String html)
	{
		String regax = "<[^>]+>";

		Pattern p = Pattern.compile(regax);
		Matcher m = p.matcher(html);
		html = m.replaceAll(" ");

		return html;
	}

	/*
	 * Replaces all HTML entities in the text with the empty string. For example, "2010&ndash;2012" will become "20102012".
	 * @param - the text with html code being checked
	 * @return text with HTML entities replaced by a space
	 */
	public static String stripEntities(String html)
	{
		String regax = "&[^;]+?;";

		Pattern p = Pattern.compile(regax);
		Matcher m = p.matcher(html);
		html = m.replaceAll(" ");

		return html;
	}
	
	/*
	 *  separate a file into words by any whitespace,including spaces, tabs, and new line characters
	 */
	private static String[] separateWordByLine(String str)
	{
		String[] words = str.split("[ ]+");

		for (int i = 0; i < words.length; i++)
		{
			words[i] = formatWord(words[i]);
		}

		return words;
	}

	/*
	 *  ignore all characters except letters and digits and to be case-insensitive
	 */
	private static String formatWord(String word)
	{

		word = word.trim().replaceAll("[^a-zA-Z0-9\\s]", "").toLowerCase();

		return word;
	}
}
