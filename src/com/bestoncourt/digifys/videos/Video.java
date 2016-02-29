package com.bestoncourt.digifys.videos;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Video {
	@Element
	String id;
	@Element
	String title;
	@Element
	String description;
	@Element(name="icon")
	String icon;
	
	
	public String getId(){
		return id;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getDescription(){
		return description;
	}
	
	public String getIcon(){
		return icon;
	}
}
