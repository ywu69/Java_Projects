public class Driver {

	public static void main(String[] args) {

		InvertedIndex index = new InvertedIndex();
		IndexSearcher is = new IndexSearcher();

		String outputFileName = "";
		String queryoutputFileName = "";
		String queryinputFileName = "";

		DirectoryTraverser dt = new DirectoryTraverser();
		ArgumentParser ap = new ArgumentParser(args);

		/*
		 * If the argument has a "-d" flag and has value, call traverse method
		 * to traverse it, else print error message.
		 */
		if (ap.hasFlag("-d") && ap.getValue("-d") != null) {
			dt.traverse(ap.getValue("-d"), index);
		} else {
			System.out.println("Error: No directory provided.");
			return;
		}

		/*
		 * If the argument has a "-i" flag and has value, output the index to
		 * the given file, else output the index to default file and print error
		 * message.
		 */
		if (ap.hasFlag("-i")) {
			if (ap.getValue("-i") != null)
				outputFileName = ap.getValue("-i");
			else {
				outputFileName = "invertedindex.txt";
				System.out.println("Error: No Output provided.");
			}
		}

		/*
		 * If the argument has not "-i" flag and has no "-q" flag, output the
		 * index to default file. If the argument has "-i" flag and has "-q"
		 * flag and "-q" flag has value, call parseQueryFile function to parse
		 * the QueryFile. Else print a error message.
		 */
		else {
			if (!ap.hasFlag("-q"))
				outputFileName = "invertedindex.txt";
		}

		if (ap.hasFlag("-q") && ap.getValue("-q") != null) {
			queryinputFileName = ap.getValue("-q");
			is.parseQueryFile(queryinputFileName, index);
		} else {
			System.out.println("Error: No queryFile provided.");
		}

		/*
		 * If the argument has "-r" flag and "-r" flag has value, output the
		 * queryWords and searchResult to the given file. Else output the
		 * queryWords to the default file and then print a error message.
		 */
		if (ap.hasFlag("-r")) {
			if (ap.getValue("-r") != null)
				queryoutputFileName = ap.getValue("-r");

			else {
				queryoutputFileName = "searchresults.txt";
				System.out.println("Error: No queryOutput provided.");
			}
		}

		if (!outputFileName.equals(""))
			index.output(outputFileName);
		if (!queryoutputFileName.equals(""))
			is.outputQuerySearchResults(queryoutputFileName);
	}
}