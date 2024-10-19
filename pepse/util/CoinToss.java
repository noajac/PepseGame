package pepse.util;
import java.util.Random;

/**
 * class for randomly creating a boolean
 */
public class CoinToss {
    /**
     * toss an unbalanced coin
     * @param p probability for true
     * @return true or false
     */
    public static boolean toss(float p) {
        Random rand = new Random();
        float randomNumber = rand.nextFloat();
        return randomNumber < p;
    }
}
