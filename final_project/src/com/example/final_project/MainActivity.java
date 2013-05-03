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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener  {
	private Button mWeather;
	// the listener to listen to the locations
	private LocationListener listener = null;
	// a location manager
	private LocationManager lm  = null;
	// locations instances to GPS and NETWORk
	private Location myLocationGPS, myLocationNetwork;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		myLocationNetwork = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		myLocationGPS = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		listener = new myLocationListener();
		Log.d("JG",""+myLocationNetwork.getLatitude());
		Log.d("JG",""+myLocationNetwork.getLongitude());
		mWeather= (Button) findViewById(R.id.button1);
		mWeather.setOnClickListener(this);
	    //Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
	    //	    Uri.parse("http://maps.google.com/maps"));
	    //	startActivity(intent);
	}
	@Override
	public void onClick(View v) {
		if (v == mWeather) {
			//Log.d("JG", "click");
			
		} 
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		
		return true;
	}

	public class myLocationListener implements LocationListener {
	    @Override
	    public void onLocationChanged(Location location) {
	        // "location" is the RECEIVED locations and its here that you should proccess it

	        // check if the incoming position has been received from GPS or network
	        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
	            lm.removeUpdates(this);
	        } else {
	            lm.removeUpdates(listener);
	        }
	    }
	    @Override
	    public void onProviderDisabled(String provider) {
	        lm.removeUpdates(this);
	        lm.removeUpdates(listener);         
	    }
	    @Override
	    public void onProviderEnabled(String provider) {
	    }
	    @Override
	    public void onStatusChanged(String provider, int status, Bundle extras) {
	    }
	}
}


