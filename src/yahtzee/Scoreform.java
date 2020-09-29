package yahtzee;

import yahtzee.interfaces.CalculateScoreInterface;

import java.time.Period;
import java.util.HashMap;

public class Scoreform {
    private HashMap<Set, Integer> scoreSets;

    public Scoreform(){
        this.scoreSets = new HashMap<>();
    }

    public int addScoreToSet(Set set, Dice[] dices) throws Exception {
        if(dices.length != 5){
            throw new Exception("Less or More then 5 dices given.");
        }
        if (scoreSets.containsKey(set)){
            throw new Exception("Set already has a score.");
        } else {
            int calculatedScores = set.getScore(dices);
            scoreSets.put(set, calculatedScores);
            return calculatedScores;
        }
    }

    public enum Set{
        ONES(1, null),
        TWOS(2, null),
        THREES(3, null),
        FOURS(5, null),
        FIVES(5, null),
        SIXES(6, null),
        THREE_OF_A_KIND(-1, dices -> {
            int[] diceCount = {0,0,0,0,0,0,0};
            for (Dice dice : dices) diceCount[dice.getValue()]++;
            int highestIndex = -1;
            for (int i = 0; i < diceCount.length; i++) {
                if (diceCount[i] >= 3){
                    highestIndex = i;
                }
            }

            return highestIndex > 0 ? (highestIndex * diceCount[highestIndex]) : 0;
        }),
        FOUR_OF_A_KIND(-1, dices -> {
            int[] diceCount = {0,0,0,0,0,0,0};
            for (Dice dice : dices) diceCount[dice.getValue()]++;
            int highestIndex = -1;
            for (int i = 0; i < diceCount.length; i++) {
                if (diceCount[i] >= 4){
                    highestIndex = i;
                }
            }

            return highestIndex > 0 ? (highestIndex * diceCount[highestIndex]) : 0;
        }),
        FULL_HOUSE(-1, dices -> {
            int[] diceCount = {0,0,0,0,0,0,0};
            for (Dice dice : dices) diceCount[dice.getValue()]++;
            int threeSameIndex = -1, twoSameIndex = -1;
            for (int i = 0; i < diceCount.length; i++) {
                if (diceCount[i] == 3) {
                    threeSameIndex = i;
                } else if (diceCount[i] == 2){
                    twoSameIndex = i;
                }
            }
            return (threeSameIndex > 0 && twoSameIndex > 0) ? 25 : 0;
        }),
        SMALL_STREET(-1, dices -> {
            int[] diceCount = {0,0,0,0,0,0,0};
            for (Dice dice : dices) diceCount[dice.getValue()]++;

            int streakCount = -1;
            for (int i = 0; i < diceCount.length; i++) {
                if (diceCount[i] == 0){
                    if (streakCount >= 4){
                        break;
                    }
                    streakCount = -1;
                }
                else if (diceCount[i] > 0){
                    if (streakCount == -1){
                        streakCount = 1;
                    } else {
                        streakCount++;
                    }
                }
            }
            return (streakCount >= 4) ? 30 : 0;
        }),
        LARGE_STREET(-1, dices -> {
            int[] diceCount = {0,0,0,0,0,0,0};
            for (Dice dice : dices) diceCount[dice.getValue()]++;

            int streakCount = -1;
            for (int i = 0; i < diceCount.length; i++) {
                if (diceCount[i] == 0){
                    if (streakCount >= 5){
                        break;
                    }
                    streakCount = -1;
                }
                else if (diceCount[i] > 0){
                    if (streakCount == -1){
                        streakCount = 1;
                    } else {
                        streakCount++;
                    }
                }
            }
            return (streakCount >= 5) ? 40 : 0;
        } ),
        YAHTZEE(-1, dices -> {
            int[] diceCount = {0,0,0,0,0,0,0};
            for (Dice dice : dices) diceCount[dice.getValue()]++;
            int highestIndex = -1;
            for (int i = 0; i < diceCount.length; i++) {
                if (diceCount[i] >= 5){
                    highestIndex = i;
                }
            }

            return highestIndex > 0 ? (highestIndex * diceCount[highestIndex]) : 0;
        }),
        CHANGE(-1, null);


        private final int checkForDiceValue;
        private final CalculateScoreInterface scoreInterface;

        Set(int checkForDiceValue, CalculateScoreInterface scoreInterface){
            this.checkForDiceValue = checkForDiceValue;
            this.scoreInterface = scoreInterface;
        }

        public int getScore(Dice[] dices){
            if (scoreInterface == null){
                int sum = 0;
                for (Dice dice : dices) {
                    if (this.checkForDiceValue == -1){
                        sum += dice.getValue();
                    } else if (dice.getValue() == this.checkForDiceValue) {
                        sum += dice.getValue();
                    }
                }
                return sum;
            } else {
                return this.scoreInterface.calculateScore(dices);
            }
        }
    }

    @Override
    public String toString() {
        return "Scoreform{" +
                "scoreSets=" + scoreSets +
                '}';
    }
}
