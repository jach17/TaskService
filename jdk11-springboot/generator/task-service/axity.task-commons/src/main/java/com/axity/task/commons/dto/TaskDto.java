package com.axity.task.commons.dto;

import java.io.Serializable;




import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Task Transfer Object class
 * 
 * @author jonathan.aldana@axity.com
 */
@Getter
@Setter
public class TaskDto implements Serializable
{
  private static final long serialVersionUID = 1L;

  
  @Schema(required = true, description = "The id")
  private  Integer id;
  
  @Schema(required = true, description = "The task")
  private  String name;
  
  @Schema(required = true, description = "The status id")
  private  StatusDto status;
  

  

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
