package csb.test;

import csb.data.Course;
import csb.data.Instructor;
import csb.gui.LectureItemDialog;
import csb.gui.MessageDialog;
import csb.gui.ScheduleItemDialog;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;

/**
 *
 * @author McKillaGorilla
 */
public class CSB_Test_AddLectureItemDialog extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Course course = new Course(new Instructor("Joe Shmo", "http://joeshmo.com"));
        MessageDialog messageDialog = new MessageDialog(primaryStage, "CLOSE");
        LectureItemDialog testDialog = new LectureItemDialog(primaryStage, course, messageDialog);
        testDialog.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
