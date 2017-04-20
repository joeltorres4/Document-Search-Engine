package dataManagement;

/**
 * This type of object represents a word in a document. For a particular word,
 * it contains the string that forms the particular word as well as the location
 * of that word in the file for the document that the word is part of. The
 * location is the index in file of the first character (byte) in the word...
 * 
 * @author Joel Torres
 *
 */
public class WordInDocument implements Comparable<WordInDocument> {
	private String word;
	private long location;

	/**
	 * Constructor for a new WordInDocument object given its word and the
	 * location in the file.
	 * 
	 * @param word
	 *            word of file
	 * @param location
	 *            location in file
	 */
	public WordInDocument(String word, long location) {
		this.word = word;
		this.location = location;
	}

	/**
	 * Compares a WordInDocument object with another, depending on its word.
	 * 
	 * @return -1 if goes before, 0 if same, 1 if goes after
	 */
	public int compareTo(WordInDocument other) {
		return this.word.compareTo(other.word);
	}

	/**
	 * Getter for word of WordInDocument object.
	 * 
	 * @return the word of the object
	 */
	public String getWord() {
		return word;
	}

	/**
	 * Getter for location of WordInDocument
	 * 
	 * @return location of the word
	 */
	public long getLocation() {
		return location;
	}

	/**
	 * Determines if other is an instance of WordInDocument and compares
	 * their words
	 * 
	 * @return true if both words are the same, false otherwise
	 */
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (!(other instanceof WordInDocument))
			return false;
		WordInDocument oWID = (WordInDocument) other;

		return word.equals(oWID.word);
	}

	/**
	 * Generates a string with word and word location
	 * 
	 * @return String representation of word and word location
	 */
	public String toString() {
		return "[" + word + ", " + location + "]";
	}
}
