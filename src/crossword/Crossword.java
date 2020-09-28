package crossword;

import java.util.ArrayList;

public class Crossword {
    private Board board;


    public Crossword(String[] crosswordOne) {
        this.board = Board.importCrosswordRows(crosswordOne);
    }

    public void findWord(String word) throws Exception {
        this.findWord(word, true);
    }

    public void findWord(String word, boolean crossOffWhenFound) throws Exception {
        System.out.println("--> Looking for word " + word);
        char[] charWord = word.toCharArray();

        // [0] Get letters which have the first character of the word we search for.
        START_LETTERS: for (Letter wordLetter : board.getLettersWithChar(charWord[0], false)) {
            // [1] Look in each direction of the first letter
            for (Board.WordDirection direction : Board.WordDirection.values()){
                // [2] Follow the letters in one direction
                ArrayList<Letter> followResult = followDirection(wordLetter, direction, charWord, 1);
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

    public ArrayList<Letter> followDirection(Letter followLetter, Board.WordDirection wordDirection, char[] charWord, int charWordIndex){
        Letter foundLetter = this.board.getLetterAtPos(followLetter.getRow() + wordDirection.getMoveRow(), followLetter.getColumn() + wordDirection.getMoveCol());
        if (foundLetter == null) {
            return null;
        } else {
            if (foundLetter.getChar() == charWord[charWordIndex]){
                if (charWordIndex == (charWord.length -1)){
                    ArrayList<Letter> wordLetters = new ArrayList<>();
                    wordLetters.add(foundLetter);
                    return wordLetters;
                } else {
                    ArrayList<Letter> wordLetters = followDirection(foundLetter, wordDirection, charWord, charWordIndex+1);
                    if (wordLetters == null){
                        return null;
                    } else{
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
