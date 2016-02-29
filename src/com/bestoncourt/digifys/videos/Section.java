package com.bestoncourt.digifys.videos;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class Section {
	@ElementList
	public List<Category> categories;
	
	@Element(name="icon")
	 String icon;
	
	@Element
	String title;
	
	@Attribute(name="id")
	
	 String id;
	
	
	public String getTitle(){
		return title;
	}
	
	public String getIcon(){
		return icon;
	}
	
	public String getId(){
		return id;
	}
	
	public List<Category> getSection(){
		return categories;
		
	}

}
