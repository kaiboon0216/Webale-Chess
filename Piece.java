import javafx.scene.layout.StackPane;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**Piece Class */
// The generic class of all pieces and it implements Move class and every pieces need to override the 
// Movement . This forms the design pattern of Strategy DP.
public class Piece extends StackPane implements Move{
    private PieceType type;
    private String name;
    private double mouseX,mouseY;
    private double oldX,oldY;
    protected ImageView imageView;

    /**@Author:Kaiboon */
    /**Piece's constructor*/
    public Piece(PieceType type, String name, int x, int y){
        this.type = type;
        this.name = name;
        /* Required to allow the pice to be move*/
        move(x,y);
        /* Required to allow the pice to be pressed and drag */
        detectDrag(); 
    }

    /**@Author:Kaiboon */
    public PieceType getType() {
        return type;
    }

    /**@Author:Kaiboon */
    public double getOldX() {
        return oldX;
    }

    /**@Author:Kaiboon */
    public double getOldY() {
        return oldY;
    }
    
    /**@Author:Kaiboon */
    public int toBoard(double pixel) {
        return (int) (pixel + GameBoard.BOARD_SIZE / 2) / GameBoard.BOARD_SIZE ;
    }

    /**@Author:Kaiboon */
    public ImageView getImageView() {
        return imageView;
    }

    /**@Author:Kaiboon */
    /**Allow the piece to be pressed and drag */
    public void detectDrag(){
        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        setOnMouseDragged(e -> {
            relocate(e.getSceneX()-mouseX + oldX , e.getSceneY()-mouseY + oldY);
        });
    }

    /**@Author:Kaiboon */
    /**Allow the piece to be move */
    public void move(int x, int y) {
        oldX = x * GameBoard.BOARD_SIZE;
        oldY = y * GameBoard.BOARD_SIZE;
        relocate(oldX, oldY);
    }

    /**@Author:Kaiboon */
    /**the piece return to its original position*/
    public void abortMove() {
        relocate(oldX,oldY);
    }

    /**@Author:Kaiboon */
    @Override
    public String toString() {
        return this.type + " " +this.name;
    }

    /**@Author:Kaiboon */
    public MoveResult movement(int newX,int newY){return null;}
    
}