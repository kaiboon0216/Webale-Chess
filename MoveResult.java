/**@Author: KaiYi*/
/**MoveResult Class*/
/**Describe which piece has move and the result of move is either NONE, NORMAL or KILL */
public class MoveResult{
    private MoveType type;
    private Piece piece;

    
    public MoveResult(MoveType type, Piece piece){
        this.type = type;
        this.piece = piece;
    }

    /**@Author:KaiYi */
    //Second constructor indicate no moving
    public MoveResult(MoveType type){
        this(type, null);
    }

    /**@Author:KaiYi */
    public MoveType getType() {
        return type;
    }

    /**@Author:KaiYi */
    public void setType(MoveType type){
        this.type = type;
    }

    /**@Author:KaiYi */
    public Piece getPiece() {
        return piece;
    }
}