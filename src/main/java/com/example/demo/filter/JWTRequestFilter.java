package com.example.demo.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.exception.InvalidTokenException;
import com.example.demo.response.ResponseWrapper;
import com.example.demo.service.CustomUserDetailService;
import com.example.demo.util.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
@Component
public class JWTRequestFilter extends OncePerRequestFilter{

	@Autowired
	CustomUserDetailService userDetailService;
	
	@Autowired
	JWTUtil jwtUtil;
	
	@Autowired
	ObjectMapper objMapper;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwtToken = request.getHeader("Authorization");
		try {	
		if(jwtToken!=null && jwtToken.startsWith("Bearer")) {
			String jwt = jwtToken.substring(7);
			String name = jwtUtil.getUserName(jwt);
			if(name !=null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userdetails = this.userDetailService.loadUserByUsername(name);
					
						if(jwtUtil.validateToken(jwt, userdetails)) {
							UsernamePasswordAuthenticationToken userNamePasswordAuthToken = new UsernamePasswordAuthenticationToken(userdetails, null,userdetails.getAuthorities());
							userNamePasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
							SecurityContextHolder.getContext().setAuthentication(userNamePasswordAuthToken);
						}
					
				} 
			}
		
		filterChain.doFilter(request, response);
		}catch(InvalidTokenException ex) {
			ResponseWrapper<String> resp = new ResponseWrapper<String>(ex.errorMsg);
			response.setContentType("application/json");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.getWriter().write(objMapper.writeValueAsString(resp));
		}
		
	}
	

}
