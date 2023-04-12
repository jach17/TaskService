package com.axity.task.service.util;

import org.springframework.kafka.support.serializer.JsonSerializer;

import com.axity.task.commons.request.MessageDto;

/**
 * Message Serializer class
 * 
 * @author jonathan.aldana@axity.com
 */
public class MessageSerializer extends JsonSerializer<MessageDto>
{

}
