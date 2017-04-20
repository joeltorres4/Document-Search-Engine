package menuClasses;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import dataManagement.DocumentIDX;
import dataManagement.MatchingSearchDocument;
import generalClasses.EntryComparator;
import ioManagementClasses.IOComponent;
import systemClasses.SystemController;

/**
 * Class to execute the word search process actions and display ranked
 * documents.
 * 
 * @author Joel Torres
 *
 */
public class PerformSearchesAction implements Action {
	private static IOComponent io = IOComponent.getComponent();

	/**
	 * Executes the system search based on words. Receives input parameters and
	 * calls other functions to rank and display documents that match this
	 * search.
	 */
	public void execute(Object arg) {
		SystemController sc = (SystemController) arg;
		String answer = "y";
		while (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes")) {
			io.output("\nSearching Based on Words:\n");
			String words = io.getInput("\nEnter words to search for (separate by spaces): ");
			Map<Integer, MatchingSearchDocument> matchingDocuments = null;
			try {
				StringTokenizer wordsTokens = new StringTokenizer(words);
				ArrayList<String> wordsList = constructListOfSearchWords(wordsTokens);
				matchingDocuments = sc.search(wordsList);
				if (matchingDocuments.isEmpty())
					io.output("No document matches this search.");
				else
					processMatchingDocuments(matchingDocuments, wordsList.size());
			} catch (IOException e) {
				e.printStackTrace();
			}
			answer = io.getInput("\n\n*** Do you want to perform another search: (y/n)? ");
		}
	}

	/**
	 * Processes matching documents from the search by ranking them and then
	 * displaying the desired documents.
	 * 
	 * @param matchingDocuments
	 *            map containing entries with the docID and the corresponding
	 *            document that matches the search
	 * @throws IOException
	 *             may throw this exception if an Input/Output error occurs
	 *             while accessing the corresponding directory.
	 */
	private void processMatchingDocuments(Map<Integer, MatchingSearchDocument> matchingDocuments, int n)
			throws IOException {
		ArrayList<MatchingSearchDocument> rankedDocuments = rankMatchingDocuments(matchingDocuments, n);
		displayHeaderLinesMatchingDocuments(rankedDocuments);
		String answer = "y";
		while (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes")) {
			int docIndex = io.getInputInteger("\n\n---Please, enter the number of document to see: ");
			if (docIndex < 1 || docIndex > rankedDocuments.size())
				io.output("Invalid index number: " + docIndex);
			else {
				io.output("\n+++++Content of document ranked: " + docIndex + " +++++ \n\n");
				rankedDocuments.get(rankedDocuments.size() - docIndex).displayDocument(0);
			}
			answer = io.getInput("\n\n*** Do you want to display another document: (y/n)? ");
		}
	}

	/**
	 * Displays headers of documents that match the search in the established
	 * relevance order.
	 * 
	 * @param rankedDocuments
	 *            the documents matching the search already ranked by relevance.
	 * @throws IOException
	 *             may throw this exception if an Input/Output error occurs
	 *             while accessing the corresponding directory.
	 */
	private void displayHeaderLinesMatchingDocuments(ArrayList<MatchingSearchDocument> rankedDocuments)
			throws IOException {

		int n = 1;
		for (int i = rankedDocuments.size() - 1; i >= 0; i--) {
			io.output("\n\n****DOCUMENT " + n++ + "****\n");
			rankedDocuments.get(i).displayDocument(3);
		}
	}

	/**
	 * Ranks the document matching the search with the established order.
	 * 
	 * @param matchingDocuments
	 *            map containing entries of docID and the matching document.
	 * @return a list with the ranked documents (in this case that list is in
	 *         ascending order of ranks)
	 * @throws IOException
	 *             may throw this exception if an Input/Output error occurs
	 *             while accessing the corresponding directory.
	 */
	private ArrayList<MatchingSearchDocument> rankMatchingDocuments(
			Map<Integer, MatchingSearchDocument> matchingDocuments, int n) throws IOException {

		ArrayList<MatchingSearchDocument> rankedDocuments = new ArrayList<>();
		ArrayList<Entry<Double, MatchingSearchDocument>> ranking = new ArrayList<>();
		for (Entry<Integer, MatchingSearchDocument> e : matchingDocuments.entrySet()) {
			ArrayList<String> kd = e.getValue().getMatchingWords();
			DocumentIDX docIDX = e.getValue().getDocIDX();
			double r = kd.size(); // number of words that match in this doc
			double pd = (double) r / n;
			double rd = 0;
			// compute relative frequency
			for (int i = 0; i < r; i++) {
				String word = kd.get(i);
				ArrayList<Integer> wordLocations = (ArrayList<Integer>) docIDX.getWordLocations(word);
				rd += (double) wordLocations.size() / docIDX.numberOfRegisteredWords();
			}
			double docRank = pd + rd;
			ranking.add(new AbstractMap.SimpleEntry<>(docRank, e.getValue()));
		}
		// sort ranking list depending on its docRank (increasing since when
		// displayed they'll be showed in decreasing)
		// tranfer MatchingSearchDocuments to rankedDocuments and end ranking process
		ranking.sort(new EntryComparator());
		for (Entry<Double, MatchingSearchDocument> msd : ranking)
			rankedDocuments.add(msd.getValue());
		return rankedDocuments;
	}

	/**
	 * Given a tokenizer, builds a list of those words inside it (the search
	 * words).
	 * 
	 * @param wordsTokens
	 *            tokenizer of search parameters
	 * @return list of those words
	 */
	private ArrayList<String> constructListOfSearchWords(StringTokenizer wordsTokens) {
		ArrayList<String> uniqueWordsList = new ArrayList<>();
		while (wordsTokens.hasMoreTokens()) {
			String word = wordsTokens.nextToken();
			if (!uniqueWordsList.contains(word))
				uniqueWordsList.add(word);
		}

		return uniqueWordsList;
	}
}
