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
        Document parsedClubs = Jsoup.parse(new URL("https://www.nsboro.k12.ma.us/Page/796"),0);
        Elements clubLinks = parsedClubs.select("a");
        List<String> clubNames = parsedClubs.select("a").eachText();
        System.out.println("Clubs");
        for(int i = 0; i < clubLinks.size(); i++)
        {
            if(i >= 90)
            {
                String clubLink = clubLinks.get(i).attr("abs:href");
                System.out.println(clubNames.get(i - 4));
                System.out.println(clubLink);
                if(clubLink.equals(""))
                {
                    break;
                }
            }
        }
    }
}
