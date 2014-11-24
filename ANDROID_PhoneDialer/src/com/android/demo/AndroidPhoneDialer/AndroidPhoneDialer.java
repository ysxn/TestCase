package com.android.demo.AndroidPhoneDialer;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;

public class AndroidPhoneDialer extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //
        //Intent DialIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:5551212"));
        Intent DialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:5551212"));
        //
        DialIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        
        //
        startActivity(DialIntent);
    }
}