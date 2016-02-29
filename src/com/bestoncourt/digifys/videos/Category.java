package com.bestoncourt.digifys.videos;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class Category {
	@Element
	 String id;
	@Element
	 String title;
	@ElementList
	public List<Video> videos;
	
	
	
	public String getId(){
		return id;
	}
	
	public String getTitle(){
		return title;
	}
	
	public List<Video> getVideo(){
		return videos;
	}
	
}
