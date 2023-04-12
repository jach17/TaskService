package com.axity.task.commons.exception;

import com.axity.task.commons.enums.ErrorCode;

import lombok.Getter;

/**
 * Business Exception
 * 
 * @author jonathan.aldana@axity.com
 */
@Getter
public class BusinessException extends RuntimeException
{
  private static final long serialVersionUID = 7665760496552274896L;
  private int code;

  /**
   * Instantiates a new internal error exception.
   */
  public BusinessException()
  {
    super();
    this.code = 0;
  }

  /**
   * Instantiates a new internal error exception.
   *
   * @param message the message
   * @param cause the cause
   */
  public BusinessException( String message, Throwable cause )
  {
    super( message, cause );
    this.code = 0;
  }

  /**
   * Instantiates a new internal error exception.
   *
   * @param message the message
   */
  public BusinessException( String message )
  {
    super( message );
    this.code = 0;
  }

  /**
   * Instantiates a new internal error exception.
   *
   * @param cause the cause
   */
  public BusinessException( Throwable cause )
  {
    super( cause );
    this.code = 0;
  }

  /**
   * Instantiates a new internal error exception.
   * 
   * @param code
   */
  public BusinessException( int code )
  {
    super();
    this.code = code;
  }

  /**
   * Instantiates a new internal error exception.
   * 
   * @param code
   * @param message
   */
  public BusinessException( int code, String message )
  {
    super( message );
    this.code = code;
  }

  /**
   * Instantiates a new internal error exception.
   * 
   * @param code
   * @param cause
   */
  public BusinessException( int code, Throwable cause )
  {
    super( cause );
    this.code = code;
  }

  /**
   * Instantiates a new internal error exception.
   * 
   * @param code
   * @param message
   * @param cause
   */
  public BusinessException( int code, String message, Throwable cause )
  {
    super( message, cause );
    this.code = code;
  }

  /**
   * Instantiates a new internal error exception.
   * 
   * @param code
   */
  public BusinessException( ErrorCode code )
  {
    super();
    this.code = code.getCode();
  }

  /**
   * Instantiates a new internal error exception.
   * 
   * @param code
   * @param message
   */
  public BusinessException( ErrorCode code, String message )
  {
    super( message );
    this.code = code.getCode();
  }

  /**
   * Instantiates a new internal error exception.
   * 
   * @param code
   * @param cause
   */
  public BusinessException( ErrorCode code, Throwable cause )
  {
    super( cause );
    this.code = code.getCode();
  }

  /**
   * Instantiates a new internal error exception.
   * 
   * @param code
   * @param message
   * @param cause
   */
  public BusinessException( ErrorCode code, String message, Throwable cause )
  {
    super( message, cause );
    this.code = code.getCode();
  }
}
