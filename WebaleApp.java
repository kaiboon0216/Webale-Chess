import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Group;
import javafx.scene.transform.Translate;
import javafx.scene.control.Button;

/**@Author:KaiYuan */
/**WebaleApp class */
public class WebaleApp extends Application{ 
    public static final int WINDOW_WIDTH = 720;
    public static final int WINDOW_HEIGHT = 934;

    /**@Author:KaiYuan */
    /**Start the whole webaleApp*/
    @Override
    public void start(Stage stage) throws Exception{
        Parent root = GameBoard.initContent();

        Scene scene = new Scene(root);
        stage.setTitle("Webale Chess");
        stage.setScene(scene);
        stage.setMinWidth(WINDOW_WIDTH);
        stage.setMinHeight(WINDOW_HEIGHT);
        stage.setResizable(true);
        stage.show();
        
        // Window horizontal resize
        stage.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            // width difference to move, divide by 2 -> to center
            double tempWidth = ((double) newWidth - WINDOW_WIDTH)/2;
            double tempHeight = (stage.getHeight()-WINDOW_HEIGHT)/2;

            // Relocate the location of board group(0) and piece group(1)
            GameController.getBoardView().getStack().getChildren().get(0).relocate(tempWidth,tempHeight);
            GameController.getBoardView().getStack().getChildren().get(1).relocate(tempWidth,tempHeight);
        });

        // Window vertical resize
        stage.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            // height difference to move, divide by 2 -> to center
            double tempHeight = ((double) newHeight - WINDOW_HEIGHT)/2;
            double tempWidth = (stage.getWidth() - WINDOW_WIDTH)/2;

            // Relocate the location of board group(0) and piece group(1)
            GameController.getBoardView().getStack().getChildren().get(0).relocate(tempWidth,tempHeight);
            GameController.getBoardView().getStack().getChildren().get(1).relocate(tempWidth,tempHeight);
        });
    }

    /**@Author:KaiYuan */
    public static void main(String[] args){
        launch(args);
    }
}