package yahtzee;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {
        YahtzeeGame game = new YahtzeeGame();
        Scoreform scoreform = new Scoreform();

//        Dice[] dices = {new Dice(2), new Dice(3), new Dice(4), new Dice(5), new Dice(4)};
        Dice[] dices = {new Dice(), new Dice(), new Dice(), new Dice(), new Dice()};
        System.out.println(Arrays.toString(dices));
        scoreform.addScoreToSet(Scoreform.Set.THREES, dices);
        scoreform.addScoreToSet(Scoreform.Set.SMALL_STREET, dices);
        scoreform.addScoreToSet(Scoreform.Set.CHANGE, dices);
        System.out.println(scoreform);
    }
}
