import edu.ksu.canvas.model.assignment.Assignment;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Ian Anderson
 * 4/12/19
 */

public class MainController {
    private CanvasInfoGetter cget;
    @FXML private Text ipassstu1, ipassstu2, ipassstu3, ipassstu4, ipassstu5, ipassstu6, ipassstu7, ipassstu8, ipassstu9, ipassstu10, ipassstu11;
    @FXML private PieChart attendpie;
    @FXML private Label piehover;
    @FXML private Text ped1, ped2, ped3, ped4, ped5, ped6, ped7;
    @FXML private Text monped1, monped2, monped3, monped4, monped5, monped6, monped7;
    @FXML private Text tueped1, tueped3, tueped4, tueped5, tueped6;
    @FXML private Text wendped2, wendped3, wendped4, wendped5, wendped6, wendped7;
    @FXML private Text thursped1, thursped2, thursped3, thursped5, thursped7;
    @FXML private Text friped1, friped2, friped4, friped5, friped6, friped7;
    @FXML private LineChart gradeline;
    @FXML private Tab canvastab1, canvastab2, canvastab3, canvastab4, canvastab5, canvastab6, canvastab7, canvastab8, canvastab9, canvastab10, canvastab11, canvastab12, canvastab13, canvastab14, canvastab15, canvastab16;
    @FXML private Text canvascrs1trm0, canvascrs1trm1, canvascrs1trm2, canvascrs1trm3, canvascrs1trm4, canvascrs1trm5, canvascrs1trm6;
    @FXML private Text canvascrs2trm0, canvascrs2trm1, canvascrs2trm2, canvascrs2trm3, canvascrs2trm4, canvascrs2trm5, canvascrs2trm6;
    @FXML private Text canvascrs3trm0, canvascrs3trm1, canvascrs3trm2, canvascrs3trm3, canvascrs3trm4, canvascrs3trm5, canvascrs3trm6;
    @FXML private Text canvascrs4trm0, canvascrs4trm1, canvascrs4trm2, canvascrs4trm3, canvascrs4trm4, canvascrs4trm5, canvascrs4trm6;
    @FXML private Text canvascrs5trm0, canvascrs5trm1, canvascrs5trm2, canvascrs5trm3, canvascrs5trm4, canvascrs5trm5, canvascrs5trm6;
    @FXML private Text canvascrs6trm0, canvascrs6trm1, canvascrs6trm2, canvascrs6trm3, canvascrs6trm4, canvascrs6trm5, canvascrs6trm6;
    @FXML private Text canvascrs7trm0, canvascrs7trm1, canvascrs7trm2, canvascrs7trm3, canvascrs7trm4, canvascrs7trm5, canvascrs7trm6;
    @FXML private Text canvascrs8trm0, canvascrs8trm1, canvascrs8trm2, canvascrs8trm3, canvascrs8trm4, canvascrs8trm5, canvascrs8trm6;
    @FXML private Text canvascrs9trm0, canvascrs9trm1, canvascrs9trm2, canvascrs9trm3, canvascrs9trm4, canvascrs9trm5, canvascrs9trm6;
    @FXML private Text canvascrs10trm0, canvascrs10trm1, canvascrs10trm2, canvascrs10trm3, canvascrs10trm4, canvascrs10trm5, canvascrs10trm6;
    @FXML private Text canvascrs11trm0, canvascrs11trm1, canvascrs11trm2, canvascrs11trm3, canvascrs11trm4, canvascrs11trm5, canvascrs11trm6;
    @FXML private Text canvascrs12trm0, canvascrs12trm1, canvascrs12trm2, canvascrs12trm3, canvascrs12trm4, canvascrs12trm5, canvascrs12trm6;
    @FXML private Text canvascrs13trm0, canvascrs13trm1, canvascrs13trm2, canvascrs13trm3, canvascrs13trm4, canvascrs13trm5, canvascrs13trm6;
    @FXML private Text canvascrs14trm0, canvascrs14trm1, canvascrs14trm2, canvascrs14trm3, canvascrs14trm4, canvascrs14trm5, canvascrs14trm6;
    @FXML private Text canvascrs15trm0, canvascrs15trm1, canvascrs15trm2, canvascrs15trm3, canvascrs15trm4, canvascrs15trm5, canvascrs15trm6;
    @FXML private Text canvascrs16trm0, canvascrs16trm1, canvascrs16trm2, canvascrs16trm3, canvascrs16trm4, canvascrs16trm5, canvascrs16trm6;
    @FXML private ListView<String> canvascrs1stud, canvascrs2stud, canvascrs3stud, canvascrs4stud, canvascrs5stud, canvascrs6stud, canvascrs7stud, canvascrs8stud, canvascrs9stud, canvascrs10stud, canvascrs11stud, canvascrs12stud, canvascrs13stud, canvascrs14stud, canvascrs15stud, canvascrs16stud;
    @FXML private ListView<String> canvascrs1assgn, canvascrs2assgn, canvascrs3assgn, canvascrs4assgn, canvascrs5assgn, canvascrs6assgn, canvascrs7assgn, canvascrs8assgn, canvascrs9assgn, canvascrs10assgn, canvascrs11assgn, canvascrs12assgn, canvascrs13assgn, canvascrs14assgn, canvascrs15assgn, canvascrs16assgn;
    @FXML private NumberAxis numAxis;
    @FXML private Text builddate;
    private List<List<String>> courseInfo;
    public void logIn(String username, String password, String oauth) throws IOException
    {
        builddate.setText("Built on " + LocalDate.now());
        new IPassLogin(username, password);
        Task<CanvasInfoGetter> canvas = new Task<CanvasInfoGetter>()
        {
            @Override
            protected CanvasInfoGetter call() throws Exception
            {
                return new CanvasInfoGetter(oauth);
            }

        };
        canvas.setOnSucceeded(e ->
        {
            cget = canvas.getValue();
            courseInfo = cget.getCourseInfo();
            setCanvasTabs();
            setCanvasStudents();
            setCanvasGrades();
            setCanvasAssignments();
        });
        Thread th = new Thread(canvas);
        th.setDaemon(true);
        th.start();
        numAxis.setForceZeroInRange(false);
        gradeline.setLegendSide(Side.RIGHT);
    }
    public void setBioText() throws IOException
    {
        Document parsedBio = Jsoup.parse(new URL("https://ipassweb.harrisschool.solutions/school/nsboro/istudentbio.htm"), 0);
        List<String> ageInfo = parsedBio.select(".DataMBl").eachText();
        List<String> bioText = parsedBio.select(".Datal").eachText();
        ipassstu1.setText(bioText.get(4));
        ipassstu2.setText(ageInfo.get(0));
        ipassstu3.setText(bioText.get(6));
        ipassstu4.setText(bioText.get(7));
        ipassstu5.setText(bioText.get(8));
        ipassstu6.setText(bioText.get(9));
        ipassstu7.setText(bioText.get(10));
        ipassstu8.setText(bioText.get(11));
        ipassstu9.setText(bioText.get(25));
        ipassstu10.setText(bioText.get(44));
        ipassstu11.setText(bioText.get(2));
    }
    public void setPieData() throws IOException
    {
        List<String> showedUp = Jsoup.parse(new URL("https://ipassweb.harrisschool.solutions/school/nsboro/samattendance.htm"), 0).select(".DataMBl").eachText();
        double daysShowed = Double.parseDouble(showedUp.get(1)) - Double.parseDouble(showedUp.get(5)) - Double.parseDouble(showedUp.get(6));
        ObservableList<PieChart.Data> attendPieData = FXCollections.observableArrayList(
                new PieChart.Data("Present", daysShowed),
                new PieChart.Data("Absent", Double.parseDouble(showedUp.get(4))),
                new PieChart.Data("Tardy", Double.parseDouble(showedUp.get(5))),
                new PieChart.Data("Dismissed", Double.parseDouble(showedUp.get(6))));
        attendpie.setData(attendPieData);
        for(PieChart.Data data: attendpie.getData())
        {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, e ->
            {
                piehover.setTranslateX(e.getX() - 60);
                piehover.setTranslateY(e.getY());
                piehover.setText(data.getPieValue() + " days");
            });
        }
    }
    public void setScheduleData() throws IOException
    {
        List<String> scheduleValues = Jsoup.parse(new URL("https://ipassweb.harrisschool.solutions/school/nsboro/samschedule.htm"), 0).select(".DATA").eachText();
        ped1.setText(scheduleValues.get(4) + "\n" + scheduleValues.get(5) + "\n" + scheduleValues.get(6));
        ped2.setText(scheduleValues.get(34) + "\n" + scheduleValues.get(35) + "\n" + scheduleValues.get(36));
        ped3.setText(scheduleValues.get(64) + "\n" + scheduleValues.get(65) + "\n" + scheduleValues.get(66));
        ped4.setText(scheduleValues.get(94) + "\n" + scheduleValues.get(95) + "\n" + scheduleValues.get(96));
        ped5.setText(scheduleValues.get(124) + "\n" + scheduleValues.get(125) + "\n" + scheduleValues.get(126));
        ped6.setText(scheduleValues.get(159) + "\n" + scheduleValues.get(160) + "\n" + scheduleValues.get(161));
        ped7.setText(scheduleValues.get(189) + "\n" + scheduleValues.get(190) + "\n" + scheduleValues.get(191));
        monped1.setText(scheduleValues.get(8));
        tueped1.setText(scheduleValues.get(15));
        thursped1.setText(scheduleValues.get(24));
        friped1.setText(scheduleValues.get(31));
        monped2.setText(scheduleValues.get(38));
        wendped2.setText(scheduleValues.get(47));
        thursped2.setText(scheduleValues.get(54));
        friped2.setText(scheduleValues.get(61));
        monped3.setText(scheduleValues.get(68));
        tueped3.setText(scheduleValues.get(75));
        wendped3.setText(scheduleValues.get(82));
        thursped3.setText(scheduleValues.get(89));
        monped4.setText(scheduleValues.get(98));
        tueped4.setText(scheduleValues.get(105));
        wendped4.setText(scheduleValues.get(112));
        friped4.setText(scheduleValues.get(121));
        monped5.setText(scheduleValues.get(128));
        tueped5.setText(scheduleValues.get(135));
        wendped5.setText(scheduleValues.get(142));
        thursped5.setText(scheduleValues.get(149));
        friped5.setText(scheduleValues.get(156));
        monped6.setText(scheduleValues.get(163));
        tueped6.setText(scheduleValues.get(170));
        wendped6.setText(scheduleValues.get(177));
        friped6.setText(scheduleValues.get(186));
        monped7.setText(scheduleValues.get(193));
        wendped7.setText(scheduleValues.get(202));
        thursped7.setText(scheduleValues.get(209));
        friped7.setText(scheduleValues.get(216));
    }
    public void setGradeChart() throws IOException
    {
        Document grades = Jsoup.parse(new URL("https://ipassweb.harrisschool.solutions/school/nsboro/samgrades.html"), 0);
        List<String> courses = grades.select(".data").eachText();
        List<String> letters = grades.select(".Datac").eachText();
        List<String> gradePeds = grades.select(".Labelc").eachText();
        int courseNum = (int) Math.round((courses.size() - 1) / 2.0);
        for(int i = 0; i < courseNum; i++)
        {
            XYChart.Series currentClassPoints = new XYChart.Series();
            currentClassPoints.setName(courses.get((i * 2) + 2).substring(0, 16));

            int counter = 3;
            int gradeValue = -10;
            for(int j = i * 12; j < (i * 12) + 10; j++)
            {
                if(!letters.get(j).isEmpty() || !letters.get(j).equals("&sp;"))
                {
                    switch (letters.get(j))
                    {
                        case "A+": gradeValue = 97;
                            break;
                        case "A": gradeValue = 93;
                            break;
                        case "A-": gradeValue = 90;
                            break;
                        case "B+": gradeValue = 87;
                            break;
                        case "B": gradeValue = 83;
                            break;
                        case "B-": gradeValue = 80;
                            break;
                        case "C+": gradeValue = 77;
                            break;
                        case "C": gradeValue = 73;
                            break;
                        case "C-": gradeValue = 70;
                            break;
                        case "D+": gradeValue = 67;
                            break;
                        case "D": gradeValue = 63;
                            break;
                        case "D-": gradeValue = 60;
                            break;
                        case "F": gradeValue = 50;
                            break;
                        }
                    if(gradeValue != -10)
                    {
                        String gradeToday = gradePeds.get(counter);
                        if (gradeToday.length() > 6)
                        {
                            gradeToday = gradeToday.substring(0, 8);
                        }
                        else
                        {
                            gradeToday = gradeToday.substring(0, 6);
                        }
                        if(!gradeToday.equals("Mid Year") && !gradeToday.equals("Final Ex"))
                        {
                            currentClassPoints.getData().add(new XYChart.Data(gradeToday, gradeValue));
                        }
                    }
                }
                counter++;
            }
            gradeline.getData().add(currentClassPoints);
        }
    }
    private void setCanvasAssignments()
    {
        List<List<Assignment>> assignments = cget.getCourseAssignments();
        List<List<String>> assignString = new ArrayList<>();
        for(int i = 0; i < assignments.size(); i++)
        {
            assignString.add(new ArrayList<>());
            for(int j = 0; j < assignments.get(i).size(); j++)
            {
                assignString.get(i).add("========================================");
                assignString.get(i).add("Name: " + assignments.get(i).get(j).getName());
                if(assignments.get(i).get(j).getPointsPossible() != null)
                {
                    assignString.get(i).add(assignments.get(i).get(j).getPointsPossible() + " points possible");
                }
                else
                {
                    assignString.get(i).add("No points possible.");
                }
                if(assignments.get(i).get(j).getDescription() != null)
                {
                    if(!assignments.get(i).get(j).getDescription().isEmpty())
                    {
                        String desc = Jsoup.clean(assignments.get(i).get(j).getDescription(), Whitelist.none());
                        assignString.get(i).add("Description: " + desc.replace("&nbsp;", "\n"));
                    }
                    else
                    {
                        assignString.get(i).add("No description.");
                    }
                }
                else
                {
                    assignString.get(i).add("No description.");
                }
                assignString.get(i).add("Created on: " + assignments.get(i).get(j).getCreatedAt().toString());
                if(assignments.get(i).get(j).getDueAt() != null)
                {
                    assignString.get(i).add("Due by: " + assignments.get(i).get(j).getDueAt().toString());
                }
                else
                {
                    assignString.get(i).add("No due date.");
                }
                assignString.get(i).add("========================================");
                assignString.get(i).add("\n\n");
            }

        }
        for(int i = 0; i < assignments.size(); i++)
        {
            ObservableList<String> currentStuds = FXCollections.observableArrayList();
            currentStuds.setAll(assignString.get(i));
            switch (i)
            {
                case 0: canvascrs1assgn.setItems(currentStuds);
                    break;
                case 1: canvascrs2assgn.setItems(currentStuds);
                    break;
                case 2: canvascrs3assgn.setItems(currentStuds);
                    break;
                case 3: canvascrs4assgn.setItems(currentStuds);
                    break;
                case 4: canvascrs5assgn.setItems(currentStuds);
                    break;
                case 5: canvascrs6assgn.setItems(currentStuds);
                    break;
                case 6: canvascrs7assgn.setItems(currentStuds);
                    break;
                case 7: canvascrs8assgn.setItems(currentStuds);
                    break;
                case 8: canvascrs9assgn.setItems(currentStuds);
                    break;
                case 9: canvascrs10assgn.setItems(currentStuds);
                    break;
                case 10: canvascrs11assgn.setItems(currentStuds);
                    break;
                case 11: canvascrs12assgn.setItems(currentStuds);
                    break;
                case 12: canvascrs13assgn.setItems(currentStuds);
                    break;
                case 13: canvascrs14assgn.setItems(currentStuds);
                    break;
                case 14: canvascrs15assgn.setItems(currentStuds);
                    break;
                case 15: canvascrs16assgn.setItems(currentStuds);
                    break;
            }
        }
    }
    private void setCanvasTabs()
    {
        for(int i = 0; i < courseInfo.size(); i++)
        {
            switch (i)
            {
                case 0: canvastab1.setText(courseInfo.get(i).get(0));
                    break;
                case 1: canvastab2.setText(courseInfo.get(i).get(0));
                    break;
                case 2: canvastab3.setText(courseInfo.get(i).get(0));
                    break;
                case 3: canvastab4.setText(courseInfo.get(i).get(0));
                    break;
                case 4: canvastab5.setText(courseInfo.get(i).get(0));
                    break;
                case 5: canvastab6.setText(courseInfo.get(i).get(0));
                    break;
                case 6: canvastab7.setText(courseInfo.get(i).get(0));
                    break;
                case 7: canvastab8.setText(courseInfo.get(i).get(0));
                    break;
                case 8: canvastab9.setText(courseInfo.get(i).get(0));
                    break;
                case 9: canvastab10.setText(courseInfo.get(i).get(0));
                    break;
                case 10: canvastab11.setText(courseInfo.get(i).get(0));
                    break;
                case 11: canvastab12.setText(courseInfo.get(i).get(0));
                    break;
                case 12: canvastab13.setText(courseInfo.get(i).get(0));
                    break;
                case 13: canvastab14.setText(courseInfo.get(i).get(0));
                    break;
                case 14: canvastab15.setText(courseInfo.get(i).get(0));
                    break;
                case 15: canvastab16.setText(courseInfo.get(i).get(0));
                    break;
            }
        }
    }
    private void setCanvasStudents()
    {
        List<List<String>> studentList = cget.getStudentNames();
        for(int i = 0; i < studentList.size(); i++)
        {
            ObservableList<String> currentStuds = FXCollections.observableArrayList();
            currentStuds.setAll(studentList.get(i));
            switch (i)
            {
                case 0: canvascrs1stud.setItems(currentStuds);
                        break;
                case 1: canvascrs2stud.setItems(currentStuds);
                        break;
                case 2: canvascrs3stud.setItems(currentStuds);
                        break;
                case 3: canvascrs4stud.setItems(currentStuds);
                        break;
                case 4: canvascrs5stud.setItems(currentStuds);
                        break;
                case 5: canvascrs6stud.setItems(currentStuds);
                        break;
                case 6: canvascrs7stud.setItems(currentStuds);
                        break;
                case 7: canvascrs8stud.setItems(currentStuds);
                        break;
                case 8: canvascrs9stud.setItems(currentStuds);
                        break;
                case 9: canvascrs10stud.setItems(currentStuds);
                        break;
                case 10: canvascrs11stud.setItems(currentStuds);
                        break;
                case 11: canvascrs12stud.setItems(currentStuds);
                        break;
                case 12: canvascrs13stud.setItems(currentStuds);
                        break;
                case 13: canvascrs14stud.setItems(currentStuds);
                        break;
                case 14: canvascrs15stud.setItems(currentStuds);
                    break;
                case 15: canvascrs16stud.setItems(currentStuds);
                    break;
            }
        }
    }
    public void setCanvasGrades()
    {
        for(int j = 2; j <= 8; j++)
        {
            switch (j)
            {
                case 2:

                    if(courseInfo.get(0).size() > j)
                    {
                        canvascrs1trm0.setText(courseInfo.get(0).get(j));
                    }
                    if(courseInfo.get(1).size() > j)
                    {
                        canvascrs2trm0.setText(courseInfo.get(1).get(j));
                    }
                    if(courseInfo.get(2).size() > j)
                    {
                        canvascrs3trm0.setText(courseInfo.get(2).get(j));
                    }
                    if(courseInfo.get(3).size() > j)
                    {
                        canvascrs4trm0.setText(courseInfo.get(3).get(j));
                    }
                    if(courseInfo.get(4).size() > j)
                    {
                        canvascrs5trm0.setText(courseInfo.get(4).get(j));
                    }
                    if(courseInfo.get(5).size() > j)
                    {
                        canvascrs6trm0.setText(courseInfo.get(5).get(j));
                    }
                    if(courseInfo.get(6).size() > j)
                    {
                        canvascrs7trm0.setText(courseInfo.get(6).get(j));
                    }
                    if(courseInfo.get(7).size() > j)
                    {
                        canvascrs8trm0.setText(courseInfo.get(7).get(j));
                    }
                    if(courseInfo.get(8).size() > j)
                    {
                        canvascrs9trm0.setText(courseInfo.get(8).get(j));
                    }
                    if(courseInfo.get(9).size() > j)
                    {
                        canvascrs10trm0.setText(courseInfo.get(9).get(j));
                    }
                    if(courseInfo.get(10).size() > j)
                    {
                        canvascrs11trm0.setText(courseInfo.get(10).get(j));
                    }
                    if(courseInfo.get(11).size() > j)
                    {
                        canvascrs12trm0.setText(courseInfo.get(11).get(j));
                    }
                    if(courseInfo.get(12).size() > j)
                    {
                        canvascrs13trm0.setText(courseInfo.get(12).get(j));
                    }
                    if(courseInfo.get(13).size() > j)
                    {
                        canvascrs14trm0.setText(courseInfo.get(13).get(j));
                    }
                    if(courseInfo.get(14).size() > j)
                    {
                        canvascrs15trm0.setText(courseInfo.get(14).get(j));
                    }
                    if(courseInfo.size() > 15)
                    {
                        if(courseInfo.get(15).size() > j)
                        {
                            canvascrs16trm0.setText(courseInfo.get(15).get(j));
                        }
                    }
                        break;
                case 3:
                    if(courseInfo.get(0).size() > j)
                    {
                        canvascrs1trm1.setText(courseInfo.get(0).get(j));
                    }
                    if(courseInfo.get(1).size() > j)
                    {
                        canvascrs2trm1.setText(courseInfo.get(1).get(j));
                    }
                    if(courseInfo.get(2).size() > j)
                    {
                        canvascrs3trm1.setText(courseInfo.get(2).get(j));
                    }
                    if(courseInfo.get(3).size() > j)
                    {
                        canvascrs4trm1.setText(courseInfo.get(3).get(j));
                    }
                    if(courseInfo.get(4).size() > j)
                    {
                        canvascrs5trm1.setText(courseInfo.get(4).get(j));
                    }
                    if(courseInfo.get(5).size() > j)
                    {
                        canvascrs6trm1.setText(courseInfo.get(5).get(j));
                    }
                    if(courseInfo.get(6).size() > j)
                    {
                        canvascrs7trm1.setText(courseInfo.get(6).get(j));
                    }
                    if(courseInfo.get(7).size() > j)
                    {
                        canvascrs8trm1.setText(courseInfo.get(7).get(j));
                    }
                    if(courseInfo.get(8).size() > j)
                    {
                        canvascrs9trm1.setText(courseInfo.get(8).get(j));
                    }
                    if(courseInfo.get(9).size() > j)
                    {
                        canvascrs10trm1.setText(courseInfo.get(9).get(j));
                    }
                    if(courseInfo.get(10).size() > j)
                    {
                        canvascrs11trm1.setText(courseInfo.get(10).get(j));
                    }
                    if(courseInfo.get(11).size() > j)
                    {
                        canvascrs12trm1.setText(courseInfo.get(11).get(j));
                    }
                    if(courseInfo.get(12).size() > j)
                    {
                        canvascrs13trm1.setText(courseInfo.get(12).get(j));
                    }
                    if(courseInfo.get(13).size() > j)
                    {
                        canvascrs14trm1.setText(courseInfo.get(13).get(j));
                    }
                    if(courseInfo.get(14).size() > j)
                    {
                        canvascrs15trm1.setText(courseInfo.get(14).get(j));
                    }
                    if(courseInfo.size() > 15)
                    {
                        if(courseInfo.get(15).size() > j)
                        {
                            canvascrs16trm1.setText(courseInfo.get(15).get(j));
                        }
                    }
                    break;
                case 4:
                    if(courseInfo.get(0).size() > j)
                    {
                        canvascrs1trm2.setText(courseInfo.get(0).get(j));
                    }
                    if(courseInfo.get(1).size() > j)
                    {
                        canvascrs2trm2.setText(courseInfo.get(1).get(j));
                    }
                    if(courseInfo.get(2).size() > j)
                    {
                        canvascrs3trm2.setText(courseInfo.get(2).get(j));
                    }
                    if(courseInfo.get(3).size() > j)
                    {
                        canvascrs4trm2.setText(courseInfo.get(3).get(j));
                    }
                    if(courseInfo.get(4).size() > j)
                    {
                        canvascrs5trm2.setText(courseInfo.get(4).get(j));
                    }
                    if(courseInfo.get(5).size() > j)
                    {
                        canvascrs6trm2.setText(courseInfo.get(5).get(j));
                    }
                    if(courseInfo.get(6).size() > j)
                    {
                        canvascrs7trm2.setText(courseInfo.get(6).get(j));
                    }
                    if(courseInfo.get(7).size() > j)
                    {
                        canvascrs8trm2.setText(courseInfo.get(7).get(j));
                    }
                    if(courseInfo.get(8).size() > j)
                    {
                        canvascrs9trm2.setText(courseInfo.get(8).get(j));
                    }
                    if(courseInfo.get(9).size() > j)
                    {
                        canvascrs10trm2.setText(courseInfo.get(9).get(j));
                    }
                    if(courseInfo.get(10).size() > j)
                    {
                        canvascrs11trm2.setText(courseInfo.get(10).get(j));
                    }
                    if(courseInfo.get(11).size() > j)
                    {
                        canvascrs12trm2.setText(courseInfo.get(11).get(j));
                    }
                    if(courseInfo.get(12).size() > j)
                    {
                        canvascrs13trm2.setText(courseInfo.get(12).get(j));
                    }
                    if(courseInfo.get(13).size() > j)
                    {
                        canvascrs14trm2.setText(courseInfo.get(13).get(j));
                    }
                    if(courseInfo.get(14).size() > j)
                    {
                        canvascrs15trm2.setText(courseInfo.get(14).get(j));
                    }
                    if(courseInfo.size() > 15)
                    {
                        if(courseInfo.get(15).size() > j)
                        {
                            canvascrs16trm2.setText(courseInfo.get(15).get(j));
                        }
                    }
                    break;
                case 5:
                    if(courseInfo.get(0).size() > j)
                    {
                        canvascrs1trm3.setText(courseInfo.get(0).get(j));
                    }
                    if(courseInfo.get(1).size() > j)
                    {
                        canvascrs2trm3.setText(courseInfo.get(1).get(j));
                    }
                    if(courseInfo.get(2).size() > j)
                    {
                        canvascrs3trm3.setText(courseInfo.get(2).get(j));
                    }
                    if(courseInfo.get(3).size() > j)
                    {
                        canvascrs4trm3.setText(courseInfo.get(3).get(j));
                    }
                    if(courseInfo.get(4).size() > j)
                    {
                        canvascrs5trm3.setText(courseInfo.get(4).get(j));
                    }
                    if(courseInfo.get(5).size() > j)
                    {
                        canvascrs6trm3.setText(courseInfo.get(5).get(j));
                    }
                    if(courseInfo.get(6).size() > j)
                    {
                        canvascrs7trm3.setText(courseInfo.get(6).get(j));
                    }
                    if(courseInfo.get(7).size() > j)
                    {
                        canvascrs8trm3.setText(courseInfo.get(7).get(j));
                    }
                    if(courseInfo.get(8).size() > j)
                    {
                        canvascrs9trm3.setText(courseInfo.get(8).get(j));
                    }
                    if(courseInfo.get(9).size() > j)
                    {
                        canvascrs10trm3.setText(courseInfo.get(9).get(j));
                    }
                    if(courseInfo.get(10).size() > j)
                    {
                        canvascrs11trm3.setText(courseInfo.get(10).get(j));
                    }
                    if(courseInfo.get(11).size() > j)
                    {
                        canvascrs12trm3.setText(courseInfo.get(11).get(j));
                    }
                    if(courseInfo.get(12).size() > j)
                    {
                        canvascrs13trm3.setText(courseInfo.get(12).get(j));
                    }
                    if(courseInfo.get(13).size() > j)
                    {
                        canvascrs14trm3.setText(courseInfo.get(13).get(j));
                    }
                    if(courseInfo.get(14).size() > j)
                    {
                        canvascrs15trm3.setText(courseInfo.get(14).get(j));
                    }
                    if(courseInfo.size() > 15)
                    {
                        if(courseInfo.get(15).size() > j)
                        {
                            canvascrs16trm3.setText(courseInfo.get(15).get(j));
                        }
                    }
                    break;
                case 6:
                    if(courseInfo.get(0).size() > j)
                    {
                        canvascrs1trm4.setText(courseInfo.get(0).get(j));
                    }
                    if(courseInfo.get(1).size() > j)
                    {
                        canvascrs2trm4.setText(courseInfo.get(1).get(j));
                    }
                    if(courseInfo.get(2).size() > j)
                    {
                        canvascrs3trm4.setText(courseInfo.get(2).get(j));
                    }
                    if(courseInfo.get(3).size() > j)
                    {
                        canvascrs4trm4.setText(courseInfo.get(3).get(j));
                    }
                    if(courseInfo.get(4).size() > j)
                    {
                        canvascrs5trm4.setText(courseInfo.get(4).get(j));
                    }
                    if(courseInfo.get(5).size() > j)
                    {
                        canvascrs6trm4.setText(courseInfo.get(5).get(j));
                    }
                    if(courseInfo.get(6).size() > j)
                    {
                        canvascrs7trm4.setText(courseInfo.get(6).get(j));
                    }
                    if(courseInfo.get(7).size() > j)
                    {
                        canvascrs8trm4.setText(courseInfo.get(7).get(j));
                    }
                    if(courseInfo.get(8).size() > j)
                    {
                        canvascrs9trm4.setText(courseInfo.get(8).get(j));
                    }
                    if(courseInfo.get(9).size() > j)
                    {
                        canvascrs10trm4.setText(courseInfo.get(9).get(j));
                    }
                    if(courseInfo.get(10).size() > j)
                    {
                        canvascrs11trm4.setText(courseInfo.get(10).get(j));
                    }
                    if(courseInfo.get(11).size() > j)
                    {
                        canvascrs12trm4.setText(courseInfo.get(11).get(j));
                    }
                    if(courseInfo.get(12).size() > j)
                    {
                        canvascrs13trm4.setText(courseInfo.get(12).get(j));
                    }
                    if(courseInfo.get(13).size() > j)
                    {
                        canvascrs14trm4.setText(courseInfo.get(13).get(j));
                    }
                    if(courseInfo.get(14).size() > j)
                    {
                        canvascrs15trm4.setText(courseInfo.get(14).get(j));
                    }
                    if(courseInfo.size() > 15)
                    {
                        if(courseInfo.get(15).size() > j)
                        {
                            canvascrs16trm4.setText(courseInfo.get(15).get(j));
                        }
                    }
                    break;
                case 7:
                    if(courseInfo.get(0).size() > j)
                    {
                        canvascrs1trm5.setText(courseInfo.get(0).get(j));
                    }
                    if(courseInfo.get(1).size() > j)
                    {
                        canvascrs2trm5.setText(courseInfo.get(1).get(j));
                    }
                    if(courseInfo.get(2).size() > j)
                    {
                        canvascrs3trm5.setText(courseInfo.get(2).get(j));
                    }
                    if(courseInfo.get(3).size() > j)
                    {
                        canvascrs4trm5.setText(courseInfo.get(3).get(j));
                    }
                    if(courseInfo.get(4).size() > j)
                    {
                        canvascrs5trm5.setText(courseInfo.get(4).get(j));
                    }
                    if(courseInfo.get(5).size() > j)
                    {
                        canvascrs6trm5.setText(courseInfo.get(5).get(j));
                    }
                    if(courseInfo.get(6).size() > j)
                    {
                        canvascrs7trm5.setText(courseInfo.get(6).get(j));
                    }
                    if(courseInfo.get(7).size() > j)
                    {
                        canvascrs8trm5.setText(courseInfo.get(7).get(j));
                    }
                    if(courseInfo.get(8).size() > j)
                    {
                        canvascrs9trm5.setText(courseInfo.get(8).get(j));
                    }
                    if(courseInfo.get(9).size() > j)
                    {
                        canvascrs10trm5.setText(courseInfo.get(9).get(j));
                    }
                    if(courseInfo.get(10).size() > j)
                    {
                        canvascrs11trm5.setText(courseInfo.get(10).get(j));
                    }
                    if(courseInfo.get(11).size() > j)
                    {
                        canvascrs12trm5.setText(courseInfo.get(11).get(j));
                    }
                    if(courseInfo.get(12).size() > j)
                    {
                        canvascrs13trm5.setText(courseInfo.get(12).get(j));
                    }
                    if(courseInfo.get(13).size() > j)
                    {
                        canvascrs14trm5.setText(courseInfo.get(13).get(j));
                    }
                    if(courseInfo.get(14).size() > j)
                    {
                        canvascrs15trm5.setText(courseInfo.get(14).get(j));
                    }
                    if(courseInfo.size() > 15)
                    {
                        if(courseInfo.get(15).size() > j)
                        {
                            canvascrs16trm5.setText(courseInfo.get(15).get(j));
                        }
                    }
                    break;
                case 8:
                    if(courseInfo.get(0).size() > j)
                    {
                        canvascrs1trm6.setText(courseInfo.get(0).get(j));
                    }
                    if(courseInfo.get(1).size() > j)
                    {
                        canvascrs2trm6.setText(courseInfo.get(1).get(j));
                    }
                    if(courseInfo.get(2).size() > j)
                    {
                        canvascrs3trm6.setText(courseInfo.get(2).get(j));
                    }
                    if(courseInfo.get(3).size() > j)
                    {
                        canvascrs4trm6.setText(courseInfo.get(3).get(j));
                    }
                    if(courseInfo.get(4).size() > j)
                    {
                        canvascrs5trm6.setText(courseInfo.get(4).get(j));
                    }
                    if(courseInfo.get(5).size() > j)
                    {
                        canvascrs6trm6.setText(courseInfo.get(5).get(j));
                    }
                    if(courseInfo.get(6).size() > j)
                    {
                        canvascrs7trm6.setText(courseInfo.get(6).get(j));
                    }
                    if(courseInfo.get(7).size() > j)
                    {
                        canvascrs8trm6.setText(courseInfo.get(7).get(j));
                    }
                    if(courseInfo.get(8).size() > j)
                    {
                        canvascrs9trm6.setText(courseInfo.get(8).get(j));
                    }
                    if(courseInfo.get(9).size() > j)
                    {
                        canvascrs10trm6.setText(courseInfo.get(9).get(j));
                    }
                    if(courseInfo.get(10).size() > j)
                    {
                        canvascrs11trm6.setText(courseInfo.get(10).get(j));
                    }
                    if(courseInfo.get(11).size() > j)
                    {
                        canvascrs12trm6.setText(courseInfo.get(11).get(j));
                    }
                    if(courseInfo.get(12).size() > j)
                    {
                        canvascrs13trm6.setText(courseInfo.get(12).get(j));
                    }
                    if(courseInfo.get(13).size() > j)
                    {
                        canvascrs14trm6.setText(courseInfo.get(13).get(j));
                    }
                    if(courseInfo.get(14).size() > j)
                    {
                        canvascrs15trm6.setText(courseInfo.get(14).get(j));
                    }
                    if(courseInfo.size() > 15)
                    {
                        if(courseInfo.get(15).size() > j)
                        {
                            canvascrs16trm6.setText(courseInfo.get(15).get(j));
                        }
                    }
                    break;
            }
        }

    }
}
