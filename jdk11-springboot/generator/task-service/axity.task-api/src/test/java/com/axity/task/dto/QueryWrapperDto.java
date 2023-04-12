package com.axity.task.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Query Wrapper Dto class
 * 
 * @author jonathan.aldana@axity.com
 */
@Getter
@Setter
public class QueryWrapperDto
{
  private String query;
  private Object variables = new Object();
}