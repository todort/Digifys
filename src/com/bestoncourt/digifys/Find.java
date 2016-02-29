package com.bestoncourt.digifys;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.webkit.WebView;

public class Find extends MasterActivity {

	private final static String log_tag = "Find";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.find);
			main();
			VideoManager videoManager = new VideoManager(this);
			File findFile = new File(videoManager.getStoragePath() + "Downloads/" + "findJSON.txt");
			WebView webView = (WebView) findViewById(R.id.WebViewFind);
			webView.setBackgroundColor(0);
			BufferedReader br = null;

			try {

				br = new BufferedReader(new FileReader(findFile));
				String find = "";
				StringBuilder sb = new StringBuilder();
				try {
					while ((find = br.readLine()) != null) {
						sb.append(find + "\n");
					}					
				} finally {
					br.close();
				}
				find = sb.toString();
				JSONObject findJSON = new JSONObject(find);
				String JSONData = findJSON.getString("Html");
				Spanned content = Html.fromHtml(JSONData);
				String htmlData = "<font color='white'>" + content.toString()
						+ "</font>";
				webView.clearCache(true);
				webView.loadDataWithBaseURL("", htmlData, "text/html",
						"iso-8859-1", "");

			} catch (FileNotFoundException e) {
				if(e != null){
					Log.e(log_tag, "File not found: " + e.toString());
					String alert = getResources().getString(R.string.alertFileNotFound);
					showAlert(alert);
				}
			} catch (IOException e) {
				if(e != null){
					Log.e(log_tag, "Error in IO: " + e.toString());
					String alert = getResources().getString(R.string.alertIO);
					showAlert(alert);
				}
			} catch (JSONException e) {
				if(e != null){
					Log.e(log_tag, "Can't convert to JSON: " + e.toString());
					String alert = getResources().getString(R.string.toastJSON);
					showAlert(alert);
				}
			}
		}
	}
}