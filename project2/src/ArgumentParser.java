import java.util.HashMap;

import java.util.Iterator;

import java.util.Set;

public class ArgumentParser

{

	private HashMap<String, String> hm = new HashMap<String, String>();

	// Build the constructor of ArgumentParser
	public ArgumentParser(String[] args) {
		parseArgs(args);
	}

	// parses a String array of arguments into flag, value pairs and stores them in a map
	private void parseArgs(String[] args)

	{
		if (args.length > 0) {
			String f = null;

			for (int i = 0; i < args.length; i++) {
				if (isFlag(args[i])) {
					f = args[i];
					if (!hasFlag(args[i]))
						hm.put(args[i], null);
				}

				else if (isValue(args[i])) {
					if (!hasValue(f)) {
						hm.put(f, args[i]);
					}
				}
			}
		}
	}

	// tests whether or not an argument is considered a flag
	public static boolean isFlag(String arg)

	{
		if (arg.length() == 0) {
			return false;
		}
		boolean ret;
		ret = arg.charAt(0) == '-';
		return ret;

	}

	// tests whether or not an argument is considered a value
	public static boolean isValue(String arg)

	{
		if (arg.length() == 0) {
			return false;
		}
		boolean ret;
		ret = arg.charAt(0) != '-';
		return ret;

	}

	// tests whether or not a particular flag was passed in as a command-line argument
	public boolean hasFlag(String flag)

	{
		return hm.containsKey(flag);
	}

	// tests whether a particular flag both exists, and has a value associated with it
	public boolean hasValue(String flag)

	{
		if (flag.length() == 0) {
			return false;
		}
		boolean cot;
		cot = hasFlag(flag) && hm.get(flag) != null;
		return cot;
	}

	// retrieves the value associated with a flag (may return null if no value exists)
	public String getValue(String flag)

	{
		return (String) hm.get(flag);
	}

	// retrieves the number of flags (not arguments) that were passed in as command-line arguments
	public int numFlags()

	{
		return hm.size();
	}

	// retrieves the total number of command-line arguments passed in
	public int numArguments() {
		int ret = 0;
		for (Iterator<String> i = hm.keySet().iterator(); i.hasNext();) {
			String val = hm.get(i.next());
			if (val != null)
				ret += 2;
			else
				ret += 1;
		}
		return ret;
	}

	public static void main(String[] args)

	{
		ArgumentParser ap = new ArgumentParser(args);
		Set<String> s = ap.hm.keySet();
		Iterator<String> it = s.iterator();

		while (it.hasNext()) {
			String s2 = (String) it.next();
			System.out.println(s2 + "," + ap.hm.get(s2));
		}
	}
}
