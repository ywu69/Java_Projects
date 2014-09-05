import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
public class ClearSearchHistoryServlet extends BaseServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		prepareResponse("Clear Search History", resp);
		String username = getUsername(req);
		String deleteid = req.getParameter("id");
		PrintWriter out = resp.getWriter();

		if(deleteid != null)
		{
			Status status = db.deleteSearchHistory(username, deleteid);
			System.out.println(status);
			if(status == Status.OK)
			{
				out.println("delete search history success!<br>");
			}else
			{
				out.println("delete search history fail!<br>");
			}
		}

		ArrayList<String[]> searchHistoryList = db.getSearchHistory(username);
		out.println(username + " search history:<br>");
		out.println("<table border=\"1\">");
		out.println("<tr><th>query word</th><th>time</th><th>delete</th></tr>");
		for(String[] s : searchHistoryList)
		{
			out.print("<tr><td>");
			out.print(s[1]+"</td><td>");
			out.print(s[2]+"</td><td>");
			out.print("<a href=\"/clearsearchhistory?id="+ s[0] +"\">delete</a></td></tr>");
		}
		out.print("</table>");
		out.println("<a href=\"/welcome\">Back</a>");
		finishResponse(req, resp);
	}
}
