package com.bestoncourt.digifys.list.category;

public class SectionItem implements Item{

	private final String title;
	private final String icon;
	private String id;
	
	public SectionItem(String title,String icon) {
		this.title = title;
		this.icon=icon;
		
	}
	
	public String getId(){
		return id;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getIcon(){
		return icon;
	}
	
	public boolean isSection() {
		return true;
	}

}
