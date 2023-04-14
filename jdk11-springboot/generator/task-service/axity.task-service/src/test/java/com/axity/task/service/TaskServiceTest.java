package com.axity.task.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import com.axity.task.commons.dto.StatusDto;
import com.axity.task.commons.dto.TaskDto;
import com.axity.task.commons.enums.ErrorCode;
import com.axity.task.commons.exception.BusinessException;
import com.axity.task.commons.request.PaginatedRequestDto;

/**
 * Class TaskServiceTest
 * 
 * @author jonathan.aldana@axity.com
 */
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Transactional
class TaskServiceTest {
  private static final Logger LOG = LoggerFactory.getLogger(TaskServiceTest.class);

  @Autowired
  private TaskService taskService;

  /**
   * Method to validate the paginated search
   */
  @Test
  void testFindTasks() {
    var request = new PaginatedRequestDto();
    request.setLimit(5);
    request.setOffset(0);
    var tasks = this.taskService.findTasks(request);

    LOG.info("Response: {}", tasks);

    assertNotNull(tasks);
    assertNotNull(tasks.getData());
    assertFalse(tasks.getData().isEmpty());
  }

  /**
   * Method to validate the search by id
   * 
   * @param taskId
   */
  @ParameterizedTest
  @ValueSource(ints = { 1 })
  void testFind(Integer taskId) {
    var task = this.taskService.find(taskId);
    assertNotNull(task);
    assertNotEquals(0, task.getBody().getId());
    assertNotEquals(ErrorCode.TASK_NOT_FOUND, task.getHeader().getCode());
    LOG.info("Response: {}", task);
  }

  /**
   * Method to validate the search by id inexistent
   */
  @Test
  void testFind_NotExists() {
    var task = this.taskService.find(999999);
    assertNull(task);
  }

  /**
   * Test method for create Task happy path
   * {@link com.axity.task.service.impl.TaskServiceImpl#create(com.axity.task.commons.dto.TaskDto)}.
   */
  @Test
  void testCreate() {
    // Data inicial
    var dto = new TaskDto();
    dto.setName("Tarea como ejemplo");
    dto.setStatus(getStatusDto(1, "Pendiente"));

    // llamada
    var response = this.taskService.create(dto);

    // validacion
    assertNotNull(response);
    assertEquals(ErrorCode.SUCCESSFULY_RESULT.getCode(), response.getHeader().getCode());
    assertNotNull(response.getBody());
    this.taskService.delete(dto.getId());
  }

  /**
   * Test method for create Task without status
   * {@link com.axity.task.service.impl.TaskServiceImpl#create(com.axity.task.commons.dto.TaskDto)}.
   */
  @Test
  void testCreateWithOutStatus() {
    // data inicial
    var dto = new TaskDto();
    dto.setName("Tarea como ejemplo");
    // llamada
    var response = this.taskService.create(dto);
    // validacion
    assertNotNull(response);
    assertEquals(ErrorCode.REQUIRED_FIELD.getCode(), response.getHeader().getCode());
    assertNull(response.getBody());
  }

  /**
   * Test method for create Task without name
   * {@link com.axity.task.service.impl.TaskServiceImpl#create(com.axity.task.commons.dto.TaskDto)}.
   */
  @Test
  void testCreateWithOutName() {
    // data inicial
    var dto = new TaskDto();
    dto.setStatus(getStatusDto(1, "Pendiente"));
    // llamada
    var response = this.taskService.create(dto);
    // validacion
    assertNotNull(response);
    assertEquals(ErrorCode.REQUIRED_FIELD.getCode(), response.getHeader().getCode());
    assertNull(response.getBody());
  }

  private StatusDto getStatusDto(int type, String name) {
    StatusDto status = new StatusDto();
    status.setId(type);
    status.setName(name);
    return status;
  }

  /**
   * Test method for create Task empty data
   * {@link com.axity.task.service.impl.TaskServiceImpl#create(com.axity.task.commons.dto.TaskDto)}.
   */
  @Test
  void testCreateWithEmptyData() {
    // data inicial
    var dto = new TaskDto();
    // llamada
    var response = this.taskService.create(dto);
    // validacion
    LOG.info("Response -> " + response);
    assertNotNull(response);
    assertEquals(ErrorCode.REQUIRED_FIELD.getCode(), response.getHeader().getCode());
    assertNull(response.getBody());
  }

  /**
   * Method to validate update happy path
   */
  @Test
  void testUpdate() {
    // Data inicial
    var task = this.taskService.find(1).getBody();
    var nameUpdated = "Nombre actualizado";

    task.setName(nameUpdated);
    // llamada
    var response = this.taskService.update(task);

    // validacion
    assertNotNull(response);
    assertEquals(ErrorCode.SUCCESSFULY_RESULT.getCode(), response.getHeader().getCode());
    assertTrue(response.getBody());
    task = this.taskService.find(1).getBody();

    // Verificar que se actualice el valor
    assertEquals(task.getName(), nameUpdated);
  }

  /**
   * Method to validate an inexistent registry
   */
  @Test
  void testUpdate_NotFound() {
    var task = new TaskDto();
    task.setId(999999);
    var ex = assertThrows(BusinessException.class, () -> this.taskService.update(task));

    assertEquals(ErrorCode.TASK_NOT_FOUND.getCode(), ex.getCode());
  }

  /**
   * Test method for
   * {@link com.axity.task.service.impl.TaskServiceImpl#delete(java.lang.String)}.
   */
  @Test
  void testDeleteNotFound() {
    var ex = assertThrows(BusinessException.class, () -> this.taskService.delete(999999));
    assertEquals(ErrorCode.TASK_NOT_FOUND.getCode(), ex.getCode());
  }
}

/***
 * Permite el crud básico
 * Valida los elementos escritos
 * No puede crear más status
 * se pueden actualizar las tareas
 * se puede actualizar el estatus de las tareas
 * se pueden listar todas las tareas pendientes
 * se pueden listar todas las tareas completadas
 * se pueden listar todas las tareas
 * Controlar los errores
 */
