import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
 * This class makes IndexSearcher MultiThreaded by adding a new SearchWork to the queue for each queryLine.
 */
public class MultithreadedIndexSearcher extends IndexSearcher {
	private WorkQueue minions;
	private int pending;
	private MultiReaderLock lock;

	public MultithreadedIndexSearcher(WorkQueue minions) {
		super();
		this.minions = minions;
		lock = new MultiReaderLock();
	}

	@Override
	public void parseQueryFile(String location, InvertedIndex index) {
		String queryLine;
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;

		try {
			fis = new FileInputStream(new File(location));
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);

			while ((queryLine = br.readLine()) != null) {
				if (!queryLine.equals("")) {
					addResult(queryLine, new ArrayList<SearchResult>());
					minions.execute(new SearchWork(queryLine,(ThreadSafeInvertedIndex) index));
				}
			}
		} catch (IOException e) {
			e.getMessage();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.getMessage();
				}
			}
		}
	}

	public synchronized void finish() {
		try {
			while (pending > 0) {
				this.wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public synchronized void shutdown() {
		finish();
		minions.shutdown();
	}

	/*
	 * Do partial search and add results to the LinkedHashMap by Multithread.
	 */
	private class SearchWork implements Runnable {
		String queryLine;
		ThreadSafeInvertedIndex index;

		public SearchWork(String line, ThreadSafeInvertedIndex index) {
			this.queryLine = line;
			this.index = index;

			incrementPending();
		}

		@Override
		public void run() {
			String[] queryWords = separateWordByLine(queryLine);
			ArrayList<SearchResult> result = index.partialSearch(queryWords);
			addResult(queryLine, result);
			decrementPending();
		}
	}

	/*
	 *  make addResult() thread-safe by use custom lock
	 */
	@Override
	protected void addResult(String line, ArrayList<SearchResult> result) {
		lock.lockWrite();
		super.addResult(line, result);
		lock.unlockWrite();
	}

	/*
	 *  increase pending
	 */
	private synchronized void incrementPending() {
		pending++;
	}

	/*
	 *  decrease pending
	 */
	private synchronized void decrementPending() {
		pending--;

		if (pending <= 0) {
			this.notifyAll();
		}
	}
}
