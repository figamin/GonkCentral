import edu.ksu.canvas.CanvasApiFactory;
import edu.ksu.canvas.exception.ObjectNotFoundException;
import edu.ksu.canvas.exception.UnauthorizedException;
import edu.ksu.canvas.interfaces.*;
import edu.ksu.canvas.model.*;
import edu.ksu.canvas.model.assignment.Assignment;
import edu.ksu.canvas.model.assignment.AssignmentGroup;
import edu.ksu.canvas.oauth.NonRefreshableOauthToken;
import edu.ksu.canvas.oauth.OauthToken;
import edu.ksu.canvas.requestOptions.*;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

/**
 * Ian Anderson
 * 4/15/19
 */

public class CleanedCanvasLooker {
    public static void main(String[] args) throws IOException {
        long time = System.currentTimeMillis();
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.OFF);
        String canvasUrl = "https://nsboro.instructure.com";
        OauthToken oauthToken = new NonRefreshableOauthToken("");
        CanvasApiFactory apiFactory = new CanvasApiFactory(canvasUrl);
        EnrollmentReader enrollmentReader = apiFactory.getReader(EnrollmentReader.class, oauthToken);
        CourseReader cread = apiFactory.getReader(CourseReader.class, oauthToken);
        UserReader uread = apiFactory.getReader(UserReader.class, oauthToken);
        AssignmentReader assignmentReader = apiFactory.getReader(AssignmentReader.class, oauthToken);
        List<Course> courses = cread.listCurrentUserCourses(new ListCurrentUserCoursesOptions());
        ArrayList<String> courseNames = new ArrayList<>();
        ArrayList<Integer> courseIds = new ArrayList<>();
        ArrayList<Date> courseStartDates = new ArrayList<>();
        for (Course crs : courses) {
            courseNames.add(crs.getName());
            courseIds.add(crs.getId());
            courseStartDates.add(crs.getStartAt());
        }
        GetEnrollmentOptions options = new GetEnrollmentOptions("self");
        int termCounter = 1;
        for(int i = 21; i <= 26; i++)
        {
            System.out.println();
            if(i - 20 == 3)
            {
                System.out.println("MIDTERMS");
            }
            else if(i - 20 == 6)
            {
                System.out.println("FINALS");
            }
            else
            {
                System.out.println("TERM " + termCounter);
                termCounter++;
            }

            System.out.println();
            for(int j = i; j <= i + 6; j += 6)
            {
                options.gradingPeriodId(j);
                List<Enrollment> gradeTest = enrollmentReader.getUserEnrollments(options);
                for(Enrollment enr: gradeTest)
                {
                        Grade classGrade = enr.getGrades();
                        if(classGrade != null)
                        {
                            if (classGrade.getCurrentScore() != null)
                            {
                                System.out.println("COURSE NAME = " + courseNames.get(courseIds.indexOf(enr.getCourseId())));
                                System.out.println("CURRENT TERM SCORE = " + classGrade.getCurrentScore());
                            }
                            else
                            {
                                //System.out.println("NO SCORE");
                            }
                        }
                }
            }
        }
        List<Enrollment> enrollments = enrollmentReader.getUserEnrollments(options);
        System.out.println("ENROLLMENTS OF USER");
        System.out.println();
        for (Enrollment theClass : enrollments) {
            int idIndex = courseIds.indexOf(theClass.getCourseId());
            if (idIndex != -1)
            {
                System.out.println("COURSE NAME = " + courseNames.get(idIndex));
                //System.out.println("COURSE ID = " + theClass.getCourseId());
                if (courseStartDates.get(idIndex) != null) {
                    System.out.println("COURSE START DATE = " + courseStartDates.get(idIndex).toString());
                }

                Grade classGrade = theClass.getGrades();
                if(classGrade != null) {
                    if (classGrade.getCurrentScore() != null) {
                        System.out.println("CURRENT OVERALL SCORE = " + classGrade.getCurrentScore());

                    } else {
                        System.out.println("NO SCORE");
                    }
                }
                if (theClass.getCourseId() != 751 && theClass.getCourseId() != 673) {
                    try
                    {
                        ListCourseAssignmentsOptions test = new ListCourseAssignmentsOptions(theClass.getCourseId().toString());
                        test.overrideAssignmentDates(false);
                        List<Assignment> assignments = assignmentReader.listCourseAssignments(test);
                        for (Assignment assignment : assignments)
                        {
                            System.out.println("ASSIGNMENT " + assignment.getName());
                            if(assignment.getPointsPossible() != null)
                            {
                                System.out.println(assignment.getPointsPossible() + " POINTS POSSIBLE");
                            }
                            else
                            {
                                System.out.println("NO POINTS POSSIBLE");
                            }
                            if (assignment.getDueAt() != null) {
                                System.out.println("DUE BY " + assignment.getDueAt());
                            } else {
                                System.out.println("NO DUE DATE");
                            }
                            if (assignment.getDescription() != null) {
                                if (assignment.getDescription().isEmpty()) {
                                    System.out.println("NO DESCRIPTION");
                                }
                                String desc = Jsoup.clean(assignment.getDescription(), Whitelist.none());
                                //System.out.println(desc);
                                System.out.println(desc.replace("&nbsp;", "\n"));
                            } else {
                                System.out.println("NO DESCRIPTION");
                            }
                            List<String> submitType = assignment.getSubmissionTypes();
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
                    }
                    catch (UnauthorizedException e)
                    {
                        e.printStackTrace();
                    }
                    try
                    {
                        List<User> students = uread.getUsersInCourse(new GetUsersInCourseOptions(theClass.getCourseId().toString()).enrollmentType(Arrays.asList(GetUsersInCourseOptions.EnrollmentType.STUDENT)));
                        for (User currentStudent : students) {
                            System.out.println("STUDENT " + currentStudent.getName());
                        }
                        List<User> tas = uread.getUsersInCourse(new GetUsersInCourseOptions(theClass.getCourseId().toString()).enrollmentType(Arrays.asList(GetUsersInCourseOptions.EnrollmentType.TA)));
                        for (User currentTeacher : tas) {
                            System.out.println("TA " + currentTeacher.getName());
                        }
                        List<User> teachers = uread.getUsersInCourse(new GetUsersInCourseOptions(theClass.getCourseId().toString()).enrollmentType(Arrays.asList(GetUsersInCourseOptions.EnrollmentType.OBSERVER, GetUsersInCourseOptions.EnrollmentType.TEACHER, GetUsersInCourseOptions.EnrollmentType.DESIGNER)));
                        for (User currentTeacher : teachers) {
                            System.out.println("TEACHER " + currentTeacher.getName());
                        }

                    }
                    catch (UnauthorizedException e)
                    {
                        e.printStackTrace();
                    }

                }
                else
                {
                    System.out.println("SCHOOLWIDE CLASS, INVALID");
                }
            }
            System.out.println();
        }
            System.out.println(System.currentTimeMillis() - time);
        }
    }

