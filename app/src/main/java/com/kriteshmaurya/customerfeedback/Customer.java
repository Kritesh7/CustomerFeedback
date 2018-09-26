package com.kriteshmaurya.customerfeedback;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Customer {

    public String CName;
    public String Spouse;
    public String Email;
    public String Mobile;
    public String Comment;
    public String ShoppingRating;
    public String StaffGestureRating;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Customer() {
    }

    public Customer(String cName,String spouse, String email,String mobile,String comment,String shoppingRating,String staffGestureRating) {
        this.CName = cName;
        this.Spouse = spouse;
        this.Email = email;
        this.Mobile = mobile;
        this.Comment = comment;
        this.ShoppingRating = shoppingRating;
        this.StaffGestureRating = staffGestureRating;
    }
}
