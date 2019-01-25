
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;
import java.util.List;

/**
 * Tony Nguyen
 * CS-375
 * Instantiates and manages the game logic of game objects that are
 * relevant to the game Dominoes, to play interact with GUI.
 */
public class Game extends Application {

    private Boneyard boneyard = new Boneyard();
    private Board board = new Board();
    private Scanner sc = new Scanner(System.in);
    // all the players.
    private Player p1;
    private Player p2;
    private boolean gameOver = false;
    private Turn turn = Turn.Human;
    private Turn prevTurn = Turn.Computer;
    private Map<String, Value> numMap = new HashMap<>();
    private Map<Value, String> valueMap = new HashMap<>();
    private static final String SPACE_REGEX = "\\s+";
    private List<Player> players = new ArrayList<>();
    // GUI stuff
    private int domHandSelect = 0;
    private Domino selectDomino;
    private dir selectDir = dir.left;
    private String valDisplay1 = "";
    private String valDisplay2 = "";
    private String statusText = "Status: ";

    // enum used to indicate desired direction of play from the user.
    private enum dir {
        left, right;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.p1 = new Human(board, boneyard);
        this.p2 = new Computer(board, boneyard);

        // STARTS GAME
        players.add(p1);
        players.add(p2);
        fillNumMap();
        // GUI SETUP
        BorderPane root = new BorderPane();
        HBox controls = new HBox(8);
        HBox btnStand1 = new HBox(8);
        HBox status = new HBox();
        Text t = new Text(statusText);
        // STATUS TEXT
        t.setFont(Font.font("Verdana", 25));
        status.getChildren().add(t);
        // BUTTONS
        Button butnLeft = new Button("LEFT PLAY");
        Button butnDraw = new Button("DRAW");
        Button butnRight = new Button("RIGHT PLAY");
        Button butnVal1 = new Button("Value1");
        Button butnVal2 = new Button("Value2");
        butnVal1.setDisable(true);
        butnVal2.setDisable(true);
        butnLeft.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                butnLeft.setDisable(true);
                butnRight.setDisable(true);
                butnVal1.setDisable(false);
                butnVal2.setDisable(false);
                butnDraw.setDisable(true);
                selectDir = dir.left;
            }
        });
        butnDraw.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String[] strs = new String[]{"d"};
                applyInput(strs, p1);
            }
        });
        butnRight.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                butnLeft.setDisable(true);
                butnRight.setDisable(true);
                butnDraw.setDisable(true);
                butnVal1.setDisable(false);
                butnVal2.setDisable(false);
                selectDir = dir.right;
            }
        });
        butnVal1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Domino d = p1.getDomino(domHandSelect);

                butnPlay(valueMap.get(d.getVal1()),
                        valueMap.get(d.getVal2()));
                butnLeft.setDisable(false);
                butnRight.setDisable(false);
                butnDraw.setDisable(false);
                butnVal1.setDisable(true);
                butnVal2.setDisable(true);
            }
        });
        butnVal2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Domino d = p1.getDomino(domHandSelect);

                butnPlay(valueMap.get(d.getVal2()),
                        valueMap.get(d.getVal1()));
                butnLeft.setDisable(false);
                butnRight.setDisable(false);
                butnDraw.setDisable(false);
                butnVal1.setDisable(true);
                butnVal2.setDisable(true);
            }
        });
        // WRAPPER, USED TO CONTAIN CANVAS.
        Pane wrapperPane = new Pane();
        wrapperPane.setStyle("-fx-background-color: maroon");
        // CONTROLS
        controls.setStyle("-fx-background-color: black");
        controls.setPrefHeight(150);
        btnStand1.getChildren().addAll(butnVal1, butnVal2);
        controls.getChildren().addAll(butnLeft, butnDraw, butnRight, btnStand1);
        controls.setAlignment(Pos.CENTER);
        // STATUS TEXT ON TOP
        status.setPrefHeight(50);
        status.setStyle("-fx-background-color: gray");
        // ROOT
        root.setCenter(wrapperPane);
        root.setBottom(controls);
        root.setTop(status);
        // CANVAS
        Canvas canvas = new Canvas();
        canvas.setHeight(600);
        canvas.setWidth(900);
        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double valX = event.getX() - 25;
                double valY = event.getY();

                if (valX >= 25 && valX < (p1.handSize() * 100)
                        && valY >= 350 && valY <= 400) {
                    if (selectDomino != null && p1.isDomino(selectDomino)) {
                        selectDomino.toggleSelected();
                    }
                    domHandSelect = (int) (valX / 100);
                    selectDomino = p1.getDomino(domHandSelect);
                    selectDomino.toggleSelected();
                    butnVal1.setText(selectDomino.getVal1().toString());
                    butnVal2.setText(selectDomino.getVal2().toString());
                }

            }
        });
        // CANVAS
        wrapperPane.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        // PRIMARY STAGE
        primaryStage.setScene(new Scene(root, 800, 600, Color.GRAY));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Dominoes");
        primaryStage.show();
        // TIMER
        AnimationTimer a = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!gameOver) {
                    runGame();
                    render(gc);
                } else {
                    statusText = "Status: Game Over! Winner is " + prevTurn;
                }
                t.setText(statusText);
            }
        };
        a.start();
    }

    /**
     * Draws graphics onto canvas, this includes the board, player, and
     * background.
     *
     * @param gc graphics context.
     */
    private void render(GraphicsContext gc) {
        gc.setFill(Color.MAROON);
        gc.fillRect(0, 0, 900, 400);
        p1.render(gc);
        board.render(gc);
    }

    /**
     * Used to apply input given a button event occured creates same format
     * used in version 1 and 2 for the scanner, uses applyInput method.
     *
     * @param match intended match in String form.
     * @param other other value besides the match in String form.
     */
    private void butnPlay(String match, String other) {
        String[] strs =
                new String[]{"p", selectDir.toString(), match, other};

        applyInput(strs, p1);
    }

    /**
     * Used to let the computer play, and checks win.
     */
    public void runGame() {
        if (checkWin()) {
            gameOver = true;
            return;
        }
        if (turn.equals(Turn.Computer)) {
            p2.doStrategy();
            this.turn = Turn.Human;
            this.prevTurn = Turn.Computer;
        }
    }

    /**
     * Used by the butnPlay method to play the game,
     *
     * @param strs Strings
     */
    private void applyInput(String[] strs, Player focusPlayer) {
        if (!validInput(strs)) {
            howInPrint();
            return;
        }
        switch (strs[0]) {
            case "p":
                humanPlay(strs[1], focusPlayer, numMap.get(strs[2]),
                        numMap.get(strs[3]));
                break;
            case "d":
                humanDraw(focusPlayer);
                break;
            default:
                howInPrint();
                break;
        }
    }

    /**
     * Used by the button to perform a draw for human player.
     *
     * @param focusPlayer the player who wishes to draw.
     */
    private void humanDraw(Player focusPlayer) {
        if (!focusPlayer.canPlay()) {
            focusPlayer.draw();
        } else {
            statusText = "You can still play a domino.";
        }
    }

    /**
     * Contains the logic in order to play the game for a
     * human player, used by applyInput.
     *
     * @param str         String
     * @param focusPlayer human player that wishes to play.
     * @param v1          of type Value
     * @param v2
     */
    private void humanPlay(String str, Player focusPlayer, Value v1, Value v2) {
        if (focusPlayer.canPlay()) {
            if (focusPlayer.isDomino(v1, v2)) {
                if (str.equals("left")) {
                    // If a play exists on the left side of board.
                    if (board.isLeft(v1, v2)) {
                        // If true the play was successful.
                        if (board.playLeft(v1, v2)) {
                            focusPlayer.remove(v1, v2);
                            this.turn = Turn.Computer;
                            this.prevTurn = Turn.Human;
                        }
                    } else {
                        errDirPrint(str);
                    }
                } else if (str.equals("right")) {
                    // If a play exists on the left side of board.
                    if (board.isRight(v1, v2)) {
                        // If true the play was successful.
                        if (board.playRight(v1, v2)) {
                            focusPlayer.remove(v1, v2);
                            this.turn = Turn.Computer;
                            this.prevTurn = Turn.Human;
                        }
                    } else {
                        errDirPrint(str);
                    }
                }
            } else {
                statusText = "You don't have Domino(" + v1 + ","
                        + v2 + ") try again.";
            }
        } else {
            statusText = turn.toString() + " cannot play," +
                    "please draw.";
        }
    }

    /**
     * Used by applyInput to ensure that input is valid.
     * (More important in version 1 and version 2.)
     *
     * @param strs Strings
     * @return if input given in String array was valid.
     */
    private boolean validInput(String[] strs) {
        boolean result = false;

        if (strs.length == 4 && strs[0].equals("p")) {
            for (int i = 0; i < 7; i++) {
                result |= (Integer.parseInt(strs[2]) == i);
                result |= (Integer.parseInt(strs[3]) == i);
            }
            result |= (strs[1].equals("right") || strs[1].equals("left"));
        } else if (strs.length == 1 && strs[0].equals("d")) {
            result = true;
        }
        return result;
    }

    /**
     * Checks if any of the players hand is empty, also checks if boneyard
     * is empty.
     *
     * @return whether the game is over.
     */
    private boolean checkWin() {
        boolean result = false;

        for (Player p : players) {
            result |= p.handEmpty();
        }
        return result || boneyard.isEmpty();

    }

    /**
     * Used to map integer numbers to enum type Value, found out that this
     * can be done within the enum itself.
     */
    private void fillNumMap() {
        List<Value> enumList = new ArrayList<>(Arrays.asList(Value.values()));

        for (int i = 0; i < 7; i++) {
            numMap.put(String.valueOf(i), enumList.get(i));
            valueMap.put(enumList.get(i), String.valueOf(i));
        }
    }

    /**
     * Used to inform user how to format input for standard in.
     * (used more in version 1 and 2.)
     */
    private void howInPrint() {
        System.out.println("Consider scanner format,\n" +
                "to Play example: p left 0 6\n" +
                "the above plays on the left and tries to match blank\n" +
                "on left side of board, 0 6 means domino(Blank,Six)\n" +
                "To draw just type 'd'.");
    }

    /**
     * Sets the status text displayed in the GUI to an error message
     * of not being able to play.
     *
     * @param str
     */
    private void errDirPrint(String str) {
        statusText = "Can't play in " + str + " direction, try again.";
    }

    public static void main(String args[]) {
        launch(args);
    }
}
