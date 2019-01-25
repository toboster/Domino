import javafx.scene.canvas.GraphicsContext;

/**
 * Tony Nguyen
 * CS-375
 * Represents a general player, could be a computer or human, has general
 * methods that every player should have.
 */
public abstract class Player {

    protected Board board;
    protected Boneyard boneyard;
    protected Hand hand;

    /**
     * board / boneyard is assumed to be shared with another player.
     *
     * @param board
     * @param boneyard
     */
    public Player(Board board, Boneyard boneyard) {
        this.board = board;
        this.boneyard = boneyard;
        this.hand = new Hand();
        this.initHand();
    }

    @Override
    public String toString() {
        return "Player{" +
                hand +
                '}';
    }

    /**
     * This might have been unnecessary since it was unused in the human
     * player class.
     */
    abstract void doStrategy();

    /**
     * Initializes hand by drawing seven dominoes.
     */
    public void initHand() {
        while (!hand.isFull()) {
            hand.addDomino(boneyard.drawRand());
        }
    }

    /**
     * Draws a random Domino given a by the boneyard.
     */
    public void draw() {
        hand.addDomino(boneyard.drawRand());
    }

    /**
     * @return whether a player can play based on the state of the Hand.
     */
    public boolean canPlay() {
        boolean result = false;

        for (Domino d : hand.getHand()) {
            result |= board.isLeft(d);
            result |= board.isRight(d);
        }
        return result;
    }

    public boolean handEmpty() {
        return hand.isEmpty();
    }

    public void remove(Domino d) {
        hand.remove(d);
    }

    public void remove(Value v1, Value v2) {
        remove(new Domino(v1, v2));
    }

    public void removeAll() {
        hand.removeAll();
    }

    public boolean isDomino(Domino d) {
        return hand.isDomino(d);
    }

    public boolean isDomino(Value v1, Value v2) {
        return isDomino(new Domino(v1, v2));
    }

    public Hand getHand() {
        return this.hand;
    }

    public void clearHand() {
        hand.handClear();
    }

    public void toggleDomino(int i) {
        hand.toggleDom(i);
    }

    public int handSize() {
        return hand.handSize();
    }

    public Domino getDomino(int i) {
        return hand.getDomino(i);
    }

    /**
     * Draws the hand of the player on the canvas.
     *
     * @param gc graphics context.
     */
    public void render(GraphicsContext gc) {
        hand.render(gc);
    }
}