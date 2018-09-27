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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import software2.Software2;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import software2.model.Appointment;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import static software2.view.CalendarWeeklyController.selectedAppointment;
import static software2.view.LoginController.conn;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;


/**
 * FXML Controller class
 *
 * @author David
 */
public class CalendarMonthlyController implements Initializable {


    @FXML
    private Button weeklyViewButton;
    @FXML
    private Button calendarButton;
    @FXML
    private Button customersButton;
    @FXML
    private Button reportsButton;
    @FXML
    private Button logoutButton;
      @FXML
    private Button createAppointmentButton;
     @FXML
    private Button modifyAppointmentButton;
     @FXML
    private Button deleteAppointmentButton;
      @FXML
    private Button applyFilterButton;
     @FXML
     private Label monthLabel;
     @FXML
     private Label yearLabel;
     @FXML
     private ChoiceBox monthChoiceBox;
     @FXML
     private ChoiceBox yearChoiceBox;
    
    @FXML
    private TableView<Appointment> calendarTable;
    @FXML
    private TableColumn<Appointment, String> titleColumn;
    @FXML
    private TableColumn<Appointment, String> descriptionColumn;
    @FXML
    private TableColumn<Appointment, String> locationColumn;
    @FXML
    private TableColumn<Appointment, String> contactColumn;
    @FXML
    private TableColumn<Appointment, String> typeColumn;
    @FXML
    private TableColumn<Appointment, String> urlColumn;
    @FXML
    private TableColumn<Appointment, String> startTimeColumn;
    @FXML
    private TableColumn<Appointment, String> endTimeColumn;
    
    public static Appointment selectedAppointment;
    private static int modifyAppointmentIndex;
   private static String year;
   private static int selectedMonth;
    
    ResourceBundle rbw = Software2.getResource();
 
     //set Page Language
     private void setLanguage() {
    
     weeklyViewButton.setText(rbw.getString("Weekly")); 
     customersButton.setText(rbw.getString("Customers"));
     calendarButton.setText(rbw.getString("Calendar"));
     createAppointmentButton.setText(rbw.getString("createAppointment"));
     modifyAppointmentButton.setText(rbw.getString("modifyAppointment"));
     deleteAppointmentButton.setText(rbw.getString("deleteAppointment"));
     applyFilterButton.setText(rbw.getString("applyFilter"));
     reportsButton.setText(rbw.getString("Reports"));
     logoutButton.setText(rbw.getString("exit"));
     titleColumn.setText(rbw.getString("Title"));
     descriptionColumn.setText(rbw.getString("Description"));
     locationColumn.setText(rbw.getString("Location"));
     contactColumn.setText(rbw.getString("Contact"));
     urlColumn.setText(rbw.getString("URL"));
     startTimeColumn.setText(rbw.getString("Start"));
     endTimeColumn.setText(rbw.getString("End"));
     monthLabel.setText(rbw.getString("monthLabel"));
     yearLabel.setText(rbw.getString("year"));
     monthChoiceBox.getItems().add(rbw.getString("jan"));
     monthChoiceBox.getItems().add(rbw.getString("feb"));
     monthChoiceBox.getItems().add(rbw.getString("mar"));
     monthChoiceBox.getItems().add(rbw.getString("april"));
     monthChoiceBox.getItems().add(rbw.getString("may"));
     monthChoiceBox.getItems().add(rbw.getString("june"));
     monthChoiceBox.getItems().add(rbw.getString("july"));
     monthChoiceBox.getItems().add(rbw.getString("aug"));
     monthChoiceBox.getItems().add(rbw.getString("sep"));
     monthChoiceBox.getItems().add(rbw.getString("oct"));
     monthChoiceBox.getItems().add(rbw.getString("nov"));
     monthChoiceBox.getItems().add(rbw.getString("dec"));
     int setYear = 2018;
     do {
            yearChoiceBox.getItems().add(String.valueOf(setYear));
            setYear++;
        } while (setYear < 2031);
    }
     
    public static int getAppointmentModifyIndex1() {
        return modifyAppointmentIndex;
    }
      
   public static Appointment getSelectedAppointment1() {
        return selectedAppointment;
    } 
     
    //SELECT * FROM appointment 
   //WHERE MONTH(start) = 1 AND YEAR(start) = 2018
      @FXML
     private void createAppointmentView(ActionEvent event){
        Parent main = null;
        try {
            main = FXMLLoader.load(getClass().getResource("createAppointment.fxml"));
            Scene scene = new Scene(main);

            Stage stage = Software2.getStage();
            stage.setTitle(rbw.getString("createAppointment"));
            stage.setScene(scene);

            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
     
     }
   
      @FXML
     private void modifyAppointmentView(ActionEvent event){
         selectedAppointment = calendarTable.getSelectionModel().getSelectedItem();
         
           if (selectedAppointment == null) {
         
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(rbw.getString("me5"));
                alert.setHeaderText("Error");
                alert.setContentText(rbw.getString("modifyAError"));
                alert.showAndWait();
        } else {
        
        Parent main = null;
        try {
            main = FXMLLoader.load(getClass().getResource("ModifyAppointment.fxml"));
            Scene scene = new Scene(main);
            modifyAppointmentIndex = Appointment.getCompleteAppointmentList().indexOf(selectedAppointment);
            
            Stage stage = Software2.getStage();
            stage.setTitle(rbw.getString("modifyAppointment"));
            stage.setScene(scene);

            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
     
     }
     }
     
     
           @FXML
    private void deleteAppointmentHandler(ActionEvent event) throws Exception {
        
        selectedAppointment = calendarTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null) {
             
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle(rbw.getString("me4"));
                alert1.setHeaderText("Error");
                alert1.setContentText(rbw.getString("errDelA"));
                alert1.showAndWait();
            
        } else {
       Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle(rbw.getString("Confirmation"));
        alert.setHeaderText(rbw.getString("Confirmation"));
        alert.setContentText(rbw.getString("delApp"));
        Optional<ButtonType> choice = alert.showAndWait();
        if (choice.get() == ButtonType.OK) {
            
            
            
            
            String selectedAppointmentId = selectedAppointment.getAppointmentId().getValue();
           
            
          try {
              
             PreparedStatement ps = conn.prepareStatement("DELETE FROM appointment where appointmentId = ?;");
             ps.setString(1, selectedAppointmentId);
             ps.executeUpdate();
           
        
            Appointment.removeAppointment(selectedAppointment);
            calendarTable.getItems().clear();
            accessDB();
           // calendarTable.getItems().addAll(Appointment.getCompleteAppointmentList());
           
            
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle(rbw.getString("Success"));
            alert2.setHeaderText(rbw.getString("delSuccessA"));
            alert2.showAndWait();
              
          }
           catch (SQLException ex) {
                ex.printStackTrace();
            }
            
       
        

        } else {
            System.out.println(rbw.getString("canDelApp"));
        }
        }
   }
 
    @FXML
    private void weeklyView(ActionEvent event) {
   
        
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
    
     @FXML
     private void calendarView(ActionEvent event) {
     
        Parent main = null;
        try {
            main = FXMLLoader.load(getClass().getResource("CalendarMonthly.fxml"));
            Scene scene = new Scene(main);

            Stage stage = Software2.getStage();
            
            stage.setScene(scene);

            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    
     }
   @FXML 
  private void reportsView(ActionEvent event) {
     
      Parent main = null;
        try {
            main = FXMLLoader.load(getClass().getResource("Reports.fxml"));
            Scene scene = new Scene(main);

            Stage stage = Software2.getStage();
            
            stage.setScene(scene);

            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
     } 
     
  @FXML 
  private void customersView(ActionEvent event) {
     
      Parent main = null;
        try {
            main = FXMLLoader.load(getClass().getResource("Customers.fxml"));
            Scene scene = new Scene(main);

            Stage stage = Software2.getStage();
            
            stage.setScene(scene);

            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    
     }   
     
    
    @FXML
     private void monthView(ActionEvent event) {
     
        Parent main = null;
        try {
            main = FXMLLoader.load(getClass().getResource("CalendarMonthly.fxml"));
            Scene scene = new Scene(main);

            Stage stage = Software2.getStage();
            
            stage.setScene(scene);

            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    
     }
     
    
     @FXML
     private void logoutButton(ActionEvent event) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle(rbw.getString("Confirmation"));
        alert.setHeaderText(rbw.getString("exit"));
        alert.setContentText(rbw.getString("exMsg"));
        Optional<ButtonType> choice = alert.showAndWait();

        if (choice.get() == ButtonType.OK) {
                     Parent main = null;
       System.exit(0);
   }}
     
       public void accessDB() {
       
       Appointment.clearAppointmentList();
        ResultSet appointmentResult = null;
        String selectedYear =yearChoiceBox.getSelectionModel().getSelectedItem().toString();
        selectedMonth = monthChoiceBox.getSelectionModel().getSelectedIndex();
        selectedMonth++;
        
        
        try{
       Appointment.getCompleteAppointmentList().clear();
      
      
       PreparedStatement ps1 = conn.prepareStatement("SELECT appointment.appointmentId, appointment.customerId, appointment.userId, appointment.title, appointment.description, appointment.location,"
               + " appointment.contact, appointment.type,\n" +
"appointment.url, appointment.start, appointment.end, customer.customerName, user.userName\n" +
" FROM appointment LEFT JOIN customer ON appointment.customerId= customer.customerId INNER JOIN user ON appointment.userId = user.userId"
               + " where userName = ? AND MONTH(start) = ? AND YEAR(start) = ? order by start Asc");
       ps1.setString(1,LoginController.getUserName());
      // ps1.setString(2,String.valueOf(selectedMonth));
       //System.out.println(selectedMonth);
       //System.out.println(finalStartDate);
      // System.out.println(finalEndDate);
       ps1.setString(2, String.valueOf(selectedMonth));
       ps1.setString(3, selectedYear);
       appointmentResult = ps1.executeQuery();
       
       
       
       while(appointmentResult.next()){
           
           String string1 = appointmentResult.getString(1);
           String string2 = appointmentResult.getString(2);
           String string3 = appointmentResult.getString(3);
           String string4 = appointmentResult.getString(4);
           String string5 = appointmentResult.getString(5);
           String string6 = appointmentResult.getString(6);
           String string7 = appointmentResult.getString(7);
           String string8 = appointmentResult.getString(8);
           String string9 = appointmentResult.getString(9);
           Timestamp string10 = appointmentResult.getTimestamp(10);
          
            //convert time
           DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss.S"); 
         ZoneId newzid = ZoneId.systemDefault();

	// ZonedDateTime newzdtStart = string10.toLocalDateTime().atZone(ZoneId.of("UTC"));
        ZonedDateTime newLocalStart = string10.toLocalDateTime().atZone(newzid);

	// ZonedDateTime newLocalStart = newzdtStart.withZoneSameInstant(newzid);
         LocalDateTime preDate1 = newLocalStart.toLocalDateTime();
           String finalDate1 = preDate1.toString().substring(0,10) + " " + preDate1.toString().substring(11,16);
           Timestamp string11 = appointmentResult.getTimestamp(11);
          //ZonedDateTime newzdtEnd = string11.toLocalDateTime().atZone(ZoneId.of("UTC"));
          ZonedDateTime newLocalEnd = string11.toLocalDateTime().atZone(newzid);

	  //ZonedDateTime newLocalEnd = newzdtEnd.withZoneSameInstant(newzid);
          LocalDateTime preDate2 = newLocalEnd.toLocalDateTime();
           String finalDate2 = preDate2.toString().substring(0,10) + " " + preDate2.toString().substring(11,16);
           String string12 = appointmentResult.getString(12);
           String string13 = appointmentResult.getString(13);
            //10 and 11 are time
           
           
            Appointment tempAppointment = new Appointment(new SimpleStringProperty(string1), new SimpleStringProperty(string2),
                    new SimpleStringProperty(string3), new SimpleStringProperty(string4),
                    new SimpleStringProperty(string5), 
                  new SimpleStringProperty(string6), new SimpleStringProperty(string7),
                    new SimpleStringProperty(string8), new SimpleStringProperty(string9), 
            new SimpleStringProperty(finalDate1.toString()), new SimpleStringProperty(finalDate2.toString()), 
                    new SimpleStringProperty(string12), new SimpleStringProperty(string13));
           Appointment.addAppointment(tempAppointment);
        }
   
     
    }    
    //insert into appointment (appointmentId, customerId, userId, title, description, location, contact, type, url, start, end) values (1, 1, 1, "Test Appointment", "This appointment is a test.", "Office", "John Doe", "Sale", "testUrl.com/JohnDoe", '2018-01-05 9:00:00', '2018-01-05 9:15:00');
    catch (SQLException ex) {
                ex.printStackTrace();
            }
     }
       
       public void populateTable(){
           calendarTable.setItems(Appointment.getCompleteAppointmentList());
           //the following expressions make it easy to have all the data grabbed from the list to populate the column
           //Grabs all titles from the appointments list and adds the values to the column
        titleColumn.setCellValueFactory(cellData -> {
            return cellData.getValue().getTitle();
        });
                   //Grabs all descriptions from the appointments list and adds the values to the column
        descriptionColumn.setCellValueFactory(cellData -> {
            return cellData.getValue().getDescription();
        });
                   //Grabs all locations from the appointments list and adds the values to the column
        locationColumn.setCellValueFactory(cellData -> {
            return cellData.getValue().getLocation();
        });
                   //Grabs all contacts from the appointments list and adds the values to the column
        contactColumn.setCellValueFactory(cellData -> {
            return cellData.getValue().getContact();
        });
                   //Grabs all types from the appointments list and adds the values to the column
        typeColumn.setCellValueFactory(cellData -> {
            return cellData.getValue().getType();
       });
                  //Grabs all urls from the appointments list and adds the values to the column
        urlColumn.setCellValueFactory(cellData -> {
            return cellData.getValue().getUrl();
        });
                   //Grabs all startTimes from the appointments list and adds the values to the column
        startTimeColumn.setCellValueFactory(cellData -> {
            return cellData.getValue().getStartDate();
        });
                   //Grabs all endTimes from the appointments list and adds the values to the column
        endTimeColumn.setCellValueFactory(cellData -> {
            return cellData.getValue().getEndDate();
        });
        
       }  
     
     @FXML
     private void applyFilter(ActionEvent event){
        accessDB();
        populateTable();
     }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setLanguage();
        LocalDateTime currentDate = LocalDateTime.now();
        String currMonth = (currentDate.toString()).substring(6,7);
        String currYear = (currentDate.toString()).substring(0,4);
        monthChoiceBox.getSelectionModel().select(Integer.parseInt(currMonth)-1);
        yearChoiceBox.getSelectionModel().select(currYear);
        accessDB();
        populateTable();
    }    
    
}
