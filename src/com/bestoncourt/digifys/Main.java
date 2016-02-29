package com.bestoncourt.digifys;

import java.io.IOException;
import java.util.Date;

import org.json.JSONException;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.bestoncourt.digifys.VideoManager;

public class Main extends MasterActivity {
	/** Called when the activity is first created. */
	private final static String log_tag = "Main";
	VideoManager videoManager = new VideoManager(this);
	JSONUpdater updater = new JSONUpdater(this);
	JSONUpdaterTask updateTask;
	SharedPreferences prefs;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		main();
		prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		PreferenceManager.setDefaultValues(getApplicationContext(), R.xml.preferences, true);
		int appVersion = 0;

		try {
			appVersion = this.getPackageManager().getPackageInfo(
					getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			if (e != null)
				Log.e(log_tag, "Package name not found." + e.toString());
		}

		if (prefs.getInt("currentVersion", 0) < appVersion) {

			CopyAssetsTask task = new CopyAssetsTask();
			task.execute(this.getAssets());
			videoManager.deleteFiles();
			prefs.edit().putInt("currentVersion", appVersion).commit();
			
		} else {
			long currentDateTime = new Date().getTime();
			long interval = 1*24*60*60*1000; // 1 day to milliseconds
			
			Digifys digifys = (Digifys) getApplicationContext();
			digifys.sections = videoManager.readXml();
			if((currentDateTime - prefs.getLong("lastUpdate", 0)) > interval ){
				updateTask = new JSONUpdaterTask();
				updateTask.execute(false);
				
			}
		}

	}

	private class CopyAssetsTask extends AsyncTask<Object, Integer, String> {

		private ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(Main.this);
			int id = R.string.dialogMessageCopyAssets;
			String message = getResources().getString(id);
			dialog.setMessage(message);
			dialog.show();
			dialog.setCancelable(false);
			dialog.setCanceledOnTouchOutside(false);
		}

		@Override
		protected String doInBackground(Object... parameters) {

			Object[] params = parameters;
			AssetManager assetManager = (AssetManager) params[0];
			videoManager.copyAssets(assetManager, "Videos");
			videoManager.copyAssets(assetManager, "Downloads");
			videoManager.copyAssets(assetManager, "mdpi");
			videoManager.copyAssets(assetManager, "hdpi");
			return null;

		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Digifys digifys = (Digifys) getApplicationContext();
			digifys.sections = videoManager.readXml();
			dialog.dismiss();
			updateTask = new JSONUpdaterTask();
			updateTask.execute(true);

		}
	}

	private class JSONUpdaterTask extends AsyncTask<Object, Integer, String> {

		private ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(Main.this);
			int id = R.string.dialogMessageJSONUpdater;
			String message = getResources().getString(id);
			dialog.setMessage(message);
			dialog.show();
			dialog.setCancelable(false);
			dialog.setCanceledOnTouchOutside(false);
		}

		@Override
		protected String doInBackground(Object... parameters) {

			String toastJSON = getResources().getString(R.string.toastJSON);
			String toastIO = getResources().getString(R.string.toastIO);
			String result = "";

			try {
				updater.update((Boolean) parameters[0]);
			}
			catch (RuntimeException e)
			{
				Throwable t = e.getCause();
				if (t != null)
				{
					if (t instanceof JSONException)
						result = toastJSON;
					else if (t instanceof IOException)
						result = toastIO;
					else
						result = "Error!";
				}
			}
		return result;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result.length() == 0) {
				prefs.edit().putLong("lastUpdate", new Date().getTime()).commit();
			} else {
				Toast.makeText(Main.this, result, Toast.LENGTH_LONG).show();
			}
			dialog.dismiss();
		}
	}
}