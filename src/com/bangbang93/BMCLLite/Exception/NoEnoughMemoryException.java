package com.bangbang93.BMCLLite.Exception;

public class NoEnoughMemoryException extends OutOfMemoryError {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3560420082814023200L;
	
	public NoEnoughMemoryException(){
		super();
	}
	
	public NoEnoughMemoryException(String s){
		super(s);
	}
}
