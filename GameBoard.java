import java.util.ArrayList;

import javafx.scene.layout.StackPane;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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


// Model part of MVC design pattern, it is also build using singleton design pattern to ensure that there is only one gameboard being
// declared every single game. The constructor of the class is private, hence, no other instantion of the class is allowed.
/**@Author: Kelvin*/
public class GameBoard{
    public static final int BOARD_SIZE = 100;
    public static final int WIDTH = 7;
    public static final int HEIGHT = 8;
    private static GameBoard board = new GameBoard();

    private static Tile[][] tilesGrid = new Tile[WIDTH][HEIGHT];

    private static int round = 0;
    private static ArrayList<Piece>redPieces = new ArrayList<>();
    private static ArrayList<Piece>bluePieces = new ArrayList<>();
    private static Boolean blueTurn = false;
    private static Boolean redTurn = true;
    private static Boolean flip = false;

    
    // Singleton DP , Constructor of the class, it is private so that there's no new instantiation of the class to ensure there's only one GameBoard
    /**@Author: Kelvin*/
    private GameBoard() {

    }

    //Initialize all the pieces on the gameBoard*
    /**@Author: Kelvin*/
    public static Parent initContent(){

        for(int y=0; y<HEIGHT;y++){
            for(int x=0; x<WIDTH;x++){
                Tile tile = new Tile((x+y)%2 == 0,x,y);
                tilesGrid[x][y] = tile;

                GameController.getBoardView().getBoardGroup().getChildren().add(tile);
                Piece piece = null;

                if( y == 0 && x == 3){
                    piece = new SunPiece(PieceType.BLUE,"Sun", x, y);
                    bluePieces.add(piece);
                    GameController.detectMove(piece);
                }
                else if( y == 7 && x == 3){
                    piece = new SunPiece(PieceType.RED,"Sun", x, y);
                    redPieces.add(piece);
                    GameController.detectMove(piece);
                }
                else if((x == 0 || x == 6)&& (y == 0)){
                    piece = new PlusPiece(PieceType.BLUE,"Plus", x, y);
                    bluePieces.add(piece);
                    GameController.detectMove(piece);
                }
                else if((x == 0 || x == 6)&& (y == 7)){
                    piece = new PlusPiece(PieceType.RED,"Plus", x, y);
                    redPieces.add(piece);
                    GameController.detectMove(piece);
                }
                else if((x == 1 || x == 5)&& (y == 0)){
                    piece = new TrianglePiece(PieceType.BLUE,"Triangle", x, y);
                    bluePieces.add(piece);
                    GameController.detectMove(piece);
                }
                else if((x == 1 || x == 5)&& (y == 7)){
                    piece = new TrianglePiece(PieceType.RED,"Triangle", x, y);
                    redPieces.add(piece);
                    GameController.detectMove(piece);
                }
                else if((x == 2 || x == 4)&& (y == 0)){
                    piece = new ChevronPiece(PieceType.BLUE,"Chevron", x,y);
                    bluePieces.add(piece);
                    GameController.detectMove(piece);
                }
                else if((x == 2 || x == 4)&& (y == 7)){
                    piece = new ChevronPiece(PieceType.RED,"Chevron", x,y);
                    redPieces.add(piece);
                    GameController.detectMove(piece);
                }else if((x == 0 || x == 2 || x == 4 || x == 6)&& (y == 1)){
                    piece = new ArrowPiece(PieceType.BLUE,"Arrow", x, y);
                    bluePieces.add(piece);
                    GameController.detectMove(piece);
                }else if((x == 0 || x == 2 || x == 4 || x == 6)&& (y == 6)){
                    piece = new ArrowPiece(PieceType.RED,"Arrow", x, y);
                    redPieces.add(piece);
                    GameController.detectMove(piece);
                }
                if(piece != null){
                    tile.setPiece(piece);
                    GameController.getBoardView().getPieceGroup().getChildren().add(piece);
                }

            }
        }

        System.out.println(redPieces);
        System.out.println(bluePieces);
        return GameController.getBoardView().getRoot();
    }

    //Accesor of Round
    /**@Author: Kelvin*/
    public static int getRound(){
        return round;
    }

    //Mutator of Round
    /**@Author: Kelvin*/
    public static void setRound(int number){
        round = number;
    }

    //Mutator of redTurn
    /**@Author: Kelvin*/
    public static void setRedTurn(Boolean bool){
        redTurn = bool;
    }

    //Mutator of blueTurn
    /**@Author: Kelvin*/
    public static void setBlueTurn(Boolean bool){
        blueTurn = bool;
    }

    //require to increment round by 1
    /**@Author: Kelvin*/
    public static void roundIncrementer(){
        round++;
    }

    //It is required to change redTurn to true and vice versa
    /**@Author: Kelvin*/
    public static void redTurnToggle(){
        redTurn = !redTurn;
    }

    //It is required to change blueTurn to true and vice versa
    /**@Author: Kelvin*/
    public static void blueTurnToggle(){
        blueTurn = !blueTurn;
    }

    //Accesor of tilesGrid
    /**@Author: Kelvin*/
    public static Tile[][] getTilesGrid(){
        return tilesGrid;
    }

    //Accesor of redPieces
    /**@Author: Kelvin*/
    public static ArrayList<Piece> getRedPieces(){
        return redPieces;
    }

    //Accesor of bluePieces
    /**@Author: Kelvin*/
    public static ArrayList<Piece> getBluePieces(){
        return bluePieces;
    }

    //Accesor of BlueTurn
    /**@Author: Kelvin*/
    public static Boolean getBlueTurn(){
        return blueTurn;
    }
    
    //Accesor of redTurn
    /**@Author: Kelvin*/
    public static Boolean getRedTurn(){
        return redTurn;
    }

    //Accesor of Flip
    /**@Author: Kelvin */
    public static Boolean getFlip(){
        return flip;
    }

   //Accesor of GameBoard
   /**@Author: Kelvin*/ 
    public static GameBoard getInstance() {
        return board;
    }

    // Arrange the pieces in row in reverse order, for flipping board purpose
    /**@Author: Kelvin*/
    public static void reverseHorizontal(){
        Piece lastPiece = null;
        Piece current = null;
    
        for(int i = 0;i < HEIGHT; i++){
            for(int j = 0; j < WIDTH/2;j++){
                lastPiece = tilesGrid[WIDTH-1-j][i].getPiece();
                current = tilesGrid[j][i].getPiece();
    
                if(current == null && lastPiece == null){
                    continue;
                }
                else if(current != null && lastPiece == null){
                    current.move((WIDTH-1-j), i);
                    tilesGrid[WIDTH-1-j][i].setPiece(current);
                    tilesGrid[j][i].setPiece(null);
                }
                else if(current == null && lastPiece != null){
                    lastPiece.move(j, i);
                    tilesGrid[j][i].setPiece(lastPiece);
                    tilesGrid[WIDTH-1-j][i].setPiece(null);
    
    
                }
                else{
                    lastPiece.move(j, i);
                    System.out.println("last piece :" + lastPiece);
                    current.move((WIDTH-1-j), i);
                    System.out.println("first piece :" + current);
    
                    //set tiles grid to contain the pieces
                    tilesGrid[j][i].setPiece(lastPiece);
                    tilesGrid[WIDTH-1-j][i].setPiece(current);
    
                    //movie the piece gui
                }
                
    
            }
        }
    }
    
    // Arrange the pieces of the column in reverse order, needed for flipping board purpose
    /**@Author: Kelvin*/
    public static void reverseVertical(){
        Piece lastPiece = null;
        Piece current = null;
    
        for(int i = 0;i < WIDTH; i++){
            for(int j = 0; j < HEIGHT/2;j++){
                lastPiece = tilesGrid[i][HEIGHT-1-j].getPiece();
                current = tilesGrid[i][j].getPiece();
    
                if(current == null && lastPiece == null){
                    continue;
                }
                else if(current != null && lastPiece == null){
                    current.move(i, HEIGHT-1-j);
                    tilesGrid[i][j].setPiece(null);
                    tilesGrid[i][HEIGHT-1-j].setPiece(current);
                }
                else if(current == null && lastPiece != null){
                    lastPiece.move(i, j);
                    tilesGrid[i][j].setPiece(lastPiece);
                    tilesGrid[i][HEIGHT-1-j].setPiece(null);
                }           
                else{
                    lastPiece.move(i, j);
                    current.move(i, (HEIGHT-1-j));
                    tilesGrid[i][j].setPiece(lastPiece);
                    tilesGrid[i][HEIGHT-1-j].setPiece(current);
                }
            }
        }
    }
    

    // Method required to flip the board into player perspective
    /**@Author: Kelvin*/
    public static void flipBoard(){
        reverseHorizontal();
        reverseVertical();
        ImageView temp;
        for(int i = 0; i < WIDTH;i++){
            for(int j = 0; j < HEIGHT; j ++){
                if(tilesGrid[i][j].getPiece() == null){
                    continue;
                }
                temp = tilesGrid[i][j].getPiece().getImageView();
                temp.setRotate(temp.getRotate()+180);
            }
        }
        System.out.println("Flip :" + flip);       
    }
}

    

    