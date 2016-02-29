package com.bestoncourt.digifys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class Instructions extends MasterActivity implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.instructions);
		main();

		ImageButton imgIns1, imgIns2, imgIns3;

		imgIns1 = (ImageButton) findViewById(R.id.imageButton1);
		imgIns2 = (ImageButton) findViewById(R.id.imageButton2);
		imgIns3 = (ImageButton) findViewById(R.id.imageButton3);

		imgIns1.setOnClickListener(this);
		imgIns2.setOnClickListener(this);
		imgIns3.setOnClickListener(this);

		imgIns1.setBackgroundResource(R.drawable.instr1);
		imgIns2.setBackgroundResource(R.drawable.instr2);
		imgIns3.setBackgroundResource(R.drawable.instr3);

	}

	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {

		case R.id.imageButton1:
			Intent i1 = new Intent(this, InstructionsPlay.class);
			i1.putExtra("instructions", "Instruction1.mp4");
			startActivity(i1);

			break;

		case R.id.imageButton2:
			Intent i2 = new Intent(this, InstructionsPlay.class);
			i2.putExtra("instructions", "Instruction2.mp4");
			startActivity(i2);

			break;

		case R.id.imageButton3:
			Intent i3 = new Intent(this, InstructionsPlay.class);
			i3.putExtra("instructions", "Instruction3.mp4");
			startActivity(i3);

			break;
		}
	}

}