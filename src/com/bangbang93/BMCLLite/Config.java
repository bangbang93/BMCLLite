package com.bangbang93.BMCLLite;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Config implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9018616938226005965L;
	public String javaw,username,javaxmx,login,lastPlayVer,extraJVMArg,Lang;
	public byte[] passwd;
	public boolean autostart, Report,CheckUpdate;
	public double WindowTransparency;
	public int DownloadSource;
	
	public Config() {
		javaw = getJavaDir();
		username = "!!!";
		javaxmx = String.format("%d", getMem());
		passwd = null;
		login = "啥都没有";
		autostart = false;
		extraJVMArg = " -Dfml.ignoreInvalidMinecraftCertificates=true -Dfml.ignorePatchDiscrepancies=true";
		WindowTransparency = 1;
		Report = true;
		DownloadSource = 0;
		Lang = "zh-cn";
		CheckUpdate = true;
		save(this);
    }
	
	public static void save(Config config, String configFilePath){
		try {
			File configFile = new File(configFilePath);
			configFile.createNewFile();
			FileOutputStream configFileWriter = new FileOutputStream(configFile);
			XStream xs = new XStream();
			xs.alias("config", Config.class);
			xs.toXML(config, configFileWriter);
			configFileWriter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void save(Config config){
		Config.save(config, BMCLLite.getCurrectDirectory() + "bmcl.xml");
	}
	
	public void save(){
		Config.save(this);
	}
	
	public void save(String configFilePath){
		Config.save(this, configFilePath);
	}
	
	public static Config load(String configFilePath){
		try {
			File configFile = new File(configFilePath);
			FileInputStream configFileStream = new FileInputStream(configFile);
			XStream xs = new XStream(new DomDriver());
			xs.alias("config", Config.class);
			Config config = (Config) xs.fromXML(configFileStream);
			if (config == null){
				return new Config();
			} else {
				return config;
			}
		} catch (FileNotFoundException e) {
			return new Config();
		} 
	}
	
	public static Config load(){
		return Config.load("bmcl.xml");
	}
	
	public static String getJavaDir(){
		return System.getProperty("java.home") + "/bin/javaw.exe";
	}
	
	public static long getMem(){
		return ((com.sun.management.OperatingSystemMXBean)java.lang.management.ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize() /1024 /1024;
	}
}
