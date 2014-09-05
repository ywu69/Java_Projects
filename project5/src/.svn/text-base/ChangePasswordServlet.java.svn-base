import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
public class ChangePasswordServlet extends BaseServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		String oldpass = req.getParameter("oldpassword");
		String newpass = req.getParameter("newpassword");
		
		String user = getUsername(req);
		
		// authenticate user
		Status status = db.authenticateUser(user, oldpass);

		if(status == Status.OK)
		{
			status = db.changePassword(user, newpass);
		}
		
		if(status == Status.OK)
		{
			resp.sendRedirect(resp.encodeRedirectURL("/changepassword?tip=Successful Change Password!"));
		}else
		{
			resp.sendRedirect(resp.encodeRedirectURL("/changepassword?error=" + status.ordinal()));
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		prepareResponse("Change Password", resp);
		
		PrintWriter out = resp.getWriter();
		String error = req.getParameter("error");
		
		if(error != null) {
			String errorMessage = StringUtilities.getStatus(error).message();
			out.println("<p style=\"color: red;\">" + errorMessage + "</p>");
		}
		
		String tip = req.getParameter("tip");
		
		if(tip != null)
		{
			out.print("<p>" + tip + "</p>");
		}
		printFrom(out);
		finishResponse(req, resp);
	}
	
	private void printFrom(PrintWriter out)
	{
		out.println("<form action=\"/changepassword\" method=\"post\">");
		out.println("<table border=\"0\">");
		out.println("\t<tr>");
		out.println("\t\t<td>old Password:</td>");
		out.println("\t\t<td><input type=\"password\" name=\"oldpassword\" size=\"30\"></td>");
		out.println("\t</tr>");
		out.println("\t<tr>");
		out.println("\t\t<td>new Password:</td>");
		out.println("\t\t<td><input type=\"password\" name=\"newpassword\" size=\"30\"></td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("<p><input type=\"submit\" value=\"change\"></p>");
		out.println("</form>");
		out.println("<a href=\"/welcome\">Back</a>");
	}
}
