/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software2.view;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import software2.Software2;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import software2.model.Customers;
import java.sql.Statement;
import static software2.view.LoginController.conn;
import java.sql.SQLException;
import java.sql.ResultSet;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import static software2.view.LoginController.conn;
import java.time.ZonedDateTime;
import java.time.LocalDateTime;
import java.util.Optional;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import software2.model.Address;
import java.util.regex.Pattern;


/**
 * FXML Controller class
 *
 * @author David
 */
public class CreateCustomerController implements Initializable {

    @FXML
    private Label customerNameLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label countryLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label phoneNumberLabel;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField address1TextField;
    @FXML
    private TextField address2TextField;
    @FXML
    private ComboBox<String> countryComboBox;
    @FXML
    private ComboBox<String> cityComboBox;
    @FXML
    private TextField postalCodeTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private Button saveButton;
     @FXML
    private Button cancelButton;
    
    
    
    //for getRecentIds
    
    private String lastCustomerId;
    private String lastAddressId;
    
   //  private  ObservableList<Customers> tempCustomerList = FXCollections.observableArrayList();
     
  //   public void setCustomerData(ObservableList<Customers> finalList) {
  //      this.tempCustomerList = finalList ;
  //  }
    
  
   static ResourceBundle rbw = Software2.getResource();

    
    private void setLanguage() {
    
     customerNameLabel.setText(rbw.getString("CusName"));
     addressLabel.setText(rbw.getString("Add"));
     countryLabel.setText(rbw.getString("Cou"));
     cityLabel.setText(rbw.getString("Ci"));
     postalCodeLabel.setText(rbw.getString("Post"));
     phoneNumberLabel.setText(rbw.getString("Ph"));
     nameTextField.setPromptText(rbw.getString("Name"));
     saveButton.setText(rbw.getString("Save"));
     cancelButton.setText(rbw.getString("cancel"));
    
     
     }
    @FXML
    private void saveButtonHandler(ActionEvent event){
        
        String customerName = nameTextField.getText();
        String address1 = address1TextField.getText();
        String address2 = address2TextField.getText();
        String postalCode = postalCodeTextField.getText();
        String phone = phoneNumberTextField.getText();
        String error = "";
        String lastCId = getlastCustomerId();
        String lastAId = getlastAddressId();

        
 
       error = customerValidation(customerName, address1, countryComboBox, cityComboBox, postalCode, phone);
        if (error.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(rbw.getString("errCusMsg"));
                alert.setHeaderText(rbw.getString("errorT"));
                alert.setContentText(error);
                alert.showAndWait();
                error = "";
            } else {
        
            String country = countryComboBox.getValue().toString();
            String city = cityComboBox.getValue().toString();
            
            try {
             
              PreparedStatement ps1 = conn.prepareStatement("SELECT cityId from city where city = ?");
              ps1.setString(1,city);
              ResultSet cityResult = ps1.executeQuery();
              cityResult.next();
              String cityIdResult = cityResult.getString(1);
              
              PreparedStatement ps3 = conn.prepareStatement("SELECT countryId from country where country = ?");
              ps3.setString(1,country);
              ResultSet countryResult = ps3.executeQuery();
              countryResult.next();
              String countryIdResult = countryResult.getString(1);
                
                
                
                PreparedStatement ps = conn.prepareStatement("INSERT INTO customer (customerId, customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy)"
                     + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                      //  + "//lastCId, customerName, lastAddressId, 1, ZonedDateTime.now(), LoginController.getUserName(), ZonedDateTime.now(), LoginController.getUserName())"
                      ps.setString(1, lastCId);
                      ps.setString(2, customerName);
                      ps.setString(3, lastAId);
                      ps.setString(4, "1");
                      ps.setString(5, LocalDateTime.now().toString());
                      ps.setString(6, LoginController.getUserName());
                      ps.setString(7, LocalDateTime.now().toString());
                      ps.setString(8, LoginController.getUserName());
                      

                      
           ps.executeUpdate();
           
           PreparedStatement ps2 = conn.prepareStatement("INSERT INTO address (addressId, address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy)"
                     + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
           //+ " VALUES (lastAId, address1, address2, cityId, postalCode, phone, ZonedDateTime.now(), LoginController.getUserName(), ZonedDateTime.now(), LoginController.getUserName())");
                      ps2.setString(1, lastAId);
                      ps2.setString(2, address1);
                      ps2.setString(3, address2);
                      ps2.setString(4, cityIdResult);
                      ps2.setString(5, postalCode);
                      ps2.setString(6, phone);
                      ps2.setString(7, LocalDateTime.now().toString());
                      ps2.setString(8, LoginController.getUserName());
                      ps2.setString(9, LocalDateTime.now().toString());
                      ps2.setString(10, LoginController.getUserName());
           
           ps2.executeUpdate();
           Customers finalCustomer = new Customers(new SimpleStringProperty(lastCId), new SimpleStringProperty(customerName),
                    new SimpleStringProperty(lastAId), new SimpleStringProperty("1"), new SimpleStringProperty(address1), 
                  new SimpleStringProperty(address2), new SimpleStringProperty(cityIdResult),
                    new SimpleStringProperty(postalCode), new SimpleStringProperty(phone), 
            new SimpleStringProperty(city), new SimpleStringProperty(countryIdResult), 
                    new SimpleStringProperty(country));
          
          Customers.addCustomer(finalCustomer);
           
       
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(rbw.getString("Success"));
            alert.setHeaderText(rbw.getString("CuSuccess"));
            alert.showAndWait();
            
            Stage stage = (Stage)saveButton.getScene().getWindow();
            stage.close();
            customerView();
          
           
           
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
            customerView();
    }
    }
    
     
    private void customerView() {
   
        
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
    
    private void populateChoiceBox() {
        
        Statement stmt;
        ResultSet countryResult = null;
        countryComboBox.getItems().clear();
        
        try {
            
            stmt=conn.createStatement();
            countryResult=stmt.executeQuery("select country from country;");
            
            while (countryResult.next()){
               countryComboBox.getItems().add(countryResult.getString(1));
               
            }
               
        }
        catch (SQLException ex) {
                ex.printStackTrace();
            }
        
        
        
    }
    @FXML
   private void countryComboBoxAction(ActionEvent event) {

       cityComboBox.getItems().clear();
       String country = countryComboBox.getValue();
       Statement stmt;
      ResultSet cityResult = null;
     
         String sql = "SELECT city.city "
                + "FROM city, country "
                + "WHERE city.countryId = country.countryId "
                + "AND country.country = \"" + country + "\"";
         
         
       try {
           stmt=conn.createStatement();
           cityResult = stmt.executeQuery(sql);
         while (cityResult.next()) {
             
             cityComboBox.getItems().add(cityResult.getString(1));
         }
                 
           
       }
         catch (SQLException ex) {
                ex.printStackTrace();
            }
    
   }  


   public static String customerValidation(String customerName, String address1, ComboBox countrySize, ComboBox citySize, String postalCode, String phoneNumber) {
       String regex = "\\d+";
       Pattern pattern = Pattern.compile(regex);
       

       String error = "";
        if (customerName.length() == 0) {
            error = rbw.getString("errMsg1");
        }
        if (address1.length() == 0) {
            error = rbw.getString("errMsg2");
        }
       
        if (postalCode.length() == 0 && pattern.matcher(postalCode).matches() == false) {
            error = rbw.getString("errMsg3");
            
        }
        if (pattern.matcher(postalCode).matches() == false) {
            error = rbw.getString("errMsg3");
            
        }
        if (phoneNumber.length() == 0 && pattern.matcher(phoneNumber).matches() == false) {
            error = rbw.getString("errMsg4");
        }
        
        if (pattern.matcher(phoneNumber).matches() == false) {
            error = rbw.getString("errMsg4");
        }
        
        if (countrySize.getSelectionModel().isEmpty() == true | citySize.getSelectionModel().isEmpty() == true) {
            error = rbw.getString("errMsg5");
        }
       
        return error;
    }    
   
   
    public String getlastCustomerId() {
        return lastCustomerId;
    }

    public void setlastCustomerId(String customerId) {
        this.lastCustomerId = customerId;
    }
    public String getlastAddressId() {
        return lastAddressId;
    }

    public void setlastAddressId(String addressId) {
        this.lastAddressId = addressId;
    }
    
   
       
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLanguage();
       populateChoiceBox();
    
       try {
       Statement stmt = conn.createStatement();
       ResultSet rs = stmt.executeQuery("SELECT MAX(customerId) from customer");
       rs.next();
       int tempCId = Integer.parseInt(rs.getString(1));
       tempCId++;
       setlastCustomerId(Integer.toString(tempCId));
      
       Statement stmt1 = conn.createStatement();
       ResultSet rs1 = stmt1.executeQuery("SELECT MAX(addressId) from address");
       rs1.next();
       int tempAId = Integer.parseInt(rs1.getString(1));
       tempAId++;
       setlastAddressId(Integer.toString(tempAId));
       }
       
       catch (SQLException ex) {
                ex.printStackTrace();
            }
       
    }
    
}
