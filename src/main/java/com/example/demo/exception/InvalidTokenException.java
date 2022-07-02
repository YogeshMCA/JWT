package com.example.demo.exception;

public class InvalidTokenException extends RuntimeException{

	public String errorMsg;
	public InvalidTokenException(String msg){
		super(msg);
		this.errorMsg = msg;
	}
}
