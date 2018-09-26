package com.kriteshmaurya.customerfeedback;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class BaseActivity extends AppCompatActivity {

    EditText txt_name,txt_spouse,txt_email,txt_mobile,txt_comment;

    TextView txt_dob,txt_anniversary;

    RatingBar rb_shopping_experience, rb_gesture;

    RadioButton rd_yes,rd_no,rd_maybe;

    Button btn_submit;

    int mYear;
    int mMonth;
    int mDay;
    Calendar c;

    int DateStatus;

    Context context = BaseActivity.this;

    String CName,Spouse,Email,Mobile,Comment;
    Float ShoppingRating,StaffGestureRating;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);


        txt_name = findViewById(R.id.txt_name);
        txt_spouse = findViewById(R.id.txt_spouse);
        txt_email = findViewById(R.id.txt_email);
        txt_mobile = findViewById(R.id.txt_mobile);
        txt_comment = findViewById(R.id.txt_comment);

        txt_dob = findViewById(R.id.txt_dob);
        txt_anniversary = findViewById(R.id.txt_anniversary);

        rb_shopping_experience = findViewById(R.id.rb_shopping_experience);
        rb_gesture = findViewById(R.id.rb_gesture);

        rd_yes = findViewById(R.id.rd_yes);
        rd_no = findViewById(R.id.rd_no);
        rd_maybe = findViewById(R.id.rd_maybe);

        btn_submit = findViewById(R.id.btnSubmit);

        c = Calendar.getInstance();

        LayerDrawable TimeLineStars = (LayerDrawable) rb_shopping_experience.getProgressDrawable();
        TimeLineStars.getDrawable(2).setColorFilter(Color.parseColor("#CFB53B"), PorterDuff.Mode.SRC_ATOP);
        TimeLineStars.getDrawable(0).setColorFilter(Color.parseColor("#808080"), PorterDuff.Mode.SRC_ATOP);
        TimeLineStars.getDrawable(1).setColorFilter(Color.parseColor("#808080"), PorterDuff.Mode.SRC_ATOP);


        LayerDrawable JobSatisfyStars = (LayerDrawable) rb_gesture.getProgressDrawable();
        JobSatisfyStars.getDrawable(2).setColorFilter(Color.parseColor("#CFB53B"), PorterDuff.Mode.SRC_ATOP);
        JobSatisfyStars.getDrawable(0).setColorFilter(Color.parseColor("#808080"), PorterDuff.Mode.SRC_ATOP);
        JobSatisfyStars.getDrawable(1).setColorFilter(Color.parseColor("#808080"), PorterDuff.Mode.SRC_ATOP);


        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("customer");

        // store app title to 'app_title' node
        mFirebaseInstance.getReference("app_title").setValue("Realtime Database");


        txt_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int DateStatus = 0;
                datePicker(DateStatus);
            }
        });

        txt_anniversary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int DateStatus = 1;
                datePicker(DateStatus);
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CName = txt_name.getText().toString().trim();
                Spouse = txt_spouse.getText().toString().trim();
                Email = txt_email.getText().toString().trim();
                Mobile = txt_mobile.getText().toString().trim();
                Comment = txt_comment.getText().toString();
                ShoppingRating = rb_shopping_experience.getRating();
                StaffGestureRating = rb_gesture.getRating();


                Config_Connection.isOnline(BaseActivity.this);
                if (Config_Connection.internetStatus == true) {


                    if(CName.compareToIgnoreCase("")!=0 && Spouse.compareToIgnoreCase("")!=0 && Mobile.compareToIgnoreCase("")!=0 && ShoppingRating != 0.0 && StaffGestureRating != 0.0){
                        String feedbackValue[] = {CName, Spouse,Email,Mobile, "" + ShoppingRating,
                                "" + StaffGestureRating, Comment};
                          new CustomerFeedbackAsync(context).execute(feedbackValue);
                    }else if(CName.compareToIgnoreCase("")==0){
                        Config_Connection.alertBox("Please Enter Your Name", BaseActivity.this);
                    }else if(Spouse.compareToIgnoreCase("")==0){
                        Config_Connection.alertBox("Please Enter Spouse", BaseActivity.this);
                    }else if(Mobile.compareToIgnoreCase("")==0){
                        Config_Connection.alertBox("Please Enter Mobile No", BaseActivity.this);
                    }else if (ShoppingRating == 0.0 || StaffGestureRating == 0.0) {
                        Config_Connection.alertBox("Please give rating ", BaseActivity.this);
                    }

                } else {
                    Config_Connection.toastShow("No Internet Connection!", BaseActivity.this);
                }
            }
        });

    }

    public void datePicker(final int DateStatus) {

        this.DateStatus = DateStatus;

        // Get Current Date
        this.mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Dialog,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, monthOfYear);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        Date date1 = new Date(String.valueOf(c.getTime()));
                        String date = DateFormat.getDateInstance().format(date1);

                        if (DateStatus == 0) {
                            txt_dob.setText(date);
                        } else if (DateStatus == 1) {
                            txt_anniversary.setText(date);
                        }

                    }
                }, this.mYear, mMonth, mDay);
        datePickerDialog.setTitle("Select Date");
        datePickerDialog.show();
    }

    String cName = "",spouse ="",email ="",mobile ="",comment="",shoppingRating="",staffGestureRating="";

    int flag = 0;

    private class CustomerFeedbackAsync extends AsyncTask<String, String, String> {
        public CustomerFeedbackAsync(Context context) {
        }

        @Override
        protected String doInBackground(String... params) {

            cName = params[0];
            spouse = params[1];
            email = params[2];
            mobile = params[3];
            comment = params[4];
            shoppingRating = params[5];
            staffGestureRating = params[6];

            userId = mFirebaseDatabase.push().getKey();

            Customer customer = new Customer(cName,spouse, email,mobile,comment,shoppingRating,staffGestureRating);

            mFirebaseDatabase.child(userId).setValue(customer);

            flag = 1;

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(flag == 1){

                Config_Connection.toastShow("Add feedback success",context);
                txt_name.setText("");
                txt_spouse.setText("");
                txt_email.setText("");
                txt_mobile.setText("");
                txt_comment.setText("");
                rb_shopping_experience.setRating(0);
                rb_gesture.setRating(0);

            }
        }
    }
}
