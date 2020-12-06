/**@Author:WenDong */
/**Java enum(Use to represent group of PieceType's constants) */
public enum PieceType {
    RED(-1),BLUE(1);
    //RED(1),BLUE(1);

    final int moveDir;
    /**@Author:WenDong */ 
    /**PieceType constructor */
    PieceType(int moveDir) {
        this.moveDir = moveDir;
    }
}