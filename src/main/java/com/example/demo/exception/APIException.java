package com.example.demo.exception;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.example.demo.response.ResponseWrapper;


@ControllerAdvice
@Order(-2)
public class APIException{
	
	@ExceptionHandler(value= InvalidTokenException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public final ResponseEntity<ResponseWrapper<String>> invalidTokenException(InvalidTokenException ex, WebRequest req){
		ResponseWrapper<String> rsp = new ResponseWrapper<String>(ex.errorMsg);
		return new ResponseEntity<ResponseWrapper<String>>(rsp,HttpStatus.BAD_REQUEST);
	}
	
}
