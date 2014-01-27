package com.bangbang93.BMCLLite.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.bangbang93.BMCLLite.BMCLLite;

public class FileHelper {
	
	public static String readToEnd(String path){
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			StringBuffer sb = new StringBuffer();
			String line;
			while(true){
				line = reader.readLine();
				if (line == null){
					break;
				}
				sb.append(line);
			}
			return sb.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static String[] listDirectory(String path) throws FileNotFoundException{
		ArrayList<String> Dirs = new ArrayList<String>();
		File dir = new File(path);
		File[] files = dir.listFiles();
		if (files == null){
			throw new FileNotFoundException();
		}
		for (File f : files){
			if (f.isDirectory()){
				Dirs.add(f.getName());
			}
		}
		return Dirs.toArray(new String[Dirs.size()]);
	}

	public static boolean ifFileVaild(String path){
		File file = new File(path);
		if (!file.exists()){
			return false;
		}
		if (file.length() == 0){
			return false;
		}
		return true;
	}

	public static void copyFile(String src, String dest) throws IOException{
		File srcFile = new File(src);
		File destFile = new File(dest);
		if (!srcFile.exists()){
			throw new FileNotFoundException(src);
		}
		FileInputStream iStream = new FileInputStream(srcFile);
		FileOutputStream oStream = new FileOutputStream(destFile);
		byte[] buffer = new byte[4096];
		int bytes;
		while (true) {
			bytes = iStream.read(buffer);
			if (bytes < 0){
				break;
			}
			oStream.write(buffer, 0, bytes);
		}
		iStream.close();
		oStream.close();
	}
	
	public static void copyDir(String src, String dest) throws IOException{
		File srcFile = new File(src);
		File destFile = new File(dest);
		if (!srcFile.exists()){
			throw new FileNotFoundException(src);
		}
		if (!destFile.exists()){
			destFile.mkdirs();
		}
		File[] files = srcFile.listFiles();
		for(File f :files){
			if (f.isDirectory()){
				copyDir(f.getAbsolutePath(),dest + BMCLLite.pathSpilter + f.getName());
			} else {
				copyFile(f.getAbsolutePath(), dest + BMCLLite.pathSpilter + f.getName());
			}
		}
	}
}
