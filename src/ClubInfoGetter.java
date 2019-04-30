/**
 * Ian Anderson
 * 4/24/19
 */
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public final class ClubInfoGetter
{
    public static List<List<String>> getInfo() throws IOException
    {
        List<List<String>> clubInfo = new ArrayList<>();
        Document parsedClubs = Jsoup.parse(new URL("https://www.nsboro.k12.ma.us/Page/796"), 0);
        Elements clubLinks = parsedClubs.select("a");
        List<String> clubNames = parsedClubs.select("a").eachText();
        int i = 90;
        while (!clubNames.get(i - 4).equals("rmoulton@nsboro.k12.ma.us"))
        {
            clubInfo.add(new ArrayList<>());
            String clubLink = clubLinks.get(i).attr("abs:href");
            clubInfo.get(i - 90).add(clubNames.get(i - 4));
            Document currentClub = Jsoup.parse(new URL(clubLink), 0);
            if(clubLink.equals("https://www.nsboro.k12.ma.us/Page/1384"))
            {
                currentClub.select("div").after("\\n");
                String fullText = currentClub.text().replace("\\n", "\n");
                clubInfo.get(i - 90).add(fullText.substring(fullText.indexOf("Advisor"), fullText.indexOf("Instagram!")) + "Instagram!");
            }
            else {
                currentClub.select("br").after("\\n");
                currentClub.select("p").append("\\n");
                String formattedText = currentClub.select("p")
                        .text()
                        .replace("\\n", "\n")
                        .replace("79 Bartlett Street, Northborough, MA 01532\n" +
                                " 508-351-7010 \n" +
                                " 508-393-9226", "")
                        .trim();
                clubInfo.get(i - 90).add(formattedText);
            }
            clubInfo.get(i - 90).add(clubLink);
            i++;
        }
        return clubInfo;
    }
}

