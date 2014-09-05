
public class Driver
{

	public static void main(String[] args)
	{
		String outputFileName = "";
		String queryoutputFileName = "";
		String queryinputFileName = "";

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

		InvertedIndex index = new ThreadSafeInvertedIndex();
		IndexSearcher is = new MultithreadedIndexSearcher(workQueue);
		DirectoryTraverser dt = new MultithreadedDirectoryTraverser(workQueue);
		WebCrawler webCrawler = new WebCrawler(workQueue);

		/*
		 * If the argument has a "-u" flag and has value, call traverse method to traverse the seed Url, else print error message.
		 */
		if (ap.hasFlag("-u"))
		{
			if (ap.getValue("-u") != null)
			{
				webCrawler.traverse(ap.getValue("-u"), index);
				webCrawler.finish();
			} else
			{
				System.out.println("Error: No URL provided.");
				return;
			}
		}

		/*
		 * If the argument has a "-d" flag and has value, call traverse method to traverse it, else print error message.
		 */
		if (ap.hasFlag("-d") && ap.getValue("-d") != null)
		{
			dt.traverse(ap.getValue("-d"), index);
			((MultithreadedDirectoryTraverser) dt).finish();
		} else
		{
			System.out.println("Error: No directory provided.");
			if (!ap.hasFlag("-u") && ap.getValue("-u") == null)
			{
				return;
			}
		}


		/*
		 * If the argument has a "-i" flag and has value, output the index to the given file, else output the index to default file and print error message.
		 */
		if (ap.hasFlag("-i"))
		{
			if (ap.getValue("-i") != null)
				outputFileName = ap.getValue("-i");
			else
			{
				outputFileName = "invertedindex.txt";
				System.out.println("Error: No Output provided.");
			}
		}

		/*
		 * If the argument has not "-i" flag and has no "-q" flag, output the index to default file. If the argument has "-i" flag and has "-q" flag and "-q" flag has value, call parseQueryFile
		 * function to parse the QueryFile. Else print a error message.
		 */
		else
		{
			if (!ap.hasFlag("-q"))
				outputFileName = "invertedindex.txt";
		}

		if (ap.hasFlag("-q") && ap.getValue("-q") != null)
		{
			queryinputFileName = ap.getValue("-q");
			is.parseQueryFile(queryinputFileName, index);
			((MultithreadedIndexSearcher) is).finish();
		} else
		{
			System.out.println("Error: No queryFile provided.");
		}

		/*
		 * If the argument has "-r" flag and "-r" flag has value, output the queryWords and searchResult to the given file. Else output the queryWords to the default file and then print a error
		 * message.
		 */
		if (ap.hasFlag("-r"))
		{
			if (ap.getValue("-r") != null)
				queryoutputFileName = ap.getValue("-r");
			else
			{
				queryoutputFileName = "searchresults.txt";
				System.out.println("Error: No queryOutput provided.");
			}
		}

		if (!outputFileName.equals(""))
			index.output(outputFileName);
		if (!queryoutputFileName.equals(""))
			is.outputQuerySearchResults(queryoutputFileName);

		workQueue.shutdown();
	}
}