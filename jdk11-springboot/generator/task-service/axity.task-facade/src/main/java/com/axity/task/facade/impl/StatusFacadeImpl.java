package com.axity.task.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axity.task.commons.dto.StatusDto;
import com.axity.task.commons.request.PaginatedRequestDto;
import com.axity.task.commons.response.GenericResponseDto;
import com.axity.task.commons.response.PaginatedResponseDto;
import com.axity.task.facade.StatusFacade;
import com.axity.task.service.StatusService;

/**
 * Class StatusFacadeImpl
 * @author jonathan.aldana@axity.com
 */
@Service
@Transactional
public class StatusFacadeImpl implements StatusFacade
{
  @Autowired
  private StatusService statusService;
  /**
   * {@inheritDoc}
   */
  @Override
  public PaginatedResponseDto<StatusDto> findStatuss( PaginatedRequestDto request )
  {
    return this.statusService.findStatuss( request );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericResponseDto<StatusDto> find( Integer id )
  {
    return this.statusService.find( id );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericResponseDto<StatusDto> create( StatusDto dto )
  {
    return this.statusService.create( dto );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericResponseDto<Boolean> update( StatusDto dto )
  {
    return this.statusService.update( dto );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericResponseDto<Boolean> delete( Integer id )
  {
    return this.statusService.delete( id );
  }
}
