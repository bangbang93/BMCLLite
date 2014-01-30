package com.bangbang93.BMCLLite.GameVersion;

import java.io.File;
import java.io.FileFilter;

public class JsonFileFilter implements FileFilter {

	@Override
	public boolean accept(File pathname) {
		if (pathname.getAbsolutePath().endsWith("json")){
			return true;
		} else {
			return false;
		}
	}

}
