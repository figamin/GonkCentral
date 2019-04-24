import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Ian Anderson
 * 4/21/19
 */

public class ClubGet {
    public static void main(String[] args) throws IOException
    {
        Document parsedClubs = Jsoup.parse(new URL("https://www.nsboro.k12.ma.us/Page/796"), 0);
        Elements clubLinks = parsedClubs.select("a");
        List<String> clubNames = parsedClubs.select("a").eachText();
        System.out.println("Clubs");
        System.out.println("====================");
        for (int i = 0; i < clubLinks.size(); i++)
        {
            if(i >= 4 && clubNames.get(i - 4).equals("rmoulton@nsboro.k12.ma.us"))
            {
                break;
            }
            else if(i >= 90)
            {
                String clubLink = clubLinks.get(i).attr("abs:href");
                System.out.println(clubNames.get(i - 4));
                Document currentClub = Jsoup.parse(new URL(clubLink), 0);
                if(clubLink.equals("https://www.nsboro.k12.ma.us/Page/1384"))
                {
                    currentClub.select("div").after("\\n");
                    String fullText = currentClub.text().replace("\\n", "\n");
                    System.out.println(fullText.substring(fullText.indexOf("Advisor"), fullText.indexOf("Instagram!")) + "Instagram!");
                }
                else
                {
                    currentClub.select("br").after("\\n");
                    currentClub.select("p").append("\\n");
                    String formattedText = currentClub.select("p")
                            .text()
                            .replace("\\n", "\n")
                            .replace("79 Bartlett Street, Northborough, MA 01532\n" +
                                    " 508-351-7010 \n" +
                                    " 508-393-9226", "")
                            .trim();
                    System.out.println(formattedText);
                }
                System.out.println(clubLink);
                System.out.println("======================");
            }
        }
    }
}
