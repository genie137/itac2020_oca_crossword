package crossword;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {
    private Letter[][] letterBoard;
    private HashMap<Character, ArrayList<Letter>> previouslyFound;

    private Board(Letter[][] letterBoard){
        this.letterBoard = letterBoard;
        this.previouslyFound = new HashMap<>();
    }

    public static Board importCrosswordRows(String[] crossword){
        /*
        Import a string array an convert the characters to Letter instances and add it to an 2D Letter array.
         */
        Letter[][] ret = new Letter[crossword.length][];
        for (int i = 0; i < crossword.length; i++) {
            char[] rowChars = crossword[i].toCharArray();
            ret[i] = new Letter[rowChars.length];
            for (int j = 0; j < rowChars.length; j++) {
                ret[i][j] = new Letter(rowChars[j], i, j);
            }
        }
        return new Board(ret);
    }

    public Letter getLetterAtPos(int x, int y, boolean mayBeCrossed){
        /*
        Get the letter at the given position.
         */
        if (x < 0 || y < 0){
            return null;
        }
        if (x < this.letterBoard.length){
            Letter[] row = this.letterBoard[x];
            if (y < row.length){
                Letter foundLetter = row[y];
                if (foundLetter.isCrossedOff()) {
                    return mayBeCrossed ? foundLetter : null;
                } else {
                    return foundLetter;
                }
            }
        }
        return null;
    }

    public ArrayList<Letter> getLettersWithChar(char c, boolean crossedOff) {
        ArrayList<Letter> found = new ArrayList<>();
        for (Letter[] row : letterBoard) {
            for (Letter letter : row) {
                if ((letter.getChar() == c) && (!letter.isCrossedOff())){
                    found.add(letter);
                }
            }
        }
        return found;
    }

    public ArrayList<Letter> getAllLetters(boolean includeCrossedOff){
        ArrayList<Letter> returnList = new ArrayList<>();
        for (Letter[] row : letterBoard) {
            for (Letter letter : row) {
                if (includeCrossedOff){
                    returnList.add(letter);
                } else {
                    if (!letter.isCrossedOff()){
                        returnList.add(letter);
                    }
                }
            }
        }
        return returnList;
    }

    @Override
    public String toString() {
        StringBuilder sbBoard = new StringBuilder();
        for (int i = 0; i < letterBoard.length; i++) {
            StringBuilder sbRow = new StringBuilder();
            Letter[] letterRow = letterBoard[i];
            for (int j = 0; j < letterRow.length; j++) {
                Letter letter = letterRow[j];
                sbRow.append(letter.getBoardPrint());
                if (j < (letterRow.length - 1)) {
                    sbBoard.append(" ");
                }
            }
            sbBoard.append(sbRow.toString());
            if (i < (letterBoard.length)) {
                sbBoard.append('\n');
            }
        }
        return sbBoard.toString();
    }

    public enum WordDirection {
        LEFT_RIGHT(1, 0),
        RIGHT_LEFT(-1, 0),
        TOP_BOTTOM(0,1),
        BOTTOM_TOP(0, -1),
        TOP_LEFT_BOTTOM_RIGHT(1, 1),
        TOP_RIGHT_BOTTOM_LEFT(-1, 1),
        BOTTOM_LEFT_TOP_RIGHT(1, -1),
        BOTTOM_RIGHT_TOP_LEFT(-1, -1);

        private final int moveRow;
        private final int moveCol;

        WordDirection(int moveRow, int moveCol) {
            this.moveRow = moveRow;
            this.moveCol = moveCol;
        }

        public int getMoveRow() {
            return moveRow;
        }

        public int getMoveColumn() {
            return moveCol;
        }
    }
}
