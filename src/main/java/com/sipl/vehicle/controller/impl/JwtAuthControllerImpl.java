package com.sipl.vehicle.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sipl.vehicle.controller.JwtAuthController;
import com.sipl.vehicle.jwt.request.JwtAuthRequest;
import com.sipl.vehicle.jwt.response.JwtAuthResponse;
import com.sipl.vehicle.service.JwtAuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class JwtAuthControllerImpl implements JwtAuthController {
	@Autowired
	private JwtAuthService jwtAuthService;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createAuthenticationToken(@RequestBody JwtAuthRequest request) {
		JwtAuthResponse response = jwtAuthService.createAuthenticationToken(request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
