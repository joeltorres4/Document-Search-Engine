package dataManagement;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import generalClasses.P3Utils;

/**
 * Represents DocsIDManager objects which stores the documents added to the
 * system. It has instance an variable dedicated to store the names of documents
 * added.
 * 
 * @author Joel Torres
 *
 */
public class DocsIDManager {
	public static final int NAMELENGTH = 20;
	private static final int RECSIZE = NAMELENGTH + 4; // 20+4
	private static DocsIDManager instance = null;

	private ArrayList<String> docNamesList;
	private File fPath;
	private RandomAccessFile file;
	private boolean modified; // to remember if modifications have been made...
	// needed to writeback to file is needed....

	/**
	 * Returns an instance of a DocsIDManager object
	 * 
	 * @return instance of DocsIDManager object if not null; if null,
	 *         initializes and returns it.
	 * @throws IOException
	 *             may throw this exception which is really thrown at the
	 *             DocsIDManager constructor
	 */
	public static DocsIDManager getInstance() throws IOException {
		if (instance == null)
			instance = new DocsIDManager();
		return instance;
	}

	/**
	 * DocsIDManager default constructor. Initializes this type of object by
	 * reseting the modified status boolean variable and initializing the
	 * docNamesList.
	 * 
	 * @throws IOException
	 *             may throw this exception if an Input/Output error occurs
	 *             while accessing the index directory.
	 */
	private DocsIDManager() throws IOException {
		modified = false;
		String fName = "docs_ID.pp3";
		fPath = new File(P3Utils.IndexDirectoryPath, fName);
		if (fPath.exists()) {
			file = new RandomAccessFile(fPath, "r");

			int listSize = (int) (file.length() / RECSIZE);
			docNamesList = new ArrayList<String>(listSize);
			// fill list with empty strings...
			for (int i = 0; i < listSize; i++)
				docNamesList.add("");
			readListContentFromFile();
			file.close();
		} else
			docNamesList = new ArrayList<String>();
	}

	/**
	 * Reads Random Access File docsID.pp3 and adds its info (name and id)
	 * to docNamesList.
	 * 
	 * @throws IOException
	 *             may throw this exception if an Input/Output error occurs
	 *             while accessing the index directory.
	 */
	private void readListContentFromFile() throws IOException {
		long fileLength = file.length();
		boolean completed = false;
		while (!completed) { // this can also be based on docNamesList.size()..,
			try {
				String name = readName();
				int docID = file.readInt();
				docNamesList.set(docID - 1, name); // docID can't be zero
			} catch (IOException e) {
				if (file.getFilePointer() == fileLength)
					completed = true;
				else
					e.printStackTrace();
			}
		}
	}

	/**
	 * Adds the name of a new document to the docs list. .
	 * 
	 * @param name
	 *            the name of the new document
	 * @return -1 if the document exists already in the system; decided by
	 *         comparing names. Returns i>-1 if the assignment was successful.
	 *         In that case, the value returned is the id number for the new
	 *         document,
	 */
	public int addDocument(String name) {
		int newID = -1;
		for (int i = 0; i < docNamesList.size(); i++) {
			if (newID == -1 && docNamesList.get(i).equals(""))
				newID = i + 1; // zero can't be a doc id
			else if (docNamesList.get(i).equals(name))
				return -1; // the document exists
		}
		if (newID == -1) {
			docNamesList.add(name);
			newID = docNamesList.size(); // zero can't be a doc id
		} else
			docNamesList.set(newID - 1, name);

		modified = true;

		return newID;

	}

	/**
	 * Removes the docID given from the docNamesList and sets a blank string
	 * where the name was for future addition of new documents.
	 * 
	 * @param docID
	 *            the given docID to remove from file
	 * @throws IllegalArgumentException
	 *             may throw this exception if the given id is invalid (not in
	 *             range).
	 */
	public void removeDocID(int docID) throws IllegalArgumentException {
		try {
			docNamesList.set(docID - 1, "");
		} catch (IndexOutOfBoundsException e) {
			throw new IllegalArgumentException("Invalid docID: " + docID);
		}
		modified = true;
	}

	/**
	 * Prepares system for closing by saving all the info regarding docsID
	 * needed for future execution.
	 */
	public void close() {
		// iterate over entries in map and write each one to file
		if (modified) {
			try {
				file = new RandomAccessFile(fPath, "rw");
				file.seek(0);
				for (int i = 0; i < docNamesList.size(); i++) {
					writeNameToFile(docNamesList.get(i));
					file.writeInt(i + 1);
				}
				file.setLength(file.getFilePointer()); // truncate the length of
														// the file
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Given a docID, returns the name of the corresponding document.
	 * 
	 * @param docID
	 *            id to return name of corresponding document
	 * @return name of document
	 * @throws IllegalArgumentException
	 *             may throw this exception if the given id is invalid (not in
	 *             range).
	 */
	public String getDocName(int docID) throws IllegalArgumentException {
		try {
			return docNamesList.get(docID - 1);
		} catch (IndexOutOfBoundsException e) {
			throw new IllegalArgumentException("No indexed document has id = " + docID);
		}
	}

	/**
	 * Writes the name of the file to the Random Access File one byte per
	 * character and fills remaining spaces with blank characters.
	 * 
	 * @param name
	 *            name of file to write
	 * @throws IOException
	 *             may throw this exception if an Input/Output error occurs
	 *             while accessing the corresponding directory.
	 */
	private void writeNameToFile(String name) throws IOException {
		for (int i = 0; i < name.length(); i++)
			file.writeByte((byte) name.charAt(i));
		for (int i = name.length(); i < NAMELENGTH; i++)
			file.writeByte((byte) ' '); // fill with blanks remaining bytes...
	}

	/**
	 * Read next name from current file pointer in file.
	 * 
	 * @param file
	 *            the random access file corresponding to the main index
	 * @return the next word
	 * @throws IOException
	 *             may throw this exception if an Input/Output error occurs
	 *             while accessing the index directory.
	 */
	private String readName() throws IOException {
		String name = "";
		char ch;
		int i = 0;
		while (i < NAMELENGTH) {
			ch = (char) file.readByte();
			i++;
			if (ch != ' ')
				name += ch;
			else
				while (i < NAMELENGTH) { // just position FP to next int
					file.readByte();
					i++;
				}
		}
		return name; // returns "" if the name is just spaces
	}

	/**
	 * Returns the docNamesList where all the documents names are.
	 * 
	 * @return docNamesList list of documents names
	 */
	public ArrayList<String> getDocNamesList() {
		return docNamesList;
	}

}
