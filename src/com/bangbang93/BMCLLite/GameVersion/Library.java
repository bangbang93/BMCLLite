package com.bangbang93.BMCLLite.GameVersion;

import java.io.Serializable;

public class Library implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2003423957804328541L;
	public String name;
	public OS natives;
	public Extract extract;
	public String url;
	public Rule[] rule;
	
}
