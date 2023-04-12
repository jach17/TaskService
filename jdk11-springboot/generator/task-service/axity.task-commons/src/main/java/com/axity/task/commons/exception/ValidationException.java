package com.axity.task.commons.exception;

import com.axity.task.commons.enums.ErrorCode;

import lombok.Getter;

/**
 * Validation Exception
 * 
 * @author jonathan.aldana@axity.com
 */
@Getter
public class ValidationException extends RuntimeException
{
  private static final long serialVersionUID = 7665760496552274896L;
  private int code;

  /**
   * Instantiates a new internal error exception.
   */
  public ValidationException()
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
  public ValidationException( String message, Throwable cause )
  {
    super( message, cause );
    this.code = 0;
  }

  /**
   * Instantiates a new internal error exception.
   *
   * @param message the message
   */
  public ValidationException( String message )
  {
    super( message );
    this.code = 0;
  }

  /**
   * Instantiates a new internal error exception.
   *
   * @param cause the cause
   */
  public ValidationException( Throwable cause )
  {
    super( cause );
    this.code = 0;
  }

  /**
   * Instantiates a new internal error exception.
   * 
   * @param code
   */
  public ValidationException( int code )
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
  public ValidationException( int code, String message )
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
  public ValidationException( int code, Throwable cause )
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
  public ValidationException( int code, String message, Throwable cause )
  {
    super( message, cause );
    this.code = code;
  }

  /**
   * Instantiates a new internal error exception.
   * 
   * @param code
   */
  public ValidationException( ErrorCode code )
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
  public ValidationException( ErrorCode code, String message )
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
  public ValidationException( ErrorCode code, Throwable cause )
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
  public ValidationException( ErrorCode code, String message, Throwable cause )
  {
    super( message, cause );
    this.code = code.getCode();
  }
}
