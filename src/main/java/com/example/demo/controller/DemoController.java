package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.modal.AuthenticationRequest;
import com.example.demo.modal.JWTToken;
import com.example.demo.response.ResponseWrapper;
import com.example.demo.service.AuthenticateService;

@RestController
@CrossOrigin
@RequestMapping(value="/demo",method = RequestMethod.GET)
public class DemoController {

	
	List<String> lst = new ArrayList<>();
	@Autowired
	private AuthenticateService authService;
		
	@RequestMapping(value="/hello",method=RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseWrapper<String> getHello() {
		return new ResponseWrapper<String>("Hello");
	}
	
	@RequestMapping(value="/authenticate", method=RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseWrapper<JWTToken> proccessAuth(@RequestBody AuthenticationRequest auth){
		return new ResponseWrapper<JWTToken>(authService.authenticateUser(auth));
			
	}
	
	@RequestMapping(value="/getList", method=RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseWrapper<List<String>> getList() throws InterruptedException{
		lst.clear();
		Thread.sleep(10000);
		return new ResponseWrapper<List<String>>(lst);
			
	}
		
	@RequestMapping(value="/updateList", method=RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseWrapper<List<String>> updateList(){
		int i=0;
		lst.clear();
		for(;i<=1000000;i++) {}
			lst.add("I"+i);
		return new ResponseWrapper<List<String>>(lst);
			
	}
}
