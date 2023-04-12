package com.axity.task.facade;

import java.util.List;

import com.axity.task.commons.dto.TaskDto;
import com.axity.task.commons.request.PaginatedRequestDto;
import com.axity.task.commons.response.GenericResponseDto;
import com.axity.task.commons.response.PaginatedResponseDto;

import graphql.schema.DataFetchingEnvironment;

/**
 * Interface TaskFacade
 * 
 * @author jonathan.aldana@axity.com
 */
public interface TaskFacade
{
  /**
   * Method to get Tasks
   * 
   * @param request
   * @return
   */
  PaginatedResponseDto<TaskDto> findTasks( PaginatedRequestDto request );

  /**
   * Method to get Task by id
   * 
   * @param id
   * @return
   */
  GenericResponseDto<TaskDto> find( Integer id );

  /**
   * Method to create a Task
   * 
   * @param dto
   * @return
   */
  GenericResponseDto<TaskDto> create( TaskDto dto );

  /**
   * Method to update a Task
   * 
   * @param dto
   * @return
   */
  GenericResponseDto<Boolean> update( TaskDto dto );

  /**
   * Method to delete a Task
   * 
   * @param id
   * @return
   */
  GenericResponseDto<Boolean> delete( Integer id );
}
