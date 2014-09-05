
public class Driver {
	public InvertedIndex index = new InvertedIndex();

	public String outputFileName = "invertedindex.txt";

	/* If the argument has a "-d" flag and has value, call traverse method to traverse it, else print error message and exit. 
	 * If the argument has a "-i" flag and has value, output the words of this directory to "invertedindex.txt".
	 */
	private void getFiles(String[] args) 
	{
		DirectoryTraverser dt = new DirectoryTraverser();
		ArgumentParser ap = new ArgumentParser(args);
		if (ap.hasFlag("-d") && ap.getValue("-d") != null) {
			dt.traverse(ap.getValue("-d"), index);
		}
		else {
		       System.out.println("Error: No directory provided.");
		       return;
		}
		if (ap.hasFlag("-i") && ap.getValue("-i") != null)
			outputFileName = ap.getValue("-i");
	}

	public static void main(String[] args) {
		Driver driver = new Driver();
		driver.getFiles(args);
		driver.index.output(driver.outputFileName);
	}
}