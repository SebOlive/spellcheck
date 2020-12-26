package spellcheck;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
//simple program that takes in a word, determines if it's a typo or not, and also finds similar words.
//Dictionary used here is courtesy of dwsyl, who can be found on github 
// v  here   v
//  https://github.com/dwyl

public class driver {
	
	public static void main(String[] args) throws FileNotFoundException {
		//sets up the scanners to read through the dictionary file and to get user input
		File file = new File("words.txt");
		Scanner readDict = new Scanner(file);
		Scanner getUserWord = new Scanner(System.in);
		ArrayList<String> matches = new ArrayList<>();
		boolean mispell = true;
		//gets user input and closes the user input scanner since it isn't used again
		System.out.print("Please enter your mispelled word: ");
		String misspel = getUserWord.next();
		getUserWord.close();
		
		//iterates through the dictionary file
		while(readDict.hasNext()) {
			//stores the current word from the dictionary to be compared against the user's potential typo
			String dictWord = readDict.next();
			//checks to see if the typo is actually a word
			//if there's a perfect match, the 'typo' is determined to be a word and the program
			//will tell inform user and terminate
			if(misspel.equalsIgnoreCase(dictWord)) {
				System.out.print(misspel + " is a word.");
				mispell = false;
				break;
			}
			//used in checking the length of the
			//potential typo and the dictionary word
			double uWord = misspel.length();
			double pWord = dictWord.length();
			
			//compares and eliminates words that are much longer or shorter than the target word
			if(!((pWord * 1.5) <= uWord)) {
				//just skips words that are significantly shorter than the target word
				if (!((uWord * 1.5) <= pWord)) {
					//just skips words that are significantly longer than the target word
					
					//creates an array of the characters in each word
					char[] userWord = misspel.toLowerCase().toCharArray();
					char[] lexWord = dictWord.toLowerCase().toCharArray();
					//to be used in determining how similar the typo and potential match are
					double userCompare = userWord.length;
					double similar = 0;
					//used to determine how many times to iterate through the comparison
					int shortest = Math.min(userWord.length,lexWord.length);
					//iterates through each char Array and determines how many characters are a match
					for(int j = 0; j < shortest; j++) {
						if(userWord[j] == lexWord[j]) {
							similar++;
						}
					}
					//determines how similar each word are, and if they're similar enough -
					//adds it to an array which will be printed off later assuming a perfect match isn't found
					//could also get user input how tolerance levels
					//but i feel like most people won't mind
					if((similar/userCompare) > .6) {
						matches.add(dictWord);
					}
				}
			}
		}
		//unused scanner
		readDict.close();
		//just lists off each potential match
		if(mispell) {
			System.out.println("Potential matches include: ");
			for(int i = 0; i < matches.size(); i++) {
				System.out.println(matches.get(i));
			}
		}
	}
}
