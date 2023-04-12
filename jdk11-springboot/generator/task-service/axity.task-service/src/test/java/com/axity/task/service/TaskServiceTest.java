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
   * Test method for
   * {@link com.axity.task.service.impl.TaskServiceImpl#create(com.axity.task.commons.dto.TaskDto)}.
   */
  @Test
  @Disabled("TODO: Actualizar la prueba de acuerdo a la entidad")
  void testCreate() {
    var dto = new TaskDto();
    // Crear de acuerdo a la entidad

    var response = this.taskService.create(dto);
    assertNotNull(response);
    assertEquals(0, response.getHeader().getCode());
    assertNotNull(response.getBody());

    this.taskService.delete(dto.getId());
  }

  /**
   * Method to validate update
   */
  @Test
  @Disabled("TODO: Actualizar la prueba de acuerdo a la entidad")
  void testUpdate() {
    var task = this.taskService.find(1).getBody();
    // TODO: actualizar de acuerdo a la entidad

    var response = this.taskService.update(task);

    assertNotNull(response);
    assertEquals(0, response.getHeader().getCode());
    assertTrue(response.getBody());
    task = this.taskService.find(1).getBody();

    // Verificar que se actualice el valor
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
