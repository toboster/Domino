import javafx.scene.canvas.GraphicsContext;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Tony Nguyen
 * CS-375
 * Represents the Board, performs plays given a domino either from a
 * computer or human player.
 */
public class Board {

    private Deque<Domino> board = new LinkedList<>();
    private Value leftOption;
    private Value rightOption;

    @Override
    public String toString() {
        return "{" +
                "board=" + board +
                "} \n{leftOption=" + leftOption +
                ", rightOption=" + rightOption +
                '}';
    }

    /**
     * Board needs to be checked using isLeft / isRight methods before play,
     * or unexpected things may happen.
     *
     * @param play  intended play that wants to be match with available play.
     * @param other other value besides intended play, assumed to be from a Domino.
     */
    public boolean playLeft(Value play, Value other) {
        boolean result = true;

        if (board.peek() == null) {
            initBoard(play, other);
        } else if (isPlayValid(play, leftOption)) {
            board.addFirst(new Domino(other, play));
            leftOption = other;

        } else {
            result = false;
            playError(play, other);
            //System.out.println("leftOption[" + leftOption + "]");
        }
        return result;
    }

    /**
     * Matches the intended play to the right side of board.
     *
     * @param play  intended match.
     * @param other other value besides play.
     * @return
     */
    public boolean playRight(Value play, Value other) {
        boolean result = true;
        if (board.peek() == null) {
            initBoard(play, other);
        } else if (isPlayValid(play, rightOption)) {
            board.addLast(new Domino(play, other));
            rightOption = other;

        } else {
            result = false;
            playError(play, other);
            //System.out.println("rightOption[" + rightOption + "]");
        }
        return result;
    }

    /**
     * Assumes that d is a valid match to leftOption.
     *
     * @param d Domino
     */
    public void compPlayLeft(Domino d) {
        Value v1 = d.getVal1();
        Value v2 = d.getVal2();
        if (isPlayValid(v1, leftOption)) {
            playLeft(v1, v2);
        } else if (v2.equals(leftOption) || v2.equals(Value.Blank)) {
            playLeft(v2, v1);
        } else {
            System.out.println("Error in comPlayLeft()");
        }

    }

    /**
     * Only used by the computer, method uses playRight method above.
     *
     * @param d Domino
     */
    public void compPlayRight(Domino d) {
        Value v1 = d.getVal1();
        Value v2 = d.getVal2();
        if (isPlayValid(v1, rightOption)) {
            playRight(v1, v2);
        } else if (v2.equals(rightOption) || v2.equals(Value.Blank)) {
            playRight(v2, v1);
        } else {
            System.out.println("Error in comPlayRight()");
        }

    }

    /**
     * @param play  intended match
     * @param other other value of Domino that is not play.
     */
    private void playError(Value play, Value other) {
        System.out.println("Cannot match domino(" + play + "," + other + ")");
    }

    /**
     * Was used in version 1 and 2.
     *
     * @param d Domino
     */
    private void initBoard(Domino d) {
        board.add(d);
        leftOption = d.getVal1();
        rightOption = d.getVal2();
    }

    /**
     * Calls overloaded method above to initialize board, assumes board is
     * empty.
     *
     * @param v1 a Value
     * @param v2 a Value
     */
    private void initBoard(Value v1, Value v2) {
        board.add(new Domino(v1, v2));
        leftOption = v1;
        rightOption = v2;
    }

    /**
     * @param play   the intended match to be played
     * @param option the other value of the same Domino.
     * @return if there exists a possible play given intended play.
     */
    private boolean isPlayValid(Value play, Value option) {
        return play.equals(option) || play.equals(Value.Blank)
                || option.equals(Value.Blank);
    }

    /**
     * Used to check if a play is possible given the domino and state of
     * leftOption.
     *
     * @param d domino
     */
    public boolean isLeft(Domino d) {
        return isMatch(d, leftOption);
    }

    public boolean isLeft(Value v1, Value v2) {
        return isLeft(new Domino(v1, v2));
    }

    /**
     * Used to check if a play is possible given the domino and state of
     * rightOption.
     *
     * @param d domino
     */
    public boolean isRight(Domino d) {
        return isMatch(d, rightOption);
    }

    public boolean isRight(Value v1, Value v2) {
        return isRight(new Domino(v1, v2));
    }

    /**
     * @param d Domino
     * @param option expects left or right option.
     * @return if there exists a play within d, given left or right option.
     */
    private boolean isMatch(Domino d, Value option) {
        Value d1 = d.getVal1();
        Value d2 = d.getVal2();

        return (d1.equals(option) || d2.equals(option) || leftOption == null
                || d1.equals(Value.Blank) || d2.equals(Value.Blank)
                || option.equals(Value.Blank));
    }

    /**
     * Draws the graphics on canvas
     *
     * @param gc graphics context
     */
    public void render(GraphicsContext gc) {
        int i = 0;
        int posX = 50;
        int posY = 60;

        if (board.size() % 4 == 0) {
            i++;
        }

        for (Domino d : board) {

            if (i == 13) {
                posY += 100;
                posX = 50;
            }

            if (i % 2 == 0) {
                d.render(gc, posX, posY);
                posX += 50;
                i++;
            } else {
                d.render(gc, posX, posY + 50);
                posX += 50;
                i++;
            }
        }
    }
}
