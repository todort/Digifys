package com.bestoncourt.digifys;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;

public class JSONUpdater {

	private final static String log_tag = "JSONUpdater";
	Activity _activity = null;
	SharedPreferences prefs = null;
	VideoManager videoManager;

	public JSONUpdater(Activity activity) {
		_activity = activity;
		videoManager = new VideoManager(_activity);
	}

	public void update(boolean forceUpdate) {

		if (isOnline()) {

			prefs = PreferenceManager.getDefaultSharedPreferences(_activity);
			String versionsURL = prefs.getString("versionsURL", null);
			JSONObject versionsJSON = getJSONfromURL(versionsURL);
			String aboutUsVersion = "";
			String findUsVersion = "";
			try {
				aboutUsVersion = versionsJSON.getString("AboutUs");
				findUsVersion = versionsJSON.getString("FindUs");
			} catch (JSONException e) {
				if (e != null) {
					Log.e(log_tag, e.toString(), e);
				}
				throw new RuntimeException(e);
			}

			if (Integer.parseInt(findUsVersion) > Integer.parseInt(prefs.getString("findVer", "0")) || forceUpdate) {
				getSaveJSON("findVer", findUsVersion, "findUsURL", "findJSON.txt");
			}

			if (Integer.parseInt(aboutUsVersion) > Integer.parseInt(prefs.getString("aboutVer", "0")) || forceUpdate) {

				getSaveJSON("aboutVer", aboutUsVersion, "aboutUsURL", "aboutJSON.txt");

			}
		}
	}

	private void getSaveJSON(String mainVersionName, String defValue, String URLname, String file) {

		prefs.edit().putString(mainVersionName, defValue).commit();
		String URL = prefs.getString(URLname, null);
		JSONObject JSON = getJSONfromURL(URL);
		String result = matchPattern(JSON.toString());
		FileWriter fw = null;
		try {

			try {
				fw = new FileWriter(videoManager.getStoragePath() + "Downloads" + File.separator + file);
				fw.write(result);
			} finally {
				if (fw != null)
					fw.close();
			}
		} catch (IOException e) {
			if (e != null) {
				Log.e(log_tag, e.toString(), e);
			}
			throw new RuntimeException(e);
		}
	}

	private String matchPattern(String content) {

		String myPattern = "src=&quot;(.+?)&quot;";
		Pattern p = Pattern.compile(myPattern);
		Matcher m = p.matcher(content);

		while (m.find()) {
			String name, picName = "";

			name = m.group(1).toString();
			String url = name.replace("\\", "");
			picName = url.substring(url.lastIndexOf("/") + 1, url.length());
			getPictures(url, picName);
			String newPath = "file://" + videoManager.getStoragePath() + "Downloads/" + picName;
			content = content.replace(name, newPath);
		}
		return content;
	}

	private boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) _activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting() && netInfo.isAvailable()) {
			return true;
		} else
			return false;
	}

	private void getPictures(String URL, String picName) {

		InputStream is = null;
		OutputStream out = null;

		String path = videoManager.getStoragePath() + "Downloads" + File.separator + picName;

		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(URL);
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();

			try {
				is = entity.getContent();
				out = new BufferedOutputStream(new FileOutputStream(path));
				copyFile(is, out);
			} finally {
				if (is != null)
					is.close();
				if (out != null)
					out.close();
			}
		} catch (Exception e) {
			if (e != null)
				Log.e(log_tag, e.toString(), e);
			throw new RuntimeException(e);
		}
	}

	private static void copyFile(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
	}

	public JSONObject getJSONfromURL(String URL) {

		InputStream is = null;
		String result = "";
		JSONObject jArray = null;

		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(URL);
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			try {
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
			} finally {
				is.close();
			}

			result = sb.toString();
			jArray = new JSONObject(result);
		} catch (UnsupportedEncodingException e) {
			if (e != null)
				Log.e(log_tag, "Error converting result " + e.toString());
		} catch (JSONException e) {
			if (e != null)
				Log.e(log_tag, "Error parsing data " + e.toString());
		} catch (IOException e) {
			if (e != null)
				Log.e(log_tag, "Error in http connection " + e.toString());

		}
		return jArray;
	}
}