package com.axity.task.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axity.task.commons.dto.StatusDto;
import com.axity.task.commons.enums.ErrorCode;
import com.axity.task.commons.exception.BusinessException;
import com.axity.task.commons.request.MessageDto;
import com.axity.task.commons.request.PaginatedRequestDto;
import com.axity.task.commons.response.GenericResponseDto;
import com.axity.task.commons.response.PaginatedResponseDto;
import com.axity.task.model.StatusDO;
import com.axity.task.model.QStatusDO;
import com.axity.task.persistence.StatusPersistence;
import com.axity.task.service.StatusService;
import com.github.dozermapper.core.Mapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;

import lombok.extern.slf4j.Slf4j;

/**
 * Class StatusServiceImpl
 * 
 * @author jonathan.aldana@axity.com
 */
@Service
@Transactional
@Slf4j
public class StatusServiceImpl implements StatusService
{
  @Autowired
  private StatusPersistence statusPersistence;

  @Autowired
  private Mapper mapper;
  
  /**
   * {@inheritDoc}
   */
  @Override
  public PaginatedResponseDto<StatusDto> findStatuss( PaginatedRequestDto request )
  {
    log.debug( "%s", request );

    int page = request.getOffset() / request.getLimit();
    Pageable pageRequest = PageRequest.of( page, request.getLimit(), Sort.by( "id" ) );

    var paged = this.statusPersistence.findAll( pageRequest );

    var result = new PaginatedResponseDto<StatusDto>( page, request.getLimit(), paged.getTotalElements() );

    paged.stream().forEach( x -> result.getData().add( this.transform( x ) ) );

    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericResponseDto<StatusDto> find( Integer id )
  {
    GenericResponseDto<StatusDto> response = null;

    var optional = this.statusPersistence.findById( id );
    if( optional.isPresent() )
    {
      response = new GenericResponseDto<>();
      response.setBody( this.transform( optional.get() ) );
    }

    return response;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericResponseDto<StatusDto> create( StatusDto dto )
  {

    StatusDO entity = new StatusDO();
    this.mapper.map( dto, entity );
    entity.setId(null);

    this.statusPersistence.save( entity );

    dto.setId(entity.getId());

    return new GenericResponseDto<>( dto );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericResponseDto<Boolean> update( StatusDto dto )
  {
    var optional = this.statusPersistence.findById( dto.getId() );
    if( optional.isEmpty() )
    {
      throw new BusinessException( ErrorCode.TASK_NOT_FOUND );
    }

    var entity = optional.get();
    
    
    entity.setName( dto.getName() );

    this.statusPersistence.save( entity );

    return new GenericResponseDto<>( true );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericResponseDto<Boolean> delete( Integer id )
  {
    var optional = this.statusPersistence.findById( id );
    if( optional.isEmpty() )
    {
      throw new BusinessException( ErrorCode.TASK_NOT_FOUND );
    }

    var entity = optional.get();
    this.statusPersistence.delete( entity );

    return new GenericResponseDto<>( true );
  }

  private StatusDto transform( StatusDO entity )
  {
    StatusDto dto = null;
    if( entity != null )
    {
      dto = this.mapper.map( entity, StatusDto.class );
    }
    return dto;
  }
}
