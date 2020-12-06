import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**ArrowPiece Class */
public class ArrowPiece extends Piece{
    private MoveResult moveResult;
    private double mouseX,mouseY;
    private double oldX,oldY;
    private boolean hasRotate = false;
    
    /**@Author:KaiYi */
    /**ArrowPiece's constructor*/
    public ArrowPiece(PieceType type, String name, int x, int y){
        super(type,name,x,y);
        move(x,y);
        
        Image img;
        if(type == PieceType.BLUE)
            img = new Image("./images/blue_arrow.png");
        else
            img = new Image("./images/red_arrow.png");

        imageView = new ImageView(img);
        imageView.setFitWidth(90);
        imageView.setFitHeight(90);
        getChildren().add(imageView);
    }

    /**@Author:KaiYi */
    /**ArrowPiece's movement verification, to check whether the movement is valid */
    public MoveResult movement(int newX,int newY){
        int x0 = super.toBoard(getOldX());
        int y0 = super.toBoard(getOldY());
        
        try{
            System.out.println((newY-y0));
            System.out.println(newY);
            System.out.println(y0);
            //To ignore clicking of the piece
            if(GameBoard.getTilesGrid()[newX][newY].hasPiece() && newX == x0 && newY == y0){
                System.out.println("Clicked!");
                return new MoveResult(MoveType.NONE);
            }

            //If there is a piece at the destination, and it is our own, abort move
            if(GameBoard.getTilesGrid()[newX][newY].getPiece() != null){
                if(GameBoard.getTilesGrid()[newX][newY].getPiece().getType() == this.getType()){
                    System.out.println(GameBoard.getTilesGrid()[newX][newY].getPiece() + " is there");
                    return new MoveResult(MoveType.NONE);
                }       
            }
           
            // Normal move: when the destination has no piece(rotate = false)
            if(!GameBoard.getTilesGrid()[newX][newY].hasPiece() && (((newY-y0) > -3) && ((newY-y0) <= 0)) && (Math.abs(newX-x0) == 0) && !hasRotate){
                System.out.println("Run Arrow");
				detectEdge(x0,y0,newY);
                return new MoveResult(MoveType.NORMAL);
            }
            
            // Normal move: when the destination has no piece(rotate = true)
            if(hasRotate){
                if(!GameBoard.getTilesGrid()[newX][newY].hasPiece() && (((newY-y0) < 3)&& ((newY-y0) >= 0)) && (Math.abs(newX-x0) == 0)){
                    System.out.println("Run Arrow");
                    detectEdge(x0,y0,newY);
                    return new MoveResult(MoveType.NORMAL);
                }
            }
            
            //If there is a piece at the destination, and it is not your own
            if(GameBoard.getTilesGrid()[newX][newY].hasPiece() && (Math.abs(newY-y0) < 3) && (Math.abs(newX-x0) == 0)){       
                if(GameBoard.getTilesGrid()[newX][newY].hasPiece() && GameBoard.getTilesGrid()[newX][newY].getPiece().getType() != getType()){
                    System.out.println(GameBoard.getTilesGrid()[x0][y0].getPiece() + " killed " + GameBoard.getTilesGrid()[newX][newY].getPiece());
					detectEdge(x0,y0,newY);
				}
			return new MoveResult(MoveType.KILL, GameBoard.getTilesGrid()[newX][newY].getPiece());
            }
        }
        
		// Exception when the piece is dragged out of game board
        catch(ArrayIndexOutOfBoundsException ex){
            System.out.println("Piece is dragged out of the gameboard");
        }
        System.out.println("Invalid move");
        return new MoveResult(MoveType.NONE);
    }
	
    /**@Author:KaiYi */
	//check if the piece is reach to the end then will turn around
    private void detectEdge(int x0, int y0, int newY){
        if(newY == 0 || newY == 7){
            ImageView temp = GameBoard.getTilesGrid()[x0][y0].getPiece().getImageView();
            temp.setRotate(temp.getRotate()+180);
            hasRotate = !hasRotate;
            System.out.println("Reaches edge: Rotate");
        }
    }
}