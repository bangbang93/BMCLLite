package com.bangbang93.BMCLLite.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import com.bangbang93.BMCLLite.BMCLLite;
import com.bangbang93.BMCLLite.GameVersion.Extract;

public class ZipHelper {
	
	private static Logger logger = BMCLLite.getLogger();
	
	public static void unZip(String file, String path, Extract extract){
		try {
			File f = new File(file);
			ZipFile zipFile = new ZipFile(f);
			ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(f), Charset.forName("UTF-8"));
			ZipEntry zipEntry;
			while ((zipEntry = zipInputStream.getNextEntry())!=null){
				boolean exc = false;
				String filename = zipEntry.getName();
				for (String excFile : extract.exclude){
					if (filename.contains(excFile)){
						exc = true;
						break;
					}
				}
				if (exc) continue;
				filename = path + filename;
				logger.info(filename);
				File outputFile = new File(filename);
				OutputStream oStream = new FileOutputStream(outputFile);
				InputStream iStream = zipFile.getInputStream(zipEntry);
				byte[] buffer = new byte[4096];
				int bytes = 0;
				while (true){
					bytes = iStream.read(buffer);
					if (bytes < 0){
						break;
					}
					oStream.write(buffer, 0, bytes);
				}
				oStream.close();
				iStream.close();
			}
			zipInputStream.close();
		} catch (IOException e) {
			logger.severe("解压" + file + "失败");
			e.printStackTrace();
		}
		
	}
}
