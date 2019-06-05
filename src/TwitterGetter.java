import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Ian Anderson
 * 5/10/19
 */

public class TwitterGetter {

    private Twitter twitterLogin;

    public TwitterGetter()
    {
        ConfigurationBuilder builden = new ConfigurationBuilder();
        builden.setOAuthConsumerKey("gCYACR7eCQspuSRp1OPJarRyJ");
        builden.setOAuthConsumerSecret("5SXXVPMYHPzq9RrSbLmiFguh2fpMQUSTnGFxNlbhSSvNnHZxDK");
        builden.setOAuthAccessToken("2259618392-nf9jsJ7Q21o109sl6I19lXo5UJtNr5ZtgF1UTVn");
        builden.setOAuthAccessTokenSecret("cjUx65iVOtoGsxkbYxCdM5tpjbGk1zfnfHOFK51aNfx0W");
        TwitterFactory tf = new TwitterFactory(builden.build());
        twitterLogin = tf.getInstance();
    }

    public List<List<String>> gonkSportsTweetGetter() throws TwitterException
    {
        List<List<String>> tweetList = new ArrayList<>();
        Paging p = new Paging();
        p.setCount(100);
        String[] gonkSportsAccounts = {"ARHSAthletics", "THawk_Football", "Thawk_Track", "Gonk_Tennis", "thawksoftball",
                "ARHSW", "gonkhoops", "algonquinvball", "GonkBaseball", "GonkGirlsLax", "GonkLax2k19"};
        for (int i = 0; i < gonkSportsAccounts.length; i++)
        {
            tweetList.add(new ArrayList<>());
            tweetList.get(i).add("@" + gonkSportsAccounts[i]);
            List<Status> currentTeamTweets = twitterLogin.getUserTimeline(gonkSportsAccounts[i], p);
            for (Status tweet : currentTeamTweets)
            {
                tweetList.get(i).add(tweet.getText() + " - " + tweet.getCreatedAt());
            }
        }
        return tweetList;
    }
}
