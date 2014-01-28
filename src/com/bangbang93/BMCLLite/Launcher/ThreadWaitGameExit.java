package com.bangbang93.BMCLLite.Launcher;

public class ThreadWaitGameExit implements Runnable {
	
	private Process game;
	private Launcher launcher;
	
	public ThreadWaitGameExit(Launcher launcher, Process game){
		this.launcher = launcher;
		this.game = game;
	}
	@Override
	public void run() {
		try {
			int exitCode = game.waitFor();
			launcher.gameExit(exitCode);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
