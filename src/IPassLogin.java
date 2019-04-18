import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Ian Anderson
 * 4/10/19
 */

public class IPassLogin {
    public IPassLogin(String username, String password) throws IOException
    {
        CookieManager cookieMan = new CookieManager();
        CookieHandler.setDefault(cookieMan);
        URL loginPage = new URL("https://ipassweb.harrisschool.solutions/school/nsboro/syslogin.htm");
        HttpURLConnection connection = (HttpURLConnection) loginPage.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.connect();
        OutputStreamWriter fillItIn = new OutputStreamWriter(connection.getOutputStream());
        fillItIn.write("userid=" + username + "&password=" + password);
        fillItIn.close();
        connection.getContent();
    }

    public String scrapePage(URL currentPage) throws IOException
    {
        HttpURLConnection linkup = (HttpURLConnection) currentPage.openConnection();
        BufferedReader pageReader = new BufferedReader(new InputStreamReader(linkup.getInputStream()));
        String currentLine;
        StringBuilder pageMaker = new StringBuilder();
        while ((currentLine = pageReader.readLine()) != null)
        {
            pageMaker.append(currentLine);
        }
        return pageMaker.toString();
    }
}
