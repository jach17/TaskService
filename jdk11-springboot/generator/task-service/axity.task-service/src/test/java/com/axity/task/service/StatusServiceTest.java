package com.axity.task.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
import com.axity.task.commons.enums.ErrorCode;
import com.axity.task.commons.exception.BusinessException;
import com.axity.task.commons.request.PaginatedRequestDto;

/**
 * Class StatusServiceTest
 * 
 * @author jonathan.aldana@axity.com
 */
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Transactional
class StatusServiceTest
{
  private static final Logger LOG = LoggerFactory.getLogger( StatusServiceTest.class );

  @Autowired
  private StatusService statusService;

  /**
   * Method to validate the paginated search
   */
  @Test
  void testFindStatuss()
  {
    var request = new PaginatedRequestDto();
    request.setLimit( 5 );
    request.setOffset( 0 );
    var statuss = this.statusService.findStatuss( request );

    LOG.info( "Response: {}", statuss );

    assertNotNull( statuss );
    assertNotNull( statuss.getData() );
    assertFalse( statuss.getData().isEmpty() );
  }

  /**
   * Method to validate the search by id
   * 
   * @param statusId
   */
  @ParameterizedTest
  @ValueSource(ints = { 1 })
  void testFind( Integer statusId )
  {
    var status = this.statusService.find( statusId );
    assertNotNull( status );
    LOG.info( "Response: {}", status );
  }

  /**
   * Method to validate the search by id inexistent
   */
  @Test
  void testFind_NotExists()
  {
    var status = this.statusService.find( 999999 );
    assertNull( status );
  }

  /**
   * Test method for
   * {@link com.axity.task.service.impl.StatusServiceImpl#create(com.axity.task.commons.dto.StatusDto)}.
   */
  @Test
  @Disabled("TODO: Actualizar la prueba de acuerdo a la entidad")
  void testCreate()
  {
    var dto = new StatusDto();
    // Crear de acuerdo a la entidad

    var response = this.statusService.create( dto );
    assertNotNull( response );
    assertEquals( 0, response.getHeader().getCode() );
    assertNotNull( response.getBody() );

    this.statusService.delete( dto.getId() );
  }

  /**
   * Method to validate update
   */
  @Test
  @Disabled("TODO: Actualizar la prueba de acuerdo a la entidad")
  void testUpdate()
  {
    var status = this.statusService.find( 1 ).getBody();
    // TODO: actualizar de acuerdo a la entidad

    var response = this.statusService.update( status );

    assertNotNull( response );
    assertEquals( 0, response.getHeader().getCode() );
    assertTrue( response.getBody() );
    status = this.statusService.find( 1 ).getBody();

    // Verificar que se actualice el valor
  }

  /**
   * Method to validate an inexistent registry
   */
  @Test
  void testUpdate_NotFound()
  {
    var status = new StatusDto();
    status.setId(999999);
    var ex = assertThrows( BusinessException.class, () -> this.statusService.update( status ) );

    assertEquals( ErrorCode.TASK_NOT_FOUND.getCode(), ex.getCode() );
  }

  /**
   * Test method for {@link com.axity.task.service.impl.StatusServiceImpl#delete(java.lang.String)}.
   */
  @Test
  void testDeleteNotFound()
  {
    var ex = assertThrows( BusinessException.class, () -> this.statusService.delete( 999999 ) );
    assertEquals( ErrorCode.TASK_NOT_FOUND.getCode(), ex.getCode() );
  }
}
