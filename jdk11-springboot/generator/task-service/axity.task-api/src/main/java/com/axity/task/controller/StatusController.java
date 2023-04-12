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
import com.axity.task.commons.dto.StatusDto;
import com.axity.task.commons.request.PaginatedRequestDto;
import com.axity.task.commons.response.GenericResponseDto;
import com.axity.task.commons.response.PaginatedResponseDto;
import com.axity.task.facade.StatusFacade;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

/**
 * Status controller class
 * 
 * @author jonathan.aldana@axity.com
 *
 */
@RestController
@RequestMapping("/api/status")
@CrossOrigin
@Slf4j
public class StatusController
{
  @Autowired
  private StatusFacade statusFacade;

  

  /***
   * Method to get Status
   * 
   * @param limit The limit
   * @param offset The offset
   * @return A paginated result of Status
   */
  @JsonResponseInterceptor
  @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(tags = "Statuss", summary = "Method to get Statuss paginated")
  public ResponseEntity<PaginatedResponseDto<StatusDto>> findStatuss(
      @RequestParam(name = "limit", defaultValue = "50", required = false)
      int limit, @RequestParam(name = "offset", defaultValue = "0", required = false)
      int offset )
  {
    var result = this.statusFacade.findStatuss( new PaginatedRequestDto( limit, offset ) );
    return ResponseEntity.ok( result );
  }

  /**
   * Method to get Status by id
   * 
   * @param id The status Id
   * @return An registry of status or 204
   */
  @JsonResponseInterceptor
  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(tags = "Statuss", summary = "Method to get Status by id")
  public ResponseEntity<GenericResponseDto<StatusDto>> findStatus( @PathVariable("id")
  Integer id )
  {
    var result = this.statusFacade.find( id );

    HttpStatus status = HttpStatus.OK;
    if( result == null )
    {
      status = HttpStatus.NO_CONTENT;
    }
    return new ResponseEntity<>( result, status );
  }

  private String getStatusKey( Integer id )
  {
    String key = new StringBuilder().append( "Status.byStatusId:" ).append( id ).toString();
    return key;
  }

  /**
   * Method to create a Status
   * 
   * @param dto The Status object
   * @return
   */
  @JsonResponseInterceptor
  @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(tags = "Statuss", summary = "Method to create a Status")
  public ResponseEntity<GenericResponseDto<StatusDto>> create( @RequestBody StatusDto dto )
  {
    var result = this.statusFacade.create( dto );
    return new ResponseEntity<>( result, HttpStatus.CREATED );
  }

  /**
   * Method to update a Status
   * 
   * @param id The Status Id
   * @param dto The Status object
   * @return
   */
  @JsonResponseInterceptor
  @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(tags = "Statuss", summary = "Method to update a Status")
  public ResponseEntity<GenericResponseDto<Boolean>> update( @PathVariable("id") Integer id, @RequestBody StatusDto dto )
  {
    dto.setId( id );
    var result = this.statusFacade.update( dto );

    

    return ResponseEntity.ok( result );
  }

  /**
   * Method to delete a Status
   * 
   * @param id The Status Id
   * @return
   */
  @JsonResponseInterceptor
  @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(tags = "Statuss", summary = "Method to delete a Status")
  public ResponseEntity<GenericResponseDto<Boolean>> delete( @PathVariable("id") Integer id )
  {
    var result = this.statusFacade.delete( id );

    
    return ResponseEntity.ok( result );
  }

  /**
   * Ping
   * 
   * @return Pong
   */
  @JsonResponseInterceptor
  @GetMapping(path = "ping", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(tags = "Statuss", summary = "Ping")
  public ResponseEntity<GenericResponseDto<String>> ping( )
  {
    return ResponseEntity.ok( new GenericResponseDto<>("pong") );
  }
}
