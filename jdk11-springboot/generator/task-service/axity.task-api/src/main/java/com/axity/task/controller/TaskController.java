package com.axity.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.axity.task.commons.aspectj.JsonResponseInterceptor;
import com.axity.task.commons.dto.TaskDto;
import com.axity.task.commons.request.PaginatedRequestDto;
import com.axity.task.commons.response.GenericResponseDto;
import com.axity.task.commons.response.PaginatedResponseDto;
import com.axity.task.facade.TaskFacade;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

/**
 * Task controller class
 * 
 * @author jonathan.aldana@axity.com
 *
 */
@RestController
@RequestMapping("/api/tasks")
@CrossOrigin
@Slf4j
public class TaskController
{
  @Autowired
  private TaskFacade taskFacade;

  

  /***
   * Method to get Task
   * 
   * @param limit The limit
   * @param offset The offset
   * @return A paginated result of Task
   */
  @JsonResponseInterceptor
  @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(tags = "Tasks", summary = "Method to get Tasks paginated")
  public ResponseEntity<PaginatedResponseDto<TaskDto>> findTasks(
      @RequestParam(name = "limit", defaultValue = "50", required = false)
      int limit, @RequestParam(name = "offset", defaultValue = "0", required = false)
      int offset )
  {
    var result = this.taskFacade.findTasks( new PaginatedRequestDto( limit, offset ) );
    return ResponseEntity.ok( result );
  }

  /**
   * Method to get Task by id
   * 
   * @param id The task Id
   * @return An registry of task or 204
   */
  @JsonResponseInterceptor
  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(tags = "Tasks", summary = "Method to get Task by id")
  public ResponseEntity<GenericResponseDto<TaskDto>> findTask( @PathVariable("id")
  Integer id )
  {
    var result = this.taskFacade.find( id );

    HttpStatus status = HttpStatus.OK;
    if( result == null )
    {
      status = HttpStatus.NO_CONTENT;
    }
    return new ResponseEntity<>( result, status );
  }

  private String getTaskKey( Integer id )
  {
    String key = new StringBuilder().append( "Task.byTaskId:" ).append( id ).toString();
    return key;
  }

  /**
   * Method to create a Task
   * 
   * @param dto The Task object
   * @return
   */
  @JsonResponseInterceptor
  @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(tags = "Tasks", summary = "Method to create a Task")
  public ResponseEntity<GenericResponseDto<TaskDto>> create( @RequestBody TaskDto dto )
  {
    var result = this.taskFacade.create( dto );
    return new ResponseEntity<>( result, HttpStatus.CREATED );
  }

  /**
   * Method to update a Task
   * 
   * @param id The Task Id
   * @param dto The Task object
   * @return
   */
  @JsonResponseInterceptor
  @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(tags = "Tasks", summary = "Method to update a Task")
  public ResponseEntity<GenericResponseDto<Boolean>> update( @PathVariable("id") Integer id, @RequestBody TaskDto dto )
  {
    dto.setId( id );
    var result = this.taskFacade.update( dto );

    

    return ResponseEntity.ok( result );
  }

  /**
   * Method to delete a Task
   * 
   * @param id The Task Id
   * @return
   */
  @JsonResponseInterceptor
  @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(tags = "Tasks", summary = "Method to delete a Task")
  public ResponseEntity<GenericResponseDto<Boolean>> delete( @PathVariable("id") Integer id )
  {
    var result = this.taskFacade.delete( id );

    
    return ResponseEntity.ok( result );
  }

  /**
   * Ping
   * 
   * @return Pong
   */
  @JsonResponseInterceptor
  @GetMapping(path = "ping", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(tags = "Tasks", summary = "Ping")
  public ResponseEntity<GenericResponseDto<String>> ping( )
  {
    return ResponseEntity.ok( new GenericResponseDto<>("pong") );
  }
}
