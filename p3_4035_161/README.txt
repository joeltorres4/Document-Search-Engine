Joel Torres Rodríguez
joel.torres4@upr.edu
802-12-8025
ICOM4035-070
Data Structures
P3

This program implements a system capable of indexing the content of text data files (documents) and also perform searches based on words. The search part will be able to find documents that contain at least one of the words in a list of words given, and show them in decreasing order of relevance. The user can specify to display the whole content of particular documents that satisfy the search criteria. When executed, the program shows a menu displaying several choices. Selection one allows the user to add (index) a document inside the docs directory of the program. Selection 2 allows the user to remove a previously added document (indexed document). Selection 3 shows information related to the current indexed documents (out of date, up to date, not added). Selection 4 conducts the search process, the user just inputs the words to look for separated by spaces and the system shows the relevant documents. Finally, to exit the program select option 5.

To compile and run the program, you must open the Command Prompt (CMD) on Windows, Terminal on Linux, or Bash Shell (this program was tested with Cygwin bash shell) and move through the folders and reach the location where you downloaded and extracted the program archives using the CD and DIR (on Windows) or CD and LS (on Linux and Cygwin) commands. If using Cygwin, use command cd .. to move to the previous directory. Other terminals and shells may have their own different commands. When located, enter the following commands (without the > character):

> cd P3_802128025

> javac -d bin -sourcepath src src/systemClasses/Main.java

> java -classpath bin systemClasses.Main


******************************************************************************************************************************************************************************************************************************************
~ The program reads input data from files located in the docs folder. If the file is not located there, the program cannot index it.

~ Included in the program are several dummy files (inside the docs folder). 

~ To make full use of the search process, a Bash Shell must be used, since maching words are set to glow green and blink for better readability. If run on Windows CMD, this function doesn't work. 