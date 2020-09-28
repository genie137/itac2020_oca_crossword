package crossword;

public class Letter{
    private char letter;
    private int row;
    private int column;
    private boolean crossedOff;

    public Letter(char letter, int row, int column) {
        this.letter = letter;
        this.row = row;
        this.column = column;
        this.crossedOff = false;
    }

    public char getChar() {
        return this.letter;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isCrossedOff() {
        return crossedOff;
    }

    public void setCrossedOff(boolean crossedOff) {
        this.crossedOff = crossedOff;
    }

    public String getBoardPrint() {
        if (this.crossedOff){
            return "[" + '#' + "]";
        } else {
            return "[" + this.letter + "]";
        }
    }

    @Override
    public String toString() {
        return "crossword.Letter{" +
                "letter=" + letter +
                ", row=" + row +
                ", column=" + column +
                ", crossedOff=" + crossedOff +
                '}';
    }
}