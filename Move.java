/**Move Interface */
public interface Move { 

    /**@Author: Kelvin */
    /**Objects that implements this interface must override this movement method for their own movement. It is needed for
     * the implementation of Strategy Design Pattern
     */
    public MoveResult movement(int newX,int newY); 
}
