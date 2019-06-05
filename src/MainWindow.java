import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Ian Anderson
 * 4/12/19
 */

public class MainWindow extends Stage{
    public MainWindow(Button starterButton, String uname, String pass, String oauth) throws IOException
    {
        MainController mControl = commonCode(starterButton);
        mControl.logIn(uname, pass, oauth);
    }

    public MainWindow(Button starterButton) throws IOException
    {
        MainController mControl = commonCode(starterButton);
        mControl.logIn();
    }

    private MainController commonCode(Button button) throws IOException
    {
        FXMLLoader loginLoad = new FXMLLoader(getClass().getResource("MainLayout.fxml"));
        Parent root = loginLoad.load();
        Stage stage = (Stage) button.getScene().getWindow();
        stage.setTitle("Gonk Central");
        MainController control = loginLoad.getController();
        Scene mainScene = new Scene(root, 600, 400);
        mainScene.widthProperty().addListener((observable, oldWidth, newWidth) -> control.resizeWidth(newWidth.doubleValue()));
        mainScene.heightProperty().addListener((observable, oldHeight, newHeight) -> control.resizeHeight(newHeight.doubleValue()));
        stage.setScene(mainScene);
        return control;
    }
}
