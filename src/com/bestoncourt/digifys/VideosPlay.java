package com.bestoncourt.digifys;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.VideoView;
import com.bestoncourt.digifys.CustomMediaController;
import com.bestoncourt.digifys.videos.Category;
import com.bestoncourt.digifys.videos.Section;
import com.bestoncourt.digifys.videos.Video;

public class VideosPlay extends MasterActivity implements OnPreparedListener {
	/** Called when the activity is first created. */
	Digifys digifys;
	String videoId;
	VideoView videoView;
	VideoManager videoManager = new VideoManager(this);
	TextView videoTitle;
	WebView videoDescription;
	boolean error;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.videos_play);
		videoTitle = (TextView) findViewById(R.id.videoTitle);
		videoDescription = (WebView) findViewById(R.id.videoDescription);
		int orientation = getResources().getConfiguration().orientation;

		Bundle b = this.getIntent().getExtras();
		videoId = b.getString("videoId");
		digifys = (Digifys) getApplicationContext();
		digifys.videoId = videoId;

		if (orientation == Configuration.ORIENTATION_PORTRAIT) {

			main();
			videoDescription.setBackgroundColor(0);
			searchVideo(true);

		} else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
			
				searchVideo(false);
		}
	}

	private void startVideo(String videoPlay) throws IOException {

		File file = new File(videoManager.getStoragePath() + "Videos/"
				+ videoPlay);

		byte[] buffer = new byte[10000];

		FileInputStream is = new FileInputStream(file);
		OutputStream out = new BufferedOutputStream(new FileOutputStream(
				videoManager.getStoragePath() + "fixedVideo.mp4"));
		int i = 0;
		try{
			while ((is.read(buffer)) != -1) {
	
				byte[] bufferCopy = new byte[9999];
	
				if (i == 1) {
					System.arraycopy(buffer, 1, bufferCopy, 0, 9999);
					out.write(bufferCopy);
				} else
					out.write(buffer);
	
				i++;
			}
		}
		finally{
			is.close();
			out.close();
		}

		String LINK = videoManager.getStoragePath() + "fixedVideo.mp4";

		videoView = (VideoView) findViewById(R.id.videoPlay);

		CustomMediaController mc = new CustomMediaController(this, videoView);
		mc.setAnchorView(videoView);
		mc.setMediaPlayer(videoView);
		Uri video = Uri.parse(LINK);
		videoView.setMediaController(mc);
		videoView.setVideoURI(video);
		videoView.setOnPreparedListener(this);
		videoView.start();

	}

	public void onPrepared(MediaPlayer mp) {
		// TODO Auto-generated method stub
		File video = new File(videoManager.getStoragePath() + "fixedVideo.mp4");
		video.delete();
	}

	@Override
	public void onStop() {
		super.onStop();
		if(!error)
			digifys.currentPosition = videoView.getCurrentPosition();

	}

	@Override
	public void onStart() {
		super.onStart();
		if(!error){
			if (videoId == digifys.videoId)
				videoView.seekTo(digifys.currentPosition);
			}else{
				String alert = getResources().getString(R.string.alertVideosPlay);
				showAlert(alert);
			}
	}

	private void searchVideo(boolean orientation) {

		boolean found = false;
		final String log_tag = "VideosPlay";

		for (Section sec : digifys.sections) {

			for (Category cat : sec.categories) {

				for (Video video : cat.videos) {

					if (video.getId().equals(videoId)) {
						try {
							startVideo(videoId);
						} catch (IOException e) {
							error = true;
							if(e != null){
								Log.e(log_tag, "Can't start video" + e.getMessage());
							}
						}
						
						if (orientation) {
							videoTitle.setText(video.getTitle());
							String header = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
							videoDescription.clearCache(true);
							videoDescription.loadData(
									header + video.getDescription(),
									"text/html; charset=utf-8", "utf-8");
						}
						found = true;
						break;
					}

				}
				if (found)
					break;
			}
			if (found)
				break;
		}
	}
}
