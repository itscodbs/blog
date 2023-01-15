package com.cos.blog.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;

@ControllerAdvice // Exception이 발생했을 때, 해당 클래스로 오게끔
@RestController
public class GlobalExceptionHandler {
	
	// IllegalArgumentException이 발생했을 때 = IllegalArgumentException
	@ExceptionHandler(value=Exception.class)
	public ResponseDto<String> handleArgumentException(Exception e) {
		return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()); // 500 
	}
}
