import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Ian Anderson
 * 4/12/19
 */

public class MainController {
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
    @FXML private NumberAxis numAxis;    public void logIn(String username, String password) throws IOException
    {
        new IPassLogin(username, password);
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
                        System.out.println("GradeToday=" + gradeToday);
                        if(!gradeToday.equals("Mid Year") && !gradeToday.equals("Final Ex"))
                        {
                            currentClassPoints.getData().add(new XYChart.Data(gradeToday, gradeValue));
                        }
                    }
                }
                counter++;
            }
            System.out.println(currentClassPoints.getName());
            gradeline.getData().add(currentClassPoints);
        }
    }
}
