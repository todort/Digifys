package com.bestoncourt.digifys;

import java.io.File;
import java.util.ArrayList;

import com.bestoncourt.digifys.R;
import com.bestoncourt.digifys.list.category.EntryItem;
import com.bestoncourt.digifys.list.category.Item;

import android.widget.ArrayAdapter;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class VideosAdapter extends ArrayAdapter<Item> {

	private final Activity context;
	private final ArrayList<Item> videos;
	private LayoutInflater vi;
	VideoManager videoManager;

	public VideosAdapter(Activity context, ArrayList<Item> videos) {
		super(context, 0, videos);
		this.context = context;
		this.videos = videos;
		vi = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		videoManager = new VideoManager(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		final Item i = videos.get(position);
		if (i != null) {

			rowView = vi.inflate(R.layout.list_item_videos, null);

			TextView viewTitle = (TextView) rowView.findViewById(R.id.title);
			ImageView image = (ImageView) rowView.findViewById(R.id.avatar);

			EntryItem ei = (EntryItem) i;

			double density = context.getResources().getDisplayMetrics().density;
			if (density <= 1.0) {
				image.setImageDrawable(Drawable.createFromPath(videoManager
						.getStoragePath()
						+ "mdpi"
						+ File.separator
						+ ei.getIcon()));
			} else if (density >= 1.5) {
				image.setImageDrawable(Drawable.createFromPath(videoManager
						.getStoragePath()
						+ "hdpi"
						+ File.separator
						+ ei.getIcon()));
			}
			if (viewTitle != null) {
				viewTitle.setText(ei.title);
			}

		}
		return rowView;
	}
}