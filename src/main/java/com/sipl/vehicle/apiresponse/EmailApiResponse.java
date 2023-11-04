package com.sipl.vehicle.apiresponse;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailApiResponse {
private String message;
private HttpStatus status;
}
