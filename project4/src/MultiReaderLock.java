public class MultiReaderLock
{
	private int readers;
	private int writers;

	/**
	 * Initializes a multi-reader (single-writer) lock.
	 */
	public MultiReaderLock()
	{
		this.readers = 0;
		this.writers = 0;
	}

	/**
	 * Will wait until there are no active writers in the system, and then will increase the number of active readers.
	 */
	public synchronized void lockRead()
	{
		while (writers > 0)
		{
			try
			{
				this.wait();
			} catch (InterruptedException ex)
			{
			}
		}
		readers++;
	}

	/**
	 * Will decrease the number of active readers, and notify any waiting threads if necessary.
	 */
	public synchronized void unlockRead()
	{
		readers--;
		notifyAll();
	}

	/**
	 * Will wait until there are no active readers or writers in the system, and then will increase the number of active writers.
	 */
	public synchronized void lockWrite()
	{
		while (readers > 0 || writers > 0)
		{
			try
			{
				this.wait();
			} catch (InterruptedException ex)
			{
			}
		}
		writers++;
	}

	/**
	 * Will decrease the number of active writers, and notify any waiting threads if necessary.
	 */
	public synchronized void unlockWrite()
	{
		writers--;
		notifyAll();
	}
}