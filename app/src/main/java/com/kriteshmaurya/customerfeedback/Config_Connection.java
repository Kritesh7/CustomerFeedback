package com.kriteshmaurya.customerfeedback;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

public class Config_Connection {

    public static boolean internetStatus = false;

    public static boolean isOnline(Context c) {
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            internetStatus = true;
            //Log.e("Status is ", ""+internetStatus);
            return true;
        } else {
            internetStatus = false;
            //Log.e("Status is 1", ""+internetStatus);
        }
        return true;
    }

    public static void toastShow(String s, Context c) {
        Toast toast = Toast.makeText(c, s, Toast.LENGTH_LONG);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        toast.setGravity(Gravity.CENTER, 0, 0);
        v.setTextSize(18);
        toast.show();
    }

    public static void alertBox(String s, Context c) {
        AlertDialog.Builder altDialog = new AlertDialog.Builder(c);
        altDialog.setMessage(s);
        altDialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        altDialog.show();
    }


}
