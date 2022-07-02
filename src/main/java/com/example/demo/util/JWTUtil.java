package com.example.demo.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.demo.exception.InvalidTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@Service
public class JWTUtil {

	public String generateToken(UserDetails details) {
		Map<String,Object> map = new HashMap<>();
		return createToken(map,details.getUsername());
	}

	private String createToken(Map<String, Object> map, String username) {
		return Jwts.builder().setClaims(map).setSubject(username).setExpiration(new Date(System.currentTimeMillis() + 1000 * 120))
				.setIssuedAt(new Date(System.currentTimeMillis())).signWith(SignatureAlgorithm.HS256, "secret").compact();
		
	}
	private boolean isTokenExpired(String jwt) throws InvalidTokenException {
		
			return ((Date)extractClaims(jwt,Claims::getExpiration)).before(new Date());
		
	}
	public String getUserName(String jwt) {
		return extractUserName(jwt);
	}
	private Claims extractAllClaims(String jwt) throws InvalidTokenException {
	try {
		return Jwts.parser().setSigningKey("secret").parseClaimsJws(jwt).getBody();
	}catch(ExpiredJwtException e) {
		throw new InvalidTokenException("Token Expired");
	}
	
	}
	private <T> T extractClaims(String jwt, Function<Claims,T> claims) {
		return claims.apply(extractAllClaims(jwt));
	}
	private String extractUserName(String jwt) {
		return extractClaims(jwt,Claims::getSubject);
	}
	public boolean validateToken(String jwt,UserDetails userDetails)  {
		String name = getUserName(jwt);
		return (name.equals(userDetails.getUsername()) && !isTokenExpired(jwt));
	}
}
