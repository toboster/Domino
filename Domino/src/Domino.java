import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.Objects;

/**
 * Tony Nguyen
 * CS-375
 * Represents a domino of the game Dominoes.
 */
public class Domino {

    private Value val1;
    private Value val2;
    private boolean selected = false;

    /**
     * Represents a Domino
     *
     * @param Val1 is of type enum Value.
     * @param Val2 is of type enum Value.
     */
    public Domino(Value Val1, Value Val2) {
        this.val1 = Val1;
        this.val2 = Val2;
    }

    public Value getVal1() {
        return val1;
    }

    public Value getVal2() {
        return val2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Domino domino = (Domino) o;
        return getVal1() == domino.getVal1() &&
                getVal2() == domino.getVal2();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getVal1(), getVal2());
    }

    @Override
    public String toString() {
        return "{" +
                "val1=" + val1 +
                ", val2=" + val2 +
                '}';
    }

    /**
     * Inverts current boolean of selected, used to highlight a
     * selected domino when shown in the gui.
     */
    public void toggleSelected() {
        selected = !selected;
    }

    /**
     * Draws a Domino on the canvas.
     *
     * @param gc   graphics context
     * @param posX x position of where domino will be drawn.
     * @param posY y position of where domino will be drawn.
     */
    public void render(GraphicsContext gc, int posX, int posY) {
        if (selected == true) {
            posY -= 50;
        }

        // RECTANGLE
        gc.setFill(Color.ORANGE);
        gc.setStroke(Color.BLACK);
        gc.fillRect(posX, posY, 80, 50);
        gc.strokeRect(posX, posY, 80, 50);

        // WORDS
        gc.setLineWidth(2);
        Font theFont = Font.font("Times New Roman", FontWeight.BOLD, 16);
        gc.setFont(theFont);
        gc.setFill(Color.BLACK);
        gc.fillText(val1.toString() + " |", posX, posY + 30);
        gc.fillText(val2.toString(), posX + 47, posY + 30);

    }

}
