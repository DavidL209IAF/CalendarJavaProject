/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software2.model;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 *
 * @author David
 */
public class Customers {
    StringProperty customerId;
    StringProperty customerName;
    StringProperty addressId;
    StringProperty active;
    StringProperty address;
    StringProperty address2;
    StringProperty postalCode;
    StringProperty phone;
    StringProperty cityId;
    StringProperty city;
    StringProperty countryId;
    StringProperty country;
    private static ObservableList<Customers> customerList = FXCollections.observableArrayList();
    
    public Customers(StringProperty customerId, StringProperty customerName, StringProperty addressId, StringProperty active,
           StringProperty address, StringProperty address2, StringProperty cityId, StringProperty postalCode, StringProperty phone, 
           StringProperty city, StringProperty countryId, StringProperty country){
        
        this.customerId=customerId;
        this.customerName=customerName;
        this.addressId=addressId;
        this.active=active;
        this.address = address;
        this.address2=address2;
        this.postalCode=postalCode;
        this.phone=phone;
        this.cityId=cityId;
        this.city=city;
        this.countryId=countryId;
        this.country=country;
         

        }
    
    public void setCustomerId(StringProperty customerId){
        this.customerId=customerId;
    }
    
    public void setCustomerName(StringProperty customerName){
        this.customerName=customerName;
    }
    
    public void setAddressId(StringProperty addressId){
        this.addressId=addressId;
    }
    
    public void setActive(StringProperty active){
        this.active=active;
    }
    
    public StringProperty getCustomerId(){
        return customerId;
    }
    
    public StringProperty getCustomerName(){
        return customerName;
    }
    
    public StringProperty getAddressId(){
        return addressId;
    }
    
    public StringProperty getActive(){
        return active;
    }
    
    public void setAddress(StringProperty address) {
        this.address = address;
    }
    
    public void setAddress2(StringProperty address2) {
        this.address2 = address2;
    }
    
    public void setCityId(StringProperty cityId) {
        this.cityId = cityId;
    }
    
    public void setPostalCode(StringProperty postalCode) {
        this.postalCode = postalCode;
    }
    
    public void setPhone(StringProperty phone) {
        this.phone = phone;
    }
    

    public StringProperty getAddress() {
        return address;
    }
    
    public StringProperty getAddress2() {
        return address2;
    }
    
    public StringProperty getCityId() {
        return cityId;
    }
    
    public StringProperty getPostalCode() {
        return postalCode;
    }
    
    public StringProperty getPhone() {
        return phone;
    }
    

public void setCity(StringProperty city){
    this.city=city;
}

public void setCountryId(StringProperty countryId){
    this.countryId=countryId;
}


public StringProperty getCity(){
    return city;
}

public StringProperty getCountryId(){
    return countryId;
}


public void setCountry(StringProperty country){
    this.country=country;
}

public StringProperty getCountry(){
    return country;
}

public static void addCustomer(Customers finalCustomer) {
          customerList.add(finalCustomer);    
   }
       public static ObservableList<Customers> getCompleteCustomerList() {
        return customerList;
    }
       
        public static void removeCustomer(Customers finalCustomer) {
          customerList.remove(finalCustomer);    
   }
        
       public static void updateCustomer(Integer index, Customers finalCustomer) {
       customerList.set(index, finalCustomer);
   }
}
