package csb.gui;

import csb.CSB_PropertyType;
import csb.data.Course;
import csb.data.Lecture;

import static csb.gui.CSB_GUI.CLASS_HEADING_LABEL;
import static csb.gui.CSB_GUI.CLASS_PROMPT_LABEL;
import static csb.gui.CSB_GUI.PRIMARY_STYLE_SHEET;
import static csb.gui.ScheduleItemDialog.DATE_PROMPT;
import java.time.LocalDate;
import javafx.beans.property.Property;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;


/**
 *
 * @author Shi Lin Lu
 */
public class LectureItemDialog  extends Stage {
    // THIS IS THE OBJECT DATA BEHIND THIS UI
    Lecture lectureItem;
    
    // GUI CONTROLS FOR OUR DIALOG
    GridPane gridPane;
    Scene dialogScene;
    Label headingLabel;
    Label topicLabel;
    TextField topicTextField;
    TextField sessionField;
    Label numSessionLabel;
    ComboBox numSessionComboBox;
    Button completeButton;
    Button cancelButton;
    ObservableList<Integer> numSessions;
    // THIS IS FOR KEEPING TRACK OF WHICH BUTTON THE USER PRESSED
    String selection;
    Label dateLabel;
    DatePicker datePicker;
    // CONSTANTS FOR OUR UI
    public static final String COMPLETE = "Complete";
    public static final String CANCEL = "Cancel";
    public static final String DESCRIPTION_PROMPT = "Description: ";
    public static final String EDIT_LECTURE_ITEM_TITLE = "Edit Lecture Item";
    public static final String ADD_LECTURE_ITEM_TITLE = "Add New Lecture";
    public static final String LECTURE_ITEM_HEADING = "Lecture Details";
    public static final String TOPIC_PROMPT = "Topic";
    public static final String NUM_SESSIONS = "Number of Sessions";
    /**
     * Initializes this dialog so that it can be used for either adding
     * new schedule items or editing existing ones.
     * 
     * @param primaryStage The owner of this modal dialog.
     */
    public LectureItemDialog(Stage primaryStage, Course course,  MessageDialog messageDialog) {       
        // MAKE THIS DIALOG MODAL, MEANING OTHERS WILL WAIT
        // FOR IT WHEN IT IS DISPLAYED
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        
        // FIRST OUR CONTAINER
        gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 20, 20, 20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        
        // PUT THE HEADING IN THE GRID, NOTE THAT THE TEXT WILL DEPEND
        // ON WHETHER WE'RE ADDING OR EDITING
        headingLabel = new Label(LECTURE_ITEM_HEADING);
        headingLabel.getStyleClass().add(CLASS_HEADING_LABEL);
    
        // NOW THE TOPIC 
        topicLabel = new Label(TOPIC_PROMPT);
        topicLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        topicTextField = new TextField();
        topicTextField.textProperty().addListener((observable, oldValue, newValue) -> {
           lectureItem.setTopic(newValue);
        });
        
        
        // AND THE NUMBER OF SESSION
        numSessionLabel = new Label(NUM_SESSIONS);
        numSessionLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        numSessionComboBox = new ComboBox();
        
        
        
        numSessions = FXCollections.observableArrayList();
        
        numSessions.add(1); numSessions.add(2); numSessions.add(3);
        
        numSessionComboBox.setItems(numSessions);
      
        // SESSION FIELD 
        
        //numSessionComboBox.itemsProperty().addListener((observable, oldValue, newValue) -> {
           // lectureItem.setSessions((int) numSessionComboBox.getSelectionModel().getSelectedItem());
       // });
        
        sessionField = new TextField();
        sessionField.textProperty().addListener((observable, oldValue, newValue) -> {
            lectureItem.setSessions(Integer.parseInt(newValue));
           
        });
        

        // WTFFFFF
        //lectureItem.setSessions((int) numSessionComboBox.getSelectionModel().getSelectedItem());
        //lectureItem.setSessions(Integer.parseInt(numSessionComboBox.getSelectionModel().getSelectedItem().toString()));
        // AND THE DATE
        dateLabel = new Label(DATE_PROMPT);
        dateLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        datePicker = new DatePicker();
        datePicker.setOnAction(e -> {
            if (datePicker.getValue().isBefore(course.getStartingMonday())
                    || datePicker.getValue().isAfter(course.getEndingFriday())) {
                // INCORRECT SELECTION, NOTIFY THE USER
                PropertiesManager props = PropertiesManager.getPropertiesManager();
                messageDialog.show(props.getProperty(CSB_PropertyType.ILLEGAL_DATE_MESSAGE));
            }             
            else {
                lectureItem.setDate(datePicker.getValue());
            }
        });


        // AND FINALLY, THE BUTTONS
        completeButton = new Button(COMPLETE);
        cancelButton = new Button(CANCEL);
        
         //REGISTER EVENT HANDLERS FOR OUR BUTTONS
        EventHandler completeCancelHandler = (EventHandler<ActionEvent>) (ActionEvent ae) -> {
            Button sourceButton = (Button)ae.getSource();
            LectureItemDialog.this.selection = sourceButton.getText();
            LectureItemDialog.this.hide();
            };
        completeButton.setOnAction(completeCancelHandler);
        cancelButton.setOnAction(completeCancelHandler);

        // NOW LET'S ARRANGE THEM ALL AT ONCE
        gridPane.add(headingLabel, 0, 0, 2, 1);
        gridPane.add(topicLabel, 0, 1, 1, 1);
        gridPane.add(topicTextField, 1, 1, 1, 1);
        gridPane.add(numSessionLabel, 0, 2, 1, 1);
        gridPane.add(numSessionComboBox, 1, 2, 1, 1);
        gridPane.add(completeButton, 0, 4, 1, 1);
        gridPane.add(cancelButton, 1, 4, 1, 1);
        gridPane.add(dateLabel, 0, 3, 1, 1);
        gridPane.add(datePicker, 1, 3, 1, 1);

        // AND PUT THE GRID PANE IN THE WINDOW
        dialogScene = new Scene(gridPane);
        dialogScene.getStylesheets().add(PRIMARY_STYLE_SHEET);
        this.setScene(dialogScene);
   
    }
    
    /**
     * Accessor method for getting the selection the user made.
     * 
     * @return Either YES, NO, or CANCEL, depending on which
     * button the user selected when this dialog was presented.
     */
    public String getSelection() {
        return selection;
    }
    
    public Lecture getLectureItem() { 
        return lectureItem;
    }
    
    /**
     * This method loads a custom message into the label and
     * then pops open the dialog.
     * 
     * @return 
     */
    
    public Lecture showAddLectureItemDialog(LocalDate initDate) {
        // SET THE DIALOG TITLE
        setTitle(ADD_LECTURE_ITEM_TITLE);
        
        // RESET THE SCHEDULE ITEM OBJECT WITH DEFAULT VALUES
        lectureItem = new Lecture();
        
        // LOAD THE UI STUFF
        topicTextField.setText(lectureItem.getTopic());
        numSessionComboBox.setItems(numSessions);
        datePicker.setValue(initDate);
        //sessionField.setText(numSessionComboBox.getSelectionModel().getSelectedItem().toString());
        // AND OPEN IT UP
        this.showAndWait();
        
        return lectureItem;
    }
   
    
    
    public void loadGUIData2() {
        // LOAD THE UI STUFF
        topicTextField.setText(lectureItem.getTopic());
        datePicker.setValue(lectureItem.getDate());
        numSessionComboBox.setItems(numSessions);       
    }
    
    public boolean wasCompleteSelected() {
        return selection.equals(COMPLETE);
    }
    
    public void showEditLectureItemDialog(Lecture itemToEdit) {
        // SET THE DIALOG TITLE
        setTitle(EDIT_LECTURE_ITEM_TITLE);
        
        // LOAD THE SCHEDULE ITEM INTO OUR LOCAL OBJECT
        lectureItem = new Lecture();
        lectureItem.setTopic(itemToEdit.getTopic());
        lectureItem.setDate(itemToEdit.getDate());
        lectureItem.setSessions(itemToEdit.getSessions());
        
        // AND THEN INTO OUR GUI
        loadGUIData2();
               
        // AND OPEN IT UP
        this.showAndWait();
    }

    public void showMoveUpLectureItemDialog(Lecture itemToEdit) {
        // SET THE DIALOG TITLE
        setTitle("Move Lecture Up");
        
        // LOAD THE SCHEDULE ITEM INTO OUR LOCAL OBJECT
        lectureItem = new Lecture();
        lectureItem.setTopic(itemToEdit.getTopic());
        lectureItem.setDate(itemToEdit.getDate());
        lectureItem.setSessions(itemToEdit.getSessions());
        
        // AND THEN INTO OUR GUI
        loadGUIData2();
               
        // AND OPEN IT UP
        this.showAndWait();
    }
    public void showMoveDownLectureItemDialog(Lecture itemToEdit) {
        // SET THE DIALOG TITLE
        setTitle("Move Lecture Down");
        
        // LOAD THE SCHEDULE ITEM INTO OUR LOCAL OBJECT
        lectureItem = new Lecture();
        lectureItem.setTopic(itemToEdit.getTopic());
        lectureItem.setDate(itemToEdit.getDate());
        lectureItem.setSessions(itemToEdit.getSessions());
        
        // AND THEN INTO OUR GUI
        loadGUIData2();
               
        // AND OPEN IT UP
        this.showAndWait();
    }


}