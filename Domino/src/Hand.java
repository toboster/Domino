import javafx.scene.canvas.GraphicsContext;
import java.util.LinkedList;
import java.util.List;

/**
 * Tony Nguyen
 * CS-375
 * Represents the hand of a Player.
 */
public class Hand {

    private List<Domino> hand = new LinkedList<>();

    @Override
    public String toString() {
        return "hand=" + hand;
    }

    public boolean isEmpty() {
        return hand.isEmpty();
    }

    public int handSize() {
        return hand.size();
    }

    public Domino getDomino(int i) {
        return hand.get(i);
    }

    /**
     * A full hand has seven dominos.
     *
     * @return
     */
    public boolean isFull() {
        return hand.size() == 7;
    }

    /**
     * Adds Domino into hand.
     *
     * @param d
     */
    public void addDomino(Domino d) {
        hand.add(d);
    }

    public List<Domino> getHand() {
        return hand;
    }

    public boolean isDomino(Domino d) {
        boolean result = false;
        Domino test = new Domino(d.getVal2(), d.getVal1());

        for (Domino i : hand) {
            result |= d.equals(i);
            result |= test.equals(i);
        }
        return result;
    }

    /**
     * Removes given Domino if it exist in the hand.
     *
     * @param d Domino
     */
    public void remove(Domino d) {
        Domino test = new Domino(d.getVal2(), d.getVal1());

        for (Domino i : hand) {
            if (d.equals(i)) {
                hand.remove(i);
                return;
            } else if (test.equals(i)) {
                hand.remove(i);
                return;
            }
        }
    }

    public void removeAll() {
        hand.clear();
    }

    public List<Domino> getHandList() {
        return hand;
    }

    public void handClear() {
        hand.clear();
    }

    /**
     * Used to draw the hand on the canvas.
     *
     * @param gc graphics context.
     */
    public void render(GraphicsContext gc) {
        int posY = 350;
        int i = 50;
        for (Domino d : hand) {
            d.render(gc, i, posY);
            i += 100;
        }
    }

    /**
     * Inverts a Dominos boolean according to the current index i
     *
     * @param i index of hand.
     */
    public void toggleDom(int i) {
        hand.get(i).toggleSelected();
    }
}
