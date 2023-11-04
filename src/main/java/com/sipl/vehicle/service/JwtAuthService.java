package com.sipl.vehicle.service;

import com.sipl.vehicle.jwt.request.JwtAuthRequest;
import com.sipl.vehicle.jwt.response.JwtAuthResponse;

public interface JwtAuthService {
	JwtAuthResponse createAuthenticationToken(JwtAuthRequest request);

}
