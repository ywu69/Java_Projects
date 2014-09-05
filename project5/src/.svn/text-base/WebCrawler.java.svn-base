import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/*
 * This class Crawl web page by adding a new CrawlerWorker to each link.
 */
public class WebCrawler
{
	private Set<String> links;

	private WorkQueue minions;
	private int pending;

	public WebCrawler(WorkQueue minions)
	{
		this.minions = minions;
		links = new HashSet<String>();
	}

	/*
	 * Decide whether the url can add to links list.
	 */
	private synchronized boolean canUrlEnterWorkQueue(String url)
	{
		return !links.contains(url) && links.size() < 50;
	}

	/*
	 * Add the url to links list.
	 */
	private synchronized void addLink(String url)
	{
		if (links.size() < 50 && !links.contains(url))
			links.add(url);
	}

	/*
	 * Traverse each seed Url by each CrawlerWorker.
	 */
	public void traverse(String base, InvertedIndex index)
	{
		minions.execute(new CrawlerWorker(base, (ThreadSafeInvertedIndex) index));
	}

	/*
	 * Create a newIndex, then parse the URL into newIndex. Once done, merge newIndex and InvertedIndex.
	 * get all URL links in the page, decide what link can be added to the WorkQueue and get it to work.
	 */
	private class CrawlerWorker implements Runnable
	{
		private String url;
		private ThreadSafeInvertedIndex index;

		public CrawlerWorker(String url, ThreadSafeInvertedIndex index)
		{
			this.url = url;
			this.index = index;
			
			addLink(url);
			
			incrementPending();
		}

		@Override
		public void run()
		{
			InvertedIndex newIndex = new InvertedIndex();
			ArrayList<String> list = HTMLCleaner.parseURL(url, newIndex);
			this.index.merge(newIndex);

			for(String u : list)
			{
				if(canUrlEnterWorkQueue(u))
				{
					minions.execute(new CrawlerWorker(u, index));
				}
			}
			decrementPending();
		}

	}

	public synchronized void finish()
	{
		try
		{
			while (pending > 0)
			{
				this.wait();
			}
		} catch (InterruptedException e)
		{
			e.getMessage();
		}
	}

	public synchronized void shutdown()
	{
		finish();
		minions.shutdown();
	}

	/* 
	 * increase pending
	 */
	private synchronized void incrementPending()
	{
		pending++;
	}

	/* 
	 * decrease pending
	 */
	private synchronized void decrementPending()
	{
		pending--;

		if (pending <= 0)
		{
			this.notifyAll();
		}
	}
}
