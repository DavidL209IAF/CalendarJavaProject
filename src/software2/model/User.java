/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software2.model;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
/**
 *
 * @author David
 */
public class User {
    
    IntegerProperty userId;
    StringProperty userName;
    StringProperty password;
    IntegerProperty active;
    
  public User(IntegerProperty userId, StringProperty userName, StringProperty password, IntegerProperty active){
      
      this.userId=userId;
      this.userName=userName;
      this.password=password;
      this.active=active;     
  }  
    
  public void setUserId(IntegerProperty userId){
      this.userId=userId;
  }  
    
  public void setUserName(StringProperty userName){
      this.userName=userName;
  }
    
  public void setPassword(StringProperty password){
      this.password=password;
  }  
  
  public void setActive(IntegerProperty active){
      this.active=active;
  }
    
  public IntegerProperty getUserId(){
      return userId;
  }  
  
  public StringProperty getUserName(){
      return userName;
  }
  
  public StringProperty getPassword(){
      return password;
  }
  
  public IntegerProperty getActive(){
      return active;
  }
}
