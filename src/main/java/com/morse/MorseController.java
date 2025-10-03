package com.morse;

import com.morse.structure.MorseTree;
import com.morse.structure.Node;
import com.morse.utils.MorseCode;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class MorseController {

    private static final double NODE_RADIUS = 22;
    private static final double VERTICAL_GAP = 80;
    private static final double INITIAL_HORIZONTAL_GAP = 200;

    @FXML
    private Pane treeViewerPane;

    @FXML
    private Button morsePad;

    @FXML
    private Button mousePadEnterButton;

    @FXML
    private Button mousePadClearButton;

    @FXML
    private TextField morsePadCodeOutput;

    @FXML
    private TextField morsePadTextOutput;

    @FXML
    private TextArea inputText;

    @FXML
    private Button toMorseButton;

    @FXML
    private Button toTextButton;

    @FXML
    private Button clearButton;

    @FXML
    private TextArea resultText;

    @FXML
    private TextArea addMorseInput;

    @FXML
    private TextArea addTextInput;

    @FXML
    private Button addMorseSubmitButton;

    @FXML
    private Button addMorseClearButton;

    @FXML
    private Button addMorseAutoFillButton;

    private long padPressTime;

    private MorseTree<String> morseTree;

    @FXML
    private void initialize() {
        morseTree = new MorseTree<>("");

        treeViewerPane
                .layoutBoundsProperty()
                .addListener((obs, oldVal, newVal) -> {
                    if (newVal.getWidth() > 0 && morseTree.getRoot() != null) {
                        drawTree(
                                treeViewerPane,
                                morseTree.getRoot(),
                                newVal.getWidth() / 2,
                                50,
                                INITIAL_HORIZONTAL_GAP
                        );
                    }
                });
    }

    private void autoFill() {
        for (MorseCode letter : MorseCode.values()) {
            morseTree.put(letter.getCode(), letter.toString());
        }
    }

    @FXML
    protected void handlePadPress(MouseEvent event) {
        padPressTime = System.currentTimeMillis();
    }

    @FXML
    protected void handlePadRelease(MouseEvent event) {
        long pressDuration = System.currentTimeMillis() - padPressTime;

        if (pressDuration < 200) {
            morsePadCodeOutput.appendText(".");
        } else {
            morsePadCodeOutput.appendText("-");
        }

        String morse = morsePadCodeOutput.getText();
        if (morse == null || morse.isBlank() || hasInvalidChars(morse)) {
            morsePadTextOutput.clear();
            return;
        }

        String text = morseTree.decode(morse, redraw);
        if (text == null || text.isBlank()) {
            morsePadTextOutput.clear();
            return;
        }

        morsePadTextOutput.setText(text);
    }

    @FXML
    protected void handleMousePadEnter() {
        String currentText = morsePadCodeOutput.getText();
        if (!currentText.endsWith(" ")) {
            morsePadCodeOutput.appendText(" ");
        }
    }

    @FXML
    protected void handleMousePadClear() {
        morsePadCodeOutput.clear();
        morsePadTextOutput.clear();
    }

    @FXML
    protected void handleToMorse() {
        String text = inputText.getText();
        if (text == null || text.isBlank()) return;

        String morseCode = morseTree.encode(text, redraw);
        if (morseCode == null || morseCode.isBlank() || hasInvalidChars(morseCode)) {
            resultText.clear();
            return;
        }

        resultText.setText(morseCode);
    }

    @FXML
    protected void handleToText() {
        String morse = inputText.getText();
        if (morse == null || morse.isBlank() || hasInvalidChars(morse)) return;

        String text = morseTree.decode(morse, redraw);
        if (text == null || text.isBlank()) return;

        resultText.setText(text);
    }

    @FXML
    protected void handleClear() {
        inputText.clear();
        resultText.clear();
        redraw.run();
    }

    @FXML
    protected void handleAddMorseSubmitButton() {
        String code = addMorseInput.getText();
        String text = addTextInput.getText().toUpperCase();

        if (code == null || code.isBlank() || hasInvalidChars(code)) return;
        if (text == null || text.isBlank()) return;

        morseTree.put(code, text);
        redraw.run();
    }

    @FXML
    protected void handleAddMorseClearButton() {
        morseTree = new MorseTree<>("");
        redraw.run();
        addMorseInput.clear();
        addTextInput.clear();
    }

    @FXML
    protected void handleAddMorseAutoFillButton() {
        autoFill();
        redraw.run();
    }

    public Runnable redraw = () -> {
        treeViewerPane.getChildren().clear();
        drawTree(
                treeViewerPane,
                morseTree.getRoot(),
                treeViewerPane.getWidth() / 2,
                50,
                INITIAL_HORIZONTAL_GAP
        );
    };

    private boolean hasInvalidChars(String morse) {
        for (char c : morse.toCharArray()) {
            if (c == ' ') continue;
            if (c != '.' && c != '-') return true;
        }
        return false;
    }

    private void drawTree(Pane pane, Node<?> node, double x, double y, double hGap) {
        if (node == null) return;

        if (node.left != null) {
            double childX = x - hGap;
            double childY = y + VERTICAL_GAP;
            Line line = new Line(x, y, childX, childY);
            line.setStroke(Color.BLACK);
            pane.getChildren().add(line);
            drawTree(pane, node.left, childX, childY, hGap / 2);
        }

        if (node.right != null) {
            double childX = x + hGap;
            double childY = y + VERTICAL_GAP;
            Line line = new Line(x, y, childX, childY);
            line.setStroke(Color.BLACK);
            pane.getChildren().add(line);
            drawTree(pane, node.right, childX, childY, hGap / 2);
        }

        Circle circle;

        if (node.circle == null) {
            circle = new Circle(x, y, NODE_RADIUS);
            circle.setFill(Color.WHITE);
            circle.setStroke(Color.DARKGRAY);
            circle.setStrokeWidth(2);
            node.circle = circle;
        } else {
            circle = node.circle;
        }

        Text text = new Text(x - 5, y + 5, (node.value != null) ? String.valueOf(node.value) : "");
        text.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        if (!pane.getChildren().contains(circle)) {
            pane.getChildren().add(circle);
        }
        pane.getChildren().add(text);
    }
}