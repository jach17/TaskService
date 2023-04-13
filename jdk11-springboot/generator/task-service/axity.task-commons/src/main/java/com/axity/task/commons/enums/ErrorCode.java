package com.axity.task.commons.enums;

/**
 * Error code enumeration
 * 
 * @author jonathan.aldana@axity.com
 */
public enum ErrorCode {

  SUCCESSFULY_RESULT(0), UNKNOWN_ERROR(0), REQUIRED_FIELD(1), EXCEEDS_MAX_LENGTH(2),

  // Validation errors
  TASK_ALREADY_EXISTS(100), TASK_NOT_FOUND(101),

  STATUS_NOT_FOUND(201);

  private int code;

  private ErrorCode(int code) {
    this.code = code;
  }

  /**
   * @return the code
   */
  public int getCode() {
    return code;
  }

}
