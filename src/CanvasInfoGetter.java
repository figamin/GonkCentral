import edu.ksu.canvas.CanvasApiFactory;
import edu.ksu.canvas.interfaces.AssignmentReader;
import edu.ksu.canvas.interfaces.CourseReader;
import edu.ksu.canvas.interfaces.EnrollmentReader;
import edu.ksu.canvas.interfaces.UserReader;
import edu.ksu.canvas.model.Course;
import edu.ksu.canvas.model.Enrollment;
import edu.ksu.canvas.model.Grade;
import edu.ksu.canvas.model.User;
import edu.ksu.canvas.model.assignment.Assignment;
import edu.ksu.canvas.oauth.NonRefreshableOauthToken;
import edu.ksu.canvas.oauth.OauthToken;
import edu.ksu.canvas.requestOptions.GetEnrollmentOptions;
import edu.ksu.canvas.requestOptions.GetUsersInCourseOptions;
import edu.ksu.canvas.requestOptions.ListCourseAssignmentsOptions;
import edu.ksu.canvas.requestOptions.ListCurrentUserCoursesOptions;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Ian Anderson
 * 4/15/19
 */

public class CanvasInfoGetter {
    //list of classes, lists of info(info, startdate, overall grade, grades by term)
    private List<List<String>> courseInfo;
    //list of classes, lists of assignments
    private List<List<Assignment>> courseAssignments;
    //list of classes, lists of names
    private List<List<String>> studentNames;
    public CanvasInfoGetter(String authToken) throws IOException{
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.OFF);
        String canvasUrl = "https://nsboro.instructure.com";
        OauthToken oauthToken = new NonRefreshableOauthToken(authToken);
        CanvasApiFactory apiFactory = new CanvasApiFactory(canvasUrl);
        EnrollmentReader enrollmentReader = apiFactory.getReader(EnrollmentReader.class, oauthToken, 100);
        CourseReader courseReader = apiFactory.getReader(CourseReader.class, oauthToken, 100);
        UserReader userReader = apiFactory.getReader(UserReader.class, oauthToken, 100);
        AssignmentReader assignmentReader = apiFactory.getReader(AssignmentReader.class, oauthToken, 100);
        List<Course> courses = courseReader.listCurrentUserCourses(new ListCurrentUserCoursesOptions());
        List<String> courseNames = new ArrayList<>();
        List<Integer> courseIds = new ArrayList<>();
        List<Date> courseStartDates = new ArrayList<>();
        for (Course crs : courses)
        {
            courseNames.add(crs.getName());
            courseIds.add(crs.getId());
            courseStartDates.add(crs.getStartAt());
        }
        GetEnrollmentOptions options = new GetEnrollmentOptions("self");
        List<Enrollment> enrollments = enrollmentReader.getUserEnrollments(options);
        courseInfo = new ArrayList<>();
        courseAssignments = new ArrayList<>();
        studentNames = new ArrayList<>();
        for (int k = 0; k < enrollments.size(); k++)
        {
            courseInfo.add(new ArrayList<>());
            courseAssignments.add(new ArrayList<>());
            studentNames.add(new ArrayList<>());
            int idIndex = courseIds.indexOf(enrollments.get(k).getCourseId());
            if (idIndex != -1)
            {
                courseInfo.get(k).add(courseNames.get(idIndex));
                if (courseStartDates.get(idIndex) != null)
                {
                    courseInfo.get(k).add(courseStartDates.get(idIndex).toString());
                }
                Grade classGrade = enrollments.get(k).getGrades();
                if(classGrade != null)
                {
                    if (classGrade.getCurrentScore() != null)
                    {
                        //overall score
                        courseInfo.get(k).add(classGrade.getCurrentScore());
                    }
                }
                if (enrollments.get(k).getCourseId() != 751 && enrollments.get(k).getCourseId() != 673)
                {
                    ListCourseAssignmentsOptions test = new ListCourseAssignmentsOptions(enrollments.get(k).getCourseId().toString());
                    test.overrideAssignmentDates(false);
                    courseAssignments.add(assignmentReader.listCourseAssignments(test));
                    List<User> students = userReader.getUsersInCourse(new GetUsersInCourseOptions(enrollments.get(k).getCourseId().toString()).enrollmentType(Arrays.asList(GetUsersInCourseOptions.EnrollmentType.STUDENT)));
                    for (User currentStudent : students)
                    {
                        studentNames.get(k).add(currentStudent.getName());
                    }
                    List<User> tas = userReader.getUsersInCourse(new GetUsersInCourseOptions(enrollments.get(k).getCourseId().toString()).enrollmentType(Arrays.asList(GetUsersInCourseOptions.EnrollmentType.TA)));
                    for (User currentTeacher : tas)
                    {
                        studentNames.get(k).add("TA " + currentTeacher.getName());
                    }
                    List<User> teachers = userReader.getUsersInCourse(new GetUsersInCourseOptions(enrollments.get(k).getCourseId().toString()).enrollmentType(Arrays.asList(GetUsersInCourseOptions.EnrollmentType.OBSERVER, GetUsersInCourseOptions.EnrollmentType.TEACHER, GetUsersInCourseOptions.EnrollmentType.DESIGNER)));
                    for (User currentTeacher : teachers)
                    {
                        studentNames.get(k).add("TEACHER " + currentTeacher.getName());
                    }
                }
            }
        }
        for(int i = 21; i <= 26; i++)
        {
            for(int j = i; j <= i + 6; j += 6)
            {
                options.gradingPeriodId(j);
                List<Enrollment> gradeTest = enrollmentReader.getUserEnrollments(options);
                if (gradeTest.get(j - 21).getCourseId() != 751 && gradeTest.get(j - 21).getCourseId() != 673)
                {
                    for(int k = 0; k < courseInfo.size(); k++)
                    {
                        Grade classGrade = gradeTest.get(k).getGrades();
                        if (classGrade != null)
                        {
                            if (classGrade.getCurrentScore() != null)
                            {
                                courseInfo.get(k).add(classGrade.getCurrentScore());
                            }
                        }
                    }
                }
            }
        }
    }
    public List<List<String>> getCourseInfo()
    {
        return courseInfo;
    }
    public List<List<Assignment>> getCourseAssignments()
    {
        return courseAssignments;
    }
    public List<List<String>> getStudentNames()
    {
        return studentNames;
    }
}

