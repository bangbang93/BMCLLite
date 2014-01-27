package com.bangbang93.BMCLLite.Launcher;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

public class OutputDebugger implements Runnable {
	private BufferedOutputStream gameOutput;
	private BufferedInputStream gameError;
	
	public OutputDebugger(BufferedOutputStream gameOutput, BufferedInputStream gameError) {
		this.gameOutput = gameOutput;
		this.gameError = gameError;
	}

	@Override
	public void run() {
	}

}
