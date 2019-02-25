package tictactoe;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TicTacToeController implements Initializable {
    
    ObservableList<String> levelOptions = 
    FXCollections.observableArrayList(
        "3 X 3 Grid",
        "4 X 4 Grid"
    );
    ObservableList<String> modeOptions = 
    FXCollections.observableArrayList(
        "Against Human",
        "Against Computer"
    );
    
    private Player p1, p2;
    private String p1Name, p2Name, mode, level, currentPlayer = "X", playerWon;
    private int p1Score = 0, p2Score = 0, moves = 0;
    private boolean isGameWon = false;
    private Vector<String> indicesX = new Vector<>(),
            indicesO = new Vector<>(), 
            win1 = new Vector<>(), 
            win2 = new Vector<>(), 
            win3 = new Vector<>(), 
            win4 = new Vector<>(), 
            win5 = new Vector<>(), 
            win6 = new Vector<>(), 
            win7 = new Vector<>(), 
            win8 = new Vector<>();
    
    
    @FXML
    private ComboBox modeSelect, levelSelect;
    @FXML
    private Button levelSelectBtn, modeSelectBtn, startBtn, resetBtn, exitBtn;
    @FXML
    private AnchorPane levelAnchorPane, modeAnchorPane, playerNamePane, gameAnchorPane;
    @FXML
    private TextField player1Input, player2Input;
    @FXML
    private GridPane playGrid;
    @FXML
    private Label p1ScoreField, p2ScoreField, p1NameField, p2NameField;
    
    @FXML
    private void levelSelectBtnAction(ActionEvent event) {
//      get the value of the level selection
        level = levelSelect.getValue().toString();
        
        if(level == "3 X 3 Grid"){
            level = "3";
//          set the mode select screen visible  
            modeAnchorPane.setVisible(true);
        } else if(level == "4 X 4 Grid"){
            level = "4";
//          set the two players name input screen visible
            playerNamePane.setVisible(true);
        } else {
//          player did not select a level
        }
        
//       hide the level select screen
        levelAnchorPane.setVisible(false);
    }
    
    @FXML
    private void modeSelectBtnAction(ActionEvent event) {
        mode = modeSelect.getValue().toString();
        
        if(mode == "Against Human"){
            mode = "h";
        } else if(mode == "Against Computer"){
            mode = "c";
            player2Input.setText("Computer");
            player2Input.setDisable(true);
        } else {
//          player did not select a mode
            return;
        }
        
        playerNamePane.setVisible(true);
        modeAnchorPane.setVisible(false);
    }
    
    @FXML
    public void startGame(ActionEvent event){
        p1 = new Player(player1Input.getText());
        p2 = new Player(player2Input.getText());
        p1Name = p1.getName();
        p2Name = p2.getName();
//        System.out.println("Game started! p1: " + p1Name + " p2: " + p2Name);
        p1NameField.setText(p1Name);
        p2NameField.setText(p2Name);
        gameAnchorPane.setVisible(true);
        playerNamePane.setVisible(false);
    }
    
    @FXML
    public void mouseClicked(MouseEvent event){
        
        if(!isGameWon){
            
            Text sym = new Text(currentPlayer);
            sym.setFont(Font.font(50));

    //      get the position of click
            Node source = (Node)event.getSource();
            Integer colIndex = (GridPane.getColumnIndex(source) == null) ?  0 : (GridPane.getColumnIndex(source));
            Integer rowIndex = (GridPane.getRowIndex(source) == null) ? 0 : (GridPane.getRowIndex(source));

    //      draw symbol (X or O)
            playGrid.add(sym, colIndex, rowIndex);
            
            GridPane.setHalignment(sym, HPos.CENTER);
//            System.out.println("Pane clicked! Row: " + rowIndex + ", Col: " + colIndex);
            recordMove(sym, rowIndex, colIndex);
//            System.out.println(indicesX + " " + indicesO);
            changePlayer();
            moves++;
            gameState();
            return;
        }
        Alert gameWonAlert = new Alert(Alert.AlertType.INFORMATION);
        gameWonAlert.setTitle("Game Won!");
        gameWonAlert.setHeaderText(null);
        gameWonAlert.setContentText(playerWon + " has won the game!");
        System.out.println("Game won by " + playerWon);
        gameWonAlert.showAndWait();
        
    }
    
    @FXML
    private void resetAll(){
        resetBoard();
        p1Score = 0;
        p2Score = 0;
        indicesX.removeAllElements();
        indicesO.removeAllElements();
        changeScore(3);
    }
    
    @FXML
    private void resetBoard(){
        playGrid.getChildren().forEach((stackPane) -> {
            System.out.println(stackPane);
            
            
        });
    }
    
    @FXML
    private void exitGame(){
        System.exit(0);
    }
    
    private void changePlayer(){
        if(currentPlayer.equalsIgnoreCase("X")){
            currentPlayer = "O";
        } else {
            currentPlayer = "X";
        }
        
    }
    
    private void recordMove(Text p, Integer r, Integer c){
        String box = "" + c + r;
        System.out.println(box);
        if(currentPlayer == "X"){
            indicesX.add(box);
        } else {
            indicesO.add(box);
        }
    }
    
    private void gameState(){
        if(indicesX.containsAll(win1) || indicesX.containsAll(win2) || indicesX.containsAll(win3) || indicesX.containsAll(win4) || indicesX.containsAll(win5) || indicesX.containsAll(win6) || indicesX.containsAll(win7) || indicesX.containsAll(win8)){
            isGameWon = true;
            playerWon = p1Name;
            p1Score++;
            changeScore(1);
        } else if(indicesO.containsAll(win1) || indicesO.containsAll(win2) || indicesO.containsAll(win3) || indicesO.containsAll(win4) || indicesO.containsAll(win5) || indicesO.containsAll(win6) || indicesO.containsAll(win7) || indicesO.containsAll(win8)){
            isGameWon = true;
            playerWon = p2Name;
            p2Score++;
            changeScore(2);
        }
    }
    
    private void changeScore(int p){
        switch(p){
            case 1:
                p1ScoreField.setText(p1Score + "");
                break;
            case 2:
                p2ScoreField.setText(p2Score + "");
                break;
            case 3:
                p1ScoreField.setText(p1Score + "");
                p2ScoreField.setText(p2Score + "");
                break; 
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//      set the options for the Comboboxes
        levelSelect.setItems(levelOptions);
        modeSelect.setItems(modeOptions);
        
//      winning combinations
        win1.add("00");
        win1.add("01");
        win1.add("02");
        
        win2.add("10");
        win2.add("11");
        win2.add("12");
        
        win3.add("20");
        win3.add("21");
        win3.add("22");
        
        win4.add("00");
        win4.add("10");
        win4.add("20");
        
        win5.add("01");
        win5.add("11");
        win5.add("21");
        
        win6.add("02");
        win6.add("12");
        win6.add("22");
        
        win7.add("00");
        win7.add("11");
        win7.add("22");
        
        win8.add("20");
        win8.add("11");
        win8.add("02");
    }    
    
}
