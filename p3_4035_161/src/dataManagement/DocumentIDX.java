package dataManagement;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import generalClasses.P3Utils;
import systemClasses.SystemController;

/**
 * Map that contains the data in memory of the IDX file corresponding to a
 * particular document in the index. An object of this type is created for any
 * document that matches a particular search.
 * 
 * @author Joel Torres
 *
 */
public class DocumentIDX {
	private Map<String, ArrayList<Integer>> wordLocationsMap = new Hashtable<>();

	// for the moment, only needed for testing purposes...
	private int docID;

	private int docNumberOfWords; // total number of words registered
									// the particular document

	/**
	 * Initializes this instance with current content of the particular idx file
	 * that corresponds to the identified document.
	 * 
	 * @param docID
	 *            id of the document
	 * @throws IOException
	 *             if file format does not match expected format
	 * @throws IllegalArgumentException
	 *             if docID does not match any existing idx file in the system
	 */
	public DocumentIDX(int docID) throws IOException, IllegalArgumentException {
		this.docID = docID;
		String fName = SystemController.makeIDXName(docID);
		File idxFilePath = new File(P3Utils.IndexDirectoryPath, fName);
		if (idxFilePath.exists()) {
			RandomAccessFile idxFile = new RandomAccessFile(idxFilePath, "r");
			loadMapContentFromIDXFile(idxFile);
			idxFile.close();
		} else
			throw new IllegalArgumentException("No document exist for id = " + docID);

	}

	/**
	 * Loads content from given index file to map containing the word and
	 * the locations of it in the file.
	 * 
	 * @param idxFile
	 *            index file to load info from
	 * @throws IOException
	 *             may throw this exception if an Input/Output error occurs
	 *             while accessing the index directory.
	 */
	private void loadMapContentFromIDXFile(RandomAccessFile idxFile) throws IOException {
		long fileLength = idxFile.length();
		boolean completed = false;
		docNumberOfWords = 0;
		while (!completed) {
			try {
				String word = P3Utils.readWord(idxFile);
				ArrayList<Integer> wordLocationsList = new ArrayList<>();
				int docID = idxFile.readInt();
				while (docID != -1) {
					wordLocationsList.add(docID);
					docID = idxFile.readInt();
				}
				wordLocationsMap.put(word, wordLocationsList);
				docNumberOfWords += wordLocationsList.size();
			} catch (IOException e) {
				if (idxFile.getFilePointer() == fileLength)
					completed = true;
				else
					e.printStackTrace();
			}
		}
	}

	/**
	 * Getter for number of word registered in system.
	 * 
	 * @return number of words
	 */
	public int numberOfRegisteredWords() {
		return docNumberOfWords;
	}

	/**
	 * Getter for document it regarding this DocIDX object.
	 * 
	 * @return id of index doc
	 */
	public int getDocID() {
		return docID;
	}

	/**
	 * Returns an iterable collection of all the locations in the index file
	 * of a given word.
	 * 
	 * @param word
	 *            word to return locations
	 * @return locations of word in file
	 */
	public Iterable<Integer> getWordLocations(String word) {
		ArrayList<Integer> locationsList = new ArrayList<>();
		ArrayList<Integer> tempList = wordLocationsMap.get(word);

		if (tempList != null)
			for (Integer location : tempList) 
				locationsList.add(location);
			

		return locationsList;
	}

}
