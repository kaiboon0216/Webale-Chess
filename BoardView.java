import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Group;
import javafx.scene.transform.Translate;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.text.*;
import javafx.event.*;
import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.BufferedReader;

// MVC part of the program, it is the view class which visualize the pieces,board and the movement of pieces
/**@Author:KaiYuan*/
public class BoardView {
    private Group boardGroup = new Group();
    private Group pieceGroup = new Group();
    private BorderPane root=  new BorderPane();
    private BorderPane infoBar =  new BorderPane();
    private Text roundText = new Text("Round = " + Integer.toString(GameBoard.getRound()));
    private Text turnText = new Text("Red Turn !");
    private Pane stack = new Pane();
    private VBox vBox2 = new VBox();
    private MenuBar menuBar = new MenuBar();



    // Constructor of the BoardView class
    /**@Author:KaiYuan */
    public BoardView(){
        roundText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        turnText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        vBox2.getChildren().addAll(roundText,turnText);
        infoBar.setCenter(vBox2);
        stack.getChildren().addAll(boardGroup,pieceGroup);
        root.setCenter(stack);
        root.setTop(infoBar);
        root.setPrefSize(GameBoard.BOARD_SIZE*GameBoard.WIDTH,GameBoard.BOARD_SIZE*GameBoard.HEIGHT);
        
        MenuBar menuBar = new MenuBar();
        ImageView imgMenu = new ImageView("./images/menu.png");
        imgMenu.setFitWidth(30);
        imgMenu.setFitHeight(30);
        Menu fileMenu = new Menu("Menu",imgMenu);
        ImageView imgReset = new ImageView("./images/reset.png");
        imgReset.setFitWidth(30);
        imgReset.setFitHeight(30);
        ImageView imgSave = new ImageView("./images/save.png");
        imgSave.setFitWidth(30);
        imgSave.setFitHeight(30);
        ImageView imgLoad = new ImageView("./images/folder.png");
        imgLoad.setFitWidth(30);
        imgLoad.setFitHeight(30);
        ImageView imgExit = new ImageView("./images/exit.png");
        imgExit.setFitWidth(30);
        imgExit.setFitHeight(30);
        MenuItem reset = new MenuItem("Reset",imgReset);
        MenuItem save = new MenuItem("Save",imgSave);
        MenuItem load = new MenuItem("Load",imgLoad);
        MenuItem exit = new MenuItem("Exit",imgExit);

        fileMenu.getItems().addAll(reset,save,load,exit);
        menuBar.getMenus().add(fileMenu);
        VBox vBox1 = new VBox(menuBar);
        infoBar.setTop(vBox1);

        save.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                
                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files(*.txt)","*.txt");
                fileChooser.getExtensionFilters().add(extFilter);
                
                try{
                    File file = fileChooser.showSaveDialog(root.getScene().getWindow());
                    System.out.println(file);
                    if(file != null){
                        GameController.saveGameFile(file);
                    }
                }catch(NullPointerException ex){}
            }
        });

        load.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                
                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files(*.txt)","*.txt");
                fileChooser.getExtensionFilters().add(extFilter);

                try{
                    File file = fileChooser.showOpenDialog(root.getScene().getWindow());
                    System.out.println(file);
                    if(file != null){
                        GameController.loadGameFile(file);
                    }
                }
                catch(NullPointerException ex){}
            }
        });

        reset.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                try{
                    GameController.resetGame();
                }catch(Exception ex){
                    System.out.println(ex.getMessage());
                }
            }
        });

        exit.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                try{
                    GameController.exitGame();
                }catch(Exception ex){
                    System.out.println(ex.getMessage());
                }
            }
        });
    }
 
    
    // Accessor of BoardGroup
    /**@Author:KaiYuan */
    public Group getBoardGroup(){
        return boardGroup;
    }

    // Accessor of PieceGroup
    /**@Author:KaiYuan */
    public Group getPieceGroup(){
        return pieceGroup;
    }

    // Accessor of Root
    /**@Author:KaiYuan */
    public BorderPane getRoot(){
        return root;
    }

    // Accessor of InfoBar
    /**@Author:KaiYuan */
    public BorderPane getInfoBar(){
        return root;
    }

    // Accessor of RoundText
    /**@Author:KaiYuan */
    public Text getRoundText(){
        return roundText;
    }

    // Accessor of TurnText
    /**@Author:KaiYuan */
    public Text getTurnText(){
        return turnText;
    }

    // Accessor of Stack
    /**@Author:KaiYuan */
    public Pane getStack(){
        return stack;
    }

    // Accessor of VBox2
    /**@Author:KaiYuan */
    public VBox getVBox2(){
        return vBox2;
    }

    // Accessor of Menu
    /**@Author:KaiYuan */
    public MenuBar getMenu(){
        return menuBar;
    }


    

    




}
