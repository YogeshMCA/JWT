package com.example.demo.service;

import com.example.demo.exception.InvalidTokenException;
import com.example.demo.modal.AuthenticationRequest;
import com.example.demo.modal.JWTToken;

public interface AuthenticateService {
public JWTToken authenticateUser(AuthenticationRequest authReq) throws InvalidTokenException;
}
