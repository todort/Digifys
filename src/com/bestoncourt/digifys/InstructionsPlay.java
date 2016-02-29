package com.bestoncourt.digifys;

import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

public class InstructionsPlay extends MasterActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.instructions_play);

		Bundle b = this.getIntent().getExtras();
		String instructions = b.getString("instructions");

		if (instructions != null) {
			startVideo(instructions);

		}

	}

	private void startVideo(String videoPlay) {
		VideoManager videoManager = new VideoManager(this);
		String LINK = videoManager.getStoragePath() + "Videos/" + videoPlay;

		VideoView videoView = (VideoView) findViewById(R.id.instructionsPlay);

		CustomMediaController mc = new CustomMediaController(this, videoView);
		mc.setAnchorView(videoView);
		mc.setMediaPlayer(videoView);
		Uri video = Uri.parse(LINK);
		videoView.setMediaController(mc);
		videoView.setVideoURI(video);
		videoView.start();
	}

}
