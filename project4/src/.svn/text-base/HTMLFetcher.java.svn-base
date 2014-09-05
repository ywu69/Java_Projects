import java.io.*;
import java.net.*;

/*
 * A class designed to make fetching the results of different HTTP operations easier. This particular class handles the GET operation.
 */
public class HTMLFetcher
{
	private static final int PORT = 80;
	private URL url;
	private boolean head;

	public HTMLFetcher(String url) throws MalformedURLException
	{
		this.url = new URL(url);
		head = true;
	}

	public URL getURL()
	{
		return url;
	}

	/*
     * Crafts the HTTP GET request from the URL.
     */
	private String createRequest()
	{
		String host = this.getURL().getHost();
		String resource = this.getURL().getFile().isEmpty() ? "/" : this.getURL().getFile();

		StringBuffer output = new StringBuffer();
		output.append("GET " + resource + " HTTP/1.1\n");
		output.append("Host: " + host + "\n");
		output.append("Connection: close\n");
		output.append("\r\n");

		return output.toString();
	}

	/*
     * Will skip any headers returned by the web server, and then output each line of HTML to the console.
     */
	private void processLine(String line)
	{
		if (head)
		{
			if (line.trim().isEmpty())
			{
				head = false;
			}
		}
	}

	/*
     * Will connect to the web server and fetch the URL using the HTTP request
     * from {@link #craftRequest()}, and then call {@link #processLine(String)}
     * on each of the returned lines.
     */
	public String fetch()
	{

		System.out.println("Server: " + url.getHost() + ":" + PORT);
		StringBuffer out = new StringBuffer();

		try (Socket socket = new Socket(url.getHost(), PORT); 
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
				PrintWriter writer = new PrintWriter(socket.getOutputStream());)
		{
			String request = createRequest();
			System.out.println("HTTP: " + request);

			writer.println(request);
			writer.flush();

			String line = reader.readLine();
			head = true;
			while (line != null)
			{
				processLine(line);

				if (head == false)
					out.append(line).append(" ");
				line = reader.readLine();
			}
		} catch (IOException e)
		{
			e.getMessage();
		}
		return out.toString();
	}
}