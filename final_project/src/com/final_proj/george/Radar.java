package com.final_proj.george;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.example.final_project.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;

public class Radar extends Activity {
	String city;
	String state;
	ImageView mImage;
	Bitmap img = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_radar);
		city = getIntent().getExtras().getString("city");
		state = getIntent().getExtras().getString("state");
		mImage = (ImageView) findViewById(R.id.imageView1);
		new DownloadImageTask()
				.execute("http://api.wunderground.com/api/d531d40c6117c8fd/radar/q/"
						+ city
						+ ","
						+ state
						+ ".png?width=280&height=280&newmaps=1");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.radar, menu);
		return true;
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
				url = new URL(
						"http://api.wunderground.com/api/d531d40c6117c8fd/radar/q/"
								+ city + "," + state
								+ ".png?width=280&height=280&newmaps=1");
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
