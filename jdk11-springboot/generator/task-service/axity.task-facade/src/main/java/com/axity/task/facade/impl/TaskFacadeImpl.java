package com.axity.task.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axity.task.commons.dto.TaskDto;
import com.axity.task.commons.request.PaginatedRequestDto;
import com.axity.task.commons.response.GenericResponseDto;
import com.axity.task.commons.response.PaginatedResponseDto;
import com.axity.task.facade.TaskFacade;
import com.axity.task.service.TaskService;

/**
 * Class TaskFacadeImpl
 * @author jonathan.aldana@axity.com
 */
@Service
@Transactional
public class TaskFacadeImpl implements TaskFacade
{
  @Autowired
  private TaskService taskService;
  /**
   * {@inheritDoc}
   */
  @Override
  public PaginatedResponseDto<TaskDto> findTasks( PaginatedRequestDto request )
  {
    return this.taskService.findTasks( request );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericResponseDto<TaskDto> find( Integer id )
  {
    return this.taskService.find( id );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericResponseDto<TaskDto> create( TaskDto dto )
  {
    return this.taskService.create( dto );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericResponseDto<Boolean> update( TaskDto dto )
  {
    return this.taskService.update( dto );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericResponseDto<Boolean> delete( Integer id )
  {
    return this.taskService.delete( id );
  }
}
