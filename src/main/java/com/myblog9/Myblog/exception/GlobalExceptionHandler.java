package com.myblog9.Myblog.exception;

import com.myblog9.Myblog.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
@ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorDetails> ResourceNotFound(
ResourceNotFound ex,
WebRequest webrequest
    ){
    ErrorDetails err=new ErrorDetails(new Date(),ex.getMessage(),webrequest.getDescription(true));
return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
}
}
