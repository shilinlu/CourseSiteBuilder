/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csb.test;

import csb.data.Course;
import csb.data.Instructor;
import csb.data.Lecture;
import csb.data.ScheduleItem;
import csb.gui.LectureItemDialog;
import csb.gui.MessageDialog;
import csb.gui.ScheduleItemDialog;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;

/**
 *
 * @author McKillaGorilla
 */
public class CSB_Test_EditLectureItemDialog extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // LET'S MAKE A SCHEDULE ITEM JUST TO TEST OUR DIALOG
        Lecture testLectureItem = new Lecture();
        
        // NOW LET'S OPEN UP THE DIALOG
        MessageDialog messageDialog = new MessageDialog(primaryStage, "CLOSE");
        Course course = new Course(new Instructor("Joe Shmo", "http://joeshmo.com"));
        LectureItemDialog testDialog = new LectureItemDialog(primaryStage, course, messageDialog);
        testDialog.showEditLectureItemDialog(testLectureItem);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
