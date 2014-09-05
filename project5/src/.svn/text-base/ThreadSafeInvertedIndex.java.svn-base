import java.util.ArrayList;

/*
 * This class overrides all methods of InvertedIndex to make them thread-safe using the custom lock.
 */

public class ThreadSafeInvertedIndex extends InvertedIndex
{
	private MultiReaderLock lock;

	public ThreadSafeInvertedIndex()
	{
		super();
		lock = new MultiReaderLock();
	}

	@Override
	public void addWordToMap(String word, String filePath, int location)
	{
		this.lock.lockWrite();
		super.addWordToMap(word, filePath, location);
		this.lock.unlockWrite();
	}

	@Override
	public void merge(InvertedIndex otherIndex)
	{
		this.lock.lockWrite();
		super.merge(otherIndex);
		this.lock.unlockWrite();
	}

	@Override
	public ArrayList<SearchResult> partialSearch(String[] queryWords)
	{
		this.lock.lockRead();
		ArrayList<SearchResult> result = super.partialSearch(queryWords);
		this.lock.unlockRead();
		return result;
	}

	@Override
	public void output(String name)
	{
		this.lock.lockRead();
		super.output(name);
		this.lock.unlockRead();
	}
}
