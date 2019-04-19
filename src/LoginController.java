import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;

/**
 * Ian Anderson
 * 4/11/19
 */

public class LoginController {
    @FXML private Text loginText;
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private Button loginpress;
    @FXML private String uname;
    @FXML private String pass;
    @FXML private boolean loggedIn = false;
    @FXML protected void buttonPress(ActionEvent e) throws IOException
    {
        uname = username.getText();
        pass = password.getText();
        IPassLogin letsGo = new IPassLogin(uname, pass);
        String pageTester = letsGo.scrapePage(new URL("https://ipassweb.harrisschool.solutions/school/nsboro/syslogin.htm"));
        if (pageTester.length() > 150)
        {
            loginText.setFill(Color.RED);
            loginText.setText("Invalid username/password.");
        }
        else
        {
            loginText.setFill(Color.GREEN);
            loginText.setText("Logging in...");
            try
            {
                new MainWindow(loginpress, uname, pass);
            }
            catch (Exception f)
            {
                f.printStackTrace();
            }
        }
    }
}