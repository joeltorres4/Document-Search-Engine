package generalClasses;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 * Important utils for this system. Provides functions as name, filename,
 * and path validators and data writers.
 * 
 * @author Joel Torres
 *
 */
public class P3Utils {
	public static final int MAXFILENAMELENGTH = 20;
	public static final File IndexDirectoryPath = new File("p340354020data", "index");
	public static final File DocsDirectoryPath = new File("p340354020data", "docs");

	/**
	 * Checks if given name is a valid one complying with the specs.
	 * 
	 * @param name
	 *            name to validate
	 * @return true if valid, false otherwise
	 */
	private static boolean validName(String name) {

		if (name == null || name.length() == 0)
			return false;

		char cc = name.charAt(0);

		if (!Character.isLetter(cc) && cc != '_')
			return false;

		boolean validSoFar = true;

		for (int i = 1; validSoFar && i < name.length(); i++) {
			cc = name.charAt(i);
			validSoFar = Character.isLetter(cc) || cc == '_' || Character.isDigit(cc);
		}
		return validSoFar;
	}

	/**
	 * Checks if given filename is a valid one complying with the specs.
	 * 
	 * @param name
	 *            name to validate
	 * @return true if valid, false otherwise
	 */
	private static boolean validFileName(String name) {
		if (name.length() > MAXFILENAMELENGTH)
			return false;
		return validName(name);
	}

	/**
	 * Validates the name given for a document. If such name is valid, and also
	 * the file exists in the docs directory, then the corresponding File object
	 * is returned. If not, an exception is thrown.
	 * 
	 * @param fName
	 *            the name of the document
	 * @return the File object that corresponds to the document's content.
	 * @throws IllegalArgumentException
	 *             if name is not valid or if file does not exist.
	 */
	public static File validateDocumentFile(String fName) throws IllegalArgumentException {
		if (!validFileName(fName))
			throw new IllegalArgumentException("Invalid file name:" + fName);
		File fPath = new File(DocsDirectoryPath, fName);
		if (!fPath.exists())
			throw new IllegalArgumentException("No such file" + fPath.getAbsolutePath());

		return fPath;
	}

	/**
	 * Checks if given filename has a corresponding file inside the Index
	 * directory.
	 * 
	 * @param fName
	 *            filename to validate
	 * @return File object if valid
	 * @throws IllegalArgumentException
	 *             may throw this exception if the given filename is invalid or
	 *             if file doesn't exists inside directory
	 */
	public static File validateIndexFile(String fName) throws IllegalArgumentException {
		if (!validFileName(fName))
			throw new IllegalArgumentException("Invalid file name:" + fName);
		File fPath = new File(IndexDirectoryPath, fName);
		if (!fPath.exists())
			throw new IllegalArgumentException("No such file indexed" + fPath.getAbsolutePath());

		return fPath;
	}

	/**
	 * Finds and return index of element inside given list.
	 * 
	 * @param list
	 *            list to check
	 * @param e
	 *            element to find
	 * @return index if found, -1 otherwise
	 */
	public static <E> int findIndex(ArrayList<E> list, E e) {
		for (int i = 0; i < list.size(); i++)
			if (list.get(i).equals(e))
				return i;

		return -1;
	}

	/**
	 * Writes given word to given Random Access File as one byte per
	 * character and finalizes by writing a blank space.
	 * 
	 * @param word
	 *            word to write
	 * @param file
	 *            file to write to
	 * @throws IOException
	 *             may throw this exception if an Input/Output error occurs
	 *             while accessing the index directory.
	 */
	public static void writeWordToFile(String word, RandomAccessFile file) throws IOException {
		for (int i = 0; i < word.length(); i++)
			file.writeByte((byte) word.charAt(i));
		file.writeByte((byte) ' ');
	}

	/**
	 * Read next word from current file pointer in file.
	 * 
	 * @param file
	 *            the random access file corresponding to the main index
	 * @return the next word
	 * @throws IOException
	 *             may throw this exception if an Input/Output error occurs
	 *             while accessing the index directory.
	 */
	public static String readWord(RandomAccessFile file) throws IOException {
		String word = "";
		char ch = (char) file.readByte();
		for (int i = 1; ch != ' '; i++) {
			word += ch;
			ch = (char) file.readByte();
		}
		return word;
	}

}
