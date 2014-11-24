package com.android.demo.AndroidLBS;

import android.app.Activity;
import android.os.Bundle;
import android.location.LocationManager; 
import android.view.View; 
import android.widget.TextView; 
import android.content.Context; 
import android.widget.Button;


public class AndroidLBS extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final Button gpsButton = (Button) findViewById(R.id.gpsButton); 
        gpsButton.setOnClickListener(new Button.OnClickListener() { 
        	public void onClick(View v){ LoadCoords(); }});
    }
    public void LoadCoords(){ 
    	TextView latText = (TextView) findViewById(R.id.latText); 
    	TextView lngText = (TextView) findViewById(R.id.lngText); 
    	LocationManager myManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE); 
    	Double latPoint = myManager.getLastKnownLocation("gps").getLatitude(); 
    	Double lngPoint = myManager.getLastKnownLocation("gps").getLongitude(); 
    	latText.setText(latPoint.toString()); 
    	lngText.setText(lngPoint.toString()); 
    	}
}