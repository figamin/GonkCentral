import edu.ksu.canvas.model.assignment.Assignment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

/**
 * Ian Anderson
 * 4/12/19
 */

public class MainController {
    private CanvasInfoGetter cget;
    @FXML
    private Text ipassstu1, ipassstu2, ipassstu3, ipassstu4, ipassstu5, ipassstu6, ipassstu7, ipassstu8, ipassstu9, ipassstu10, ipassstu11;
    @FXML
    private PieChart attendpie;
    @FXML
    private Label piehover;
    @FXML
    private Text ped1, ped2, ped3, ped4, ped5, ped6, ped7;
    @FXML
    private Text monped1, monped2, monped3, monped4, monped5, monped6, monped7;
    @FXML
    private Text tueped1, tueped3, tueped4, tueped5, tueped6;
    @FXML
    private Text wendped2, wendped3, wendped4, wendped5, wendped6, wendped7;
    @FXML
    private Text thursped1, thursped2, thursped3, thursped5, thursped7;
    @FXML
    private Text friped1, friped2, friped4, friped5, friped6, friped7;
    @FXML
    private LineChart gradeline;
    @FXML
    private CategoryAxis yAxis;
    @FXML
    private CategoryAxis catAxis;
    @FXML
    private ProgressIndicator canvasload, iPassLoad, clubLoad;
    @FXML
    private Text canvasloadtext, iPassLoadText, clubLoadText;
    @FXML
    private Text builddate;
    @FXML
    private TabPane clubTabs;
    @FXML
    private TabPane canvasTabs;
    private List<List<String>> courseInfo;
    private List<List<String>> clubData;

    public void logIn(String username, String password, String oauth)
    {
        builddate.setText("Built on " + LocalDate.now());
        Task<List<Document>> iPassGetter = new Task<List<Document>>() {
            @Override
            protected List<Document> call() throws Exception
            {
                new IPassLogin(username, password);
                List<Document> docs = new ArrayList<>();
                docs.add(Jsoup.parse(new URL("https://ipassweb.harrisschool.solutions/school/nsboro/istudentbio.htm"), 0));
                docs.add(Jsoup.parse(new URL("https://ipassweb.harrisschool.solutions/school/nsboro/samattendance.htm"), 0));
                docs.add(Jsoup.parse(new URL("https://ipassweb.harrisschool.solutions/school/nsboro/samschedule.htm"), 0));
                docs.add(Jsoup.parse(new URL("https://ipassweb.harrisschool.solutions/school/nsboro/samgrades.html"), 0));
                return docs;
            }
        };
        Task<CanvasInfoGetter> canvasGetter = new Task<CanvasInfoGetter>() {
            @Override
            protected CanvasInfoGetter call() throws Exception
            {
                return new CanvasInfoGetter(oauth);
            }

        };
        Task<List<List<String>>> clubGetter = new Task<List<List<String>>>() {
            @Override
            protected List<List<String>> call() throws Exception
            {
                return ClubInfoGetter.getInfo();
            }
        };
        iPassGetter.setOnSucceeded(e ->
        {
            List<Document> iPassData = iPassGetter.getValue();
            setBioText(iPassData.get(0));
            setPieData(iPassData.get(1));
            setScheduleData(iPassData.get(2));
            setGradeChart(iPassData.get(3));
            iPassLoad.setVisible(false);
            iPassLoadText.setVisible(false);
        });
        clubGetter.setOnSucceeded(e ->
        {
            clubData = clubGetter.getValue();
            setClubTabs();
            clubLoad.setVisible(false);
            clubLoadText.setVisible(false);
        });
        canvasGetter.setOnSucceeded(e ->
        {
            cget = canvasGetter.getValue();
            courseInfo = cget.getCourseInfo();
            setCanvasTabs();
            setCanvasGradesAndStudents();
            setCanvasAssignments();
            canvasload.setVisible(false);
            canvasloadtext.setVisible(false);
        });
        Thread iPassThread = new Thread(iPassGetter);
        Thread canvThread = new Thread(canvasGetter);
        Thread clubThread = new Thread(clubGetter);
        iPassThread.setDaemon(true);
        iPassThread.start();
        canvThread.setDaemon(true);
        canvThread.start();
        clubThread.setDaemon(true);
        clubThread.start();
        gradeline.setLegendSide(Side.RIGHT);
    }

    private void setBioText(Document parsedBio)
    {
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

    private void setPieData(Document pieData)
    {
        List<String> showedUp = pieData.select(".DataMBl").eachText();
        double daysShowed = Double.parseDouble(showedUp.get(1));
        ObservableList<PieChart.Data> attendPieData = FXCollections.observableArrayList();
        if (!showedUp.get(4).isEmpty())
        {
            attendPieData.add(new PieChart.Data("Absent", Double.parseDouble(showedUp.get(4))));
        }
        if (!showedUp.get(5).isEmpty())
        {
            attendPieData.add(new PieChart.Data("Tardy", Double.parseDouble(showedUp.get(5))));
            daysShowed -= Double.parseDouble(showedUp.get(5));
        }
        if (!showedUp.get(5).isEmpty())
        {
            attendPieData.add(new PieChart.Data("Dismissed", Double.parseDouble(showedUp.get(6))));
            daysShowed -= Double.parseDouble(showedUp.get(6));
        }
        attendPieData.add(new PieChart.Data("Present", daysShowed));
        attendpie.setData(attendPieData);
        for (PieChart.Data data : attendpie.getData())
        {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, e ->
            {
                piehover.setTranslateX(e.getX() - 60);
                piehover.setTranslateY(e.getY());
                piehover.setText(data.getPieValue() + " days");
            });
        }
    }

    private void setScheduleData(Document scheduleData)
    {
        List<String> scheduleValues = scheduleData.select(".DATA").eachText();
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

    private void setGradeChart(Document grades)
    {
        Set<String> categs = new LinkedHashSet<>();
        Set<String> ygrades = new LinkedHashSet<>();
        List<String> courses = grades.select(".data").eachText();
        List<String> letters = grades.select(".Datac").eachText();
        List<String> gradePeds = grades.select(".Labelc").eachText();
        int courseNum = (int) Math.round((courses.size() - 1) / 2.0);
        for (int i = 0; i < courseNum; i++)
        {
            XYChart.Series currentClassPoints = new XYChart.Series();
            currentClassPoints.setName(courses.get((i * 2) + 2).substring(0, 16));
            int counter = 3;
            String gradeValue = "";
            for (int j = i * 12; j < (i * 12) + 10; j++)
            {
                if (!letters.get(j).isEmpty() || !letters.get(j).equals("&sp;") || !letters.get(j).equals("&nbsp;"))
                {
                    gradeValue = letters.get(j);
                    if (!gradeValue.isEmpty() && gradeValue.indexOf("-") == -1 && gradeValue.indexOf("+") == -1)
                    {
                        gradeValue += ",";
                    }

                }
                if (!gradeValue.isEmpty())
                {
                    String gradeToday = gradePeds.get(counter);
                    if (gradeToday.length() > 6)
                    {
                        gradeToday = gradeToday.substring(0, 8) + "rog";
                    } else
                    {
                        gradeToday = gradeToday.substring(0, 6) + " end";
                    }
                    if (!gradeToday.equals("Mid Yearrog") && !gradeToday.equals("Final Exrog"))
                    {
                        categs.add(gradeToday);
                        ygrades.add(gradeValue);
                        currentClassPoints.getData().add(new XYChart.Data(gradeToday, gradeValue));
                    }

                }
                counter++;
            }
            gradeline.getData().add(currentClassPoints);
        }
        ObservableList<String> sortCats = FXCollections.observableArrayList(categs);
        ObservableList<String> sortVals = FXCollections.observableArrayList(ygrades);
        Collections.sort(sortCats);
        Collections.sort(sortVals);
        Collections.reverse(sortVals);
        catAxis.setCategories(sortCats);
        yAxis.setCategories(sortVals);
        catAxis.setAutoRanging(true);
    }

    private void setCanvasAssignments()
    {
        ObservableList<Tab> cTabs = canvasTabs.getTabs();
        List<List<Assignment>> assignments = cget.getCourseAssignments();
        for (int i = 0; i < assignments.size(); i++)
        {
            TabPane assigns = new TabPane();
            for (int j = 0; j < assignments.get(i).size(); j++)
            {
                Tab anAssignment = new Tab(assignments.get(i).get(j).getName());
                anAssignment.setClosable(false);
                assigns.getTabs().add(anAssignment);
                String assignString = "";
                if (assignments.get(i).get(j).getPointsPossible() != null)
                {
                    assignString += (assignments.get(i).get(j).getPointsPossible() + " points possible");
                } else
                {
                    assignString += ("No points possible.");
                }
                if (assignments.get(i).get(j).getDescription() != null)
                {
                    if (!assignments.get(i).get(j).getDescription().isEmpty())
                    {
                        String desc = Jsoup.clean(assignments.get(i).get(j).getDescription(), Whitelist.none());
                        assignString += ("\n\nDescription: " + desc.replace("&nbsp;", "\n"));
                    } else
                    {
                        assignString += ("\n\nNo description.");
                    }
                } else
                {
                    assignString += ("\n\nNo description.");
                }
                assignString += ("\n\nCreated on: " + assignments.get(i).get(j).getCreatedAt().toString());
                if (assignments.get(i).get(j).getDueAt() != null)
                {
                    assignString += ("\n\nDue by: " + assignments.get(i).get(j).getDueAt().toString());
                } else
                {
                    assignString += ("\n\nNo due date.");
                }
                Text assignText = new Text(assignString);
                assignText.setFont(new Font("Montserrat SemiBold", 14));
                assignText.setWrappingWidth(600);
                assigns.getTabs().get(j).setContent(assignText);
            }
            TabPane tPane = (TabPane) cTabs.get(i).getContent();
            Tab assignmentTab = tPane.getTabs().get(1);
            assignmentTab.setContent(assigns);
        }
    }

    private void setCanvasTabs()
    {

        for (int i = 0; i < courseInfo.size(); i++)
        {
            TabPane subTabs = new TabPane();
            Tab gradesMates = new Tab("Grades/Classmates");
            Tab assign = new Tab("Assignments");
            gradesMates.setClosable(false);
            assign.setClosable(false);
            subTabs.getTabs().add(gradesMates);
            subTabs.getTabs().add(assign);
            Tab currentClass = new Tab(courseInfo.get(i).get(0), subTabs);
            canvasTabs.getTabs().add(currentClass);
        }
    }
        private void setCanvasGradesAndStudents()
        {
            ObservableList<Tab> cTabs = canvasTabs.getTabs();
            List<List<String>> studentList = cget.getStudentNames();
            for(int i = 0; i < cTabs.size(); i++)
            {
                TabPane tPane = (TabPane) cTabs.get(i).getContent();
                Tab gradesAndStus = tPane.getTabs().get(0);
                GridPane grid = new GridPane();
                List<ColumnConstraints> cols = new ArrayList<>();
                ListView<String> viewStudents = new ListView<>();
                ObservableList<String> currentStuds = FXCollections.observableArrayList();
                if(studentList.size() > i)
                {
                    currentStuds.setAll(studentList.get(i));
                }
                viewStudents.setItems(currentStuds);
                for(int j = 0; j < 7; j++)
                {
                    cols.add(new ColumnConstraints());
                    cols.get(j).setHgrow(Priority.ALWAYS);
                }
                RowConstraints row1 = new RowConstraints();
                RowConstraints row2 = new RowConstraints();
                grid.getColumnConstraints().addAll(cols);
                grid.getRowConstraints().add(row1);
                grid.getRowConstraints().add(row2);
                grid.setGridLinesVisible(true);
                grid.setAlignment(Pos.TOP_CENTER);
                List<Text> words = new ArrayList<>();
                words.add(new Text("Overall"));
                words.add(new Text("Term 1"));
                words.add(new Text("Term 2"));
                words.add(new Text("Midterms"));
                words.add(new Text("Term 3"));
                words.add(new Text("Term 4"));
                words.add(new Text("Finals"));
                for(int j = 2; j <= 8; j++)
                {
                    if(courseInfo.get(i).size() > j)
                    {
                        words.add(new Text(courseInfo.get(i).get(j)));
                    }
                }
                for(Text t: words)
                {
                    t.setFont(new Font("Montserrat SemiBold", 16));
                }
                for(int j = 0; j <= 1; j++)
                {
                    for(int k = 0; k <= 6; k++)
                    {
                        if(!words.isEmpty())
                        {
                            grid.add(words.get(0), k, j);
                            words.remove(0);
                        }
                    }
                }
                for(Node n: grid.getChildren())
                {
                    GridPane.setHalignment(n, HPos.CENTER);
                    GridPane.setMargin(n, new Insets(5));
                }
                gradesAndStus.setContent(new VBox(grid, viewStudents));
            }
        }
        private void setClubTabs()
        {
            for (List<String> currentClubData : clubData)
            {
                Text clubBody = new Text(currentClubData.get(1));
                clubBody.setFont(new Font("Montserrat SemiBold", 14));
                clubBody.setWrappingWidth(600);
                Tab clubTab = new Tab(currentClubData.get(0), clubBody);
                clubTabs.getTabs().add(clubTab);
            }
        }
    }
