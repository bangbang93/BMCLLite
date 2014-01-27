package com.bangbang93.BMCLLite.Launcher;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import com.bangbang93.BMCLLite.BMCLLite;
import com.bangbang93.BMCLLite.Exception.DownloadLibraryFailedException;
import com.bangbang93.BMCLLite.Exception.NoEnoughMemoryException;
import com.bangbang93.BMCLLite.Exception.UnSupportVersionException;
import com.bangbang93.BMCLLite.GameVersion.Library;
import com.bangbang93.BMCLLite.GameVersion.Rule;
import com.bangbang93.BMCLLite.GameVersion.Version;
import com.bangbang93.BMCLLite.Resource.Url;
import com.bangbang93.BMCLLite.Util.Downloader;
import com.bangbang93.BMCLLite.Util.FileHelper;
import com.bangbang93.BMCLLite.Util.StringHelper;
import com.bangbang93.BMCLLite.Util.ZipHelper;

public class Launcher {
	
	private ProcessBuilder game;
	private Process runningGame;
	private StringBuilder javaArgs = new StringBuilder();
	
	private Logger logger = BMCLLite.getLogger();
	
    private String java = "";
    private String javaxmx = "";
    private String username = "";
    private String version;
    private String name;
    private Version gameinfo;
    private long timestamp = System.currentTimeMillis();
    private String urlLib = Url.URL_DOWNLOAD_BASE;
    private Downloader downloader = new Downloader();
    private Thread logThread;
    private File GameLock = new File(BMCLLite.getCurrectDirectory() + "Game.lck");
    
    public Launcher(String JavaPath, String JavaXmx, String UserName, String name, Version info, String extarg) throws UnSupportVersionException, DownloadLibraryFailedException{
    	this.java = JavaPath;
    	if (Long.parseLong(JavaXmx) <0){
    		logger.severe("JavaXmx过小");
    		throw new NoEnoughMemoryException("JavaXmx过小");
    	}
    	this.username = UserName;
    	this.javaxmx = JavaXmx;
    	this.gameinfo = info;
    	this.version = this.gameinfo.id;
    	this.name = name;
    	game = new ProcessBuilder(java);
    	logger.info("正在创建命令行");
    	game.command().add("-Xincgc");
    	javaArgs.append("-Xincgc ");
    	game.command().add("-Xmx" + this.javaxmx + "M");
    	javaArgs.append("-Xmx" + this.javaxmx + "M ");
    	game.command().add(extarg.trim());
    	javaArgs.append(extarg.trim()).append(' ');
    	StringBuilder librariesPath = new StringBuilder("-Djava.library.path=\"");
    	librariesPath.append(Version.getVersionDir(name)).append(name).append("-natives-").append(timestamp);
    	librariesPath.append("\" -cp \"");
    	for (Library library : info.libraries){
    		logger.info("正在准备" + library.name);
    		if (library.natives !=null){
    			continue;
    		}
    		if (library.rules != null){
    			boolean goflag = false;
    			for (Rule rule : library.rules){
    				if (rule.action == "disallow"){
    					if (rule.os == null){
    						goflag = false;
    						break;
    					} 
    					if (rule.os.name.toLowerCase().trim() == BMCLLite.os){
    						goflag = false;
    						break;
    					}
    				} else {
    					if (rule.os == null){
    						goflag = true;
    						break;
    					} 
    					if (rule.os.name.toLowerCase().trim() == BMCLLite.os){
    						goflag = true;
    						break;
    					}
    				}
    			}
    			if (!goflag){
    				continue;
    			}
    		}
    		String libraryPath = Library.getLibPath(library);
    		if (!FileHelper.ifFileVaild(libraryPath)){
    			logger.warning("未找到依赖" + library.name + "开始下载");
    			String url;
    			if (library.url == null){
    				url = this.urlLib;
    			} else {
    				url = library.url;
    			}
    			logger.info(url);
    			logger.info(libraryPath);
    			downloader = new Downloader(url + libraryPath.substring(BMCLLite.getCurrectDirectory().length() + 22), libraryPath);
    			try {
					downloader.download();
				} catch (IOException e) {
					logger.severe("原始地址下载失败，尝试作者源");
					downloader = new Downloader(Url.URL_LIBRARIES_bangbang93 + libraryPath.substring(BMCLLite.getCurrectDirectory().length() + 22), libraryPath);
					try {
						downloader.download();
					} catch (IOException e1) {
						logger.severe(library.name + "下载失败，无法启动");
						throw new DownloadLibraryFailedException(library.name + "下载失败");
					}
				}
    		}
			librariesPath.append(libraryPath).append(";");
    	}
    	logger.info("创建mc参数");
    	StringBuilder mcpath = new StringBuilder(Version.getVersionDir(name));
    	mcpath.append(name).append(".jar");
    	mcpath.append("\"");
    	librariesPath.append(mcpath);
    	game.command().add(librariesPath.toString());
    	javaArgs.append(librariesPath.toString()).append(' ');
    	game.command().add(info.mainClass);
    	javaArgs.append(info.mainClass).append(' ');
    	StringBuilder mcArg = new StringBuilder(info.minecraftArguments);
    	mcArg = StringHelper.replace(mcArg, "${auth_player_name}", username);
    	mcArg = StringHelper.replace(mcArg, "${version_name}", version);
    	mcArg = StringHelper.replace(mcArg, "${game_directory}", ".minecraft");
    	mcArg = StringHelper.replace(mcArg, "${game_assets}", ".minecraft" + BMCLLite.pathSpilter + "assets");
    	mcArg = StringHelper.replace(mcArg, "${assets_root}", ".minecraft" + BMCLLite.pathSpilter + "assets");
    	mcArg = StringHelper.replace(mcArg, "${user_type}", "Legacy");
    	mcArg = StringHelper.replace(mcArg, "${assets_index_name}", info.assets);
    	mcArg = StringHelper.replace(mcArg, "${auth_uuid}", "{}");
    	mcArg = StringHelper.replace(mcArg, "${auth_access_token}", "{}");
    	mcArg = StringHelper.replace(mcArg, "${user_properties}", "{}");
    	//TODO Auth
    	mcArg = StringHelper.replace(mcArg, "${auth_session}", "no");
    	game.command().add(mcArg.toString());
    	logger.info(game.command().toString());
    	javaArgs.append(mcArg).append(' ');
    	logger.info(javaArgs.toString());
    }
    
    public boolean start() throws DownloadLibraryFailedException, IOException{
    	logger.info("释放依赖并启动游戏");
    	StringBuilder nativeExtPath = new StringBuilder(Version.getVersionDir(name));
    	Library.cleanOldNatives(nativeExtPath.toString());
    	nativeExtPath.append(version).append("-natives-").append(timestamp).append(BMCLLite.pathSpilter);
    	File nativeExtPathFile = new File(nativeExtPath.toString());
    	nativeExtPathFile.mkdir();
    	for (Library library: gameinfo.libraries){
    		if (library.natives == null){
    			continue;
    		}
    		if (library.rules != null){
    			boolean goflag = false;
    			for (Rule rule : library.rules){
    				if (rule.action == "disallow"){
    					if (rule.os == null){
    						goflag = false;
    						break;
    					}
    					if (rule.os.name.toLowerCase().trim() == BMCLLite.os){
    						goflag = false;
    						break;
    					}
    				}  else {
    					if (rule.os == null){
    						goflag = true;
    						break;
    					} 
    					if (rule.os.name.toLowerCase().trim() == BMCLLite.os){
    						goflag = true;
    						break;
    					}
    				}
    			}
    			if (!goflag){
    				continue;
    			}
    		}
    		logger.info("处理" + library.name);
    		String nativePath = Library.getNativePath(library);
    		if (!FileHelper.ifFileVaild(nativePath)){
    			logger.warning("未找到" + library.name + "开始下载");
    			String url;
    			if (library.url == null){
    				url = this.urlLib;
    			} else {
    				url = library.url;
    			}
    			logger.info(url);
    			logger.info(nativePath);
    			downloader = new Downloader(url + nativePath.substring(BMCLLite.getCurrectDirectory().length() + 22), nativePath);
    			try {
					downloader.download();
				} catch (IOException e) {
					logger.severe("原始地址下载失败，尝试作者源");
					downloader = new Downloader(Url.URL_LIBRARIES_bangbang93 + nativePath.substring(BMCLLite.getCurrectDirectory().length() + 22), nativePath);
					try {
						downloader.download();
					} catch (IOException e1) {
						logger.severe(library.name + "下载失败，无法启动");
						throw new DownloadLibraryFailedException(library.name + "下载失败");
					}
				}
    		}
    		ZipHelper.unZip(nativePath, nativeExtPath.toString(), library.extract);
    	}
    	game.directory(new File(BMCLLite.getCurrectDirectory()));
    	game.environment().put("APPDATA", BMCLLite.getCurrectDirectory());
    	if (BMCLLite.debug){
    		//game.redirectErrorStream(true);
    		game.redirectError(new File("javaerror.log"));
    		game.redirectOutput(new File("javaoutput.log"));
    	}
		runningGame = game.start();
		GameLock.createNewFile();
		if (BMCLLite.debug){
			logThread = new Thread(new OutputDebugger(new BufferedOutputStream(runningGame.getOutputStream()), new BufferedInputStream(runningGame.getErrorStream())));
			logThread.run();
		}
		return true;
    }
    
}
