package com.bestoncourt.digifys;

import java.util.ArrayList;

import com.bestoncourt.digifys.list.category.EntryItem;
import com.bestoncourt.digifys.list.category.Item;
import com.bestoncourt.digifys.videos.Category;
import com.bestoncourt.digifys.videos.Section;
import com.bestoncourt.digifys.videos.Video;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class Videos extends MasterActivity implements OnItemClickListener {

	ArrayList<Item> videos = new ArrayList<Item>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.videos);
		main();

		ListView list = (ListView) findViewById(R.id.listView1);
		TextView header = (TextView) findViewById(R.id.videos_title);

		Bundle b = this.getIntent().getExtras();
		String catId = b.getString("catId");

		Digifys digifys = (Digifys) getApplicationContext();

		boolean found = false;
		for (Section sec : digifys.sections) {

			for (Category cat : sec.categories) {
				if (cat.getId().equals(catId)) {
					header.setText(cat.getTitle());

					for (Video video : cat.videos) {
						videos.add(new EntryItem(video.getTitle(), video
								.getId(), video.getIcon()));
					}
					found = true;
					break;
				}

			}
			if (found)
				break;
		}

		VideosAdapter adapter = new VideosAdapter(this, videos);

		list.setAdapter(adapter);
		list.setTextFilterEnabled(true);
		list.setClickable(true);
		list.setOnItemClickListener(this);

	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Intent intent = new Intent(getApplicationContext(), VideosPlay.class);

		intent.putExtra("videoId",
				((EntryItem) parent.getItemAtPosition(position)).id);

		startActivity(intent);

	}

}
