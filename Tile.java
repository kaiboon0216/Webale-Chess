import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.layout.StackPane;

/**@Author:KaiYi,WenDong */
/**Tile Class */
public class Tile extends StackPane{
    private Piece piece;

    /**@Author:KaiYi */
    /**Tile's constructor */
    public Tile(boolean light,int x, int y) {
        // set each tile box to black color
        setStyle("-fx-border-color: black");

        // set each tile box size
        setPrefSize(GameBoard.BOARD_SIZE,GameBoard.BOARD_SIZE);
        
        // set the position of each tile box (0 at top left)
        relocate(x*GameBoard.BOARD_SIZE, y*GameBoard.BOARD_SIZE);
    }

    /**@Author:WenDong*/
    public boolean hasPiece() {
        return piece != null;
    }

    /**@Author:WenDong*/
    public Piece getPiece() {
        return piece;
    }

    /**@Author:WenDong*/
    public void setPiece(Piece piece){
        this.piece = piece;
    }
    
    

}