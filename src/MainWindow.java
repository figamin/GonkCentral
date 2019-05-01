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
        FXMLLoader loginLoad = new FXMLLoader(getClass().getResource("MainLayout.fxml"));
        Parent root = loginLoad.load();
        Stage stage = (Stage) starterButton.getScene().getWindow();
        stage.setTitle("Gonk Central");
        MainController control = loginLoad.getController();
        stage.setScene(new Scene(root, 600, 400));
        control.logIn(uname, pass, oauth);
    }

    public MainWindow(Button starterButton) throws IOException
    {
        FXMLLoader loginLoad = new FXMLLoader(getClass().getResource("MainLayout.fxml"));
        Parent root = loginLoad.load();
        Stage stage = (Stage) starterButton.getScene().getWindow();
        stage.setTitle("Gonk Central");
        MainController control = loginLoad.getController();
        stage.setScene(new Scene(root, 600, 400));
        control.logIn();
    }
}
