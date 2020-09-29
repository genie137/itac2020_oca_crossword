package yahtzee;

public class Dice {
    private int value;

    public Dice() {
        this.roll();
    }
    public Dice(int value) {
        this.value = value;
    }

    private int roll(){
        int rolled = (int) (Math.round(Math.random() * 5)) +1;
        this.value = rolled;
        System.out.println("Dice rolled: " + rolled);
        return rolled;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Dice{" +
                "value=" + value +
                '}';
    }
}
