/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software2.model;

import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import java.time.ZonedDateTime;
/**
 *
 * @author David
 */
public class Address {
    
    StringProperty addressid;
    StringProperty address;
    StringProperty address2;
    StringProperty cityid;
    StringProperty postalCode;
    StringProperty phone;
   
    
    public Address(StringProperty addressid, StringProperty address, StringProperty address2, StringProperty cityid, StringProperty postalCode, 
            StringProperty phone) {
        this.addressid = addressid;
        this.address = address;
        this.address2=address2;
        this.cityid=cityid;
        this.postalCode=postalCode;
        this.phone=phone;
        
        
    }


    public void setAddressId(StringProperty addressid) {
        this.addressid = addressid;
    }
    
    public void setAddress(StringProperty address) {
        this.address = address;
    }
    
    public void setAddress2(StringProperty address2) {
        this.address2 = address2;
    }
    
    public void setCityId(StringProperty cityid) {
        this.cityid = cityid;
    }
    
    public void setPostalCode(StringProperty postalCode) {
        this.postalCode = postalCode;
    }
    
    public void setPhone(StringProperty phone) {
        this.phone = phone;
    }
    
   
     
    
    public StringProperty getAddressId() {
        return addressid;
    }
    
    public StringProperty getAddress() {
        return address;
    }
    
    public StringProperty getAddress2() {
        return address2;
    }
    
    public StringProperty getCityId() {
        return cityid;
    }
    
    public StringProperty getPostalCode() {
        return postalCode;
    }
    
    public StringProperty getPhone() {
        return phone;
    }
    
    
}
