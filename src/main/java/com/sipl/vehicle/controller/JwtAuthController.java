package com.sipl.vehicle.controller;

import org.springframework.http.ResponseEntity;

import com.sipl.vehicle.jwt.request.JwtAuthRequest;
import com.sipl.vehicle.jwt.response.JwtAuthResponse;

public interface JwtAuthController {
	ResponseEntity<JwtAuthResponse> createAuthenticationToken(JwtAuthRequest request);

}
