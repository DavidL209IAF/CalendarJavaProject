/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software2;


import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import software2.view.LoginController;
import software2.view.CalendarWeeklyController;
import javafx.event.ActionEvent;



/**
 *
 * @author David
 */
public class Software2 extends Application {
    
      //variable delarations
// private static Stage stage;
  private static BorderPane rootLayout;
  private static Stage primaryStage;
 
// public static Locale locale = Locale.getDefault();
  //Test Locale
 public static Locale flocale = new Locale.Builder().setLanguage("fr").setRegion("FR").build();
 public static Locale ulocale = new Locale.Builder().setLanguage("en").setRegion("US").build();
 private static ResourceBundle rbw;
   
 
 public static ResourceBundle getResource() {
     
     return rbw;
 }
 
 public void setResource(ResourceBundle rb) {
     
     this.rbw = rb;
 }
 
 
    @Override
    public void start(Stage primaryStage) throws Exception {
            setResource(ResourceBundle.getBundle("lang", ulocale));
         this.primaryStage = primaryStage;
        this.primaryStage.setTitle(getResource().getString("Login"));
      //Loads rootlayout from fxml file RootLayout.fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Software2.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Shows the scene that has the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
       
         try {
         
            // Load Main.fxml
         FXMLLoader loader1 = new FXMLLoader();
         loader1.setLocation(Software2.class.getResource("view/Login.fxml"));
         AnchorPane mainOverview = (AnchorPane) loader1.load();
         rootLayout.setCenter(mainOverview);
            
        LoginController controller = loader1.getController();
        controller.setMainApp(this);
       
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        /*  Parent root = FXMLLoader.load(getClass().getResource("view/Login.fxml"));
        
        Scene scene = new Scene(root);
        setStage(stage);
        stage.setScene(scene);
        stage.setTitle(rb.getString("Login"));
        stage.show();
        
        */ 
           
       
    }

  public static BorderPane getRootLayout(){
      return rootLayout;
 }
    
 public void setStage(Stage stage){
      this.primaryStage = stage;
  }  
    
 public static Stage getStage(){
      return primaryStage;
  }  
    
    /**
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
