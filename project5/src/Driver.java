import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public class Driver
{
	private static final Logger log = LogManager.getLogger();
	private static int PORT = 8080;
	public static InvertedIndex index;
	//public static IndexSearcher is;
	public static WebCrawler webCrawler;

	public static void main(String[] args)
	{
		ArgumentParser ap = new ArgumentParser(args);

		int threads = 3;

		if (ap.hasFlag("-t"))
		{
			if (ap.getValue("-t") != null)
			{
				try
				{
					threads = Integer.parseInt(ap.getValue("-t"));
					if (threads <= 0)
						threads = 3;
				} catch (NumberFormatException e)
				{
					System.out.println("-t value is not an integer. ");
				}
			}
		}

		WorkQueue workQueue = new WorkQueue(threads);

		index = new ThreadSafeInvertedIndex();
		//is = new MultithreadedIndexSearcher(workQueue);
		webCrawler = new WebCrawler(workQueue);

		if (ap.hasFlag("-u"))
		{
			if (ap.getValue("-u") != null)
			{
				webCrawler.traverse(ap.getValue("-u"), index);
				webCrawler.finish();
				log.info("webCrawler done");
			} else
			{
				System.out.println("Error: No URL provided.");
				return;
			}
		}

		if (ap.hasFlag("-p"))
		{
			if(ap.getValue("-p") != null)
			{
				PORT = Integer.parseInt(ap.getValue("-p"));
			}
		}

		Server server = new Server(PORT);

		ServletHandler handler = new ServletHandler();
		server.setHandler(handler);

		handler.addServletWithMapping(LoginUserServlet.class, "/login");
		handler.addServletWithMapping(LoginRegisterServlet.class, "/register");
		handler.addServletWithMapping(LoginWelcomeServlet.class, "/welcome");
		handler.addServletWithMapping(SearchServlet.class, "/search");
		handler.addServletWithMapping(ChangePasswordServlet.class, "/changepassword");
		handler.addServletWithMapping(ClearSearchHistoryServlet.class, "/clearsearchhistory");
		handler.addServletWithMapping(VisitServlet.class, "/visit");
		handler.addServletWithMapping(VisitedHistoryServlet.class, "/visitedhistory");
		handler.addServletWithMapping(LoginRedirectServlet.class, "/*");

		log.info("Starting server on port " + PORT + "...");

		try
		{
			server.start();
			server.join();

			log.info("Exiting...");
		} catch (Exception ex)
		{
			log.fatal("Interrupted while running server.", ex);
			System.exit(-1);
		}
	}
}