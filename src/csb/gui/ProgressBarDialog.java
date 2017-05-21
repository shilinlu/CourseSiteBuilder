package csb.gui;

import csb.data.Course;
import java.util.concurrent.locks.ReentrantLock;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;

/**
 * This class serves to present custom text messages to the user when
 * events occur. Note that it always provides the same controls, a label
 * with a message, and a single ok button. The scheduled release of 
 * Java 8 version 40 in March 2015 will make this class irrelevant 
 * because the Alert class will do what this does and much more.
 * 
 * @author Shi Lin Lu
 */
public class ProgressBarDialog extends Stage {
    HBox messagePane;
    Scene messageScene;
     Label messageLabel=new Label();
    Button closeButton;
    ProgressBar pb;
    ProgressIndicator pi;
    BorderPane borderPane;
    int page;  double progress=0;
    Course course;
    /**
     * Initializes this dialog so that it can be used repeatedly
     * for all kinds of messages.
     * 
     * @param owner The owner stage of this modal dialoge.
     * 
     * @param closeButtonText Text to appear on the close button.
     */
    public ProgressBarDialog(Stage owner, String closeButtonText, Course course) {
        // MAKE IT MODAL
        initModality(Modality.WINDOW_MODAL);
        initOwner(owner);
        
        //page=course.getPages().size();
        // LABEL TO DISPLAY THE CUSTOM MESSAGE
        //messageLabel = new Label();        
        pb = new ProgressBar(0.0);
        pi = new ProgressIndicator(0.0);
        // CLOSE BUTTON
        closeButton = new Button(closeButtonText);
        closeButton.setOnAction(e->{ ProgressBarDialog.this.close(); });

        // WE'LL PUT EVERYTHING HERE
        messagePane = new HBox();
        borderPane=new BorderPane();
        messagePane.setAlignment(Pos.CENTER);
        messageLabel.setStyle("-fx-font-size: 12pt;");
        borderPane.setTop(messageLabel);
        borderPane.setLeft(pb); borderPane.setRight(pi);
        borderPane.setBottom(closeButton);
        messagePane.getChildren().add(borderPane);
        // MAKE IT LOOK NICE
        messagePane.setPadding(new Insets(10, 20, 20, 20));
        messagePane.setSpacing(10);
        messagePane.setMinSize(230, 100);
        // AND PUT IT IN THE WINDOW
        messageScene = new Scene(messagePane);
        this.setScene(messageScene);
        
    }
    //public int getSize(){
        //return page;
    //}
    /**
     * This method loads a custom message into the label and
     * then pops open the dialog.
     * 
     * @param message Message to appear inside the dialog.
     */
   
    
   
    public void startBar()  {
        final ReentrantLock lock = new ReentrantLock();
       lock.lock();
        try{
       Task<Void> task = new Task<Void>() {
                @Override protected Void call() throws Exception {
                
               String [] text= new String [4];
        text[0]="Exporting Schedule Page";text[1]="Exporting Home Page";
        text[2]="Exporting Syllabus Page";text[3]="Exporting HW Page";
                for(int i=0;i<4;i++){
               int x = (int) (Math.random() * 4);
                 
                 Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                
                messageLabel.setText(text[x]);
                progress=progress+0.25;
                pb.setProgress(progress);
            pi.setProgress(progress);
                          
                            }
                            });
            
                    // SLEEP EACH FRAME
                        try { Thread.sleep(500); }
                        catch(InterruptedException ie) {
                            ie.printStackTrace();
                        }
                    }
            return null;
              
                }
            };
        
       
                Thread thread = new Thread(task);
            thread.start();
      //  });
            this.showAndWait();
            //this.showAndWait();
        }
        finally{ lock.unlock();}
    
    }    
    public void setProgress(int x){
        progress=x;
    }



}