package dice;

public class Dice {
    private int totalRolled;
    private int min;
    private int max;

    public Dice() {
        this.totalRolled = 0;
    }

    public int roll(){
        int rolled = (int) (Math.round(Math.random() * 6));
        this.totalRolled += rolled;
        System.out.println("Dice rolled: " + rolled + ", Total: " + this.totalRolled);
        return rolled;
    }
}
