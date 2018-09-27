/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software2.view;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import software2.Software2;
import software2.model.Appointment;
import static software2.view.CalendarWeeklyController.getSelectedAppointment;
import static software2.view.CalendarWeeklyController.getAppointmentModifyIndex;
import static software2.view.CalendarMonthlyController.getSelectedAppointment1;
import static software2.view.CalendarMonthlyController.getAppointmentModifyIndex1;
import static software2.view.LoginController.conn;


/**
 * FXML Controller class
 *
 * @author David
 */
public class ModifyAppointmentController implements Initializable {

    
    
    @FXML
    private Label titleLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label locationLabel;
    @FXML
    private Label contactLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label startDateLabel;
    @FXML
    private Label startTimeLabel;
    @FXML
    private Label endTimeLabel;
    
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private TextField locationTextField;
    
    @FXML
    private ComboBox contactComboBox;
     @FXML
    private ComboBox typeComboBox;
    
   @FXML
   private DatePicker startDatePicker;
    @FXML
    private ComboBox startTimeHour;
    @FXML
    private ComboBox startTimeMinutes;
    @FXML
    private ComboBox endTimeHour;
    @FXML
    private ComboBox endTimeMinutes;
    @FXML
    private Button saveButton;
     @FXML
    private Button cancelButton;
    
    
    
    
    
    
    static ResourceBundle rbw = Software2.getResource();

    
    private void setLanguage() {
    
     titleLabel.setText(rbw.getString("tit"));
     descriptionLabel.setText(rbw.getString("desc"));
     locationLabel.setText(rbw.getString("loc"));
     contactLabel.setText(rbw.getString("cont"));
     typeLabel.setText(rbw.getString("ty"));
     startDateLabel.setText(rbw.getString("startD"));
     startTimeLabel.setText(rbw.getString("startT"));
     endTimeLabel.setText(rbw.getString("endT"));
     saveButton.setText(rbw.getString("Save"));
     cancelButton.setText(rbw.getString("cancel"));
    
    }
    
    
    @FXML
    private void saveButtonHandler(ActionEvent event){
    
    String title = titleTextField.getText();
    String description = descriptionTextField.getText();
    String location = locationTextField.getText();
    String error = ""; 
        
     error = appointmentValidation(title, description, location, contactComboBox, typeComboBox, startDatePicker,
             startTimeHour, startTimeMinutes, endTimeHour, endTimeMinutes);
        if (error.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(rbw.getString("errAppMsg"));
                alert.setHeaderText(rbw.getString("errorT"));
                alert.setContentText(error);
                alert.showAndWait();
                error = "";
            } else {    
    String contact = contactComboBox.getValue().toString();
    String type = typeComboBox.getValue().toString();
    String startTHour = startTimeHour.getValue().toString();
    String startTMin = startTimeMinutes.getValue().toString();
    String endTHour = endTimeHour.getValue().toString();
    String endTMin = endTimeMinutes.getValue().toString();
    String Date = startDatePicker.getValue().toString();
    String finalStartDate = Date + " " + startTHour + ":" + startTMin + ":00.0";
    String finalEndDate = Date + " " + endTHour + ":" + endTMin + ":00.0";
    
     DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss.S");
    
   
    LocalDateTime ldtStart = LocalDateTime.parse(finalStartDate, df);
    ZoneId zid = ZoneId.systemDefault();
    ZonedDateTime zdtStart = ldtStart.atZone(zid);
    //ZonedDateTime utcStart = zdtStart.withZoneSameInstant(ZoneId.of("UTC"));
    ldtStart = zdtStart.toLocalDateTime();
    Timestamp startTime = Timestamp.valueOf(ldtStart);
    
     LocalDateTime ldtEnd = LocalDateTime.parse(finalEndDate, df);
     ZonedDateTime zdtEnd = ldtEnd.atZone(zid);
    // ZonedDateTime utcEnd = zdtEnd.withZoneSameInstant(ZoneId.of("UTC"));
     ldtEnd = zdtEnd.toLocalDateTime();
     Timestamp endTime = Timestamp.valueOf(ldtEnd);
        
        try {
             LocalDateTime now = LocalDateTime.now();
           Statement stmt = conn.createStatement();
           ResultSet appRS = stmt.executeQuery("select start, end from appointment");
           while(appRS.next()) {
           
           Timestamp dbStart = appRS.getTimestamp("start");
           Timestamp dbEnd = appRS.getTimestamp("end"); 
           if(endTime.before(dbStart) || startTime.after(dbEnd)){
              
           } else {
            Alert overlapAlert = new Alert(Alert.AlertType.INFORMATION);
                overlapAlert.setTitle(rbw.getString("errAppMsg"));
                overlapAlert.setHeaderText(rbw.getString("errorT"));
                overlapAlert.setContentText(rbw.getString("errOverLap"));
                overlapAlert.showAndWait();
                return;
           }
           }
            
        PreparedStatement ps1 = conn.prepareStatement("SELECT customerId from customer where customerName = ?");
              ps1.setString(1,contact);
              ResultSet customerResult = ps1.executeQuery();
              customerResult.next();
              String customerIdResult = customerResult.getString(1);
              
              PreparedStatement ps2 = conn.prepareStatement("SELECT userId from user where userName = ?");
              ps2.setString(1,LoginController.getUserName());
              ResultSet userResult = ps2.executeQuery();
              userResult.next();
              String userId = userResult.getString(1);
        
        PreparedStatement updateAppointment = conn.prepareStatement("UPDATE appointment SET customerId = ?, userId = ?, title = ?, description = ?, "
                + "location = ?, contact = ?, type = ?, url = ?, start = ?, end = ?, lastUpdate = ?, lastUpdateBy = ? WHERE appointmentID = ?");
                    
                     
                      updateAppointment.setString(1, customerIdResult);
                      updateAppointment.setString(2, userId);
                      updateAppointment.setString(3, title);
                      updateAppointment.setString(4, description);
                      updateAppointment.setString(5, location);
                      updateAppointment.setString(6, contact);
                      updateAppointment.setString(7, type);
                      updateAppointment.setString(8, "testUrl.com/" + contact);
                      updateAppointment.setTimestamp(9, startTime);
                      updateAppointment.setTimestamp(10, endTime);
                      updateAppointment.setString(11, LocalDateTime.now().toString());
                      updateAppointment.setString(12, LoginController.getUserName());
                      if (getSelectedAppointment() != null){
                      updateAppointment.setString(13, getSelectedAppointment().getAppointmentId().getValue() );}
                      else if (getSelectedAppointment1() != null){
                          updateAppointment.setString(13, getSelectedAppointment1().getAppointmentId().getValue() );
                      }
                      

                      
           updateAppointment.executeUpdate();
           if (getSelectedAppointment() != null){
               Appointment finalAppointment = new Appointment(new SimpleStringProperty(getSelectedAppointment().getAppointmentId().getValue()), new SimpleStringProperty(customerIdResult),
                    new SimpleStringProperty(userId), new SimpleStringProperty(title), new SimpleStringProperty(description), 
                  new SimpleStringProperty(location), new SimpleStringProperty(contact),
                    new SimpleStringProperty(type), new SimpleStringProperty("testUrl.com/" + contact), 
            new SimpleStringProperty(Date + "" + startTHour + ":" + startTMin), new SimpleStringProperty(Date + "" + endTHour + ":" + endTMin), 
                    new SimpleStringProperty(contact), new SimpleStringProperty(LoginController.getUserName()) );
               
               Appointment.updateAppointment(getAppointmentModifyIndex(), finalAppointment);
           }
           else if (getSelectedAppointment1() != null){
                Appointment finalAppointment = new Appointment(new SimpleStringProperty(getSelectedAppointment1().getAppointmentId().getValue()), new SimpleStringProperty(customerIdResult),
                    new SimpleStringProperty(userId), new SimpleStringProperty(title), new SimpleStringProperty(description), 
                  new SimpleStringProperty(location), new SimpleStringProperty(contact),
                    new SimpleStringProperty(type), new SimpleStringProperty("testUrl.com/" + contact), 
            new SimpleStringProperty(Date + "" + startTHour + ":" + startTMin), new SimpleStringProperty(Date + "" + endTHour + ":" + endTMin), 
                    new SimpleStringProperty(contact), new SimpleStringProperty(LoginController.getUserName()) );
                
           Appointment.updateAppointment(getAppointmentModifyIndex1(), finalAppointment);
           }
          
          
           
       
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(rbw.getString("Success"));
            alert.setHeaderText(rbw.getString("ApSuccess"));
            alert.showAndWait();
            
            Stage stage = (Stage)saveButton.getScene().getWindow();
            stage.close();
            calendarView();
        
              
              
        
         }
           
            catch (SQLException ex) {
                ex.printStackTrace();
            }
       
        }
 
    }
    
    @FXML
    private void cancelButtonHandler(ActionEvent event){
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle(rbw.getString("Confirmation"));
        alert.setHeaderText(rbw.getString("Confirmation"));
        alert.setContentText(rbw.getString("cancelMsg"));
        Optional<ButtonType> choice = alert.showAndWait();
        if (choice.get() == ButtonType.OK) {
        Stage stage = (Stage)cancelButton.getScene().getWindow();
            stage.close();
            calendarView();
    }
    }
    
    
    
    
        private void calendarView() {
   
        
        Parent main = null;
        try {
            main = FXMLLoader.load(getClass().getResource("CalendarWeekly.fxml"));
            Scene scene = new Scene(main);

            Stage stage = Software2.getStage();
            
            stage.setScene(scene);

            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
     }
    
    
     private void populateChoiceBox() {
        
       Statement stmt;
        ResultSet customerResult = null;
        
        try{
       
        stmt = conn.createStatement();
        
       
        customerResult=stmt.executeQuery("SELECT customerName FROM customer;");
        while(customerResult.next()){
           contactComboBox.getItems().add(customerResult.getString(1)); 
           
        }
    }    
    
    catch (SQLException ex) {
                ex.printStackTrace();
            }
     }
        
                public static String appointmentValidation(String titleTextField, String descriptionTextField, String locationTextField, ComboBox contactComboBox, 
                  ComboBox typeComboBox, DatePicker startDatePicker, ComboBox startTimeHour, ComboBox startTimeMinutes, ComboBox endTimeHour, ComboBox endTimeMinutes) {
        
       String error = "";
             if (titleTextField.length() == 0) {
            error = rbw.getString("errMsg6");
        }
        if (descriptionTextField.length() == 0) {
            error = rbw.getString("errMsg7");
        }
       
        if (locationTextField.length() == 0) {
            error = rbw.getString("errMsg8");
        }
     
        if (contactComboBox.getSelectionModel().isEmpty() == true ) {
            error = rbw.getString("errMsg9");
        }
        
        if (typeComboBox.getSelectionModel().isEmpty() == true ) {
            error = rbw.getString("errMsg10");
        }
        
        if (startDatePicker.getValue() == null) {
            error = rbw.getString("errMsg11");
        }
        
        if (startTimeHour.getSelectionModel().isEmpty() == true | startTimeMinutes.getSelectionModel().isEmpty() == true) {
            error = rbw.getString("errMsg12");
        } else {
                      if (Integer.parseInt(startTimeHour.getValue().toString()) < 9) {
       error = rbw.getString("startBusinessHour");
          }
                  }
        
        
        if (endTimeHour.getSelectionModel().isEmpty() == true | endTimeMinutes.getSelectionModel().isEmpty() == true) {
            error = rbw.getString("errMsg13");
        } else if (Integer.parseInt(endTimeHour.getValue().toString()) > 17 || Integer.parseInt(endTimeMinutes.getValue().toString()) > 00) {
             
          error = rbw.getString("endBusinessHour");
          
        }
       
        return error;
    }
        
        
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        setLanguage();
        populateChoiceBox();
        
        if (getSelectedAppointment() != null){
       titleTextField.setText(getSelectedAppointment().getTitle().getValue());
       descriptionTextField.setText(getSelectedAppointment().getDescription().getValue());
       locationTextField.setText(getSelectedAppointment().getLocation().getValue());
       contactComboBox.getSelectionModel().select(getSelectedAppointment().getContact().getValue());
       typeComboBox.getSelectionModel().select(getSelectedAppointment().getType().getValue());
       String tempDate = getSelectedAppointment().getStartDate().getValue();
       int year = Integer.parseInt(tempDate.substring(0, 4));
       int month = Integer.parseInt(tempDate.substring(5, 7));
       int day = Integer.parseInt(tempDate.substring(8, 10));
       LocalDate ldDate = LocalDate.of(year, month, day);
       startDatePicker.setValue(ldDate);
       String start = getSelectedAppointment().getStartDate().getValue();
       String startHour = start.substring(11, 13);
       String startMinute = start.substring(14, 16);
       startTimeHour.getSelectionModel().select(startHour);
       startTimeMinutes.getSelectionModel().select(startMinute);
       String end = getSelectedAppointment().getEndDate().getValue();
       String endHour = end.substring(11, 13);
       String endMinute = end.substring(14, 16);
       endTimeHour.getSelectionModel().select(endHour);
       endTimeMinutes.getSelectionModel().select(endMinute);
        }
       else if (getSelectedAppointment1() != null){
               titleTextField.setText(getSelectedAppointment1().getTitle().getValue());
       descriptionTextField.setText(getSelectedAppointment1().getDescription().getValue());
       locationTextField.setText(getSelectedAppointment1().getLocation().getValue());
       contactComboBox.getSelectionModel().select(getSelectedAppointment1().getContact().getValue());
       typeComboBox.getSelectionModel().select(getSelectedAppointment1().getType().getValue());
       String tempDate = getSelectedAppointment1().getStartDate().getValue();
       int year = Integer.parseInt(tempDate.substring(0, 4));
       int month = Integer.parseInt(tempDate.substring(5, 7));
       int day = Integer.parseInt(tempDate.substring(8, 10));
       LocalDate ldDate = LocalDate.of(year, month, day);
       startDatePicker.setValue(ldDate);
       String start = getSelectedAppointment1().getStartDate().getValue();
       String startHour = start.substring(11, 13);
       String startMinute = start.substring(14, 16);
       startTimeHour.getSelectionModel().select(startHour);
       startTimeMinutes.getSelectionModel().select(startMinute);
       String end = getSelectedAppointment1().getEndDate().getValue();
       String endHour = end.substring(11, 13);
       String endMinute = end.substring(14, 16);
       endTimeHour.getSelectionModel().select(endHour);
       endTimeMinutes.getSelectionModel().select(endMinute);
               }
       
        typeComboBox.getItems().add(rbw.getString("consultation")); 
        typeComboBox.getItems().add(rbw.getString("sale"));
         startTimeMinutes.getItems().add("00");
        startTimeMinutes.getItems().add("15");
        startTimeMinutes.getItems().add("30");
        startTimeMinutes.getItems().add("45");
        
         endTimeMinutes.getItems().add("00");
         endTimeMinutes.getItems().add("15");
         endTimeMinutes.getItems().add("30");
         endTimeMinutes.getItems().add("45");
         
           int i = 1;
         do {
            startTimeHour.getItems().add("0" + Integer.toString(i));
            endTimeHour.getItems().add("0" + Integer.toString(i));
            i++;
        } while (i < 10);
        
        do {
            startTimeHour.getItems().add(Integer.toString(i));
            endTimeHour.getItems().add(Integer.toString(i));
            i++;
        } while (i < 24);  
// TODO
    }    
}

