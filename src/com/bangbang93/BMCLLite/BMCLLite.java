package com.bangbang93.BMCLLite;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.bangbang93.BMCLLite.GameVersion.Version;
import com.bangbang93.BMCLLite.Gui.MainWindow;

public class BMCLLite {
	public static Version selectedVersion;
	public static Config config;
	public static String os = System.getProperty("os.name").toLowerCase().trim();
	public static final Logger logger = Logger.getLogger("BMCLLite");
	public static final boolean is64BitOS = (System.getProperty("sun.arch.data.model") == "64"? true:false);
	public static boolean debug = true;
	public static char pathSpilter = System.getProperty("file.separator").charAt(0);
	
	public static void main(String[] args){
		try {
			FileHandler fileHandler = new FileHandler("bmcl.log", false);
			fileHandler.setLevel(Level.ALL);
			fileHandler.setFormatter(new SimpleFormatter());
			logger.addHandler(fileHandler);
			logger.info("BMCLLite启动中");
			config = Config.load();
			MainWindow.main(args);
		} catch (SecurityException | IOException e) {
			logger.severe("Log文件创建失败");
			e.printStackTrace();
		}
	}
	
	public static String getCurrectDirectory(){
		return System.getProperty("user.dir") + BMCLLite.pathSpilter;
	}
	
	public static String getMinecraftDirectory(){
		return getCurrectDirectory() + ".minecraft" + BMCLLite.pathSpilter;
	}
	public static Logger getLogger(){
		return BMCLLite.logger;
	}
	
}
