import java.io.File;

class DirectoryTraverser
{
	FileParser fileParser;

	public DirectoryTraverser()
	{
		fileParser = new FileParser();
	}

	/*
	 * Function traverse() recursively traverses a given directory. When it encounters a .txt file,it calls the FileParser to parse it
	 */
	void traverse(String directory, InvertedIndex index)
	{
		File file = new File(directory);
		if (file.isFile())
		{
			if (file.getName().endsWith(".txt") || file.getName().endsWith(".TXT"))
			{
				fileParser.parseFile(file.getPath(), index);
			}
		} else if (file.isDirectory())
		{
			File[] fl = file.listFiles();
			int l = fl.length;
			for (int i = 0; i < l; ++i)
			{
				traverse(fl[i].toString(), index);
			}
		}
	}
}