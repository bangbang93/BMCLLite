package com.bangbang93.BMCLLite.GameVersion;

import java.io.File;
import java.io.Serializable;

import com.bangbang93.BMCLLite.BMCLLite;
import com.bangbang93.BMCLLite.Exception.UnSupportVersionException;
import com.bangbang93.BMCLLite.Util.FileHelper;
import com.bangbang93.BMCLLite.Util.StringHelper;

public class Library implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2003423957804328541L;
	public String name;
	public OS natives;
	public Extract extract;
	public String url;
	public Rule[] rules;
	
	public static String getLibPath(Library library) throws UnSupportVersionException{
        StringBuilder libp = new StringBuilder(BMCLLite.getCurrectDirectory() + ".minecraft" + BMCLLite.pathSpilter + "libraries" + BMCLLite.pathSpilter);
        String[] split = library.name.split(":");//0 包;1 名字；2 版本
        if (split.length != 3)
        {
            throw new UnSupportVersionException("依赖包分析失败，意外的字符串规则");
        }
        libp.append(split[0].replace('.', BMCLLite.pathSpilter));
        libp.append(BMCLLite.pathSpilter);
        libp.append(split[1]).append(BMCLLite.pathSpilter);
        libp.append(split[2]).append(BMCLLite.pathSpilter);
        libp.append(split[1]).append("-");
        libp.append(split[2]).append(".jar");
        return libp.toString();
	}
	
	public static String getNativePath(Library lib)
    {
        StringBuilder libp = new StringBuilder(BMCLLite.getCurrectDirectory() + ".minecraft" + BMCLLite.pathSpilter + "libraries" + BMCLLite.pathSpilter);
        String[] split = lib.name.split(":");//0 包;1 名字；2 版本
        libp.append(split[0].replace('.', BMCLLite.pathSpilter));
        libp.append(BMCLLite.pathSpilter);
        libp.append(split[1]).append(BMCLLite.pathSpilter);
        libp.append(split[2]).append(BMCLLite.pathSpilter);
        libp.append(split[1]).append("-").append(split[2]).append("-").append(lib.natives.windows);
        libp.append(".jar");
        if (split[0] == "tv.twitch" && libp.indexOf("${arch}")!=-1)
        {
            if (BMCLLite.is64BitOS)
            {
                libp = StringHelper.replace(libp, "${arch}", "64");
            }
            else
            {
            	libp = StringHelper.replace(libp, "${arch}", "32");
            }
        }
        return libp.toString();
    }

	public static void cleanOldNatives(String path){
		File file = new File(path);
		File[] files = file.listFiles();
		for (File f : files){
			if (f.isDirectory()){
				if (f.getName().contains("-natives-")){
					FileHelper.deleteDir(f.getAbsolutePath());
				}
			}
		}
	}
}
