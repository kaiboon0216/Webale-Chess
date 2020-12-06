import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**PlusPiece Class */
public class PlusPiece extends Piece{
    private MoveResult moveResult;
    private double mouseX,mouseY;
    private double oldX,oldY;

    /**@Author:Kaiboon */
    /**PlusPiece's constructor*/
    public PlusPiece(PieceType type, String name, int x, int y){
        super(type,name,x,y);
        move(x,y);
        
        Image img;
        // Determine image according to type color
        if(type == PieceType.BLUE){
            img = new Image("./images/blue_plus.png");
        }else{
            img = new Image("./images/red_plus.png");
        }

        // Set the image and add to group
        imageView = new ImageView(img);
        imageView.setFitWidth(90);
        imageView.setFitHeight(90);
        getChildren().add(imageView);
    }
    
    /**@Author:Kaiboon */
    /**PlusPiece's movement verification, to check whether the movement is valid */
    public MoveResult movement(int newX,int newY){
        // Initial piece coordinate
        int x0 = super.toBoard(getOldX());
        int y0 = super.toBoard(getOldY());

        //Determine move direction 
        MoveDirection direction ;
        if(newY > y0){
            direction = MoveDirection.DOWN;
        }
        else if(newY < y0){
            direction = MoveDirection.UP;
        }
        else if(newX > x0){
            direction = MoveDirection.RIGHT;
        }
        else{
            direction = MoveDirection.LEFT;
        }
        
        try{
            //To ignore clicking of the piece
            if(GameBoard.getTilesGrid()[newX][newY].hasPiece() && newX == x0 && newY == y0){
                System.out.println("Clicked!");
                return new MoveResult(MoveType.NONE);
            }

            //If there is a piece at the destination, and it is our own, abort move
            if(GameBoard.getTilesGrid()[newX][newY].getPiece() != null){
                if(GameBoard.getTilesGrid()[newX][newY].getPiece().getType() == this.getType()){
                    return new MoveResult(MoveType.NONE);
                }       
            }

            if(x0 != newX && y0 != newY){ //Deny plus from moving diagonally
                return new MoveResult(MoveType.NONE);
            } //Check whether it can kill
            else if(GameBoard.getTilesGrid()[newX][newY].hasPiece() && GameBoard.getTilesGrid()[newX][newY].getPiece().getType() != getType()){ 
                int space;
                switch(direction){ 
                    case DOWN:
                    space = Math.abs(newY - y0);
                    for(int i=1; i < space; i++){
                        Piece p = GameBoard.getTilesGrid()[x0][y0+i].getPiece();
                        if(p != null ){
                            return new MoveResult(MoveType.NONE); 
                        }
                    }
                    break;
                    case UP:
                    space = Math.abs(newY - y0);
                    for(int i=1; i < space; i++){
                        Piece p = GameBoard.getTilesGrid()[x0][y0-i].getPiece();
                        if(p != null){
                            return new MoveResult(MoveType.NONE); 
                        }
                    }
                    break;
                    case RIGHT:
                    space = Math.abs(newX - x0);
                    for(int i=1; i < space; i++){
                        Piece p = GameBoard.getTilesGrid()[x0+i][y0].getPiece();
                        if(p != null){
                            return new MoveResult(MoveType.NONE); 
                        }
                    }
                    break;
                    case LEFT:
                    space = Math.abs(newX - x0);
                    for(int i=1; i < space; i++){
                        Piece p = GameBoard.getTilesGrid()[x0-i][y0].getPiece();
                        if(p != null){
                            return new MoveResult(MoveType.NONE); 
                        }
                    }
                    break;                
                }
                return new MoveResult(MoveType.KILL, GameBoard.getTilesGrid()[newX][newY].getPiece());
            }
            else{
                int space;
                switch(direction){ 
                    case DOWN:
                    space = Math.abs(newY - y0);
                    for(int i=1; i < space; i++){
                        Piece p = GameBoard.getTilesGrid()[x0][y0+i].getPiece();
                        if(p != null ){
                            return new MoveResult(MoveType.NONE); 
                        }
                    }
                    break;
                    case UP:
                    space = Math.abs(newY - y0);
                    for(int i=1; i < space; i++){
                        Piece p = GameBoard.getTilesGrid()[x0][y0-i].getPiece();
                        if(p != null){
                            return new MoveResult(MoveType.NONE); 
                        }
                    }
                    break;
                    case RIGHT:
                    space = Math.abs(newX - x0);
                    for(int i=1; i < space; i++){
                        Piece p = GameBoard.getTilesGrid()[x0+i][y0].getPiece();
                        if(p != null){
                            return new MoveResult(MoveType.NONE); 
                        }
                    }
                    break;
                    case LEFT:
                    space = Math.abs(newX - x0);
                    for(int i=1; i < space; i++){
                        Piece p = GameBoard.getTilesGrid()[x0-i][y0].getPiece();
                        if(p != null){
                            return new MoveResult(MoveType.NONE); 
                        }
                    }
                    break;

                } // check if there's any pieces blocking the plus
                return new MoveResult(MoveType.NORMAL);
            }
        }
        catch(ArrayIndexOutOfBoundsException ex){
            System.out.println("Piece is dragged out of the gameboard");
        }
        System.out.println("Invalid move");
        return new MoveResult(MoveType.NONE);
    }

}