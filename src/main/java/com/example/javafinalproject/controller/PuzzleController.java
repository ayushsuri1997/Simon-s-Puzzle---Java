package com.example.javafinalproject.controller;

import com.example.javafinalproject.model.Puzzle;
import com.example.javafinalproject.model.UserDetails;
import com.example.javafinalproject.utils.AlertBoxHandler;
import com.example.javafinalproject.utils.AudioHandler;
import com.example.javafinalproject.utils.FileHandler;
import com.example.javafinalproject.utils.ImageViewHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.*;
import java.util.*;
import java.util.List;

/** FXML Components, UI Updates and placement computation is all done in this class. This is the class responsible for interacting with the FX Components.
 * @author Ayush
 * @version 1.0
 */

public class PuzzleController implements Serializable {
    //FXML Components Declaration
    @FXML
    private Label nameLabel;

    @FXML
    private TextField aPiece;

    @FXML
    private TextField bPiece;

    @FXML
    private TextField cPiece;

    @FXML
    private TextField dPiece;

    @FXML
    private TextField ePiece;

    @FXML
    private Label oneOne;

    @FXML
    private Label oneThree;

    @FXML
    private Label oneTwo;

    @FXML
    private Label oneZero;

    @FXML
    private Label threeOne;

    @FXML
    private Label threeThree;

    @FXML
    private Label threeTwo;

    @FXML
    private Label threeZero;

    @FXML
    private Label twoOne;

    @FXML
    private Label twoThree;

    @FXML
    private Label twoTwo;

    @FXML
    private Label twoZero;

    @FXML
    private Label zeroOne;

    @FXML
    private Label zeroThree;

    @FXML
    private Label zeroTwo;

    @FXML
    private Label zeroZero;

    @FXML
    private Label aPieceLabel;

    @FXML
    private Label bPieceLabel;

    @FXML
    private Label cPieceLabel;

    @FXML
    private Label dPieceLabel;

    @FXML
    private Label ePieceLabel;

    @FXML
    ArrayList<Label> labels;

    @FXML
    ArrayList<TextField> textFields;

    @FXML
    private Label currentScore;

    @FXML
    private ListView<String> topPlayersListView;

    @FXML
    private Button reIntLabel;

    @FXML
    private ListView<String> previousResultsView;

    //Handlers
    FileHandler fileHandler;
    AlertBoxHandler alertBoxHandler;
    ImageViewHandler imageViewHandler;
    AudioHandler audioHandler;

    //Declaration
    Puzzle puzzle;
    PlayerState playerState;
    UserDetails userDetails;
    char[][] arr;
    String selectText;
    HashMap<String, TextField> pieceMap;
    List<Character> characterList;
    ArrayList<String> placedPieces;
    ObservableList<String> observableList = FXCollections.observableArrayList();
    ObservableList<String> observableListImage = FXCollections.observableArrayList();
    File topPlayers;
    int currentPoints;

    /**
     * Initialises all the variables required to store the state of the game.
     */
    public PuzzleController() {
        puzzle = new Puzzle();
        playerState = new PlayerState();
        fileHandler = new FileHandler();
        alertBoxHandler = new AlertBoxHandler();
        imageViewHandler = new ImageViewHandler();
        audioHandler = new AudioHandler();
        arr = new char[4][4];
        puzzle.initialiseCharacterList();
        characterList = puzzle.getCharacterList();
        fileHandler.createImageDirectory();
        labels = new ArrayList<>();
        textFields = new ArrayList<>();
        pieceMap = new HashMap<>();
        placedPieces = new ArrayList<>();
        topPlayersListView = new ListView<>();
        previousResultsView = new ListView<>();
        currentPoints = 0;
    }

    /**
     * FXML Initialise method to initialise all the FXML Components like ListView, Label etc.
     * @throws IOException Checks if the File is present or not.
     * @throws ClassNotFoundException Check if the class is found or not.
     */
    @FXML
    public void initialize() throws IOException, ClassNotFoundException {
        userDetails = new UserDetails();
        nameLabel.setText(userDetails.getUserName());
        topPlayersListView.setItems(observableList);
        previousResultsView.setItems(observableListImage);
        topPlayers = new File("topPlayers.txt");
        topPlayersListView.setItems(fileHandler.loadListView(topPlayers, observableList));
        previousResultsView.setItems(fileHandler.loadListViewImage(observableListImage));
        previousResultsView.getSelectionModel().selectedIndexProperty().addListener((observable, oldVal, newVal) -> {
            String fileName = observableListImage.get(newVal.intValue());
            try {
                imageViewHandler.openImageWindow(fileName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        initialiseAll();
    }

    /**
     * A methods to select the Piece which is selected on mouse click
     * @param event Mouse Event to get the component on which the click happened.
     */
    @FXML
    void initialize(MouseEvent event) {
        resetColor((Label) event.getSource());
        Label aLabel = (Label) event.getSource();
        aLabel.setTextFill(Color.BLUE);
        selectText = aLabel.getText();
    }

    /**
     * This method is to select a piece and place it in board.
     * @param event Mouse Event to get the component on which the click happened.
     * @throws IOException Check if the input/output from a file is possible or not.
     */
    @FXML
    void mouseClick(MouseEvent event) throws IOException {
        Node source = (Node) event.getTarget();
        Node parent = source.getParent();
        if (parent.getClass().equals(Label.class)) {
            Integer row = GridPane.getRowIndex(parent) == null ? 0 : GridPane.getRowIndex(parent);
            Integer column = GridPane.getColumnIndex(parent) == null ? 0 : GridPane.getColumnIndex(parent);
            if (selectText == null || selectText.length() == 0) {
                alertBoxHandler.showAlertDialog("Select a Piece to Replace!");
            } else {
                checkPlacement(row, column, selectText, (Label) parent);
            }
        }
    }

    /**
     * This method is to Re-Initialise the board state. This can be done only once and only if a piece is not placed yet.
     * @param event Mouse Event to get the component on which the click happened.
     */
    @FXML
    void reInitialiseBoard(MouseEvent event) {
        initialiseBoard();
        Button button = (Button) event.getSource();
        button.setDisable(true);
    }

    /**
     * This is to initialise all the pieces and the components related to the board.
     */
    private void initialiseAll() {
        initialiseArrayListLabels();
        initialiseTextFields();
        initialisePieces();
        initialisePieceMap();
        initialiseBoard();
    }

    /**
     * Check if there is a possible move for the selected piece in the board at a given position.
     * @param i Int value which specifies the row number of the grid.
     * @param j Int vale which specifies the column number of the grid.
     * @param selectText The piece selected by the in a for of a String.
     * @param label The label which is to be updated in case of a valid move.
     * @throws IOException Check if the input/output from a file is possible or not.
     */
    private void checkPlacement(int i, int j, String selectText, Label label) throws IOException {
        char chars = selectText.toCharArray()[0];
        if (checkPlaced(i,j)) {
            //00
            if(i==0 && j ==0) {
                if (arr[i][j] == chars || arr[i+1][j] == chars || arr[i][j+1] == chars || arr[i+1][j+1] == chars || (!characterList.contains(chars))) {
                    alertBoxHandler.showAlertDialog("Not a Valid Move");
                } else {
                   updatePlacement(i,j, label, chars);
                }
            }
            //01,02
            if(i == 0 && j > 0 && j < 3) {
                if (arr[i][j] == chars || arr[i][j-1] == chars || arr[i][j+1] == chars || arr[i+1][j] == chars || arr[i+1][j-1] == chars || arr[i+1][j+1] == chars || (!characterList.contains(chars))) {
                    alertBoxHandler.showAlertDialog("Not a Valid Move");
                } else {
                    updatePlacement(i, j, label, chars);
                }
            }
            //03
            if(i == 0 && j == 3) {
                if (arr[i][j] == chars || arr[i][j-1] == chars || arr[i+1][j-1] == chars || arr[i+1][j] == chars || (!characterList.contains(chars))) {
                    alertBoxHandler.showAlertDialog("Not a Valid Move");
                } else {
                    updatePlacement(i, j, label, chars);
                }
            }
            //10,20
            if(i > 0 && j == 0 && i < 3) {
                if (arr[i][j] == chars || arr[i-1][j] == chars || arr[i+1][j] == chars || arr[i-1][j+1] == chars || arr[i+1][j+1] == chars || arr[i][j+1] == chars || (!characterList.contains(chars))) {
                    alertBoxHandler.showAlertDialog("Not a Valid Move");
                } else {
                    updatePlacement(i, j, label, chars);
                }
            }
            //30
            if(i == 3 && j == 0) {
                if (arr[i][j] == chars || arr[i][j+1] == chars || arr[i-1][j] == chars || arr[i-1][j+1] == chars || (!characterList.contains(chars))) {
                    alertBoxHandler.showAlertDialog("Not a Valid Move");
                } else {
                    updatePlacement(i, j, label, chars);
                }
            }
            //31,32
            if(i == 3 && j > 0 && j < 3) {
                if (arr[i][j] == chars || arr[i][j-1] == chars || arr[i][j+1] == chars || arr[i-1][j-1] == chars || arr[i-1][j+1] == chars || arr[i-1][j] == chars || (!characterList.contains(chars))) {
                    alertBoxHandler.showAlertDialog("Not a Valid Move");
                } else {
                    updatePlacement(i, j, label, chars);
                }
            }
            //13,23
            if(i > 0  && i < 3 && j== 3) {
                if (arr[i][j] == chars || arr[i-1][j] == chars || arr[i+1][j] == chars || arr[i][j-1] == chars || arr[i-1][j-1] == chars || arr[i+1][j-1] == chars || (!characterList.contains(chars))) {
                    alertBoxHandler.showAlertDialog("Not a Valid Move");
                } else {
                    updatePlacement(i, j, label, chars);
                }
            }
            //11,12,21,22
            if(i > 0  && i < 3 && j > 0 && j < 3) {
                if (arr[i][j] == chars || arr[i-1][j] == chars || arr[i+1][j] == chars || arr[i][j+1] == chars || arr[i][j-1] == chars || arr[i-1][j-1] == chars || arr[i+1][j-1] == chars || arr[i+1][j+1] == chars || arr[i-1][j+1] == chars || (!characterList.contains(chars))) {
                    alertBoxHandler.showAlertDialog("Not a Valid Move");
                } else {
                    updatePlacement(i, j, label, chars);
                }
            }
            //33
            if(i == 3 && j == 3) {
                if (arr[i][j] == chars || arr[i][j-1] == chars || arr[i-1][j-1] == chars || arr[i-1][j] == chars || (!characterList.contains(chars))) {
                    alertBoxHandler.showAlertDialog("Not a Valid Move");
                } else {
                    updatePlacement(i, j, label, chars);
                }
            }
            //Win Condition
            if (characterList.size() == 0) {
                userDetails.setScore(currentPoints);
                fileHandler.addPlayer(userDetails);
                fileHandler.writeArrayList(topPlayers);
                audioHandler.getWinSound().play();
                fileHandler.doTakeScreenshot(userDetails);
                alertBoxHandler.finishGameAlert("Won the Game, Do you want to play again?", aPieceLabel);
            } else {
                HashSet<Character> hashSet = new HashSet<Character>(characterList);
                boolean lose = false;
                outerloop:
                for (Character character : hashSet) {
                    for (int x = 0; x < 4; x++) {
                        for (int y = 0; y < 4; y++) {
                            lose = playerState.checkIfLost(x, y, character, arr, characterList, placedPieces);
                            if (!lose) {
                                break outerloop;
                            }
                        }
                    }
                }
                //Lose - No Possible Moves Left
//                if (lose) {
//                    userDetails.setScore(currentPoints);
//                    fileHandler.addPlayer(userDetails);
//                    fileHandler.writeArrayList(topPlayers);
//                    audioHandler.getLoseSound().play();
//                    fileHandler.doTakeScreenshot(userDetails);
//                    alertBoxHandler.finishGameAlert("Your Score is "+currentPoints+"\nNo more valid moves, Lost the Game, Do you want to play again?", aPieceLabel);
//                }
            }
        } else {
            alertBoxHandler.showAlertDialog("Piece already placed can't be moved");
        }
    }

    /**
     * Updates the UI Components once the piece is placed
     * @param i Int value which specifies the row number of the grid.
     * @param j Int vale which specifies the column number of the grid.
     * @param label The label which is to be updated in case of a valid move.
     * @param chars Character which was place to remove it from the characterList.
     */
    private void updatePlacement(int i, int j,Label label, char chars) {
        //Disable Re-Int
        reIntLabel.setDisable(true);
        //Shift
        label.setText(selectText);
        //Score +
        incrementScore();
        //Color
        label.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        //Piece -
        TextField changeField = pieceMap.get(selectText);
        decrementPiece(changeField);
        //mark as placed
        placedPieces.add(i+""+j);
        //Change List of Character
        characterList.remove(characterList.indexOf(chars));
        //Update original Char Array
        arr[i][j] = chars;
    }

    /**
     * Decrements the piece number once a piece is selected
     * @param textField The text field for which the value has to be changed
     */
    private void decrementPiece(TextField textField) {
        int value = Integer.parseInt(textField.getText());
        value--;
        for (TextField t : textFields) {
            if (t.equals(textField)) {
                t.setText(String.valueOf(value));
                pieceMap.replace(selectText, t);
            }
        }
    }

    /**
     * When a piece is placed, the score is incremented by this method
     */
    private void incrementScore() {
        currentPoints += 1;
        currentScore.setText(String.valueOf(currentPoints));
    }

    /**
     * When a piece is selected for placement, unselect other pieces.
     * @param source Get the current label which was selected.
     */
    private void resetColor(Label source) {
        for (Label label : labels) {
            if (!(label.getId().equals(source.getId()))) {
                label.setTextFill(Color.BLACK);
            }
        }
    }

    /**
     * Initialise the hashmap which contains Piece name as key and piece TextField which has the number of pieces as value.
     */
    private void initialisePieceMap() {
        pieceMap.put("A",aPiece);
        pieceMap.put("B",bPiece);
        pieceMap.put("C",cPiece);
        pieceMap.put("D",dPiece);
        pieceMap.put("E",ePiece);

    }

    /**
     * Initialise the initial count of each piece in the textField.
     */
    private void initialisePieces() {
        aPiece.setText("2");
        bPiece.setText("3");
        cPiece.setText("3");
        dPiece.setText("4");
        ePiece.setText("4");
    }

    /**
     * Initialise the board which is the starting state.
     */
    private void initialiseBoard() {
        arr = puzzle.createBoard(arr,4,5);
        putInitialisation();
    }

    /**
     * Initialises the ArrayList of text fields which contains the count for each left piece.
     */
    private void initialiseTextFields() {
        textFields.add(aPiece);
        textFields.add(bPiece);
        textFields.add(cPiece);
        textFields.add(dPiece);
        textFields.add(ePiece);
    }

    /**
     * Initialises the ArrayList of Labels which contains the labels for all pieces.
     */
    private void initialiseArrayListLabels() {
        labels.add(aPieceLabel);
        labels.add(bPieceLabel);
        labels.add(cPieceLabel);
        labels.add(dPieceLabel);
        labels.add(ePieceLabel);
    }

    /**
     * Initialises the board after getting the initial array of characters
     */
    private void putInitialisation() {
        zeroZero.setText(String.valueOf(arr[0][0]));
        zeroOne.setText(String.valueOf(arr[0][1]));
        zeroTwo.setText(String.valueOf(arr[0][2]));
        zeroThree.setText(String.valueOf(arr[0][3]));
        oneZero.setText(String.valueOf(arr[1][0]));
        oneOne.setText(String.valueOf(arr[1][1]));
        oneTwo.setText(String.valueOf(arr[1][2]));
        oneThree.setText(String.valueOf(arr[1][3]));
        twoZero.setText(String.valueOf(arr[2][0]));
        twoOne.setText(String.valueOf(arr[2][1]));
        twoTwo.setText(String.valueOf(arr[2][2]));
        twoThree.setText(String.valueOf(arr[2][3]));
        threeZero.setText(String.valueOf(arr[3][0]));
        threeOne.setText(String.valueOf(arr[3][1]));
        threeTwo.setText(String.valueOf(arr[3][2]));
        threeThree.setText(String.valueOf(arr[3][3]));
    }

    /**
     * Checks the placed position of the pieces on the board.
     * @param i Int value which specifies the row number of the grid.
     * @param j Int vale which specifies the column number of the grid.
     * @return False if the rowColumn has a piece and True if the rowColumn doesn't have a placed piece.
     */
    private boolean checkPlaced(int i, int j) {
        String check = i+""+j;
        return !placedPieces.contains(check);
    }
}