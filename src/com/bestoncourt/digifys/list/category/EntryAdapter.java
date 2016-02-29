package com.bestoncourt.digifys.list.category;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bestoncourt.digifys.R;
import com.bestoncourt.digifys.VideoManager;

public class EntryAdapter extends ArrayAdapter<Item> {

	private Activity context;
	private ArrayList<Item> items;
	private LayoutInflater vi;

	public EntryAdapter(Activity context, ArrayList<Item> items) {
		super(context, 0, items);
		this.context = context;
		this.items = items;
		vi = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		final Item i = items.get(position);
		if (i != null) {
			if (i.isSection()) {
				SectionItem si = (SectionItem) i;
				v = vi.inflate(R.layout.list_item_section, null);

				v.setOnClickListener(null);
				v.setOnLongClickListener(null);
				v.setClickable(false);
				v.setLongClickable(false);

				final TextView text = (TextView) v
						.findViewById(R.id.list_item_section_text);
				final ImageView image = (ImageView) v
						.findViewById(R.id.list_item_section_image);
				text.setText(si.getTitle());

				VideoManager videoManager = new VideoManager(context);
				image.setImageDrawable(Drawable.createFromPath(videoManager
						.getStoragePath()
						+ "mdpi"
						+ File.separator
						+ si.getIcon()));

			} else {
				EntryItem ei = (EntryItem) i;
				v = vi.inflate(R.layout.list_item_category, null);
				final TextView title = (TextView) v.findViewById(R.id.title);

				if (title != null)
					title.setText(ei.title);

			}
		}
		return v;
	}

}
