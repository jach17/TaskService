package com.axity.task.facade;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.axity.task.commons.dto.StatusDto;
import com.axity.task.commons.request.PaginatedRequestDto;
import com.axity.task.commons.response.GenericResponseDto;
import com.axity.task.commons.response.PaginatedResponseDto;
import com.axity.task.facade.impl.StatusFacadeImpl;
import com.axity.task.service.StatusService;

/**
 * Class StatusFacadeTest
 * 
 * @author jonathan.aldana@axity.com
 */
class StatusFacadeTest
{
  private StatusFacade statusFacade;
  private StatusService statusService;

  @BeforeEach
  public void setUp()
  {
    this.statusFacade = new StatusFacadeImpl();

    this.statusService = mock( StatusService.class );
    ReflectionTestUtils.setField( this.statusFacade, "statusService", this.statusService );
  }

  /**
   * Test method for
   * {@link com.axity.task.facade.impl.StatusFacadeImpl#findStatuss(com.axity.task.commons.request.PaginatedRequestDto)}.
   */
  @Test
  void testFindStatuss()
  {
    int pageSize = 20;

    var data = new ArrayList<StatusDto>();
    for( int i = 0; i < pageSize; i++ )
    {
      data.add( this.createStatus( i + 1 ) );
    }
    var paginated = new PaginatedResponseDto<StatusDto>( 0, pageSize, 50, data );
    when( this.statusService.findStatuss( any( PaginatedRequestDto.class ) ) ).thenReturn( paginated );

    var result = this.statusFacade.findStatuss( new PaginatedRequestDto( pageSize, 0 ) );
    assertNotNull( result );
  }

  /**
   * Test method for {@link com.axity.task.facade.impl.StatusFacadeImpl#find(java.lang.String)}.
   */
  @Test
  void testFind()
  {
    var response = new GenericResponseDto<StatusDto>( this.createStatus( 1 ) );
    when( this.statusService.find( anyInt() ) ).thenReturn( response );

    var result = this.statusFacade.find( 1 );
    assertNotNull( result );
  }

  /**
   * Test method for
   * {@link com.axity.task.facade.impl.StatusFacadeImpl#create(com.axity.task.commons.dto.StatusDto)}.
   */
  @Test
  void testCreate()
  {
    var status = this.createStatus( 8 );
    var response = new GenericResponseDto<>( status );
    when( this.statusService.create( any( StatusDto.class ) ) ).thenReturn( response );

    var result = this.statusFacade.create( status );
    assertNotNull( result );
  }

  /**
   * Test method for
   * {@link com.axity.task.facade.impl.StatusFacadeImpl#update(com.axity.task.commons.dto.StatusDto)}.
   */
  @Test
  void testUpdate()
  {
    var status = this.createStatus( 1 );

    var response = new GenericResponseDto<>( true );
    when( this.statusService.update( any( StatusDto.class ) ) ).thenReturn( response );
    var result = this.statusFacade.update( status );
    assertNotNull( result );
  }

  /**
   * Test method for {@link com.axity.task.facade.impl.StatusFacadeImpl#delete(java.lang.String)}.
   */
  @Test
  void testDelete()
  {
    var response = new GenericResponseDto<>( true );
    when( this.statusService.delete( anyInt() ) ).thenReturn( response );

    var result = this.statusFacade.delete( 9 );
    assertNotNull( result );
  }

  private StatusDto createStatus( int i )
  {
    var status = new StatusDto();
    status.setId( i );
    return status;
  }
}
