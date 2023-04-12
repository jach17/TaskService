package com.axity.task.commons.aspectj;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.axity.task.commons.enums.ErrorCode;
import com.axity.task.commons.exception.BusinessException;
import com.axity.task.commons.exception.ValidationException;
import com.axity.task.commons.response.GenericResponseDto;
import com.axity.task.commons.response.HeaderDto;

/**
 * Aspect Json Response Handler Interceptor class
 * 
 * @author jonathan.aldana@axity.com
 */
@Aspect
@Component
public class JsonResponseHandlerInterceptor implements HandlerInterceptor
{
  private static final Logger LOG = LoggerFactory.getLogger( JsonResponseHandlerInterceptor.class );

  @Autowired
  private Environment env;

  @Value("${arquetipo.allowTrace}")
  private boolean allowTrace;

  /**
   * 
   */
  @Around("execution (* com.axity.task.controller.*.*(..))"
      + " and @annotation(com.axity.task.commons.aspectj.JsonResponseInterceptor)")
  public Object interceptMethodAdvice( ProceedingJoinPoint pjp ) throws Throwable
  {
    Object result = null;
    try
    {
      LOG.debug( "{}", pjp.toLongString() );
      result = pjp.proceed();
    }
    catch (ValidationException e) {
      LOG.error( e.getMessage(), e );

      var genericResponse = new GenericResponseDto<>();
      var header = new HeaderDto();
      header.setMessage( this.env.getProperty( String.format( "arquetipo.error.%d", e.getCode() ) ) );
      header.setCode( e.getCode() );
      genericResponse.setHeader( header );
      result = new ResponseEntity<>( genericResponse, HttpStatus.BAD_REQUEST );
    }
    catch( BusinessException e )
    {
      LOG.error( e.getMessage(), e );

      var genericResponse = new GenericResponseDto<>();
      var header = new HeaderDto();
      header.setMessage( this.env.getProperty( String.format( "arquetipo.error.%d", e.getCode() ) ) );
      header.setCode( e.getCode() );
      header.setDetail( e.getMessage() );
      genericResponse.setHeader( header );
      result = new ResponseEntity<>( genericResponse, HttpStatus.INTERNAL_SERVER_ERROR );
    }
    catch( Exception e )
    {
      LOG.error( e.getMessage(), e );

      var genericResponse = new GenericResponseDto<>();
      var header = new HeaderDto();
      header.setMessage(
        this.env.getProperty( String.format( "arquetipo.error.%d", ErrorCode.UNKNOWN_ERROR.getCode() ) ) );
      header.setCode( ErrorCode.UNKNOWN_ERROR.getCode() );

      if( this.allowTrace )
      {
        header.setDetail( String.format( "%s %s", e.getMessage(), ExceptionUtils.getStackTrace( e ) ) );
      }
      else
      {
        header.setDetail( e.getMessage() );
      }
      genericResponse.setHeader( header );
      result = new ResponseEntity<>( genericResponse, HttpStatus.INTERNAL_SERVER_ERROR );
    }
    return result;
  }

}
