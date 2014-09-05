import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class LoginWelcomeServlet extends BaseServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String user = getUsername(request);

		if (user != null) {
			// update last login time
			db.updateLastLoginTime(user);
			
			// add user to user list
			userlist.add(user);
			
			prepareResponse("Welcome", response);
			PrintWriter out = response.getWriter();
			out.println("<p>Hello " + user + "!</p>");
			printAccountInfo(out);
			printSearchForm(out);
			ArrayList<String> suggestedQueryList = db.getSuggestQuery();
			printSuggestedQuery(out, suggestedQueryList);
			out.println("<p><a href=\"/login?logout\">(logout)</a></p>");

			finishResponse(request, response);
		}
		else {
			response.sendRedirect("/login");
		}
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		doGet(request, response);
	}
	
	private void printAccountInfo(PrintWriter out)
	{
		out.println("<a href=\"/changepassword\">Change Password</a>");
		out.println("<a href=\"/clearsearchhistory\">Clear History</a>");
		out.println("<a href=\"/visitedhistory\">Visited History</a>");
	}
	
	private void printSearchForm(PrintWriter out)
	{
		out.println("<form action=\"/search\" method=\"get\">");
		out.println("<input type=\"text\" id=\"queryword\" name=\"queryword\"></input>");
		out.println("<input type=\"checkbox\" name=\"privatesearch\">private search</input>");
		out.println("<input type=\"submit\" value=\"search\"></input>");
		out.println("</form>");
	}
	
	private void printSuggestedQuery(PrintWriter out, ArrayList<String> suggestedQueryList)
	{
		out.println("suggested query:<br>");
		
		for(String query : suggestedQueryList)
		{
			out.println("<a href=\"/search?queryword=" + query + "\">" + query + "</a><br>");
		}
	}
}
