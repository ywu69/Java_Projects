import java.io.File;

/*
 * A new FileWorker is added to the queue when we need to traverse a subdirectory or when we need to parse a file. 
 * For parsing the file, we first created a local variable, InvertedIndex newIndex, add all the data to newIndex. 
 * Then merge newIndex with the shared index.
 */
public class MultithreadedDirectoryTraverser extends DirectoryTraverser
{
	private WorkQueue minions;
	private int pending;
	
	public MultithreadedDirectoryTraverser(WorkQueue minions)
	{
		super();
		this.minions = minions;
		pending = 0;
	}
	
	public synchronized void finish()
	{
		try
		{
			while(pending > 0)
			{
				this.wait();
			}
		}catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	public synchronized void shutdown()
	{
		finish();
		minions.shutdown();
	}
	
	@Override
	public void traverse(String directory, InvertedIndex index)
	{
		File file = new File(directory);
		if(file.isFile() || file.isDirectory())
		{
			minions.execute(new FileWorker(file, (ThreadSafeInvertedIndex)index));
		}
	}

	/*
     * Create a newIndex,then create a FileParser for each file that will parse this file. Once done, merge newIndex with invertedIndex
     */
	private class FileWorker implements Runnable
	{
		private File file;
		private ThreadSafeInvertedIndex index;
		
		public FileWorker(File file, ThreadSafeInvertedIndex index)
		{
			this.file = file;
			this.index = index;
			incrementPending();
		}
		
		@Override
		public void run()
		{
			if(file.isDirectory())
			{
				for(File f : file.listFiles())
				{
					minions.execute(new FileWorker(f, index));
				}
			}else if(file.isFile())
			{
				if (file.getName().endsWith(".txt") || file.getName().endsWith(".TXT"))
				{
					InvertedIndex newIndex = new InvertedIndex();
					FileParser fileParser = new FileParser();
					fileParser.parseFile(file.getAbsolutePath(), newIndex);
					this.index.merge(newIndex);
				}
			}	
			decrementPending();
		}
	}
	
	// increase pending
	private synchronized void incrementPending()
	{
		pending++;
	}
	
	// decrease pending
	private synchronized void decrementPending()
	{
		pending--;
		
		if(pending <= 0)
		{
			this.notifyAll();
		}
	}
}
