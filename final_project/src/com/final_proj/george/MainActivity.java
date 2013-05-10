package com.final_proj.george;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.final_project.R;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {
	private Button mWeather;
	private Button mForecast;
	private Button mRadar;
	private Button mSatelite;
	ImageView mImage;
	Bitmap img = null;
	public static int Forecast_Request;
	TextView mTextView;
	TextView mWind;
	TextView mLocation;
	TextView mFeelsLike;
	String city;
	String state;
	String icon;
	// the listener to listen to the locations
	private LocationListener listener = null;
	// a location manager
	private LocationManager lm = null;
	// locations instances to GPS and NETWORk
	private Location myLocationGPS, myLocationNetwork;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		myLocationNetwork = lm
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		myLocationGPS = lm
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		listener = new myLocationListener();
		// Log.d("JG", "" + myLocationNetwork.getLatitude());
		// Log.d("JG", "" + myLocationNetwork.getLongitude());
		mImage = (ImageView) findViewById(R.id.imageButton1);
		mWeather = (Button) findViewById(R.id.button1);
		mForecast = (Button) findViewById(R.id.button2);
		mRadar = (Button) findViewById(R.id.button3);
		mSatelite = (Button) findViewById(R.id.button4);
		mTextView = (TextView) findViewById(R.id.textView2);
		mLocation = (TextView) findViewById(R.id.textView3);
		mWind = (TextView) findViewById(R.id.textView4);
		mFeelsLike = (TextView) findViewById(R.id.textView5);
		mWeather.setOnClickListener(this);
		mForecast.setOnClickListener(this);
		mRadar.setOnClickListener(this);
		mSatelite.setOnClickListener(this);
		new JsonExtractor()
				.execute("http://api.wunderground.com/api/d531d40c6117c8fd/conditions/q/"
						+ myLocationNetwork.getLatitude()
						+ ","
						+ myLocationNetwork.getLongitude() + ".json");
		new DownloadImageTask().execute(icon);
		// Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
		// Uri.parse("http://maps.google.com/maps"));
		// startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		if (v == mWeather) {
			new JsonExtractor()
					.execute("http://api.wunderground.com/api/d531d40c6117c8fd/conditions/q/"
							+ myLocationNetwork.getLatitude()
							+ ","
							+ myLocationNetwork.getLongitude() + ".json");
		}
		if (v == mForecast) {
			// Log.d("JG","mForecast");
			Intent intent = new Intent(this, Forecast.class);
			intent.putExtra("city", city);
			intent.putExtra("state", state);
			startActivity(intent);
		}
		if (v == mRadar) {
			// Log.d("JG","mForecast");
			Intent intent = new Intent(this, Radar.class);
			intent.putExtra("city", city);
			intent.putExtra("state", state);
			startActivity(intent);
		}
		if (v == mSatelite) {
			// Log.d("JG","mForecast");
			Intent intent = new Intent(this, Satellite.class);
			intent.putExtra("city", city);
			intent.putExtra("state", state);
			startActivity(intent);
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
			// "location" is the RECEIVED locations and its here that you should
			// process it

			// check if the incoming position has been received from GPS or
			// network
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

	private class JsonExtractor extends AsyncTask<String, Void, String> {
		JSONObject weather;
		String Location = "";
		String temp = "0";
		String wind = "0";
		String feelsLike = "0";

		@Override
		protected String doInBackground(String... params) {
			Json a = new Json();
			weather = Json
					.getJson("http://api.wunderground.com/api/d531d40c6117c8fd/conditions/q/"
							+ myLocationNetwork.getLatitude()
							+ ","
							+ myLocationNetwork.getLongitude() + ".json");
			// Log.d("JG",""+ weather.toString());
			JSONObject currentObservationJson = null;
			JSONObject displayLocationJson = null;
			try {
				currentObservationJson = weather
						.getJSONObject("current_observation");
				displayLocationJson = currentObservationJson
						.getJSONObject("display_location");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				// Log.d("JG", "" + weather2.getString("temp_f"));
				temp = currentObservationJson.getString("temp_f");
				wind = currentObservationJson.getString("wind_string");
				icon = currentObservationJson.getString("icon_url");
				feelsLike = currentObservationJson
						.getString("feelslike_string");
				Location = displayLocationJson.getString("full");
				city = displayLocationJson.getString("city");
				state = displayLocationJson.getString("state");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "";
		}

		@Override
		protected void onPostExecute(String text) {
			mTextView.setText(temp + " °F");
			mWind.setText("Wind: \n" + wind);
			mLocation.setText("Location:  " + Location);

			mFeelsLike.setText("Feels Like:  " + feelsLike);
		}

	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		/**
		 * The system calls this to perform work in a worker thread and delivers
		 * it the parameters given to AsyncTask.execute()
		 */
		@Override
		protected Bitmap doInBackground(String... urls) {
			URL url;
			try {
				// A uniform resource locator aka the place where the data is
				// located
				url = new URL(icon);
				// Opens an HTTPUrlConnection and downloads the input stream
				// into a
				// Bitmap
				img = BitmapFactory.decodeStream(url.openStream());
			} catch (MalformedURLException e) {
				Log.e("JG", "URL is bad");
				e.printStackTrace();
			} catch (IOException e) {
				Log.e("JG", "Failed to decode Bitmap");
				e.printStackTrace();
			}
			return img;

		}

		/**
		 * The system calls this to perform work in the UI thread and delivers
		 * the result from doInBackground()
		 */
		@Override
		protected void onPostExecute(Bitmap result) {
			mImage.setImageBitmap(result);

		}
	}

}
