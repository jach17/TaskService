package com.axity.task.commons.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.Getter;
import lombok.Setter;

/**
 * Paginated response transfer object class
 * 
 * @author jonathan.aldana@axity.com
 */
@Getter
@Setter
public class PaginatedResponseDto<T extends Serializable> implements Serializable
{
  private static final long serialVersionUID = -7923049268047269630L;

  private HeaderDto header;

  private long page;
  private long size;
  private long pages;
  private long registries;
  private List<T> data;

  /**
   * Constructor default
   */
  public PaginatedResponseDto()
  {
    this.header = new HeaderDto();
  }

  /**
   * Constructor by page, size and registries
   * 
   * @param page
   * @param size
   * @param registries
   */
  public PaginatedResponseDto( long page, long size, long registries )
  {
    this.header = new HeaderDto();
    this.page = page;
    this.size = size;
    this.registries = registries;

    if( registries % size == 0 )
    {
      this.pages = registries / size;
    }
    else
    {
      this.pages = registries / size + 1;
    }
    this.data = new ArrayList<>();
  }

  /**
   * Constructor by page, size, registries and data
   * 
   * @param page
   * @param size
   * @param registries
   * @param data
   */
  public PaginatedResponseDto( long page, long size, long registries, List<T> data )
  {
    this( page, size, registries );
    this.data = data;
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
