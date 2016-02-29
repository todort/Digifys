package com.bestoncourt.digifys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MasterActivity extends Activity implements OnClickListener {

	Button homeButton;
	Button videosButton;
	Button instructionsButton;
	Button findButton;
	Button aboutButton;

	public void main() {

		homeButton = (Button) findViewById(R.id.homeButton);
		videosButton = (Button) findViewById(R.id.videosButton);
		instructionsButton = (Button) findViewById(R.id.instructionsButton);
		findButton = (Button) findViewById(R.id.findButton);
		aboutButton = (Button) findViewById(R.id.aboutButton);

		if (this instanceof Main)
			homeButton.setEnabled(false);
		else
			homeButton.setOnClickListener(this);

		if (this instanceof VideosCategory)
			videosButton.setEnabled(false);
		else
			videosButton.setOnClickListener(this);

		if (this instanceof Videos)
			videosButton.setEnabled(false);
		else
			videosButton.setOnClickListener(this);

		if (this instanceof VideosPlay)
			videosButton.setEnabled(false);
		else
			videosButton.setOnClickListener(this);

		if (this instanceof Instructions)
			instructionsButton.setEnabled(false);
		else
			instructionsButton.setOnClickListener(this);

		if (this instanceof InstructionsPlay)
			instructionsButton.setEnabled(false);
		else
			instructionsButton.setOnClickListener(this);

		if (this instanceof Find)
			findButton.setEnabled(false);
		else
			findButton.setOnClickListener(this);

		if (this instanceof About)
			aboutButton.setEnabled(false);
		else
			aboutButton.setOnClickListener(this);

	}

	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.homeButton:
			Intent i1 = new Intent(this, Main.class);
			startActivity(i1);

			break;

		case R.id.videosButton:
			Intent i2 = new Intent(this, VideosCategory.class);
			startActivity(i2);

			break;

		case R.id.instructionsButton:
			Intent i3 = new Intent(this, Instructions.class);
			startActivity(i3);

			break;

		case R.id.findButton:
			Intent i4 = new Intent(this, Find.class);
			startActivity(i4);

			break;

		case R.id.aboutButton:
			Intent i5 = new Intent(this, About.class);
			startActivity(i5);

			break;
		}
	}

	@Override
	public void onBackPressed() {
		if (this instanceof Main) {
			this.finish();
			this.moveTaskToBack(true);
		} else if (this instanceof VideosCategory) {
			Intent intent = new Intent(this, Main.class);
			startActivity(intent);
		} else if (this instanceof Videos) {
			Intent intent = new Intent(this, VideosCategory.class);
			startActivity(intent);
		} else if (this instanceof VideosPlay) {
			Intent intent = new Intent(this, Videos.class);
			startActivity(intent);
		} else if (this instanceof Instructions) {
			Intent intent = new Intent(this, Main.class);
			startActivity(intent);
			this.finish();
		} else if (this instanceof InstructionsPlay) {
			Intent intent = new Intent(this, Instructions.class);
			startActivity(intent);
			this.finish();
		} else if (this instanceof Find) {
			Intent intent = new Intent(this, Main.class);
			startActivity(intent);
			this.finish();
		} else if (this instanceof About) {
			Intent intent = new Intent(this, Main.class);
			startActivity(intent);
			this.finish();
		}
		return;
	}
	
	public void showAlert(String msg){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(msg)
		       .setCancelable(false)
		       .setNeutralButton("OK", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	  dialog.cancel();
		           }
		       });
		       
		       
		AlertDialog alert = builder.create();
		alert.show();
		
	}

}
