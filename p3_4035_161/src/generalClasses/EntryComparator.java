package generalClasses;

import java.util.Comparator;
import java.util.Map.Entry;

import dataManagement.MatchingSearchDocument;

/**
 * Comparator objects for Entries. Compares the keys of two entries.
 * @author Joel Torres
 *
 */
public class EntryComparator implements Comparator<Entry<Double, MatchingSearchDocument>> {

	/**
	 * Method to compare the two keys of two entries.
	 * @return -1 if o1 key goes before o2 key, 0 if equal, 1 otherwise
	 */
	public int compare(Entry<Double, MatchingSearchDocument> o1, Entry<Double, MatchingSearchDocument> o2) {
		if (o1.getKey() < o2.getKey())
			return -1;
		else if (o2.getKey() > o2.getKey())
			return 1;
		return 0;

	}
}
