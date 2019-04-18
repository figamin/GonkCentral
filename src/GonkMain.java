import com.sun.javafx.stage.StageHelper;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Ian Anderson
 * 4/11/19
 */

public class GonkMain extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader loginLoad = new FXMLLoader(getClass().getResource("LoginLayout.fxml"));
        Parent root = loginLoad.load();
        primaryStage.setTitle("Gonk Central Login");
        Scene userLogin = new Scene(root, 500, 333);
        primaryStage.setScene(userLogin);
        primaryStage.show();
    }
}
