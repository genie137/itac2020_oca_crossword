package crossword;

import java.util.ArrayList;

public class Crossword {
    private Board board;


    public Crossword(String[] crosswordOne) {
        //Create a crossword object and initialize the board with the given string-array.
        this.board = Board.importCrosswordRows(crosswordOne);
    }

    public void findWord(String word) {
        //Find a word in the crossword.
        this.findWord(word, true, false);
    }

    public void findWord(String word, boolean crossOffWhenFound, boolean letterMayBeCrossed) {

        //Find a word in the crossword.
        System.out.println("--> Looking for word " + word);
        char[] charWord = word.toCharArray();

        // [1] Get letters which have the first character of the word we search for.
        START_LETTERS: for (Letter wordLetter : board.getLettersWithChar(charWord[0], false)) {
            // [2] Look in each direction of the first letter
            for (Board.WordDirection direction : Board.WordDirection.values()){
                // [3] Follow the letters in one direction
                ArrayList<Letter> followResult = followDirection(wordLetter, direction, charWord, 1, letterMayBeCrossed);
                // [4] If the direction does result in a word, add the first letter to the arraylist.
                //       And print the found letters and crossOff the letters if 'crossOffWhenFound` is True.
                if (followResult != null) {
                    followResult.add(0, wordLetter);
                    System.out.println("--> Found word " + word + " in direction " + direction.toString() + " at the following letters: ");
                    for (int i = 0; i < followResult.size(); i++) {
                        Letter l = followResult.get(i);
                        System.out.println("[" + i + "] " + l.toString());
                        if (crossOffWhenFound) {
                            l.setCrossedOff(true);
                        }
                    }
                    break START_LETTERS;
                }
            }
        }
    }

    public ArrayList<Letter> followDirection(Letter followLetter, Board.WordDirection wordDirection, char[] charWord, int charWordIndex, boolean letterMayBeCrossed){
        //[1] Add the direction x and y to the current followLetter to get the letter at that position.
        Letter foundLetter = this.board.getLetterAtPos(followLetter.getRow() + wordDirection.getMoveRow(), followLetter.getColumn() + wordDirection.getMoveColumn(), letterMayBeCrossed);
        //[2] If no letter has been found, return null to signify that this direction does not result in the word.
        if (foundLetter == null) {
            return null;
        } else {
            //[3] If a letter is found, check if the character of the letter is the one we are searching for.
            if (foundLetter.getChar() == charWord[charWordIndex]){
                //[4] If we are looking for the last character in the word, we found the whole word so we can return the list with Letters.
                if (charWordIndex == (charWord.length -1)){
                    ArrayList<Letter> wordLetters = new ArrayList<>();
                    wordLetters.add(foundLetter);
                    return wordLetters;
                } else {
                    //[5] If it is not the last character, we look for the next character of the word in the specified direction.
                    ArrayList<Letter> wordLetters = followDirection(foundLetter, wordDirection, charWord, charWordIndex+1, letterMayBeCrossed);
                    //[6] If the next character(s) did not result in an ArrayList a word cannot be found in ths direction, so we return null.
                    if (wordLetters == null){
                        return null;
                    } else{
                        //[7] If a word has been found, we add the letter to the front of the ArrayList.
                        wordLetters.add(0, foundLetter);
                    }
                    return wordLetters;
                }
            } else {
                return null;
            }
        }

    }
    public String getSolution() {
        // Get all letters that are not crossed off.
        StringBuilder sb = new StringBuilder();
        for (Letter let : board.getAllLetters(false)) {
            sb.append(let.getChar());
        }
        return sb.toString();
    }

    public Board getBoard() {
        return this.board;
    }
}
