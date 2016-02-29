package com.bestoncourt.digifys;

import java.util.ArrayList;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.bestoncourt.digifys.list.category.EntryAdapter;
import com.bestoncourt.digifys.list.category.EntryItem;
import com.bestoncourt.digifys.list.category.Item;
import com.bestoncourt.digifys.list.category.SectionItem;
import com.bestoncourt.digifys.videos.Category;
import com.bestoncourt.digifys.videos.Section;
import com.bestoncourt.digifys.Digifys;

public class VideosCategory extends MasterActivity implements
		OnItemClickListener {

	ArrayList<Item> items = new ArrayList<Item>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.videos_category);
		main();

		ListView list = (ListView) findViewById(R.id.listView1);

		Digifys digifys = (Digifys) getApplicationContext();

		for (Section sec : digifys.sections) {

			items.add(new SectionItem(sec.getTitle(), sec.getIcon()));

			for (Category cat : sec.categories) {

				items.add(new EntryItem(cat.getTitle(), cat.getId(), ""));
			}
		}

		EntryAdapter adapter = new EntryAdapter(this, items);

		list.setAdapter(adapter);
		list.setTextFilterEnabled(true);
		list.setClickable(true);
		list.setOnItemClickListener(this);
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Item i = items.get(position);
		if (!i.isSection()) {

			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setClass(this, Videos.class);
			intent.putExtra("catId",
					((EntryItem) parent.getItemAtPosition(position)).id);

			startActivity(intent);

		} else {
			view.setOnClickListener(null);
			view.setOnLongClickListener(null);
			view.setClickable(false);
			view.setEnabled(false);
			view.setLongClickable(false);
			view.setSelected(false);
			view.setFocusable(false);
			view.setOnTouchListener(null);
			view.setPressed(false);

		}
	}

}