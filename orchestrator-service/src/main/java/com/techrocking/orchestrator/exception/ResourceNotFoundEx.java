package com.techrocking.orchestrator.exception;

public class ResourceNotFoundEx extends Exception{

   private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundEx(String error) {
		super(error);
	}
}
