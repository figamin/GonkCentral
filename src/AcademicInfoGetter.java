import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Ian Anderson
 * 4/30/19
 */

public final class AcademicInfoGetter {

    public static List<List<String>> getInfo() throws IOException
    {
        List<List<String>> academicInfo = new ArrayList<>();
        Document parsedDepartments = Jsoup.parse(new URL("https://www.nsboro.k12.ma.us/Page/143"), 0);
        Elements academicLinks = parsedDepartments.select("a");
        List<String> academicNames = parsedDepartments.select("a").eachText();
        int i = 90;
        while (!academicNames.get(i - 4).equals("facebook"))
        {
            academicInfo.add(new ArrayList<>());
            String departmentLink = academicLinks.get(i).attr("abs:href");
            academicInfo.get(i - 90).add(academicNames.get(i - 4));
            Document currentDepartment = Jsoup.parse(new URL(departmentLink), 0);
            currentDepartment.select("br").after("\\n");
            currentDepartment.select("p").append("\\n");
            String formattedText = currentDepartment.select("p")
                    .text()
                    .replace("\\n", "\n")
                    .replace("79 Bartlett Street, Northborough, MA 01532\n" +
                            " 508-351-7010 \n" +
                            " 508-393-9226", "")
                    .trim();
            academicInfo.get(i - 90).add(formattedText);
            academicInfo.get(i - 90).add(departmentLink);
            i++;
        }
        return academicInfo;
    }
}
