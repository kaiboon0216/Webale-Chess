import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**ChevronPiece Class */
public class ChevronPiece extends Piece{
    private MoveResult moveResult;
    private double mouseX,mouseY;
    private double oldX,oldY;

    /**@Author:KaiYuan */
    /**ChevronPiece's constructor*/
    public ChevronPiece(PieceType type, String name, int x, int y){
        super(type,name,x,y);
        move(x,y);
        
        Image img;
        // Determine image according to type color
        if(type == PieceType.BLUE)
            img = new Image("./images/blue_chevron.png");
        else
            img = new Image("./images/red_chevron.png");

        // Set the image and add to group
        imageView = new ImageView(img);
        imageView.setFitWidth(90);
        imageView.setFitHeight(90);
        getChildren().add(imageView);
    }

    /**@Author:KaiYuan */
    /**ChevronPiece's movement verification, to check whether the movement is valid */
    public MoveResult movement(int newX,int newY){
        // Initial piece coordinate
        int x0 = super.toBoard(getOldX());
        int y0 = super.toBoard(getOldY());

        try{
            // No Movement: The piece stands still
            if(GameBoard.getTilesGrid()[newX][newY].hasPiece() && (newX == x0) && (newY == y0)){
                System.out.println("No Movement");
                return new MoveResult(MoveType.NONE);
            }

            // Normal Move (Destination has no piece): 2 steps UP/DOWN, then 1 step LEFT/RIGTH
            if( (!GameBoard.getTilesGrid()[newX][newY].hasPiece() && (Math.abs(newY-y0) == 2) && (Math.abs(newX-x0) == 1)) ||
                // Normal Move (Destination has no piece): 2 steps LEFT/RIGHT, then 1 step UP/DOWN
                (!GameBoard.getTilesGrid()[newX][newY].hasPiece() && (Math.abs(newX-x0) == 2) && (Math.abs(newY-y0) == 1)) )
                return new MoveResult(MoveType.NORMAL);

            // Normal move (Destination has other piece): 2 steps then 1 step perpendicular
            if( (GameBoard.getTilesGrid()[newX][newY].hasPiece() && (Math.abs(newY-y0) == 2) && (Math.abs(newX-x0) == 1)) ||
                (GameBoard.getTilesGrid()[newX][newY].hasPiece() && (Math.abs(newX-x0) == 2) && (Math.abs(newY-y0) == 1)) ){
                // Kill Move: If position of mouse released has piece, and the 2 piece types are not the same
                if(GameBoard.getTilesGrid()[newX][newY].getPiece().getType() != this.getType()){
                    System.out.println(GameBoard.getTilesGrid()[x0][y0].getPiece() + " killed " + GameBoard.getTilesGrid()[newX][newY].getPiece());
                    return new MoveResult(MoveType.KILL, GameBoard.getTilesGrid()[newX][newY].getPiece());
                }
            }
        }
        // Exception when the piece is dragged out of the game board
        catch(ArrayIndexOutOfBoundsException ex){
            System.out.println("Piece is dragged out of the gameboard");
        }

        // Invalid Move: The piece stands still too
        System.out.println("Invalid Move");
        return new MoveResult(MoveType.NONE);
    }
}