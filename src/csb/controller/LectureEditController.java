package csb.controller;

import static csb.CSB_PropertyType.REMOVE_ITEM_MESSAGE;
import csb.data.Course;
import csb.data.CourseDataManager;
import csb.data.Lecture;

import csb.gui.CSB_GUI;
import csb.gui.LectureItemDialog;
import csb.gui.MessageDialog;

import csb.gui.YesNoCancelDialog;
import java.util.Collections;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;

/**
 *
 * @author Shi Lin Lu
 */
public class LectureEditController {
    LectureItemDialog sid;
    MessageDialog messageDialog;
    YesNoCancelDialog yesNoCancelDialog;
    
    public LectureEditController(Stage initPrimaryStage, Course course, MessageDialog initMessageDialog, YesNoCancelDialog initYesNoCancelDialog) {
        sid = new LectureItemDialog(initPrimaryStage, course, initMessageDialog);
        messageDialog = initMessageDialog;
        yesNoCancelDialog = initYesNoCancelDialog;
    }
    
    // THESE ARE FOR SCHEDULE ITEMS
    
    public void handleAddLectureItemRequest(CSB_GUI gui) {
        CourseDataManager cdm = gui.getDataManager();
        Course course = cdm.getCourse();
        sid.showAddLectureItemDialog(course.getStartingMonday());
        
        // DID THE USER CONFIRM?
        if (sid.wasCompleteSelected()) {
            // GET THE SCHEDULE ITEM
            Lecture si = sid.getLectureItem();
            
            // AND ADD IT AS A ROW TO THE TABLE
            course.addLecture(si);
        }
        else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
        }
    }
   
    public void handleEditLectureItemRequest(CSB_GUI gui, Lecture itemToEdit) {
        CourseDataManager cdm = gui.getDataManager();
        Course course = cdm.getCourse();
        sid.showEditLectureItemDialog(itemToEdit);
        
        // DID THE USER CONFIRM?
        if (sid.wasCompleteSelected()) {
            // UPDATE THE SCHEDULE ITEM
            Lecture si = sid.getLectureItem();
            itemToEdit.setTopic(si.getTopic());
            itemToEdit.setDate(si.getDate());
            itemToEdit.setSessions(si.getSessions());
            Collections.sort(course.getLectures());
        }
        else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
        }        
    }
    public void handleMoveUpLectureItemRequest(CSB_GUI gui, Lecture itemToEdit) {
        CourseDataManager cdm = gui.getDataManager();
        Course course = cdm.getCourse();
        sid.showMoveUpLectureItemDialog(itemToEdit);
        
        // DID THE USER CONFIRM?
        if (sid.wasCompleteSelected()) {
            // UPDATE THE SCHEDULE ITEM
            Lecture si = sid.getLectureItem();
            itemToEdit.setTopic(si.getTopic());
            itemToEdit.setDate(si.getDate());
            itemToEdit.setSessions(si.getSessions());
            Collections.sort(course.getLectures());
        }
        else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
        }        
    }
    public void handleMoveDownLectureItemRequest(CSB_GUI gui, Lecture itemToEdit) {
        CourseDataManager cdm = gui.getDataManager();
        Course course = cdm.getCourse();
        sid.showMoveDownLectureItemDialog(itemToEdit);
        
        // DID THE USER CONFIRM?
        if (sid.wasCompleteSelected()) {
            // UPDATE THE SCHEDULE ITEM
            Lecture si = sid.getLectureItem();
            itemToEdit.setTopic(si.getTopic());
            itemToEdit.setDate(si.getDate());
            itemToEdit.setSessions(si.getSessions());
            Collections.sort(course.getLectures());
        }
        else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
        }        
    }
    public void handleRemoveLectureItemRequest(CSB_GUI gui, Lecture itemToRemove) {
        // PROMPT THE USER TO SAVE UNSAVED WORK
        yesNoCancelDialog.show(PropertiesManager.getPropertiesManager().getProperty(REMOVE_ITEM_MESSAGE));
        
        // AND NOW GET THE USER'S SELECTION
        String selection = yesNoCancelDialog.getSelection();

        // IF THE USER SAID YES, THEN SAVE BEFORE MOVING ON
        if (selection.equals(YesNoCancelDialog.YES)) { 
            gui.getDataManager().getCourse().removeLecture(itemToRemove);
        }
    }
}