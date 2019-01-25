import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Tony Nguyen
 * CS-375
 * Used as the computer player within the game, deals with the majority of the
 * plays the computer must take on, see the doStrategy class.
 */
public class Computer extends Player {

    private boolean turnFinnish = false;
    private Random rand = new Random(1);
    /* Contains the possible plays that computer can make on left side. */
    private List<Domino> leftPlays = new ArrayList<>();
    /* Contains the possible plays that computer can make on right side. */
    private List<Domino> rightPlays = new ArrayList<>();

    /**
     * Board / Boneyard is assumed to be shared between another player.
     *
     * @param board
     * @param boneyard
     */
    public Computer(Board board, Boneyard boneyard) {
        super(board, boneyard);
    }

    /**
     * Performs an action given the position of the computers hand.
     */
    public void doStrategy() {
        while (!turnFinnish) {
            if (boneyard.isEmpty() || hand.isEmpty()) {
                break;
            }
            if (canPlay()) {
                findPlay();
                compPlay();
                turnFinnish = true;
            } else {
                draw();
            }
        }
        turnFinnish = false;
    }

    /**
     * Finds the possible plays and stores them into two lists based on a
     * left or right play.
     */
    private void findPlay() {
        for (Domino d : hand.getHandList()) {
            if (board.isLeft(d)) {
                leftPlays.add(d);
            }
            if (board.isRight(d)) {
                rightPlays.add(d);
            }
        }
    }

    /**
     * Performs a 'play' for the computer according to possible solutions
     * given in leftPlays and rightPlays list.
     */
    private void compPlay() {
        if (!leftPlays.isEmpty() && !rightPlays.isEmpty()) {
            playRand();
        } else if (!leftPlays.isEmpty()) {
            playRandLeft();
        } else if (!rightPlays.isEmpty()) {
            playRandRight();
        }
    }

    /**
     * Used by compPlay() where it will randomly pick a play, assuming
     * that there exists a play in both the right and left side.
     */
    private void playRand() {
        int random = rand.nextInt(2);

        if (random == 0) {
            playRandLeft();
        } else {
            playRandRight();
        }
    }

    /**
     * Used by playHand() in order to actually perform the play on the left.
     */
    private void playRandLeft() {
        int leftsize = leftPlays.size();
        int random = rand.nextInt(leftsize);
        Domino d = leftPlays.get(random);
        System.out.println("Comp left Domino: " + d);
        board.compPlayLeft(d);
        hand.remove(d);
        leftPlays.clear();
    }

    /**
     * Used by playHand() in order to actually perform the play on the right.
     */
    private void playRandRight() {
        int rightsize = rightPlays.size();
        int random = rand.nextInt(rightsize);
        Domino d = rightPlays.get(random);
        System.out.println("Comp right Domino: " + d);
        board.compPlayRight(d);
        hand.remove(d);
        rightPlays.clear();
    }
}
