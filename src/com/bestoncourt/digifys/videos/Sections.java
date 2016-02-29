package com.bestoncourt.digifys.videos;

import java.util.List;

import org.simpleframework.xml.ElementList;


public class Sections {
	@ElementList(inline=true)
	public List<Section> sections;
}
