package com.example.demo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(Include.NON_NULL)
public class ResponseWrapper<T> {
	public T data;
	
	public ResponseWrapper(T data) {
		this.data = data;
	}
	
}
