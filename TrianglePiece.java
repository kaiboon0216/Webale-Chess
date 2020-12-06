import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**TrianglePiece Class */
public class TrianglePiece extends Piece{
    private MoveResult moveResult;
    private double mouseX,mouseY;
    private double oldX,oldY;

    /**@Author:Kelvin */
    /**TrianglePiece's constructor*/
    public TrianglePiece(PieceType type, String name, int x, int y){
        super(type,name,x,y);
        move(x,y);
        
        Image img;
        // Determine image according to type color
        if(type == PieceType.BLUE){
            img = new Image("./images/blue_triangle.png");
        }else{
            img = new Image("./images/red_triangle.png");
        }

        // Set the image and add to group
        imageView = new ImageView(img);
        imageView.setFitWidth(90);
        imageView.setFitHeight(90);
        getChildren().add(imageView);    
    }

    /**@Author:Kelvin */
    /**TrianglePiece's movement verification, to check whether the movement is valid */
    public MoveResult movement(int newX,int newY){
        // Initial piece coordinate
        int x0 = super.toBoard(getOldX());
        int y0 = super.toBoard(getOldY());

        //Determine move direction
        MoveDirection direction ;
        if(newX > x0 && newY < y0){
            direction = MoveDirection.DIAGONALUPRIGHT;
        }
        else if(newX < x0 && newY < y0){
            direction = MoveDirection.DIAGONALUPLEFT;
        }
        else if(newX < x0 && newY > y0){
            direction = MoveDirection.DIAGONALDOWNLEFT;
        }
        else{
            direction = MoveDirection.DIAGONALDOWNRIGHT;
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
            if(x0 == newX || y0 == newY){ //Deny plus from moving straight
                return new MoveResult(MoveType.NONE);
            }
            if(Math.abs(newX - x0) != Math.abs(newY - y0)){ // ensure only diagonal move
                return new MoveResult(MoveType.NONE);
            }
            else if(GameBoard.getTilesGrid()[newX][newY].hasPiece() && GameBoard.getTilesGrid()[newX][newY].getPiece().getType() != getType()){
                return new MoveResult(MoveType.KILL, GameBoard.getTilesGrid()[newX][newY].getPiece());
            }
            else{ // check if there's any pieces blocking the plus
                int space;
                switch(direction){ 
                    case DIAGONALUPRIGHT:
                    space = Math.abs(newY - y0);
                    for(int i= 1; i < space; i++){
                        Piece p = GameBoard.getTilesGrid()[x0+i][y0-i].getPiece();
                        if(p != null ){
                            System.out.println("DOWN");
                            System.out.println("x :" + (x0+i));
                            System.out.println("y :" + (y0+i));
                            System.out.println(p);

                            return new MoveResult(MoveType.NONE); 
                        }
                    }
                    
                    break;
                    case DIAGONALUPLEFT:
                    space = Math.abs(newY - y0);
                    for(int i=1; i < space; i++){
                        Piece p = GameBoard.getTilesGrid()[x0-i][y0-i].getPiece();
                        if(p != null){
                            System.out.println("UP");
                            System.out.println("x :" + (x0-i));
                            System.out.println("y :" + (y0-i));
                            System.out.println(p);

                            return new MoveResult(MoveType.NONE); 
                        }
                    }
                    break;
                    case DIAGONALDOWNLEFT:
                    space = Math.abs(newY - y0);
                    for(int i=1; i < space; i++){
                        Piece p = GameBoard.getTilesGrid()[x0-i][y0+i].getPiece();
                        if(p != null){
                            System.out.println("UP");
                            System.out.println("x :" + (x0-i));
                            System.out.println("y :" + (y0-i));
                            System.out.println(p);

                            return new MoveResult(MoveType.NONE); 
                        }
                    }
                    break;
                    case DIAGONALDOWNRIGHT:
                    space = Math.abs(newY - y0);
                    for(int i=1; i < space; i++){
                        Piece p = GameBoard.getTilesGrid()[x0+i][y0+i].getPiece();
                        if(p != null){
                            System.out.println("UP");
                            System.out.println("x :" + (x0-i));
                            System.out.println("y :" + (y0-i));
                            System.out.println(p);

                            return new MoveResult(MoveType.NONE); 
                        }
                    }
                    break;
                }
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

     

