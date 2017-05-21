package csb.controller;

import static csb.CSB_PropertyType.REMOVE_ITEM_MESSAGE;
import csb.data.Assignment;
import csb.data.Course;
import csb.data.CourseDataManager;

import csb.data.ScheduleItem;
import csb.gui.CSB_GUI;
import csb.gui.HwItemDialog;
import csb.gui.MessageDialog;
import csb.gui.ScheduleItemDialog;
import csb.gui.YesNoCancelDialog;
import java.util.Collection;
import java.util.Collections;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;

/**
 *
 * @author Shi Lin Lu
 */
public class HwEditController {
    HwItemDialog sid;
    MessageDialog messageDialog;
    YesNoCancelDialog yesNoCancelDialog;
    
    public HwEditController(Stage initPrimaryStage, Course course, MessageDialog initMessageDialog, YesNoCancelDialog initYesNoCancelDialog) {
        sid = new HwItemDialog(initPrimaryStage, course, initMessageDialog);
        messageDialog = initMessageDialog;
        yesNoCancelDialog = initYesNoCancelDialog;
    }

    // THESE ARE FOR SCHEDULE ITEMS
    
    public void handleAddHwItemRequest(CSB_GUI gui) {
        CourseDataManager cdm = gui.getDataManager();
        Course course = cdm.getCourse();
        sid.showAddHwItemDialog(course.getStartingMonday());
        
        // DID THE USER CONFIRM?
        if (sid.wasCompleteSelected()) {
            // GET THE SCHEDULE ITEM
            Assignment si = sid.getHwItem();
            
            // AND ADD IT AS A ROW TO THE TABLE
            course.addAssignment(si);
        }
        else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
        }
    }
   
    public void handleEditHwItemRequest(CSB_GUI gui, Assignment itemToEdit) {
        CourseDataManager cdm = gui.getDataManager();
        Course course = cdm.getCourse();
        sid.showEditHwItemDialog(itemToEdit);
        
        // DID THE USER CONFIRM?
        if (sid.wasCompleteSelected()) {
            // UPDATE THE SCHEDULE ITEM
            Assignment si = sid.getHwItem();
            itemToEdit.setName(si.getName());
            itemToEdit.setDate(si.getDate());
            itemToEdit.setTopics(si.getTopics());
            Collections.sort(course.getAssignments());
        }
        else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
        }        
    }
    
    public void handleRemoveHwItemRequest(CSB_GUI gui, Assignment itemToRemove) {
        // PROMPT THE USER TO SAVE UNSAVED WORK
        yesNoCancelDialog.show(PropertiesManager.getPropertiesManager().getProperty(REMOVE_ITEM_MESSAGE));
        
        // AND NOW GET THE USER'S SELECTION
        String selection = yesNoCancelDialog.getSelection();

        // IF THE USER SAID YES, THEN SAVE BEFORE MOVING ON
        if (selection.equals(YesNoCancelDialog.YES)) { 
            gui.getDataManager().getCourse().removeAssignment(itemToRemove);
        }
    }
}