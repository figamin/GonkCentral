import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Ian Anderson
 * 4/12/19
 */

public class MainWindow extends Stage{
    public MainWindow(Button starterButton, String uname, String pass) throws Exception
    {
        FXMLLoader loginLoad = new FXMLLoader(getClass().getResource("MainLayout.fxml"));
        Parent root = loginLoad.load();
        Stage stage = (Stage) starterButton.getScene().getWindow();
        Scene mainWindow = new Scene(root, 600, 400);
        stage.setTitle("Gonk Central");
        stage.setScene(mainWindow);
        MainController control = loginLoad.getController();
        control.logIn(uname, pass);
        control.setBioText();
        control.setPieData();
        control.setScheduleData();
        control.setGradeChart();
    }
}