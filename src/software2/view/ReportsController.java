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
import java.time.LocalDateTime;
import java.time.ZoneId;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;

import javafx.stage.Modality;
import javafx.stage.Stage;
import software2.Software2;
import software2.model.Appointment;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.ChoiceBox;
import static software2.view.LoginController.conn;

/**
 * FXML Controller class
 *
 * @author David
 */
public class ReportsController implements Initializable {

    
   
    @FXML
    private Button calendarButton;
    @FXML
    private Button customersButton;
    @FXML
    private Button reportsButton;
    @FXML
    private Button logoutButton;
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
    private TableColumn<Appointment, String> urlColumn;
    @FXML
    private TableColumn<Appointment, String> startTimeColumn;
    @FXML
    private TableColumn<Appointment, String> endTimeColumn;
    @FXML
    private TableColumn<Appointment, String> typeColumn;
    @FXML
    private Button runReportButton;
    @FXML
    private Button appointmentByTypeButton;
    @FXML
    private Button scheduleReportButton;
    @FXML
    private Button deletedAppointmentsButton;
    @FXML
    private Label typeLabel;
    @FXML
    private ChoiceBox typeChoiceBox;
    @FXML
    private Label monthLabel;
    @FXML
    private ChoiceBox monthChoiceBox;
    
    ResourceBundle rbw = Software2.getResource();
    private static int selectedMonth;
 
     //set Page Language
     private void setLanguage() {
    
     
     calendarButton.setText(rbw.getString("Calendar"));
     customersButton.setText(rbw.getString("Customers"));
     reportsButton.setText(rbw.getString("Reports"));
     logoutButton.setText(rbw.getString("exit"));
     titleColumn.setText(rbw.getString("Title"));
     descriptionColumn.setText(rbw.getString("Description"));
     locationColumn.setText(rbw.getString("Location"));
     contactColumn.setText(rbw.getString("Contact"));
     urlColumn.setText(rbw.getString("URL"));
     startTimeColumn.setText(rbw.getString("Start"));
     endTimeColumn.setText(rbw.getString("End"));
     typeColumn.setText(rbw.getString("Type"));
     runReportButton.setText(rbw.getString("Run"));
     appointmentByTypeButton.setText(rbw.getString("ByType"));
     scheduleReportButton.setText(rbw.getString("Schedule"));
     deletedAppointmentsButton.setText(rbw.getString("Deleted"));
     typeLabel.setText(rbw.getString("ty"));
     monthLabel.setText(rbw.getString("monthLabel"));
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
     
    
     }
    
         @FXML 
  private void rep3View(ActionEvent event) {
     
          Parent main = null;
        try {
            main = FXMLLoader.load(getClass().getResource("Rep3.fxml"));
            Scene scene = new Scene(main);

            Stage stage = Software2.getStage();
            
            stage.setScene(scene);
            stage.setTitle(rbw.getString("Deleted"));

            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    
     }
    
      @FXML 
  private void scheduleView(ActionEvent event) {
     
          Parent main = null;
        try {
            main = FXMLLoader.load(getClass().getResource("Schedule.fxml"));
            Scene scene = new Scene(main);

            Stage stage = Software2.getStage();
            
            stage.setScene(scene);
            stage.setTitle(rbw.getString("Schedule"));

            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    
     }
    
    @FXML 
  private void calendarView(ActionEvent event) {
     
          Parent main = null;
        try {
            main = FXMLLoader.load(getClass().getResource("CalendarWeekly.fxml"));
            Scene scene = new Scene(main);

            Stage stage = Software2.getStage();
            
            stage.setScene(scene);
            stage.setTitle(rbw.getString("Calendar"));

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
            stage.setTitle(rbw.getString("ByType"));

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
            stage.setTitle(rbw.getString("Customers"));

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
        String selectedType = typeChoiceBox.getSelectionModel().getSelectedItem().toString();
        selectedMonth = monthChoiceBox.getSelectionModel().getSelectedIndex();
        selectedMonth++;
              try{
       Appointment.getCompleteAppointmentList().clear();
      
      
       PreparedStatement ps1 = conn.prepareStatement("SELECT appointment.appointmentId, appointment.customerId, appointment.userId, appointment.title, appointment.description, appointment.location,"
               + " appointment.contact, appointment.type,\n" +
"appointment.url, appointment.start, appointment.end, customer.customerName, user.userName\n" +
" FROM appointment LEFT JOIN customer ON appointment.customerId= customer.customerId INNER JOIN user ON appointment.userId = user.userId"
               + " where type = ? AND MONTH(start) = ? order by start Asc");
       ps1.setString(1, selectedType);
       ps1.setString(2, String.valueOf(selectedMonth));
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
     
     
     
     
     
      private void populateChoiceBox() {
        
        typeChoiceBox.getItems().add(rbw.getString("consultation")); 
        typeChoiceBox.getItems().add(rbw.getString("sale"));
     
    }
          public void populateTable(){
           calendarTable.setItems(Appointment.getCompleteAppointmentList());
        //uses getter method from appointment model to populate the column
           titleColumn.setCellValueFactory(cellData -> {
            return cellData.getValue().getTitle();
        });
        
        //uses getter method from appointment model to populate the column
        descriptionColumn.setCellValueFactory(cellData -> {
            return cellData.getValue().getDescription();
        });
        //uses getter method from appointment model to populate the column
        locationColumn.setCellValueFactory(cellData -> {
            return cellData.getValue().getLocation();
        });
        //uses getter method from appointment model to populate the column
        contactColumn.setCellValueFactory(cellData -> {
            return cellData.getValue().getContact();
        });
        //uses getter method from appointment model to populate the column
        typeColumn.setCellValueFactory(cellData -> {
            return cellData.getValue().getType();
       });
       //uses getter method from appointment model to populate the column
        urlColumn.setCellValueFactory(cellData -> {
            return cellData.getValue().getUrl();
        });
        //uses getter method from appointment model to populate the column
        startTimeColumn.setCellValueFactory(cellData -> {
            return cellData.getValue().getStartDate();
        });
        //uses getter method from appointment model to populate the column
        endTimeColumn.setCellValueFactory(cellData -> {
            return cellData.getValue().getEndDate();
        });
        //uses getter method from appointment model to populate the column
          typeColumn.setCellValueFactory(cellData -> {
            return cellData.getValue().getType();
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
        populateChoiceBox();
        LocalDateTime currentDate = LocalDateTime.now();
        String currMonth = (currentDate.toString()).substring(6,7);
        typeChoiceBox.getSelectionModel().selectFirst();
        monthChoiceBox.getSelectionModel().select(Integer.parseInt(currMonth)-1);
        accessDB();
        populateTable();
    }    
    
}
