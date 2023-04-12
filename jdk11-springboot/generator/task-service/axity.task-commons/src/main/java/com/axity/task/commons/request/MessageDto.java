package com.axity.task.commons.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Message Transfer object
 * 
 * @author jonathan.aldana@axity.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto
{
  private String message;
  private String json;
}
