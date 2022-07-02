package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.demo.exception.InvalidTokenException;
import com.example.demo.modal.AuthenticationRequest;
import com.example.demo.modal.JWTToken;
import com.example.demo.service.AuthenticateService;
import com.example.demo.service.CustomUserDetailService;
import com.example.demo.util.JWTUtil;

@Service
public class UserAuthenticationService implements AuthenticateService{
	
	@Autowired
	private AuthenticationManager authenticationMgr;
	
	@Autowired
	private CustomUserDetailService userDetailService;
	
	@Autowired
	JWTUtil jwtutil;

	@Override
	public JWTToken authenticateUser(AuthenticationRequest authReq) {
		try {
			 authenticationMgr.authenticate(new UsernamePasswordAuthenticationToken(authReq.getUsername(),authReq.getPassword()));
		}catch(BadCredentialsException ex) {
			throw new InvalidTokenException("Invalid Username and Password");
		}
		
		UserDetails userDetails = userDetailService.loadUserByUsername(authReq.getUsername());
		String jwt = jwtutil.generateToken(userDetails);
		return new JWTToken(jwt);
		
	}

	
}
