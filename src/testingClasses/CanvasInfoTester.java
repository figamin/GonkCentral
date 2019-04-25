
import edu.ksu.canvas.model.assignment.Assignment;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.io.IOException;
import java.util.List;

/**
 * Ian Anderson
 * 4/18/19
 */

public class CanvasInfoTester {
    public static void main(String[] args) throws IOException
    {
        long time = System.currentTimeMillis();
        CanvasInfoGetter canIn = new CanvasInfoGetter("9522~l72NXLBu6py0n0rZsiAeAjFPIa4IVoHJKgLgeTWyZL553qOVnkbiSgkrEcZo7vZ6");
        List<List<String>> courseInfo = canIn.getCourseInfo();
        List<List<Assignment>> assignInfo = canIn.getCourseAssignments();
        List<List<String>> studentNames = canIn.getStudentNames();

        for(int i = 0; i < courseInfo.size(); i++)
        {
            System.out.println("INFO COURSE " + i);
            List<String> currentCourse = courseInfo.get(i);
            for(String str: currentCourse)
            {
                System.out.println(str);
            }
            System.out.println();
        }
        for(int i = 0; i < courseInfo.size(); i++)
        {
            System.out.println("STUDENT COURSE " + i);
            List<String> currentCourse = studentNames.get(i);
            for(String str: currentCourse)
            {
                System.out.println(str);
            }
            System.out.println();
        }
        for(int i = 0; i < courseInfo.size(); i++)
        {
            System.out.println("ASSIGNMENT COURSE " + i);
            List<Assignment> currentCourse = assignInfo.get(i);
            for(Assignment asr: currentCourse)
            {
                System.out.println("ASSIGNMENT " + asr.getName());
                if(asr.getPointsPossible() != null)
                {
                    System.out.println(asr.getPointsPossible() + " POINTS POSSIBLE");
                }
                else
                {
                    System.out.println("NO POINTS POSSIBLE");
                }
                String[] extensions = asr.getAllowedExtensions();
                if(extensions != null)
                {
                    System.out.println("ALLOWED FILE EXTENSIONS");
                    for(String str: extensions)
                    {
                        System.out.println(str);
                    }
                }
                if (asr.getCreatedAt() != null)
                {
                    System.out.println("CREATED ON " + asr.getCreatedAt());
                }
                if (asr.getDueAt() != null) {
                    System.out.println("DUE BY " + asr.getDueAt());
                } else {
                    System.out.println("NO DUE DATE");
                }
                if (asr.getDescription() != null) {
                    if (asr.getDescription().isEmpty()) {
                        System.out.println("NO DESCRIPTION");
                    }
                    String desc = Jsoup.clean(asr.getDescription(), Whitelist.none());
                    //System.out.println(desc);
                    System.out.println(desc.replace("&nbsp;", "\n"));
                } else {
                    System.out.println("NO DESCRIPTION");
                }
                List<String> submitType = asr.getSubmissionTypes();
                System.out.println("SUBMIT TYPES");
                for (String str : submitType) {
                    switch (str) {
                        case "none":
                            System.out.println("NO TYPE SPECIFIED");
                            break;
                        case "not_graded":
                            System.out.println("ASSIGNMENT UNGRADED");
                            break;
                        case "on_paper":
                            System.out.println("TURN IN ON PAPER");
                            break;
                        case "online_upload":
                            System.out.println("UPLOAD FILES ONLINE");
                            break;
                        case "media_recording":
                            System.out.println("UPLOAD MEDIA ONLINE");
                            break;
                        case "online_quiz":
                            System.out.println("TAKE QUIZ ONLINE");
                            break;
                        case "discussion_topic":
                            System.out.println("REPLY TO DISCUSSION THREAD");
                            break;
                        case "online_text_entry":
                            System.out.println("TYPE IN CANVAS BOX");
                            break;
                        case "online_url":
                            System.out.println("SEND LINK");
                            break;
                        default:
                            System.out.println("INVALID TYPE!");
                            break;
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
        System.out.println("TOTAL TIME = " + ((System.currentTimeMillis() - time) / 1000.0) + " SECONDS");
    }
}
