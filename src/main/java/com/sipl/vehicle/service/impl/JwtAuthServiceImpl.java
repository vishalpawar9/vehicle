package com.sipl.vehicle.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.sipl.vehicle.jwt.JwtTokenHelper;
import com.sipl.vehicle.jwt.request.JwtAuthRequest;
import com.sipl.vehicle.jwt.response.JwtAuthResponse;
import com.sipl.vehicle.service.JwtAuthService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JwtAuthServiceImpl implements JwtAuthService {
	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public JwtAuthResponse createAuthenticationToken(JwtAuthRequest request) {
		log.info("creation Authentication endpoint called");
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				request.getUsername(), request.getPassword());
		JwtAuthResponse response = new JwtAuthResponse();
		try {
			this.authenticationManager.authenticate(authenticationToken);
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
			log.info("user details" + userDetails);
			String token = this.jwtTokenHelper.generateToken(userDetails);
			log.info("Token " + token);
			response.setToken(token);
			response.setMessage("TOKEN SUCCESSFULLY GENERATED");
			response.setStatus(HttpStatus.OK);
			return response;
		} catch (BadCredentialsException e) {
			e.printStackTrace();
			response.setMessage("Username or Password Incorrect");
			response.setStatus(HttpStatus.NOT_FOUND);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Internal Server Error");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			return response;
		}
	}

	private void authenticate(String username, String password) throws Exception {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);
		try {
			this.authenticationManager.authenticate(authenticationToken);
		} catch (BadCredentialsException e) {
			e.printStackTrace();
			System.out.println("Invalid Details!");
			throw new Exception("Invalid Username or Password!!");

		}
	}
}
