package com.example.final_project;

import java.io.IOException;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    LocationListener ll = new mylocationlistener();
	    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
	    	    Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345"));
	    	startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		Log.d("jg","hi" );
		Location location = null;
		String myLocation = "Latitude = " + location.getLatitude() + " Longitude = " + location.getLongitude();
	    Log.d("jg",myLocation);
		
		return true;
	}

	 private class mylocationlistener implements LocationListener {
		    @Override
		    public void onLocationChanged(Location location) {
		        if (location != null) {
		        Log.d("LOCATION CHANGED", location.getLatitude() + "");
		        Log.d("LOCATION CHANGED", location.getLongitude() + "");
		        Log.d("jg",location.getLatitude() + "" + location.getLongitude());
		        }
		    }
		    @Override
		    public void onProviderDisabled(String provider) {
		    }
		    @Override
		    public void onProviderEnabled(String provider) {
		    }
		    @Override
		    public void onStatusChanged(String provider, int status, Bundle extras) {
		    }
		    }
}


