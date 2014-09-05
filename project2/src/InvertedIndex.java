import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

class InvertedIndex {

	private TreeMap<String, Map<String, Set<Integer>>> map;

	// Build the constructor of InvertedIndex
	public InvertedIndex() {
		map = new TreeMap<String, Map<String, Set<Integer>>>();
	}

	// add every word and its filePath and location into map
	public void addWordToMap(String word, String filePath, int location) {
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

	/*Search the keys in the tailMap which starts with the queryWords
	 * and return their searchResults from the tailMap.
	 */
	public ArrayList<SearchResult> partialSearch(String[] queryWords) {

		Map<String, SearchResult> hm = new HashMap<String, SearchResult>();

		for (String queryWord : queryWords) {
			SortedMap<String, Map<String, Set<Integer>>> tmap = map.tailMap(queryWord);
			Set<String> keys = tmap.keySet();
			for (Iterator<String> iter = keys.iterator(); iter.hasNext();) {
				String word = iter.next();
				if (!word.startsWith(queryWord)) {
					break;
				} else {
					Map<String, Set<Integer>> mp = tmap.get(word);
					Set<String> keys1 = mp.keySet();
					for (Iterator<String> iter1 = keys1.iterator(); iter1.hasNext();) {
						String location = iter1.next();
						Set<Integer> s = mp.get(location);
						int frequency = s.size();
						int position = Collections.min(s);
						if (!hm.containsKey(location)) {
							SearchResult sr = new SearchResult(location);
							sr.setFrequency(frequency);
							sr.setPosition(position);
							hm.put(location, sr);
						} else {
							SearchResult sr = hm.get(location);
							sr.update(frequency, position);
						}
					}
				}
			}
		}

		ArrayList<SearchResult> al = new ArrayList<SearchResult>(hm.values());
		Collections.sort(al);
		return al;
	}

	// output every word as requested format to .txt file
	public void output(String name) {
		File file = new File(name);

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.getMessage();
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
				for (Iterator<String> fmiter = fileMapKeys.iterator(); fmiter
						.hasNext();) {
					String filePath = fmiter.next();
					pw.print("\"" + filePath + "\",");

					Set<Integer> set = fileMap.get(filePath);

					for (Iterator<Integer> niter = set.iterator(); niter
							.hasNext();) {
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
			e.getMessage();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
}
