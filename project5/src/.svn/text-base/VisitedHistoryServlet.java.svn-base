import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
public class VisitedHistoryServlet extends BaseServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		prepareResponse("Visited History", resp);
		
		String username = getUsername(req);
		ArrayList<String[]> history = db.getVisitedHistory(username);
		PrintWriter out = resp.getWriter();
		
		out.println(username + " Visited History:<br>");
		out.println("<table border=\"1\">");
		out.println("<tr><th>url</th><th>time</th></tr>");
		for(String[] s : history)
		{
			out.println("<tr><td>" + addLink(s[0]) + "</td><td>" + s[1] + "</td></tr>");
		}
		out.println("</table>");
		out.println("<a href=\"/welcome\">Back</a>");
		
		finishResponse(req, resp);
	}
	
	private String addLink(String url)
	{
		return "<a href=\""+ url +"\">" + url + "</a>";
	}
}
