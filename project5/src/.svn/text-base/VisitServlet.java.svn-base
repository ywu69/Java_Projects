import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class VisitServlet extends BaseServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		String url = req.getParameter("url");
		String username = getUsername(req);
		db.insertVisitedHistory(username, url);
		resp.sendRedirect(url);
	}
}
