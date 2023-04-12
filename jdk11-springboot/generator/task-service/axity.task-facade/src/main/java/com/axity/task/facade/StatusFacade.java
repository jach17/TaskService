package com.axity.task.facade;

import java.util.List;

import com.axity.task.commons.dto.StatusDto;
import com.axity.task.commons.request.PaginatedRequestDto;
import com.axity.task.commons.response.GenericResponseDto;
import com.axity.task.commons.response.PaginatedResponseDto;

import graphql.schema.DataFetchingEnvironment;

/**
 * Interface StatusFacade
 * 
 * @author jonathan.aldana@axity.com
 */
public interface StatusFacade
{
  /**
   * Method to get Statuss
   * 
   * @param request
   * @return
   */
  PaginatedResponseDto<StatusDto> findStatuss( PaginatedRequestDto request );

  /**
   * Method to get Status by id
   * 
   * @param id
   * @return
   */
  GenericResponseDto<StatusDto> find( Integer id );

  /**
   * Method to create a Status
   * 
   * @param dto
   * @return
   */
  GenericResponseDto<StatusDto> create( StatusDto dto );

  /**
   * Method to update a Status
   * 
   * @param dto
   * @return
   */
  GenericResponseDto<Boolean> update( StatusDto dto );

  /**
   * Method to delete a Status
   * 
   * @param id
   * @return
   */
  GenericResponseDto<Boolean> delete( Integer id );
}
