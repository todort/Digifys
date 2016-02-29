package com.bestoncourt.digifys;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.bestoncourt.digifys.videos.Section;
import com.bestoncourt.digifys.videos.Sections;

import android.app.Activity;
import android.content.res.AssetManager;
import android.util.Log;

public class VideoManager {

	Activity _activity = null;
	private final static String tag = "VideoManager";
	
	public VideoManager(Activity activity) {
		_activity = activity;
	}

	public void copyAssets(AssetManager assetManager, String dir) {

		String[] files = null;
		try {
			files = assetManager.list(dir);
		} catch (IOException e) {
			if(e != null)
			Log.e(tag, e.getMessage());
		}
		File root = new File(_activity.getExternalFilesDir(null)
				.getAbsolutePath() + "/Digifys/" + dir);
		root.mkdirs();

		for (String filename : files) {
			InputStream in = null;
			OutputStream out = null;
			try {
				if (dir.equals("Videos") || dir.equals("Downloads")
						|| dir.equals("mdpi") || dir.equals("hdpi"))
					in = assetManager.open(dir + File.separator + filename);
				else
					in = assetManager.open(filename);

				out = new FileOutputStream(getStoragePath() + dir
						+ File.separator + filename);
				try{
					copyFile(in, out);
				}
				finally{
					in.close();
					in = null;
					out.flush();
					out.close();
					out = null;
				}
			} catch (Exception e) {
				if(e != null)
				Log.e(tag, e.getMessage());
			}
		}

	}

	private static void copyFile(InputStream in, OutputStream out)
			throws IOException {
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
	}

	public List<Section> readXml() {

		Sections result = null;

		try {
			Serializer serializer = new Persister();
			File source = new File(getStoragePath() + "Videos/"
					+ "resource.xml");
			result = serializer.read(Sections.class, source);

		} catch (Exception e) {
			if(e != null)
			Log.e(tag, "Error reading the XML file" +  e.getMessage());
		}
		if (result == null)
			return new ArrayList<Section>();
		else
			return result.sections;
	}

	public String getStoragePath() {

		String path = _activity.getExternalFilesDir(null).getAbsolutePath()
				+ File.separator + "Digifys" + File.separator;

		return path;
	}

	public void deleteFiles() {
		File dir = new File(getStoragePath());
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				new File(dir, children[i]).delete();
			}
		}
	}

}
