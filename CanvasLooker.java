import edu.ksu.canvas.CanvasApiFactory;
import edu.ksu.canvas.interfaces.*;
import edu.ksu.canvas.model.*;
import edu.ksu.canvas.model.assignment.Assignment;
import edu.ksu.canvas.model.assignment.Quiz;
import edu.ksu.canvas.oauth.NonRefreshableOauthToken;
import edu.ksu.canvas.oauth.OauthToken;
import edu.ksu.canvas.oauth.OauthTokenRefresher;
import edu.ksu.canvas.oauth.RefreshableOauthToken;
import edu.ksu.canvas.requestOptions.*;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Ian Anderson
 * 4/15/19
 */

public class CanvasLooker {
    public static void main(String[] args) throws IOException
    {
        long time = System.currentTimeMillis();
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.OFF);
        String canvasUrl = "https://nsboro.instructure.com";
        // temp solution
        OauthToken oauthToken = new NonRefreshableOauthToken("9522~l72NXLBu6py0n0rZsiAeAjFPIa4IVoHJKgLgeTWyZL553qOVnkbiSgkrEcZo7vZ6");
        // need admin to get Oauth ClientID/Secret
        //OauthToken oauthToken = new RefreshableOauthToken(new OauthTokenRefresher())
        CanvasApiFactory apiFactory = new CanvasApiFactory(canvasUrl);
        /*QuizQuestionReader quizQuestionReader = apiFactory.getReader(QuizQuestionReader.class, oauthToken);
        List<QuizQuestion> econQuestions = quizQuestionReader.getQuizQuestions(new GetQuizQuestionsOptions("1346", 4719));
        for(QuizQuestion currentQuestion: econQuestions)
        {
            System.out.println(currentQuestion.getQuestionName());
            System.out.println(currentQuestion.getQuestionText());
            System.out.println();
        }*/
        CourseReader cread = apiFactory.getReader(CourseReader.class, oauthToken);
        List<Course> courses = cread.listCurrentUserCourses(new ListCurrentUserCoursesOptions());
        for(Course crs: courses)
        {
            System.out.println("COURSE NAME = " + crs.getName());
            //System.out.println("COURSE FULL NAME = " + crs.getCourseCode());
            System.out.println("COURSE ID = " + crs.getId());
            /*List<Enrollment> enrollments1 = crs.getEnrollments();
            if(enrollments1 != null)
            {
                if(!enrollments1.isEmpty())
                {
                    System.out.println("ENROLL SIZE = " + enrollments1.size());
                }
                else
                {
                    System.out.println("EMPTY!");
                }
            }
            else
            {
                System.out.println("NULLED!");
            }*/
            if(crs.getName() != null)
            {
                System.out.println("START DATE: " + crs.getStartAt().toString());
            }
            System.out.println();
        }
        EnrollmentReader enrollmentReader = apiFactory.getReader(EnrollmentReader.class, oauthToken);
        List<Enrollment> enrollments = enrollmentReader.getUserEnrollments(new GetEnrollmentOptions("self"));
        ArrayList<Integer> courseIds = new ArrayList<>();
        System.out.println("ENROLLMENTS OF USER");
        System.out.println();
        for(Enrollment theClass: enrollments)
        {
            //System.out.println("CLASS NAME = " + theClass.getTotalActivityTime());
            //System.out.println("CLASS ID = " + theClass.getId());
            courseIds.add(theClass.getCourseId());
            System.out.println("CLASS COURSE ID = " + theClass.getCourseId());
            //System.out.println("CLASS HTML URL = " + theClass.getHtmlUrl());
            Grade classGrade = theClass.getGrades();
            try
            {
                if(classGrade.getCurrentGrade() != null)
                {
                    System.out.println("CLASS CURRENT GRADE = " + classGrade.getCurrentGrade());
                }
                else
                {
                    System.out.println("NO LETTER GRADE");
                }
            }
            catch (NullPointerException e)
            {

            }
            try
            {
                if(classGrade.getCurrentScore() != null)
                {
                    System.out.println("CLASS CURRENT SCORE = " + classGrade.getCurrentScore());

                }
                else
                {
                    System.out.println("NO SCORE");
                }
            }
            catch (NullPointerException e)
            {

            }
            System.out.println();
        }

        /*PageReader page = apiFactory.getReader(PageReader.class, oauthToken);
        List<Page> econPages = page.listPagesInCourse("1346");
        for(Page thePage: econPages)
        {
            System.out.println(thePage.getTitle());
            System.out.println(thePage.getBody());
            System.out.println(thePage.getCreatedAt());
            System.out.println();
        }*/
        /*EnrollmentTermReader enrollmentTermReader = apiFactory.getReader(EnrollmentTermReader.class, oauthToken);
        List<EnrollmentTerm> termz = enrollmentTermReader.getEnrollmentTerms(new GetEnrollmentTermOptions("1886"));
        for(EnrollmentTerm trm: termz)
        {
            System.out.println(trm.getName());
            System.out.println(trm.getId());
            System.out.println(trm.getCreatedAt());
            System.out.println(trm.getEndAt());
            System.out.println();
        }*/




        /*CalendarReader calendarReader = apiFactory.getReader(CalendarReader.class, oauthToken);
        System.out.println("CALVENT = " + calendarReader.getCalendarEvent(1886).toString());
        ListCalendarEventsOptions options2 = new ListCalendarEventsOptions();
        options2.includeAllEvents(true);
        List<CalendarEvent> cals = calendarReader.listCalendarEvents("1886", options2);
        System.out.println("THE SIZE = " + cals.size());*/

        UserReader uread = apiFactory.getReader(UserReader.class, oauthToken);
        AssignmentReader assignmentReader = apiFactory.getReader(AssignmentReader.class, oauthToken);

        for(Integer num: courseIds)
        {
            if(num != 751 && num != 673)
            {
                /*List<Tab> tabEse = read.listAvailableCourseTabs(num.toString(), false);
                for(Tab theTab: tabEse)
                {
                    System.out.println("TAB = " + theTab.getLabel());
                    System.out.println(theTab.getType());
                }*/

                List<Assignment> assignments = assignmentReader.listCourseAssignments(new ListCourseAssignmentsOptions(num.toString()));
                for(Assignment assignment: assignments)
                {
                    System.out.println("ASSIGNMENT " + assignment.getName());
                    if(assignment.getDueAt() != null)
                    {
                        System.out.println("DUE BY " + assignment.getDueAt());
                    }
                    else
                    {
                        System.out.println("NO DUE DATE");
                    }
                    if(assignment.getDescription() != null)
                    {
                        if(assignment.getDescription().isEmpty())
                        {
                            System.out.println("NO DESCRIPTION");
                        }
                        System.out.println(Jsoup.clean(assignment.getDescription(), Whitelist.none()));
                    }
                    else
                    {
                        System.out.println("NO DESCRIPTION");
                    }
                    List<String> submitType = assignment.getSubmissionTypes();
                    System.out.println("SUBMIT TYPES");
                    for(String str: submitType)
                    {
                        switch (str)
                        {
                            case "none": System.out.println("NO TYPE SPECIFIED");
                            break;
                            case "not_graded": System.out.println("ASSIGNMENT UNGRADED");
                            break;
                            case "on_paper": System.out.println("TURN IN ON PAPER");
                            break;
                            case "online_upload": System.out.println("UPLOAD FILES ONLINE");
                            break;
                            case "media_recording": System.out.println("UPLOAD MEDIA ONLINE");
                            break;
                            case "online_quiz": System.out.println("TAKE QUIZ ONLINE");
                            break;
                            case "discussion_topic": System.out.println("REPLY TO DISCUSSION THREAD");
                            break;
                            case "online_text_entry": System.out.println("TYPE IN CANVAS BOX");
                            break;
                            case "online_url": System.out.println("SEND LINK");
                            break;
                            default: System.out.println("INVALID TYPE!");
                            break;
                        }
                    }
                    System.out.println();
                }

                List<User> users = uread.getUsersInCourse(new GetUsersInCourseOptions(num.toString()).enrollmentType(Arrays.asList(GetUsersInCourseOptions.EnrollmentType.STUDENT, GetUsersInCourseOptions.EnrollmentType.OBSERVER, GetUsersInCourseOptions.EnrollmentType.TEACHER, GetUsersInCourseOptions.EnrollmentType.DESIGNER)));
                for(User use: users)
                {
                    System.out.println("USER " + use.getName());
                    //System.out.println("USER ID = " + use.getId());
                    List<Enrollment> userEnrollments = use.getEnrollments();
                    if(userEnrollments != null)
                    {
                        System.out.println("NOT EMPTY");
                        for(Enrollment userClass: userEnrollments)
                        {
                            System.out.println("ID = " + userClass.getId());
                            System.out.println("COURSE ID = " + userClass.getCourseId());
                        }
                    }
                    System.out.println();
                }
            }
        }

        System.out.println(System.currentTimeMillis() - time);
    }
}
