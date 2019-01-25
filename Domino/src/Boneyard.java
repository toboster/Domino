import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Tony Nguyen
 * CS-375
 * Represents the Boneyard in the Domino game, should be used to contain
 * Dominoes not yet in play.
 */
public class Boneyard {

    private List<Domino> boneyard = new ArrayList<>();
    private Random rand = new Random(1);

    /**
     * Fills boneyard with all possible dominoes.
     */
    public Boneyard() {
        List<Value> enumList = new ArrayList<>(Arrays.asList(Value.values()));

        for (Value i : Value.values()) {
            for (Value j : enumList) {
                boneyard.add(new Domino(i, j));
            }
            enumList.remove(i);
        }
    }

    @Override
    public String toString() {
        return "Boneyard{" +
                "boneyard=" + boneyard +
                '}';
    }

    /**
     * @return A Domino randomly picked from pseudo random generator.
     */
    public Domino drawRand() {
        Domino result = boneyard.get(rand.nextInt(boneyard.size()));

        boneyard.remove(result);
        return result;
    }

    public boolean isEmpty() {
        return boneyard.isEmpty();
    }
}
