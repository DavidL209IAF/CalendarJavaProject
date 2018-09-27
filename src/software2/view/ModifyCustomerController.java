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
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import software2.Software2;
import software2.model.Customers;
import static software2.view.CustomersController.getSelectedCustomer;
import static software2.view.LoginController.conn;

/**
 * FXML Controller class
 *
 * @author David
 */
public class ModifyCustomerController implements Initializable {

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
    String cityIdResult;
    String error = "";
     error = CreateCustomerController.customerValidation(nameTextField.getText(), address1TextField.getText(), countryComboBox, 
               cityComboBox, postalCodeTextField.getText(), phoneNumberTextField.getText());
        if (error.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(rbw.getString("errCusMsg"));
                alert.setHeaderText(rbw.getString("errorT"));
                alert.setContentText(error);
                alert.showAndWait();
                error = "";
            } else {
    
    try {
        
              PreparedStatement ps1 = conn.prepareStatement("SELECT cityId from city where city = ?");
              ps1.setString(1, cityComboBox.getValue().toString());
              ResultSet cityResult = ps1.executeQuery();
              cityResult.next();
              cityIdResult = cityResult.getString(1);
        
              PreparedStatement ps3 = conn.prepareStatement("SELECT countryId from country where country = ?");
              ps3.setString(1,countryComboBox.getValue().toString());
              ResultSet countryResult = ps3.executeQuery();
              countryResult.next();
              String countryIdResult = countryResult.getString(1);
        
        
        
        PreparedStatement updateCustomer = conn.prepareStatement ("UPDATE customer SET customerName = ?, lastUpdate = ?, lastUpdateBy = ? WHERE customerId = ?");
        updateCustomer.setString(1, nameTextField.getText());
        updateCustomer.setString(2, LocalDateTime.now().toString());
        updateCustomer.setString(3, LoginController.getUserName());
        updateCustomer.setString(4, getSelectedCustomer().getCustomerId().getValue());
        updateCustomer.executeUpdate();
        
        PreparedStatement ps2 = conn.prepareStatement("UPDATE address SET address = ?, address2 = ?, cityId = ?, postalCode = ?, phone = ?, lastUpdate = ?, lastUpdateBy = ? WHERE addressId = ?");
                  
                      ps2.setString(1, address1TextField.getText());
                      ps2.setString(2, address2TextField.getText());
                      ps2.setString(3, cityIdResult);
                      ps2.setString(4, postalCodeTextField.getText());
                      ps2.setString(5, phoneNumberTextField.getText());
                      ps2.setString(6, LocalDateTime.now().toString());
                      ps2.setString(7, LoginController.getUserName());
                      ps2.setString(8, getSelectedCustomer().getAddressId().getValue());
                      
           
           ps2.executeUpdate();
       Customers finalCustomer = new Customers(new SimpleStringProperty(getSelectedCustomer().getCustomerId().getValue()), new SimpleStringProperty(nameTextField.getText()), new SimpleStringProperty(CustomersController.getSelectedCustomer().getAddressId().getValue()), new SimpleStringProperty("1"),
       new SimpleStringProperty(address1TextField.getText()), new SimpleStringProperty(address2TextField.getText()), new SimpleStringProperty(cityIdResult), new SimpleStringProperty(postalCodeTextField.getText()), new SimpleStringProperty(phoneNumberTextField.getText()), 
               new SimpleStringProperty(cityComboBox.getValue().toString()), new SimpleStringProperty(countryIdResult), new SimpleStringProperty(countryComboBox.getValue().toString()) );
       
       Customers.updateCustomer(CustomersController.getCustomerModifyIndex(), finalCustomer);
       
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(rbw.getString("Success"));
            alert.setHeaderText(rbw.getString("CuSuccess"));
            alert.showAndWait();
           Stage stage = (Stage) saveButton.getScene().getWindow(); 
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


  
    
    
    
    
    
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        setLanguage();
        populateChoiceBox();
        
        nameTextField.setText(getSelectedCustomer().getCustomerName().getValue());
        
        address1TextField.setText(getSelectedCustomer().getAddress().getValue());
        address2TextField.setText(getSelectedCustomer().getAddress2().getValue());
        countryComboBox.getSelectionModel().select(getSelectedCustomer().getCountry().getValue());
        postalCodeTextField.setText(getSelectedCustomer().getPostalCode().getValue());
        phoneNumberTextField.setText(getSelectedCustomer().getPhone().getValue());
        
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
       cityComboBox.getSelectionModel().select(getSelectedCustomer().getCity().getValue());
        
    }
    
}
