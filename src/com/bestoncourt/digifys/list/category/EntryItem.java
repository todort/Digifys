package com.bestoncourt.digifys.list.category;


public class EntryItem implements Item{

	public final String title;
	public final String id;
	public String icon;

	public EntryItem(String title, String id, String icon) {
		this.title = title;
		this.id=id;
		this.icon=icon;
	}

	public String getIcon(){
		return icon;
	}
	
	public boolean isSection() {
		return false;
	}

}
