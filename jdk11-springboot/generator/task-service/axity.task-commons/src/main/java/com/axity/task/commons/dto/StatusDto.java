package com.axity.task.commons.dto;

import java.io.Serializable;

import java.util.List;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Status Transfer Object class
 * 
 * @author jonathan.aldana@axity.com
 */
@Getter
@Setter
public class StatusDto implements Serializable
{
  private static final long serialVersionUID = 1L;

  
  @Schema(required = true, description = "The id")
  private  Integer id;
  
  @Schema(required = true, description = "The name of the status")
  private  String name;
  

  

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
