
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

class InvertedIndex {
		
	private Map<String, Map<String, Set<Integer>>> map;
	
	// Build the constructor of InvertedIndex
	public InvertedIndex() {
				map = new TreeMap<String, Map<String, Set<Integer>>>();
	}
	
	// add every word and its filePath and location into map
	public void addWordToMap(String word, String filePath, int location)
	{
		if (map.get(word) == null) {
			Set<Integer> set = new TreeSet<Integer>();
			Map<String, Set<Integer>> fileMap = new TreeMap<String, Set<Integer>>();
			fileMap.put(filePath, set);
			map.put(word, fileMap);
		}

		Map<String, Set<Integer>> fileMap = map.get(word);

		if (!fileMap.containsKey(filePath)) {
			Set<Integer> set = new TreeSet<Integer>();
			fileMap.put(filePath, set);
		}
		Set<Integer> set = fileMap.get(filePath);
		set.add(location);
	}

	// output every word as requested format to .txt file
	public void output(String name)
	{
		File file = new File(name);

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		FileWriter fw = null;
		PrintWriter pw = null;
        
		try {
			 fw = new FileWriter(file);
			 pw = new PrintWriter(fw);
			 Set<String> keys = map.keySet();

			for (Iterator<String> iter = keys.iterator(); iter.hasNext();) {
				String word = iter.next();
				pw.println(word);
				
				Map<String, Set<Integer>> fileMap = map.get(word);
				Set<String> fileMapKeys = fileMap.keySet();
			        for (Iterator<String> fmiter = fileMapKeys.iterator(); fmiter.hasNext();) {
					String filePath = fmiter.next();	 
					pw.print("\"" + filePath + "\",");
					
					Set<Integer> set = fileMap.get(filePath);
					
					for (Iterator<Integer> niter = set.iterator(); niter.hasNext();) {
						pw.print(" " + String.valueOf(niter.next()));

						if (niter.hasNext()) {
							pw.print(",");
						}
					}
					pw.print("\n");
				}
				pw.print("\n");
			}
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
}
