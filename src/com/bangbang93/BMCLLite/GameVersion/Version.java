package com.bangbang93.BMCLLite.GameVersion;

import java.io.FileNotFoundException;
import java.io.Serializable;

import com.bangbang93.BMCLLite.BMCLLite;
import com.bangbang93.BMCLLite.Util.FileHelper;
import com.google.gson.Gson;

public class Version implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3483890555295567297L;
	public String id;
	public String time;
	public String releaseTime;
	public String type;
	public String minecraftArguments;
	public String mainClass;
	public Library[] libraries;
	public int minimumLauncherVersion;
	public String assets;
	
	public static Version readVersion(String path){
		String jsonString = FileHelper.readToEnd(path);
		Gson gson = new Gson();
		return gson.fromJson(jsonString, Version.class);
	}
	
	public static String[] refreshVersion() throws FileNotFoundException{
		return FileHelper.listDirectory(BMCLLite.getCurrectDirectory() + ".minecraft" + BMCLLite.pathSpilter + "versions" + BMCLLite.pathSpilter);
	}
	
	public static String getVersionDir(String version){
		return BMCLLite.getCurrectDirectory() + ".minecraft" + BMCLLite.pathSpilter + "versions" + BMCLLite.pathSpilter + version + BMCLLite.pathSpilter;
	}
	
	public static String getVersionJsonFile(String version){
		return getVersionDir(version) + version + ".json";
	}
	
	@Override
	public String toString(){
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
