/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software2.view;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
import software2.model.Customers;
import software2.model.Address;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static software2.view.LoginController.conn;
import java.sql.PreparedStatement;
import java.util.HashSet;
import java.util.Set;
import javafx.scene.input.MouseEvent;


/**
 * FXML Controller class
 *
 * @author David
 */
public class CustomersController implements Initializable {

    
   
    @FXML
    private Button calendarButton;
    @FXML
    private Button customersButton;
    @FXML
    private Button reportsButton;
    @FXML
    private Button logoutButton;
    @FXML 
    private Button createCustomerButton;
    @FXML 
    private Button modifyCustomerButton;
    @FXML 
    private Button deleteCustomerButton;
    
    @FXML
    private  TableView<Customers> customersTable;
    @FXML
    private TableColumn<Customers, String> nameColumn;
    @FXML
    private TableColumn<Customers, String> phoneColumn;
    @FXML
    private TableColumn<Customers, String> addressColumn;
    @FXML
    private TableColumn<Customers, String> address2Column;
    @FXML
    private TableColumn<Customers, String> cityColumn;
    @FXML
    private TableColumn<Customers, String> postalCodeColumn;
    @FXML
    private TableColumn<Customers, String> countryColumn;
    
    public static Customers selectedCustomer;
    private static int modifyCustomerIndex;
    Stage stage;
    
   
    ObservableList<Address> addressList = FXCollections.observableArrayList();
 
    
    ResourceBundle rbw = Software2.getResource();
 
     //set Page Language
     private void setLanguage() {
    
    
     calendarButton.setText(rbw.getString("Calendar"));
     customersButton.setText(rbw.getString("Customers"));
     reportsButton.setText(rbw.getString("Reports"));
     logoutButton.setText(rbw.getString("exit"));
     createCustomerButton.setText(rbw.getString("CreateCustomer"));
     modifyCustomerButton.setText(rbw.getString("ModifyCustomer"));
     deleteCustomerButton.setText(rbw.getString("DeleteCustomer"));
     nameColumn.setText(rbw.getString("Name"));
     phoneColumn.setText(rbw.getString("Phone"));
     addressColumn.setText(rbw.getString("Address"));
     cityColumn.setText(rbw.getString("City"));
     postalCodeColumn.setText(rbw.getString("Postal"));
     countryColumn.setText(rbw.getString("Country"));
     }
     
     public static int getCustomerModifyIndex() {
        return modifyCustomerIndex;
    }
     

 
       
     @FXML
     private void createCustomerView(ActionEvent event){
        Parent main = null;
        try {
            main = FXMLLoader.load(getClass().getResource("createCustomer.fxml"));
            Scene scene = new Scene(main);

            Stage stage = Software2.getStage();
            stage.setTitle(rbw.getString("CreateCustomer"));
            stage.setScene(scene);

            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    
         
         
   
         
     }
     
    
     
    @FXML
     private void modifyCustomerView(ActionEvent event){
        
        selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
         
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(rbw.getString("me2"));
                alert.setHeaderText("Error");
                alert.setContentText(rbw.getString("modifyError"));
                alert.showAndWait();
        } else {
        
            
           
          Parent main = null;
        try {
            main = FXMLLoader.load(getClass().getResource("ModifyCustomer.fxml"));
            Scene scene = new Scene(main);

            modifyCustomerIndex = Customers.getCompleteCustomerList().indexOf(selectedCustomer);
            Stage stage = Software2.getStage();
           stage.setTitle(rbw.getString("ModifyCustomer"));
            stage.setScene(scene);

            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
     
        }}


    
    @FXML 
  private void calendarView(ActionEvent event) {
     
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
    private void deleteCUstomertHandler(ActionEvent event) throws Exception {
        
        selectedCustomer = customersTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
             
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle(rbw.getString("me3"));
                alert1.setHeaderText("Error");
                alert1.setContentText(rbw.getString("errDel"));
                alert1.showAndWait();
            
        } else {
       Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle(rbw.getString("Confirmation"));
        alert.setHeaderText(rbw.getString("Confirmation"));
        alert.setContentText(rbw.getString("delCus"));
        Optional<ButtonType> choice = alert.showAndWait();
        if (choice.get() == ButtonType.OK) {
            
            
            
            
            String selectedCustomerId = selectedCustomer.getCustomerId().getValue();
            String selectedAddressId = selectedCustomer.getAddressId().getValue();
            
          try {
              
             PreparedStatement ps = conn.prepareStatement("DELETE FROM customer where customerId = ?;");
             ps.setString(1, selectedCustomerId);
             ps.executeUpdate();
           
            PreparedStatement ps1 = conn.prepareStatement("DELETE FROM address where addressId = ?;");
             ps1.setString(1, selectedAddressId);
             ps1.executeUpdate();
           
            Customers.removeCustomer(selectedCustomer);
            customersTable.getItems().clear();
            accessDB();
            customersTable.getItems().addAll(Customers.getCompleteCustomerList());
           
            
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle(rbw.getString("Success"));
            alert2.setHeaderText(rbw.getString("delSuccess"));
            alert2.showAndWait();
              
          }
           catch (SQLException ex) {
                ex.printStackTrace();
            }
            
       
        

        } else {
            System.out.println(rbw.getString("canDelCus"));
        }
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
         
        Statement stmt;
        ResultSet customerResult = null;
        

        try{
        Customers.getCompleteCustomerList().clear();
        stmt = conn.createStatement();
        
       
        customerResult=stmt.executeQuery("SELECT * FROM customer LEFT JOIN address ON customer.addressId = address.addressId INNER JOIN city ON address.cityId = city.cityId INNER JOIN country ON city.countryID = country.countryID order by customerName Asc;");
        while(customerResult.next()){
            Customers tempCustomer = new Customers(new SimpleStringProperty(customerResult.getString(1)), new SimpleStringProperty(customerResult.getString(2)),
                    new SimpleStringProperty(customerResult.getString(3)), new SimpleStringProperty(customerResult.getString(4)), new SimpleStringProperty(customerResult.getString(10)), 
                  new SimpleStringProperty(customerResult.getString(11)), new SimpleStringProperty(customerResult.getString(12)),
                    new SimpleStringProperty(customerResult.getString(13)), new SimpleStringProperty(customerResult.getString(14)), 
            new SimpleStringProperty(customerResult.getString(20)), new SimpleStringProperty(customerResult.getString(21)), 
                    new SimpleStringProperty(customerResult.getString(27)));
           Customers.addCustomer(tempCustomer);
        }
    }    
    
    catch (SQLException ex) {
                ex.printStackTrace();
            }
     }
     
    
     
      public static Customers getSelectedCustomer() {
        return selectedCustomer;
    } 
     
      
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        setLanguage();
       accessDB();
        //The following statements grab the respective data using the getter methods for the list and then populates the column
        nameColumn.setCellValueFactory(cellData -> {
            return cellData.getValue().getCustomerName();
        });
        //uses getter method from appointment model to populate the column
        phoneColumn.setCellValueFactory(cellData -> {
            return cellData.getValue().getPhone();
        });
        //uses getter method from appointment model to populate the column
        addressColumn.setCellValueFactory(cellData -> {
            return cellData.getValue().getAddress();
        });
        //uses getter method from appointment model to populate the column
        address2Column.setCellValueFactory(cellData -> {
            return cellData.getValue().getAddress2();
        });
        //uses getter method from appointment model to populate the column
        postalCodeColumn.setCellValueFactory(cellData -> {
            return cellData.getValue().getPostalCode();
       });
       //uses getter method from appointment model to populate the column
        cityColumn.setCellValueFactory(cellData -> {
            return cellData.getValue().getCity();
        });//uses getter method from appointment model to populate the column
        
        countryColumn.setCellValueFactory(cellData -> {
            return cellData.getValue().getCountry();
        });
        
        
        customersTable.getItems().addAll(Customers.getCompleteCustomerList());
       
        
        
        
       
    }    
    
}
