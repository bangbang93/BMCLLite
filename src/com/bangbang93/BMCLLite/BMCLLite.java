package com.bangbang93.BMCLLite;

import com.bangbang93.BMCLLite.GameVersion.Version;
import com.bangbang93.BMCLLite.Gui.MainWindow;

public class BMCLLite {
	public static Version selectedVersion ;
	public static void main(String[] args){
		MainWindow.main(args);
	}
	public static String getCurrectDirectory(){
		return System.getProperty("user.dir") + "/";
	}
}
