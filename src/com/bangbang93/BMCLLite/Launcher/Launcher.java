package com.bangbang93.BMCLLite.Launcher;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;

import com.bangbang93.BMCLLite.BMCLLite;
import com.bangbang93.BMCLLite.Exception.NoEnoughMemoryException;
import com.bangbang93.BMCLLite.GameVersion.Version;
import com.bangbang93.BMCLLite.Resource.Url;
import com.bangbang93.BMCLLite.Util.Downloader;

public class Launcher {
	
	private ProcessBuilder game = new ProcessBuilder();
	
    private String java = "";
    private String javaxmx = "";
    private String username = "";
    private String version;
    private String name;
    private Version gameinfo;
    private long timestamp = System.currentTimeMillis();
    private String urlLib = Url.URL_DOWNLOAD_BASE;
    private int downloading = 0;
    private Downloader downloader = new Downloader();
    private BufferedInputStream gameOutput;
    private BufferedInputStream gameError;
    private Thread logThread;
    private Thread thError;
    private Thread thOutput;
    private File GameLock = new File(BMCLLite.getCurrectDirectory() + "Game.lck");
    
    public Launcher(String JavaPath, String JavaXmx, String UserName, String name, Version info, String extarg){
    	this.java = JavaPath;
    	if (Long.parseLong(JavaXmx) <0){
    		System.out.println("JavaXmx过小");
    		throw new NoEnoughMemoryException("JavaXmx过小");
    	}
    	this.username = UserName;
    	this.gameinfo = info;
    	this.version = this.gameinfo.id;
    	this.name = name;
    }
    
}
