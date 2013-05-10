package com.final_proj.george;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.final_project.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class Forecast extends Activity {
	String city;
	String state;
	TextView day1;
	TextView day1Forecast;
	TextView day2;
	TextView day2Forecast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forecast);
		day1 = (TextView) findViewById(R.id.textView2);
		day1Forecast = (TextView) findViewById(R.id.textView3);
		day2 = (TextView) findViewById(R.id.textView4);
		day2Forecast = (TextView) findViewById(R.id.textView5);
		// day3=(TextView) findViewById(R.id.textView6);
		// day3Forecast=(TextView) findViewById(R.id.textView7);
		city = getIntent().getExtras().getString("city");
		state = getIntent().getExtras().getString("state");
		// Log.d("JG","http://api.wunderground.com/api/d531d40c6117c8fd/conditions/q/"+city+".json");
		new JsonExtractor()
				.execute("http://api.wunderground.com/api/d531d40c6117c8fd/conditions/q/.json");
	}

	@SuppressWarnings("static-access")
	private class JsonExtractor extends AsyncTask<String, Void, String> {
		JSONObject weather;
		JSONArray forecast;

		@Override
		protected String doInBackground(String... params) {
			Json a = new Json();
			weather = a
					.getJson("http://api.wunderground.com/api/d531d40c6117c8fd/forecast/q/"
							+ city + "," + state + ".json");
			// Log.d("JG",""+ weather.toString());
			JSONObject weather2 = null;
			JSONObject weather3 = null;
			try {
				weather2 = weather.getJSONObject("forecast");
				// Log.d("JG",""+ weather2.getString("fcttext"));
				weather3 = weather2.getJSONObject("txt_forecast");
				// Log.d("JG", weather3.getJSONArray("forecastday"));
				forecast = weather3.getJSONArray("forecastday");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "";
		}

		@Override
		protected void onPostExecute(String text) {

			try {
				// Log.d("JG",forecast.getJSONObject(0).getString("fcttext"));
				day1.setText(forecast.getJSONObject(0).getString("title") + ":");
				day1Forecast.setText(forecast.getJSONObject(0).getString(
						"fcttext"));
				day2.setText(forecast.getJSONObject(1).getString("title") + ":");
				day2Forecast.setText(forecast.getJSONObject(1).getString(
						"fcttext"));

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.forecast, menu);
		return true;
	}

}
