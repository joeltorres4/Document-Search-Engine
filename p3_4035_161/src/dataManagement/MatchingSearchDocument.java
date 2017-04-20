package dataManagement;

import java.io.IOException;
import java.util.ArrayList;

/**
 * An object of this type is created for each document that matches a particular
 * search; that is, a document that contains at least one of the words in the
 * searching list.
 * 
 * @author Joel Torres
 *
 */
public class MatchingSearchDocument {
	private DocumentIDX docIDX; // contains data read from the idx file of the
								// document
	private Document document; // the correponding document....

	// the following is the list of words (from the search list)
	// that are part of the document
	private ArrayList<String> matchingWords; // words
	private ArrayList<Long> matchingLocations; // locations in document

	/**
	 * Constructor for MatchingSearchDocument. Initializes it with its
	 * corresponding Document and its lists of matching words and locations.
	 * 
	 * @param docID
	 *            id of document
	 * @throws IllegalArgumentException
	 *             may throw this exception if the given id is invalid (not in
	 *             range).
	 * @throws IOException
	 *             may throw this exception if an Input/Output error occurs
	 *             while accessing the index directory.
	 */
	public MatchingSearchDocument(int docID) throws IllegalArgumentException, IOException {
		docIDX = new DocumentIDX(docID); // instantiates object with data from
											// IDX file
		matchingWords = new ArrayList<>();
		matchingLocations = null;
		document = null;
	}

	/**
	 * Add a new word from the search list, which is identified as part of the
	 * document.
	 * 
	 * @param word
	 *            word to add
	 */
	public void addMatchingWord(String word) {
		matchingWords.add(word);
	}

	/**
	 * Constructs the list of all locations in the document where one of the
	 * matching words begins. For each matching word, all its locations are
	 * included as part of this list. That list is finally sorted in increasing
	 * order.
	 */
	public void buildMatchingLocations() {
		matchingLocations = new ArrayList<>();
		for (String word : matchingWords)
			for (Integer location : docIDX.getWordLocations(word))
				matchingLocations.add((long) location);

		matchingLocations.sort(null);
	}

	/**
	 * Get a copy of that locations in document that contain one of the words in
	 * the list of words to search
	 * 
	 * @return the list of locations
	 */
	public ArrayList<Long> getMathingWordsLocations() {
		if (matchingWords == null)
			buildMatchingLocations();
		ArrayList<Long> result = new ArrayList<>();
		for (Long location : matchingLocations)
			result.add(location);

		return result;
	}

	/**
	 * Getter for matchingWords list.
	 * 
	 * @return the matchingWords list
	 */
	public ArrayList<String> getMatchingWords() {
		return matchingWords;
	}

	/**
	 * Getter for corresponding Document instance of this MatchingSearchDocument
	 * object.
	 * 
	 * @return the document object.
	 */
	public Document getDocument() {
		return document;
	}

	/**
	 * Getter for the corresponding DocIDX instance of this MatchingSearchDocument
	 * object.
	 * 
	 * @return the docIDX object
	 */
	public DocumentIDX getDocIDX() {
		return docIDX;
	}

	/**
	 * Displays a specific number of lines from the specified document. If the
	 * number of lines is given as 0, then the whole document is displayed.
	 * 
	 * @param nLines
	 *            number of initial lines from the document to be displayed
	 * @throws IOException
	 *             may throw this exception if an Input/Output error occurs
	 *             while accessing the index directory.
	 */
	public void displayDocument(int nLines) throws IOException {
		if (document == null)
			document = new Document(docIDX.getDocID());
		document.displayDocumentContent(matchingLocations, nLines);
	}

}
