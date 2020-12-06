import javafx.event.*;
import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileReader;
import java.io.BufferedReader;

import javafx.scene.control.Button;
import javafx.scene.control.Alert;

// Controller part of the MVC design pattern, it controls how the data flow into the gameboard and updates BoardView
// whenever the pieces changes. It also have Singleton Design pattern as there's only one gameboard hence it only
// need one GameController. The class have a private constructor hence no other instantiation of GameController is allowed.
/**@Author:Kaiboon,KaiYi */
public class GameController {
    private static BoardView boardView = new BoardView();
    // Singleton DP, Constructor of the class
    /**@Author:Kaiboon */

    private GameController(){} 

    //Accessor of BoardView
    /**@Author: Kaiboon*/
    public static BoardView getBoardView(){
        return boardView;
    }
    
    //Save the position of chess into a txt file
    /**@Author: Kaiboon*/
    public static void saveGameFile(File file){
        try{            
            PrintWriter writer = new PrintWriter(file);
            // write current round and red piece turn to the file
            writer.println(GameBoard.getRound() + " redTurn " + GameBoard.getRedTurn());

            for(int y = 0; y < GameBoard.HEIGHT; y++){
                for(int x = 0; x < GameBoard.WIDTH; x++){
                    // write current piece and its location to the file
                    writer.println(x + " " + y + " " + GameBoard.getTilesGrid()[x][y].getPiece());
                }
            }
            writer.close(); // finish writing and close
        }
        catch(Exception ex){}
    }

    //load the gamefile into the app
    /**@Author: Kaiboon*/
    public static void loadGameFile(File file){
        // clear all current data before loading existing game file
        GameBoard.getRedPieces().clear();
        GameBoard.getBluePieces().clear();
        boardView.getPieceGroup().getChildren().clear();
        for(int y=0; y<GameBoard.HEIGHT;y++){
            for(int x=0; x<GameBoard.WIDTH;x++){
                GameBoard.getTilesGrid()[x][y].setPiece(null);
            }
        }
        //boardGroup.getChildren().clear();
        
        try{
            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            // Data string for each line from the save file
            String content = fileReader.readLine();

            // first line of the save file to get number of round and red piece turn
            String[] temp = content.split("\\s+");

            GameBoard.setRound(Integer.parseInt(temp[0]));
            GameBoard.setRedTurn(Boolean.parseBoolean(temp[2]));
            GameBoard.setBlueTurn(!Boolean.parseBoolean(temp[2]));
            System.out.println("Blue turn :" + GameBoard.getBlueTurn());
            
            updateRound();
           
            // move to next line of the save file
            content = fileReader.readLine();
            
            while(content != null){
                //System.out.println(content+" Content here");
                // load non null pieces only, split the data content by space
                if(!content.contains("null"))
                    //System.out.println(content+" Content here");
                    // Pass in only non null pieces data to load the game, others are null piece
                    loadSavedGame(content.split("\\s+"));

                content = fileReader.readLine();
            }
            if(GameBoard.getBlueTurn()== true){
                ImageView pieceImage;
                for(int i = 0; i < GameBoard.WIDTH;i++){
                    for(int j = 0; j < GameBoard.HEIGHT; j ++){
                        if(GameBoard.getTilesGrid()[i][j].getPiece() == null){
                            continue;
                        }
                        System.out.println("rotated");
                        pieceImage = GameBoard.getTilesGrid()[i][j].getPiece().getImageView();
                        pieceImage.setRotate(pieceImage.getRotate()+180);
                    }
                }
            }
            fileReader.close();
            System.out.println(GameBoard.getRedPieces());
            System.out.println(GameBoard.getBluePieces());
            updateTurn(temp[1]);
        }
        catch(IOException ex){}
    }

    //load the pieces of the saved game into the board
    /**@Author:Kaiboon */
    public static void loadSavedGame(String[] contentSplit){
        // Print out x, y, piece colour, piece type
        System.out.println(contentSplit[0] + " " + contentSplit[1]+ " " + contentSplit[2]+" " + contentSplit[3]);
        int tempX = Integer.parseInt(contentSplit[0]);
        int tempY = Integer.parseInt(contentSplit[1]);
        
        Piece piece = null;
        if(contentSplit.length == 4){
            if(contentSplit[2].equals("RED")){
                switch(contentSplit[3]){
                    case "Sun" : 
                        piece = new SunPiece(PieceType.RED,"Sun", tempX, tempY);
                        break;
                    case "Plus":
                        piece = new PlusPiece(PieceType.RED,"Plus", tempX, tempY);
                        break;
                    case "Triangle":
                        piece = new TrianglePiece(PieceType.RED,"Triangle", tempX, tempY);
                        break;
                    case "Chevron":
                        piece = new ChevronPiece(PieceType.RED,"Chevron", tempX,tempY);
                        break;
                    case "Arrow": 
                        piece = new ArrowPiece(PieceType.RED,"Arrow", tempX, tempY);
                        break;
                    default:
                        System.out.println("default");
                }
                
            }else if (contentSplit[2].equals("BLUE")){
                switch(contentSplit[3]){
                    case "Sun" : 
                        piece = new SunPiece(PieceType.BLUE,"Sun", tempX, tempY);
                        break;
                    case "Plus":
                        piece = new PlusPiece(PieceType.BLUE,"Plus", tempX, tempY);
                        break;
                    case "Triangle":
                        piece = new TrianglePiece(PieceType.BLUE,"Triangle", tempX, tempY);
                        break;
                    case "Chevron":
                        piece = new ChevronPiece(PieceType.BLUE,"Chevron", tempX,tempY);
                        break;
                    case "Arrow": 
                        piece = new ArrowPiece(PieceType.BLUE,"Arrow", tempX, tempY);
                        break;
                    default:
                        System.out.println("default");
                    }
            }
        }
        if(piece != null){
            if(contentSplit[2].equals("RED")){
                GameBoard.getRedPieces().add(piece);
                        
            }else if(contentSplit[2].equals("BLUE")){
                GameBoard.getBluePieces().add(piece);
            }
            GameBoard.getTilesGrid()[tempX][tempY].setPiece(piece);
            boardView.getPieceGroup().getChildren().add(piece);
            detectMove(piece);
        }

    }

    // to rest the game to its initial layout
    /**@Author:Kaiboon */
    public static void resetGame(){
        // clear the current data
        GameBoard.setRound(0);
        
        updateRound();
        GameBoard.getRedPieces().clear();
        GameBoard.getBluePieces().clear();

        //Reset Means restart, is red turn
        GameBoard.setBlueTurn(false);
        GameBoard.setRedTurn(true);

        
        System.out.println("Red turn now!");
        boardView.getTurnText().setText("Red Turn !");
        
        boardView.getPieceGroup().getChildren().clear();

        for(int y=0; y<GameBoard.HEIGHT;y++){
            for(int x=0; x<GameBoard.WIDTH;x++){
                Tile tile = new Tile((x+y)%2 == 0,x,y);
                GameBoard.getTilesGrid()[x][y] = tile;

               boardView.getBoardGroup().getChildren().add(tile);
                Piece piece = null;

                if( y == 0 && x == 3){
                    piece = new SunPiece(PieceType.BLUE,"Sun", x, y);
                    GameBoard.getBluePieces().add(piece);
                    detectMove(piece);
                }
                else if( y == 7 && x == 3){
                    piece = new SunPiece(PieceType.RED,"Sun", x, y);
                    GameBoard.getRedPieces().add(piece);
                    detectMove(piece);
                }
                else if((x == 0 || x == 6)&& (y == 0)){
                    piece = new PlusPiece(PieceType.BLUE,"Plus", x, y);
                    GameBoard.getBluePieces().add(piece);
                    detectMove(piece);
                }
                else if((x == 0 || x == 6)&& (y == 7)){
                    piece = new PlusPiece(PieceType.RED,"Plus", x, y);
                    GameBoard.getRedPieces().add(piece);
                    detectMove(piece);
                }
                else if((x == 1 || x == 5)&& (y == 0)){
                    piece = new TrianglePiece(PieceType.BLUE,"Triangle", x, y);
                    GameBoard.getBluePieces().add(piece);
                    detectMove(piece);
                }
                else if((x == 1 || x == 5)&& (y == 7)){
                    piece = new TrianglePiece(PieceType.RED,"Triangle", x, y);
                    GameBoard.getRedPieces().add(piece);
                    detectMove(piece);
                }
                else if((x == 2 || x == 4)&& (y == 0)){
                    piece = new ChevronPiece(PieceType.BLUE,"Chevron", x,y);
                    GameBoard.getBluePieces().add(piece);
                    detectMove(piece);
                }
                else if((x == 2 || x == 4)&& (y == 7)){
                    piece = new ChevronPiece(PieceType.RED,"Chevron", x,y);
                    GameBoard.getRedPieces().add(piece);
                    detectMove(piece);
                }else if((x == 0 || x == 2 || x == 4 || x == 6)&& (y == 1)){
                    piece = new ArrowPiece(PieceType.BLUE,"Arrow", x, y);
                    GameBoard.getBluePieces().add(piece);
                    detectMove(piece);
                }else if((x == 0 || x == 2 || x == 4 || x == 6)&& (y == 6)){
                    piece = new ArrowPiece(PieceType.RED,"Arrow", x, y);
                    GameBoard.getRedPieces().add(piece);
                    detectMove(piece);
                }
                if(piece != null){
                    tile.setPiece(piece);
                    boardView.getPieceGroup().getChildren().add(piece);
                }

            }
        }

        System.out.println(GameBoard.getRedPieces());
        System.out.println(GameBoard.getBluePieces());
    }

    //To exit the game 
    /**@Author:Kaiboon */
    public static void exitGame(){
        System.exit(0);
    }

    //Update the round text
    /**@Author:KaiYi */
    public static void updateRound(){
        boardView.getRoundText().setText("Round = " + Integer.toString(GameBoard.getRound()));
    }


    // Update the turntext 
    /**@Author:KaiYi */
    public static void updateTurn(String turn){
        if(turn.equals("redTurn") && GameBoard.getRedTurn()==true){
            System.out.println("Red turn now!");
            boardView.getTurnText().setText("Red Turn !");
        }
        else{
            System.out.println("Blue turn now!");
            boardView.getTurnText().setText("Blue Turn !");
        }
    }

    // Detect the movement of the pieces
    /**@Author:KaiBoon */
    public static void detectMove(Piece piece){
        piece.setOnMouseReleased(e -> {
            int newX = toBoard(piece.getLayoutX());
            int newY = toBoard(piece.getLayoutY());

            MoveResult result;

            int x0 = toBoard(piece.getOldX());
            int y0 = toBoard(piece.getOldY());


            Boolean tempBlue = GameBoard.getBlueTurn();
            Boolean tempRed = GameBoard.getRedTurn();
            

            
            if(GameBoard.getBlueTurn() == false && piece.getType() == PieceType.BLUE){
                result = new MoveResult(MoveType.NONE);
            }
            else if(GameBoard.getRedTurn() == false && piece.getType() == PieceType.RED){
                result = new MoveResult(MoveType.NONE);
            }
            else{
                result = piece.movement(newX,newY);
            }


            switch (result.getType()){
                case NONE:
                    piece.abortMove();
                    System.out.println("Round" + GameBoard.getRound());

                    break;
                case NORMAL:
                    piece.move(newX,newY);
                    GameBoard.getTilesGrid()[x0][y0].setPiece(null);
                    GameBoard.getTilesGrid()[newX][newY].setPiece(piece);
                    if(GameBoard.getBlueTurn() == false){
                        
                        GameBoard.blueTurnToggle();
                        GameBoard.redTurnToggle();

                        System.out.println("Blue turn now!");
                        boardView.getTurnText().setText("Blue Turn !");
                    }
                    else{
                        GameBoard.redTurnToggle();
                        GameBoard.blueTurnToggle();

                        System.out.println("Red turn now!");
                        boardView.getTurnText().setText("Red Turn !");

                    }
                    GameBoard.roundIncrementer();
                    updateRound();
                    GameBoard.flipBoard();
                    changeState();
                    System.out.println("Round" + GameBoard.getRound());
                    
                    break;
                case KILL:
                    Piece otherPiece = GameBoard.getTilesGrid()[newX][newY].getPiece();
                    GameBoard.getTilesGrid()[newX][newY].setPiece(null);
                    piece.move(newX,newY);
                    GameBoard.getTilesGrid()[newX][newY].setPiece(piece);
                    GameBoard.getTilesGrid()[x0][y0].setPiece(null);
                    boardView.getPieceGroup().getChildren().remove(otherPiece);
                    
                    String message = new String();
                    if(otherPiece.toString().equals("RED Sun")||otherPiece.toString().equals("BLUE Sun")){
                        if(otherPiece.toString().equals("RED Sun")) message = "BLUE Won";
                        if(otherPiece.toString().equals("BLUE Sun")) message = "RED Won";

                        Alert gameOver = new Alert(Alert.AlertType.INFORMATION);
                        gameOver.setHeaderText(message);
                        gameOver.setTitle("Game Over");
                        gameOver.setContentText("The game is reset.");
                        gameOver.showAndWait();
                        resetGame();
                        break;
                    }

                    if(GameBoard.getBlueTurn() == false){
                        
                        GameBoard.blueTurnToggle();
                        GameBoard.redTurnToggle();
                        System.out.println("Blue turn now!");
                        boardView.getTurnText().setText("Blue Turn !");
                    }
                    else{
                        GameBoard.redTurnToggle();
                        GameBoard.blueTurnToggle();
                        System.out.println("Red turn now!");
                        boardView.getTurnText().setText("Red Turn !");

                    }
                    
                    GameBoard.roundIncrementer();
                    updateRound();
                    GameBoard.flipBoard();
                    changeState();
                    System.out.println("Round" + GameBoard.getRound());
                    break;
            }

        });
    }


    // Act as a translator of X and Y, changing x to the value of the board size
    /**@Author:KaiYi */
    public static int toBoard(double pixel) {
        // +BOARD_SIZE/2 >> half box range for each to detect outside of which box to in
        // /BOARD_SIZE >> 80/100=0.8 >> x at 0
        return (int) (pixel + GameBoard.BOARD_SIZE / 2) / GameBoard.BOARD_SIZE;
    }


    // It is required to change Plus to Triangle and Triangle to Plus
    /**@Author:Kaiboon */         
    public static void changeState(){
        String pieceInfoString;
        Piece piece = null;
        if(GameBoard.getRound()%4 == 0){
            System.out.println("Change State");
            for(int y=0; y<GameBoard.HEIGHT;y++){
                for(int x=0; x<GameBoard.WIDTH;x++){ 
                    pieceInfoString = String.valueOf(GameBoard.getTilesGrid()[x][y].getPiece());
                    String[] pieceInfoStringSplit = pieceInfoString.split("\\s+");
                    if(!pieceInfoStringSplit[0].equals("null")){
                        if(pieceInfoStringSplit[0].equals("RED") && (pieceInfoStringSplit[1].equals("Plus") || pieceInfoStringSplit[1].equals("Triangle"))){                           
                            boardView.getPieceGroup().getChildren().remove(GameBoard.getTilesGrid()[x][y].getPiece());
                            GameBoard.getTilesGrid()[x][y].setPiece(null);
                            GameBoard.getRedPieces().remove(GameBoard.getTilesGrid()[x][y].getPiece());
                            switch(pieceInfoStringSplit[1]){ 
                                case "Plus":
                                    piece = new TrianglePiece(PieceType.RED,"Triangle", x, y);
                                    break;
                                case "Triangle":
                                    piece = new PlusPiece(PieceType.RED,"Plus", x, y);
                                    break;
                                default:
                                    System.out.println("Default");
                                    piece = new Piece(PieceType.RED,"Plus", x, y);
                            }
                            GameBoard.getTilesGrid()[x][y].setPiece(piece);
                            GameBoard.getRedPieces().add(piece);
                            detectMove(piece);
                            boardView.getPieceGroup().getChildren().add(piece);    
                        }else if (pieceInfoStringSplit[0].equals("BLUE") && (pieceInfoStringSplit[1].equals("Plus") || pieceInfoStringSplit[1].equals("Triangle"))){
                            boardView.getPieceGroup().getChildren().remove(GameBoard.getTilesGrid()[x][y].getPiece());
                            GameBoard.getTilesGrid()[x][y].setPiece(null);
                            GameBoard.getBluePieces().remove(GameBoard.getTilesGrid()[x][y].getPiece());
                            switch(pieceInfoStringSplit[1]){ 
                                case "Plus":
                                    piece = new TrianglePiece(PieceType.BLUE,"Triangle", x, y);
                                    break;
                                case "Triangle":
                                    piece = new PlusPiece(PieceType.BLUE,"Plus", x, y);
                                    break;
                                default:
                                    System.out.println("Default");
                                    piece = new Piece(PieceType.BLUE,"Plus", x, y);
                            }
                            GameBoard.getTilesGrid()[x][y].setPiece(piece);
                            GameBoard.getBluePieces().add(piece);
                            detectMove(piece);
                            boardView.getPieceGroup().getChildren().add(piece);
                        }
                    }
                }
            }
        }
    }

    
}