/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software2.model;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author David
 */
public class Appointment {
    
    StringProperty appointmentId;
    StringProperty customerId;
    StringProperty userId;
    StringProperty title;
    StringProperty description;
    StringProperty location;
    StringProperty contact;
    StringProperty type;
    StringProperty url;
    StringProperty startDate;
    StringProperty endDate;
    StringProperty customerName;
    StringProperty userName;
    private static ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    
    public Appointment(StringProperty appointmentId, StringProperty customerId, StringProperty userId, StringProperty title, 
            StringProperty description, StringProperty location, StringProperty contact, StringProperty type, StringProperty url, StringProperty startDate,
            StringProperty endDate, StringProperty customerName, StringProperty userName) {
        
        this.appointmentId=appointmentId;
        this.customerId=customerId;
        this.userId=userId;
        this.title=title;
        this.description=description;
        this.location=location;
        this.contact=contact;
        this.type=type;
        this.url=url;
        this.startDate=startDate;
        this.endDate=endDate;
        this.userName=userName;
    }
    
  
    public void setAppointmentId(StringProperty appointmentId) {   
    this.appointmentId=appointmentId;
   }
    
    public void setCustomerId(StringProperty customerId){
        this.customerId=customerId;
    }
    
    public void setUserId(StringProperty userId){
        this.userId=userId;
    }
    
    public void setTitle(StringProperty title){
        this.title=title;
    }
    
    public void setDeescription(StringProperty description){
        this.description=description;
    }
    
    public void setLocation(StringProperty location){
        this.location=location;
    }
    
    public void setContact(StringProperty contact){
        this.contact=contact;
    }
    
    public void setType(StringProperty type){
        this.type=type;
    }
    
    public void setUrl(StringProperty url){
        this.url=url;
    }
    
     public void setStartDate(StringProperty startDate){
        this.startDate=startDate;
    }
    
     public void setendDate(StringProperty endDate){
        this.endDate=endDate;
    }
    
     public void setCustomerName(StringProperty customerName){
        this.customerName=customerName;
    }
   
      public void setUserName(StringProperty userName){
        this.userName=userName;
    }
    
    
    public StringProperty getAppointmentId() {
        return appointmentId;
    }
    
    public StringProperty getCustomerId(){
        return customerId;
    }
    
    public StringProperty getUserId(){
        return userId;
    }
    
    public StringProperty getTitle(){
        return title;
    }
    
    public StringProperty getDescription(){
        return description;
    }
    
    public StringProperty getLocation(){
        return location;
    }
    
    public StringProperty getContact(){
        return contact;
    }
    
    public StringProperty getType(){
        return type;
    }
    
    public StringProperty getUrl(){
        return url;
    }
    
    public StringProperty getStartDate(){
        return startDate;
    }
    
    public StringProperty getEndDate(){
        return endDate;
    }
    
    public StringProperty getCustomerName(){
        return customerName;
    }
    
    public StringProperty getUserName(){
        return userName;
    }
    
    public static void addAppointment(Appointment finalAppointment) {
          appointmentList.add(finalAppointment);    
   }
       public static ObservableList<Appointment> getCompleteAppointmentList() {
        return appointmentList;
    }
       
        public static void removeAppointment(Appointment finalAppointment) {
          appointmentList.remove(finalAppointment);    
   }
        
       public static void updateAppointment(Integer index, Appointment finalAppointment) {
       appointmentList.set(index, finalAppointment);
   }
    
    public static void clearAppointmentList(){
        appointmentList.clear();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
