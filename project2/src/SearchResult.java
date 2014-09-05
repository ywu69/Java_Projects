

public class SearchResult implements Comparable<SearchResult>

{
	private String location;
	private int frequency = 0;
	private int position = Integer.MAX_VALUE;

	public SearchResult(String location) {
		this.location = location;
	}

	// Sort the SearchResults based on their frequency,position and location
	public int compareTo(SearchResult s) {
		if (this.frequency > s.frequency)
			return -1;
		else if (this.frequency < s.frequency)
			return 1;
		else if (this.position < s.position)
			return -1;
		else if (this.position > s.position)
			return 1;
		else
			return this.location.compareTo(s.location);
	}

	// Get the SearchResults' location
	public String getLocation() {
		return this.location;
	}

	// Get the SearchResults' frequency
	public int getFrequency() {
		return this.frequency;
	}

	// Get the SearchResults' position
	public int getPosition() {
		return this.position;
	}

	// Set the SearchResults' frequency
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	// Set the SearchResults' position
	public void setPosition(int position) {
		this.position = position;
	}
	
	// Update the SearchResults' frequency and position
		public void update(int anotherFrequency, int anotherPosition) {
			this.frequency += anotherFrequency;
			if (anotherPosition < this.position)
				this.position = anotherPosition;
		}
		
	// Returns a string with location, frequency and position in the correct format
		public String toString(){
			return "\"" + this.location + "\", " + this.frequency + ", " + this.position;
		}
}