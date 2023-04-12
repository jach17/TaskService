package com.axity.task.commons.request;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author jonathan.aldana@axity.com
 */
@Getter
@Setter
public class PaginatedRequestDto
{
  private int limit;
  private int offset;

  /**
   * Constructor default
   */
  public PaginatedRequestDto()
  {
  }

  /**
   * Constructor por límite y offset
   * 
   * @param limit
   * @param offset
   */
  public PaginatedRequestDto( int limit, int offset )
  {
    this.limit = limit;
    this.offset = offset;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString()
  {
    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    return gson.toJson( this );
  }
}
