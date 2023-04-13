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

import com.axity.task.commons.dto.TaskDto;
import com.axity.task.commons.enums.ErrorCode;
import com.axity.task.commons.exception.BusinessException;
import com.axity.task.commons.request.MessageDto;
import com.axity.task.commons.request.PaginatedRequestDto;
import com.axity.task.commons.response.GenericResponseDto;
import com.axity.task.commons.response.HeaderDto;
import com.axity.task.commons.response.PaginatedResponseDto;
import com.axity.task.model.TaskDO;
import com.axity.task.model.QTaskDO;
import com.axity.task.persistence.TaskPersistence;
import com.axity.task.service.TaskService;
import com.github.dozermapper.core.Mapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;

import lombok.extern.slf4j.Slf4j;

/**
 * Class TaskServiceImpl
 * 
 * @author jonathan.aldana@axity.com
 */
@Service
@Transactional
@Slf4j
public class TaskServiceImpl implements TaskService {
  @Autowired
  private TaskPersistence taskPersistence;

  @Autowired
  private Mapper mapper;

  /**
   * {@inheritDoc}
   */
  @Override
  public PaginatedResponseDto<TaskDto> findTasks(PaginatedRequestDto request) {
    log.debug("%s", request);

    int page = request.getOffset() / request.getLimit();
    Pageable pageRequest = PageRequest.of(page, request.getLimit(), Sort.by("id"));

    var paged = this.taskPersistence.findAll(pageRequest);

    var result = new PaginatedResponseDto<TaskDto>(page, request.getLimit(), paged.getTotalElements());

    paged.stream().forEach(x -> result.getData().add(this.transform(x)));

    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericResponseDto<TaskDto> find(Integer id) {
    GenericResponseDto<TaskDto> response = null;

    var optional = this.taskPersistence.findById(id);
    if (optional.isPresent()) {
      response = new GenericResponseDto<>();
      response.setBody(this.transform(optional.get()));
    }

    return response;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericResponseDto<TaskDto> create(TaskDto dto) {
    if (dto.getStatus() == null) {
      return getGenericResponse(ErrorCode.REQUIRED_FIELD.getCode(), "No se encontró un estatus en la tarea");
    }
    if (dto.getName() == null) {
      return getGenericResponse(ErrorCode.REQUIRED_FIELD.getCode(), "No se encontró un nombre para la tarea");
    }
    TaskDO entity = new TaskDO();
    this.mapper.map(dto, entity);
    entity.setId(null);

    this.taskPersistence.save(entity);

    dto.setId(entity.getId());

    return new GenericResponseDto<>(dto);
  }

  private GenericResponseDto<TaskDto> getGenericResponse(int code, String msg) {
    var response = new GenericResponseDto<TaskDto>();
    var header = new HeaderDto();
    header.setCode(code);
    header.setMessage(msg);
    response.setHeader(header);
    return response;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericResponseDto<Boolean> update(TaskDto dto) {
    var optional = this.taskPersistence.findById(dto.getId());
    if (optional.isEmpty()) {
      throw new BusinessException(ErrorCode.TASK_NOT_FOUND);
    }

    var entity = optional.get();

    entity.setName(dto.getName());
    // TODO: Actualizar entity.Status (?)

    this.taskPersistence.save(entity);

    return new GenericResponseDto<>(true);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericResponseDto<Boolean> delete(Integer id) {
    var optional = this.taskPersistence.findById(id);
    if (optional.isEmpty()) {
      throw new BusinessException(ErrorCode.TASK_NOT_FOUND);
    }

    var entity = optional.get();
    this.taskPersistence.delete(entity);

    return new GenericResponseDto<>(true);
  }

  private TaskDto transform(TaskDO entity) {
    TaskDto dto = null;
    if (entity != null) {
      dto = this.mapper.map(entity, TaskDto.class);
    }
    return dto;
  }
}
