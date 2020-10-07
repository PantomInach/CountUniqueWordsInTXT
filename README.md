# CountWordsInTXT
Counts the number of words in given .txt files.

This code is compatible only with UTF-8 .txt files. Other were not tested/supported.\n
It was desinged to work mainly with the german language. It sanitizes special german characters like "Ä" to "Ae" and so on.\n
Also all characters other than the normal alphabet and "'" while split your words and capital letters while be converted to lowercase letters.\n
There is also an option to remove words, which will not be considered in the final statistics, threw the delet() methode.

# How to use
Compile the .java files with javac.
Then create a new folder in the run-directory named "textin" und paste all .txt files in this folder, of which you want the number of words.\n
Finaly run the main.class file with java and you will get the resultes in a new folder "textout".\n
You will get an terrible tabell of the individual words, how often they occure, (words in total)/(unique words), (characters in total)/(unique words), the amount of unique words and words in total. 
