/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software2.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import software2.Software2;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javafx.scene.control.Alert;
import java.util.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.File;
import javafx.scene.control.RadioButton;
import static software2.Software2.getStage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * FXML Controller class
 *
 * @author David
 */
public class LoginController implements Initializable {

     @FXML
     private TextField loginUsername;
     @FXML
     private PasswordField loginPassword;
     @FXML
     private Label usernameLabel;
     @FXML
     private Label passwordLabel;
     @FXML
     private Label loginLabel;
     @FXML
     private Button loginButton;
     
     private static String user;
     private static String userId;
     
     @FXML
    private RadioButton engRadioButton;
     @FXML
    private RadioButton freRadioButton;
     
  @FXML
    private Software2 Software2;
   private Stage dialogStage;
     
     //for username, pass connection and connresult
     public static Connection conn = null;
     private boolean loginRes = false;
     
  
     
    
     @FXML
     public void setMainApp(Software2 Software2) {
        this.Software2 = Software2;

    }       
     //set Login Page Language
     private void setLanguage() {
    
     usernameLabel.setText(Software2.getResource().getString("Username"));
     passwordLabel.setText(Software2.getResource().getString("Password"));
     loginLabel.setText(Software2.getResource().getString("Login"));
     loginButton.setText(Software2.getResource().getString("Login"));
     loginUsername.setPromptText(Software2.getResource().getString("fuser"));
     loginPassword.setPromptText(Software2.getResource().getString("puser"));
     
     }
     
     
     private void accessDatabase() {
         
        // JDBC driver name and database URL
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	final String db_Url = "jdbc:mysql://52.206.157.109/U04TJO";

	//  Database credentials
	final String dbUser = "U04TJO";
	final String dbPass = "53688338214";
        
        try {
		//Register JDBC driver
		Class.forName("com.mysql.jdbc.Driver");

		//Open a connection
		System.out.println(Software2.getResource().getString("Attempting"));
                conn = DriverManager.getConnection(db_Url, dbUser, dbPass);
		
                System.out.println(Software2.getResource().getString("Succeed")
                        + conn.getMetaData().getDatabaseProductName());   //conn before getConn
                
	} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(Software2.getResource().getString("Failed"));
            alert.setHeaderText(Software2.getResource().getString("Error"));
            alert.showAndWait();
	} catch (SQLException ex) {
			ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(Software2.getResource().getString("Failed"));
            alert.setHeaderText(Software2.getResource().getString("Error"));
            alert.showAndWait();
                        

		}
         
     }
    
    @FXML
    private void loginButton(ActionEvent event) {
        String userName = loginUsername.getText();
        String pass = loginPassword.getText();
        ResultSet loginResults = null;
        PreparedStatement ps = null;
      
        
        
        
        try {
            
         //prepared statement to check login
          String queryLogin = "SELECT userId, userName, password, active FROM user where userName=? and password=?";
          ps = conn.prepareStatement(queryLogin);
          ps.setString(1,userName);
          ps.setString(2,pass);
          loginResults = ps.executeQuery();
         
          
          
          
          if (loginResults.next()) {
            userId = loginResults.getString("userId");
            int active = loginResults.getInt("active");
            
              if(active ==1) {
                 //login and display calendar
                 user = userName;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(Software2.getResource().getString("Success"));
            alert.setHeaderText(Software2.getResource().getString("Correct"));
            alert.showAndWait();
            
            try{
                writeToFile();
          String queryApp = "SELECT start FROM appointment where userId= ?";
          ps = conn.prepareStatement(queryApp);
          ps.setString(1,userId);
          ResultSet startRS = ps.executeQuery();
              
               
               while(startRS.next()) {
				Timestamp tsStart = startRS.getTimestamp("start");
                               // System.out.println(tsStart);

				ZoneId newzid = ZoneId.systemDefault();

				ZonedDateTime newzdtStart = tsStart.toLocalDateTime().atZone(newzid);

				LocalDateTime finalDateTime = newzdtStart.toLocalDateTime();
                                //System.out.println(LocalDateTime.now());
                                //System.out.println(finalDateTime);
                               
                                /*DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss.S");
                                String finalStartDate = "2018-01-01" + " " + 01 + ":" + 00 + ":00.0";
                                LocalDateTime ldtStartT = LocalDateTime.parse(finalStartDate, df);
                                
                                String finalEndDate = "2018-01-01" + " " + "01" + ":" + "15" + ":00.0";
                                LocalDateTime ldtEndT = LocalDateTime.parse(finalEndDate, df);
                                Duration dfa = Duration.between(ldtStartT, ldtEndT);
                               System.out.println(dfa);
                              */ Duration d = Duration.between( LocalDateTime.now() , finalDateTime );
                                int seconds = (int) ChronoUnit.SECONDS.between(finalDateTime , LocalDateTime.now());
                                
                                if (seconds <= -1 && seconds >= -900) {
                                 Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                                 alert1.setTitle(Software2.getResource().getString("rem"));
                                 alert1.setHeaderText(Software2.getResource().getString("app15"));
                                 alert1.showAndWait();
                               } 

			}
               
               
            } catch (SQLException | IOException ex){
                
            }
                
            
            handleSceneChange();
            
            
            
           
              }
              else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(Software2.getResource().getString("Inactive"));
            alert.setHeaderText(Software2.getResource().getString("userInactive"));
            alert.showAndWait();
              }
          }
          else {
                 
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(Software2.getResource().getString("Wrong"));
            alert.setHeaderText(Software2.getResource().getString("Invalid"));
            alert.showAndWait();
             } 
          

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
           
    }
    
     @FXML
     private void radioButtonSelection(ActionEvent event)  {
         String Label = "";
         if (engRadioButton.isSelected()) {
             Locale uslocale = new Locale.Builder().setLanguage("en").setRegion("US").build();
             ResourceBundle rb1 = ResourceBundle.getBundle("lang", uslocale);
             
             Software2.setResource(rb1);
             
             setLanguage();
             
         }
         else if (freRadioButton.isSelected()) {
             Locale frlocale = new Locale.Builder().setLanguage("fr").setRegion("FR").build();
             ResourceBundle rb2 = ResourceBundle.getBundle("lang", frlocale);
             
             Software2.setResource(rb2);
             
             setLanguage();
         }
         
     }
    
 
 
    

    private void handleSceneChange() {
        Parent main = null;
        try {
            main = FXMLLoader.load(getClass().getResource("CalendarWeekly.fxml"));
            Scene scene = new Scene(main);

            Stage stage = Software2.getStage();
            stage.setTitle(Software2.getResource().getString("calendarWeekly"));
            stage.setScene(scene);

            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
    
    
    public static String getUserName(){
        return user;
    }
    
    
public void writeToFile() 
  throws IOException {
    ZonedDateTime time= ZonedDateTime.now();
    
    String str = "User: " + getUserName() + '\t' + '\t' + "Login Time: " + DateTimeFormatter.ofPattern("MM/dd/yyyy - hh:mm a").format(time);;
    BufferedWriter writer = new BufferedWriter(new FileWriter("userLog.txt", true));
    writer.append((System.getProperty("line.separator")));
    writer.append((System.getProperty("line.separator")));
    writer.append(str);
    
     
    writer.close();
}
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Sets Login page language
        setLanguage();
      
        //starts connection with database
        accessDatabase();
        
    }    
 
    
    
    
}
